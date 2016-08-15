package pe.gob.servir.gpa.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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
import pe.gob.servir.gpa.model.dto.DetalleProgramacionAreaProductoDTO;
import pe.gob.servir.gpa.model.dto.EstadoDTO;
import pe.gob.servir.gpa.model.dto.ProductoDTO;
import pe.gob.servir.gpa.model.dto.ProgramacionAreasDTO;
import pe.gob.servir.gpa.model.dto.ProgramacionDTO;
import pe.gob.servir.gpa.model.dto.TipoDTO;
import pe.gob.servir.gpa.service.ejb.AreaServiceLocal;
import pe.gob.servir.gpa.service.ejb.DetalleProgramacionAreaProductoServiceLocal;
import pe.gob.servir.gpa.service.ejb.ProgramacionAreasServiceLocal;
import pe.gob.servir.gpa.service.ejb.ProgramacionServiceLocal;
import pe.gob.servir.gpa.service.ejb.TipoServiceLocal;
import pe.gob.servir.gpa.util.ConstanteWeb;
import pe.gob.servir.gpa.util.UBaseManage;
import pe.gob.servir.gpa.util.Utilitarios;
@ManagedBean(name="programacionBean")
@ViewScoped
public class ProgramacionBean extends UBaseManage implements Serializable
{
	private static final long serialVersionUID = 1937070803672180760L;

	private Logger log = Logger.getLogger(ProgramacionBean.class);
	
	@EJB
	private ProgramacionServiceLocal programacionServiceLocal;
	@EJB
	private TipoServiceLocal tipoServicioLocal;
	@EJB
	private AreaServiceLocal areaServiceLocal;
	@EJB
	private ProgramacionAreasServiceLocal programacionAreasServiceLocal;
	@EJB
	private DetalleProgramacionAreaProductoServiceLocal detalleProgramacionAreaProductoServiceLocal;
	
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
	private String[]seleccionAreas;
	private List<AreaDTO>listaArea;
	List<ProductoDTO>productosSeleccionados = new ArrayList<>();
	@ManagedProperty("#{productoBean}")
	private ProductoBean productoBean;
	private ProductoDTO productoDTO;	
	private Integer valorAreaProducto;
	private ArrayList<ArrayList<ArrayList<Integer>>> listaValores = new ArrayList<ArrayList<ArrayList<Integer>>>();
	private List<String> rowNames = new ArrayList<String>();
    private List<String> colNames = new ArrayList<String>();
    private boolean verAreaProductos;	
    @ManagedProperty("#{programacionAreasBean}")
    private ProgramacionAreasBean programacionAreasBean;
    @ManagedProperty("#{detalleProgramacionAreaProductoBean}")
    private DetalleProgramacionAreaProductoBean detalleProgramacionAreaProductoBean; 
    private Integer[] listaProgramacionAreasId; //Lista de los Id de prograacionesArea.
    private Boolean[]seleccionProductoArray;//Lista para almacenar el valor booleano del checkbox de cada producto de la tabla, dependiendo su posicion.
	List<DetalleProgramacionAreaProductoDTO>listaDetalleProgramacionAreaProductoXFiltro;
	private ProgramacionAreasDTO programacionAreasDTO;
	private DetalleProgramacionAreaProductoDTO detalleProgramacionAreasProductoDTO;
	private List<ProgramacionAreasDTO>listaProgramacionAreasXFiltro;
	List<AreaDTO>listaAreasParaCalcularCantidad;
	List<ProductoDTO>listaProductosParaCalcularCantidad;	
	List<ProgramacionDTO> listaProgramacionEnviadaAlEncargadoDeAlmacen;
	
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
		this.verAreaProductos = false;		
		this.listaProgramacionEnviadaAlEncargadoDeAlmacen = new ArrayList<>();
		generarListaTipoPedido();
		generarListaTipoPeriodo();
		generarListaAprobacion();
		generarListaEstado();
		generarListaAnios();
		buscarProgramacion();
		inicializarPedidoPeriodo();
		enCambioPedido();
		generarListaAreas();	
		cargarListaProgramacionEnviadaAlEncargadoDeAlmacen();

	}
	
	//Método que retorna todas las programaciones almacenadas en la BD
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
				//Prueba
				setearANuloVariablesDeFormulario();
			}else
			{
				
					listaProgramacion = programacionServiceLocal.buscarProgramacion(anio, aprobacion, estado, tipoPedido, tipoPeriodo);
					//Prueba
					setearANuloVariablesDeFormulario();
			}

			
		} catch (Exception e) 
		{
		
		}
	}
	
	//Prueba para setear las variables de formulario a nulo.
	public void setearANuloVariablesDeFormulario()
	{
		   this.anio =null;
		   this.aprobacion =null;
		   this.estado=null;
		   this.tipoPedido =null;
		   this.tipoPeriodo= null; 
	}
	
	
	public void generarListaEstado() 
	{
		listaEstado = new ArrayList<>();
		listaEstado.add(new SelectItem(null, ConstanteWeb.CAMPO_SELECCIONE));
		listaEstado.add(new SelectItem(ConstanteWeb.VALOR_REGISTRADO,
				ConstanteWeb.CAMPO_REGISTRADO));
		listaEstado.add(new SelectItem(ConstanteWeb.VALOR_PROGR_ENVIADO ,
				ConstanteWeb.CAMPO_PROGR_ENVIADO));		
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
		this.listaProgramacionEnviadaAlEncargadoDeAlmacen = new ArrayList<>();
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
		this.verAreaProductos = false;		
		context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogoNuevoProgramacion').show();"); 
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
	
	public void generarDetalleProgramacion(Integer posicion)
	{
		this.paraCrear=false;
		this.paraVerDetalles=true;
		this.paraModificar=false;
		this.verAreaProductos=true;
		abrirPopupVerDetalles(posicion);
	}
	
	
	public void abrirPopupVerDetalles(Integer posicion)
	{
		obtenerDatosProgramacion(posicion);
		//PRUEBA para cargar las áreas en un arreglo con sus Ids.
		this.cargarSeleccionAreas();
		//Prueba inicializar seleccionProductoArray
		this.inicializarSeleccionProductoArray();
		//PRUEBA para cargar los productos seleccionados.
		this.cargarSeleccionProductos();
		this.posicion=posicion;
		context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogoNuevoProgramacion').show();");
	}
	
	//Método que permite visualizar las áreas seleccionadas que han sido almacenadas en la BD.
	public void cargarSeleccionAreas()
	{
		this.programacionAreasBean.setProgramacionId(this.programacionDTO.getId());
		//Seteamos null para traer todas las áreas
		this.programacionAreasBean.setAreaId(null);
		this.programacionAreasBean.buscarProgramacionAreas();
		int contador=0;
		for(ProgramacionAreasDTO objeto:this.programacionAreasBean.getListaProgramacionAreas())
		{			
			for(int i=contador;i<this.listaArea.size();i++)
			{					
				if(objeto.getArea().getId() !=null )
				{
					this.seleccionAreas[i]=String.valueOf(objeto.getArea().getId());
					break;
				}				
			}
			contador=contador+1;
		}
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
		this.verAreaProductos = true;
		
	}
	
	//Método que permite actualizar los datos en la BD , desde el bean con las propiedades seteadas.
	public void actualizarProgramacion()
	{
		try 
		{
		this.programacionDTO.setCodigoUsuarioModificacion(this.usuarioSesion);
		this.programacionDTO.setIpModificacion(this.obtieneIPCliente());
		Boolean seActualizoProgramacion = programacionServiceLocal.actualizarProgramacion(this.programacionDTO);
		Boolean seActualizoProgramacionAreas = programacionAreasServiceLocal.actualizarProgramacionAreas(this.programacionAreasDTO);
		Boolean seActualizoDetalleProgAreaProducto = detalleProgramacionAreaProductoServiceLocal.actualizarDetalleProgramacionAreaProducto(this.detalleProgramacionAreasProductoDTO);
		Boolean seActualizoDetalle = this.actualizarDetalle();
		if(seActualizoProgramacion == true && seActualizoDetalle)
		{
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Aviso",
					"Se actualizó programación con éxito"));
			cerrarPopupProgramacion();
			cerrarMensajeConfirmacion();				
			listaProgramacion.set(posicion,programacionDTO);
			limpiarProgramacion();
		}
		else
		{
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Error",
					"Error al actualizar Detalle"));
		}
		
		} catch (Exception e) 
		{
			log.error(e.getMessage(),e);
		}
		
	}
	
	
	//Método que sirve para obtener descripción de las áreas y agregarlas a la lista listaAreas.
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

	
	//Método que nos muestra un popup con una tabla dinámica entre las áreas y productos, seleccionados.
	public void abriPopupAgregarAreaProducto()
	{		
		//Prueba para obtener las filas y columnas en caso no existan registros en la bd de programacionesAreasProducto
		obtenerProductoSeleccionadoAlAbrirPopup();
		context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogoAreaProducto').show();");
		generarFilasColumnasDinámicas();		
		obtenerValoresTablaDinamica();	
		cargarValoresTablaDesdeBD();
		setearCantidadAListaValores();

	}
	
	//Método que nos permite obtener las cabeceras de las filas y las columnas de la tabla dinámica área/producto.    
	public void generarFilasColumnasDinámicas()
	{    	
		this.listaAreasParaCalcularCantidad = new ArrayList<>();
		this.listaProductosParaCalcularCantidad = new ArrayList<>();
		//Prueba para ordenar la listaArea
		this.ordenarListaAreaXId(this.listaArea);
		//Prueba para ordenar la lista productosSeleccionados
		this.ordenarListaProductosXId(this.productosSeleccionados);
		for(String objeto:this.seleccionAreas)
	    {        		  
			for(AreaDTO areaDTO:listaArea)
	    	{
				if(Integer.valueOf(objeto) == areaDTO.getId())
	    		{
					//En esta parte agregamos la descripcion de las áreas que son seleccioanadas,entonces
					//también podemos almacenar el código.
					this.rowNames.add(areaDTO.getDescripcionArea());
					//Prueba -- Creamos una lista para almacenar las áreas que han sido seleccionadas.
					this.listaAreasParaCalcularCantidad.add(areaDTO);
	    		}
	    	}	    	
	    }		
	    for (ProductoDTO objeto: this.productosSeleccionados) 
	    {
	    	//En esta parte almacenamos la descripción de los productos seleccionados,entonces también podemos 
	    	//almacenar su código.
			this.colNames.add(objeto.getDescripcionProducto());
			//Prueba -- Creamos una lista para almacenar los productos seleccionados,para tenerlos en orden
			this.listaProductosParaCalcularCantidad.add(objeto);
		}    
	    
	    //Una vez ya seteados las filas y columnas en la tabla, queremos obtener las programacionesAreas pasándole los parámetros programacionID
	    //y el areaId que ya ha sido cargado en los bucles for.
	    this.obtenerProgramacionAreasXFiltror();
	}
	
	//PRUEBA
	//Ordenar áreas	
	@SuppressWarnings("unchecked")
	public List<AreaDTO> ordenarListaAreaXId(List<AreaDTO> listaOriginal)
	{
		Collections.sort(listaOriginal,new AreaDTO());
		//Pintar lista consola
		for (int i = 0; i < listaOriginal.size(); i++) 
		{
			System.out.println(listaOriginal.get(i));
		}
		return listaOriginal;
	}
	
	//PRUEBA
	//Ordenar lista de productos
	@SuppressWarnings("unchecked")
	public List<ProductoDTO>ordenarListaProductosXId(List<ProductoDTO> listaOriginal)
	{
		Collections.sort(listaOriginal,new ProductoDTO());
		//Pinta lista en consola
		for (int i = 0; i < listaOriginal.size(); i++) 
		{
			System.out.println(listaOriginal.get(i));
		}
		return listaOriginal;
	}
	
	
	
	//Una vez que tengamos los valores de las áreas y productos seleccionados para las filas y columnas respectivamente
	//Entonces recorremos esas intersecciones y pasaremos como parámetros la programaciónId y el áreaId para traer el id de 
	//la programacionAreasId
	public void obtenerProgramacionAreasXFiltror()
	{
		this.listaProgramacionAreasXFiltro = new ArrayList<ProgramacionAreasDTO>();
		for (AreaDTO fila : this.listaAreasParaCalcularCantidad) 
		{		
				this.resetearProgramacionAreasBeanProgramacionYAreaId();
				//Necesito el id de la programación en proceso.
				this.programacionAreasBean.setProgramacionId(this.programacionDTO.getId());
				this.programacionAreasBean.setAreaId(fila.getId());
				//Aquí seteamos a la listaProgramacionAreas los valores de la búsqueda
				this.programacionAreasBean.buscarProgramacionAreas();
				if(this.programacionAreasBean.getListaProgramacionAreas()!=null && this.programacionAreasBean.getListaProgramacionAreas().size()>0)
				{
					for(ProgramacionAreasDTO objeto : this.programacionAreasBean.getListaProgramacionAreas())
					{
						this.listaProgramacionAreasXFiltro.add(objeto);
					}
				}
				else
				{
					System.out.println("No tiene objetos cuando se asignan los filtros programacionId y areaID");
				}							
		}
	}
	
	
	//Método para limpiar los valores que están indicados en el método de abajo,de ProgramacionAreasBean.
	public void resetearProgramacionAreasBeanProgramacionYAreaId()
	{
		this.programacionAreasBean.setProgramacionId(null);
		this.programacionAreasBean.setAreaId(null);
		this.programacionAreasBean.setListaProgramacionAreas(new ArrayList<ProgramacionAreasDTO>());
	}
	
	
	//Método que permite cargar las columnas con los nombres de los productos traidos desde la base de datos, desde ProgramacionAreaProducto
	public void obtenerProductoSeleccionadoAlAbrirPopup()
	{
		int contador=0;
		
			for(ProductoDTO objeto1:this.productoBean.getListaProducto())
			{
				if(this.seleccionProductoArray[contador]==true)
				{					
					System.out.println("Ya está registrado");
				}
				else
				{
					for (ProductoDTO objeto2: this.productosSeleccionados)     			
	    			{
	    				if(objeto2.getId() == objeto1.getId())
	    				{    					
	    					this.productosSeleccionados.remove(objeto2);    					
	    				}	    				    			
					}					
				}
				contador++;
			}		
	}	
	
	
	//Método que nos permite obtener los productos que va seleccionando el usuario.
	public void obtenerProductoSeleccionado(Integer posicion,Boolean seleccionProducto)
	{
    	if(posicion!=null && posicion>=0)
		{    	
    		productoDTO = new ProductoDTO();
    		this.productoBean.obtenerDatosProducto(posicion);			
    		productoDTO = this.productoBean.getProductoDTO();
    		if(seleccionProducto == true )
			{		    			
				this.productosSeleccionados.add(productoDTO);				
			}
    		else    			
			{       			
    			//Se tiene que recorrer toda la lista y ene el caso que cumpla la condicion, si queremos eliminar
    			//el objeto tenemo que indicar el nombre del objeto el cual está recorriendo el array, no al que 
    			//sirve para para comparar.
    			for (ProductoDTO objeto: this.productosSeleccionados)     			
    			{
    				if(objeto.getId() == productoDTO.getId())
    				{    					
    					this.productosSeleccionados.remove(objeto);    					
    				}
    				    				
				}
    							
			}		
		}
    	else
		{		
		}    
	}
	
		
	//Método que sirve para inicializar las cabeceras de las filas y columnas dinámicas, la selección de los productos
    //y las áreas.
    public void resetSeleccionProducto()
    {    	    
    	//Prueba resetear el productoDTO
    	//this.productoDTO = new ProductoDTO();
    	//Reseteamos el array a false 
    	this.resetearSeleccionProductoArray();
    	this.seleccionAreas = new String[this.listaArea.size()];
    	this.rowNames = new ArrayList<>();
    	this.colNames = new ArrayList<>();    	
    	this.listaValores = new ArrayList<ArrayList<ArrayList<Integer>>>();
    	this.valorAreaProducto = 0;
    	//Método que permite cargar las áreas seleccionadas luego de cerrar el popup posterior
    	this.cargarSeleccionAreas();
    	//Prueba para cargar los productos luego que se oculta el popup
    	this.cargarSeleccionProductos();
    	//this.cargarSeleccionAreas();
    	
    }
    
    //Método que permite ocultar el panel de áreas y la tabla de los productos disponibles,también llama al método 
    //resetSeleccionProducto
    public void resetSeleccionProductoAreaProducto()
    {    	
    	resetSeleccionProducto();
    	this.verAreaProductos=false;    
    }
	
    
    public void abrirPopupAgregarAreaProductoDetalles()
	{
    	//Instrucción para agregar la fila TOTAL a rowNames
		this.rowNames.add("TOTAL");
		setearValoresATotal();
		//prueba
    	context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogoAreaProductoDetalles').show();");
		resetearSumaCantidadXArea();
		calcularTotalXArea();
		//Prueba
		this.obtenerProgramacionAreasDTOYDetallesProgramacionAreaProductoDTO();
	}

    //Métodos para setear los valores a total
    public void setearValoresATotal()
    {
    	for(int i=0;i<this.rowNames.size();i++)
    	{
    		for(int j=0;j<this.colNames.size();j++)
    		{
    			if(i == this.rowNames.size()-1)
    			{
    				this.listaValores.get(i).get(j).add(0);	
    			}    		
    		}    		
    	}
    }
	

	
	//Método que permite obtener los valores de la tabla dinámica entre áreas y productos.	
	public void  obtenerValoresTablaDinamica()
	{
		for (int i = 0; i < this.rowNames.size()+1; i++) 
		{
            this.listaValores.add(new ArrayList<ArrayList<Integer>>());
            for (int j = 0; j < this.colNames.size()+1; j++) 
            {
                this.listaValores.get(i).add(new ArrayList<Integer>());
                this.listaValores.get(i).get(j).add(0);
            }
        }	
	}	
	
	 
	//Método que permite obtener los valores de las cantidades de productos pedidos por cada área en el caso que existan en la BD 
	//para setearlos a la tabla,
	//pasando como parámetro las programacionAreasId y programacionId
	private Integer valor[];
	public void cargarValoresTablaDesdeBD()
	{
		//Prueba pra obtener las áreas seleccionadas
		this.obtenerAreasSeleccionadasDTO();
		this.valor = new Integer[this.rowNames.size()*this.colNames.size()];
		//Limpiar los atributos:programacionAreaId,areaId
		this.resetearProgramacionAreasBeanProgramacionYAreaId();
		Integer contador = 0;
		for (AreaDTO area : this.listaAreasSeleccionadas) 
		{			
			this.programacionAreasBean.setProgramacionId(this.programacionDTO.getId());
			this.programacionAreasBean.setAreaId(area.getId());
			this.programacionAreasBean.buscarProgramacionAreas();
			for(ProgramacionAreasDTO programacionArea:this.programacionAreasBean.getListaProgramacionAreas())
			{		
				for(ProductoDTO producto:this.productosSeleccionados)
				{
					//Método para limpiar los atributos de DetalleProgramacionAreaProducto
					this.detalleProgramacionAreaProductoBean.resetearAtributosDetalleProgramacionAreaProductos();
					this.detalleProgramacionAreaProductoBean.setProgramacionAreasId(programacionArea.getId());
					//Asigno null para que me traiga todos los productos del detalle, pero siempre va ser 1 producto
					//porque 1 producto solo tiene 1 detalle
					this.detalleProgramacionAreaProductoBean.setProductoDisponibleId(producto.getId());
					this.detalleProgramacionAreaProductoBean.buscarProductosXArea();
					for(DetalleProgramacionAreaProductoDTO detalle:this.detalleProgramacionAreaProductoBean.getListaDetalleProgramacionAreaProducto())
					{
						valor[contador] = detalle.getStockDisponible();
						break;
					}
					contador++;
				}
				
			}
			
		}
		
	}
	
	//Método para setear los valores del arreglo valro[] hacia la lista List<>valores
	public void setearCantidadAListaValores()
	{
		Integer contador=0;
		for (int i = 0; i < this.rowNames.size(); i++) 
		{
            this.listaValores.add(new ArrayList<ArrayList<Integer>>());
            for (int j = 0; j < this.colNames.size(); j++) 
            {
                this.listaValores.get(i).add(new ArrayList<Integer>());
                this.listaValores.get(i).get(j).remove(0);
                this.listaValores.get(i).get(j).add(this.valor[contador]);
                contador++;
            }
        }	
	}
	

	//Método para cargar las áreas selccionadas en una lista de tipo AreaDTO
	List<AreaDTO>listaAreasSeleccionadas;
	public void obtenerAreasSeleccionadasDTO()
	{
		this.listaAreasSeleccionadas = new ArrayList<>();
		for (AreaDTO area : this.listaArea) 
		{			
			for(String idString:this.seleccionAreas)
			{
				if(area.getId() == Integer.parseInt(idString+""))
				{
					this.listaAreasSeleccionadas.add(area);
				}
				else
				{
					System.out.println("El área no se encuentra seleccionada");
				}				
			}
		}
	}
	
	
	//Método para limpiar la lista areaSeleccionadasDTO
	public void resetarAreasSeleccionadasDTO()
	{
		this.listaAreasSeleccionadas = new ArrayList<>();
	}
	
	//PRUEBA
	//Crear variable para obtener los datos de la tabla dinámica antes de obtener suma por columa y por fila
	private Integer[]arregloValoresTablaDinamica;
	
	
	//Método que permite calcular la suma de todos los productos que va consumir cada área. 
	public void calcularTotalXArea()
	{
		//-1 porque se ha agregado una fila extra y una columna extra.
		this.arregloValoresTablaDinamica = new Integer[(this.rowNames.size()-1) * this.colNames.size()];
		int contador=0;
		
		//PRUEBA
		//Método para capturar los valores de la tabla dinámica antes de sumar
		for (int i = 0; i < this.rowNames.size()-1; i++) 
		{
			for (int j = 0; j <this.colNames.size(); j++) 
			{
				this.arregloValoresTablaDinamica[contador] = Integer.parseInt(this.listaValores.get(i).get(j).get(0)+"");
				contador++;
			}
		}		
				
		for(int i=0;i<this.rowNames.size()-1;i++)
		{
			int suma=0;			
			for(int j=0;j<this.colNames.size()+1;j++)
			{
				suma = suma + Integer.parseInt(this.listaValores.get(i).get(j).get(0)+"");				
			}			
			this.listaValores.get(i).get(this.colNames.size()).remove(0);
			this.listaValores.get(i).get(this.colNames.size()).add(suma);		
		}
					
		
		for(int j=0;j<this.colNames.size();j++)
		{
			int suma=0;
			
			for(int i=0;i<this.rowNames.size()-1;i++)
			{				
			suma = suma +Integer.parseInt(this.listaValores.get(i).get(j).get(0)+"");
			}			
			this.listaValores.get(this.rowNames.size()-1).get(j).remove(1);					
			this.listaValores.get(this.rowNames.size()-1).get(j).remove(0);											
			this.listaValores.get(this.rowNames.size()-1).get(j).add(suma);		
		}
		calcularCantidadTotalAreaProductos();
	}
	
	//Método que permite obtener total entre area y productos.
	public void calcularCantidadTotalAreaProductos()
	{
			int total =0;
			for(int j=0;j<this.colNames.size();j++)
			{
			total = total +Integer.parseInt(this.listaValores.get(this.rowNames.size()-1).get(j).get(0)+"");
			}				
		this.listaValores.get(this.rowNames.size()-1).get(this.colNames.size()).remove(0);
		this.listaValores.get(this.rowNames.size()-1).get(this.colNames.size()).add(total);		
	}
	
	
	//Méotod para obtener una lista con todas los detalles de la programaciones area producto que se estan procesando.
	public void obtenerProgramacionAreasDTOYDetallesProgramacionAreaProductoDTO()
	{
		this.listaDetalleProgramacionAreaProductoXFiltro = new ArrayList<>();
		
		for(ProgramacionAreasDTO lista : this.listaProgramacionAreasXFiltro)
		{
			Integer i = this.listaProgramacionAreasXFiltro.indexOf(lista);
			for(ProductoDTO columna : this.listaProductosParaCalcularCantidad)
			{
				Integer j=this.listaProductosParaCalcularCantidad.indexOf(columna);
									
					this.detalleProgramacionAreaProductoBean.setProgramacionAreasId(lista.getId());
					this.detalleProgramacionAreaProductoBean.setProductoDisponibleId(columna.getId());
					this.detalleProgramacionAreaProductoBean.buscarProductosXArea();
					if(this.detalleProgramacionAreaProductoBean.getListaDetalleProgramacionAreaProducto()!=null && this.detalleProgramacionAreaProductoBean.getListaDetalleProgramacionAreaProducto().size()>0)
					{
						for(DetalleProgramacionAreaProductoDTO objeto : this.detalleProgramacionAreaProductoBean.getListaDetalleProgramacionAreaProducto())
						{
							objeto.setStockDisponible(Integer.parseInt(this.listaValores.get(i).get(j).get(0)+""));
							this.listaDetalleProgramacionAreaProductoXFiltro.add(objeto);
							break;
						}
					}
					else
					{
						System.out.println("No existe registro con los parámetros ingresados");
					}
					
				}
			}
		//Limpiar los atributos del bean DetalleProgramcionAreaProductoBean
		this.resetearDetalleProgramacionAreaProductoBean();

	}
	
		
	//Método para inicializar los atributos especificados de la clase DetalleProgramacionAreaProductoBean
	public void resetearDetalleProgramacionAreaProductoBean()
	{
		this.detalleProgramacionAreaProductoBean.setProgramacionAreasId(null);
		this.detalleProgramacionAreaProductoBean.setProductoDisponibleId(null);
		this.detalleProgramacionAreaProductoBean.setListaDetalleProgramacionAreaProducto(new ArrayList<DetalleProgramacionAreaProductoDTO>());
	}
	
	
	
	//Método que permite resetear la suma de las filas
	public void resetearSumaCantidadXArea()
	{		
		for(int i=0;i<this.rowNames.size();i++)
		{			
			for(int j=0;j<this.colNames.size()+1;j++)
			{				
			}			
			this.listaValores.get(i).get(this.colNames.size()).remove(0);
			this.listaValores.get(i).get(this.colNames.size()).add(0);		
		}	

	}
	
	//Método que permite eliminar la fila que tiene la cabecera TOTAL
	public void eliminarFilaTotal()
	{
		//Prueba para borrar la fila total
		this.rowNames.remove(this.rowNames.size()-1);
	}

	
	//Método para resetear los valores de la tabla dinámica areás x productos
	public void resetearValoresTablaDinamica()
	{
		for(int i=0;i<this.rowNames.size();i++)
		{			
			for(int j=0;j<this.colNames.size()+1;j++)
			{
				this.listaValores.get(i).get(j).remove(0);
				this.listaValores.get(i).get(j).add(0);
			}			
			
			
		}		
	}
	
	//Método que permite obtener los id de las areas seleccionadas que han sido almacenadas en la BD; 
	//con el objetivo de hacer una consulta para traer los id de los productos solicitado por cada área.
	public void obtenerListaProgramacionAreasId()
	{
		//Prueba incializar al lista de las programaciones areas id
		this.inicializarListaProgramacionAreaId();
		int contador=0;
		for(ProgramacionAreasDTO objeto:this.programacionAreasBean.getListaProgramacionAreas())
		{						
				if(objeto.getId()!=null && objeto.getId()>=0)
				{
					listaProgramacionAreasId[contador]=objeto.getId();
				}	
				contador++;
		}
		
	}
		
				
	//Inicializamos la listaProgramacionAreasId con la cantidad de objetos que existen en la lista seleccionAreas,debido 
	//que la cantidad de áreas seleccionadas es la misma cantidad de registro en la tabla ProgramacionAreas filtrando por areaId.
	public void inicializarListaProgramacionAreaId()
	{	
		//Prueba pasar cantidad de productos seleccionados
		this.listaProgramacionAreasId = new Integer[this.seleccionAreas.length];
		for(int i=0;i<this.seleccionAreas.length;i++)
		{
			this.listaProgramacionAreasId[i]=0;
		}
	}
	
	//Método que permite inicializar todos los elementos del arreglo con el valor false.La cantidad de elementos depende
    //de la cantidad de la lista de productos.
    public void inicializarSeleccionProductoArray()
    {
    	//Prueba iniciar arraylist
    	this.productosSeleccionados = new ArrayList<>();
    	this.seleccionProductoArray = new Boolean[this.productoBean.getListaProducto().size()];
    	for(int i=0;i<this.productoBean.getListaProducto().size();i++)
    	{
    		this.seleccionProductoArray[i]=false;

    	}
    }
    
    //Método que permite resetear todo los elemento del array seleccionProductoArray a false.
    public void resetearSeleccionProductoArray()
    {
    	for(int i=0;i<this.productoBean.getListaProducto().size();i++)
    	{
    		this.seleccionProductoArray[i]=false;
    	}
    }
	
	//Método que permite cargar los productos que han sido seleccionados y almacenados en la tabla DetalleProgramacionAreaProducto 
	public void cargarSeleccionProductos()
	{		
			this.obtenerListaProgramacionAreasId();	
			//Prrueba inicializar productos seleccionados
			this.productosSeleccionados = new ArrayList<>();
			//Se carga los productos si existen en la base de datos de lo contrario, el seleccionProductoArray por defecto es false
			if(this.listaProgramacionAreasId[0] > 0 && this.listaProgramacionAreasId!=null)
			{
			this.detalleProgramacionAreaProductoBean.setProgramacionAreasId(this.listaProgramacionAreasId[0]);
			//Pasamos null para traer todos los productos.
			this.detalleProgramacionAreaProductoBean.setProductoDisponibleId(null);
			this.detalleProgramacionAreaProductoBean.buscarProductosXArea();
			int contador =0;
			for(DetalleProgramacionAreaProductoDTO objeto1:this.detalleProgramacionAreaProductoBean.getListaDetalleProgramacionAreaProducto())
			{
				for (ProductoDTO objeto2: this.productoBean.getListaProducto()) 
				{
					if(objeto1.getProductoDisponible().getId() == objeto2.getId())
					{
						this.seleccionProductoArray[contador] = true;
						//Prueba agregar los productos seleccionados desde bd
						this.productosSeleccionados.add(objeto2);
					}					
				}	
				contador++;
			}						
			}else
			{
				this.inicializarSeleccionProductoArray();
				//Prrueba inicializar productos seleccionados
				this.productosSeleccionados = new ArrayList<>();
			}

	}

	
/*
 	-------------------------------------------------------------------------------------------------------------------------------------------
	------------------------------------------------------------ MÉTODOS DE ACCESO ------------------------------------------------------------ 
	-------------------------------------------------------------------------------------------------------------------------------------------
*/
	
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

	public String[] getSeleccionAreas() {
		return seleccionAreas;
	}

	public void setSeleccionAreas(String[] seleccionAreas) {
		this.seleccionAreas = seleccionAreas;
	}

	public List<AreaDTO> getListaArea() {
		return listaArea;
	}

	public void setListaArea(List<AreaDTO> listaArea) {
		this.listaArea = listaArea;
	}

	public AreaServiceLocal getAreaServiceLocal() {
		return areaServiceLocal;
	}

	public void setAreaServiceLocal(AreaServiceLocal areaServiceLocal) {
		this.areaServiceLocal = areaServiceLocal;
	}

	public List<ProductoDTO> getProductosSeleccionados() {
		return productosSeleccionados;
	}

	public void setProductosSeleccionados(List<ProductoDTO> productosSeleccionados) {
		this.productosSeleccionados = productosSeleccionados;
	}

	public ProductoBean getProductoBean() {
		return productoBean;
	}

	public void setProductoBean(ProductoBean productoBean) {
		this.productoBean = productoBean;
	}

	public List<String> getRowNames() {
		return rowNames;
	}

	public void setRowNames(List<String> rowNames) {
		this.rowNames = rowNames;
	}

	public List<String> getColNames() {
		return colNames;
	}

	public void setColNames(List<String> colNames) {
		this.colNames = colNames;
	}

	public ProductoDTO getProductoDTO() {
		return productoDTO;
	}

	public void setProductoDTO(ProductoDTO productoDTO) {
		this.productoDTO = productoDTO;
	}
	
	public Integer getValorAreaProducto() {
		return valorAreaProducto;
	}

	public void setValorAreaProducto(Integer valorAreaProducto) {
		this.valorAreaProducto = valorAreaProducto;
	}	

	public ArrayList<ArrayList<ArrayList<Integer>>> getListaValores() {
		return listaValores;
	}

	public void setListaValores(
			ArrayList<ArrayList<ArrayList<Integer>>> listaValores) {
		this.listaValores = listaValores;
	}

	public boolean isVerAreaProductos() {
		return verAreaProductos;
	}

	public void setVerAreaProductos(boolean verAreaProductos) {
		this.verAreaProductos = verAreaProductos;
	}
		   
	public ProgramacionAreasBean getProgramacionAreasBean() {
		return programacionAreasBean;
	}

	public void setProgramacionAreasBean(ProgramacionAreasBean programacionAreasBean) {
		this.programacionAreasBean = programacionAreasBean;
	}

	
	public Integer[] getListaProgramacionAreasId() {
		return listaProgramacionAreasId;
	}

	public void setListaProgramacionAreasId(Integer[] listaProgramacionAreasId) {
		this.listaProgramacionAreasId = listaProgramacionAreasId;
	}

	public DetalleProgramacionAreaProductoBean getDetalleProgramacionAreaProductoBean() {
		return detalleProgramacionAreaProductoBean;
	}

	public void setDetalleProgramacionAreaProductoBean(
			DetalleProgramacionAreaProductoBean detalleProgramacionAreaProductoBean) {
		this.detalleProgramacionAreaProductoBean = detalleProgramacionAreaProductoBean;
	}	
    
	public Boolean[] getSeleccionProductoArray() {
		return seleccionProductoArray;
	}

	public void setSeleccionProductoArray(Boolean[] seleccionProductoArray) {
		this.seleccionProductoArray = seleccionProductoArray;
	}

	public ProgramacionAreasServiceLocal getProgramacionAreasServiceLocal() {
		return programacionAreasServiceLocal;
	}

	public void setProgramacionAreasServiceLocal(
			ProgramacionAreasServiceLocal programacionAreasServiceLocal) {
		this.programacionAreasServiceLocal = programacionAreasServiceLocal;
	}

	public DetalleProgramacionAreaProductoServiceLocal getDetalleProgramacionAreaProductoServiceLocal() {
		return detalleProgramacionAreaProductoServiceLocal;
	}

	public void setDetalleProgramacionAreaProductoServiceLocal(
			DetalleProgramacionAreaProductoServiceLocal detalleProgramacionAreaProductoServiceLocal) {
		this.detalleProgramacionAreaProductoServiceLocal = detalleProgramacionAreaProductoServiceLocal;
	}

	
	public List<AreaDTO> getListaAreasParaCalcularCantidad() {
		return listaAreasParaCalcularCantidad;
	}

	public void setListaAreasParaCalcularCantidad(
			List<AreaDTO> listaAreasParaCalcularCantidad) {
		this.listaAreasParaCalcularCantidad = listaAreasParaCalcularCantidad;
	}

	public List<ProductoDTO> getListaProductosParaCalcularCantidad() {
		return listaProductosParaCalcularCantidad;
	}

	public void setListaProductosParaCalcularCantidad(
			List<ProductoDTO> listaProductosParaCalcularCantidad) {
		this.listaProductosParaCalcularCantidad = listaProductosParaCalcularCantidad;
	}

	public ProgramacionAreasDTO getProgramacionAreasDTO() {
		return programacionAreasDTO;
	}

	public void setProgramacionAreasDTO(ProgramacionAreasDTO programacionAreasDTO) {
		this.programacionAreasDTO = programacionAreasDTO;
	}

	public DetalleProgramacionAreaProductoDTO getDetalleProgramacionAreasProductoDTO() {
		return detalleProgramacionAreasProductoDTO;
	}

	public void setDetalleProgramacionAreasProductoDTO(
			DetalleProgramacionAreaProductoDTO detalleProgramacionAreasProductoDTO) {
		this.detalleProgramacionAreasProductoDTO = detalleProgramacionAreasProductoDTO;
	}

	public List<ProgramacionAreasDTO> getListaProgramacionAreasXFiltro() {
		return listaProgramacionAreasXFiltro;
	}

	public void setListaProgramacionAreasXFiltro(
			List<ProgramacionAreasDTO> listaProgramacionAreasXFiltro) {
		this.listaProgramacionAreasXFiltro = listaProgramacionAreasXFiltro;
	}

	public List<DetalleProgramacionAreaProductoDTO> getListaDetalleProgramacionAreaProductoXFiltro() {
		return listaDetalleProgramacionAreaProductoXFiltro;
	}

	public void setListaDetalleProgramacionAreaProductoXFiltro(
			List<DetalleProgramacionAreaProductoDTO> listaDetalleProgramacionAreaProductoXFiltro) {
		this.listaDetalleProgramacionAreaProductoXFiltro = listaDetalleProgramacionAreaProductoXFiltro;
	}

	public List<AreaDTO> getListaAreasSeleccionadas() {
		return listaAreasSeleccionadas;
	}

	public void setListaAreasSeleccionadas(List<AreaDTO> listaAreasSeleccionadas) {
		this.listaAreasSeleccionadas = listaAreasSeleccionadas;
	}

	public Integer[] getValor() {
		return valor;
	}

	public void setValor(Integer[] valor) {
		this.valor = valor;
	}
	
	//Método para comprobar si se logra actualizar el detalle
	public boolean actualizarDetalle()
	{		
		try 
		{
			this.compararListasAreasBDMemoria();
			return true;
		} 
		catch (Exception e) 
		{
			System.out.println("Error -->"+e.getMessage());
		}
		return false;
	}
	
	//PRUEBA
	//Método para comparar la lista de las áreas almacenadas en la BD y la lista actual de areas en memoria
	public void compararListasAreasBDMemoria() throws Exception	
	{
		//Necesito el id de la programacion actual para traer todas las programacionAreasId de esa programación.
		this.programacionAreasBean.setProgramacionId(this.programacionDTO.getId());
		this.programacionAreasBean.setAreaId(null);
		//Buscamos para obtener la programacionesAreas.
		this.programacionAreasBean.buscarProgramacionAreas();		
		//Comparamos cantidad de elementos.Tienen que ser iguales.
		if(this.programacionAreasBean.getListaProgramacionAreas().size() == this.seleccionAreas.length)
		{
			//El orden de cada elementos almacenado en la BD y seleccionado debe ser el mismo.
			if(this.compararIgualdadListasAreasSeleccionadaYAlmacenada() == true)
			{
			//Método para comparar la cantidad de productos.
			int valor =1;
			compararCantidadProductos(valor);	
			}
			else
			{
				System.out.println("Tiene la misma cantidad de elmentos pero en orden diferente.");
			}
		}	
		else
		{
			System.out.println("Se ha alterado la cantidad de elementos entre areas seleccionadas y almacenadas en la BD.");				
			if(this.programacionAreasBean.getListaProgramacionAreas().size() < this.seleccionAreas.length)
			{
				System.out.println("La cantidad almacenada es menor a la cantidad actual seleccionada");
				int valor =2;				
				compararCantidadProductos(valor);
			}
			if(this.programacionAreasBean.getListaProgramacionAreas().size() > this.seleccionAreas.length)
			{
				System.out.println("La cantidad almacenada es mayor a la cantidad actual seleccionada");
				int valor =3;
				compararCantidadProductos(valor);
			}						
		}		
	}
		
	
	//PRUEBA
	//Método para comparar los productos almacenados en la BD y los productos selccionados.Pasamos como parámetro un número para identificar que método los está enlazando 
	//en la parte de comparacion entre áreas almacenadas y seleccionadas. 1 = areas iguales; 2=area almacenada menor a seleccionada ; 3=area almacenada mayor a seleccionada  
	@SuppressWarnings("unused")
	public void compararCantidadProductos(int valor) throws Exception
	{
		//Variable para obtener la cantidad de detalles almacenados en la BD.
		Integer cantidadDetalles =0;
		for(ProgramacionAreasDTO programacionArea:this.programacionAreasBean.getListaProgramacionAreas())		
		{
			this.detalleProgramacionAreaProductoBean.setProgramacionAreasId(programacionArea.getId());
			this.detalleProgramacionAreaProductoBean.setProductoDisponibleId(null);
			this.detalleProgramacionAreaProductoBean.buscarProductosXArea();
			for (DetalleProgramacionAreaProductoDTO detalle: this.detalleProgramacionAreaProductoBean.getListaDetalleProgramacionAreaProducto()) 
			{
				cantidadDetalles++;
			}
			break;
		}

		//COMPARAMOS LA CANTIDAD DE DETALLES PARA VER LA CANTIDAD DE PRODUCTOS DISPONIBLE CON LOS PRODUCTOS SELECCIONADOS
		//if(this.detalleProgramacionAreaProductoBean.getListaDetalleProgramacionAreaProducto().size()==cantidadDetalles)
		if(this.detalleProgramacionAreaProductoBean.getListaDetalleProgramacionAreaProducto().size()==this.productosSeleccionados.size())
		{
			
			if(this.compararIgualdadListasProductosSeleccionadosYAlmacenados()==true)
			{				
				 //Método para actualizar la programacion,programacionAreas,detalleProgramacionAreasProductos.				
				switch (valor) 
				{
				case 1:System.out.println("El área tiene los mismos elementos y en el mismo orden");
					   System.out.println("Los productos tienen los mismos elementos y el mismo orden."); 
					   actualizarAreaProductoUno();break;					   
				case 2:System.out.println("La cantidad de areas almacenada es menor a la cantidad actual seleccionada");
					   System.out.println("Los productos tienen los mismos elementos y el mismo orden.");
					   actualizarAreaProductoDos();break;
				case 3:System.out.println("La cantidad almacenada es mayor a la cantidad actual seleccionada");
				       System.out.println("Los productos tienen los mismos elementos y el mismo orden.");
					   actualizarAreaProductoTres();break;
				default:break;
				}
			}else
			{
				//RETORNA FALSO PORQUE LOS PRODUCTOS SELECCIONADOS Y ALMACENADOS NO SON LOS MISMOS
				//SI LOS PRODUCTOS LA CANTIDAD DE PRODUCTOS ALMACENADOS ES MENOR A LA CANTIDAD DE PRODUCTOS SELCCIONADOS. 
				if(this.detalleProgramacionAreaProductoBean.getListaDetalleProgramacionAreaProducto().size() < this.productosSeleccionados.size())
				{
									
				}
				
				//SI LOS PRODUCTOS LA CANTIDAD DE PRODUCTOS ALMACENADOS ES MENOR A LA CANTIDAD DE PRODUCTOS SELCCIONADOS.
				if(this.detalleProgramacionAreaProductoBean.getListaDetalleProgramacionAreaProducto().size() > this.productosSeleccionados.size())
				{
					switch (valor) {
					case 1:System.out.println("El área tiene los mismos elementos y en el mismo orden");
						   System.out.println("La cantidad de productos almacenados es menora la cantidad de productos seleccionados.");
						   
						   
					default:break;

					}
				}
				
			}
			
		}
		else
		{
			//if(this.detalleProgramacionAreaProductoBean.getListaDetalleProgramacionAreaProducto().size() > this.listaValores.size())
			if(this.detalleProgramacionAreaProductoBean.getListaDetalleProgramacionAreaProducto().size() > this.productosSeleccionados.size())
			{
				switch(valor)
				{
				case 1:System.out.println("El área tiene los mismos elementos y en el mismo orden");
					   System.out.println("La cantidad de productos almacenados es mayor a los seleccionados");
					   //Método para actualizar la programacion,programacionAreas,detalleProgramacionAreasProductos.
					   actualizarAreaProductosCuatro();
					   break;
				case 2:System.out.println("La cantidad de elementos de áreas almacenadas es menor a selecciondas");
					   System.out.println("La cantidad de productos almacenados es mayor a los seleccionados");
					   //Método para actualizar la programacion,programacionAreas,detalleProgramacionAreasProductos.
					   //actualizarAreaProductosCinco();
					   break;
				case 3:System.out.println("La cantidad de elementos de áreas almacenadas es mayor a selecciondas");
					   System.out.println("La cantidad de productos almacenados es mayor a los seleccionados");
					   //Método para actualizar la programacion,programacionAreas,detalleProgramacionAreasProductos.
				       //actualizarAreaProductosSeis();
					   break;
				default:break;
				}
			}
			//if(this.detalleProgramacionAreaProductoBean.getListaDetalleProgramacionAreaProducto().size() < this.listaValores.size())
			if(this.detalleProgramacionAreaProductoBean.getListaDetalleProgramacionAreaProducto().size() < this.productosSeleccionados.size())			
			{
				
				switch(valor)
				{
				case 1:System.out.println("El área tiene los mismos elementos y en el mismo orden");
				       System.out.println("La cantidad de productos almacenados es menor a los seleccionados");
					   //Método para actualizar la programacion,programacionAreas,detalleProgramacionAreasProductos.
				       //actualizarAreaProductosSiete();
					   break;
				case 2:System.out.println("La cantidad de elementos de áreas almacenadas es menor a selecciondas");
				       System.out.println("La cantidad de productos almacenados es menor a los seleccionados");
				   	   //Método para actualizar la programacion,programacionAreas,detalleProgramacionAreasProductos.
			           //actualizarAreaProductosOcho();
					   break;
				case 3:System.out.println("La cantidad de elementos de áreas almacenadas es mayor a selecciondas");
					   System.out.println("La cantidad de productos almacenados es menor a los seleccionados");
      				   //Método para actualizar la programacion,programacionAreas,detalleProgramacionAreasProductos.
		      	       //actualizarAreaProductosNueve();
					   break;
				default:break;
				}
			}
		}
		
		
	}
	
	
	//PRUEBA
	//"El área tiene los mismos elementos y en el mismo orden"
	//"La cantidad de productos almacenados es mayor a los seleccionados"
	//Método para actualizar la programacion,programacionAreas,detalleProgramacionAreasProductos.
	public void actualizarAreaProductosCuatro() throws Exception	
	{
		//BUSCAMOS LAS PROGRAMACIONES AREAS DE LA PROGRAMACION ACTUAL
		this.programacionAreasBean.setProgramacionId(this.programacionDTO.getId());
		this.programacionAreasBean.setAreaId(null);
		this.programacionAreasBean.buscarProgramacionAreas();
		Integer index=0;
		int indiceArregloValores=0;
		for(ProgramacionAreasDTO programacionArea:this.programacionAreasBean.getListaProgramacionAreas())
		{
			//Van a ser iguales porque tienen los mismo elementos y el mismo orden.
			if(programacionArea.getArea().getId() == this.listaAreasSeleccionadas.get(index).getId())
			{
				Boolean seActualizoProgramacionArea = this.programacionAreasServiceLocal.actualizarProgramacionAreas(programacionArea);
				if(seActualizoProgramacionArea == true)
				{
					//Comparamos los elementos de la lista almacenada y la lista seleccionada
					//Las que tengan el mismo ID las actualizamos, las diferentes las eliminamos.
					this.detalleProgramacionAreaProductoBean.setProgramacionAreasId(programacionArea.getId());
					this.detalleProgramacionAreaProductoBean.setProductoDisponibleId(null);
					this.detalleProgramacionAreaProductoBean.buscarProductosXArea();
					//Para obtener el objeto según el indice de los productos almacenados
					int indiceProductoAlmacenado=0;
					for(ProductoDTO producto:this.productosSeleccionados)
					{						
						for(int i=indiceProductoAlmacenado;i<this.detalleProgramacionAreaProductoBean.getListaDetalleProgramacionAreaProducto().size();i++)
						{
							if(producto.getId() == this.detalleProgramacionAreaProductoBean.getListaDetalleProgramacionAreaProducto().get(indiceProductoAlmacenado).getProductoDisponible().getId())
							{
								System.out.println("Los elementos son iguales.");
								//ENTONCES ACTUALIZAMOS EL DETALLE
								DetalleProgramacionAreaProductoDTO detalle = this.detalleProgramacionAreaProductoBean.getListaDetalleProgramacionAreaProducto().get(index);
								detalle.setStockDisponible(this.arregloValoresTablaDinamica[indiceArregloValores]);
								Boolean seActualizoDetalle = this.detalleProgramacionAreaProductoServiceLocal.actualizarDetalleProgramacionAreaProducto(detalle);
								if(seActualizoDetalle == true)
								{
									indiceProductoAlmacenado++;
									indiceArregloValores++;
									System.out.println("Se actualizó");
									break;
								}
								else
								{
									System.out.println("No se actualizó");									
								}
							}
							else
							{
								System.out.println("Los elementos son diferentes.");
								

							}
						}												
					}					
				}
				else
				{
					System.out.println("No se logró actualizar la programación área actual.");
					break;
				}	
			index++;
			}
			else
			{
				System.out.println("El área almacenada es diferente al área seleccionada.");
				break;
			}
			
		}
		
		//AHORA NECESITO ELIMINAR LOS PRODUCTOS QUE NO HAN SIDO SELECCIONADOS
		int cantidadProductoAlmacenados=this.detalleProgramacionAreaProductoBean.getListaDetalleProgramacionAreaProducto().size();
		int cantidadProductoSeleccionados=this.productosSeleccionados.size();
		int []productoParaEliminar = new int [cantidadProductoAlmacenados-cantidadProductoSeleccionados];
		int indiceEliminar=0;
		Integer programacionAreaId=this.programacionAreasBean.getListaProgramacionAreas().get(0).getId();
		this.detalleProgramacionAreaProductoBean.setProgramacionAreasId(programacionAreaId);
		this.detalleProgramacionAreaProductoBean.setProductoDisponibleId(null);
		this.detalleProgramacionAreaProductoBean.buscarProductosXArea();
		//Comparamos entre los productos almacenado y seleccionados
		int indiceDetalle = 0;
		for(ProductoDTO productoSeleccionado : this.productosSeleccionados)
		{
			for(int i =indiceDetalle ;i<this.detalleProgramacionAreaProductoBean.getListaDetalleProgramacionAreaProducto().size();i++)
			{
				if(productoSeleccionado.getId() == this.detalleProgramacionAreaProductoBean.getListaDetalleProgramacionAreaProducto().get(indiceDetalle).getProductoDisponible().getId())
				{
					//SON IGUALES					
					indiceDetalle++;					
					break;
				}
				else
				{
					//SON DIFERENTES ENTONCES AGREGAMOS A UN ARREGLO PARA ELIMINAR LUEGO
					productoParaEliminar[indiceEliminar]=this.detalleProgramacionAreaProductoBean.getListaDetalleProgramacionAreaProducto().get(indiceDetalle).getProductoDisponible().getId();
					indiceDetalle++;
					indiceEliminar++;
				}
			}
		}
		
		
		//AHORA ELIMINAMOS LOS DETALLES
		int i=0;
		for (DetalleProgramacionAreaProductoDTO detalle : this.detalleProgramacionAreaProductoBean.getListaDetalleProgramacionAreaProducto())
		{
			if(detalle.getId() == productoParaEliminar[i])
			{
				//ELIMINAR
				Boolean seEliminoProducto = this.detalleProgramacionAreaProductoServiceLocal.eliminarDetalleProgramacionAreaProducto(productoParaEliminar[i]);
				if(seEliminoProducto == true)
				{
					System.out.println("Se eliminó producto");
					i++;
				}
				else
				{
					System.out.println("No se ha podido eliminar producto");
					break;
				}
				
			}
			else
			{
				System.out.println("No se ha eliminado son diferentes.");
			}
		}
		
		
		
	}
	
	
	
	//PRUEBA 
	//"La cantidad almacenada es mayor a la cantidad actual seleccionada"
    //"Los productos tienen los mismos elementos y el mismo orden."
	public void actualizarAreaProductoTres()throws Exception
	{
		//Recorrer toda la lista seleccionada
		//Luego las que faltan recorrer eliminarlas , comenzar por los detalles y luego con
		//las programacionesAreas.
		//Indice para los valores del areagloTablaDinamicaValores.
		int j=0;
		for(AreaDTO area:this.listaAreasSeleccionadas)
		{
			//El indice i es para productos seleccionados.
			int i=0;
			this.programacionAreasBean.setProgramacionId(this.programacionDTO.getId());
			this.programacionAreasBean.setAreaId(area.getId());
			//Ya tengo el id de la programacionArea,retorna una lista con 1 solo objeto programacionArea
			this.programacionAreasBean.buscarProgramacionAreas();
			//Obtengo la programacion área para actualizarla.
			ProgramacionAreasDTO programacionAreaDTO= this.programacionAreasBean.getListaProgramacionAreas().get(0);
			this.detalleProgramacionAreaProductoBean.setProgramacionAreasId(programacionAreaDTO.getId());
			this.detalleProgramacionAreaProductoBean.setProductoDisponibleId(null);
			Boolean seActualizoProgrArea = this.programacionAreasServiceLocal.actualizarProgramacionAreas(programacionAreaDTO);		
			if(seActualizoProgrArea == true)
			{
				//Entonces obtenemos todos los detalles de una determinada programacionAreaId.
				this.detalleProgramacionAreaProductoBean.buscarProductosXArea();
				for(DetalleProgramacionAreaProductoDTO detalle : this.detalleProgramacionAreaProductoBean.getListaDetalleProgramacionAreaProducto())
				{
					if(detalle.getProductoDisponible().getId() == this.productosSeleccionados.get(i).getId())
					{
						detalle.setStockDisponible(this.arregloValoresTablaDinamica[j]);
						Boolean seActualizoDetalle = this.detalleProgramacionAreaProductoServiceLocal.actualizarDetalleProgramacionAreaProducto(detalle);
						if(seActualizoDetalle == true)
						{
							System.out.println("Se actualizó detalle.");
							i++;
							j++;
						}
						else
						{
							System.out.println("No se actualizó detalle.");
							break;
						}											
					}
					else
					{
						System.out.println("El producto almacenado en la BD y el seleccionado son diferentes, en este orden.");
						break;
					}
				}
			}
			else
			{
				System.out.println("No se actualizó programación área.");
				break;
			}

		}
		
		//Ahora vamos a eliminar las programacionesAreas que están sobrando la tabla PROGRAMACIONAREA
		//que en este instante ya no hemos selecciondo.
		//TENGO QUE COMPARAR AMBAS LISTAS DE AREA POR ID 
		Integer indice=0;
		this.programacionAreasBean.setProgramacionId(this.programacionDTO.getId());
		this.programacionAreasBean.setAreaId(null);
		this.programacionAreasBean.buscarProgramacionAreas();
		
		for(AreaDTO areaSeleccionada : this.listaAreasSeleccionadas)
		{
			for(int h=indice;h<this.programacionAreasBean.getListaProgramacionAreas().size();h++)
			{
				if(areaSeleccionada.getId() == this.programacionAreasBean.getListaProgramacionAreas().get(h).getArea().getId())
				{
					indice++;
					break;
				}else
				{
					h++;
					System.out.println("Tenemos que eliminar la programacionArea que no es igual entre area seleccionada y almacenada");
					//ELIMINAMOS LA PROGRAMACION AREA QUE NO ES IDENTICA ENTRE LAS AREAS SLECCIONADAS Y ALMACENADAS
					Integer programacionAreaId = this.programacionAreasBean.getListaProgramacionAreas().get(h).getId();
					Boolean seElimino = this.programacionAreasServiceLocal.eliminarProgramacionAreas(programacionAreaId);
					if(seElimino == true)
					{
						System.out.println("Se eliminó la programacion area.Por consiguiente también todos los detalles de esa programacionAreaId");
						System.out.println("Porque tiene un restriccion FOREIGN KEY ON DELETE CASCADE");
					}
					else
					{
						System.out.println("No se logró eliminar.");
						break;
					}
					System.out.println("Se eliminó la programacion área.");					
				}
			}				
		}
							
		
	}
	
	//PRUEBA
	//La cantidad de areas almacenada es menor a la cantidad actual seleccionada
	//Los productos tienen los mismos elementos y el mismo orden.
	public void actualizarAreaProductoDos() throws Exception
	{
		int i=0;
		int indiceArea=0;		
			//Verificar el id de los elementos de la lista seleccionada y almacenada con respecto a las áreas
			//Ya que los objetos de productos son los mismos y en el mismo orden			
			//Debo registrar las programacionesAreas
			for(ProgramacionAreasDTO programacionAreas: this.programacionAreasBean.getListaProgramacionAreas())
			{				
				if(programacionAreas.getArea().getId()!=null && (programacionAreas.getArea().getId() == this.listaAreasSeleccionadas.get(indiceArea).getId()))
				{								
					//ACTUALIZA PROGRAMACION AREA
					Boolean isActualizo = programacionAreasServiceLocal.actualizarProgramacionAreas(programacionAreas);
					this.detalleProgramacionAreaProductoBean.setProgramacionAreasId(programacionAreas.getId());
					this.detalleProgramacionAreaProductoBean.setProductoDisponibleId(null);
					
					if(isActualizo == true)
					{
						this.detalleProgramacionAreaProductoBean.buscarProductosXArea();		
						for (DetalleProgramacionAreaProductoDTO detalle: this.detalleProgramacionAreaProductoBean.getListaDetalleProgramacionAreaProducto()) 
						{
							//Como la cantidad y el orden de los elementos en areas y productos son idénticos,entonces pasamos los 
							//valores actuales en memoria para almacenarlos en la BD.
							detalle.setStockDisponible(this.arregloValoresTablaDinamica[i]);
							//ACTUALIZA DETALLEPROGRAMACIONAREAPRODUCTO
							Boolean seActualizo = detalleProgramacionAreaProductoServiceLocal.actualizarDetalleProgramacionAreaProducto(detalle);
							if(seActualizo==true)
							{
								i++;						
							}
							else
							{
								System.out.println("No se actualizó el DetalleProgramacionAreaProducto");
								break;
							}
									
						}			
					}
					else
					{
						System.out.println("No se actualizó ProgramaciónArea");
						break;
					}
					indiceArea++;
				}					
			}
			
			//FALTA INGRESAR LAS ÁREAS QUE SE HAN SELECCIONADO Y NO ESTÁN REGISTRADAS EN LA BD
			int a= this.programacionAreasBean.getListaProgramacionAreas().size();
			int indiceValorTablaDinamica=this.programacionAreasBean.getListaProgramacionAreas().size() * this.productosSeleccionados.size();
			for(int j=a;j<this.listaAreasSeleccionadas.size();j++)
			{
				short indicadorActivo=1;
				int estadoRegistrado=1;
				String codigoUsuarioRegistro = "GERALDO";
				//REGISTRAR PROGRAMACION AREA 
				ProgramacionAreasDTO programacionAreaDTO = new ProgramacionAreasDTO();											
				programacionAreaDTO.setProgramacion(new ProgramacionDTO());
				programacionAreaDTO.getProgramacion().setId(this.programacionDTO.getId());
				programacionAreaDTO.setArea(new AreaDTO());
				programacionAreaDTO.getArea().setId(this.listaAreasSeleccionadas.get(a).getId());				
				programacionAreaDTO.setIndicadorActivo(indicadorActivo);
				programacionAreaDTO.setCodigoUsuarioRegistro(codigoUsuarioRegistro);
				programacionAreaDTO.setIpRegistro(obtieneIPCliente());
				programacionAreaDTO.setEstado(new EstadoDTO());
				programacionAreaDTO.getEstado().setId(estadoRegistrado);
				ProgramacionAreasDTO registro = this.programacionAreasServiceLocal.registrarProgramacionAreas(programacionAreaDTO);
				if(registro!=null)
				{					
					System.out.println("Se registró");
					//NECESITO REGITRAR LOS DETALLES ENTRE AREA PRODUCTO DE CADA PROGRAMACIONAREA
					for(ProductoDTO producto:this.productosSeleccionados)
					{
						DetalleProgramacionAreaProductoDTO detalle = new DetalleProgramacionAreaProductoDTO();
						detalle.setProgramacionAreas(new ProgramacionAreasDTO());
						detalle.getProgramacionAreas().setId(registro.getId());
						detalle.setProductoDisponible(new ProductoDTO());
						detalle.getProductoDisponible().setId(producto.getId());
						detalle.setStockDisponible(this.arregloValoresTablaDinamica[indiceValorTablaDinamica]);						
						detalle.setIndicadorActivo(indicadorActivo);
						detalle.setCodigoUsuarioRegistro(codigoUsuarioRegistro);
						detalle.setIpRegistro(obtieneIPCliente());
						detalle.setEstado(new EstadoDTO());
						detalle.getEstado().setId(estadoRegistrado);
						DetalleProgramacionAreaProductoDTO registroDetalle = this.detalleProgramacionAreaProductoServiceLocal.registrarDetalleProgramacionAreaProducto(detalle);
						if(registroDetalle!=null)
						{
							System.out.println("Se registró el detalle");
							indiceValorTablaDinamica++;	
						}
						else
						{
							System.out.println("No se registró el detalle");
							break;
						}
						
					}									
				}
				else
				{
					System.out.println("No se registró");
					break;
				}
			}
			
			
			
	}
	 
	//PRUEBA
	//El área tiene los mismos elementos y en el mismo orden
	//Los productos tienen los mismos elementos y el mismo orden.
	public void actualizarAreaProductoUno() throws Exception
	{
	int i=0;
	
	for (ProgramacionAreasDTO programacionArea: this.programacionAreasBean.getListaProgramacionAreas()) 
	{		
		//ACTUALIZA PROGRAMACION AREA
		Boolean isActualizo = programacionAreasServiceLocal.actualizarProgramacionAreas(programacionArea);
		
		this.detalleProgramacionAreaProductoBean.setProgramacionAreasId(programacionArea.getId());
		this.detalleProgramacionAreaProductoBean.setProductoDisponibleId(null);
		if(isActualizo == true)
		{
			this.detalleProgramacionAreaProductoBean.buscarProductosXArea();		
			for (DetalleProgramacionAreaProductoDTO detalle: this.detalleProgramacionAreaProductoBean.getListaDetalleProgramacionAreaProducto()) 
			{
				//Como la cantidad y el orden de los elementos en areas y productos son idénticos,entonces pasamos los 
				//valores actuales en memoria para almacenarlos en la BD.
				detalle.setStockDisponible(this.arregloValoresTablaDinamica[i]);
//				detalle.setStockDisponible(this.valor[i]);
				//ACTUALIZA DETALLEPROGRAMACIONAREAPRODUCTO
				Boolean seActualizo = detalleProgramacionAreaProductoServiceLocal.actualizarDetalleProgramacionAreaProducto(detalle);
				if(seActualizo==true)
				{
					i++;						
				}
				else
				{
					System.out.println("No se actualizó el DetalleProgramacionAreaProducto");
					break;
				}
						
			}			
		}
		else
		{
			System.out.println("No se actualizó ProgramaciónArea");
			break;
		}

	}		
	}
		
		
	
	
	
	
	//PRUEBA
	//Método para comparar elemento por elemento entre las listas : almacenadas y seleccionadas de productos		
	public boolean compararIgualdadListasProductosSeleccionadosYAlmacenados()
	{
		int contador=0;
		for(DetalleProgramacionAreaProductoDTO detalle : this.detalleProgramacionAreaProductoBean.getListaDetalleProgramacionAreaProducto() )
		{
			if(detalle.getProductoDisponible().getId() == this.productosSeleccionados.get(contador).getId())
			{
				contador++;
			}
			else
			{
				return false;
			}
		}
		return true;
	}
	
	//PRUEBA
	//Método para compara elemento por elemento entre las listas : almacenadas y seleccionadas de áreas
	public boolean compararIgualdadListasAreasSeleccionadaYAlmacenada()
	{
		Integer contador=0;
		for (ProgramacionAreasDTO programacionAreas: this.programacionAreasBean.getListaProgramacionAreas()) 
		{
			if(programacionAreas.getArea().getId() == Integer.parseInt(this.seleccionAreas[contador]+""))
			{
				contador++;
			}			
			else
			{
				return false;				
			}			
		}		
		return true;
	}

	
	/*************************************** ENCARGADO DE ALMACEN *************************/
	
	//Prueba de método para cargar la lista de programaciones aprobadas y enviadas al encargado del almacén
	public void cargarListaProgramacionEnviadaAlEncargadoDeAlmacen()
	{
		buscarProgramacion();
		this.listaProgramacionEnviadaAlEncargadoDeAlmacen = new ArrayList<>();		
		for(ProgramacionDTO objeto : this.listaProgramacion)
		{
			//6 : ENVIADO AL ENCARGADO
			if(objeto.getEstado().getId() == 6)
			{
				this.listaProgramacionEnviadaAlEncargadoDeAlmacen.add(objeto);				
			}
			else
			{
				System.out.println("El objeto no ha sido enviado al encargado de almacén.");
			}
		}				
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/************* METODOS DE ACCESO ****************************************************/
	
	
	public Integer[] getArregloValoresTablaDinamica() {
		return arregloValoresTablaDinamica;
	}

	public void setArregloValoresTablaDinamica(Integer[] arregloValoresTablaDinamica) {
		this.arregloValoresTablaDinamica = arregloValoresTablaDinamica;
	}

	public List<ProgramacionDTO> getListaProgramacionEnviadaAlEncargadoDeAlmacen() {
		return listaProgramacionEnviadaAlEncargadoDeAlmacen;
	}

	public void setListaProgramacionEnviadaAlEncargadoDeAlmacen(
			List<ProgramacionDTO> listaProgramacionEnviadaAlEncargadoDeAlmacen) {
		this.listaProgramacionEnviadaAlEncargadoDeAlmacen = listaProgramacionEnviadaAlEncargadoDeAlmacen;
	}

		
	
	

	
	
}
