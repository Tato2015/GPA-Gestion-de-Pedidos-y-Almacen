package pe.gob.servir.gpa.bean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import pe.gob.servir.gpa.model.dto.AreaDTO;
import pe.gob.servir.gpa.model.dto.DetalleProgramacionAreaProductoDTO;
import pe.gob.servir.gpa.model.dto.DetalleRequerimientoUsuarioProductoDTO;
import pe.gob.servir.gpa.model.dto.EstadoDTO;
import pe.gob.servir.gpa.model.dto.ProductoDTO;
import pe.gob.servir.gpa.model.dto.ProgramacionAreasDTO;
import pe.gob.servir.gpa.model.dto.ProgramacionDTO;
import pe.gob.servir.gpa.model.dto.RequerimientoDTO;
import pe.gob.servir.gpa.model.dto.RequerimientoUsuarioDTO;
import pe.gob.servir.gpa.model.dto.TipoDTO;
import pe.gob.servir.gpa.model.dto.UsuarioDTO;
import pe.gob.servir.gpa.model.dto.UsuarioPerfilesDTO;
import pe.gob.servir.gpa.service.ejb.DetalleProgramacionAreaProductoServiceLocal;
import pe.gob.servir.gpa.service.ejb.DetalleRequerimientoUsuarioProductoServiceLocal;
import pe.gob.servir.gpa.service.ejb.ProgramacionAreasServiceLocal;
import pe.gob.servir.gpa.service.ejb.ProgramacionServiceLocal;
import pe.gob.servir.gpa.service.ejb.RequerimientoServiceLocal;
import pe.gob.servir.gpa.service.ejb.RequerimientoUsuarioServiceLocal;
import pe.gob.servir.gpa.service.ejb.UsuarioPerfilesServiceLocal;
import pe.gob.servir.gpa.service.ejb.UsuarioServiceLocal;
import pe.gob.servir.gpa.util.ConstanteWeb;
import pe.gob.servir.gpa.util.UBaseManage;

@ManagedBean(name="requerimientoUsuarioBean")
@SessionScoped
public class RequerimientoUsuarioBean extends UBaseManage implements Serializable
{
	private static final long serialVersionUID = -2600184213953915567L;
	private Logger log = Logger.getLogger(TipoBean.class);
	
	@EJB
	private RequerimientoUsuarioServiceLocal requerimientoUsuarioServiceLocal;
	@EJB
	private ProgramacionServiceLocal programacionServiceLocal;
	@EJB
	private UsuarioServiceLocal usuarioServiceLocal; 
	@EJB
	private ProgramacionAreasServiceLocal programacionAreasServiceLocal;
	@EJB
	private DetalleProgramacionAreaProductoServiceLocal detalleProgramacionAreaProductoServiceLocal;
	@EJB
	private DetalleRequerimientoUsuarioProductoServiceLocal detalleRequerimientoUsuarioProductoServiceLocal;
	@EJB
	private RequerimientoServiceLocal requerimientoServiceLocal;
	@EJB
	private UsuarioPerfilesServiceLocal usuarioPerfilesServiceLocal;
	@ManagedProperty("#{loginUsuarioBean}")
	private LoginUsuarioBean loginUsuarioBean;
	
	/** Variable contexto */
	private RequestContext context;
	
	public RequerimientoUsuarioBean()
	{
		
	}
	
	
	//VARIABLES
	private List<SelectItem> listaEstado;
	private List<SelectItem> listaEstadoDetalleRequerimiento;	
	private List<SelectItem> listaIndicadorEnvioAlEncargado;
	private List<SelectItem> listaIndicadorRecibidoDelEncargado;
	private List<SelectItem> listaIndicadorDetalleRequerimiento;
	private List<RequerimientoUsuarioDTO>listaRequerimientoUsuario;
	private Integer posicion;
	private RequerimientoUsuarioDTO requerimientoUsuarioDTO;
	private ProgramacionDTO programacionDTO;
	private UsuarioDTO usuarioDTO;
	private ProgramacionAreasDTO programacionAreasDTO;
	private List<String> rowNames; 
	private List<String> colNames;
	private Integer arregloValoresAreaProducto[][];
	private DetalleRequerimientoUsuarioProductoDTO detalleRequerimientoUsuarioProductoDTO;
	private RequerimientoDTO requerimientoDTO;
	//Prueba para pasarle el usuarioId
//	private int usuarioId = this.loginUsuarioBean.getUsuarioDTO().getId();
//	private int usuarioId= 16;
	private int usuarioId;
	
	private Integer identificadorRequerimientoUsuario;
	private Integer identificadorEstado;
	
	
	
	@PostConstruct
	public void Inicializando()
	{
	this.identificadorRequerimientoUsuario = null;
	this.identificadorEstado = null;	
	this.requerimientoUsuarioDTO = new RequerimientoUsuarioDTO();
	this.programacionDTO = new ProgramacionDTO();
	this.rowNames = new ArrayList<String>();
	this.colNames = new ArrayList<String>();
	generarListaEstado();
	this.usuarioId = this.loginUsuarioBean.getUsuarioDTO().getId();
	//Cargamos el usuario. Usuario -- > id= 1 , desc = primer usuario , clave =123
//	cargarUsuario(1, "primer usuario", "123");
	//Cargamos el usuario. Usuario -- > id= 2 , desc = null , clave =null
//	cargarUsuario(2, null, null);
//Cargamos el usuario. Usuario -- > id= 3 , desc = null , clave =null
//	cargarUsuario(this.loginUsuarioBean.getUsuarioDTO().getId(), null, null);
	cargarUsuario(this.usuarioId, null, null);
	buscarRequerimientoSuministro();	
}
	
	//Método para generar una lista de estados para mostrarlos en el combobox del formulario.
	public void generarListaEstado() 
	{
		listaEstado = new ArrayList<>();
		listaEstado.add(new SelectItem(null, ConstanteWeb.CAMPO_SELECCIONE));
		listaEstado.add(new SelectItem(ConstanteWeb.VALOR_REGISTRADO,ConstanteWeb.CAMPO_ACTIVO));		
	}
	
	//Método que permite obtener todos los objetos de la tabla RequerimientoUsuario, pasando como parámetro el Id de 
	//del requerimiento de usuario y el id del estado.
	public void buscarRequerimientoSuministro()
	{
		try 
		{
		this.listaRequerimientoUsuario = new ArrayList<RequerimientoUsuarioDTO>();
		if(identificadorRequerimientoUsuario == null || identificadorRequerimientoUsuario==0 )					
		{
			if(identificadorEstado == null || identificadorEstado==0)
			{
				List<RequerimientoUsuarioDTO>listaReal = requerimientoUsuarioServiceLocal.buscarRequerimientoUsuarioDTO(null, null,this.usuarioId,null,null,null,null);
				if(listaReal!=null && listaReal.size()>0)
				{
					for(RequerimientoUsuarioDTO objeto:listaReal)
					{
						if(objeto.getId()!=null && objeto.getId()>=0)
						{
							this.listaRequerimientoUsuario.add(objeto);
						}
					}					
				}
			}	
			else
			{
				this.listaRequerimientoUsuario = requerimientoUsuarioServiceLocal.buscarRequerimientoUsuarioDTO(null,null,this.usuarioId,null, this.identificadorEstado,null,null);
			}
		}
		else
		{
			if(identificadorEstado == null || identificadorEstado==0)
			{
				List<RequerimientoUsuarioDTO>listaReal = requerimientoUsuarioServiceLocal.buscarRequerimientoUsuarioDTO(this.identificadorRequerimientoUsuario, null,null,null,null,null,null);
				if(listaReal!=null && listaReal.size()>0)
				{
					for(RequerimientoUsuarioDTO objeto:listaReal)
					{
						if(objeto.getId()!=null && objeto.getId()>=0)
						{
							this.listaRequerimientoUsuario.add(objeto);
						}
					}					
				}
			}
			else
			{
				this.listaRequerimientoUsuario = requerimientoUsuarioServiceLocal.buscarRequerimientoUsuarioDTO(this.identificadorRequerimientoUsuario,null,null, null,this.identificadorEstado,null,null); 
			}
			
		}
			
		}
		catch (Exception e) 
		{
		
		}
		
	}
		
	//Método que permite limpiar los componentes en el formulario.
	public void limpiarRequerimientoSuministro()
	{
		this.identificadorRequerimientoUsuario = null;
		this.identificadorEstado = null;
		this.listaRequerimientoUsuario = new ArrayList<>();
		
	}
	
	//Método para abrir el popup que presenta los detalles del requerimiento de usuario.
	public void abrirPopupDetallesRequerimiento(Integer posicion) 
	{
		obtenerDatosRequerimiento(posicion);
		this.posicion = posicion;
		generarListaEstadoDetalleRequerimiento();
		generarListaIndicadorEnvioAlEncargado();
		generarListaIndicadorRecibidoDelEncargado();
		generarListaIndicadorDetalleRequerimiento();
		//isEditar = true;
		//titulo = "ACTUALIZAR TIPO";
		context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogoDetalleRequerimientoUsuario').show();");
	}
	
	//Método que permite cargar una lista de indicadores, que permite comparar el id del indicadorActivo del requerimiento en la bd y el que se carga en el
	//selectOneMenu .
	//id = 0-->INACTIVO::::    id = 1 -->ACTIVO
	public void generarListaIndicadorDetalleRequerimiento()
	{
		listaIndicadorDetalleRequerimiento = new ArrayList<>();
		listaIndicadorDetalleRequerimiento.add(new SelectItem(null,ConstanteWeb.CAMPO_SELECCIONE));		
		listaIndicadorDetalleRequerimiento.add(new SelectItem((Integer)ConstanteWeb.INDICADRO_INACTIVO,ConstanteWeb.CAMPO_INACTIVO));
		listaIndicadorDetalleRequerimiento.add(new SelectItem((Integer)ConstanteWeb.INDICADRO_ACTIVO,ConstanteWeb.CAMPO_ACTIVO));
	}
	
	
	//Método que permite cargar una lista de indicadores, que permite comparar el id del indicadorRecibidoDelEncargado del requerimiento en la bd y el que se carga en el
	//selectOneMenu .
	//id = 0-->NO ENVIADO  ::::    id = 1 -->ENVIADO	
	public void generarListaIndicadorRecibidoDelEncargado()
	{
		listaIndicadorRecibidoDelEncargado = new ArrayList<>();
		listaIndicadorRecibidoDelEncargado.add(new SelectItem(null,ConstanteWeb.CAMPO_SELECCIONE));
		listaIndicadorRecibidoDelEncargado.add(new SelectItem((Integer)ConstanteWeb.VALOR_NO_RECIBIDO,ConstanteWeb.CAMPO_NO_RECIBIDO));
		listaIndicadorRecibidoDelEncargado.add(new SelectItem((Integer)ConstanteWeb.VALOR_RECIBIDO,ConstanteWeb.CAMPO_RECIBIDO));
	}
	
	//Método que permite cargar una lista de indicadores, que permite comparar el id del indicadorEnvioAlEncargado del requerimiento en la bd y el que se carga en el
	//selectOneMenu .
	//id = 0-->NO ENVIADO  ::::    id = 1 -->ENVIADO   :::::  id=2 ---->RECHAZADO
	public void generarListaIndicadorEnvioAlEncargado()
	{
		listaIndicadorEnvioAlEncargado = new ArrayList<>();
		listaIndicadorEnvioAlEncargado.add(new SelectItem(null,ConstanteWeb.CAMPO_SELECCIONE));
		listaIndicadorEnvioAlEncargado.add(new SelectItem((Integer)ConstanteWeb.VALOR_NO_ENVIADO,ConstanteWeb.CAMPO_NO_ENVIADO));
		listaIndicadorEnvioAlEncargado.add(new SelectItem((Integer)ConstanteWeb.VALOR_ENVIADO,ConstanteWeb.CAMPO_ENVIADO));
		listaIndicadorEnvioAlEncargado.add(new SelectItem((Integer)ConstanteWeb.VALOR_RECHAZADO,ConstanteWeb.CAMPO_RECHAZADO));	
	}
	
	
	//Método que permite cargar una lista de estados, que permite comparar el id de estado del requerimiento en la bd y el que se carga en el
	//selectOneMenu .
	public void generarListaEstadoDetalleRequerimiento() 
	{
		listaEstadoDetalleRequerimiento = new ArrayList<>();
		listaEstadoDetalleRequerimiento.add(new SelectItem(null, ConstanteWeb.CAMPO_SELECCIONE));
		listaEstadoDetalleRequerimiento.add(new SelectItem((Integer)ConstanteWeb.VALOR_REGISTRADO,ConstanteWeb.CAMPO_REGISTRADO));		
	}
	
	//Método que permite setear todos al objeto requerimientoUsuarioDTO todos los atributos del elemento de la listaRequerimientoSuministro que esté en la posición --> posicion.intValue().
	public void obtenerDatosRequerimiento(Integer posicion)
	{
		RequerimientoUsuarioDTO objeto = this.listaRequerimientoUsuario.get(posicion.intValue());
		this.requerimientoUsuarioDTO = new RequerimientoUsuarioDTO();
		this.requerimientoUsuarioDTO.setId(objeto.getId());
		this.requerimientoUsuarioDTO.setRequerimiento(new RequerimientoDTO());
		this.requerimientoUsuarioDTO.getRequerimiento().setId(objeto.getRequerimiento().getId());
		this.requerimientoUsuarioDTO.setUsuario(new UsuarioDTO());
		this.requerimientoUsuarioDTO.getUsuario().setId(objeto.getUsuario().getId());
		this.requerimientoUsuarioDTO.setProgramacionAreas(new ProgramacionAreasDTO());
		this.requerimientoUsuarioDTO.getProgramacionAreas().setId(objeto.getProgramacionAreas().getId());
		this.requerimientoUsuarioDTO.setIndicadorEnviadoAlEncargado(objeto.getIndicadorEnviadoAlEncargado());
		this.requerimientoUsuarioDTO.setFechaEnviadoAlEncargado(objeto.getFechaEnviadoAlEncargado());
		this.requerimientoUsuarioDTO.setIndicadorRequerimientoRecibidoDelEncargado(objeto.getIndicadorRequerimientoRecibidoDelEncargado());
		this.requerimientoUsuarioDTO.setFechaRequerimientoRecibidoDelEncargado(objeto.getFechaRequerimientoRecibidoDelEncargado());
		this.requerimientoUsuarioDTO.setIndicadorActivo(objeto.getIndicadorActivo());
		this.requerimientoUsuarioDTO.setCodigoUsuarioRegistro(objeto.getCodigoUsuarioRegistro());
		this.requerimientoUsuarioDTO.setIpRegistro(objeto.getIpRegistro());
		this.requerimientoUsuarioDTO.setFechaRegistro(objeto.getFechaRegistro());
		this.requerimientoUsuarioDTO.setCodigoUsuarioModificacion(objeto.getCodigoUsuarioModificacion());
		this.requerimientoUsuarioDTO.setIpModificacion(objeto.getIpModificacion());
		this.requerimientoUsuarioDTO.setFechaModificacion(objeto.getFechaModificacion());
		this.requerimientoUsuarioDTO.setEstado(new EstadoDTO());
		this.requerimientoUsuarioDTO.getEstado().setId(objeto.getEstado().getId());
		this.requerimientoUsuarioDTO.getUsuario().setDescripcionUsuario(objeto.getUsuario().getDescripcionUsuario());
		this.requerimientoUsuarioDTO.getProgramacionAreas().setArea(new AreaDTO());
		this.requerimientoUsuarioDTO.getProgramacionAreas().getArea().setId(objeto.getProgramacionAreas().getArea().getId());
		this.requerimientoUsuarioDTO.getProgramacionAreas().getArea().setDescripcionArea(objeto.getProgramacionAreas().getArea().getDescripcionArea());
		this.requerimientoUsuarioDTO.getProgramacionAreas().setProgramacion(new ProgramacionDTO());
		this.requerimientoUsuarioDTO.getProgramacionAreas().getProgramacion().setId(objeto.getProgramacionAreas().getProgramacion().getId());
		this.requerimientoUsuarioDTO.getProgramacionAreas().getProgramacion().setDescripcionProgramacion(objeto.getProgramacionAreas().getProgramacion().getDescripcionProgramacion());
		this.requerimientoUsuarioDTO.getProgramacionAreas().getProgramacion().setTipoPedido(new TipoDTO());
		this.requerimientoUsuarioDTO.getProgramacionAreas().getProgramacion().getTipoPedido().setId(objeto.getProgramacionAreas().getProgramacion().getTipoPedido().getId());
		this.requerimientoUsuarioDTO.getProgramacionAreas().getProgramacion().getTipoPedido().setDescripcionTipo(objeto.getProgramacionAreas().getProgramacion().getTipoPedido().getDescripcionTipo());
	}
	
	//Método que permite abrir un popup para poder generar un requerimiento de usuario.
	public void nuevoRequerimientoUsuario()
	{
		obtenerListaMesActual();
		//Método para cargar el id de la programacion area, para luego traer los productos disponibles.
		Integer idProgramacion =this.programacionDTO.getId();
		Integer idArea = this.usuarioDTO.getArea().getId();
		cargarProgramacionAreas(idProgramacion,idArea);
		//Método pra cargar el area en la tabla
		cargarAreaEnTabla();
		//Método para cargar los productos pasándole como parámetro la programacionAreaId.
		Integer idProgramacionArea = this.programacionAreasDTO.getId();
		cargarProductosEnTabla(idProgramacionArea);
		//Método para inicializar los valores del ArregloValoresAreaProducto.
		inicializarArregloValoresAreaProducto();
		
		boolean verificar = verificarRequerimientoUsuario();
		if(verificar == true)
		{			
			context = RequestContext.getCurrentInstance();
			context.execute("PF('dialogoNuevoRequerimientoUsuario').show();");
		}
		else
		{
//			FacesContext context = FacesContext.getCurrentInstance();
//			context.addMessage(null, new FacesMessage("Aviso",
//			"Se registró con éxito"));			
//			FacesContext.getCurrentInstance().addMessage("idRequerimientoArea:btnNuevo", 
//					new FacesMessage("Aviso","Se registró con éxito"));	
			FacesContext.getCurrentInstance().addMessage("Message1", 
			new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se puede crear un requerimiento de usuario. Puede ser que ya hubiera creado un requerimiento para la " +
			        "programación actual : " + this.programacionDTO.getDescripcionProgramacion()));
			//error();		
			
			//Prueba para que las cabeceras no se dupliquen			
			limpiarCabecerasTabla();
			
		}
		
		
	}
	
	//Prueba de método para presentar mensaje de error.
	public void error() 
	{
		FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",
        "No se puede crear un requerimiento de usuario. Puede ser que ya hubiera creado un requerimiento para la " +
        "programación actual."));
    }
	
	
	//Prueba de método para verificar que solo puede tener 1 requerimiento de usuario por programación.
	public boolean verificarRequerimientoUsuario()
	{
		try 
		{
			int usuarioId = this.usuarioId;
			int programacionAreasId = this.programacionAreasDTO.getId();
			int cantidadMaximaDeRequerimientoXProgramacion = 1;
			List<RequerimientoUsuarioDTO>listaRequerimientoUsuario = requerimientoUsuarioServiceLocal.buscarRequerimientoUsuarioDTO(null, null, usuarioId, programacionAreasId, null, null, null);
			if(listaRequerimientoUsuario.size() >= cantidadMaximaDeRequerimientoXProgramacion)
			{
				System.out.println("No se puede crear más de 1 requerimiento de usuario por Programación.");
				return false;
			}
			else
			{
				System.out.println("Se puede crear requerimiento de usuario .");
				return true;
			}			
		}
		catch (Exception e) 
		{
		System.out.println("Error : "+e.getMessage());
		}
		return false;
	}
	
	
	
	
	
	//Método para obtener la lista actual de programaciones para este mes, pasandole como parámetro el mes actual.
	public void obtenerListaMesActual()
	{
		Integer mesActual = getMesSistema();
		List<ProgramacionDTO> listaProgramacionMesActual = new ArrayList<ProgramacionDTO>();
		listaProgramacionMesActual = programacionServiceLocal.buscarProgramacionXMesActual(mesActual);
		if(listaProgramacionMesActual.size()>=0 && listaProgramacionMesActual!=null)
		{			
			ProgramacionDTO objeto = listaProgramacionMesActual.get(0);
			this.programacionDTO = new ProgramacionDTO();
			this.programacionDTO.setId(objeto.getId());
			this.programacionDTO.setDescripcionProgramacion(objeto.getDescripcionProgramacion());
			this.programacionDTO.setTipoPedido(new TipoDTO());
			this.programacionDTO.getTipoPedido().setId(objeto.getTipoPedido().getId());
			this.programacionDTO.getTipoPedido().setDescripcionTipo(objeto.getTipoPedido().getDescripcionTipo());
			this.programacionDTO.setTipoPeriodo(new TipoDTO());
			this.programacionDTO.getTipoPeriodo().setId(objeto.getTipoPeriodo().getId());
			this.programacionDTO.getTipoPeriodo().setDescripcionTipo(objeto.getTipoPeriodo().getDescripcionTipo());
			this.programacionDTO.setFechaInicioRegistro(objeto.getFechaInicioRegistro());
			this.programacionDTO.setFechaFinRegistro(objeto.getFechaFinRegistro());
		}
	}
	
	//Método para buscar el requerimiento de usuario mediante el mes en el cual se ha registrado.
	public void obtenerListaMesDelRequerimientoUsuario(Date fechaRegistroRequerimiento)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(fechaRegistroRequerimiento);
		Integer mesRequerimeintoUsuario = c.get(Calendar.MONTH)+1;
		List<ProgramacionDTO> listaProgramacionMesActual = new ArrayList<ProgramacionDTO>();
		listaProgramacionMesActual = programacionServiceLocal.buscarProgramacionXMesActual(mesRequerimeintoUsuario);
		if(listaProgramacionMesActual.size()>=0 && listaProgramacionMesActual!=null)
		{			
			ProgramacionDTO objeto = listaProgramacionMesActual.get(0);
			this.programacionDTO = new ProgramacionDTO();
			this.programacionDTO.setId(objeto.getId());
			this.programacionDTO.setDescripcionProgramacion(objeto.getDescripcionProgramacion());
			this.programacionDTO.setTipoPedido(new TipoDTO());
			this.programacionDTO.getTipoPedido().setId(objeto.getTipoPedido().getId());
			this.programacionDTO.getTipoPedido().setDescripcionTipo(objeto.getTipoPedido().getDescripcionTipo());
			this.programacionDTO.setTipoPeriodo(new TipoDTO());
			this.programacionDTO.getTipoPeriodo().setId(objeto.getTipoPeriodo().getId());
			this.programacionDTO.getTipoPeriodo().setDescripcionTipo(objeto.getTipoPeriodo().getDescripcionTipo());
			this.programacionDTO.setFechaInicioRegistro(objeto.getFechaInicioRegistro());
			this.programacionDTO.setFechaFinRegistro(objeto.getFechaFinRegistro());
		}
	}
	
	
	//Método que permite cargar el usuario mediante los oarámetros idUsuario, descripcionUsuario,claveUsuario.
	public void cargarUsuario(Integer idUsuario,String descripcionUsuario,String claveUsuario)
	{
		List<UsuarioDTO>listaUsuario = new ArrayList<UsuarioDTO>();
		this.usuarioDTO = new UsuarioDTO();
		try 
		{
			listaUsuario= usuarioServiceLocal.buscarUsuario(idUsuario, descripcionUsuario, claveUsuario);
			this.usuarioDTO = listaUsuario.get(0);			
		} 
		catch (Exception e) 
		{
		log.error(e.getMessage(),e);
		}
	}
	
	//Método para cargar el objeto programacionAreasDTO.
	public void cargarProgramacionAreas(Integer idProgramacion,Integer idArea)
	{
		List<ProgramacionAreasDTO>listaProgramacionAreasActual = new ArrayList<ProgramacionAreasDTO>();
		this.programacionAreasDTO = new ProgramacionAreasDTO();
		try 
		{
			listaProgramacionAreasActual = programacionAreasServiceLocal.buscarProgramacionAreas(idProgramacion, idArea);
			for (ProgramacionAreasDTO objeto: listaProgramacionAreasActual) 
			{
				if(objeto.getProgramacion().getId()== this.programacionDTO.getId() && 
				   objeto.getArea().getId() == this.usuarioDTO.getArea().getId())
				{
					this.programacionAreasDTO.setId(objeto.getId());
					this.programacionAreasDTO.setProgramacion(new ProgramacionDTO());
					this.programacionAreasDTO.getProgramacion().setId(objeto.getProgramacion().getId());
					this.programacionAreasDTO.setArea(new AreaDTO());
					this.programacionAreasDTO.getArea().setId(objeto.getArea().getId());					
					this.programacionAreasDTO.setAnio(objeto.getAnio());
					this.programacionAreasDTO.setIndicadorActivo(objeto.getIndicadorActivo());
					this.programacionAreasDTO.setCodigoUsuarioRegistro(objeto.getCodigoUsuarioRegistro());
					this.programacionAreasDTO.setIpRegistro(objeto.getIpRegistro());
					this.programacionAreasDTO.setFechaRegistro(objeto.getFechaRegistro());
					this.programacionAreasDTO.setCodigoUsuarioModificacion(objeto.getCodigoUsuarioModificacion());
					this.programacionAreasDTO.setIpModificacion(objeto.getIpModificacion());
					this.programacionAreasDTO.setFechaModificacion(objeto.getFechaModificacion());
					this.programacionAreasDTO.setEstado(new EstadoDTO());
					this.programacionAreasDTO.getEstado().setId(objeto.getEstado().getId());
					this.programacionAreasDTO.getProgramacion().setDescripcionProgramacion(objeto.getProgramacion().getDescripcionProgramacion());
					this.programacionAreasDTO.getArea().setDescripcionArea(objeto.getArea().getDescripcionArea());
					this.programacionAreasDTO.getEstado().setDescripcionEstado(objeto.getEstado().getDescripcionEstado());					
				}		
				else
				{
					System.out.println("Las programaciones son diferenetes ó las áreas son diferenetes.");
				}
			}			
		}
		catch (Exception e) 
		{
		
		}
		
	}
	
	//Agregar la descripción del área en el cual estamos
	public void cargarAreaEnTabla()
	{
		try 
		{
			//Comparamos si el area de la programacionarea es la misma que el area en la que está el usuario.
			if(this.programacionAreasDTO.getArea().getId() == this.usuarioDTO.getArea().getId())
			{
				if(this.rowNames.size() == 0)
				{
					this.rowNames.add(this.programacionAreasDTO.getArea().getDescripcionArea());	
				}
				else
				{
					System.out.println("Solo debe tener 1 área.");
				}
			
			}
			else
			{
				System.out.println("El usuario no pertenece a esta área.");
			}
			
		}
		catch (Exception e) 
		{
		
		}
	}
	
	//Método que permite inicializar la variable rowNames.
	public void resetearAreaEnTabla()
	{
		this.rowNames = new ArrayList<String>();
	}
	
	//Método que permite cargar los productos de una programaciónArea específica,
	//es decir de una programación específica y un área específica.
	public void cargarProductosEnTabla(Integer programacionAreasId)
	{
		List<DetalleProgramacionAreaProductoDTO>listaDetalles = new ArrayList<DetalleProgramacionAreaProductoDTO>();		
		try 
		{	
			//Comparamos si el area de la programacionarea es la misma que el area en la que está el usuario.
			if(this.programacionAreasDTO.getArea().getId() == this.usuarioDTO.getArea().getId())
			{
				if(listaDetalles.size() == 0)
				{
					listaDetalles = detalleProgramacionAreaProductoServiceLocal.buscarDetalleProgramacionAreaProducto(programacionAreasId,null);
					for(DetalleProgramacionAreaProductoDTO lista : listaDetalles)
					{
						this.colNames.add(lista.getProductoDisponible().getDescripcionProducto());
					}
					
				}
				else
				{
					System.out.println("Tiene que ser inicializado en 0.Sino va cargar 2 veces los mismos productos");
				}
				
				
			}				
			
		}
		catch (Exception e) 
		{
		System.out.println("Error : "+e.getMessage());
		}
		
	}
	
	//Método que permite inicializar la variable colNames.
	public void resetearProductosEnTabla()
	{
		this.colNames = new ArrayList<String>();				
	}
	
	//Método que permite limpiar la cabecera de fila (area) y las cabeceras de columnas (productos dispnibles)
	public void limpiarCabecerasTabla()
	{
		resetearAreaEnTabla();
		resetearProductosEnTabla();
		//Método para resetear ArregloValoresAreaProducto.
		resetearArregloValoresAreaProducto();
		//Método para resetear requerimientoUsuarioDTO
		this.requerimientoUsuarioDTO = new RequerimientoUsuarioDTO();
	}
	
	//Método para inicializar y setear los valores del arregloValoresAreaProducto a 0.
	public void inicializarArregloValoresAreaProducto()
	{
		int fila = this.rowNames.size(); 
		int columna = this.colNames.size();
		this.arregloValoresAreaProducto = new Integer[fila][columna];
		for(int i=0;i<fila;i++)
		{
			for(int j=0;j<columna;j++)
			{
				arregloValoresAreaProducto[i][j]=0;
			}
		}
		
	}
	
	//Método para setear a 0 los valores del arregloValoresAreaProducto.
	public void resetearArregloValoresAreaProducto()
	{
		//Es similar al método para inicializar.
		this.inicializarArregloValoresAreaProducto();
		
	}
	
	
	
	//Método que permite abrir un popup para poder actualizar un requerimiento de usuario.
	public void actualizarRequerimientoUsuario(Integer posicion) throws Exception
	{
	RequerimientoUsuarioDTO objeto= this.listaRequerimientoUsuario.get(posicion);
	
	if(objeto.getIndicadorEnviadoAlEncargado() != 1)
	{
		System.out.println("Sí se puede actualizar.");
		obtenerListaMesDelRequerimientoUsuario(objeto.getFechaRegistro());
		//obtenerListaMesActual();		
		//Método para cargar el id de la programacion area, para luego traer los productos disponibles.
//		Integer idProgramacion =this.programacionDTO.getId();
		Integer idProgramacion =objeto.getProgramacionAreas().getProgramacion().getId();
		Integer idArea = this.usuarioDTO.getArea().getId();
		cargarProgramacionAreas(idProgramacion,idArea);
		//Método pra cargar el area en la tabla
		cargarAreaEnTabla();
		//Método para cargar los productos pasándole como parámetro la programacionAreaId.
		Integer idProgramacionArea = this.programacionAreasDTO.getId();
		cargarProductosEnTabla(idProgramacionArea);
		//Método para inicializar los valores del ArregloValoresAreaProducto.
		inicializarArregloValoresAreaProducto();
		//Método para cargar los valores de la cantidad de cada producto seleccionado desde la BD.
		this.posicion = posicion;
		//Se pasa el id del usuario como parámetro, desde el usuarioDTO de clase
		Integer usuarioId = this.usuarioDTO.getId();
		cargarValoresAreaProductoDesdeBD(idProgramacionArea,usuarioId);
		context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogoActualizarRequerimientoUsuario').show();");		
	}
	else
	{		
		System.out.println("No se puede actualizar porque el requerimiento ha sido enviado al encargado de área.");
		FacesContext.getCurrentInstance().addMessage("Message2", 
				new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error!", 
						"No se puede actualizar el requerimiento N°"+objeto.getId()+
						" del usuario :"+objeto.getUsuario().getDescripcionUsuario()+
						" porque el requerimiento ya ha sido enviado al encargado de área.")						
				);
		//errorRequerimientoYaSeHaEnviado();	
		
		
	}
		
	}
	
	//Prueba de método para mostrar el mensaje de error que no se puede actualizar si el requerimiento de usuario ya se ha enviado
	public void errorRequerimientoYaSeHaEnviado()
	{
		FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",
        "No se puede actualizar porque el requerimiento ha sido enviado al encargado de área."));
	}
	
	
	
	//Prueba para abrir popup actualizarRequerimeintoUsuario desde el formulario agregarRequerimientoUsuario
	public void abrirPopupActualizarRequerimientoUsuarioDesdeAgregarRequerimientoUsuario(RequerimientoUsuarioDTO requerimientoUsuarioDTO) throws Exception
	{		
		this.requerimientoUsuarioDTO = requerimientoUsuarioDTO;
		//Se pasa el id del usuario como parámetro.
		Integer usuarioId = this.requerimientoUsuarioDTO.getUsuario().getId();
		obtenerListaMesActual();
		//Método para cargar el id de la programacion area, para luego traer los productos disponibles.
		Integer idProgramacion =this.programacionDTO.getId();
		Integer idArea = this.usuarioDTO.getArea().getId();
		cargarProgramacionAreas(idProgramacion,idArea);
		//Método pra cargar el area en la tabla
		cargarAreaEnTabla();
		//Método para cargar los productos pasándole como parámetro la programacionAreaId.
		Integer idProgramacionArea = this.programacionAreasDTO.getId();
		cargarProductosEnTabla(idProgramacionArea);
		//Método para inicializar los valores del ArregloValoresAreaProducto.
		inicializarArregloValoresAreaProducto();
		//Método para cargar los valores de la cantidad de cada producto seleccionado desde la BD.
		//Asigno 0 a la posicion porque solo se va evaluar 1 objeto de la lista requerimientoUsuario de cada usuario,
		//Por ello el indice 0 indica el 1er y unico objeto.
		this.posicion = 0;
		cargarValoresAreaProductoDesdeBD(idProgramacionArea,usuarioId);		
	}
	
	
	
	//Método para cargar valores de la cantidad de productos desde la BD.
	public void cargarValoresAreaProductoDesdeBD(Integer programacionAreasId,Integer usuarioId) throws Exception
	{
		List<DetalleProgramacionAreaProductoDTO> listaDetalles = new ArrayList<DetalleProgramacionAreaProductoDTO>();
		List<DetalleRequerimientoUsuarioProductoDTO>listaRequerimientoUsuarioProducto =new ArrayList<DetalleRequerimientoUsuarioProductoDTO>();
		//Se pasa el id del usuario como parámetro.
//		Integer usuarioId = this.usuarioDTO.getId();
		//Integer programacionAreaId = this.programacionAreasDTO.getId();
		List<RequerimientoUsuarioDTO>listaRequerimientoUsuario =requerimientoUsuarioServiceLocal.buscarRequerimientoUsuarioDTO(null, null, usuarioId, programacionAreasId, null,null,null); 
		Integer requerimientoUsuarioId = listaRequerimientoUsuario.get(this.posicion.intValue()).getId(); 
		try 
		{
			listaDetalles = this.detalleProgramacionAreaProductoServiceLocal.buscarDetalleProgramacionAreaProducto(programacionAreasId, null);
			listaRequerimientoUsuarioProducto = this.detalleRequerimientoUsuarioProductoServiceLocal.buscarDetalleRequerimientoUsuarioProducto(null, requerimientoUsuarioId, null);
			//Comparamos si el area de la programacionarea es la misma que el area en la que está el usuario.
			if(this.programacionAreasDTO.getArea().getId() == this.usuarioDTO.getArea().getId())			{
				if(listaDetalles!=null &&  listaDetalles.size()>=0)
				{
					int fila = 0;
					int columna = 0;					
					for (DetalleRequerimientoUsuarioProductoDTO objeto: listaRequerimientoUsuarioProducto) 
					{						
						for(DetalleProgramacionAreaProductoDTO programacionAreaProducto : listaDetalles)
						{
							if(objeto.getProducto().getId() == programacionAreaProducto.getProductoDisponible().getId())
							{
								//Setea el valor de la cantidad almacenada por el usuario.
								this.arregloValoresAreaProducto[fila][columna] = objeto.getCantidad();
								columna++;
								break;
								
							}
							else
							{								
								System.out.println("El orden es diferente.");
							}
						}
					}
					
				}				
				else
				{
					System.out.println("Es nulo, tiene 0 elementos.");
				}
			}
			else
			{
				System.out.println("Las áreas son diferentes.");
			}			
		}
		catch (Exception e) 
		{
		
		}
		
	}
	
	//Prueba para actualizar la cantidad de un producto disponible para el usuario.
	public void compararParaActualizarDetalleRequerimientoUsuarioProducto(Integer programacionAreasId,Integer usuarioId) throws Exception
	{
		//Lista que tiene solo 1 elemento de requerimientoUsuario, para obtener el requerimientoUsuarioId
		List<RequerimientoUsuarioDTO>listaRequerimientoUsuario = this.requerimientoUsuarioServiceLocal.buscarRequerimientoUsuarioDTO(null, null, usuarioId, programacionAreasId, null,null,null);
		//Lista que tiene todos los productos de una programacionArea
		List<DetalleProgramacionAreaProductoDTO>listaDetalleProgramacionAreaProducto = this.detalleProgramacionAreaProductoServiceLocal.buscarDetalleProgramacionAreaProducto(programacionAreasId,null);
		Integer requerimientoUsuarioId = listaRequerimientoUsuario.get(0).getId();
		//Lista que tiene todos los productos del detalleRquerimientoUsuario
		List<DetalleRequerimientoUsuarioProductoDTO>listaDetalleRequerimientoUsuario=this.detalleRequerimientoUsuarioProductoServiceLocal.buscarDetalleRequerimientoUsuarioProducto(null, requerimientoUsuarioId, null);
		try 
		{
			int fila=0;
			int columna=0;
			Integer estadoId=1;
			String usuarioModificacion ="GERALDO";
			for(DetalleProgramacionAreaProductoDTO objeto1: listaDetalleProgramacionAreaProducto)
			{
				for (DetalleRequerimientoUsuarioProductoDTO objeto2: listaDetalleRequerimientoUsuario) 
				{
					
					if(objeto1.getProductoDisponible().getId() == objeto2.getProducto().getId())
					{
						objeto2.setCantidad(arregloValoresAreaProducto[fila][columna]);
						objeto2.setCodigoUsuarioModificacion(usuarioModificacion);
						objeto2.setIpModificacion(obtieneIPCliente());
						objeto2.setEstado(new EstadoDTO());
						objeto2.getEstado().setId(estadoId);
					
						Boolean isActualizo = detalleRequerimientoUsuarioProductoServiceLocal.actualizarDetalleRequerimientoUsuarioProducto(objeto2);
						
						if(isActualizo == true)
						{												
							columna++;
							break;
						}
						else
						{
							System.out.println("No se ha actualizado");
							break;
						}
						
					}
					else
					{
						System.out.println("Los productos son diferentes.");
					}
				}
			}
			
			
		}		
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		}
		
		
	}

	public boolean generarActualizarDetalleRequerimientoUsuarioProducto() throws Exception
	{
		try 
		{		
			Integer programacionAreasId = this.programacionAreasDTO.getId();
			Integer usuarioId = this.usuarioDTO.getId();
			compararParaActualizarDetalleRequerimientoUsuarioProducto(programacionAreasId, usuarioId);			
			return true;
		}
		catch (Exception e) 
		{
			return false;
		}		
		
	}
	
	public void actualizarDetalleRequerimientoUsuarioProducto() throws Exception
	{
		//Método para actualizar el envío al encargado.
		actualizarEnvioAlEncargado(this.posicion);
		generarActualizarDetalleRequerimientoUsuarioProducto();
		boolean seActualizo = generarActualizarDetalleRequerimientoUsuarioProducto();
		if(seActualizo == true)
		{
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Aviso",
					"Se actualizó con éxito"));
			cerrarPopupActualizarDetalleRequerimientoUsuarioProducto();
			cerrarMensajeConfirmacion();							
		}
		else
		{
			System.out.println("No se actualizó");
		}
	}
	
	//Prueba para actualizar el envío del requerimiento hacia el encargado del área.
	public void actualizarEnvioAlEncargado(Integer posicion)
	{
		short indicadorEnviadoAlEncargado = 1;
		java.util.Date fechaJava = new java.util.Date(); 
		java.sql.Date fecha = new java.sql.Date(fechaJava.getTime());
		try 	
		{
			RequerimientoUsuarioDTO objeto = new RequerimientoUsuarioDTO();
			objeto = this.listaRequerimientoUsuario.get(posicion);			
			objeto.setIndicadorEnviadoAlEncargado(indicadorEnviadoAlEncargado);
			objeto.setFechaEnviadoAlEncargado((java.sql.Date) fecha);
			boolean seActualizo = this.requerimientoUsuarioServiceLocal.actualizarRequerimientoUsuario(objeto);
			if(seActualizo == true)
			{
				System.out.println("Se actualizó");
			}
			else
			{
				System.out.println("Error : No se actualizó.");
			}
		}
		catch (Exception e) 
		{
			System.out.println("Error : "+e.getMessage());
			log.error(e.getMessage(), e);
		}		
	}
	
	
	//Prueba pra cerrar popupactualizardetallerequerimientousuarioproducto
	public void cerrarPopupActualizarDetalleRequerimientoUsuarioProducto() 
	{
		context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogoActualizarRequerimientoUsuario').hide();");
	}
	//Prueba para cerrar el popup mensaje de confirmación
	public void cerrarMensajeConfirmacion() 
	{
		context = RequestContext.getCurrentInstance();
		context.execute("PF('popupConfirmar').hide();");
	}
	
	
	public void mensajeConfirmacionNuevoGuardar() 
	{

		context = RequestContext.getCurrentInstance();
		context.execute("PF('popupConfirmarNuevoGuardar').show();");		
		
	}
	
	
	public void mensajeConfirmacionActualizar() 
	{
		
		context = RequestContext.getCurrentInstance();
		context.execute("PF('popupConfirmar').show();");	
		
	}

	
	
	//PRUEBA
	//Método para crear un requerimiento de usuario
	public boolean crearRequerimientoUsuario(Short indicador)
	{
		try 
		{
		
			//Prueba para obtener un requerimientoId.
			generarRequerimiento();
			//Prueba para obtener un requerimientoUsuarioId
			generarRequerimientoUsuario(indicador);
			//Prueba para generar un detalleRequerimientoUsuarioProducto
			generarDetalleRequerimientoUsuario();
			return true;
		} 
		catch (Exception e) 
		{
			log.error(e.getMessage(),e);
		}
		return false;
	}
	
	//Prueba para obtener un requerimientoId.
	//Pasaremos null a los atributos del requerimiento
	public void generarRequerimiento()
	{
		String usuarioRegistro = this.usuarioDTO.getDescripcionUsuario();
		int idRegistrado = 1;
		try 
		{
			this.requerimientoDTO = new RequerimientoDTO();
			this.requerimientoDTO.setRequerimientoNumero("null");
			this.requerimientoDTO.setMontoTotal(0);
			this.requerimientoDTO.setIndicadorAnulado((short)0);
			this.requerimientoDTO.setIndicadorAprobado((short)0);
			this.requerimientoDTO.setFechaAprobacion(null);
			this.requerimientoDTO.setIndicadorActivo((short)1);
			this.requerimientoDTO.setCodigoUsuarioRegistro(usuarioRegistro);
			this.requerimientoDTO.setIpRegistro(obtieneIPCliente());
			this.requerimientoDTO.setEstado(new EstadoDTO());
			this.requerimientoDTO.getEstado().setId(idRegistrado);			
			this.requerimientoDTO = requerimientoServiceLocal.registrarRequerimiento(this.requerimientoDTO);			
									
		}
		catch (Exception e) 
		{
		log.error(e.getMessage(),e);
		}
	}
	
	
	public void generarRequerimientoUsuario(Short indicadorEnviadoAlEncargado) throws Exception
	{
		List<RequerimientoDTO>listaRequerimiento = requerimientoServiceLocal.buscarRequerimiento(null, null);
		//La lista está ordenada de mayor a menor, por eso que el 1er item es el mayor a todos.
		Integer requerimientoId = listaRequerimiento.get(0).getId();
		//Tengo que buscar el id de la tabla TBL_USUARIO_PERFILES
		Integer usuarioId = this.usuarioDTO.getId();
		List<UsuarioPerfilesDTO>listaUsuarioPerfiles = usuarioPerfilesServiceLocal.buscarUsuarioPerfiles(null, usuarioId, null, null);
		Integer usuarioPerfilesId = listaUsuarioPerfiles.get(0).getId();		
		Integer programacionAreaId = this.programacionAreasDTO.getId();
		short indicadorActivo = 1;
		String codigoUsuarioRegistro = this.usuarioDTO.getDescripcionUsuario();
		Integer estadoId = 1;
		try 
		{
			this.requerimientoUsuarioDTO = new RequerimientoUsuarioDTO();
			this.requerimientoUsuarioDTO.setRequerimiento(new RequerimientoDTO());
			this.requerimientoUsuarioDTO.getRequerimiento().setId(requerimientoId);
			this.requerimientoUsuarioDTO.setUsuario(new UsuarioDTO());
			this.requerimientoUsuarioDTO.getUsuario().setId(usuarioPerfilesId);
			this.requerimientoUsuarioDTO.setProgramacionAreas(new ProgramacionAreasDTO());			
			this.requerimientoUsuarioDTO.getProgramacionAreas().setId(programacionAreaId);			
			if(indicadorEnviadoAlEncargado == 0)
			{
				this.requerimientoUsuarioDTO.setIndicadorEnviadoAlEncargado(indicadorEnviadoAlEncargado);
				this.requerimientoUsuarioDTO.setFechaEnviadoAlEncargado(null);	
			}
			if(indicadorEnviadoAlEncargado == 1)
			{				
//				String fechaStr = obtenerFechaActualStr();
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				//Se pasa el parámetro 0 en referencia a los milisegundos
				java.util.Date fechaJava = new java.util.Date(); 
				java.sql.Date fecha = new java.sql.Date(fechaJava.getTime());				
								
				this.requerimientoUsuarioDTO.setIndicadorEnviadoAlEncargado(indicadorEnviadoAlEncargado);
				this.requerimientoUsuarioDTO.setFechaEnviadoAlEncargado((java.sql.Date) fecha);
				System.out.println(fecha.toString());
			}			
			
			this.requerimientoUsuarioDTO.setIndicadorRequerimientoRecibidoDelEncargado((short)0);
			this.requerimientoUsuarioDTO.setIndicadorActivo(indicadorActivo);
			this.requerimientoUsuarioDTO.setCodigoUsuarioRegistro(codigoUsuarioRegistro);
			this.requerimientoUsuarioDTO.setIpRegistro(obtieneIPCliente());
			this.requerimientoUsuarioDTO.setEstado(new EstadoDTO());
			this.requerimientoUsuarioDTO.getEstado().setId(estadoId);
			
			this.requerimientoUsuarioDTO = this.requerimientoUsuarioServiceLocal.registrarRequerimientoUsuarioDTO(this.requerimientoUsuarioDTO);
			
			
			
		}
		catch (Exception e) 
		{
			System.out.println("ERROR : "+e.getMessage());
		}
	}
	
	
	public void generarDetalleRequerimientoUsuario() throws Exception
	{
		Integer usuarioId = this.usuarioDTO.getId();
		Integer programacionAreasId = this.programacionAreasDTO.getId();
		List<RequerimientoUsuarioDTO>listaRequerimientoUsuario = this.requerimientoUsuarioServiceLocal.buscarRequerimientoUsuarioDTO(null, null, usuarioId, programacionAreasId, null,null,null);
		Integer requerimientoUsuarioId = listaRequerimientoUsuario.get(0).getId(); 
		List<DetalleProgramacionAreaProductoDTO>listaDetalleProgramacionAreaProducto= this.detalleProgramacionAreaProductoServiceLocal.buscarDetalleProgramacionAreaProducto(programacionAreasId, null);
		int fila = 0;
		int columna = 0;
		String usuarioRegistro = this.usuarioDTO.getDescripcionUsuario();
		int estadoRegistrado = 1;
		try 
		{
			for (DetalleProgramacionAreaProductoDTO lista : listaDetalleProgramacionAreaProducto) 
			{
				Integer productoId = lista.getProductoDisponible().getId();
				DetalleRequerimientoUsuarioProductoDTO objeto = new DetalleRequerimientoUsuarioProductoDTO();
				objeto.setRequerimientoUsuario(new RequerimientoUsuarioDTO());
				objeto.getRequerimientoUsuario().setId(requerimientoUsuarioId);
				objeto.setProducto(new ProductoDTO());
				objeto.getProducto().setId(productoId);
				objeto.setCantidad(this.arregloValoresAreaProducto[fila][columna]);
				objeto.setIndicadorActivo((short)1);
				objeto.setCodigoUsuarioRegistro(usuarioRegistro);
				objeto.setIpRegistro(obtieneIPCliente());
				objeto.setEstado(new EstadoDTO());
				objeto.getEstado().setId(estadoRegistrado);
				objeto = this.detalleRequerimientoUsuarioProductoServiceLocal.registrarDetalleRequerimientoUsuarioProducto(objeto);
				columna++;			
			}
		
			System.out.println("Se registraro los detalles del requerimiento de usuario.");

			
		} 
		catch (Exception e) 
		{
		
		}
		
		
	}
	
	//Prueba
	//Método para registrar un detalle requerimiento usuario producto.
	public void registrarRequerimientoUsuarioProducto(Integer valor)
	{
		short indicador = valor.shortValue();
		try 
		{			
			boolean seRegistro = crearRequerimientoUsuario(indicador);
			if(seRegistro == true)
			{				
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Aviso",
						"Se registró con éxito"));
				cerrarPopupRegistrarDetalleRequerimientoUsuarioProducto();
				if(valor == 0)
				{
					cerrarMensajeConfirmacionGuardar();	
				}
				else
				{
					cerrarMensajeConfirmacionRegistro();	
				}
			}
			else
			{
				System.out.println("No se pudo registrar");
			}
			
		}
		catch (Exception e) 
		{
		log.error(e.getMessage(),e);
		}		
		
	}
	

	//Prueba pra cerrar popupRegistrardetallerequerimientousuarioproducto
	public void cerrarPopupRegistrarDetalleRequerimientoUsuarioProducto() 
	{
		context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogoNuevoRequerimientoUsuario').hide();");
	}
	//Prueba para cerrar el popup mensaje de confirmación registro
	public void cerrarMensajeConfirmacionGuardar() 
	{
		context = RequestContext.getCurrentInstance();
		context.execute("PF('popupConfirmarNuevoGuardar').hide();");
	}
	
	//Prueba para cerrar el popup mensaje de confirmación guardar
	public void cerrarMensajeConfirmacionRegistro() 
	{
		context = RequestContext.getCurrentInstance();
		context.execute("PF('popupConfirmarNuevoRegistrar').hide();");
	}

	
		
	public void mensajeConfirmacionNuevoRegistrar() 
	{

		context = RequestContext.getCurrentInstance();
		context.execute("PF('popupConfirmarNuevoRegistrar').show();");		
		
	}

	
	
	
	
	
	
	
	
	
	
	//METODOS DE ACCESO

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public RequerimientoUsuarioServiceLocal getRequerimientoUsuarioServiceLocal() {
		return requerimientoUsuarioServiceLocal;
	}

	public void setRequerimientoUsuarioServiceLocal(
			RequerimientoUsuarioServiceLocal requerimientoUsuarioServiceLocal) {
		this.requerimientoUsuarioServiceLocal = requerimientoUsuarioServiceLocal;
	}

	public RequestContext getContext() {
		return context;
	}

	public void setContext(RequestContext context) {
		this.context = context;
	}

	public List<SelectItem> getListaEstado() {
		return listaEstado;
	}

	public void setListaEstado(List<SelectItem> listaEstado) {
		this.listaEstado = listaEstado;
	}

	public Integer getIdentificadorRequerimientoUsuario() {
		return identificadorRequerimientoUsuario;
	}

	public void setIdentificadorRequerimientoUsuario(
			Integer identificadorRequerimientoUsuario) {
		this.identificadorRequerimientoUsuario = identificadorRequerimientoUsuario;
	}

	public Integer getIdentificadorEstado() {
		return identificadorEstado;
	}

	public void setIdentificadorEstado(Integer identificadorEstado) {
		this.identificadorEstado = identificadorEstado;
	}

	
	
	public List<RequerimientoUsuarioDTO> getListaRequerimientoUsuario() {
		return listaRequerimientoUsuario;
	}

	public void setListaRequerimientoUsuario(
			List<RequerimientoUsuarioDTO> listaRequerimientoUsuario) {
		this.listaRequerimientoUsuario = listaRequerimientoUsuario;
	}

	public Integer getPosicion() {
		return posicion;
	}

	public void setPosicion(Integer posicion) {
		this.posicion = posicion;
	}

	public RequerimientoUsuarioDTO getRequerimientoUsuarioDTO() {
		return requerimientoUsuarioDTO;
	}

	public void setRequerimientoUsuarioDTO(
			RequerimientoUsuarioDTO requerimientoUsuarioDTO) {
		this.requerimientoUsuarioDTO = requerimientoUsuarioDTO;
	}

	public List<SelectItem> getListaEstadoDetalleRequerimiento() {
		return listaEstadoDetalleRequerimiento;
	}

	public void setListaEstadoDetalleRequerimiento(
			List<SelectItem> listaEstadoDetalleRequerimiento) {
		this.listaEstadoDetalleRequerimiento = listaEstadoDetalleRequerimiento;
	}

	public List<SelectItem> getListaIndicadorEnvioAlEncargado() {
		return listaIndicadorEnvioAlEncargado;
	}

	public void setListaIndicadorEnvioAlEncargado(
			List<SelectItem> listaIndicadorEnvioAlEncargado) {
		this.listaIndicadorEnvioAlEncargado = listaIndicadorEnvioAlEncargado;
	}

	public List<SelectItem> getListaIndicadorRecibidoDelEncargado() {
		return listaIndicadorRecibidoDelEncargado;
	}

	public void setListaIndicadorRecibidoDelEncargado(
			List<SelectItem> listaIndicadorRecibidoDelEncargado) {
		this.listaIndicadorRecibidoDelEncargado = listaIndicadorRecibidoDelEncargado;
	}

	public List<SelectItem> getListaIndicadorDetalleRequerimiento() {
		return listaIndicadorDetalleRequerimiento;
	}

	public void setListaIndicadorDetalleRequerimiento(
			List<SelectItem> listaIndicadorDetalleRequerimiento) {
		this.listaIndicadorDetalleRequerimiento = listaIndicadorDetalleRequerimiento;
	}

	public ProgramacionServiceLocal getProgramacionServiceLocal() {
		return programacionServiceLocal;
	}

	public void setProgramacionServiceLocal(
			ProgramacionServiceLocal programacionServiceLocal) {
		this.programacionServiceLocal = programacionServiceLocal;
	}

	public ProgramacionDTO getProgramacionDTO() {
		return programacionDTO;
	}

	public void setProgramacionDTO(ProgramacionDTO programacionDTO) {
		this.programacionDTO = programacionDTO;
	}

	public UsuarioServiceLocal getUsuarioServiceLocal() {
		return usuarioServiceLocal;
	}

	public void setUsuarioServiceLocal(UsuarioServiceLocal usuarioServiceLocal) {
		this.usuarioServiceLocal = usuarioServiceLocal;
	}

	public UsuarioDTO getUsuarioDTO() {
		return usuarioDTO;
	}

	public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

	public ProgramacionAreasServiceLocal getProgramacionAreasServiceLocal() {
		return programacionAreasServiceLocal;
	}

	public void setProgramacionAreasServiceLocal(
			ProgramacionAreasServiceLocal programacionAreasServiceLocal) {
		this.programacionAreasServiceLocal = programacionAreasServiceLocal;
	}

	public ProgramacionAreasDTO getProgramacionAreasDTO() {
		return programacionAreasDTO;
	}

	public void setProgramacionAreasDTO(ProgramacionAreasDTO programacionAreasDTO) {
		this.programacionAreasDTO = programacionAreasDTO;
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

	public DetalleProgramacionAreaProductoServiceLocal getDetalleProgramacionAreaProductoServiceLocal() {
		return detalleProgramacionAreaProductoServiceLocal;
	}

	public void setDetalleProgramacionAreaProductoServiceLocal(
			DetalleProgramacionAreaProductoServiceLocal detalleProgramacionAreaProductoServiceLocal) {
		this.detalleProgramacionAreaProductoServiceLocal = detalleProgramacionAreaProductoServiceLocal;
	}

	public Integer[][] getArregloValoresAreaProducto() {
		return arregloValoresAreaProducto;
	}

	public void setArregloValoresAreaProducto(Integer[][] arregloValoresAreaProducto) {
		this.arregloValoresAreaProducto = arregloValoresAreaProducto;
	}

	public DetalleRequerimientoUsuarioProductoServiceLocal getDetalleRequerimientoUsuarioProductoServiceLocal() {
		return detalleRequerimientoUsuarioProductoServiceLocal;
	}

	public void setDetalleRequerimientoUsuarioProductoServiceLocal(
			DetalleRequerimientoUsuarioProductoServiceLocal detalleRequerimientoUsuarioProductoServiceLocal) {
		this.detalleRequerimientoUsuarioProductoServiceLocal = detalleRequerimientoUsuarioProductoServiceLocal;
	}

	public DetalleRequerimientoUsuarioProductoDTO getDetalleRequerimientoUsuarioProductoDTO() {
		return detalleRequerimientoUsuarioProductoDTO;
	}

	public void setDetalleRequerimientoUsuarioProductoDTO(
			DetalleRequerimientoUsuarioProductoDTO detalleRequerimientoUsuarioProductoDTO) {
		this.detalleRequerimientoUsuarioProductoDTO = detalleRequerimientoUsuarioProductoDTO;
	}

	public RequerimientoServiceLocal getRequerimientoServiceLocal() {
		return requerimientoServiceLocal;
	}

	public void setRequerimientoServiceLocal(
			RequerimientoServiceLocal requerimientoServiceLocal) {
		this.requerimientoServiceLocal = requerimientoServiceLocal;
	}

	public RequerimientoDTO getRequerimientoDTO() {
		return requerimientoDTO;
	}

	public void setRequerimientoDTO(RequerimientoDTO requerimientoDTO) {
		this.requerimientoDTO = requerimientoDTO;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public UsuarioPerfilesServiceLocal getUsuarioPerfilesServiceLocal() {
		return usuarioPerfilesServiceLocal;
	}

	public void setUsuarioPerfilesServiceLocal(
			UsuarioPerfilesServiceLocal usuarioPerfilesServiceLocal) {
		this.usuarioPerfilesServiceLocal = usuarioPerfilesServiceLocal;
	}

	public LoginUsuarioBean getLoginUsuarioBean() {
		return loginUsuarioBean;
	}

	public void setLoginUsuarioBean(LoginUsuarioBean loginUsuarioBean) {
		this.loginUsuarioBean = loginUsuarioBean;
	}
	
	

	

}
