/**
 * <ul>
 * <li></li> 
 * </ul>
 * Objeto: UBaseManage.java
 * Descripción: Clase  de Sesion 
 * Fecha de Creación: 21/04/2015
 * Autor: JCayulla - P&P BMS
 * ----------------------------------------------------------------------------
 * Modificaciones
 * Código	Fecha		Nombre			Descripción
 * ----------------------------------------------------------------------------
 * 
 */

package pe.gob.servir.gpa.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class UBaseManage {

	/** Variable estatica que identifica el log de la clase */
	final static Logger log = Logger.getLogger(UBaseManage.class);


	/**
	 * Atributo del tipo properties de recursos, donde se almacenas propiedades
	 * de la aplicacion
	 */
	ResourceBundle prop_Recursos = ResourceBundle.getBundle(ConstanteWeb.PROPERTIES_FS);

	/** Constructor de la clase UBaseManage.java */
	public UBaseManage() {

	}

	/**
	 * Metodo que permite obtener el IP del Ordenador Usuaria
	 * 
	 * @return ipAddress, IP del Ordenador del Usuario, Tipo String
	 */
	public String obtieneIPCliente() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		try {
			if (ipAddress == null) {
				ipAddress = request.getRemoteAddr();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ipAddress;
	}

	//
	// /**
	// * Metodo que permite obtener la ruta del Servidor
	// * @return String, devuelve la ruta del servidor, Tipo String
	// */
	// public String obtenerRutaServidor(){
	// ServletContext servletContext = (ServletContext)
	// FacesContext.getCurrentInstance().getExternalContext().getContext();
	// /** Sustituye "/" por el directorio ej: "/upload" */
	// return (String) servletContext.getRealPath("/");
	// }
	//
	/**
	 * Obtiene un objeto del contexto.
	 * 
	 * @param nombreObjeto
	 *            ,Identificador del objeto, tipo String.
	 * */
	public Object obtenerObjetoContexto(String nombreObjeto) {
		return ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getAttribute(nombreObjeto);
	}


	/**
	 * Establece un mensaje en el Managed de Mensajes.
	 * 
	 * @param mensaje
	 *            , descripcion del mensaje , tipo String.
	 * */
	protected void setMensajeroInfo(String mensaje) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,mensaje, ""));
	}

	/**
	 * Establece un mensaje en el Managed de Mensajes.
	 * 
	 * @param mensaje
	 *            descripcion del mensaje , tipo String.
	 * */
	protected void setMensajeroInfo(String mensaje, String detalle) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				mensaje, detalle));
	}

	
	/**
	 * Método que obtiene un Objeto que se encuentra en Session.
	 * 
	 * @param nombreObjeto
	 *            , parámetro por el cual se busca la Session , tipo String.
	 * @return objeto, objeto buscado, tipo Object.
	 */
	public Object obtenerObjetoSession(String nombreObjeto) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get(nombreObjeto);
	}

	/**
	 * Método que establece un objeto de Session.
	 * 
	 * @param nombreObjeto
	 *            Identificador del objeto, tipo String.
	 * @param objeto
	 *            , Valor del objeto, tipo Object
	 */
	public void establecerObjetoSession(String nombreObjeto, Object objeto) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()	.put(nombreObjeto, objeto);
	}

	/**
	 * Método que elimina un Objeto de Session.
	 * 
	 * @param nombreObjeto
	 *            , valor con el cual se va a buscar, tipo String.
	 */
	public void removerObjetoSession(String nombreObjeto) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()	.remove(nombreObjeto);
	}

	/**
	 * Metodo que permite obtener un objeto compuesto InputStream
	 * 
	 * @param fileName , nombre del objeto, tipo String
	 * @param in , objeto compuesto, tipo InputStream
	 * @throws IOException , excepcion de proposito general, tipo IOException
	 */
	public void copyFile(String fileName, InputStream in) {
		String archivo = prop_Recursos.getString(ConstanteWeb.FS_PERSONALES) + fileName;
		try {
			OutputStream out = new FileOutputStream(new File(archivo));
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			in.close();
			out.flush();
			out.close();

		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * Método que obtiene la sesión.
	 * 
	 * @param arg0
	 *            , valor para validar, Tipo Boolean
	 * @return HttpSession, retorna la sesión, tipo HttpSession
	 */
	public HttpSession getSession(boolean arg0) {
		return (HttpSession) (FacesContext.getCurrentInstance()	.getExternalContext().getSession(arg0));
	}

	
}
