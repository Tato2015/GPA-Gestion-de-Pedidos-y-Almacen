package pe.gob.servir.gpa.bean;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import pe.gob.servir.gpa.model.dto.UsuarioDTO;
import pe.gob.servir.gpa.model.dto.UsuarioPerfilesDTO;
import pe.gob.servir.gpa.service.ejb.UsuarioPerfilesServiceLocal;
import pe.gob.servir.gpa.service.ejb.UsuarioServiceLocal;
import pe.gob.servir.gpa.util.UBaseManage;

@ManagedBean(name = "loginUsuarioBean")
@SessionScoped
public class LoginUsuarioBean extends UBaseManage implements Serializable 
{

	private static final long serialVersionUID = -3503488480584623065L;


	/** permite capturar los erroes de ejecucion **/
	private Logger log = Logger.getLogger(LoginUsuarioBean.class);

	@EJB
	private UsuarioServiceLocal usuarioServiceLocal;
	@EJB
	private UsuarioPerfilesServiceLocal usuarioPerfilesServiceLocal;

	private String usuario;
	private String contrasenia;
	private UsuarioDTO usuarioDTO;
	private UsuarioPerfilesDTO usuarioPerfilesDTO;
	private Integer progress;
	boolean mostrarAprobacionJefe;
	boolean mostrarJefeAlmacen;
	boolean mostrarEncargadoAlmacen;
	boolean mostrarRequerimientoUsuario;
	boolean mostrarRequerimientoArea;
	boolean mostrarMantenimiento;
	boolean mostrarAdministracion;
	boolean mostrarProgramacion;
	
	
	@PostConstruct
	public void inicializando()
	{
		inicializarVariables();
	}
	
	public void inicializarVariables()
	{
		this.usuarioDTO = new UsuarioDTO();
		this.usuario = null;
		this.contrasenia = null;
		//Método que setea las variables booleanas a false para que no se muestren
		renderDefecto();
	}

	
	public String validarUsuario() throws Exception
	{
		String redireccionar = "";
		try 
		{					
			if(this.usuario == null || this.usuario.equals(""))
			{
				FacesContext.getCurrentInstance().addMessage("ErrorUsuario", 
				new FacesMessage(
								FacesMessage.SEVERITY_ERROR, "Error!", 
								"Falta su usuario")						
						);
				inicializarProgress();
			}
			else
			{
				System.out.println("Si ingresó su usuario.");
			}
			
			
			if(this.contrasenia == null || this.contrasenia.equals(""))
			{
				FacesContext.getCurrentInstance().addMessage("ErrorContrasenia", 
				new FacesMessage(
								FacesMessage.SEVERITY_ERROR, "Error!", 
								"Falta su contraseña")						
						);
				inicializarProgress();
			}
			else
			{
				System.out.println("Si ingresó su contraseña.");
			}
			
			
			if(this.usuario!=null && !this.usuario.equals("") &&
			   this.contrasenia!=null && !this.contrasenia.equals(""))
			{
				
				Short indicadorActivo = 1;
				this.usuarioDTO= new UsuarioDTO();
				this.usuarioDTO = this.usuarioServiceLocal.buscarLoginUsuario(null, this.usuario, this.contrasenia, indicadorActivo);		
				buscarUsuarioPerfileActual(this.usuarioDTO);
				
				if(this.usuarioDTO == null)
				{
					System.err.println("Usuario o contraseña erróneos, ó no existe el usuario.");
					FacesContext.getCurrentInstance().addMessage("ErrorUsuarioContrasenia", 
							new FacesMessage(
											FacesMessage.SEVERITY_ERROR, "Error!", 
											"Usuario o contraseña erróneos, ó no existe el usuario.")						
									);
					redireccionar= null;
					inicializarProgress();
				}
									
				if(this.usuarioDTO!=null)
				{
					System.out.println("El usuario si existe.");					
					//redireccionar = "VistaMenu";
					inicializarProgress();
					renderElementosPorPerfilUsuario();
					redireccionar();
						
				}
				
				
			}
		}
		catch (Exception e) 
		{
		System.out.println("Error : "+e.getMessage());
		System.err.println("Usuario o contraseña erróneos, ó no existe el usuario.");
		FacesContext.getCurrentInstance().addMessage("ErrorUsuarioContrasenia", 
				new FacesMessage(
								FacesMessage.SEVERITY_ERROR, "Error!", 
								"Usuario o contraseña erróneos, ó no existe el usuario.")						
						);
		inicializarProgress();
		}
		return redireccionar;
	}
	
	//Prueba de método para limpiar las variables del login.
	public void limpiar()
	{
		this.usuario = null;
		this.contrasenia = null;
	}
	
	
	  

	public Integer getProgress() 
	{  
	    if(progress == null)  
	      progress = 0;  
	    else 
	    {  
	      progress = progress + 50;      
	      if(progress > 100)  
	      progress = 100;  
	   }  

	 return progress;  
	 }  
	
	 public void onComplete() 
	 {  
		    FacesContext.getCurrentInstance().addMessage(null, new  FacesMessage(FacesMessage.SEVERITY_INFO, "Progress Completed", "Progress Completed"));  
	 }  
	
	
	 public void inicializarProgress()
	 {
		 this.progress = null;
	 }
	 
	 
	 
	 //Prueba de método para redireccionar a la bandeja principal de la webapp.
	public void redireccionar()
	{ 	
		String url ="template/bandejaPrincipal.xhtml";
	 	FacesContext fc =FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		try {
		    ec.redirect(url);
		}
		catch (IOException ex) 
		{
		System.out.println("Error : "+ex.getMessage());    
		}
	
	}

	//Prueba de método para buscar el perfil del usuario actual
	public void buscarUsuarioPerfileActual(UsuarioDTO usuarioDTO) throws Exception
	{
		List<UsuarioPerfilesDTO>listaUsuarioPerfiles = new ArrayList<>();
		//Estado 1 : activo 
		Integer estadoId = 1;
		Integer usuarioId = this.usuarioDTO.getId();
		listaUsuarioPerfiles = this.usuarioPerfilesServiceLocal.buscarUsuarioPerfilesXAreaId(null, usuarioId, null, estadoId,null);		
		this.usuarioPerfilesDTO = listaUsuarioPerfiles.get(0);
		if(this.usuarioPerfilesDTO!=null)
		{
			System.out.println("Sí existe el perfil del usuario.");
		} 
		else
		{
			System.out.println("NO EXISTE el perfil del usuario.");
		}
		
	}
	
	//Método para mostrar los elementos que solo puede ver un usuario con determinado perfil.
	public void renderElementosPorPerfilUsuario()
	{
		Integer idPerfil = this.usuarioPerfilesDTO.getPerfil().getId();
		switch(idPerfil)
		{
		case 1: renderPerfilAdministrador();break;
		case 2: renderPerfilUsuario();break;
		case 3: renderPerfilJefeArea();break;
		case 4: renderPerfilEncargadoArea();break;
		case 5: renderPerfilJefeAlmacen();break;
		case 6: renderPerfilEncargadoAlmacen();break;
		default :renderDefecto();break;
		}
	}


	//Prueba de método para setear las variables booleanas de acuerdo al perfil de ENCARGADO DE ALMACEN
	public void renderPerfilEncargadoAlmacen()
	{
		this.mostrarAprobacionJefe = false;
		this.mostrarJefeAlmacen = false;
		this.mostrarEncargadoAlmacen = true;
		this.mostrarRequerimientoUsuario = false;
		this.mostrarRequerimientoArea = false;		
		this.mostrarAdministracion = false;
		this.mostrarProgramacion = false;
	}
	
	
	
	
	//Prueba de método para setear las variables booleanas de acuerdo al perfil de JEFE DE ALMACEN
	public void renderPerfilJefeAlmacen()
	{
		this.mostrarAprobacionJefe = false;
		this.mostrarJefeAlmacen = true;
		this.mostrarEncargadoAlmacen = false;
		this.mostrarRequerimientoUsuario = false;
		this.mostrarRequerimientoArea = false;		
		this.mostrarAdministracion = false;
		this.mostrarProgramacion = false;
	}
	
	
	//Prueba de método para setear las variables booleanas de acuerdo al perfil de JEFE DE AREA
	public void renderPerfilEncargadoArea()
	{
		this.mostrarAprobacionJefe = false;
		this.mostrarJefeAlmacen = false;
		this.mostrarEncargadoAlmacen = false;
		this.mostrarRequerimientoUsuario = false;
		this.mostrarRequerimientoArea = true;		
		this.mostrarAdministracion = false;
		this.mostrarProgramacion = false;
	}
	
	//Prueba de método para setear las variables booleanas de acuerdo al perfil de JEFE DE AREA
	public void renderPerfilJefeArea()
	{
		this.mostrarAprobacionJefe = true;
		this.mostrarJefeAlmacen = false;
		this.mostrarEncargadoAlmacen = false;
		this.mostrarRequerimientoUsuario = false;
		this.mostrarRequerimientoArea = false;		
		this.mostrarAdministracion = false;
		this.mostrarProgramacion = false;
	}
	
	
	//Prueba de método para setear las variables booleanas de acuerdo al perfil de USUARIO
	public void renderPerfilUsuario()
	{
		this.mostrarAprobacionJefe = false;
		this.mostrarJefeAlmacen = false;
		this.mostrarEncargadoAlmacen = false;
		this.mostrarRequerimientoUsuario = true;
		this.mostrarRequerimientoArea = false;		
		this.mostrarAdministracion = false;
		this.mostrarProgramacion = false;
	}
		
	
	
	//Prueba de método para setear las variables booleanas de acuerdo al perfil de ADMINISTRADOR
	public void renderPerfilAdministrador()
	{
		this.mostrarAprobacionJefe = false;
		this.mostrarJefeAlmacen = false;
		this.mostrarEncargadoAlmacen = false;
		this.mostrarRequerimientoUsuario = false;
		this.mostrarRequerimientoArea = false;		
		this.mostrarAdministracion = true;
		this.mostrarProgramacion = true;
	}
	
	//Prueba de método para setear las variables booleanas a false
	public void renderDefecto()
	{
		this.mostrarAprobacionJefe = false;
		this.mostrarJefeAlmacen = false;
		this.mostrarEncargadoAlmacen = false;
		this.mostrarRequerimientoUsuario = false;
		this.mostrarRequerimientoArea = false;
		this.mostrarMantenimiento = false;
		this.mostrarAdministracion = false;
		this.mostrarProgramacion = false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/****************************************************************/
						//	MÉTODOS DE ACCESO
	/****************************************************************/
	
	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public UsuarioServiceLocal getUsuarioServiceLocal() {
		return usuarioServiceLocal;
	}

	public void setUsuarioServiceLocal(UsuarioServiceLocal usuarioServiceLocal) {
		this.usuarioServiceLocal = usuarioServiceLocal;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public UsuarioDTO getUsuarioDTO() {
		return usuarioDTO;
	}

	public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

	public UsuarioPerfilesDTO getUsuarioPerfilesDTO() {
		return usuarioPerfilesDTO;
	}

	public void setUsuarioPerfilesDTO(UsuarioPerfilesDTO usuarioPerfilesDTO) {
		this.usuarioPerfilesDTO = usuarioPerfilesDTO;
	}

	public UsuarioPerfilesServiceLocal getUsuarioPerfilesServiceLocal() {
		return usuarioPerfilesServiceLocal;
	}

	public void setUsuarioPerfilesServiceLocal(
			UsuarioPerfilesServiceLocal usuarioPerfilesServiceLocal) {
		this.usuarioPerfilesServiceLocal = usuarioPerfilesServiceLocal;
	}

	public boolean isMostrarAprobacionJefe() {
		return mostrarAprobacionJefe;
	}

	public void setMostrarAprobacionJefe(boolean mostrarAprobacionJefe) {
		this.mostrarAprobacionJefe = mostrarAprobacionJefe;
	}

	public boolean isMostrarJefeAlmacen() {
		return mostrarJefeAlmacen;
	}

	public void setMostrarJefeAlmacen(boolean mostrarJefeAlmacen) {
		this.mostrarJefeAlmacen = mostrarJefeAlmacen;
	}

	public boolean isMostrarEncargadoAlmacen() {
		return mostrarEncargadoAlmacen;
	}

	public void setMostrarEncargadoAlmacen(boolean mostrarEncargadoAlmacen) {
		this.mostrarEncargadoAlmacen = mostrarEncargadoAlmacen;
	}

	public boolean isMostrarRequerimientoUsuario() {
		return mostrarRequerimientoUsuario;
	}

	public void setMostrarRequerimientoUsuario(boolean mostrarRequerimientoUsuario) {
		this.mostrarRequerimientoUsuario = mostrarRequerimientoUsuario;
	}

	public boolean isMostrarRequerimientoArea() {
		return mostrarRequerimientoArea;
	}

	public void setMostrarRequerimientoArea(boolean mostrarRequerimientoArea) {
		this.mostrarRequerimientoArea = mostrarRequerimientoArea;
	}

	public boolean isMostrarMantenimiento() {
		return mostrarMantenimiento;
	}

	public void setMostrarMantenimiento(boolean mostrarMantenimiento) {
		this.mostrarMantenimiento = mostrarMantenimiento;
	}

	public boolean isMostrarAdministracion() {
		return mostrarAdministracion;
	}

	public void setMostrarAdministracion(boolean mostrarAdministracion) {
		this.mostrarAdministracion = mostrarAdministracion;
	}

	public boolean isMostrarProgramacion() {
		return mostrarProgramacion;
	}

	public void setMostrarProgramacion(boolean mostrarProgramacion) {
		this.mostrarProgramacion = mostrarProgramacion;
	}

	
	
	
	
	
	
}
