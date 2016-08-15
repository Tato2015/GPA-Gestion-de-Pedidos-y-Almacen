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
import pe.gob.servir.gpa.model.dto.TipoDTO;
import pe.gob.servir.gpa.service.ejb.TipoServiceLocal;
import pe.gob.servir.gpa.util.ConstanteWeb;
import pe.gob.servir.gpa.util.UBaseManage;

@ManagedBean(name = "tipoBean")
@ViewScoped
public class TipoBean extends UBaseManage implements Serializable 
{

	
	private static final long serialVersionUID = 9087457344640420167L;


	/** permite capturar los erroes de ejecucion **/
	private Logger log = Logger.getLogger(TipoBean.class);

	@EJB
	private TipoServiceLocal tipoServiceLocal;

	/** Variable contexto */
	private RequestContext context;
	/** Variable tipoDTO */
	private String titulo;
	private Integer posicion;
	private Boolean isEditar;
	private Boolean isActivar;
	private TipoDTO tipoDTO;
	private List<TipoDTO> listaTipoPadre;
	private List<TipoDTO> listaTipo;
	private String usuarioSessio;
	private String usuarioModificacion;

	// varible listado se encuentra en Criterios de búsqueda
	private Integer identificadorPadre;
	// varible estado se encuentra en Criterios de búsqueda
	private Integer identificadorEstado;

	private List<SelectItem> listaEstado;

	// Constructor vacío de la clase TipoBean
	public TipoBean() {
	}

	@PostConstruct
	public void Inicializando() {
		this.usuarioSessio = "usuario";
		this.usuarioModificacion = "usu modificacion";
		this.listaTipoPadre = new ArrayList<TipoDTO>();
		this.listaTipo = new ArrayList<TipoDTO>();
		this.tipoDTO = new TipoDTO();		
		this.tipoDTO.setEstado(new EstadoDTO());
		this.tipoDTO.setPadre(new TipoDTO());
		this.identificadorPadre = null;
		this.identificadorEstado = null;
		generarListaEstado();
		buscarTipo();
		buscarTipoPadre();
	}

	public void generarListaEstado() {
		listaEstado = new ArrayList<>();
		listaEstado.add(new SelectItem(null, ConstanteWeb.CAMPO_SELECCIONE));
		listaEstado.add(new SelectItem(ConstanteWeb.VALOR_ACTIVO,
				ConstanteWeb.CAMPO_ACTIVO));
		listaEstado.add(new SelectItem(ConstanteWeb.VALOR_INACTIVO,
				ConstanteWeb.CAMPO_INACTIVO));
	}

	public void abrirPopupNuevoTipo() {
		this.tipoDTO = new TipoDTO();
		this.tipoDTO.setPadre(new TipoDTO());
		isEditar = false;
		this.titulo = "NUEVO TIPO";
		context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogoNuevoTipo').show();");
	}

	// La listaTipo devuelve una lista con todos los objetos hijos
	public void buscarTipo() {
		try {
			this.listaTipo = new ArrayList<TipoDTO>();
			if (identificadorPadre == null || identificadorPadre == 0) 
			{
				List<TipoDTO> listaReal = tipoServiceLocal.buscarTipo(
						identificadorPadre, identificadorEstado);
				if (listaReal != null && listaReal.size() > 0) 
				{
					for (TipoDTO objeto : listaReal) {
						if (objeto.getPadre().getId() != null
								&& objeto.getPadre().getId() != 0) {
							listaTipo.add(objeto);
						}
					}
				}
			} else {
				listaTipo = tipoServiceLocal.buscarTipo(identificadorPadre,
						identificadorEstado);
			}
		} catch (Exception e) {
		}
	}

	public void buscarTipoPadre() {
		try {

			List<TipoDTO> listaReal = tipoServiceLocal.buscarTipo(null, identificadorEstado);
			if (listaReal != null && listaReal.size() > 0) {

				for (TipoDTO objeto : listaReal) {
					if (objeto.getPadre().getId() == null
							|| objeto.getPadre().getId() == 0) {
						listaTipoPadre.add(objeto);
					}
				}

			}

		} catch (Exception e) {
			System.out.println("Error " + e.getMessage());
		}
	}

	// Método que consiste en limpiar la tabla y devuelve los valores por
	// defecto a cada SelectOneMenu
	public void limpiarTipo() {
		listaTipo = new ArrayList<>();
		this.identificadorPadre = null;
		this.identificadorEstado = null;
	}

	public void registrarTipo() {
		try {			
			tipoDTO.setCodigoUsuarioRegistro(usuarioSessio);
			tipoDTO.setIpRegistro(obtieneIPCliente());
			tipoDTO.setEstado(new EstadoDTO());
			tipoDTO.getEstado().setId(1);
			tipoDTO = tipoServiceLocal.registrarTipo(tipoDTO);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Aviso",
					"Se registro con éxito"));
			cerrarPopupTipo();
			cerrarMensajeConfirmacion();
			listaTipo.add(tipoDTO);
			limpiarTipo();			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void abrirPopupActualizarTipo(Integer posicion) 
	{
		obtenerDatosTipo(posicion);
		this.posicion = posicion;
		isEditar = true;
		titulo = "ACTUALIZAR TIPO";
		context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogoNuevoTipo').show();");
	}
		
	private void obtenerDatosTipo(Integer posicion) 
	{
		TipoDTO objeto = listaTipo.get(posicion.intValue());
		tipoDTO = new TipoDTO();
		tipoDTO.setId(objeto.getId());
		tipoDTO.setPadre(new TipoDTO());
		tipoDTO.getPadre().setId(objeto.getPadre().getId());
		tipoDTO.setCodigoTipo(objeto.getCodigoTipo());
		tipoDTO.setDescripcionTipo(objeto.getDescripcionTipo());
		tipoDTO.setAbreviaturaTipo(objeto.getAbreviaturaTipo());
		tipoDTO.setIndicadorActivo(objeto.getIndicadorActivo());		
		tipoDTO.setIpRegistro(objeto.getIpRegistro());
		tipoDTO.setFechaRegistro(objeto.getFechaRegistro());
		tipoDTO.setCodigoUsuarioModificacion(objeto.getCodigoUsuarioModificacion());
		tipoDTO.setIpModificacion(objeto.getIpModificacion());
		tipoDTO.setFechaModificacion(objeto.getFechaModificacion());
		tipoDTO.setEstado(new EstadoDTO());
		tipoDTO.getEstado().setId(objeto.getEstado().getId());
		tipoDTO.getPadre().setDescripcionTipo(objeto.getPadre().getDescripcionTipo());

	}
		
	public void actualizarTipo() 
	{
		try {			
			tipoDTO.setCodigoUsuarioModificacion(usuarioModificacion);
			tipoDTO.setIpModificacion(obtieneIPCliente());
			tipoDTO.setEstado(new EstadoDTO());
			Integer identificadorEstado = ConstanteWeb.VALOR_REGISTRADO;
			tipoDTO.setEstado(new EstadoDTO());
			tipoDTO.getEstado().setId(identificadorEstado);
			
			Boolean isActualizo = tipoServiceLocal.actualizarTipo(tipoDTO);
			if (isActualizo) {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Aviso",
						"Se actualizó con éxito"));
				cerrarPopupTipo();
				cerrarMensajeConfirmacion();				
				listaTipo.set(posicion,tipoDTO);
				limpiarTipo();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	public void abrirActualizarEstado(Integer posicion) 
	{
		tipoDTO = new TipoDTO();
		this.titulo = ConstanteWeb.TITULO_EDITAR_PRODUCTO;
		obtenerDatosTipo(posicion.intValue());
		this.posicion = posicion;
		this.isActivar = false;
		if (tipoDTO.getEstado().getId() == ConstanteWeb.VALOR_REGISTRADO.intValue()
			&& tipoDTO.getIndicadorActivo() == ConstanteWeb.INDICADRO_INACTIVO.intValue()) {
			this.isActivar = true;
		}
		context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogoActualizarEstado').show();");
	}

	public void actualizarEstadoTipo() {
		try {
			if (isActivar == true) {
				tipoDTO.setIndicadorActivo(ConstanteWeb.INDICADRO_ACTIVO.shortValue());
			} else {
				tipoDTO.setIndicadorActivo(ConstanteWeb.INDICADRO_INACTIVO.shortValue());
			}
			tipoDTO.setCodigoUsuarioModificacion(usuarioSessio);
			tipoDTO.setIpModificacion(obtieneIPCliente());
			tipoServiceLocal.actualizarTipo(tipoDTO);

			listaTipo.set(posicion, tipoDTO);
			this.posicion = null;
			context = RequestContext.getCurrentInstance();
			context.execute("PF('popupConfirmar').hide();");
			context = RequestContext.getCurrentInstance();
			context.execute("PF('dialogoActualizarEstado').hide();");
			FacesContext context = FacesContext.getCurrentInstance();

			if (isActivar == true) {
				context.addMessage(null, new FacesMessage("Aviso",
						"Se activo con éxito"));
			} else {
				context.addMessage(null, new FacesMessage("Aviso",
						"Se inactivo con éxito"));
			}
			buscarTipo();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	public void mensajeConfirmacion() 
	{
		context = RequestContext.getCurrentInstance();
		context.execute("PF('popupConfirmar').show();");
	}

	public void cerrarPopupTipo() {
		context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogoNuevoTipo').hide();");
	}

	public void cerrarMensajeConfirmacion() {
		context = RequestContext.getCurrentInstance();
		context.execute("PF('popupConfirmar').hide();");
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<SelectItem> getListaEstado() {
		return listaEstado;
	}

	public void setListaEstado(List<SelectItem> listaEstado) {
		this.listaEstado = listaEstado;
	}

	public TipoDTO getTipoDTO() {
		return tipoDTO;
	}

	public void setTipoDTO(TipoDTO tipoDTO) {
		this.tipoDTO = tipoDTO;
	}

	public Integer getIdentificadorPadre() {
		return identificadorPadre;
	}

	public void setIdentificadorPadre(Integer identificadorPadre) {
		this.identificadorPadre = identificadorPadre;
	}

	public List<TipoDTO> getListaTipo() {
		return listaTipo;
	}

	public void setListaTipo(List<TipoDTO> listaTipo) {
		this.listaTipo = listaTipo;
	}

	public Integer getIdentificadorEstado() {
		return identificadorEstado;
	}

	public void setIdentificadorEstado(Integer identificadorEstado) {
		this.identificadorEstado = identificadorEstado;
	}

	public Boolean getIsEditar() {
		return isEditar;
	}

	public void setIsEditar(Boolean isEditar) {
		this.isEditar = isEditar;
	}

	public Integer getPosicion() {
		return posicion;
	}

	public void setPosicion(Integer posicion) {
		this.posicion = posicion;
	}

	public String getUsuarioSessio() {
		return usuarioSessio;
	}

	public void setUsuarioSessio(String usuarioSessio) {
		this.usuarioSessio = usuarioSessio;
	}

	public Boolean getIsActivar() {
		return isActivar;
	}

	public void setIsActivar(Boolean isActivar) {
		this.isActivar = isActivar;
	}

	public List<TipoDTO> getListaTipoPadre() {
		return listaTipoPadre;
	}

	public void setListaTipoPadre(List<TipoDTO> listaTipoPadre) {
		this.listaTipoPadre = listaTipoPadre;
	}

}
