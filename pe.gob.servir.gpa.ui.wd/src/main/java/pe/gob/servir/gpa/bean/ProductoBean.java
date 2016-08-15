package pe.gob.servir.gpa.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import pe.gob.servir.gpa.model.dto.EstadoDTO;
import pe.gob.servir.gpa.model.dto.ProductoDTO;
import pe.gob.servir.gpa.model.dto.TipoDTO;
import pe.gob.servir.gpa.service.ejb.ProductoServiceLocal;
import pe.gob.servir.gpa.service.ejb.TipoServiceLocal;
import pe.gob.servir.gpa.util.ConstanteWeb;
import pe.gob.servir.gpa.util.UBaseManage;

@ManagedBean(name="productoBean")
@ViewScoped
public class ProductoBean extends UBaseManage implements Serializable
{
	private static final long serialVersionUID = -5624971016336610172L;	
	private Logger log = Logger.getLogger(ProductoBean.class);
	
	@EJB
	private ProductoServiceLocal productoServicioLocal;
	@EJB
	private TipoServiceLocal tipoServicioLocal;
	
	//Variable de contexto
	private RequestContext context;
	private Integer identificadorTipoFamilia;
	private Integer identificadorTipoCategoria;
	private Integer identificadorEstado;
	private String descripcionProducto;
	private String codigoProducto;
	
	//--
	private List<ProductoDTO>listaProducto;
	private List<TipoDTO> listaTipoFamilia;
	private List<TipoDTO>listaTipoCategoria;
	private List<SelectItem> listaEstado;
	private Integer posicion;
	private String titulo;
	private ProductoDTO productoDTO;
	private boolean paraModificar;
	
	
	//Constructor sin parámetros
	public ProductoBean()
	{
		
	}
	
	@PostConstruct
	public void inicializando()
	{			
		this.identificadorTipoFamilia=null;
		this.identificadorTipoCategoria=null;
		this.identificadorEstado=null;
		this.descripcionProducto = "";
		this.codigoProducto = "";
		this.paraModificar=false;
		generarTipoFamilia();
		generarTipoCategoria();
		generarListaEstado();
		buscarProducto();
	}
	
	//Método que lista los productos que están activados, es decir indicador activo = 1.
	public void buscarProductoCatalogo()
	{
		try 
		{
			this.listaProducto = new ArrayList<ProductoDTO>();
			if
				(
				(identificadorTipoFamilia == null || identificadorTipoFamilia ==0) &&
				(identificadorTipoCategoria == null || identificadorTipoCategoria ==0) &&
				(identificadorEstado == null || identificadorEstado ==0) &&
				(descripcionProducto == null || descripcionProducto.equals(""))&&
				(codigoProducto == null || codigoProducto.equals(""))
				)
			{															
				List<ProductoDTO>listaReal = productoServicioLocal.buscarProducto(identificadorTipoFamilia, identificadorTipoCategoria, identificadorEstado, descripcionProducto, codigoProducto);
				if(listaReal != null && listaReal.size() >=0)
				{
					for (ProductoDTO productoDTO : listaReal) {
						if(productoDTO.getIndicadorActivo() == ConstanteWeb.INDICADRO_ACTIVO.intValue())
						{
							listaProducto.add(productoDTO);	
						}else
						{
							
						}
						
					}
				}				
			}else
			{
//				if(productoDTO.getIndicadorActivo() == ConstanteWeb.INDICADRO_ACTIVO.intValue())				
//				{
					listaProducto =productoServicioLocal.buscarProducto(identificadorTipoFamilia, identificadorTipoCategoria, identificadorEstado, descripcionProducto, codigoProducto);					
//				}	 
			}
		} 
		catch (Exception e) 
		{
		
		}
	}
	
	//Método que sirve para listar todos los productos,activos e inactivos
	public void buscarProducto()
	{
		try 
		{
			this.listaProducto = new ArrayList<ProductoDTO>();
			if
				(
				(identificadorTipoFamilia == null || identificadorTipoFamilia ==0) &&
				(identificadorTipoCategoria == null || identificadorTipoCategoria ==0) &&
				(identificadorEstado == null || identificadorEstado ==0) &&
				(descripcionProducto == null || descripcionProducto.equals(""))&&
				(codigoProducto == null || codigoProducto.equals(""))
				)
			{															
				List<ProductoDTO>listaReal = productoServicioLocal.buscarProducto(identificadorTipoFamilia, identificadorTipoCategoria, identificadorEstado, descripcionProducto, codigoProducto);
				if(listaReal != null && listaReal.size() >=0)
				{
					for (ProductoDTO productoDTO : listaReal) {						
							listaProducto.add(productoDTO);													
					}
				}				
			}else
			{				
					listaProducto =productoServicioLocal.buscarProducto(identificadorTipoFamilia, identificadorTipoCategoria, identificadorEstado, descripcionProducto, codigoProducto);					
				 
			}
		} 
		catch (Exception e) 
		{
		
		}
	}
	
	
	
	
	public void generarTipoFamilia()
	{
		try 
		{
			List<TipoDTO>listaReal;
			this.listaTipoFamilia = new ArrayList<TipoDTO>();
			//En la BD el id del tipo Familia es 14 :D, el id del estado registrado es 1.
			Integer idTipoFamilia = 14;
			Integer idEstadoActivo=1;
			listaReal = tipoServicioLocal.buscarTipo(idTipoFamilia, idEstadoActivo);
			if(listaReal !=null && listaReal.size()>=0)
			{
				for(TipoDTO tipoDTO:listaReal)
				{
					this.listaTipoFamilia.add(tipoDTO);
				}
			}else
			{
				System.err.println("Error,al parecer no existe objetos de tipo familia");
			}
			
		}
		catch (Exception e) 
		{
			System.err.println("Error "+e.getMessage());
		}
	}
	
	public void generarTipoCategoria()
	{		
		try 
		{
			List<TipoDTO>listaReal;
			this.listaTipoCategoria = new ArrayList<TipoDTO>();
			//En la BD el id del tipo Familia es 13 :D, el id del estado registrado es 1.
			Integer idTipoCategoria = 13;
			Integer idEstadoActivo=1;
			listaReal = tipoServicioLocal.buscarTipo(idTipoCategoria, idEstadoActivo);
			if(listaReal!=null && listaReal.size()>=0)
			{
				for(TipoDTO tipoDTO:listaReal)
				{
					this.listaTipoCategoria.add(tipoDTO);
				}
			}else
			{
				System.out.println("Error,al parecer no existe objetos de tipo familia");
			}
		} 
		catch (Exception e) 
		{
			System.err.println("Error "+e.getMessage());
		}		
	}
	
	public void generarListaEstado() 
	{
		listaEstado = new ArrayList<>();
		listaEstado.add(new SelectItem(null, ConstanteWeb.CAMPO_SELECCIONE));
		listaEstado.add(new SelectItem(ConstanteWeb.VALOR_REGISTRADO,
				ConstanteWeb.CAMPO_REGISTRADO));
		
	}
	
	public void limpiarBusqueda()
	{
		this.listaProducto = new ArrayList<>();
		this.identificadorTipoCategoria = null;
		this.identificadorTipoFamilia = null;
		this.identificadorEstado = null;
		this.descripcionProducto = "";
		this.codigoProducto = "";
	}
	
	
	public void abrirDetalleProducto(Integer posicion)
	{
		obtenerDatosProducto(posicion);
		this.posicion = posicion;
//		this.titulo = "DETALLES DEL PRODUCTO";
		context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogoDetalleProducto').show();");		
	}
	
	public void generarDetalleProducto(Integer posicion)
	{
		this.paraModificar = false;
		this.titulo="DETALLES DEL PRODUCTO";
		abrirDetalleProducto(posicion);
	}
	
	public void generarModificarProducto(Integer posicion)
	{
		this.paraModificar = true;
		this.titulo="MODIFICAR PRODUCTO";
		abrirDetalleProducto(posicion);
	}
	
	public void obtenerDatosProducto(Integer posicion)
	{
		ProductoDTO objeto = listaProducto.get(posicion.intValue());
		this.productoDTO = new ProductoDTO();
		this.productoDTO.setId(objeto.getId());
		this.productoDTO.setCodigoProducto(objeto.getCodigoProducto());
		this.productoDTO.setDescripcionProducto(objeto.getDescripcionProducto());		
		this.productoDTO.setTipoFamilia(new TipoDTO());
		this.productoDTO.getTipoFamilia().setId(objeto.getTipoFamilia().getId());
		this.productoDTO.setTipoCategoria(new TipoDTO());
		this.productoDTO.getTipoCategoria().setId(objeto.getTipoCategoria().getId());
		this.productoDTO.setTipoUnidadMedida(new TipoDTO());
		this.productoDTO.getTipoUnidadMedida().setId(objeto.getTipoUnidadMedida().getId());
		this.productoDTO.setStockMinimo(objeto.getStockMinimo());
		this.productoDTO.setStockMaximo(objeto.getStockMaximo());
		this.productoDTO.setHabilitado(objeto.getHabilitado());
		this.productoDTO.setIndicadorActivo(objeto.getIndicadorActivo());
		this.productoDTO.setCodigoUsuarioRegistro(objeto.getCodigoUsuarioRegistro());
		this.productoDTO.setIpRegistro(objeto.getIpRegistro());
		this.productoDTO.setFechaRegistro(objeto.getFechaRegistro());
		this.productoDTO.setCodigoUsuarioModificacion(objeto.getCodigoUsuarioModificacion());
		this.productoDTO.setIpModificacion(objeto.getIpModificacion());
		this.productoDTO.setFechaModificacion(objeto.getFechaModificacion());
		this.productoDTO.setEstado(new EstadoDTO());
		this.productoDTO.getEstado().setId(objeto.getEstado().getId());		
		this.productoDTO.getTipoFamilia().setDescripcionTipo(objeto.getTipoFamilia().getDescripcionTipo());
		this.productoDTO.getTipoCategoria().setDescripcionTipo(objeto.getTipoCategoria().getDescripcionTipo());
		this.productoDTO.getTipoUnidadMedida().setDescripcionTipo(objeto.getTipoUnidadMedida().getDescripcionTipo());
		this.productoDTO.getEstado().setDescripcionEstado(objeto.getEstado().getDescripcionEstado());		
	}
	
	public void actualizarProducto()
	{
		try 
		{			
			this.productoDTO.setCodigoUsuarioModificacion("usuario");
			this.productoDTO.setIpModificacion(obtieneIPCliente());
			Boolean seActualizo = productoServicioLocal.actualizarProducto(productoDTO);

			if (seActualizo == true) 
			{
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Aviso",
						"Se actualizó con éxito"));
				cerrarPopupProducto();
				cerrarMensajeConfirmacion();				
				listaProducto.set(posicion,productoDTO);
				limpiarBusqueda();
			} 
		} catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		}
	}
	
	
	public void abrirCatalogoPopup()
	{
		buscarProductoCatalogo();
		context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogoCatalogoProducto').show();");		
	}
	
	

		

	public void mensajeConfirmacion() 
	{
		context = RequestContext.getCurrentInstance();
		context.execute("PF('popupConfirmar').show();");
	}
	
	public void cerrarPopupProducto() 
	{
		context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogoDetalleProducto').hide();");
	}

	public void cerrarMensajeConfirmacion() 
	{
		context = RequestContext.getCurrentInstance();
		context.execute("PF('popupConfirmar').hide();");
	}

	

	// Métodos de acceso
	
	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public ProductoServiceLocal getProductoServicioLocal() {
		return productoServicioLocal;
	}

	public void setProductoServicioLocal(ProductoServiceLocal productoServicioLocal) {
		this.productoServicioLocal = productoServicioLocal;
	}
	
	public Integer getIdentificadorTipoFamilia() {
		return identificadorTipoFamilia;
	}

	public void setIdentificadorTipoFamilia(Integer identificadorTipoFamilia) {
		this.identificadorTipoFamilia = identificadorTipoFamilia;
	}

	public Integer getIdentificadorTipoCategoria() {
		return identificadorTipoCategoria;
	}

	public void setIdentificadorTipoCategoria(Integer identificadorTipoCategoria) {
		this.identificadorTipoCategoria = identificadorTipoCategoria;
	}

	public Integer getIdentificadorEstado() {
		return identificadorEstado;
	}

	public void setIdentificadorEstado(Integer identificadorEstado) {
		this.identificadorEstado = identificadorEstado;
	}

	public String getDescripcionProducto() {
		return descripcionProducto;
	}

	public void setDescripcionProducto(String descripcionProducto) {
		this.descripcionProducto = descripcionProducto;
	}

	public String getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public List<ProductoDTO> getListaProducto() {
		return listaProducto;
	}

	public void setListaProducto(List<ProductoDTO> listaProducto) {
		this.listaProducto = listaProducto;
	}

	public TipoServiceLocal getTipoServicioLocal() {
		return tipoServicioLocal;
	}

	public void setTipoServicioLocal(TipoServiceLocal tipoServicioLocal) {
		this.tipoServicioLocal = tipoServicioLocal;
	}

	public List<TipoDTO> getListaTipoFamilia() {
		return listaTipoFamilia;
	}

	public void setListaTipoFamilia(List<TipoDTO> listaTipoFamilia) {
		this.listaTipoFamilia = listaTipoFamilia;
	}

	public List<TipoDTO> getListaTipoCategoria() {
		return listaTipoCategoria;
	}

	public void setListaTipoCategoria(List<TipoDTO> listaTipoCategoria) {
		this.listaTipoCategoria = listaTipoCategoria;
	}

	public List<SelectItem> getListaEstado() {
		return listaEstado;
	}

	public void setListaEstado(List<SelectItem> listaEstado) {
		this.listaEstado = listaEstado;
	}

	public Integer getPosicion() {
		return posicion;
	}

	public void setPosicion(Integer posicion) {
		this.posicion = posicion;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public ProductoDTO getProductoDTO() {
		return productoDTO;
	}

	public void setProductoDTO(ProductoDTO productoDTO) {
		this.productoDTO = productoDTO;
	}

	public boolean isParaModificar() {
		return paraModificar;
	}

	public void setParaModificar(boolean paraModificar) {
		this.paraModificar = paraModificar;
	}

	
	
	
}
