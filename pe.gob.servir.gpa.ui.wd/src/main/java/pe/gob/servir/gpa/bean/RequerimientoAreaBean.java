package pe.gob.servir.gpa.bean;


import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import pe.gob.servir.gpa.model.dto.AreaDTO;
import pe.gob.servir.gpa.model.dto.DetalleEncargadoAlmacenDTO;
import pe.gob.servir.gpa.model.dto.DetalleJefeAlmacenDTO;
import pe.gob.servir.gpa.model.dto.DetalleProgramacionAreaProductoDTO;
import pe.gob.servir.gpa.model.dto.DetalleRequerimientoUsuarioProductoDTO;
import pe.gob.servir.gpa.model.dto.EstadoDTO;
import pe.gob.servir.gpa.model.dto.ProgramacionAreasDTO;
import pe.gob.servir.gpa.model.dto.ProgramacionDTO;
import pe.gob.servir.gpa.model.dto.RequerimientoAreaDTO;
import pe.gob.servir.gpa.model.dto.RequerimientoDTO;
import pe.gob.servir.gpa.model.dto.RequerimientoJefeAprobacionDTO;
import pe.gob.servir.gpa.model.dto.RequerimientoUsuarioDTO;
import pe.gob.servir.gpa.model.dto.TipoDTO;
import pe.gob.servir.gpa.model.dto.UsuarioDTO;
import pe.gob.servir.gpa.model.dto.UsuarioPerfilesDTO;
import pe.gob.servir.gpa.service.ejb.AreaServiceLocal;
import pe.gob.servir.gpa.service.ejb.DetalleEncargadoAlmacenServiceLocal;
import pe.gob.servir.gpa.service.ejb.DetalleJefeAlmacenServiceLocal;
import pe.gob.servir.gpa.service.ejb.DetalleProgramacionAreaProductoServiceLocal;
import pe.gob.servir.gpa.service.ejb.DetalleRequerimientoUsuarioProductoServiceLocal;
import pe.gob.servir.gpa.service.ejb.ProgramacionAreasServiceLocal;
import pe.gob.servir.gpa.service.ejb.ProgramacionServiceLocal;
import pe.gob.servir.gpa.service.ejb.RequerimientoAreaServiceLocal;
import pe.gob.servir.gpa.service.ejb.RequerimientoJefeAprobacionServiceLocal;
import pe.gob.servir.gpa.service.ejb.RequerimientoUsuarioServiceLocal;
import pe.gob.servir.gpa.service.ejb.UsuarioPerfilesServiceLocal;
import pe.gob.servir.gpa.service.ejb.UsuarioServiceLocal;
import pe.gob.servir.gpa.util.ConstanteWeb;
import pe.gob.servir.gpa.util.UBaseManage;

@ManagedBean(name = "requerimientoAreaBean")
@ViewScoped
public class RequerimientoAreaBean extends UBaseManage implements Serializable 
{

	private static final long serialVersionUID = 3178598440564336168L;

	/** permite capturar los erroes de ejecucion **/
	private Logger log = Logger.getLogger(TipoBean.class);

	public RequerimientoAreaBean()
	{
		
	}
	
	@EJB
	private RequerimientoAreaServiceLocal requerimientoAreaServiceLocal;
	@EJB
	private RequerimientoUsuarioServiceLocal requerimientoUsuarioServiceLocal;
	@EJB
	private UsuarioServiceLocal usuarioServiceLocal;
	@EJB
	private ProgramacionServiceLocal programacionServiceLocal;
	@EJB
	private AreaServiceLocal areaServiceLocal;
	@EJB
	private ProgramacionAreasServiceLocal programacionAreaServiceLocal;
	@EJB
	private DetalleProgramacionAreaProductoServiceLocal detalleProgramacionAreaProductoServiceLocal;
	@EJB
	private DetalleRequerimientoUsuarioProductoServiceLocal detalleRequerimientoUsuarioProductoServiceLocal;
	@EJB
	private RequerimientoJefeAprobacionServiceLocal requerimientoJefeAprobacionServiceLocal; 
	@EJB
	private UsuarioPerfilesServiceLocal usuarioPerfilesServiceLocal;
	
	
	/** Variable contexto */
	private RequestContext context;
	
	private Integer requerimientoAreaId;
	private Integer areaId;
	private Short indicadorEnvioAlJefe;
	private Short indicadorRecepcionDelJefe;
	private Integer programacionId;
	
	//VARIABLES 
	private List<RequerimientoAreaDTO>listaRequerimientoArea;
	private Integer posicion;
	private RequerimientoAreaDTO requerimientoAreaDTO;
	private List<SelectItem> listaIndicadorEnviadoAlJefe;
	private List<SelectItem> listaIndicadorRecepcionDelJefe;
	private List<SelectItem> listaEstado;
	private UsuarioDTO usuarioDTO;
	private ProgramacionDTO programacionDTO; 
	private List<String> rowNames; 
	private AreaDTO areaDTO;
	private List<String> rowUsuarios;
	private List<String> colNames;
	private ProgramacionAreasDTO programacionAreasDTO;
	private Integer arregloValoresAreaProducto[][];
	private Integer arregloValoresRequerimientoUsuarios[][];
	private RequerimientoUsuarioDTO requerimientoUsuarioDTO;
	@ManagedProperty("#{requerimientoUsuarioBean}")
	private RequerimientoUsuarioBean requerimientoUsuarioBean;
	private List<RequerimientoAreaDTO>listaRequerimientoAreaJefe;	
	private RequerimientoJefeAprobacionDTO requerimientoJefeAprobacionDTO;
	private boolean seAproboRequerimientoArea;
	private List<SelectItem> listaIndicadorRecepcionEncargadoDelArea;
	private List<SelectItem> listaIndicadorEnviadoAlJefeDelAlmacen;
	private List<SelectItem> listaIndicadorRecepcionDelJefeDelAlmacen;
	private List<SelectItem> listaIndicadorEnvioAlEncargadoDelAlmacen;
	@ManagedProperty("#{programacionBean}")
	private ProgramacionBean programacionBean;	
	private ProgramacionDTO programacionSeleccionAlmacen;
	private List<RequerimientoAreaDTO>listaRequerimientoAreaAprobados;
	private List<RequerimientoAreaDTO>listaRequerimientoParaJefeAlmacen;
	private UsuarioPerfilesDTO usuarioPerfilesDTO;
	private Integer indexProgramacion;
	private DetalleJefeAlmacenDTO detalleJefeAlmacenDTO;
	@EJB
	private DetalleJefeAlmacenServiceLocal detalleJefeAlmacenServiceLocal;
	private boolean paraDerivarAlEncargadoDeAlmacen;
	private String avisoDerivado ;	
	private DetalleEncargadoAlmacenDTO detalleEncargadoAlmacenDTO;
	@EJB
	private DetalleEncargadoAlmacenServiceLocal detalleEncargadoAlmacenServiceLocal;
	private Integer posicionSeleccionRequerimeintoAreaEncargadoAlmacen;
	@ManagedProperty("#{loginUsuarioBean}")
	private LoginUsuarioBean loginUsuarioBean;
	
	
	
	 @PostConstruct
	 public void inicializando()
	 {
	 inicializarVariables();
	 buscarRequerimientoArea();
	 //Prueba para buscar los requerimientos enviados al jefe.
	 buscarRequerimientoAreaJefe();
	 //Cargamos el usuario. Usuario -- > id= 1 , desc = primer usuario , clave =123
//	 cargarUsuario(1, "primer usuario", "123");
	 cargarUsuario(this.loginUsuarioBean.getUsuarioDTO().getId(), null, null);
		 
	 }

	 //Método para inicalizar las variables de búsqueda.
	 public void inicializarVariables()
	 {
	 this.requerimientoAreaId=null;
	 this.indicadorEnvioAlJefe = null;
	 this.indicadorRecepcionDelJefe = null;
	 this.areaId = null;
	 this.programacionId = null;
	 this.requerimientoAreaDTO = new RequerimientoAreaDTO();
	 this.posicion = null;
	 this.listaRequerimientoArea = new ArrayList<>();
	 this.rowNames = new ArrayList<String>();
	 this.colNames = new ArrayList<String>();
	 this.rowUsuarios = new ArrayList<String>();
	 this.listaRequerimientoAreaJefe = new ArrayList<>();
	 this.requerimientoJefeAprobacionDTO = new RequerimientoJefeAprobacionDTO();
	 this.seAproboRequerimientoArea = true;
	 this.programacionSeleccionAlmacen = new ProgramacionDTO();
	 this.listaRequerimientoAreaAprobados = new ArrayList<>();
	 this.listaRequerimientoParaJefeAlmacen = new ArrayList<>();
	 this.indexProgramacion = null;
	 this.detalleJefeAlmacenDTO = new DetalleJefeAlmacenDTO();
	 this.avisoDerivado= "";
	 this.detalleEncargadoAlmacenDTO= new DetalleEncargadoAlmacenDTO(); 
	 }
	 
	 //Método para limpiar e inicializar las variables
	 public void limpiar()
	 {
		 inicializarVariables();
	 }
	
	 //Método para asignarle valor null a las variables que se incializan en cero, con ello ya se puede buscar.
	 public void compararVariablesANull()
	 {
		 if(this.requerimientoAreaId!=null && this.requerimientoAreaId == 0)
		 {
			 this.requerimientoAreaId =null;			 
		 }
		 if(this.indicadorEnvioAlJefe!=null && this.indicadorEnvioAlJefe == 0)
		 {
			 this.indicadorEnvioAlJefe = null;			 
		 }
		 
		 if(this.indicadorRecepcionDelJefe!=null &&  this.indicadorRecepcionDelJefe  == 0)
		 {
			 this.indicadorRecepcionDelJefe = null;			 
		 }
		 
		 if(this.areaId!=null &&  this.areaId  == 0)
		 {
			 this.areaId = null;			 
		 }
		 
		 if(this.programacionId!=null && this.programacionId == 0)
		 {
			 this.programacionId = null; 
		 }
		 
	 }
	 
	 
	 //Prueba de método para buscar los requerimientos de área.
	 public void buscarRequerimientoArea()
	 {		 
		 compararVariablesANull();
		 try 
		 {	
			 //Prueba para obtener el areaId en función del usuario	
			 cargarUsuario(this.loginUsuarioBean.getUsuarioDTO().getId(),null ,null);
			 Integer usuarioId = this.usuarioDTO.getId();
			 this.usuarioPerfilesDTO = new UsuarioPerfilesDTO();
			 List<UsuarioPerfilesDTO>listaUsuarioPerfiles = new ArrayList<>();
			 listaUsuarioPerfiles =this.usuarioPerfilesServiceLocal.buscarUsuarioPerfiles(null, usuarioId, null, null);
			 this.usuarioPerfilesDTO = listaUsuarioPerfiles.get(0); 
			 Integer usuarioPerfilId = this.usuarioPerfilesDTO.getPerfil().getId();
			 
			 if(usuarioPerfilId == 3 || usuarioPerfilId==4)
			 {
				 Integer areaId = this.usuarioDTO.getArea().getId();
				 this.listaRequerimientoArea = new ArrayList<RequerimientoAreaDTO>();		 
				 if(requerimientoAreaId ==null && 
				    indicadorEnvioAlJefe == null && 
				    indicadorRecepcionDelJefe ==null && 
				    areaId == null)
				 {
						 List<RequerimientoAreaDTO>listaReal = requerimientoAreaServiceLocal.buscarRequerimientoArea(null, null, null, null, null);
						 for (RequerimientoAreaDTO requerimientoAreaDTO : listaReal) 
						 {
							 if(requerimientoAreaDTO.getId()>=0 && requerimientoAreaDTO.getId()!=null && requerimientoAreaDTO.getArea().getId() == areaId)
							 {
								 this.listaRequerimientoArea.add(requerimientoAreaDTO);
							 }
							 else
							 {
								 System.out.println("No se pudo agregar el objeto.");
							 }
						 }				
				 }
				 else
				 {			 
					 this.listaRequerimientoArea = requerimientoAreaServiceLocal.buscarRequerimientoArea(requerimientoAreaId, areaId, indicadorEnvioAlJefe, indicadorRecepcionDelJefe,programacionId);
				 }			

			 }
			 else
			 {
				 System.out.println("El usuario no puede ver los requerimeinto de área porque no es encargado ni jefe.");
			 }
			 
			 
				 	 
				 
				 
				 
		 } 
		 catch (Exception e) 
		 {
			 System.out.println("Error : "+e.getMessage());
		 }
	 }

	 //Prueba de método para abrir el popup que presenta los detalles del requerimiento del área.
	 public void abrirPopupDetallesRequerimientoArea(Integer posicion)
	 {
		 obtenerDatosRequerimientoArea(posicion);
		 generarListaIndicadorEnviadoAlJefe();
		 generarListaIndicadorRecepcionDelJefe();
		 generarListaEstado();	   	 	   	 
		 this.posicion = posicion;
		 context = RequestContext.getCurrentInstance();
		 context.execute("PF('dialogoDetalleRequerimientoArea').show();");
		 
		 
	 }
	 
	 
	 //Prueba para cargar el combo del indicadorEstado
	 public void generarListaEstado() 
		{
			listaEstado= new ArrayList<>();
			listaEstado.add(new SelectItem(null, ConstanteWeb.CAMPO_SELECCIONE));
			listaEstado.add(new SelectItem((Integer)ConstanteWeb.VALOR_REGISTRADO,ConstanteWeb.CAMPO_REGISTRADO));		
		}

	 
	 //Prueba para cargar el combo de indicadorEnviadoAlJefe
	 public void generarListaIndicadorEnviadoAlJefe()
	 {
		listaIndicadorEnviadoAlJefe= new ArrayList<>();
		listaIndicadorEnviadoAlJefe.add(new SelectItem(null,ConstanteWeb.CAMPO_SELECCIONE));		
		listaIndicadorEnviadoAlJefe.add(new SelectItem((Integer)ConstanteWeb.REQ_AREA_VALOR_NO_ENVIADO,ConstanteWeb.REQ_AREA_CAMPO_NO_ENVIADO));
		listaIndicadorEnviadoAlJefe.add(new SelectItem((Integer)ConstanteWeb.REQ_AREA_VALOR_ENVIADO,ConstanteWeb.REQ_AREA_CAMPO_ENVIADO));
	 }
	 
	 //Prueba para cargar el combo de indicadorRecepcionDelJefe
	 public void generarListaIndicadorRecepcionDelJefe()
	 {
		listaIndicadorRecepcionDelJefe = new ArrayList<>();
		listaIndicadorRecepcionDelJefe.add(new SelectItem(null,ConstanteWeb.CAMPO_SELECCIONE));
		listaIndicadorRecepcionDelJefe.add(new SelectItem((Integer)ConstanteWeb.REQ_AREA_VALOR_NO_RECEPCIONADO,ConstanteWeb.REQ_AREA_CAMPO_NO_RECEPCIONADO));
		listaIndicadorRecepcionDelJefe.add(new SelectItem((Integer)ConstanteWeb.REQ_AREA_VALOR_RECEPCIONADO,ConstanteWeb.REQ_AREA_CAMPO_RECEPCIONADO));
		listaIndicadorRecepcionDelJefe.add(new SelectItem((Integer)ConstanteWeb.REQ_AREA_VALOR_RECHAZADO,ConstanteWeb.REQ_AREA_CAMPO_RECHAZADO));
		listaIndicadorRecepcionDelJefe.add(new SelectItem((Integer)ConstanteWeb.REQ_AREA_VALOR_APROBADO,ConstanteWeb.REQ_AREA_CAMPO_APROBADO));		
	 }
	 
	 
	 //Prueba para obtener todos los datos del requerimiento de área.
	 public void obtenerDatosRequerimientoArea(Integer posicion)
	 {
	 RequerimientoAreaDTO objteo = this.listaRequerimientoArea.get(posicion.intValue());
	 this.requerimientoAreaDTO = new RequerimientoAreaDTO();
	 this.requerimientoAreaDTO.setId(objteo.getId());
	 this.requerimientoAreaDTO.setIndicadorEnviadoAlJefe(objteo.getIndicadorEnviadoAlJefe());
	 this.requerimientoAreaDTO.setFechaEnviadoAlJefe(objteo.getFechaEnviadoAlJefe());
	 this.requerimientoAreaDTO.setIndicadorRequerimientoRecepcionDelJefe(objteo.getIndicadorRequerimientoRecepcionDelJefe());
	 this.requerimientoAreaDTO.setFechaRequerimientoRecepcionDelJefe(objteo.getFechaRequerimientoRecepcionDelJefe());
	 this.requerimientoAreaDTO.setIndicadorActivo(objteo.getIndicadorActivo());
	 this.requerimientoAreaDTO.setCodigoUsuarioRegistro(objteo.getCodigoUsuarioRegistro());
	 this.requerimientoAreaDTO.setIpRegistro(objteo.getIpRegistro());
	 this.requerimientoAreaDTO.setFechaRegistro(objteo.getFechaRegistro());
	 this.requerimientoAreaDTO.setCodigoUsuarioModificacion(objteo.getCodigoUsuarioModificacion());
	 this.requerimientoAreaDTO.setIpModificacion(objteo.getIpModificacion());
	 this.requerimientoAreaDTO.setFechaModificacion(objteo.getFechaModificacion());
	 this.requerimientoAreaDTO.setEstado(new EstadoDTO());
	 this.requerimientoAreaDTO.getEstado().setId(objteo.getEstado().getId());
	 this.requerimientoAreaDTO.setArea(new AreaDTO());
	 this.requerimientoAreaDTO.getArea().setId(objteo.getArea().getId());
	 this.requerimientoAreaDTO.getArea().setDescripcionArea(objteo.getArea().getDescripcionArea());
	 this.requerimientoAreaDTO.setProgramacion(new ProgramacionDTO());
	 this.requerimientoAreaDTO.getProgramacion().setId(objteo.getProgramacion().getId());
	 this.requerimientoAreaDTO.getProgramacion().setDescripcionProgramacion(objteo.getProgramacion().getDescripcionProgramacion());
	 this.requerimientoAreaDTO.getEstado().setDescripcionEstado(objteo.getEstado().getDescripcionEstado());
	 this.requerimientoAreaDTO.getArea().setDescripcionArea(objteo.getArea().getDescripcionArea());
	 this.requerimientoAreaDTO.getProgramacion().setDescripcionProgramacion(objteo.getProgramacion().getDescripcionProgramacion());
	 this.requerimientoAreaDTO.getProgramacion().setTipoPedido(new TipoDTO());
	 this.requerimientoAreaDTO.getProgramacion().getTipoPedido().setDescripcionTipo(objteo.getProgramacion().getTipoPedido().getDescripcionTipo());
	 this.requerimientoAreaDTO.getProgramacion().setTipoPeriodo(new TipoDTO());
	 this.requerimientoAreaDTO.getProgramacion().getTipoPeriodo().setDescripcionTipo(objteo.getProgramacion().getTipoPeriodo().getDescripcionTipo());
	 }
	 
	 //Prueba de método para abrir popup nuevorequerimientoarea
	 public void abrirPopupNuevoRequerimientoArea() throws Exception
	{	
		 
	generarListaIndicadorEnviadoAlJefe();
	generarListaIndicadorRecepcionDelJefe();
	generarListaEstado();	   	 	   	 
	inicializarListasParaRegistrarRequerimientoArea();
	
	Integer idProgramacion =this.programacionDTO.getId();
	Integer idArea = this.usuarioDTO.getArea().getId();
	//Prueba para diferenciar usuarios,encargados,jefe
//	Integer usuarioId = this.usuarioDTO.getId();
//	this.usuarioPerfilesDTO = new UsuarioPerfilesDTO();
//	this.usuarioPerfilesDTO = (UsuarioPerfilesDTO) this.usuarioPerfilesServiceLocal.buscarUsuarioPerfiles(null, usuarioId, null, null);
	//4: ENCARGADO DE ÁREA
//	if(this.usuarioPerfilesDTO.getPerfil().getId() == 4)
//	{

		List<RequerimientoAreaDTO>listaCantidadRequerimientoArea = this.requerimientoAreaServiceLocal.buscarRequerimientoArea(null, idArea,null,null, idProgramacion);	
		if(listaCantidadRequerimientoArea.size()>=1)
		{
			System.out.println("No puede tener más de 1 requerimiento de área por programación");
			FacesContext.getCurrentInstance().addMessage("ErrorCantidadRequerimientoArea", 
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR, "Error!", 
							"No puede crear otro requerimiento de área porque " +
							"ya existe un requerimiento de área creado en la programación " +
							"actual : "+this.programacionDTO.getDescripcionProgramacion())						
					);
			//Prueba para limpiar las variables, y que una posteriora apertura del popup 
			//no se duplique los valores.
			limpiarNuevoRequerimientoArea();
		}
		else
		{
			cargarProgramacionAreas(idProgramacion,idArea);
			//Método pra cargar el area en la tabla
			cargarAreaEnTabla();
			//Método para cargar los productos pasándole como parámetro la programacionAreaId.
			Integer idProgramacionArea = this.programacionAreasDTO.getId();
			cargarProductosEnTabla(idProgramacionArea);
			//Método para inicializar los valores del ArregloValoresAreaProducto.
			inicializarArregloValoresAreaProducto();
			//Prueba para traer la cantidad de productos seleccionados desde la BD	
			cargarValoresAreaProductoDesdeBD(idProgramacionArea);
			context = RequestContext.getCurrentInstance();
			context.execute("PF('dialogoNuevoRequerimientoArea').show();");	
		}
		
	}
//	else
//	{
//		System.out.println("El usuario actual no es el encargado del área");
//	}
	
//	}
	
	
	
	 
	//Prueba para traer la cantidad de productos seleccionados desde la BD
	public void cargarValoresAreaProductoDesdeBD(Integer idProgramacionArea)
	{
		List<DetalleProgramacionAreaProductoDTO>listaDetalleProgramacionAreaProducto = new ArrayList<>();		
		try 
		{
			listaDetalleProgramacionAreaProducto = detalleProgramacionAreaProductoServiceLocal.buscarDetalleProgramacionAreaProducto(idProgramacionArea, null);
			int fila = 0;
			int columna = 0;					
			
				for(DetalleProgramacionAreaProductoDTO objeto: listaDetalleProgramacionAreaProducto)
				{
					//Está en orden por ello no se necesita comprobar.				
					if(objeto.getProductoDisponible().getId()!= null)
					{
						//Setea el valor de la cantidad almacenada por el usuario.
						this.arregloValoresAreaProducto[fila][columna] = objeto.getStockDisponible();
						columna++;						
						
					}
					else
					{								
					System.out.println("El orden es diferente.");
					}
				}
			
			
			
		}
		catch (Exception e) 
		{
		
		}
				
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
	 
	 
	//Prueba de método para cargar las programaciones del área.
	 public void cargarProgramacionAreas(Integer idProgramacion,Integer idArea)
	{
		List<ProgramacionAreasDTO>listaProgramacionAreasActual = new ArrayList<ProgramacionAreasDTO>();
		this.programacionAreasDTO = new ProgramacionAreasDTO();
		try 
		{
			listaProgramacionAreasActual = programacionAreaServiceLocal.buscarProgramacionAreas(idProgramacion, idArea);
			for (ProgramacionAreasDTO objeto: listaProgramacionAreasActual) 
			{
				if(objeto.getProgramacion().getId()== this.programacionDTO.getId() && 
				   objeto.getArea().getId() == idArea)
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
	 
	 
	 
	 

	//Prueba de método para inicializar las listas para registrar un requerimiento de área.
	public void inicializarListasParaRegistrarRequerimientoArea()
	{
		cargarProgramacion();
		cargarArea();
		short valorNoEnviado = 0;
		short valorNoRecepcionado = 0;
		Integer paraRegistrar = 1;
		int areaId = this.areaDTO.getId();
		String descripcionArea = this.areaDTO.getDescripcionArea();
		int programacionId = this.programacionDTO.getId();
		int tipoPedido = this.programacionDTO.getTipoPedido().getId();
		String descripcionTipoPedido = this.programacionDTO.getTipoPedido().getDescripcionTipo();
		int tipoPeriodo = this.programacionDTO.getTipoPeriodo().getId();
		String descripcionTipoPeriodo = this.programacionDTO.getTipoPeriodo().getDescripcionTipo();
		String descripcionProgramacion = this.programacionDTO.getDescripcionProgramacion();
		this.requerimientoAreaDTO = new RequerimientoAreaDTO();		
		this.requerimientoAreaDTO.setIndicadorEnviadoAlJefe(valorNoEnviado);
		this.requerimientoAreaDTO.setIndicadorRequerimientoRecepcionDelJefe(valorNoRecepcionado);
		this.requerimientoAreaDTO.setEstado(new EstadoDTO());
		this.requerimientoAreaDTO.getEstado().setId(paraRegistrar);
		this.requerimientoAreaDTO.setArea(new AreaDTO());
		this.requerimientoAreaDTO.getArea().setId(areaId);
		this.requerimientoAreaDTO.getArea().setDescripcionArea(descripcionArea);
		this.requerimientoAreaDTO.setProgramacion(new ProgramacionDTO());
		this.requerimientoAreaDTO.getProgramacion().setId(programacionId);
		this.requerimientoAreaDTO.getProgramacion().setDescripcionProgramacion(descripcionProgramacion);
		this.requerimientoAreaDTO.getProgramacion().setTipoPedido(new TipoDTO());
		this.requerimientoAreaDTO.getProgramacion().getTipoPedido().setId(tipoPedido);
		this.requerimientoAreaDTO.getProgramacion().getTipoPedido().setDescripcionTipo(descripcionTipoPedido);
		this.requerimientoAreaDTO.getProgramacion().setTipoPeriodo(new TipoDTO());
		this.requerimientoAreaDTO.getProgramacion().getTipoPeriodo().setId(tipoPeriodo);
		this.requerimientoAreaDTO.getProgramacion().getTipoPeriodo().setDescripcionTipo(descripcionTipoPeriodo);
	} 
	
	//Prueba de método para limpiar el nuevo requerimiento de área
	public void limpiarNuevoRequerimientoArea()
	{
		this.listaEstado = new ArrayList<>();
		this.listaIndicadorEnviadoAlJefe = new ArrayList<>();
		this.listaIndicadorRecepcionDelJefe = new ArrayList<>();
		//Prueba :No es necesario inicializar porque tiene que estar cargado en sesión
		//this.programacionDTO = new ProgramacionDTO();
		//this.areaDTO = new AreaDTO();
		this.requerimientoAreaDTO = new RequerimientoAreaDTO();
		
		resetearAreaEnTabla();
		resetearProductosEnTabla();
		//Método para resetear ArregloValoresAreaProducto.
		resetearArregloValoresAreaProducto();
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

	
	
	
	//Método que permite inicializar la variable colNames.
		public void resetearProductosEnTabla()
		{
			this.colNames = new ArrayList<String>();				
		}
	
	
	//Método que permite inicializar la variable rowNames.
	public void resetearAreaEnTabla()
	{
		this.rowNames = new ArrayList<String>();
	}
	
	//Método que permite inicializar la variable rowUsuario.
		public void resetearUsuariosEnTabla()
		{
			this.rowUsuarios= new ArrayList<String>();
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
	
	//Prueba de método para cargar el área.
	public void cargarArea()
	{
		List<ProgramacionAreasDTO>listaProgramacionArea = new ArrayList<>();		
		int programacionId = this.programacionDTO.getId();
		int areaId = this.usuarioDTO.getArea().getId();
		this.areaDTO = new AreaDTO();
		ProgramacionAreasDTO programacionAreaDTO = new ProgramacionAreasDTO();
		try 
		{
			listaProgramacionArea = programacionAreaServiceLocal.buscarProgramacionAreas(programacionId , areaId);
			programacionAreaDTO = listaProgramacionArea.get(0);
			this.areaDTO.setId(programacionAreaDTO.getArea().getId());
			this.areaDTO.setDescripcionArea(programacionAreaDTO.getArea().getDescripcionArea());
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(),e);
		}
	}
	
	//Prueba de método para cargar la programaciónDTO ingresando como parámetro el mes actual del sistema.
	public void cargarProgramacion()
	{
		try 
		{
			int mesActual = getMesSistema();
			List<ProgramacionDTO> listaProgramacion= this.programacionServiceLocal.buscarProgramacionXMesActual(mesActual);
			this.programacionDTO = new ProgramacionDTO(); 
			this.programacionDTO = listaProgramacion.get(0);					
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(),e);
		}	
	}
	
	//Prueba de método para cargar programacionDTO pasandole como parámetro el mes de la fecha que se registró.
	public void cargarProgramacionRequerimientoArea(Date fechaRegistroRequerimientoArea)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(fechaRegistroRequerimientoArea);
		Integer mesRequerimientoArea = c.get(Calendar.MONTH)+1;
		List<ProgramacionDTO> listaProgramacion= this.programacionServiceLocal.buscarProgramacionXMesActual(mesRequerimientoArea);
		this.programacionDTO = new ProgramacionDTO(); 
		this.programacionDTO = listaProgramacion.get(0);			
	}
	
	
	//Método para abrir el popupConfirmarNuevoRegistrar.
	public void mensajeConfirmacionNuevoRegistrar() 
	{

		context = RequestContext.getCurrentInstance();
		context.execute("PF('popupConfirmarNuevoRegistrar').show();");		
		
	}

	//Prueba de método para registrar un requerimiento de área.
	public void registrarRequerimientoArea() throws Exception
	{
		boolean seRegistro = crearRequerimientoArea();
		if(seRegistro == true)
		{
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Aviso",
					"Se registró con éxito"));
			cerrarPopupRegistrarDetalleRequerimientoArea();
			cerrarMensajeConfirmacionRegistro();
			
		}
		else
		{
		System.out.println("No se pudo registrar.");
		}
		buscarRequerimientoArea();
	}
	
	//Prueba para cerrar el popup mensaje de confirmación guardar
	public void cerrarMensajeConfirmacionRegistro() 
	{
		context = RequestContext.getCurrentInstance();
		context.execute("PF('popupConfirmarNuevoRegistrar').hide();");
	}

	
	//Prueba pra cerrar popupRegistrardetallerequerimientousuarioproducto
	public void cerrarPopupRegistrarDetalleRequerimientoArea() 
	{
		context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogoNuevoRequerimientoArea').hide();");
	}
	
	
	
	
	public boolean crearRequerimientoArea() throws Exception
	{		
	//Prueba de ip registro, codigo duro se tiene que obtener mediante el sistema.	
//	String ip = "192.168.1.10";
	RequerimientoAreaDTO objeto = new RequerimientoAreaDTO();
	objeto.setIndicadorEnviadoAlJefe(this.requerimientoAreaDTO.getIndicadorEnviadoAlJefe());
	objeto.setFechaEnviadoAlJefe(this.requerimientoAreaDTO.getFechaEnviadoAlJefe());
	objeto.setIndicadorRequerimientoRecepcionDelJefe(this.requerimientoAreaDTO.getIndicadorRequerimientoRecepcionDelJefe());
	objeto.setFechaRequerimientoRecepcionDelJefe(this.requerimientoAreaDTO.getFechaRequerimientoRecepcionDelJefe());
	objeto.setIndicadorActivo((short) 1);
	objeto.setCodigoUsuarioRegistro(this.usuarioDTO.getDescripcionUsuario());
	objeto.setIpRegistro(obtieneIPCliente());
	objeto.setEstado(new EstadoDTO());
	objeto.getEstado().setId(this.requerimientoAreaDTO.getEstado().getId());
	objeto.setArea(new AreaDTO());
	objeto.getArea().setId(this.requerimientoAreaDTO.getArea().getId());
	objeto.setProgramacion(new ProgramacionDTO());
	objeto.getProgramacion().setId(this.requerimientoAreaDTO.getProgramacion().getId());
	objeto.getProgramacion().setTipoPedido(new TipoDTO());
	objeto.getProgramacion().getTipoPedido().setId(this.requerimientoAreaDTO.getProgramacion().getTipoPedido().getId());
	objeto.getProgramacion().setTipoPeriodo(new TipoDTO());
	objeto.getProgramacion().getTipoPeriodo().setId(this.requerimientoAreaDTO.getProgramacion().getTipoPeriodo().getId());
	requerimientoAreaServiceLocal.registrarRequerimientoArea(objeto);			
	return true;			
	}
	
	
	//Prueba para actualizar el requerimiento de área.
	public void abrirPopupactualizarRequerimientoArea(Integer posicion)
	{
	//Prueba para cargar los combos del formulario actualizar requerimiento área.
	generarListaIndicadorEnviadoAlJefe();
	generarListaIndicadorRecepcionDelJefe();
	generarListaEstado();	   	 	   	 
	//Prueba para obtener los datos del objeto seleccinado en la lista de requerimiento de área.	
	obtenerDatosRequerimientoArea(posicion);	
	
	if(this.requerimientoAreaDTO.getIndicadorEnviadoAlJefe() == 1)
	{
		System.out.println("No se puede abrir el popup actualizar porque el consolidado del requerimiento de área se ha enviado al jefe.");
		FacesContext.getCurrentInstance().addMessage("RequerimientoAreaEnviadoAlJefeDesdeActualizar", 
				new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Error!", 
						"Ya se ha enviado el consolidado del requerimiento de área "+this.requerimientoAreaDTO.getId()+ 
						" hacia el jefe, por ello no puede actualizarse.")						
				);
		
	}
	else
	{
		cargarProgramacion();
		cargarArea();
		
		//Prueba para mostrar las cabeceras de columnas y de fila
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
		//Prueba para traer la cantidad de productos seleccionados desde la BD	
		cargarValoresAreaProductoDesdeBD(idProgramacionArea);
		
		context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogoActualizarRequerimientoArea').show();");
	}	
	
	}
	
	
	public void actualizarRequerimientoArea() throws Exception
	{
		java.util.Date fechaJava = new java.util.Date(); 
		java.sql.Date fecha = new java.sql.Date(fechaJava.getTime());
		if(this.requerimientoAreaDTO.getIndicadorEnviadoAlJefe() == 1)
		{
			this.requerimientoAreaDTO.setFechaEnviadoAlJefe(fecha);
		}
		else
		{
			this.requerimientoAreaDTO.setFechaEnviadoAlJefe(null);
		}
		
		boolean seActualizo = requerimientoAreaServiceLocal.actualizarRequerimientoAreaDTO(this.requerimientoAreaDTO);
		if(seActualizo == true)
		{
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Aviso",
					"Se actualizó con éxito"));
			cerrarPopupActualizarRequerimientoArea();
			cerrarMensajeConfirmacionActualizar();							
		}
		else
		{
			System.out.println("No se actualizó");
		}
		buscarRequerimientoArea();
	}
	
	//Prueba pra cerrar dialogoActualizarRequerimientoArea
	public void cerrarPopupActualizarRequerimientoArea() 
	{
		context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogoActualizarRequerimientoArea').hide();");
	}
	
	//Prueba para cerrar el popup mensaje de confirmación de actualizar
	public void cerrarMensajeConfirmacionActualizar() 
	{
		context = RequestContext.getCurrentInstance();
		context.execute("PF('popupConfirmarActualizar').hide();");
	}
	
	//Prueba para mostrar el popup de confirmación
	public void mensajeConfirmacionActualizar() 
	{
		context = RequestContext.getCurrentInstance();
		context.execute("PF('popupConfirmarActualizar').show();");				
	}
	
	
	//Prueba de método para abrir el popup de agregar requerimientos de usuarios.
	public void abrirPopupAgregarRequerimientoUsuarios(Integer posicion) throws Exception
	{		
		//Prueba para cargar los combos del formulario actualizar requerimiento área.
		generarListaIndicadorEnviadoAlJefe();
		generarListaIndicadorRecepcionDelJefe();
		generarListaEstado();	   	 	   	 
		//Prueba para obtener los datos del objeto seleccinado en la lista de requerimiento de área.	
		obtenerDatosRequerimientoArea(posicion);	
		
		if(this.requerimientoAreaDTO.getIndicadorEnviadoAlJefe() == 1)
		{
			System.out.println("Ya se ha enviado el requerimiento de área hacia el jefe, por ello no puede actualizarse.");
			FacesContext.getCurrentInstance().addMessage("RequerimientoAreaEnviadoAlJefe", 
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR, "Error!", 
							"Ya se ha enviado el requerimiento de área "+this.requerimientoAreaDTO.getId()+ 
							" hacia el jefe, por ello no puede actualizarse.")						
					);
		}
		else
		{
			//cargarProgramacion();		
			cargarProgramacionRequerimientoArea(this.requerimientoAreaDTO.getFechaRegistro());
			cargarArea();
			
			//Prueba para mostrar las cabeceras de columnas y de fila
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
			//Prueba para traer la cantidad de productos seleccionados desde la BD	
			cargarValoresAreaProductoDesdeBD(idProgramacionArea);
			//Prueba para cargar la descripción de los usuarios como cabecera de fila.
			cargarFilaUsuarioEnTabla();
			//Prueba para inicializar el arregloValoresRequerimientoUsuario
			inicializarArregloValoresRequerimientoUsuario();
			//Prueba para cargar valores al arregloAgregarRequerimientoUsuarios
			cargarValoresAgregarRequerimientoUsuarioDesdeBD(idProgramacionArea);
			
			context = RequestContext.getCurrentInstance();
			context.execute("PF('dialogoAgregarRequerimientoUsuarios').show();");	

		}
		
		
	}
	
	
	//Prueba de método para cargar la fila con la descripción del usuario
	public void cargarFilaUsuarioEnTabla() throws Exception
	{
		Short indicadorEnviadoAlEncargado = 1;
		Integer programacionAreasId=this.programacionAreasDTO.getId();
		List<RequerimientoUsuarioDTO>listaRequerimientoUsuario = requerimientoUsuarioServiceLocal.buscarRequerimientoUsuarioDTO(null, null, null, programacionAreasId, null,indicadorEnviadoAlEncargado,null);
		try 
		{
			for (RequerimientoUsuarioDTO objeto : listaRequerimientoUsuario) 
			{
				if(this.rowUsuarios.size() >= 0)
				{
					this.rowUsuarios.add(objeto.getUsuario().getDescripcionUsuario());
				}
				else
				{
					System.out.println("No se pudo agregar el usuario.");
				}
			}
		}
		catch (Exception e) 
		{
		System.out.println("Error : "+e.getMessage());
		log.error(e.getMessage(),e);
		}
	}

	
	//Prueba de método para limpiar el nuevo requerimiento de área
	public void limpiarAgregarRequerimientoUsuarios()
	{
		this.listaEstado = new ArrayList<>();
		this.listaIndicadorEnviadoAlJefe = new ArrayList<>();
		this.listaIndicadorRecepcionDelJefe = new ArrayList<>();
		this.programacionDTO = new ProgramacionDTO();
		this.areaDTO = new AreaDTO();
		this.requerimientoAreaDTO = new RequerimientoAreaDTO();				
		resetearAreaEnTabla();
		resetearUsuariosEnTabla();
		resetearProductosEnTabla();
		//Método para resetear ArregloValoresAreaProducto.
		resetearArregloValoresAreaProducto();
	}
	
	
	//Prueba de método para inicializar y setear los valores del arregloValoresRequerimientoUsuario
		public void inicializarArregloValoresRequerimientoUsuario()
		{
			int fila = this.rowUsuarios.size(); 
			//Suma 1 para guardar el id del requerimiento de usuario
			int columna = this.colNames.size()+1;
			this.arregloValoresRequerimientoUsuarios= new Integer[fila][columna];
			for(int i=0;i<fila;i++)
			{
				for(int j=0;j<columna;j++)
				{
					arregloValoresRequerimientoUsuarios[i][j]=1;
				}
			}			
		}
		
	//Método para setear a 0 los valores del arregloValoresRequerimientoUsuario.
	public void resetearArregloValoresRequerimientoUsuario()
	{
		//Es similar al método para inicializar.
		this.inicializarArregloValoresRequerimientoUsuario();		
	}

	
	//Prueba para cargar los valores de la cantidad de productos
	//de los requerimientos de usuarios desde la BD. 
	public void cargarValoresAgregarRequerimientoUsuarioDesdeBD(Integer idProgramacionArea) throws Exception
	{
		Short indicadorEnviadoAlEncargado = 1;
		int fila = 0;		
		int columna;
		List<RequerimientoUsuarioDTO>listaRequerimientoUsuario = requerimientoUsuarioServiceLocal.buscarRequerimientoUsuarioDTO(null,null,null,idProgramacionArea, null,indicadorEnviadoAlEncargado,null);
		for (RequerimientoUsuarioDTO objeto: listaRequerimientoUsuario) 		
		{
			columna=0;
			List<DetalleRequerimientoUsuarioProductoDTO>listaDetalleRequerimientoUsuarioProducto = detalleRequerimientoUsuarioProductoServiceLocal.buscarDetalleRequerimientoUsuarioProducto(null,objeto.getId(), null);
			for (DetalleRequerimientoUsuarioProductoDTO objeto1: listaDetalleRequerimientoUsuarioProducto) 
			{
				this.arregloValoresRequerimientoUsuarios[fila][columna] = objeto1.getCantidad();
				columna++;
			}
				this.arregloValoresRequerimientoUsuarios[fila][columna] = objeto.getId();
			fila++;			
		}
	}
	
	//Prueba de método para buscar el requerimiento de usuario desde el popupAgregarRequerimientoUsuario
	public void abrirPopupRequerimientoUsuarioDesdeAgregarRequerimientoUsuario(Integer posicion)
	{
		try 
		{
			RequerimientoUsuarioDTO objeto = obtenerRequerimientoUsuarioDTOXRequerimientoUsuarioId(posicion);
			requerimientoUsuarioBean.abrirPopupActualizarRequerimientoUsuarioDesdeAgregarRequerimientoUsuario(objeto);
			context = RequestContext.getCurrentInstance();
			context.execute("PF('dialogoActualizarRequerimientoUsuarioDesdeAprobacionJefe').show();");
		}
		catch (Exception e) 
		{
		System.out.println("Error : "+e.getMessage());
		}

	}
	

	
	
	//Prueba de méotodo para buscar el id de programacionArea dentro del arregloValoresRequerimientoUsuarios
	public int buscarIdProgramacionAreaArregloVariablesRequerimientoArea(Integer posicion)
	{
		int fila = posicion;
		int columna = colNames.size();
		Integer valor = 0;
		valor = this.arregloValoresRequerimientoUsuarios[fila][columna];		
		return valor;
	}
		
	
	//Prueba de método para obtener el requerimientoUsuarioDTO
	public RequerimientoUsuarioDTO obtenerRequerimientoUsuarioDTOXRequerimientoUsuarioId(Integer posicion) throws Exception
	{
				
		int requerimientoId = buscarIdProgramacionAreaArregloVariablesRequerimientoArea(posicion);
		List<RequerimientoUsuarioDTO> lista = requerimientoUsuarioServiceLocal.buscarRequerimientoUsuarioDTO(requerimientoId,null,null, null, null,null,null);
		RequerimientoUsuarioDTO objeto = new RequerimientoUsuarioDTO();
		objeto = lista.get(0);
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
		return objeto;
	}
	
	//Método para inicializar la variable requerimientoUsuarioDTO.
	public void inicializarRequerimientoUsuarioDTO()
	{
		this.requerimientoAreaDTO = new RequerimientoAreaDTO();
	}

	//Método para abrir el popup de confimración
	public void abrirDevolucionRequerimientoUsuario()
	{
		context = RequestContext.getCurrentInstance();
		context.execute("PF('popupConfirmarDevolucionRequerimientoUsuario').show();");		
	}
	
	
	//Prueba de método para devolver el requerimiento del usuario que se visualiza en el popup de los
	//requerimiento de usuarios que van a ser consolidados.
	public void devolverRequerimientoUsuarioAlUsuario(Integer posicion)
	{		
		try 
		{
			Short indicadorDevuelto = 2; 
			RequerimientoUsuarioDTO objeto = obtenerRequerimientoUsuarioDTOXRequerimientoUsuarioId(posicion);
			objeto.setIndicadorEnviadoAlEncargado(indicadorDevuelto);
			objeto.setFechaEnviadoAlEncargado(null);
			boolean seActualizo = requerimientoUsuarioServiceLocal.actualizarRequerimientoUsuario(objeto);
			
			if(seActualizo == true)
			{
					FacesContext context = FacesContext.getCurrentInstance();
					context.addMessage(null, new FacesMessage("Aviso","Se devolvió el requerimiento :"+this.requerimientoUsuarioDTO.getId()
					+"\tdel Usuario :"+this.requerimientoUsuarioDTO.getUsuario().getDescripcionUsuario()));
					cerrarMensajeConfirmacionDevolverRequerimientoUsuario();
					cerrarAgregarRequerimientoUsuario();
			}
			else
			{
				System.out.println("No se ha actualizado");
			}

		}
		catch (Exception e) 
		{
		System.out.println("Error : "+e.getMessage());
		}		
	}
	
	
	public void cerrarMensajeConfirmacionDevolverRequerimientoUsuario() 
	{
		context = RequestContext.getCurrentInstance();
		context.execute("PF('popupConfirmarDevolucionRequerimientoUsuario').hide();");
	}
	
	public void cerrarAgregarRequerimientoUsuario() 
	{
		context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogoAgregarRequerimientoUsuarios').hide();");
	}
	
	
	/************************************************************************************************/
	/************************************************************************************************/
	/******************* VER  REQUERIMIENTOS PARA CONSOLIDAR         ********************************/
	/************************************************************************************************/
	/************************************************************************************************/
	
	//Prueba de método para abrir el popup del form que presentan el total de productos y cantidades disponibles; 
	//además el total de productos y cantidades demandadas por los usuarios
	public void abrirPopupVerRequerimientosParaConsolidar()
	{

		//Prueba de método para inicializar la variable rowTotalDiferencia con los valores de TOTAL DEMANDAD,
		//y DIFERENCIA, para visualizar en las cabeceras de filas.
		inicializarRowTotalDiferencia();
		//Prueba de método para generar un arreglo que permita verificar el total de los requerimientos demandados por los usuarios
		//así como la diferencia entre la cantidad disponible y demandada.
		generarArregloTotalCantidadProductosRequerimientoUsuario();
		context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogoVerRequerimientosParaConsolidarRequerimientoUsuario').show();");
	}
	
	
	private int arregloTotalSobranteCantidadRequerimientosUsuarios[][];
	private List<String> rowTotalDiferencia;
	
	//Prueba de método para inicializar la variable arregloTotalSobranteCantidadRequerimientosUsuarios
	//con número de filas 2 y cantidad de columnas dinámicas de acuerdo a los productos seleccionadas para
	//el requerimiento.
	public void inicializarArregloTotalSobranteCantidadRequerimientosUsuarios(int cantidadProductos)
	{
	this.arregloTotalSobranteCantidadRequerimientosUsuarios = new int[2][cantidadProductos];	
	}
	
	//Prueba de método para inicializar la variable rowTotalDiferencia con los valores de TOTAL DEMANDAD,
	//y DIFERENCIA, para visualizar en las cabeceras de filas.
	public void inicializarRowTotalDiferencia()
	{
		resetearRowTotalDiferencia();
		this.rowTotalDiferencia.add("TOTAL DEMANDADA ");
		this.rowTotalDiferencia.add("DIFERENCIA ");
	}
	
	//Prueba de método que sirve para limpiar la variable rowTotalDiferencia,
	//es decir para reiniciarla sin valores.
	public void resetearRowTotalDiferencia()
	{
		this.rowTotalDiferencia = new ArrayList<String>();
	}
	
	//Prueba de método para generar un arreglo que permita verificar el total de los requerimientos demandados por los usuarios
	//así como la diferencia entre la cantidad disponible y demandada.
	public void generarArregloTotalCantidadProductosRequerimientoUsuario()
	{
		
		
		//necesito listas de todos los usuarios y la cantidad de productos demandados por estos.		
		int fila = this.rowUsuarios.size(); 
		//Suma 1 para guardar el id del requerimiento de usuario
		//int columna = this.colNames.size()+1;
		int columna = this.colNames.size();
		//this.arregloValoresRequerimientoUsuarios= new Integer[fila][columna];
		inicializarArregloTotalSobranteCantidadRequerimientosUsuarios(columna);
		for(int i=0;i<fila;i++)
		{
			for(int j=0;j<columna;j++)
			{
				//Sumamos los valores de cada cantidad de producto por usuario al arreglo que va contener toda la suma
				this.arregloTotalSobranteCantidadRequerimientosUsuarios[0][j] +=arregloValoresRequerimientoUsuarios[i][j];
				
			}
		}					
		//Probamos para obtener la diferencia entre los productos disponibles menos los productos demandados,
		//seteandolos al índice 1 del arreglo :arregloTotalSobranteCantidadRequerimientosUsuarios. 
		for(int j= 0;j<columna;j++)
		{
			this.arregloTotalSobranteCantidadRequerimientosUsuarios[1][j] = this.arregloValoresAreaProducto[0][j] - this.arregloTotalSobranteCantidadRequerimientosUsuarios[0][j];
		}					
	}
	
	//Prueba de método para enviar consolidado al jefe.
	public void enviarConsolidadoAlJefe() throws Exception
	{
		//comparar la cantidad de producto disponible y la diferencia entre la cantidad disponible menos la cantidad demandada.
		//la cantidad disponible tiene que ser igual o mayor a la diferencia entre disponible y demandada.				
		boolean seComparo = compararCantidadProductosDisponiblesVSCantidadDemandada();
		
		if(seComparo == true)
		{
			//si es cierta la comparación se envía el consolidado al jefe.
			//se bloquea la actualización del requerimiento de área debido a que se le ha enviado,se podrá actualizar si no se ha enviado
			//Envió al jefe 1 = se envió
			short enviarAlJefe = 1;
			java.util.Date fechaJava = new java.util.Date(); 
			java.sql.Date fechaEnviadoAlJefe = new java.sql.Date(fechaJava.getTime());
			this.requerimientoAreaDTO.setIndicadorEnviadoAlJefe(enviarAlJefe);
			this.requerimientoAreaDTO.setFechaEnviadoAlJefe((java.sql.Date)fechaEnviadoAlJefe);
			boolean seActualizoRequerimientoArea = this.requerimientoAreaServiceLocal.actualizarRequerimientoAreaDTO(this.requerimientoAreaDTO);
			if(seActualizoRequerimientoArea == true)
			{
				System.out.println("Se actualizó el requerimiento de área.");
				System.out.println("El bloqueo del popup actualizar se dee realizar al llamar a esa action.");
				//El excedente se actualizará cuando el jefe de área apruebe el requerimiento de área y el jefe de almacén de el visto bueno.
				//o se ha rechazado el requerimiento de área.
				//si hay excedente se devuelve al almacén la diferencia de lo contrario se continua.
				//limpiamos el formulario.
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Aviso","Se envió el consolidado del requerimiento de área :"+this.requerimientoAreaDTO.getId()+" al jefe de área."));				
				
//				cerrar mensaje de confirmación para aceptar o cancelar
				cerrarMensajeConfirmacionConsolidarRequerimientoArea();
//				cerrar popup verrequerimeinto
				cerrarDialogoVerRequerimientoUsuarios();
//				cerrar popup agregarrequerimientousuarios
				cerrarDialogoAgregarRequerimientoUsuarios();
				//Prueba de método para cargar los objeto de la lista de requerimeinto de áreas.(enviados y no enviados)
				buscarRequerimientoArea();
				
				
			}else
			{
//				cerrar mensaje de confirmación para aceptar o cancelar
				cerrarMensajeConfirmacionConsolidarRequerimientoArea();
//				cerrar popup verrequerimeinto
				cerrarDialogoVerRequerimientoUsuarios();
//				cerrar popup agregarrequerimientousuarios
				cerrarDialogoAgregarRequerimientoUsuarios();
				
				
				System.out.println("No se actualizó el requerimiento de área.");
				FacesContext.getCurrentInstance().addMessage("NoSeActualizoRequerimientoArea", 
						new FacesMessage(
								FacesMessage.SEVERITY_ERROR, "Error!", 
								"No se logró actualizar el requerimiento de área.")						
						);
			}			
			
		}
		else
		{
//			cerrar mensaje de confirmación para aceptar o cancelar
			cerrarMensajeConfirmacionConsolidarRequerimientoArea();
//			cerrar popup verrequerimeinto
			cerrarDialogoVerRequerimientoUsuarios();
//			cerrar popup agregarrequerimientousuarios
			cerrarDialogoAgregarRequerimientoUsuarios();
			
			//* si es falsa la comparación se emite el error que unos de los productos excede en cantidad a lo disponible.
			System.out.println("La cantidad de uno de los productos disponibles es menor que la cantidad de unos de los productos " +
					"demandados por los usuarios.");
			FacesContext.getCurrentInstance().addMessage("ProductoDemandadoExcedeProductoDisponible", 
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR, "Error!", 
							"La cantidad de uno de los productos disponibles es menor que la cantidad de unos de los productos " +
							"demandados por los usuarios.")						
					);
		}
				
	}
	
	
	//comparar la cantidad de producto disponible y la diferencia entre la cantidad disponible menos la cantidad demandada.
	//la cantidad disponible tiene que ser igual o mayor a la diferencia entre disponible y demandada.		
	public boolean compararCantidadProductosDisponiblesVSCantidadDemandada()
	{
		//int fila = this.rowUsuarios.size(); 
		int columna = this.colNames.size();				
		for(int j=0;j<columna;j++)
		{
			if(this.arregloValoresAreaProducto[0][j] >= this.arregloTotalSobranteCantidadRequerimientosUsuarios[0][j])
			{
				System.out.println("El valor "+this.arregloValoresAreaProducto[0][j]+" es mayor o igual a "+this.arregloTotalSobranteCantidadRequerimientosUsuarios[0][j]);
			}
			else
			{
				System.out.println("El valor "+this.arregloValoresAreaProducto[0][j]+" es menor a  "+this.arregloTotalSobranteCantidadRequerimientosUsuarios[0][j]+" y no se puede procesar.");
				return false;							
			}						
		}
		return true;
	}
	 
	//Prueba para abrir un dialogo de confirmación.
	public void mensajeConfirmacionConsolidarRequerimientoArea() 
	{

		context = RequestContext.getCurrentInstance();
		context.execute("PF('popupConsolidarRequerimientoArea').show();");		
		
	}
	
	//Prueba para cerrar el dialogo de confirmación
	public void cerrarMensajeConfirmacionConsolidarRequerimientoArea() 
	{
		context = RequestContext.getCurrentInstance();
		context.execute("PF('popupConsolidarRequerimientoArea').hide();");
	}

	//Prueba para cerrar el dialogo de ver requerimientos
	public void cerrarDialogoVerRequerimientoUsuarios()
	{
		context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogoVerRequerimientosParaConsolidarRequerimientoUsuario').hide();");	
	}
	
	//Prueba para cerrar el dialogo de agregar requerimeintos de usuarios
	public void cerrarDialogoAgregarRequerimientoUsuarios()
	{
		context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogoAgregarRequerimientoUsuarios').hide();");	
	}
	
	
	/*************************************************************************************************/
	/*************************          APROBACION JEFE                      *************************/
	/*************************************************************************************************/
	 
	//Prueba de método para buscar si se ha enviado requerimientos de área al jefe.
	 public void buscarRequerimientoAreaJefe()
	 {
		 buscarRequerimientoArea();
		 this.listaRequerimientoAreaJefe = new ArrayList<RequerimientoAreaDTO>();
		 for(RequerimientoAreaDTO objeto : this.listaRequerimientoArea)
		 {
			 //1 : ENVIADO AL JEFE   3 : APROBADO POR EL JEFE
//			 if(objeto.getIndicadorEnviadoAlJefe() == 1 || objeto.getIndicadorEnviadoAlJefe() == 3)
			 if((objeto.getIndicadorEnviadoAlJefe() == 1 || objeto.getIndicadorEnviadoAlJefe() == 3)&& this.usuarioPerfilesDTO.getPerfil().getId() == 3)
			 {
				 this.listaRequerimientoAreaJefe.add(objeto);
				 System.out.println("El requerimiento de área : "+objeto.getId()+" se envió al jefe.");
			 }
			 else
			 {
				 System.out.println("El requerimiento de área : "+objeto.getId()+" no ha sido enviado al jefe.");
			 }
		 }
		 
	 }
	 
	 //Prueba de método para abrir el popup que presenta los detalles del requerimiento del área desde aprobación de jefe .
	 public void abrirPopupDetallesRequerimientoAreaDesdeAprobacionJefe(Integer posicion)
	 {
	 obtenerDatosRequerimientoArea(posicion);
	 generarListaIndicadorEnviadoAlJefe();
	 generarListaIndicadorRecepcionDelJefe();
	 generarListaEstado();	   	 	   	 
	 this.posicion = posicion;
	 context = RequestContext.getCurrentInstance();
	 context.execute("PF('dialogoDetalleRequerimientoUsuarioDesdeAprobacionJefe').show();");
	 }

	 	//Prueba de método para abrir el popup de agregar requerimientos de usuarios en la sesión del jefe de área.
		public void abrirPopupAgregarRequerimientoUsuariosDesdeAprobacionJefe(Integer posicion) throws Exception
		{		
			//Prueba para cargar los combos del formulario actualizar requerimiento área.
			generarListaIndicadorEnviadoAlJefe();
			generarListaIndicadorRecepcionDelJefe();
			generarListaEstado();	   	 	   	 
			//Prueba para obtener los datos del objeto seleccinado en la lista de requerimiento de área.	
			obtenerDatosRequerimientoArea(posicion);	

			//cargarProgramacion();		
			cargarProgramacionRequerimientoArea(this.requerimientoAreaDTO.getFechaRegistro());
			cargarArea();
			
			//Prueba para mostrar las cabeceras de columnas y de fila
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
			//Prueba para traer la cantidad de productos seleccionados desde la BD	
			cargarValoresAreaProductoDesdeBD(idProgramacionArea);
			//Prueba para cargar la descripción de los usuarios como cabecera de fila.
			cargarFilaUsuarioEnTabla();
			//Prueba para inicializar el arregloValoresRequerimientoUsuario
			inicializarArregloValoresRequerimientoUsuario();
			//Prueba para cargar valores al arregloAgregarRequerimientoUsuarios
			cargarValoresAgregarRequerimientoUsuarioDesdeBD(idProgramacionArea);		
			
			//Prueba de método para verificar que el requerimeento de área se halla enviado al jefe de almacén
			if(this.requerimientoAreaDTO.getIndicadorRequerimientoRecepcionDelJefe()!=3)
			{
				this.seAproboRequerimientoArea = true;
				
				//Prueba de método para setear el indicadorRecepcionaPorElJefe a 1 y la fecha recepcionada
				boolean seActualizo = setearIndicadorRecepcionJefeFechaJefe();
				if(seActualizo == true)
				{				
					//Prueba de método para inicializar la variable rowTotalDiferencia con los valores de TOTAL DEMANDAD,
					//y DIFERENCIA, para visualizar en las cabeceras de filas.
					inicializarRowTotalDiferencia();
					//Prueba de método para generar un arreglo que permita verificar el total de los requerimientos demandados por los usuarios
					//así como la diferencia entre la cantidad disponible y demandada.
					generarArregloTotalCantidadProductosRequerimientoUsuario();
					
					System.out.println("Se actualizó el requerimiento de área.");
					context = RequestContext.getCurrentInstance();
					context.execute("PF('dialogoAgregarRequerimientoUsuariosDesdeAprobacionJefe').show();");	
				}
				else
				{
					System.out.println("No se actualizó el requerimiento de área.");
				}

			}
			else
			{
				System.out.println("Ya no debería aprobar el requerimiento porque ya se ha enviado al jefe de almacén.");
				this.seAproboRequerimientoArea = false;
				
				//Prueba de método para inicializar la variable rowTotalDiferencia con los valores de TOTAL DEMANDAD,
				//y DIFERENCIA, para visualizar en las cabeceras de filas.
				inicializarRowTotalDiferencia();
				//Prueba de método para generar un arreglo que permita verificar el total de los requerimientos demandados por los usuarios
				//así como la diferencia entre la cantidad disponible y demandada.
				generarArregloTotalCantidadProductosRequerimientoUsuario();
				
				System.out.println("Se actualizó el requerimiento de área.");
				context = RequestContext.getCurrentInstance();
				context.execute("PF('dialogoAgregarRequerimientoUsuariosDesdeAprobacionJefe').show();");	

			}
			
											
		}
		
		//Prueba de método para buscar el requerimiento de usuario desde el popupAgregarRequerimientoUsuarioDesdeAprobacionJefe
		public void abrirPopupRequerimientoUsuarioDesdeAgregarRequerimientoUsuarioDesdeAprobacionJefe(Integer posicion)
		{
			try 
			{
				RequerimientoUsuarioDTO objeto = obtenerRequerimientoUsuarioDTOXRequerimientoUsuarioId(posicion);
				requerimientoUsuarioBean.abrirPopupActualizarRequerimientoUsuarioDesdeAgregarRequerimientoUsuario(objeto);
				context = RequestContext.getCurrentInstance();
				context.execute("PF('dialogoActualizarRequerimientoUsuarioDesdeA').show();");
			}
			catch (Exception e) 
			{
			System.out.println("Error : "+e.getMessage());
			}

		}

		//Prueba de método para setear el indicadorRecepcionaPorElJefe a 1 y la fecha recepcionada
		public boolean setearIndicadorRecepcionJefeFechaJefe() throws Exception
		{
			short indicadorRecepcionDelJefe = 1;			
			java.util.Date fecha = new Date();
			java.sql.Date fechaDeRecepcionDelJefe = new java.sql.Date(fecha.getTime());			
			this.requerimientoAreaDTO.setIndicadorRequerimientoRecepcionDelJefe(indicadorRecepcionDelJefe);
			this.requerimientoAreaDTO.setFechaRequerimientoRecepcionDelJefe(fechaDeRecepcionDelJefe);			
			boolean actualizarIndicadorFechaJefe = this.requerimientoAreaServiceLocal.actualizarRequerimientoAreaDTO(this.requerimientoAreaDTO);
			if(actualizarIndicadorFechaJefe == true)
			{
				System.out.println("Se actualizó el requerimientoAreaDTO");
			}
			else
			{
				System.out.println("Hubo error al actualizar el requerimientoAreaDTO");
			}
			return actualizarIndicadorFechaJefe;
		}
		
		
		//Prueba para abrir popup de confirmación para aprobar el requerimiento consolidado por el jefe de área.
		public void abrirPopupConfirmarAprobarConsolidado()
		{
			context = RequestContext.getCurrentInstance();
			context.execute("PF('popupConfirmarAprobarConsolidado').show();");	
		}
		
		//Prueba para abrir popup de devolución para regresar el requerimiento consolidado al encargado de área, que lo revise
		//y luego que lo vuelva a enviar una vez que ya ha sido revisado y esté dentro del rango de fechas establecidas pro la 
		//programación.
		public void abrirPopupConfirmarDevolverConsolidado()
		{
			context = RequestContext.getCurrentInstance();
			context.execute("PF('popupConfirmarDevolucionConsolidado').show();");	
		}
		
		
		//Prueba de método para devover el requerimiento de área al encargado para que lo revise y luego lo vuelva a enviar
		public void devolverRequerimientoAreaAlEncargado() throws Exception
		{
			//Primero cargar el id del requerimeinto de área.
			Integer requerimientoAreaId = this.requerimientoAreaDTO.getId();
			//setear al indicador enviado al jefe como 2 = rechazado
			short indicadorNoEnviado = 0;
			short indicadorRechazado = 2;
			this.requerimientoAreaDTO.setIndicadorEnviadoAlJefe(indicadorNoEnviado);
			this.requerimientoAreaDTO.setIndicadorRequerimientoRecepcionDelJefe(indicadorRechazado);
			boolean seActualizo = this.requerimientoAreaServiceLocal.actualizarRequerimientoAreaDTO(this.requerimientoAreaDTO);
			
			if(seActualizo == true)
			{
				System.out.println("El requerimiento de área :"+requerimientoAreaId+" se actualizó");
				//actualizar los metodo de búsqueda de requerimientos de área.
				buscarRequerimientoAreaJefe();
				//limpiar los popups
				//Prueba de método para cerrar el popup de confirmación de devolución de requerimiento de área
				cerrarPopupDevolucionRequerimientoArea();
				//Prueba para cerrar el popup de agregar requerimeinto de área desde aprobación jefe
				cerrarDialogoAgregarRequerimientoUsuariosDesdeAprobacionJefe();	
				//Aviso sobre correcta devolución del requerimiento de área haci el encargado del área
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Aviso","Se devolvió el requerimiento de área :"+this.requerimientoAreaDTO.getId()+" hacia el encargado de área."));
				
			}			
			else
			{
				//Prueba de método para cerrar el popup de confirmación de devolución de requerimiento de área
				cerrarPopupDevolucionRequerimientoArea();
				//Prueba para cerrar el popup de agregar requerimeinto de área
				cerrarAgregarRequerimientoUsuario();	
				System.out.println("No se pudo actualizar el requerimietno de área : "+requerimientoAreaId);
				//Mostra el error que avisa que no se actualizado
				FacesContext.getCurrentInstance().addMessage("ErrorDevolucionRequerimientoArea", 
						new FacesMessage(
								FacesMessage.SEVERITY_ERROR, "Error!", 
								"No se pudo actualizar el requerimietno de área : "+requerimientoAreaId)						
						);
				
			}

		}
		
		//Prueba para cerrar el popup de agregar requerimeinto de área desde aprobación jefe
		public void cerrarDialogoAgregarRequerimientoUsuariosDesdeAprobacionJefe()
		{
			context = RequestContext.getCurrentInstance();
			context.execute("PF('dialogoAgregarRequerimientoUsuariosDesdeAprobacionJefe').hide();");
			
		}
			
		//Prueba de método para cerrar el popup de confirmación de devolución de requerimiento de área
		public void cerrarPopupDevolucionRequerimientoArea()
		{
			context = RequestContext.getCurrentInstance();
			context.execute("PF('popupConfirmarDevolucionConsolidado').hide();");
		}
	 
	 
		//Prueba para aprobar el requerimeinto de área que ha sido consolidado, este requerimiento será enviado
		//al jefe de almacén
		public void aprobarRequerimientoArea()
		{
			try 
			{
			//Crear un registro en la tabla aprobacinJefe con los datos del objeto requerimientoarea			 			
			registrarRequerimientoJefeAprobacion();			
			//Requerimiento de área actual actualizar el indicador de recpción del jefe en el form del encargado como APROBADO,
			short indicadorAprobado = 3;
			this.requerimientoAreaDTO.setIndicadorRequerimientoRecepcionDelJefe(indicadorAprobado);
			boolean seActualizo = this.requerimientoAreaServiceLocal.actualizarRequerimientoAreaDTO(this.requerimientoAreaDTO);
			
			if(seActualizo == true)
			{
				System.out.println("Se actualizó el requerimiento de área.");
				//Cerrar el dialogo de agregar requerimiento usuarios dede aprobacion jefe.
				cerrarDialogoAgregarRequerimientoUsuariosDesdeAprobacionJefe();
				//Cerrar el dialogo de aprobación de requerimeinto de área.
				cerrarDialogoAprobacionRequerimientoArea();				
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage
						("Aviso","Se aprobó el requerimiento de área :"+this.requerimientoAreaDTO.getId()+" y se envió al Jefe de Almacén."));
				
				
				this.seAproboRequerimientoArea =true;				
				
			}
			else
			{
				System.out.println("No se actualizó el requerimiento de área.");
				this.seAproboRequerimientoArea =true;
			}
			//Actualizar el requerimeinto de cada usuario como aprobado.
			
			
			
			
			
			
			
			
			} catch (Exception e) 
			{
			System.out.println("Error : "+e.getMessage());
			}
			
		}
	 
		
		//Prueba de método para cerrar el dialogo de aprobación de requerimiento de área.
		public void cerrarDialogoAprobacionRequerimientoArea()
		{			
			context = RequestContext.getCurrentInstance();
			context.execute("PF('popupConfirmarAprobarConsolidado').hide();");			
		}
		
		
		
		//Prueba de regisro de requerimiento aprobado por el jefe
		public void registrarRequerimientoJefeAprobacion() throws Exception
		{
			Integer usuarioId = this.usuarioDTO.getId();
			Integer requerimientoAreaId = this.requerimientoAreaDTO.getId();
			short indicadorRecibioEncargadoDeArea = 1;//1 : Recibido
			java.util.Date fechaActual = new Date();
			java.sql.Date fechaRecibioEncargadoDeArea = new java.sql.Date(fechaActual.getTime());
			short indicadorEnviadoAlJefeDeAlmacen = 1;//Enviado
			java.sql.Date fechaEnviadoAlJefeAlmacen = new java.sql.Date(fechaActual.getTime());
			short indicadorRecibioJefeAlmacen = 0; //0:NO RECIBIDO  , 1 : RECIBIDO , VA RECIBIR CUANDO EL JEFE DE ALMACÉN ABRA EL POPUP
			java.sql.Date fechaRecibioJefeAlmacen = null;
			short indicadorEnviadoEncargadoDeAlmacen = 0; // 0:NO ENVIADO , 1 : ENVIADO
			java.sql.Date fechaEnviadoEncargadoDeAlmacen = null;
			short indicadorActivo = 1;
			String codigoUsuarioRegistro = this.usuarioDTO.getDescripcionUsuario();
			String ipRegistro = obtieneIPCliente();
			Integer estadoId = 1;
			
			RequerimientoJefeAprobacionDTO requerimientoJefeAprobacionDTO = new RequerimientoJefeAprobacionDTO();
			requerimientoJefeAprobacionDTO.setJefe(new UsuarioPerfilesDTO());			
			requerimientoJefeAprobacionDTO.getJefe().setId(usuarioId);
			requerimientoJefeAprobacionDTO.setRequerimientoArea(new RequerimientoAreaDTO());
			requerimientoJefeAprobacionDTO.getRequerimientoArea().setId(requerimientoAreaId);
			requerimientoJefeAprobacionDTO.setIndicadorRecibioEncargadoArea(indicadorRecibioEncargadoDeArea);
			requerimientoJefeAprobacionDTO.setFechaRecibioEncargadoArea(fechaRecibioEncargadoDeArea);
			requerimientoJefeAprobacionDTO.setIndicadorEnviadoJefeAlmacen(indicadorEnviadoAlJefeDeAlmacen);
			requerimientoJefeAprobacionDTO.setFechaEnviadoJefeAlmacen(fechaEnviadoAlJefeAlmacen);
			requerimientoJefeAprobacionDTO.setIndicadorRecibioJefeAlmacen(indicadorRecibioJefeAlmacen);					
			requerimientoJefeAprobacionDTO.setFechaRecibioJefeAlmacen(fechaRecibioJefeAlmacen);
			requerimientoJefeAprobacionDTO.setIndicadorEnviadoEncargadoAlmacen(indicadorEnviadoEncargadoDeAlmacen);
			requerimientoJefeAprobacionDTO.setFechaEnviadoEncargadoAlmacen(fechaEnviadoEncargadoDeAlmacen);
			requerimientoJefeAprobacionDTO.setIndicadorActivo(indicadorActivo);
			requerimientoJefeAprobacionDTO.setCodigoUsuarioRegistro(codigoUsuarioRegistro);
			requerimientoJefeAprobacionDTO.setIpRegistro(ipRegistro);
			requerimientoJefeAprobacionDTO.setEstado(new EstadoDTO());
			requerimientoJefeAprobacionDTO.getEstado().setId(estadoId);
								
			this.requerimientoJefeAprobacionDTO = this.requerimientoJefeAprobacionServiceLocal.registrarRequerimientoJefeAprobacion(requerimientoJefeAprobacionDTO); 
			
		}
		


		/*************************************************************************************************/
		/*************************          DETALLE APROBACION JEFE                      *************************/
		/**
		 * @throws Exception ***********************************************************************************************/
		
		//Prueba para abrir el popup detalle de arobación jefe 
		public void abrirDialogoDetalleAprobacionJefe(Integer posicion) throws Exception
		{
			//Prueba para cargar los combos del dialogo.
			cargarComboIndicadores();
			//Prueba para obtener los datos del objeto seleccinado en la lista de requerimiento de área.	
			obtenerDatosRequerimientoArea(posicion);	
			Integer jefeId = this.usuarioDTO.getId();
			List<UsuarioDTO>listaUsuario = this.usuarioServiceLocal.buscarUsuario(jefeId, null, null);
			if(listaUsuario!= null && listaUsuario.size()>=0)
			{
				this.usuarioDTO = listaUsuario.get(0);
			}
			else
			{
				System.out.println("No se encontró el usuario.");
			}
								
			Integer requerimientoAreaId = this.requerimientoAreaDTO.getId();								
			//Buscar el requerimiento aprobación de jefe 
			List<RequerimientoJefeAprobacionDTO> listaRequerimientoAprobado = new ArrayList<>();
			listaRequerimientoAprobado = this.requerimientoJefeAprobacionServiceLocal.buscarRequerimientoJefeAprobacion(null, jefeId, requerimientoAreaId,null, null);
			//Solo existe 1 requerimiento con requerimientoId único
			if(listaRequerimientoAprobado!=null && listaRequerimientoAprobado.size()!=0)
			{
				this.requerimientoJefeAprobacionDTO = listaRequerimientoAprobado.get(0);	
			}
			else
			{				
				this.requerimientoJefeAprobacionDTO = new RequerimientoJefeAprobacionDTO();
				this.requerimientoJefeAprobacionDTO.setIndicadorEnviadoJefeAlmacen((short) 0);
			}
			
			
			//1: ENVIADO AL JEFE DE ALMACEN	
			if(this.requerimientoJefeAprobacionDTO !=null)
			{
				if(this.requerimientoJefeAprobacionDTO.getIndicadorEnviadoJefeAlmacen() == 1)
				{
					System.out.println("Se puede ver el detalle de la aprobación jefe.");
					context = RequestContext.getCurrentInstance();
					context.execute("PF('dialogoDetalleAprobacionJefe').show();");
				}
				else
				{
					System.out.println("Error, no se puede visualizar el detalle debido que no se ha aprobado el requerimiento de área");
					FacesContext.getCurrentInstance().addMessage("ErrorDetalleAprobacionRequerimientoJefe", 
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR, "Error!", 
									"No se puede visualizar el detalle debido que no se ha aprobado el requerimiento de área: "+requerimientoAreaId)						
							);									
				}

			}
			else			
			{				
				System.out.println("El objeto requerimiento aprobacion jefe es nulo, es decir no se ha registrado como aprobado");				
				FacesContext.getCurrentInstance().addMessage("ErrorDetalleAprobacionRequerimientoJefeNulo", 
						new FacesMessage(
								FacesMessage.SEVERITY_ERROR, "Error!", 
								"No se ha registrado el requerimiento como aprobado : "+requerimientoAreaId)						
						);
				
				
			}
			
			//setearlos al objeto de clase requerimientoAprobacionJefe
		}
		
		//Prueba para cargar los combros con los valores de los indicadores
		public void cargarComboIndicadores()
		{
			generarListaIndicadorRecepcionEncargadoDelArea();
			generarListaIndicadorEnviadoAlJefeDelAlmacen();
			generarListaIndicadorRecepcionDelJefeDelAlmacen();
			generarListaIndicadorEnvioAlEncargadoDelAlmacen();
		}
		
		
		
		//Prueba para cargar el combo de indicadorEnviadoAlJefe
		 public void generarListaIndicadorRecepcionEncargadoDelArea()
		 {
			listaIndicadorRecepcionEncargadoDelArea= new ArrayList<>();
			listaIndicadorRecepcionEncargadoDelArea.add(new SelectItem(null,ConstanteWeb.CAMPO_SELECCIONE));
			listaIndicadorRecepcionEncargadoDelArea.add(new SelectItem((Integer)ConstanteWeb.REQ_APROBACION_JEFE_VALOR_NO_RECEPCIONADO,ConstanteWeb.REQ_APROBACION_JEFE_CAMPO_NO_RECEPCIONADO));
			listaIndicadorRecepcionEncargadoDelArea.add(new SelectItem((Integer)ConstanteWeb.REQ_APROBACION_JEFE_VALOR_RECEPCIONADO,ConstanteWeb.REQ_APROBACION_JEFE_CAMPO_RECEPCIONADO));
		 }
		
		//Prueba de método para limpiar la lista listaIndicadorRecepcionEncargadoDelArea.
		public void resetearListaIndicadorRecepcionEncargadoDelArea()
		{
			listaIndicadorRecepcionEncargadoDelArea = new ArrayList<>();
		}
		
		//Prueba para carga la lista listIndicadorEnviadoAlJefeDelAlmacen
		public void generarListaIndicadorEnviadoAlJefeDelAlmacen()
		 {
			listaIndicadorEnviadoAlJefeDelAlmacen = new ArrayList<>();
			listaIndicadorEnviadoAlJefeDelAlmacen.add(new SelectItem(null,ConstanteWeb.CAMPO_SELECCIONE));
			listaIndicadorEnviadoAlJefeDelAlmacen.add(new SelectItem((Integer)ConstanteWeb.REQ_APROBACION_JEFE_VALOR_NO_ENVIADO,ConstanteWeb.REQ_APROBACION_JEFE_CAMPO_NO_ENVIADO));
			listaIndicadorEnviadoAlJefeDelAlmacen.add(new SelectItem((Integer)ConstanteWeb.REQ_APROBACION_JEFE_VALOR_ENVIADO,ConstanteWeb.REQ_APROBACION_JEFE_CAMPO_ENVIADO));
		 }
		
		//Prueba de método para limpiar la lista listaIndicadorRecepcionEncargadoDelArea.
		public void resetearListaIndicadorEnviadoAlJefeDelAlmacen()
		{
			listaIndicadorEnviadoAlJefeDelAlmacen = new ArrayList<>();
		}
		

		//Prueba para carga la lista listIndicadorRecepcionDelJefeDelAlmacen
		public void generarListaIndicadorRecepcionDelJefeDelAlmacen()
		{
			listaIndicadorRecepcionDelJefeDelAlmacen = new ArrayList<>();
			listaIndicadorRecepcionDelJefeDelAlmacen.add(new SelectItem(null,ConstanteWeb.CAMPO_SELECCIONE));
			listaIndicadorRecepcionDelJefeDelAlmacen.add(new SelectItem((Integer)ConstanteWeb.REQ_APROBACION_JEFE_VALOR_NO_RECEPCIONADO,ConstanteWeb.REQ_APROBACION_JEFE_CAMPO_NO_RECEPCIONADO));
			listaIndicadorRecepcionDelJefeDelAlmacen.add(new SelectItem((Integer)ConstanteWeb.REQ_APROBACION_JEFE_VALOR_RECEPCIONADO,ConstanteWeb.REQ_APROBACION_JEFE_CAMPO_RECEPCIONADO));
		}
		
		//Prueba de método para limpiar la lista listaIndicadorRecepcionEncargadoDelArea.
		public void resetearListaIndicadorRecepcionDelJefeDelAlmacen()
		{
			listaIndicadorRecepcionDelJefeDelAlmacen = new ArrayList<>();
		}
				
				
		//Prueba para carga la lista listIndicadorRecepcionDelJefeDelAlmacen
		public void generarListaIndicadorEnvioAlEncargadoDelAlmacen()
		{
			listaIndicadorEnvioAlEncargadoDelAlmacen = new ArrayList<>();
			listaIndicadorEnvioAlEncargadoDelAlmacen.add(new SelectItem(null,ConstanteWeb.CAMPO_SELECCIONE));
			listaIndicadorEnvioAlEncargadoDelAlmacen.add(new SelectItem((Integer)ConstanteWeb.REQ_APROBACION_JEFE_VALOR_NO_ENVIADO,ConstanteWeb.REQ_APROBACION_JEFE_CAMPO_NO_ENVIADO));
			listaIndicadorEnvioAlEncargadoDelAlmacen.add(new SelectItem((Integer)ConstanteWeb.REQ_APROBACION_JEFE_VALOR_ENVIADO,ConstanteWeb.REQ_APROBACION_JEFE_CAMPO_ENVIADO));
		}
		
		
		//Prueba de método para limpiar la lista listaIndicadorEnvioAlEncargadoDelAlmacen.
		public void resetearListaIndicadorEnvioAlEncargadoDelAlmacen()
		{
			listaIndicadorEnvioAlEncargadoDelAlmacen = new ArrayList<>();
		}
					
		
		/*************************************************************************************************/
		/*************************          EXPRESSION LENGUAGE                     **********************/	
		/************************************************************************************************/
		
		
		//Prueba para cargar el outputlabel del indicador de envío al jefe de área en la tabla de requerimiento área
		public String obtenerStringIndicadorEnviadoAlJefe(Short indicador)
		{
			String valor ="";
			switch (indicador) 
			{
			case 0: valor="NO ENVIADO";	break;			
			case 1: valor="ENVIADO";	break;			
			default:valor="VALOR POR DEFECTO";
				break;
			}			
			return valor;
		}
		
		//Prueba para cargar el outputlabel del indicador de recepción del jefe en la tabla de requerimiento área.
		public String obtenerStringIndicadorRecepcionDelJefe(Short indicador)
		{
			String valor="";
			switch (indicador) 
			{
			case 0:valor="NO RECEPCIONADO";break;
			case 1:valor="RECEPCIONADO";break;
			case 2:valor="DEVUELTO/RECHAZADO";break;
			case 3:valor="APROBADO";break;
			default:valor="VALOR POR DEFECTO";break;
			}
			return valor;
		}
		
		
		/*************************************************************************************************/
		/*************************DETALLES DE REQUERIMIENTO APROBADOS                     ****************/	
		/************************************************************************************************/
		
		//Prueba para abrir el popup detalles de requerimientos aprobados desde el form jefe de almacén.
		public void abrirPopupDetallesRequerimientosAprobados(Integer posicion)
		{			
			this.paraDerivarAlEncargadoDeAlmacen = true;
			this.indexProgramacion = posicion;
//			buscarRequerimientoAreaJefe();
			//Traer la lista de programaciones.	
			List<ProgramacionDTO>listaProgramacion = new ArrayList<>();
			programacionBean.buscarProgramacion();			
			listaProgramacion = programacionBean.getListaProgramacion();
			//Ubicar la programación en la ubicación 'posicion'.
			//setear los valores de programacion a programacionDTO en el bean RequerimientoAreaBean.
			this.programacionSeleccionAlmacen = listaProgramacion.get(posicion);
			//Prueba para buscar requerimiento de jefe de almacen
			Integer programacionActualId = this.programacionSeleccionAlmacen.getId();			
			buscarRequerimientoParaJefeAlmacen(programacionActualId);
			Integer programacionSeleccionAlmacenId = this.programacionSeleccionAlmacen.getId();
			//Traer todos los requerimientos aprobados que corresponda a la programaciónActual.
			this.listaRequerimientoAreaAprobados = new ArrayList<>();
			
			for(RequerimientoAreaDTO objeto:this.listaRequerimientoAreaJefe)
			{
				//3 : APROBADO
				if(objeto.getIndicadorRequerimientoRecepcionDelJefe() == 3 && objeto.getProgramacion().getId()==programacionSeleccionAlmacenId)
				{
					System.out.println("El requerimiento de área :"+objeto.getId()+" ha sido aprobado");
					this.listaRequerimientoAreaAprobados.add(objeto);
				}
				else
				{
					System.out.println("El requerimiento de áre"+objeto.getId()+"no ha sido aprobado");
				}
			}
			
			//setear los objetos a la tabla 
			if(this.programacionSeleccionAlmacen.getEstado().getId() == 6)
			{
				this.paraDerivarAlEncargadoDeAlmacen = false;
				
				this.avisoDerivado = "La programación de : "+this.programacionSeleccionAlmacen.getDescripcionProgramacion()+
						" se derivó al encargado de almacén.";
				
				context = RequestContext.getCurrentInstance();
				context.execute("PF('dialogoAbrirPopupDetallesRequerimientosAprobados').show();");
				

				
			}
			else
			{				
				this.paraDerivarAlEncargadoDeAlmacen= true;
				this.avisoDerivado = "";
				
				
				
				context = RequestContext.getCurrentInstance();
				context.execute("PF('dialogoAbrirPopupDetallesRequerimientosAprobados').show();");
			}
			
			
							
		}
		
		
		
		
		
		//Prueba de método para buscar los requerimientos de área.
		 public void buscarRequerimientoParaJefeAlmacen(Integer programacionId)
		 {		 			 
			 try 
			 {	
				 //Prueba para obtener el areaId en función del usuario	
				 cargarUsuario(this.loginUsuarioBean.getUsuarioDTO().getId(),null ,null);
				 
				 //Prueba para cargar el usuario perfil
//				 Integer usuarioId = this.usuarioDTO.getId();
//				 List<UsuarioPerfilesDTO>listaUsuarioPerfiles = new ArrayList<>();
//				 listaUsuarioPerfiles = this.usuarioPerfilesServiceLocal.buscarUsuarioPerfiles(null, usuarioId, null, null);
//				 this.usuarioPerfilesDTO = new UsuarioPerfilesDTO();
//				 this.usuarioPerfilesDTO = listaUsuarioPerfiles.get(0);
//				 Integer usuarioPerfilId = this.usuarioPerfilesDTO.getPerfil().getId();
//				 Integer perfilJefeAlmacen = 5;
				 Integer perfilEncargadoAlmacen = 6;
				 
				 //Integer programacionActualId = this.programacionDTO.getId();
				 this.listaRequerimientoParaJefeAlmacen = new ArrayList<RequerimientoAreaDTO>();		 
				 if(requerimientoAreaId ==null && 
				    indicadorEnvioAlJefe == null && 
				    indicadorRecepcionDelJefe ==null && 
				    areaId == null)
				 {
						 List<RequerimientoAreaDTO>listaReal = requerimientoAreaServiceLocal.buscarRequerimientoArea(null, null, null, null, null);
						 for (RequerimientoAreaDTO requerimientoAreaDTO : listaReal) 
						 {
							 if(requerimientoAreaDTO.getId()>=0 && requerimientoAreaDTO.getId()!=null && requerimientoAreaDTO.getProgramacion().getId() == programacionId
									 && requerimientoAreaDTO.getIndicadorEnviadoAlJefe()==1 && requerimientoAreaDTO.getIndicadorRequerimientoRecepcionDelJefe()==3 && this.usuarioPerfilesDTO.getPerfil().getId() == perfilEncargadoAlmacen)
							 {
								 this.listaRequerimientoParaJefeAlmacen.add(requerimientoAreaDTO);
							 }
							 else
							 {
								 System.out.println("No se pudo agregar el objeto, tal vez el usuario no es jefe de almacén.");
							 }
						 }				
				 }
				 else
				 {			 
					 this.listaRequerimientoArea = requerimientoAreaServiceLocal.buscarRequerimientoArea(requerimientoAreaId, areaId, indicadorEnvioAlJefe, indicadorRecepcionDelJefe,programacionId);
				 }			
			 } 
			 catch (Exception e) 
			 {
				 System.out.println("Error : "+e.getMessage());
			 }
		 }
		
		
		
		
		
		
		
		

		/************************************************************************************************/
		/************************************************************************************************/
		/******************* VER  REQUERIMIENTOS USUARIOS CONSOLIDADOS DESDE EL JEFE DE ALMACEN       ********************************/
		/************************************************************************************************/
		/************************************************************************************************/
		
		 
			//Prueba de método para abrir el popup de agregar requerimientos de usuarios.
			public void abrirPopupAgregarRequerimientoUsuariosDesdeAlmacen(Integer posicion) throws Exception
			{		
				//Prueba para cargar los combos del formulario actualizar requerimiento área.
				generarListaIndicadorEnviadoAlJefe();
				generarListaIndicadorRecepcionDelJefe();
				generarListaEstado();
				cargarProgramacion();
				Integer programacionId = this.programacionDTO.getId();
				buscarRequerimientoParaJefeAlmacen(programacionId);
				//Prueba para obtener los datos del objeto seleccinado en la lista de requerimiento de área.	
				obtenerDatosRequerimientoAreaParaJefeAlmacen(posicion);	
				
				if(this.requerimientoAreaDTO.getIndicadorRequerimientoRecepcionDelJefe() != 3)
				{
//					System.out.println("Ya se ha enviado el requerimiento de área hacia el jefe, por ello no puede actualizarse.");
					System.out.println("El requerimiento de área no ha sido aprobado.");
					FacesContext.getCurrentInstance().addMessage("RequerimientoAreaEnviadoAlJefe", 
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR, "Error!", 
									"Ya se ha enviado el requerimiento de área "+this.requerimientoAreaDTO.getId()+ 
									" hacia el jefe, por ello no puede actualizarse.")						
							);
				}
				else
				{
					//cargarProgramacion();		
					cargarProgramacionRequerimientoArea(this.requerimientoAreaDTO.getFechaRegistro());
					Integer areaId = this.requerimientoAreaDTO.getArea().getId();
					cargarAreaXRequerimiento(areaId);
					
					//Prueba para mostrar las cabeceras de columnas y de fila
					Integer idProgramacion =this.programacionDTO.getId();
					Integer idArea = this.areaDTO.getId();	
					cargarProgramacionAreas(idProgramacion,idArea);
					//Método pra cargar el area en la tabla
					cargarAreaEnTablaXAreaId(areaId);
					//Método para cargar los productos pasándole como parámetro la programacionAreaId.
					Integer idProgramacionArea = this.programacionAreasDTO.getId();
					cargarProductosEnTablaXAreaId(idProgramacionArea,idArea);
					//Método para inicializar los valores del ArregloValoresAreaProducto.
					inicializarArregloValoresAreaProducto();
					//Prueba para traer la cantidad de productos seleccionados desde la BD	
					cargarValoresAreaProductoDesdeBD(idProgramacionArea);
					//Prueba para cargar la descripción de los usuarios como cabecera de fila.
					cargarFilaUsuarioEnTabla();
					//Prueba para inicializar el arregloValoresRequerimientoUsuario
					inicializarArregloValoresRequerimientoUsuario();
					//Prueba para cargar valores al arregloAgregarRequerimientoUsuarios
					cargarValoresAgregarRequerimientoUsuarioDesdeBD(idProgramacionArea);
					
					context = RequestContext.getCurrentInstance();
					context.execute("PF('dialogoAgregarRequerimientoUsuariosDesdeAlmacen').show();");	

				}
				
				
			}

		
			 //Prueba para obtener todos los datos del requerimiento de área.
			 public void obtenerDatosRequerimientoAreaParaJefeAlmacen(Integer posicion)
			 {
			 RequerimientoAreaDTO objteo = this.listaRequerimientoParaJefeAlmacen.get(posicion.intValue());
			 this.requerimientoAreaDTO = new RequerimientoAreaDTO();
			 this.requerimientoAreaDTO.setId(objteo.getId());
			 this.requerimientoAreaDTO.setIndicadorEnviadoAlJefe(objteo.getIndicadorEnviadoAlJefe());
			 this.requerimientoAreaDTO.setFechaEnviadoAlJefe(objteo.getFechaEnviadoAlJefe());
			 this.requerimientoAreaDTO.setIndicadorRequerimientoRecepcionDelJefe(objteo.getIndicadorRequerimientoRecepcionDelJefe());
			 this.requerimientoAreaDTO.setFechaRequerimientoRecepcionDelJefe(objteo.getFechaRequerimientoRecepcionDelJefe());
			 this.requerimientoAreaDTO.setIndicadorActivo(objteo.getIndicadorActivo());
			 this.requerimientoAreaDTO.setCodigoUsuarioRegistro(objteo.getCodigoUsuarioRegistro());
			 this.requerimientoAreaDTO.setIpRegistro(objteo.getIpRegistro());
			 this.requerimientoAreaDTO.setFechaRegistro(objteo.getFechaRegistro());
			 this.requerimientoAreaDTO.setCodigoUsuarioModificacion(objteo.getCodigoUsuarioModificacion());
			 this.requerimientoAreaDTO.setIpModificacion(objteo.getIpModificacion());
			 this.requerimientoAreaDTO.setFechaModificacion(objteo.getFechaModificacion());
			 this.requerimientoAreaDTO.setEstado(new EstadoDTO());
			 this.requerimientoAreaDTO.getEstado().setId(objteo.getEstado().getId());
			 this.requerimientoAreaDTO.setArea(new AreaDTO());
			 this.requerimientoAreaDTO.getArea().setId(objteo.getArea().getId());
			 this.requerimientoAreaDTO.getArea().setDescripcionArea(objteo.getArea().getDescripcionArea());
			 this.requerimientoAreaDTO.setProgramacion(new ProgramacionDTO());
			 this.requerimientoAreaDTO.getProgramacion().setId(objteo.getProgramacion().getId());
			 this.requerimientoAreaDTO.getProgramacion().setDescripcionProgramacion(objteo.getProgramacion().getDescripcionProgramacion());
			 this.requerimientoAreaDTO.getEstado().setDescripcionEstado(objteo.getEstado().getDescripcionEstado());
			 this.requerimientoAreaDTO.getArea().setDescripcionArea(objteo.getArea().getDescripcionArea());
			 this.requerimientoAreaDTO.getProgramacion().setDescripcionProgramacion(objteo.getProgramacion().getDescripcionProgramacion());
			 this.requerimientoAreaDTO.getProgramacion().setTipoPedido(new TipoDTO());
			 this.requerimientoAreaDTO.getProgramacion().getTipoPedido().setDescripcionTipo(objteo.getProgramacion().getTipoPedido().getDescripcionTipo());
			 this.requerimientoAreaDTO.getProgramacion().setTipoPeriodo(new TipoDTO());
			 this.requerimientoAreaDTO.getProgramacion().getTipoPeriodo().setDescripcionTipo(objteo.getProgramacion().getTipoPeriodo().getDescripcionTipo());
			 }

		
			//Prueba de método para cargar el área.
				public void cargarAreaXRequerimiento(Integer areaId)
				{
					List<ProgramacionAreasDTO>listaProgramacionArea = new ArrayList<>();		
					int programacionId = this.programacionDTO.getId();
//					int areaId = this.usuarioDTO.getArea().getId();
					this.areaDTO = new AreaDTO();
					ProgramacionAreasDTO programacionAreaDTO = new ProgramacionAreasDTO();
					try 
					{
						listaProgramacionArea = programacionAreaServiceLocal.buscarProgramacionAreas(programacionId , areaId);
						programacionAreaDTO = listaProgramacionArea.get(0);
						this.areaDTO.setId(programacionAreaDTO.getArea().getId());
						this.areaDTO.setDescripcionArea(programacionAreaDTO.getArea().getDescripcionArea());
					}
					catch (Exception e) 
					{
						log.error(e.getMessage(),e);
					}
				}
				
				
				//Agregar la descripción del área en el cual estamos
				public void cargarAreaEnTablaXAreaId(Integer areaId)
				{
					try 
					{
						//Comparamos si el area de la programacionarea es la misma que el area en la que está el usuario.
					if(this.programacionAreasDTO.getArea().getId() == areaId)
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

				
				
				
				
				

				//Método que permite cargar los productos de una programaciónArea específica,
				//es decir de una programación específica y un área específica.
				public void cargarProductosEnTablaXAreaId(Integer programacionAreasId,Integer areaId)
				{
					List<DetalleProgramacionAreaProductoDTO>listaDetalles = new ArrayList<DetalleProgramacionAreaProductoDTO>();		
					try 
					{	
					//Comparamos si el area de la programacionarea es la misma que el area en la que está el usuario.
						if(this.programacionAreasDTO.getArea().getId() == areaId)
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

				
				
				/************************************************************************************************/
				/************************************************************************************************/
				/******************* VER POPUP DERIVACION AL ENCARGADO DE ALMACEN********************************/
				/************************************************************************************************/
				/************************************************************************************************/
		
				//Prueba de método para abrir el dialogo de confirmación para la derviación de los requerimientos de área hacia
				//el encargado de almacén
				public void abrirPopupConfirmarDerivacionAlEncargadoDelArea()
				{
					context = RequestContext.getCurrentInstance();
					context.execute("PF('dialogoConfirmarDerivacionAlEncargadoDelArea').show();");
				}
				
				
				//Prueba de método para verificar si la cantidad de requerimientos de áreas para determinada programación
				//es igual a la cantidad de requerimientos de área aprobados,si es igual derivar al encargado de almacén
				//,si es menor mostrar mensaje que faltan requerimeintos por aprobar requerimientos de área por aprobar.
				public void verificarRequerimientoAreaProgrVSRequAprob() throws Exception
				{					
					List<ProgramacionDTO>listaProgramacion = new ArrayList<>();
					programacionBean.buscarProgramacion();							
					listaProgramacion = programacionBean.getListaProgramacion();
					//Ubicar la programación en la ubicación 'posicion'.
					//setear los valores de programacion a programacionDTO en el bean RequerimientoAreaBean.
					this.programacionSeleccionAlmacen = listaProgramacion.get(this.indexProgramacion);
										
					Integer programacionId = this.programacionSeleccionAlmacen.getId();
					List<ProgramacionAreasDTO>listaProgramacionArea = new ArrayList<>();
					listaProgramacionArea = this.programacionAreaServiceLocal.buscarProgramacionAreas(programacionId, null);					
					
					Integer cantidadRequAprobados = this.listaRequerimientoParaJefeAlmacen.size();
					Integer cantidadRequeAreaProg = listaProgramacionArea.size();
					
					
					if(cantidadRequeAreaProg!=0 && cantidadRequAprobados!=0 && cantidadRequeAreaProg==cantidadRequAprobados)
					{
						System.out.println("Son iguales entonces ya se puede derivar al encargado de almacén");
						boolean seDerivo =derivarRquerimientoAreasAlEncargadoDeAlmacen();
						if(seDerivo == true)
						{
							//Prueba para actualizar la programación actual y setear su estado a 6:
							boolean seActualizoProgrActual = actualizarProgramacionActual();
							String mensajeActualizoProgrActual = "";
							if(seActualizoProgrActual == true)
							{
								mensajeActualizoProgrActual ="Se actualizó la programación :";
							}
							else
							{
								mensajeActualizoProgrActual ="No se pudo actualizar la programación :";
							}
							
							cerrarPopupConfirmarDerivacionAlEncargadoDelArea();
							cerrarDialogoAbrirPopupDetallesRequerimientosAprobados();
							FacesContext context = FacesContext.getCurrentInstance();
							context.addMessage(null, new FacesMessage
									("Aviso","Se derivaron los requerimientos de área aprobados de la programación de :"+this.programacionSeleccionAlmacen.getDescripcionProgramacion()
											+" al encargado del almacén. "+mensajeActualizoProgrActual+" "+this.programacionSeleccionAlmacen.getDescripcionProgramacion()));	
						}
						else
						{
							System.out.println("No se pudo derivar al encargado del almacén");
							FacesContext.getCurrentInstance().addMessage("ErrorNoSePudoDerivar", 
									new FacesMessage(
											FacesMessage.SEVERITY_ERROR, "Error!", 
											"No se pudo derivar al encargado del almacén."));
							cerrarPopupConfirmarDerivacionAlEncargadoDelArea();
							cerrarDialogoAbrirPopupDetallesRequerimientosAprobados();
						}
						
												
					}
					else
					{
						System.out.println("No se puede derivar porque faltan cargar requreimientos aprobados de algunas áreas.");
						FacesContext.getCurrentInstance().addMessage("ErrorCantidadesDiferentesRequAreaVSAprobados", 
								new FacesMessage(
										FacesMessage.SEVERITY_ERROR, "Error!", 
										"No se puede derivar los requerimientos de la programación de : "+this.programacionSeleccionAlmacen.getDescripcionProgramacion() 
										+" porque faltan cargar requrerimientos aprobados de algunas áreas."));
						cerrarPopupConfirmarDerivacionAlEncargadoDelArea();
						cerrarDialogoAbrirPopupDetallesRequerimientosAprobados();						
					}
					
				}
				
				//Prueba de método para cerrar el dialogo de confirmación para la derviación de los requerimientos de área hacia
				//el encargado de almacén
				public void cerrarPopupConfirmarDerivacionAlEncargadoDelArea()
				{
					context = RequestContext.getCurrentInstance();
					context.execute("PF('dialogoConfirmarDerivacionAlEncargadoDelArea').hide();");
				}
				
				//Prueba de método para cerrar el popup de requerimientos aprobados.			
				public void cerrarDialogoAbrirPopupDetallesRequerimientosAprobados()
				{
					context = RequestContext.getCurrentInstance();
					context.execute("PF('dialogoAbrirPopupDetallesRequerimientosAprobados').hide();");
					
				}
				
				//Prueba de método para los requerimientos de área de la programación actual, hacia el encargado de 
				//almacén.
				public boolean derivarRquerimientoAreasAlEncargadoDeAlmacen() throws Exception
				{
					System.out.println("Hola soy goku");
					if(this.listaRequerimientoParaJefeAlmacen!=null)
					{
						for(RequerimientoAreaDTO requerimientoAprobado:this.listaRequerimientoParaJefeAlmacen)
						{
							//Registrar el requerimiento aprobado en la tabla detalle jefe almcen
							boolean seRegistro= setearValoresADetalleJefeAlmacenDTO(requerimientoAprobado);
							if(seRegistro == true)
							{
								System.out.println("Se registró el detalle jefe almacén");
							}
							else
							{
								System.out.println("Error, no se pudo registrar el detalle jefe almacén.");
								break;
							}
							
						}
					return true;	
					}
					else
					{
						System.out.println("La lista listaRequerimientoParaJefeAlmacen no tiene objetos");
						return false;
					}
																
					
				}
				
				//Prueba para setear los valores a la variable DetalleJefeAlmacenDTO.
				public boolean setearValoresADetalleJefeAlmacenDTO(RequerimientoAreaDTO requerimientoAprobado) throws Exception
				{					
					Integer jefeAlmacenId = obtenerJefeAlmacenId();
					List<RequerimientoJefeAprobacionDTO>listaRequerimientoJefeAprobacion = new ArrayList<>();
					listaRequerimientoJefeAprobacion = this.requerimientoJefeAprobacionServiceLocal.buscarRequerimientoJefeAprobacion(null, null, requerimientoAprobado.getId(),null, null);
					Integer requerimientoJefeAprobadoId ;
					if(listaRequerimientoJefeAprobacion!=null && listaRequerimientoJefeAprobacion.size()==1)
					{
						requerimientoJefeAprobadoId = listaRequerimientoJefeAprobacion.get(0).getId();
					}
					else
					{
						requerimientoJefeAprobadoId = null; 
						System.out.println("No tiene requerimiento aprobado, ó es diferente de 1");
					}
					
					//1:RECIBIDO
					Short indicadorRecepcionJefeArea=1;
					//1:ENVIADO
					Short indicadorEnviadoEncargadoAlmacen=1;
					java.util.Date fechaActualJava = new Date();
					java.sql.Date fechaRecepcionJefeArea = new java.sql.Date(fechaActualJava.getTime());
					java.sql.Date  fechaEnviadoEncargadoAlmacen = new java.sql.Date(fechaActualJava.getTime());				
					Short indicadorActivo = 1;
					String codigoUsuarioRegistro = this.usuarioDTO.getDescripcionUsuario();
					//1:REGISTRADO
					Integer estadoRegistrado=1;
					this.detalleJefeAlmacenDTO = new DetalleJefeAlmacenDTO();
					this.detalleJefeAlmacenDTO.setJefeAlmacen(new UsuarioDTO());
					this.detalleJefeAlmacenDTO.getJefeAlmacen().setId(jefeAlmacenId);
					this.detalleJefeAlmacenDTO.setRequerimientoAprobacionJefe(new RequerimientoAreaDTO());
					this.detalleJefeAlmacenDTO.getRequerimientoAprobacionJefe().setId(requerimientoJefeAprobadoId);
					this.detalleJefeAlmacenDTO.setIndicadorRecepcionJefeArea(indicadorRecepcionJefeArea);
					this.detalleJefeAlmacenDTO.setFechaRecepcionJefeArea(fechaRecepcionJefeArea);
					this.detalleJefeAlmacenDTO.setIndicadorEnviadoEncargadoAlmacen(indicadorEnviadoEncargadoAlmacen);
					this.detalleJefeAlmacenDTO.setFechaEnviadoEncargadoAlmacen(fechaEnviadoEncargadoAlmacen);
					this.detalleJefeAlmacenDTO.setIndicadorActivo(indicadorActivo);
					this.detalleJefeAlmacenDTO.setCodigoUsuarioRegistro(codigoUsuarioRegistro);
					this.detalleJefeAlmacenDTO.setIpRegistro(obtieneIPCliente());
					this.detalleJefeAlmacenDTO.setEstado(new EstadoDTO());
					this.detalleJefeAlmacenDTO.getEstado().setId(estadoRegistrado);
					this.detalleJefeAlmacenDTO.setRequerimientoArea(new RequerimientoAreaDTO());
					this.detalleJefeAlmacenDTO.getRequerimientoArea().setId(requerimientoAprobado.getId());					
					this.detalleJefeAlmacenDTO = this.detalleJefeAlmacenServiceLocal.registrarDetalleJefeAlmacen(this.detalleJefeAlmacenDTO);
					if(this.detalleJefeAlmacenDTO!=null)
					{
						return true;	
					}
					else
					{
						return false;
					}
												
				}
				
				
				public boolean actualizarProgramacionActual() throws Exception
				{
					ProgramacionDTO programacionActual = this.programacionSeleccionAlmacen;
					int estadoEnviado = 6;
					programacionActual.getEstado().setId(estadoEnviado);
					boolean seActualizoProgrActual = this.programacionServiceLocal.actualizarProgramacion(programacionActual);
					if(seActualizoProgrActual==true)
					{
						return true;
					}
					else
					{
						return false;
					}					
				}
				
				
				
				//Prueba para obtener el id del jefe de almacen
				public Integer obtenerJefeAlmacenId() throws Exception
				{					
					//PERFIL JEFE ALMACEN : 5
					Integer perfilJefeAlmacen = 5;
					//Registrado y activo =1
					Integer estadoId = 1;
					//id area almacen = 13
					Integer areaId = 13;
					List<UsuarioPerfilesDTO>listaUsuarioPerfiles = new ArrayList<>();
					//perfilJefeAlmacen, estadoId, areaId
					listaUsuarioPerfiles = this.usuarioPerfilesServiceLocal.buscarUsuarioPerfilesXAreaId(null, null, perfilJefeAlmacen, estadoId, areaId);
					Integer jefeAlmacenId=null;
					if(listaUsuarioPerfiles !=null && listaUsuarioPerfiles.size() ==1)
					{
						jefeAlmacenId = listaUsuarioPerfiles.get(0).getUsuario().getId();
					}
					else
					{
						System.out.println("Existe más de 1 jefe de almacén, tendría que asignar a solo 1 como activo en el estado del usuario");
						jefeAlmacenId = null;
						
					}
					return jefeAlmacenId; 
				}
				
				//Prueba para obtener el id del jefe de almacen
				public Integer obtenerJefeAreaId(Integer areaId) throws Exception
				{					
					//PERFIL JEFE ALMACEN : 3
					Integer perfilJefeArea= 3;
					//Registrado y activo =1
					Integer estadoId = 1;
					List<UsuarioPerfilesDTO>listaUsuarioPerfiles = new ArrayList<>();
					listaUsuarioPerfiles = this.usuarioPerfilesServiceLocal.buscarUsuarioPerfilesXAreaId(null, null, perfilJefeArea, estadoId, areaId);
					Integer jefeAreaId=null;
					if(listaUsuarioPerfiles !=null && listaUsuarioPerfiles.size() ==1)
					{
						jefeAreaId = listaUsuarioPerfiles.get(0).getUsuario().getId();
					}
					else
					{
						System.out.println("Existe más de 1 jefe de área, tendría que asignar a solo 1 como activo en el estado del usuario");
						jefeAreaId = null;
						
					}
					
					return jefeAreaId;
				}

				
				
				//Prueba para abrir el popup detalles de requerimientos aprobados desde el form encargado de almacén.
				public void abrirPopupDetallesRequerimientosAprobadosEncargadoAlmacen(Integer posicion) throws Exception
				{			
					this.paraDerivarAlEncargadoDeAlmacen = true;
					this.indexProgramacion = posicion;
//					buscarRequerimientoAreaJefe();
					//Traer la lista de programaciones.	
					List<ProgramacionDTO>listaProgramacion = new ArrayList<>();
					programacionBean.buscarProgramacion();			
					listaProgramacion = programacionBean.getListaProgramacion();
					//Ubicar la programación en la ubicación 'posicion'.
					//setear los valores de programacion a programacionDTO en el bean RequerimientoAreaBean.
					this.programacionSeleccionAlmacen = listaProgramacion.get(posicion);
					//Prueba para buscar requerimiento de jefe de almacen
					Integer programacionActualId = this.programacionSeleccionAlmacen.getId();			
					buscarRequerimientoParaJefeAlmacen(programacionActualId);
					Integer programacionSeleccionAlmacenId = this.programacionSeleccionAlmacen.getId();
					//Traer todos los requerimientos aprobados que corresponda a la programaciónActual.
					this.listaRequerimientoAreaAprobados = new ArrayList<>();
					
					for(RequerimientoAreaDTO objeto:this.listaRequerimientoParaJefeAlmacen)
					{
						//3 : APROBADO
						if(objeto.getIndicadorRequerimientoRecepcionDelJefe() == 3 && objeto.getProgramacion().getId()==programacionSeleccionAlmacenId)
						{
							System.out.println("El requerimiento de área : "+objeto.getId()+" ha sido aprobado");
							this.listaRequerimientoAreaAprobados.add(objeto);

							
							boolean verificarRegistro = verificarRegistroEnEncargadoAlmacen(objeto);
							if(verificarRegistro == true)
							{
								System.out.println("El requerimiento aprobado ya se registró en la tabla encargado almacen");								
							}
							else
							{
								//Prueba para guardar el requerimeinto de área e indicar que el encargado de almacén ya lo ha recibido						
								boolean seRegistroEnEncargadoAlmacen = registrarRequerimientoAreaEnTablaDetalleEncargadoAlmacen(objeto);
								if(seRegistroEnEncargadoAlmacen == true)
								{
									System.out.println("Se registró el requerimeinto en la tabla Encargado almacen");
								}
								else
								{
									System.out.println("ERROR : NO SE REGISTRÓ EL REQUERIMEINTO EN ENCARGADO ALMACEN");
								}

							}
							
						
						}
						else
						{
							System.out.println("El requerimiento de área : "+objeto.getId()+"no ha sido aprobado");
						}
					}
					
					//setear los objetos a la tabla 
					if(this.programacionSeleccionAlmacen.getEstado().getId() == 6)
					{
						this.paraDerivarAlEncargadoDeAlmacen = false;
						
						this.avisoDerivado = "La programación de : "+this.programacionSeleccionAlmacen.getDescripcionProgramacion()+
								" se derivó al encargado de almacén.";
						
						context = RequestContext.getCurrentInstance();
						context.execute("PF('dialog" +
								"oAbrirPopupDetallesRequerimientosAprobadosEncargadoAlmacen').show();");
						

						
					}
					else
					{				
						this.paraDerivarAlEncargadoDeAlmacen= true;
						this.avisoDerivado = "";
						
						
						
						context = RequestContext.getCurrentInstance();
						context.execute("PF('dialogoAbrirPopupDetallesRequerimientosAprobadosEncargadoAlmacen').show();");
					}
					
					
									
				}
				
				
				
				//Prueba de método para abrir el popup de agregar requerimientos de usuarios.
				public void abrirPopupAgregarRequerimientoUsuariosDesdeEncargadoAlmacen(Integer posicion) throws Exception
				{		
					//Prueba para cargar los combos del formulario actualizar requerimiento área.
					generarListaIndicadorEnviadoAlJefe();
					generarListaIndicadorRecepcionDelJefe();
					generarListaEstado();
					cargarProgramacion();
					Integer programacionId = this.programacionDTO.getId();
					buscarRequerimientoParaJefeAlmacen(programacionId);
					//Prueba para obtener los datos del objeto seleccinado en la lista de requerimiento de área.	
					obtenerDatosRequerimientoAreaParaJefeAlmacen(posicion);	
					
					if(this.requerimientoAreaDTO.getIndicadorRequerimientoRecepcionDelJefe() != 3)
					{
//						System.out.println("Ya se ha enviado el requerimiento de área hacia el jefe, por ello no puede actualizarse.");
						System.out.println("El requerimiento de área no ha sido aprobado.");
						FacesContext.getCurrentInstance().addMessage("RequerimientoAreaEnviadoAlJefe", 
								new FacesMessage(
										FacesMessage.SEVERITY_ERROR, "Error!", 
										"Ya se ha enviado el requerimiento de área "+this.requerimientoAreaDTO.getId()+ 
										" hacia el jefe, por ello no puede actualizarse.")						
								);
					}
					else
					{
						//cargarProgramacion();		
						cargarProgramacionRequerimientoArea(this.requerimientoAreaDTO.getFechaRegistro());
						Integer areaId = this.requerimientoAreaDTO.getArea().getId();
						cargarAreaXRequerimiento(areaId);
						
						//Prueba para mostrar las cabeceras de columnas y de fila
						Integer idProgramacion =this.programacionDTO.getId();
						Integer idArea = this.areaDTO.getId();	
						cargarProgramacionAreas(idProgramacion,idArea);
						//Método pra cargar el area en la tabla
						cargarAreaEnTablaXAreaId(areaId);
						//Método para cargar los productos pasándole como parámetro la programacionAreaId.
						Integer idProgramacionArea = this.programacionAreasDTO.getId();
						cargarProductosEnTablaXAreaId(idProgramacionArea,idArea);
						//Método para inicializar los valores del ArregloValoresAreaProducto.
						inicializarArregloValoresAreaProducto();
						//Prueba para traer la cantidad de productos seleccionados desde la BD	
						cargarValoresAreaProductoDesdeBD(idProgramacionArea);
						//Prueba para cargar la descripción de los usuarios como cabecera de fila.
						cargarFilaUsuarioEnTabla();
						//Prueba para inicializar el arregloValoresRequerimientoUsuario
						inicializarArregloValoresRequerimientoUsuario();
						//Prueba para cargar valores al arregloAgregarRequerimientoUsuarios
						cargarValoresAgregarRequerimientoUsuarioDesdeBD(idProgramacionArea);
						
						context = RequestContext.getCurrentInstance();
						context.execute("PF('dialogoAgregarRequerimientoUsuariosDesdeEncargadoAlmacen').show();");	

					}
					
					
				}

				
				
				//Prueba de método para registrar lso requerimientos de área aprbados que son derviados por el jefe de almacen al encargado de almacén
				public boolean registrarRequerimientoAreaEnTablaDetalleEncargadoAlmacen(RequerimientoAreaDTO requerimientiAreaAprobado) throws Exception
				{
					//Necesito el id del requerimiento area
					Integer requerimientoAreaId = requerimientiAreaAprobado.getId();					
					
					//Busco el id del detalle jefe almacen por el id del requerimeinto area
					List<DetalleJefeAlmacenDTO>listaDetalleJefeAlmacen=new ArrayList<>();
					listaDetalleJefeAlmacen = this.detalleJefeAlmacenServiceLocal.buscarDetalleJefeAlmace(null, null, null, null, null, null,requerimientoAreaId);
					DetalleJefeAlmacenDTO detalleJefeAlmacenDTO = new DetalleJefeAlmacenDTO();
					detalleJefeAlmacenDTO = listaDetalleJefeAlmacen.get(0);									
					
					//luego registro el el requerimeinto de area en detalle encargado almacen con el indicador de recibido de jefe almacen
					Integer encargadoAlmacenId = this.usuarioPerfilesDTO.getId();
					Integer detalleJefeAlmacenId = detalleJefeAlmacenDTO.getId();
					//1:RECIBIDO
					Short indicadorRecepcionJefeAlmacen = 1;
					Short indicadorActivo = 1;
					String codigoUsuarioRegistro = this.usuarioPerfilesDTO.getUsuario().getDescripcionUsuario();
					Integer estadoRegistrado=1;
					
					DetalleEncargadoAlmacenDTO detalleEncargadoAlmacenDTO = new DetalleEncargadoAlmacenDTO();
					detalleEncargadoAlmacenDTO.setEncargado(new UsuarioPerfilesDTO());
					detalleEncargadoAlmacenDTO.getEncargado().setId(encargadoAlmacenId);
					detalleEncargadoAlmacenDTO.setDetalleJefeAlmacen(new DetalleJefeAlmacenDTO());
					detalleEncargadoAlmacenDTO.getDetalleJefeAlmacen().setId(detalleJefeAlmacenId);
					detalleEncargadoAlmacenDTO.setIndicadorRecepcionJefeAlmacen(indicadorRecepcionJefeAlmacen);
					detalleEncargadoAlmacenDTO.setIndicadorActivo(indicadorActivo);
					detalleEncargadoAlmacenDTO.setCodigoUsuarioRegistro(codigoUsuarioRegistro);
					detalleEncargadoAlmacenDTO.setIpRegistro(obtieneIPCliente());
					detalleEncargadoAlmacenDTO.setEstado(new EstadoDTO());
					detalleEncargadoAlmacenDTO.getEstado().setId(estadoRegistrado);
					
					this.detalleEncargadoAlmacenDTO = this.detalleEncargadoAlmacenServiceLocal.registrarDetalleEncargadoAlmacen(detalleEncargadoAlmacenDTO);
					if(this.detalleEncargadoAlmacenDTO != null)
					{
						return true;
					}
					else
					{
						return false;
					}
				}
		
				//Prueba de método para verificar si existe un registro en la tabla encargado almacen
				public boolean verificarRegistroEnEncargadoAlmacen(RequerimientoAreaDTO objeto) throws Exception
				{
					//Necesito id del requerimiento de area
					Integer requerimientoAreaId = objeto.getId();					
					//Necesito id del detalle jefe almacen buscado por requerieminto area id
					List<DetalleJefeAlmacenDTO>listaDetalleJefeAlmacen = new ArrayList<>();
					listaDetalleJefeAlmacen = this.detalleJefeAlmacenServiceLocal.buscarDetalleJefeAlmace(null, null, null, null, null, null, requerimientoAreaId);					
					//Si la lista es nula quiere decir que no se ha registrado,caso contrario registrar
										
					if(listaDetalleJefeAlmacen!=null && listaDetalleJefeAlmacen.size()==1)
					{
						DetalleJefeAlmacenDTO detalleJefeAlmacenDTO = new DetalleJefeAlmacenDTO();
						detalleJefeAlmacenDTO = listaDetalleJefeAlmacen.get(0);
						List<DetalleEncargadoAlmacenDTO>listaDetalleEncargadoAlmacenDTO = this.detalleEncargadoAlmacenServiceLocal.buscarDetalleEncargadoAlmacen(null,null,detalleJefeAlmacenDTO.getId(),null,null, null, null);
						
						if(listaDetalleEncargadoAlmacenDTO!=null && listaDetalleEncargadoAlmacenDTO.size() >0)
						{
							System.out.println("Ya existe un objeto idéntico registrado en la tabla encargado almacen ");
							return true;
						}
						else
						{
							System.out.println("No existe un objeto con esos datos registraos en la tabla encargado almacen");
							return false;
							
						}																
					}
					else
					{
						System.out.println("No tiene valores o es diferente de 1");
						return true;
					}
					
				}
				
				
				
				
				
				
				
				/********************************************************************************/
				/**************************   REPORTE            ********************************/
				/**
				 * @throws Exception ******************************************************************************/
				
				
				public Connection conexionDataSource() throws NamingException, SQLException
				{					
					try 
					{						
						 Class.forName ("oracle.jdbc.driver.OracleDriver");
					     Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SERVIR", "123456");
					     return conn;
					}
					catch (Exception e) 
					{
					System.out.println("Error : "+e.getMessage());
					return null;
					}		
						
				}
				
				
//				public void exportarPDF(ActionEvent actionEvent) throws Exception,IOException
				//1ERA FORMA DE EXPORTAR A PDF
				@SuppressWarnings("deprecation")
				public void exportarPDF(ActionEvent actionEvent) throws JRException,IOException
				{				
					try 
					{
						Integer programacionAreasId = this.programacionAreasDTO.getId();
						Integer productoDisponibleId = null;
						Map<String,Object> parametros=new HashMap<String,Object>();
						parametros.put("prtProgramacionAreasId",programacionAreasId);
						parametros.put("prtProductoDisponibleId",productoDisponibleId);					
						//Cargamos el archivo con extesión .jasper
						FacesContext context = FacesContext.getCurrentInstance();
						ServletContext servletContext =(ServletContext)context.getExternalContext().getContext();
						String ruta = servletContext.getRealPath("./ReporteAreaProductosDisponibles.jasper");
						HttpServletResponse response = (HttpServletResponse)context.getExternalContext().getResponse();
						String anio = String.valueOf(obtenerAnioSistema());
						String mes = String.valueOf(getMesSistema());
						String dia = String.valueOf(obtenerDiaSistema());
						String nombreArchivoPdf = this.getProgramacionSeleccionAlmacen().getDescripcionProgramacion()+this.programacionAreasDTO.getArea().getCodigoArea()+""+dia+""+mes+""+anio;
						response.addHeader("Content-disposition","attachment; filename="+nombreArchivoPdf+".pdf");
//						response.addHeader("Content-disposition","attachment; filename=reporte.pdf");
						response.setContentType("application/pdf");
						JasperPrint jasperPrint = JasperFillManager.fillReport(ruta,parametros,conexionDataSource());						
						JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
						context.getApplication().getStateManager().saveView(context);
						context.responseComplete();									
						System.out.println("Se debería imprimir el Pdf del reporte.");	
					}
					catch (Exception e) 
					{
					System.out.println("Error :"+e.getMessage());
					}					
				}
				
				
				
				//2DA FORMA DE EXPORTAR A PDF 
				public void exportarAPdf(ActionEvent actionEvent) throws JRException,IOException
				{				
					try 
					{
//						Integer programacionAreasId = 15;
						Integer programacionAreasId = this.programacionAreasDTO.getId();
						Integer productoDisponibleId = null;
						Map<String,Object> parametros=new HashMap<String,Object>();
						parametros.put("prtProgramacionAreasId",programacionAreasId);
						parametros.put("prtProductoDisponibleId",productoDisponibleId);
						File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/ReporteAreaProductosDisponibles.jasper"));						
						JasperReport reporte = (JasperReport) JRLoader.loadObject(jasper);
//						//Presentamos en pantalla 
						JasperPrint jasperPrint = JasperFillManager.fillReport(reporte,parametros,conexionDataSource());						
						HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
//						HttpServletResponse response = (HttpServletResponse);
						String anio = String.valueOf(obtenerAnioSistema());
						String mes = String.valueOf(getMesSistema());
						String dia = String.valueOf(obtenerDiaSistema());
						String nombreArchivoPdf = this.areaDTO.getAbreviaturaArea()+""+dia+""+mes+""+anio;
						response.addHeader("Content-disposition","attachment; filename="+nombreArchivoPdf+".pdf");
//						response.addHeader("Content-disposition","attachment; filename=jsfReporte.pdf");
						ServletOutputStream stream = response.getOutputStream();					
//						
						JasperExportManager.exportReportToPdfStream(jasperPrint, stream);					
//																	
						stream.flush();
						stream.close();
						FacesContext.getCurrentInstance().responseComplete();
						System.out.println("Se debería imprimir el Pdf del reporte.");	
					}
					catch (Exception e) 
					{
					System.out.println("Error :"+e.getMessage());
					}					
				}
				
				
				
																				
				public void exportarExcel(ActionEvent actionEvent) throws JRException,IOException
				{				
					try 
					{
						Integer programacionAreasId = 15;
						Integer productoDisponibleId = 1;
						Map<String,Object> parametros=new HashMap<String,Object>();
						parametros.put("prtProgramacionAreasId",programacionAreasId);
						parametros.put("prtProductoDisponibleId",productoDisponibleId);
						File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/ReporteAreaProductosDisponibles.jasper"));						
						JasperReport reporte = (JasperReport) JRLoader.loadObject(jasper);
						JasperPrint xlsPrint = JasperFillManager.fillReport(reporte,parametros,conexionDataSource());						
//						HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
//						response.addHeader("Content-disposition","attachment; filename=jsfReporte.pdf");
//						ServletOutputStream stream = response.getOutputStream();					
						
						String outXlsName = "test.xls";												
						
						JRXlsExporter xlsExporter = new JRXlsExporter();
						xlsExporter.setExporterInput(new SimpleExporterInput(xlsPrint));
						xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outXlsName));
						
						SimpleXlsReportConfiguration xlsReportConfiguration = new SimpleXlsReportConfiguration();
				        xlsReportConfiguration.setOnePagePerSheet(false);
				        xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
				        xlsReportConfiguration.setDetectCellType(false);
				        xlsReportConfiguration.setWhitePageBackground(false);
				        xlsExporter.setConfiguration(xlsReportConfiguration);
				        
//						stream.flush();
//						stream.close();
						
						xlsExporter.exportReport();
						FacesContext.getCurrentInstance().responseComplete();
						System.out.println("Se debería imprimir el Excel del reporte.");	
					}
					catch (Exception e) 
					{
					System.out.println("Error :"+e.getMessage());
					}					
				}
				
								
				public void exportarAExcel(ActionEvent actionEvent) throws JRException,IOException
				{				
					try 
					{
//						Integer programacionAreasId = 15;
						Integer programacionAreasId = this.programacionAreasDTO.getId();
						Integer productoDisponibleId = null;
						Map<String,Object> parametros=new HashMap<String,Object>();
						parametros.put("prtProgramacionAreasId",programacionAreasId);
						parametros.put("prtProductoDisponibleId",productoDisponibleId);
						File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/ReporteAreaProductosDisponibles.jasper"));						
						JasperReport reporte = (JasperReport) JRLoader.loadObject(jasper);
//						//Presentamos en pantalla 
						JasperPrint jasperPrint = JasperFillManager.fillReport(reporte,parametros,conexionDataSource());						
						HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
//						HttpServletResponse response = (HttpServletResponse);
												
						String anio = String.valueOf(obtenerAnioSistema());
						String mes = String.valueOf(getMesSistema());
						String dia = String.valueOf(obtenerDiaSistema());
						String nombreArchivoPdf = this.areaDTO.getAbreviaturaArea()+""+dia+""+mes+""+anio;
						response.addHeader("Content-disposition","attachment; filename="+nombreArchivoPdf+".xls");
						ServletOutputStream stream = response.getOutputStream();											
								
						JRXlsExporter exporter = new JRXlsExporter();
						exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
						exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(stream));
						SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();						
						configuration.setOnePagePerSheet(true);
						configuration.setRemoveEmptySpaceBetweenRows(false);
						configuration.setDetectCellType(true);
						configuration.setWhitePageBackground(false);
						configuration.setCollapseRowSpan(false);
						exporter.setConfiguration(configuration);						
						stream.flush();
						stream.close();						
						FacesContext.getCurrentInstance().responseComplete();
						exporter.exportReport();
						System.out.println("Se debería imprimir el Excel del reporte.");	
					}
					catch (Exception e) 
					{
					System.out.println("Error :"+e.getMessage());
					}					
				}
				
				
		//Prueba para abrir el diálogo de confirmación para entregar los ítems al encargado del área		
		public void abrirDialogoConfirmacionEntrega(Integer posicion)
		{
			this.posicionSeleccionRequerimeintoAreaEncargadoAlmacen = posicion;
			context = RequestContext.getCurrentInstance();
			context.execute("PF('dialogoAbrirConfirmacionEntrega').show();"); 	
		}		
			
		//Prueba para cerrar el dialogo de confimración para entregar los ítems al encargado del área
		public void cerrarDialogoConfirmacionEntrega()
		{
			this.posicionSeleccionRequerimeintoAreaEncargadoAlmacen = null;
			context = RequestContext.getCurrentInstance();
			context.execute("PF('dialogoAbrirConfirmacionEntrega').hide();");
		}
		
		public void cerrarDialogoAbrirPopupDetallesRequerimientosAprobadosEncargadoAlmacen()
		{
			context = RequestContext.getCurrentInstance();
			context.execute("PF('dialogoAbrirPopupDetallesRequerimientosAprobadosEncargadoAlmacen').hide();");
		}
		
		//Prueba de método para buscar el requerimeinto de área que se ha seleccionado para buscar ese objeto
		//en la lista detalleencargadoalmacen, con eso podemos actualizar el indicador de entregado al encargado
		//de área.
		public void buscarRequerimientoAreaEnDetalleEncargadoAlmacen() throws Exception
		{
			RequerimientoAreaDTO requerimientoAreaSeleccionado=new RequerimientoAreaDTO();
			requerimientoAreaSeleccionado = this.listaRequerimientoParaJefeAlmacen.get(this.posicionSeleccionRequerimeintoAreaEncargadoAlmacen);
			Integer idRequerimientoAreaSeleccionado = requerimientoAreaSeleccionado.getId();
			
			DetalleJefeAlmacenDTO detalleJefeAlmacenDTO = new DetalleJefeAlmacenDTO();
			List<DetalleJefeAlmacenDTO>listaDetalleJefeAlmacen = new ArrayList<>();
			listaDetalleJefeAlmacen= this.detalleJefeAlmacenServiceLocal.buscarDetalleJefeAlmace(null, null, null, null, null, null, idRequerimientoAreaSeleccionado);
			detalleJefeAlmacenDTO = listaDetalleJefeAlmacen.get(0);
			
			DetalleEncargadoAlmacenDTO detalleEncargadoAlmacen = new DetalleEncargadoAlmacenDTO();
			List<DetalleEncargadoAlmacenDTO>listaDetalleEncargadoAlmacen = new ArrayList<>();
			Integer idDetalleJefeAlmacen = detalleJefeAlmacenDTO.getId();
			listaDetalleEncargadoAlmacen = this.detalleEncargadoAlmacenServiceLocal.buscarDetalleEncargadoAlmacen(null, null, idDetalleJefeAlmacen, null, null,null, null);
			detalleEncargadoAlmacen = listaDetalleEncargadoAlmacen.get(0);
			
			
			if(detalleEncargadoAlmacen.getIndicadorEnviadoAlJefeArea()!=null && detalleEncargadoAlmacen.getIndicadorEnviadoAlJefeArea()==1)
			{
				System.out.println("Ya se ha enviado el requerimeinto de área al Jefe del área.");				
				cerrarDialogoConfirmacionEntrega();				
				cerrarDialogoAbrirPopupDetallesRequerimientosAprobadosEncargadoAlmacen();
				FacesContext.getCurrentInstance().addMessage("ErrorEntregaAlJefeDeArea", 
						new FacesMessage(
								FacesMessage.SEVERITY_ERROR, "Error!", 
								"El requerimiento actual : "+idRequerimientoAreaSeleccionado+" ya se ha entregado al jefe de área."));
				
			}
			else
			{
				//1:ENTREGADO 
				Short indicadorEnviadoAlJefeArea = 1;
				java.util.Date fechaActualJava = new Date();
				java.sql.Date fechaActualSql = new java.sql.Date(fechaActualJava.getTime());
				String codigoUsuarioModificacion = this.usuarioDTO.getCodigoUsuario();
				detalleEncargadoAlmacen.setIndicadorEnviadoAlJefeArea(indicadorEnviadoAlJefeArea);
				detalleEncargadoAlmacen.setFechaEnviadoAlJefeArea(fechaActualSql);
				detalleEncargadoAlmacen.setCodigoUsuarioModificacion(codigoUsuarioModificacion);
				detalleEncargadoAlmacen.setIpModificacion(obtieneIPCliente());
				boolean seActualizo = this.detalleEncargadoAlmacenServiceLocal.actualizarDetalleEncargadoAlmacen(detalleEncargadoAlmacen);
				if(seActualizo == true)
				{
					System.out.println("Se actualizó el requerimiento de área y se enviaron los ítems al jefe de almacén.");
					cerrarDialogoConfirmacionEntrega();
					cerrarDialogoAbrirPopupDetallesRequerimientosAprobadosEncargadoAlmacen();
					FacesContext context = FacesContext.getCurrentInstance();					
					context.addMessage(null, new FacesMessage
							("Aviso","Se actualizó el requerimiento actual : "+idRequerimientoAreaSeleccionado));
				}
				else
				{
					System.err.println("ERROR no se pudo actualizar el requerimiento de área.");
					cerrarDialogoConfirmacionEntrega();
					cerrarDialogoAbrirPopupDetallesRequerimientosAprobadosEncargadoAlmacen();
					FacesContext.getCurrentInstance().addMessage("ErrorActuaizarEntregaAlJefeDeArea", 
							new FacesMessage(
									FacesMessage.SEVERITY_ERROR, "Error!", 
									"No se pudo actualizar el requerimiento : "+idRequerimientoAreaSeleccionado));
				}
			}
			
			
			

		}
				
				
				
				
				
				
				
								
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
		
	 
	 //MÉTODOS DE ACCESO

	public List<RequerimientoAreaDTO> getListaRequerimientoArea() {
		return listaRequerimientoArea;
	}

	public void setListaRequerimientoArea(
			List<RequerimientoAreaDTO> listaRequerimientoArea) {
		this.listaRequerimientoArea = listaRequerimientoArea;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public RequerimientoAreaServiceLocal getRequerimientoAreaServiceLocal() {
		return requerimientoAreaServiceLocal;
	}

	public void setRequerimientoAreaServiceLocal(
			RequerimientoAreaServiceLocal requerimientoAreaServiceLocal) {
		this.requerimientoAreaServiceLocal = requerimientoAreaServiceLocal;
	}

	public RequestContext getContext() {
		return context;
	}

	public void setContext(RequestContext context) {
		this.context = context;
	}

	public Integer getRequerimientoAreaId() {
		return requerimientoAreaId;
	}

	public void setRequerimientoAreaId(Integer requerimientoAreaId) {
		this.requerimientoAreaId = requerimientoAreaId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Short getIndicadorEnvioAlJefe() {
		return indicadorEnvioAlJefe;
	}

	public void setIndicadorEnvioAlJefe(Short indicadorEnvioAlJefe) {
		this.indicadorEnvioAlJefe = indicadorEnvioAlJefe;
	}

	public Short getIndicadorRecepcionDelJefe() {
		return indicadorRecepcionDelJefe;
	}

	public void setIndicadorRecepcionDelJefe(Short indicadorRecepcionDelJefe) {
		this.indicadorRecepcionDelJefe = indicadorRecepcionDelJefe;
	}

	public Integer getProgramacionId() {
		return programacionId;
	}

	public void setProgramacionId(Integer programacionId) {
		this.programacionId = programacionId;
	}

	public Integer getPosicion() {
		return posicion;
	}

	public void setPosicion(Integer posicion) {
		this.posicion = posicion;
	}

	public RequerimientoAreaDTO getRequerimientoAreaDTO() {
		return requerimientoAreaDTO;
	}

	public void setRequerimientoAreaDTO(RequerimientoAreaDTO requerimientoAreaDTO) {
		this.requerimientoAreaDTO = requerimientoAreaDTO;
	}

	public List<SelectItem> getListaIndicadorEnviadoAlJefe() {
		return listaIndicadorEnviadoAlJefe;
	}

	public void setListaIndicadorEnviadoAlJefe(
			List<SelectItem> listaIndicadorEnviadoAlJefe) {
		this.listaIndicadorEnviadoAlJefe = listaIndicadorEnviadoAlJefe;
	}

	public List<SelectItem> getListaIndicadorRecepcionDelJefe() {
		return listaIndicadorRecepcionDelJefe;
	}

	public void setListaIndicadorRecepcionDelJefe(
			List<SelectItem> listaIndicadorRecepcionDelJefe) {
		this.listaIndicadorRecepcionDelJefe = listaIndicadorRecepcionDelJefe;
	}

	public List<SelectItem> getListaEstado() {
		return listaEstado;
	}

	public void setListaEstado(List<SelectItem> listaEstado) {
		this.listaEstado = listaEstado;
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

	public AreaServiceLocal getAreaServiceLocal() {
		return areaServiceLocal;
	}

	public void setAreaServiceLocal(AreaServiceLocal areaServiceLocal) {
		this.areaServiceLocal = areaServiceLocal;
	}

	public AreaDTO getAreaDTO() {
		return areaDTO;
	}

	public void setAreaDTO(AreaDTO areaDTO) {
		this.areaDTO = areaDTO;
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

	public ProgramacionAreasDTO getProgramacionAreasDTO() {
		return programacionAreasDTO;
	}

	public void setProgramacionAreasDTO(ProgramacionAreasDTO programacionAreasDTO) {
		this.programacionAreasDTO = programacionAreasDTO;
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

	public ProgramacionAreasServiceLocal getProgramacionAreaServiceLocal() {
		return programacionAreaServiceLocal;
	}

	public void setProgramacionAreaServiceLocal(
			ProgramacionAreasServiceLocal programacionAreaServiceLocal) {
		this.programacionAreaServiceLocal = programacionAreaServiceLocal;
	}

	public List<String> getRowUsuarios() {
		return rowUsuarios;
	}

	public void setRowUsuarios(List<String> rowUsuarios) {
		this.rowUsuarios = rowUsuarios;
	}

	public RequerimientoUsuarioServiceLocal getRequerimientoUsuarioServiceLocal() {
		return requerimientoUsuarioServiceLocal;
	}

	public void setRequerimientoUsuarioServiceLocal(
			RequerimientoUsuarioServiceLocal requerimientoUsuarioServiceLocal) {
		this.requerimientoUsuarioServiceLocal = requerimientoUsuarioServiceLocal;
	}

	public Integer[][] getArregloValoresRequerimientoUsuarios() {
		return arregloValoresRequerimientoUsuarios;
	}

	public void setArregloValoresRequerimientoUsuarios(
			Integer[][] arregloValoresRequerimientoUsuarios) {
		this.arregloValoresRequerimientoUsuarios = arregloValoresRequerimientoUsuarios;
	}

	public DetalleRequerimientoUsuarioProductoServiceLocal getDetalleRequerimientoUsuarioProductoServiceLocal() {
		return detalleRequerimientoUsuarioProductoServiceLocal;
	}

	public void setDetalleRequerimientoUsuarioProductoServiceLocal(
			DetalleRequerimientoUsuarioProductoServiceLocal detalleRequerimientoUsuarioProductoServiceLocal) {
		this.detalleRequerimientoUsuarioProductoServiceLocal = detalleRequerimientoUsuarioProductoServiceLocal;
	}

	public RequerimientoUsuarioDTO getRequerimientoUsuarioDTO() {
		return requerimientoUsuarioDTO;
	}

	public void setRequerimientoUsuarioDTO(
			RequerimientoUsuarioDTO requerimientoUsuarioDTO) {
		this.requerimientoUsuarioDTO = requerimientoUsuarioDTO;
	}

	public RequerimientoUsuarioBean getRequerimientoUsuarioBean() {
		return requerimientoUsuarioBean;
	}

	public void setRequerimientoUsuarioBean(
			RequerimientoUsuarioBean requerimientoUsuarioBean) {
		this.requerimientoUsuarioBean = requerimientoUsuarioBean;
	}

	public int[][] getArregloTotalSobranteCantidadRequerimientosUsuarios() {
		return arregloTotalSobranteCantidadRequerimientosUsuarios;
	}

	public void setArregloTotalSobranteCantidadRequerimientosUsuarios(
			int[][] arregloTotalSobranteCantidadRequerimientosUsuarios) {
		this.arregloTotalSobranteCantidadRequerimientosUsuarios = arregloTotalSobranteCantidadRequerimientosUsuarios;
	}

	public List<String> getRowTotalDiferencia() {
		return rowTotalDiferencia;
	}

	public void setRowTotalDiferencia(List<String> rowTotalDiferencia) {
		this.rowTotalDiferencia = rowTotalDiferencia;
	}

	public List<RequerimientoAreaDTO> getListaRequerimientoAreaJefe() {
		return listaRequerimientoAreaJefe;
	}

	public void setListaRequerimientoAreaJefe(
			List<RequerimientoAreaDTO> listaRequerimientoAreaJefe) {
		this.listaRequerimientoAreaJefe = listaRequerimientoAreaJefe;
	}

	public RequerimientoJefeAprobacionDTO getRequerimientoJefeAprobacionDTO() {
		return requerimientoJefeAprobacionDTO;
	}

	public void setRequerimientoJefeAprobacionDTO(
			RequerimientoJefeAprobacionDTO requerimientoJefeAprobacionDTO) {
		this.requerimientoJefeAprobacionDTO = requerimientoJefeAprobacionDTO;
	}

	public RequerimientoJefeAprobacionServiceLocal getRequerimientoJefeAprobacionServiceLocal() {
		return requerimientoJefeAprobacionServiceLocal;
	}

	public void setRequerimientoJefeAprobacionServiceLocal(
			RequerimientoJefeAprobacionServiceLocal requerimientoJefeAprobacionServiceLocal) {
		this.requerimientoJefeAprobacionServiceLocal = requerimientoJefeAprobacionServiceLocal;
	}

	public boolean isSeAproboRequerimientoArea() {
		return seAproboRequerimientoArea;
	}

	public void setSeAproboRequerimientoArea(boolean seAproboRequerimientoArea) {
		this.seAproboRequerimientoArea = seAproboRequerimientoArea;
	}

	public List<SelectItem> getListaIndicadorRecepcionEncargadoDelArea() {
		return listaIndicadorRecepcionEncargadoDelArea;
	}

	public void setListaIndicadorRecepcionEncargadoDelArea(
			List<SelectItem> listaIndicadorRecepcionEncargadoDelArea) {
		this.listaIndicadorRecepcionEncargadoDelArea = listaIndicadorRecepcionEncargadoDelArea;
	}

	public List<SelectItem> getListaIndicadorEnviadoAlJefeDelAlmacen() {
		return listaIndicadorEnviadoAlJefeDelAlmacen;
	}

	public void setListaIndicadorEnviadoAlJefeDelAlmacen(
			List<SelectItem> listaIndicadorEnviadoAlJefeDelAlmacen) {
		this.listaIndicadorEnviadoAlJefeDelAlmacen = listaIndicadorEnviadoAlJefeDelAlmacen;
	}

	public List<SelectItem> getListaIndicadorRecepcionDelJefeDelAlmacen() {
		return listaIndicadorRecepcionDelJefeDelAlmacen;
	}

	public void setListaIndicadorRecepcionDelJefeDelAlmacen(
			List<SelectItem> listaIndicadorRecepcionDelJefeDelAlmacen) {
		this.listaIndicadorRecepcionDelJefeDelAlmacen = listaIndicadorRecepcionDelJefeDelAlmacen;
	}

	public List<SelectItem> getListaIndicadorEnvioAlEncargadoDelAlmacen() {
		return listaIndicadorEnvioAlEncargadoDelAlmacen;
	}

	public void setListaIndicadorEnvioAlEncargadoDelAlmacen(
			List<SelectItem> listaIndicadorEnvioAlEncargadoDelAlmacen) {
		this.listaIndicadorEnvioAlEncargadoDelAlmacen = listaIndicadorEnvioAlEncargadoDelAlmacen;
	}

	public ProgramacionBean getProgramacionBean() {
		return programacionBean;
	}

	public void setProgramacionBean(ProgramacionBean programacionBean) {
		this.programacionBean = programacionBean;
	}

	public ProgramacionDTO getProgramacionSeleccionAlmacen() {
		return programacionSeleccionAlmacen;
	}

	public void setProgramacionSeleccionAlmacen(
			ProgramacionDTO programacionSeleccionAlmacen) {
		this.programacionSeleccionAlmacen = programacionSeleccionAlmacen;
	}

	public List<RequerimientoAreaDTO> getListaRequerimientoAreaAprobados() {
		return listaRequerimientoAreaAprobados;
	}

	public void setListaRequerimientoAreaAprobados(
			List<RequerimientoAreaDTO> listaRequerimientoAreaAprobados) {
		this.listaRequerimientoAreaAprobados = listaRequerimientoAreaAprobados;
	}

	public List<RequerimientoAreaDTO> getListaRequerimientoParaJefeAlmacen() {
		return listaRequerimientoParaJefeAlmacen;
	}

	public void setListaRequerimientoParaJefeAlmacen(
			List<RequerimientoAreaDTO> listaRequerimientoParaJefeAlmacen) {
		this.listaRequerimientoParaJefeAlmacen = listaRequerimientoParaJefeAlmacen;
	}

	public UsuarioPerfilesServiceLocal getUsuarioPerfilesServiceLocal() {
		return usuarioPerfilesServiceLocal;
	}

	public void setUsuarioPerfilesServiceLocal(
			UsuarioPerfilesServiceLocal usuarioPerfilesServiceLocal) {
		this.usuarioPerfilesServiceLocal = usuarioPerfilesServiceLocal;
	}

	public UsuarioPerfilesDTO getUsuarioPerfilesDTO() {
		return usuarioPerfilesDTO;
	}

	public void setUsuarioPerfilesDTO(UsuarioPerfilesDTO usuarioPerfilesDTO) {
		this.usuarioPerfilesDTO = usuarioPerfilesDTO;
	}

	public Integer getIndexProgramacion() {
		return indexProgramacion;
	}

	public void setIndexProgramacion(Integer indexProgramacion) {
		this.indexProgramacion = indexProgramacion;
	}

	public DetalleJefeAlmacenDTO getDetalleJefeAlmacenDTO() {
		return detalleJefeAlmacenDTO;
	}

	public void setDetalleJefeAlmacenDTO(DetalleJefeAlmacenDTO detalleJefeAlmacenDTO) {
		this.detalleJefeAlmacenDTO = detalleJefeAlmacenDTO;
	}

	public DetalleJefeAlmacenServiceLocal getDetalleJefeAlmacenServiceLocal() {
		return detalleJefeAlmacenServiceLocal;
	}

	public void setDetalleJefeAlmacenServiceLocal(
			DetalleJefeAlmacenServiceLocal detalleJefeAlmacenServiceLocal) {
		this.detalleJefeAlmacenServiceLocal = detalleJefeAlmacenServiceLocal;
	}

	public boolean isParaDerivarAlEncargadoDeAlmacen() {
		return paraDerivarAlEncargadoDeAlmacen;
	}

	public void setParaDerivarAlEncargadoDeAlmacen(
			boolean paraDerivarAlEncargadoDeAlmacen) {
		this.paraDerivarAlEncargadoDeAlmacen = paraDerivarAlEncargadoDeAlmacen;
	}

	public String getAvisoDerivado() {
		return avisoDerivado;
	}

	public void setAvisoDerivado(String avisoDerivado) {
		this.avisoDerivado = avisoDerivado;
	}

	public DetalleEncargadoAlmacenDTO getDetalleEncargadoAlmacenDTO() {
		return detalleEncargadoAlmacenDTO;
	}

	public void setDetalleEncargadoAlmacenDTO(
			DetalleEncargadoAlmacenDTO detalleEncargadoAlmacenDTO) {
		this.detalleEncargadoAlmacenDTO = detalleEncargadoAlmacenDTO;
	}

	public DetalleEncargadoAlmacenServiceLocal getDetalleEncargadoAlmacenServiceLocal() {
		return detalleEncargadoAlmacenServiceLocal;
	}

	public void setDetalleEncargadoAlmacenServiceLocal(
			DetalleEncargadoAlmacenServiceLocal detalleEncargadoAlmacenServiceLocal) {
		this.detalleEncargadoAlmacenServiceLocal = detalleEncargadoAlmacenServiceLocal;
	}

	public Integer getPosicionSeleccionRequerimeintoAreaEncargadoAlmacen() {
		return posicionSeleccionRequerimeintoAreaEncargadoAlmacen;
	}

	public void setPosicionSeleccionRequerimeintoAreaEncargadoAlmacen(
			Integer posicionSeleccionRequerimeintoAreaEncargadoAlmacen) {
		this.posicionSeleccionRequerimeintoAreaEncargadoAlmacen = posicionSeleccionRequerimeintoAreaEncargadoAlmacen;
	}

	public LoginUsuarioBean getLoginUsuarioBean() {
		return loginUsuarioBean;
	}

	public void setLoginUsuarioBean(LoginUsuarioBean loginUsuarioBean) {
		this.loginUsuarioBean = loginUsuarioBean;
	}
		
	
	
}
