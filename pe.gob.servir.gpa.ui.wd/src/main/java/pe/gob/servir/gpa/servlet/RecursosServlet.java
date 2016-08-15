/**
 * <ul>
 * <li>Copyright 2015 Organismo Supervisor de las Contrataciones del Estado -
 * OSCE. Todos los derechos reservados.</li> 
 * </ul>
 * Objeto: RecursosServlet.java
 * Descripcion: Clase que permite la carga de Recursos externos en las paginas JSF
 * ----------------------------------------------------------------------------
 * Modificaciones
 * Codigo	Fecha		Nombre			Descripcion
 * ----------------------------------------------------------------------------
 * 
 */
package pe.gob.servir.gpa.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import pe.gob.servir.gpa.util.ConstanteWeb;



@WebServlet("/recurso")
public class RecursosServlet extends HttpServlet {

	/** Atributo que debe ser distinto para cada versión compilada que tengamos de esta clase */
	private static final long serialVersionUID = -2699152855892991914L;
	
	/** Variable estatica que identifica el log de la clase*/
	final static Logger log = Logger.getLogger(RecursosServlet.class);
	
	/** Metodo Constructor */
	public RecursosServlet() {}
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String nombreRecurso=(String)request.getParameter("nombre");
    	String tipoRecurso=nombreRecurso.substring(nombreRecurso.indexOf(".")+1);
    	
    	String ruta="";
    	String tipoMIME="";
    	ResourceBundle propServir_Recursos = ResourceBundle.getBundle(ConstanteWeb.PROPERTIES_FS);
    	
    	if(tipoRecurso.equals("css")){
    		tipoMIME="text/css";
    		ruta=propServir_Recursos.getString(ConstanteWeb.FS_CSS);
    	}if(tipoRecurso.equals("js")){
    		tipoMIME="text/javascript";
    		ruta=propServir_Recursos.getString(ConstanteWeb.FS_JS);
    	}if(tipoRecurso.equals("jpg")||tipoRecurso.equals("png")||tipoRecurso.equals("gif")){
    		tipoMIME="image/"+tipoRecurso;
    		ruta=propServir_Recursos.getString(ConstanteWeb.FS_IMAGENES);
    	}
    	
    	try {
    		File archivoRecurso= new File(ruta+""+nombreRecurso);
    		 byte[] content = new byte[(int) archivoRecurso.length()];
             
        	 FileInputStream fileInputStream = new FileInputStream(archivoRecurso);
        	 fileInputStream.read(content);
    		
            response.setContentType(tipoMIME);
            response.setContentLength(content.length);
            response.getOutputStream().write(content);
            fileInputStream.close();
		} catch (Exception e) {
	        log.error(e.getMessage(), e);
		}
    }

}