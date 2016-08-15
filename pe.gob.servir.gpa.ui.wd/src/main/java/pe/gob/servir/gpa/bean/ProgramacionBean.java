package pe.gob.servir.gpa.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import pe.gob.servir.gpa.model.dto.AreaDTO;
import pe.gob.servir.gpa.model.dto.EstadoDTO;
import pe.gob.servir.gpa.model.dto.ProductoDTO;
import pe.gob.servir.gpa.model.dto.ProgramacionDTO;
import pe.gob.servir.gpa.model.dto.TipoDTO;



import pe.gob.servir.gpa.service.ejb.AreaServiceLocal;
import pe.gob.servir.gpa.service.ejb.ProgramacionServiceLocal;
import pe.gob.servir.gpa.service.ejb.TipoServiceLocal;
import pe.gob.servir.gpa.util.ConstanteWeb;
import pe.gob.servir.gpa.util.UBaseManage;
import pe.gob.servir.gpa.util.Utilitarios;
@ManagedBean(name="programacionBean")
@ViewScoped
public class ProgramacionBean extends UBaseManage implements Serializable
{
	
	private static final long serialVersionUID = 5721137892605529004L;
	
	private Logger log = Logger.getLogger(ProgramacionBean.class);
	
	@EJB
	private ProgramacionServiceLocal programacionServiceLocal;
	@EJB
	private TipoServiceLocal tipoServicioLocal;
	@EJB
	private AreaServiceLocal areaServiceLocal;
	
	//Variable de contexto
	private RequestContext context;
	private Integer anio;
	private Integer aprobacion;
	private Integer estado;
	private Integer tipoPedido;
	private Integer tipoPeriodo;	
	
	//--
	private List<ProgramacionDTO>listaProgramacion;
	private ProgramacionDTO programacionDTO;
	private AreaDTO areaDTO;
	private List<SelectItem>listaEstado;
	private List<SelectItem>listaAnios;
	private List<SelectItem>listaAprobacion;
	private List<TipoDTO>listaTipoPeriodo;
	private List<TipoDTO>listaTipoPedido;
	private Map<Integer,Map<Integer,String>>data1=new HashMap<Integer,Map<Integer,String>>();	
	private Map<Integer,String>pedidos;
	private Map<Integer,String>periodos;
	private Utilitarios utilitario = new Utilitarios();
	private Boolean paraCrear;
	private Boolean paraVerDetalles;
	private Boolean paraModificar;
	private String usuarioSesion;
	private Integer posicion;
	private List<AreaDTO>listaArea;
	private String[]seleccionAreas;
	private Boolean seleccionProducto;
	private Integer cantidadProducto;
	@ManagedProperty("#{productoBean}")
	private ProductoBean productoBean;
	private ProductoDTO productoDTO;
	
	//Constructor sin parámetros
	public ProgramacionBean()
	{
		
	}
	
	@PostConstruct
	public void inicializando()
	{	
		this.listaProgramacion = new ArrayList<ProgramacionDTO>();
		this.listaTipoPedido = new ArrayList<TipoDTO>();
		this.listaTipoPeriodo=new ArrayList<TipoDTO>();
		this.programacionDTO = new ProgramacionDTO();
		this.programacionDTO.setEstado(new EstadoDTO());
		this.programacionDTO.setTipoPedido(new TipoDTO());
		this.programacionDTO.setTipoPeriodo(new TipoDTO());		
		this.anio =null;
		this.aprobacion=null;
		this.estado = null;
		this.tipoPedido =null;
		this.tipoPeriodo=null;
		this.paraCrear=false;
		this.paraVerDetalles=false;
		this.paraModificar =false;
		this.usuarioSesion="USUARIO";
		this.posicion = null;
		this.seleccionProducto=false;
		this.areaDTO=new AreaDTO();		
		generarListaTipoPedido();
		generarListaTipoPeriodo();
		generarListaAprobacion();
		generarListaEstado();
		generarListaAnios();
		buscarProgramacion();	
		inicializarPedidoPeriodo();
		enCambioPedido();
		generarListaAreas();	
		//PRUEBA

	}
	
	
	public void buscarProgramacion()
	{
		try 
		{
			this.listaProgramacion=new ArrayList<ProgramacionDTO>();
			if(
			   (anio ==null || anio==0) &&
			   (aprobacion ==null || aprobacion ==0)&&
			   (estado ==null || estado ==0) &&
			   (tipoPedido==null || tipoPedido ==0) &&
			   (tipoPeriodo==null || tipoPeriodo ==0)
			  )
			{
				List<ProgramacionDTO>listaReal = programacionServiceLocal.buscarProgramacion(anio, aprobacion, estado, tipoPedido, tipoPeriodo);
				if(listaReal!=null & listaReal.size()>=0)
				{
					for(ProgramacionDTO programacionDTO:listaReal)
					{
						if(programacionDTO.getIndicadorActivo()==ConstanteWeb.INDICADRO_ACTIVO.intValue())
						{
							listaProgramacion.add(programacionDTO);
						}else
						{
							
						}
						
					}
				}
			}else
			{
				
					listaProgramacion = programacionServiceLocal.buscarProgramacion(anio, aprobacion, estado, tipoPedido, tipoPeriodo);
				
			}
		} catch (Exception e) 
		{
		
		}
	}
	
	public void generarListaEstado() 
	{
		listaEstado = new ArrayList<>();
		listaEstado.add(new SelectItem(null, ConstanteWeb.CAMPO_SELECCIONE));
		listaEstado.add(new SelectItem(ConstanteWeb.VALOR_REGISTRADO,
				ConstanteWeb.CAMPO_REGISTRADO));		
	}
	
	public void generarListaAnios() 
	{
		listaAnios = new ArrayList<>();
		listaAnios.add(new SelectItem(null, ConstanteWeb.CAMPO_SELECCIONE));
		listaAnios.add(new SelectItem((Integer)2016,"2016"));
		listaAnios.add(new SelectItem((Integer)2017,"2017"));
		listaAnios.add(new SelectItem((Integer)2018,"2018"));
		listaAnios.add(new SelectItem((Integer)2019,"2019"));
		listaAnios.add(new SelectItem((Integer)2020,"2020"));
		listaAnios.add(new SelectItem((Integer)2021,"2021"));
	}
	
	public void generarListaAprobacion()
	{
		listaAprobacion = new ArrayList<>();
		listaAprobacion.add(new SelectItem(null, ConstanteWeb.CAMPO_SELECCIONE));
		listaAprobacion.add(new SelectItem((Integer)1,"Aprobado"));		
	}
	
	public void generarListaTipoPeriodo()
	{
		try 
		{
			List<TipoDTO>listaReal;
			this.listaTipoPeriodo = new ArrayList<TipoDTO>();
			//En la BD el id del tipo Familia es 24 :D, el id del estado registrado es 1.
			Integer idTipoPeriodo = 24;
			Integer idEstadoActivo=1;
			listaReal = tipoServicioLocal.buscarTipo(idTipoPeriodo, idEstadoActivo); 
			if(listaReal !=null && listaReal.size()>=0)
			{
				for (TipoDTO tipoDTO : listaReal) 
				{
				 this.listaTipoPeriodo.add(tipoDTO);
				}
			}else
			{
				System.err.println("Erro, al parecer no existen objetos en la BD.");
			}
		} catch (Exception e) 
		{
			System.out.println("Error "+e.getMessage());
		}
	}
	
	public void generarListaTipoPedido()
	{
		try 
		{
			List<TipoDTO>listaReal;
			this.listaTipoPedido = new ArrayList<TipoDTO>();
			//En la BD el id del tipo Familia es 24 :D, el id del estado registrado es 1.
			Integer idTipoPedido=10;
			Integer idEstadoActivo=1;
			listaReal = tipoServicioLocal.buscarTipo(idTipoPedido,idEstadoActivo);
			if(listaReal !=null && listaReal.size()>=0)
			{
				for (TipoDTO tipoDTO : listaReal) 
				{
					this.listaTipoPedido.add(tipoDTO);
				}
			}else
			{
				System.out.println("Error, al parecer no existe objetos en la BD.");
			}
			
		} catch (Exception e) 
		{
			System.out.println("Error "+e.getMessage());
		}
	}
	
	public void limpiarProgramacion()
	{
		this.aprobacion=null;
		this.anio=null;
		this.tipoPedido=null;
		this.tipoPeriodo=null;
		this.estado=null;
		this.listaProgramacion = new ArrayList<ProgramacionDTO>();
	}
	
	public void abrirPopupNuevaProgramacion()
	{		
		this.programacionDTO = new ProgramacionDTO();
		this.programacionDTO.setEstado(new EstadoDTO());
		this.programacionDTO.setTipoPedido(new TipoDTO());
		this.programacionDTO.setTipoPeriodo(new TipoDTO());
		this.paraCrear = true;		
		this.paraVerDetalles=false;
		this.paraModificar=false;
		context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogoNuevoProgramacion').show();"); 
	}
		
	public void abriPopupAgregarAreaProducto()
	{		
		context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogoAreaProducto').show();");
		//PRUEBA FILAS Y COLUMNAS DINAMICAS
		generarFilasColumnasDinámicas();	
		this.cantidadAreaProducto = new int [this.rowNames.size()][this.colNames.size()];
	}	
	
	
	public void inicializarPedidoPeriodo()
	{
		pedidos = new HashMap<Integer,String>();
		// GENERAR LISTA PARA TIPO DE PEDIDO --> SUMINISTRO
		Integer idSuministro =7;
		Integer idComestible =8;
		Integer idExtraordinario =9;
		Integer idMensual = 25;
		Integer idTrimestral=26;
		generarListaTipoPedido();
		for (TipoDTO objeto: this.listaTipoPedido) 
		{
			//SUMINISTRO --> ID=7 EN LA BD.
			//COMESTIBLE --> ID=8 EN LA BD.
			//EXTRAORDINARIO -->ID=9 EN LA BD.
			if(objeto.getId() == idSuministro || objeto.getId() == idComestible || objeto.getId() == idExtraordinario)
			{
				pedidos.put(objeto.getId(), objeto.getDescripcionTipo());	
			}

				
		}
		//GENERAR LISTA PARA TIPO DE PEDIDO --> SUMINISTRO
		Map<Integer,String> map = new HashMap<Integer,String>();
		generarListaTipoPeriodo();
		for (TipoDTO objeto : this.listaTipoPeriodo) 
		{
			//PERIODO PARA EL REQ. SUMINISTRO ES MENSUAL.
			//PERIODO -->ID=25 EN LA BD			
			if(objeto.getId() == idMensual)
			{
				map.put(objeto.getId(), objeto.getDescripcionTipo());
			}
		}
		data1.put(idSuministro, map);
	
		// GENERAR LISTA PARA TIPO DE PEDIDO --> COMESTIBLE	
		map=new HashMap<Integer,String>();
		for (TipoDTO objeto: this.listaTipoPeriodo) 
		{
			//PERIODO PARA EL REQ. COMESTIBLE  ES TRIMESTRAL.
			//PERIODO -->ID=26 EN LA BD			
			if(objeto.getId() == idTrimestral)
			{
				map.put(objeto.getId(), objeto.getDescripcionTipo());
			}			
		}
		data1.put(idComestible, map);
		
		
		//GENERAR LISTA PARA TIPO DE PEDIDO -->EXTRAORDINARIO
		map=new HashMap<Integer,String>();
		for (TipoDTO objeto : this.listaTipoPeriodo) 
		{
			//PERIODO PARA EL REQ. EXTRAORDINARIO ES MENSUAL .
			//PERIODO -->ID=25 , 
			if (objeto.getId()==idMensual) 
			{
				map.put(objeto.getId(), objeto.getDescripcionTipo());
			}
		}
		data1.put(idExtraordinario, map);
	}
	
	public String obtenerFechaActual()
	{
		String fechaActual = Utilitarios.getFechaSistema();
		return fechaActual;
	}
	
	public void obtenerDuracionMeses()
	{
		switch(this.programacionDTO.getTipoPedido().getId())
		{
		case 8:this.programacionDTO.setMesesDuracionPeriodo(3);break;
		case 7:this.programacionDTO.setMesesDuracionPeriodo(1);break;
		case 9:this.programacionDTO.setMesesDuracionPeriodo(1);break;
		default:this.programacionDTO.setMesesDuracionPeriodo(0);break;
		}		
	}
	
	public void enCambioPedido()
	{
		if(this.programacionDTO.getTipoPedido().getId()!=null && this.programacionDTO.getTipoPedido().getId()>= 0)
		{
			periodos=data1.get(this.programacionDTO.getTipoPedido().getId());
		}else
		{
			periodos=new HashMap<Integer,String>();
		}
	}
	
	public void registrarProgramacion()
	{
		try 
		{
			programacionDTO.setCodigoUsuarioRegistro(usuarioSesion);
			programacionDTO.setIpRegistro(obtieneIPCliente());			
			programacionDTO = programacionServiceLocal.registrarProgramacion(programacionDTO);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Aviso",
					"Se registro programación con éxito"));
			cerrarPopupProgramacion();
			cerrarMensajeConfirmacion();
			listaProgramacion.add(programacionDTO);
			limpiarProgramacion();		
		} catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		}
	}
	public void mensajeConfirmacion() 
	{
		context = RequestContext.getCurrentInstance();
		context.execute("PF('popupConfirmar').show();");
	}
	
	public void cerrarPopupProgramacion() 
	{
		context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogoNuevoProgramacion').hide();");
	}
	
	public void cerrarMensajeConfirmacion() 
	{
		context = RequestContext.getCurrentInstance();
		context.execute("PF('popupConfirmar').hide();");
	}
	
	public void abrirPopupVerDetalles(Integer posicion)
	{
		obtenerDatosProgramacion(posicion);
		this.posicion=posicion;
		context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogoNuevoProgramacion').show();");
	}
	
	public void generarDetalleProgramacion(Integer posicion)
	{
		this.paraCrear=false;
		this.paraVerDetalles=true;
		this.paraModificar=false;
		abrirPopupVerDetalles(posicion);
	}
	
	public void obtenerDatosProgramacion(Integer posicion)
	{
		ProgramacionDTO objeto = listaProgramacion.get(posicion.intValue());
		this.programacionDTO = new ProgramacionDTO();
		this.programacionDTO.setId(objeto.getId());
		this.programacionDTO.setDescripcionProgramacion(objeto.getDescripcionProgramacion());
		this.programacionDTO.setTipoPedido(new TipoDTO());
		this.programacionDTO.getTipoPedido().setId(objeto.getTipoPedido().getId());
		this.programacionDTO.setTipoPeriodo(new TipoDTO());
		this.programacionDTO.getTipoPeriodo().setId(objeto.getTipoPeriodo().getId());
		this.programacionDTO.setFechaInicioRegistro(objeto.getFechaInicioRegistro());
		this.programacionDTO.setFechaFinRegistro(objeto.getFechaFinRegistro());
		this.programacionDTO.setIndicadorAprobacion(objeto.getIndicadorAprobacion());
		this.programacionDTO.setIndicadorActivo(objeto.getIndicadorActivo());
		this.programacionDTO.setCodigoUsuarioRegistro(objeto.getCodigoUsuarioRegistro());
		this.programacionDTO.setIpRegistro(objeto.getIpRegistro());
		this.programacionDTO.setFechaRegistro(objeto.getFechaRegistro());
		this.programacionDTO.setCodigoUsuarioModificacion(objeto.getCodigoUsuarioModificacion());
		this.programacionDTO.setIpModificacion(objeto.getIpModificacion());
		this.programacionDTO.setFechaModificacion(objeto.getFechaModificacion());
		this.programacionDTO.setEstado(new EstadoDTO());
		this.programacionDTO.getEstado().setId(objeto.getEstado().getId());
		this.programacionDTO.setMesesDuracionPeriodo(objeto.getMesesDuracionPeriodo());
		this.enCambioPedido();
	}
	
	
	public void generarModificacionProgramacion(Integer posicion)
	{
		this.paraVerDetalles=false;
		this.paraModificar=true;
		this.paraCrear=false;
		this.seleccionAreas = new String[this.listaArea.size()];
		abrirPopupVerDetalles(posicion);
	}
	
	public void actualizarProgramacion()
	{
		try 
		{
		this.programacionDTO.setCodigoUsuarioModificacion(this.usuarioSesion);
		this.programacionDTO.setIpModificacion(this.obtieneIPCliente());
		Boolean seActualizo = programacionServiceLocal.actualizarProgramacion(programacionDTO);
		
		if(seActualizo == true)
		{
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Aviso",
					"Se actualizó programación con éxito"));
			cerrarPopupProgramacion();
			cerrarMensajeConfirmacion();				
			listaProgramacion.set(posicion,programacionDTO);
			limpiarProgramacion();
		}
		
		} catch (Exception e) 
		{
			log.error(e.getMessage(),e);
		}
		
	}
	
	
	public void generarListaAreas()
	{
		try 
		{
			List<AreaDTO>listaReal;			
			this.listaArea = new ArrayList<AreaDTO>();
			//Para que nos retorne una lista con todos los objetos , pasamos parámetros nulos.
			listaReal = this.areaServiceLocal.buscarArea(null, null);
			if(listaReal!=null && listaReal.size()>=0)
			{
				for (AreaDTO areaDTO : listaReal) 
				{
					if(areaDTO.getAreaPadre().getId()==null || areaDTO.getAreaPadre().getId()==0)
					{
						this.listaArea.add(areaDTO);
					}else
					{
						
					}
				}
			}
											
		} catch (Exception e) 
		{
			System.out.println("Error "+e.getMessage());
		}
	}
	
	//----------------------------------------------------------
	//PRUEBA PARA AGREGAR LOS PRODUCTOS COMO CABECERA DE COLUMNA
	//----------------------------------------------------------
	
	List<ProductoDTO>productosSeleccionados = new ArrayList<>();	
	
	public void obtenerProductoSeleccionado(Integer posicion,Boolean seleccionProducto)
	{
    	if(posicion!=null && posicion>=0)
		{    	
    		this.productoBean.obtenerDatosProducto(posicion);			
    		this.productoDTO = this.productoBean.getProductoDTO();
    		if(seleccionProducto == true)
			{		    			
				this.productosSeleccionados.add(this.productoDTO);				
			}
    		else    			
			{       			
    			//Se tiene que recorrer toda la lista y ene el caso que cumpla la condicion, si queremos eliminar
    			//el objeto tenemo que indicar el nombre del objeto el cual está recorriendo el array, no al que 
    			//sirve para para comparar.
    			for (ProductoDTO objeto: this.productosSeleccionados)     			
    			{
    				if(objeto.getId() == this.productoDTO.getId())
    				{    					
    					this.productosSeleccionados.remove(objeto);    					
    				}
    				    				
				}
    							
			}		
		}
    	else
		{		
		}		
    	this.seleccionProducto = false;	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//Métodos de acceso.
	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public ProgramacionServiceLocal getProgramacionServiceLocal() {
		return programacionServiceLocal;
	}

	public void setProgramacionServiceLocal(
			ProgramacionServiceLocal programacionServiceLocal) {
		this.programacionServiceLocal = programacionServiceLocal;
	}

	public RequestContext getContext() {
		return context;
	}

	public void setContext(RequestContext context) {
		this.context = context;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public Integer getAprobacion() {
		return aprobacion;
	}

	public void setAprobacion(Integer aprobacion) {
		this.aprobacion = aprobacion;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Integer getTipoPedido() {
		return tipoPedido;
	}

	public void setTipoPedido(Integer tipoPedido) {
		this.tipoPedido = tipoPedido;
	}

	public Integer getTipoPeriodo() {
		return tipoPeriodo;
	}

	public void setTipoPeriodo(Integer tipoPeriodo) {
		this.tipoPeriodo = tipoPeriodo;
	}

	public List<ProgramacionDTO> getListaProgramacion() {
		return listaProgramacion;
	}

	public void setListaProgramacion(List<ProgramacionDTO> listaProgramacion) {
		this.listaProgramacion = listaProgramacion;
	}

	public ProgramacionDTO getProgramacionDTO() {
		return programacionDTO;
	}

	public void setProgramacionDTO(ProgramacionDTO programacionDTO) {
		this.programacionDTO = programacionDTO;
	}

	public TipoServiceLocal getTipoServicioLocal() {
		return tipoServicioLocal;
	}

	public void setTipoServicioLocal(TipoServiceLocal tipoServicioLocal) {
		this.tipoServicioLocal = tipoServicioLocal;
	}

	public List<SelectItem> getListaEstado() {
		return listaEstado;
	}

	public void setListaEstado(List<SelectItem> listaEstado) {
		this.listaEstado = listaEstado;
	}

	public List<TipoDTO> getListaTipoPeriodo() {
		return listaTipoPeriodo;
	}

	public void setListaTipoPeriodo(List<TipoDTO> listaTipoPeriodo) {
		this.listaTipoPeriodo = listaTipoPeriodo;
	}

	public List<TipoDTO> getListaTipoPedido() {
		return listaTipoPedido;
	}

	public void setListaTipoPedido(List<TipoDTO> listaTipoPedido) {
		this.listaTipoPedido = listaTipoPedido;
	}

	public List<SelectItem> getListaAnios() {
		return listaAnios;
	}

	public void setListaAnios(List<SelectItem> listaAnios) {
		this.listaAnios = listaAnios;
	}

	public List<SelectItem> getListaAprobacion() {
		return listaAprobacion;
	}

	public void setListaAprobacion(List<SelectItem> listaAprobacion) {
		this.listaAprobacion = listaAprobacion;
	}

	public Map<Integer, Map<Integer, String>> getData1() {
		return data1;
	}

	public void setData1(Map<Integer, Map<Integer, String>> data1) {
		this.data1 = data1;
	}

	public Map<Integer, String> getPedidos() {
		return pedidos;
	}

	public void setPedidos(Map<Integer, String> pedidos) {
		this.pedidos = pedidos;
	}

	public Map<Integer, String> getPeriodos() {
		return periodos;
	}

	public void setPeriodos(Map<Integer, String> periodos) {
		this.periodos = periodos;
	}

	public Utilitarios getUtilitario() {
		return utilitario;
	}

	public void setUtilitario(Utilitarios utilitario) {
		this.utilitario = utilitario;
	}

	
	public String getUsuarioSesion() {
		return usuarioSesion;
	}

	public void setUsuarioSesion(String usuarioSesion) {
		this.usuarioSesion = usuarioSesion;
	}

	public Boolean getParaCrear() {
		return paraCrear;
	}

	public void setParaCrear(Boolean paraCrear) {
		this.paraCrear = paraCrear;
	}

	public Boolean getParaVerDetalles() {
		return paraVerDetalles;
	}

	public void setParaVerDetalles(Boolean paraVerDetalles) {
		this.paraVerDetalles = paraVerDetalles;
	}

	public Integer getPosicion() {
		return posicion;
	}

	public void setPosicion(Integer posicion) {
		this.posicion = posicion;
	}

	public Boolean getParaModificar() {
		return paraModificar;
	}

	public void setParaModificar(Boolean paraModificar) {
		this.paraModificar = paraModificar;
	}

	public AreaServiceLocal getAreaServiceLocal() {
		return areaServiceLocal;
	}

	public void setAreaServiceLocal(AreaServiceLocal areaServiceLocal) {
		this.areaServiceLocal = areaServiceLocal;
	}

	public List<AreaDTO> getListaArea() {
		return listaArea;
	}

	public void setListaArea(List<AreaDTO> listaArea) {
		this.listaArea = listaArea;
	}

	public String[] getSeleccionAreas() {
		return seleccionAreas;
	}

	public void setSeleccionAreas(String[] seleccionAreas) {
		this.seleccionAreas = seleccionAreas;
	}
	
	public Integer getCantidadProducto() {
		return cantidadProducto;
	}

	public void setCantidadProducto(Integer cantidadProducto) {
		this.cantidadProducto = cantidadProducto;
	}

	public AreaDTO getAreaDTO() {
		return areaDTO;
	}

	public void setAreaDTO(AreaDTO areaDTO) {
		this.areaDTO = areaDTO;
	}
	
	
	//PRUEBA DE FILAS Y COLUMNAS DINÁMICAS
	private List<String> rowNames = new ArrayList<String>();
    private List<String> colNames = new ArrayList<String>();

    public void generarFilasColumnasDinámicas()
    {    	
    	for(String objeto:this.seleccionAreas)
    	{        		  
    		for(AreaDTO areaDTO:listaArea)
    		{
    			if(Integer.valueOf(objeto) == areaDTO.getId())
    			{
    				this.rowNames.add(areaDTO.getDescripcionArea());
    			}
    		}
    		
    	}
    	for (ProductoDTO objeto: this.productosSeleccionados) 
    	{
			this.colNames.add(objeto.getDescripcionProducto());
		}    
    }
    
    //Método que sirve para inicializar las cabeceras de las filas y columnas dinámicas, la selección de los productos
    //y las áreas.
    public void resetSeleccionProducto()
    {    	
    	this.seleccionProducto=false;   
    	this.seleccionAreas = new String[this.listaArea.size()];
    	this.productosSeleccionados = new ArrayList<>();
    	this.rowNames = new ArrayList<>();
    	this.colNames = new ArrayList<>();
    }
    
	public List<String> getRowNames() 
	{
		return rowNames;
	}

	public void setRowNames(List<String> rowNames) 
	{
		this.rowNames = rowNames;
	}

	public List<String> getColNames() 
	{
		return colNames;
	}

	public void setColNames(List<String> colNames) 
	{
		this.colNames = colNames;
	}

	public ProductoBean getProductoBean() {
		return productoBean;
	}

	public void setProductoBean(ProductoBean productoBean) {
		this.productoBean = productoBean;
	}

	public ProductoDTO getProductoDTO() {
		return productoDTO;
	}

	public void setProductoDTO(ProductoDTO productoDTO) {
		this.productoDTO = productoDTO;
	}

	public List<ProductoDTO> getProductosSeleccionados() {
		return productosSeleccionados;
	}

	public void setProductosSeleccionados(List<ProductoDTO> productosSeleccionados) {
		this.productosSeleccionados = productosSeleccionados;
	}

	public Boolean getSeleccionProducto() {
		return seleccionProducto;
	}

	public void setSeleccionProducto(Boolean seleccionProducto) {
		this.seleccionProducto = seleccionProducto;
	}
    
    
//  PRUEBA EN TABLA DE DOBLE ENTRADA : VALUE SPINNER
	private int[][] cantidadAreaProducto;
	private Integer valorAreaProducto;
	
	public void obtenerValoresAreaProducto(Integer row,Integer col,int valor)
	{
		for(int i=row;i<=row;i++)
		{
			for(int j=col;j<=col;j++)
			{
				if(i==row && j==col)
				{
					cantidadAreaProducto[i][j]=valor;	
				}else
				{
					if(cantidadAreaProducto[i][j]==0)
					{
						cantidadAreaProducto[i][j]=0;
					}
				}
				
			}
		}
		this.valorAreaProducto = null;
	}
	
	
	
	
	
	



	public int[][] getCantidadAreaProducto() {
		return cantidadAreaProducto;
	}

	public void setCantidadAreaProducto(int[][] cantidadAreaProducto) {
		this.cantidadAreaProducto = cantidadAreaProducto;
	}

	public Integer getValorAreaProducto() {
		return valorAreaProducto;
	}

	public void setValorAreaProducto(Integer valorAreaProducto) {
		this.valorAreaProducto = valorAreaProducto;
	}
	
	
	
	
	public void prueba()
	{
		System.out.println("Prueba");
	}
    
    
}
