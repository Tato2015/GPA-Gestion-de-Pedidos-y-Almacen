/**
 * <ul>
 * <li>Copyright 2015 Organismo Supervisor de las Contrataciones del Estado -
 * OSCE. Todos los derechos reservados.</li> 
 * </ul>
 * Objeto: PdfReportServlet.java
 * Descripcion: Clase que permite la visualizacion de un archivo PDF, generado en un proceso previo

 * ----------------------------------------------------------------------------
 * Modificaciones
 * Codigo	Fecha		Nombre			Descripcion
 * ----------------------------------------------------------------------------
 * 
 */
package pe.gob.servir.gpa.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pe.gob.servir.gpa.util.ConstanteWeb;


@WebServlet("/report.pdf")
public class PdfReportServlet extends HttpServlet {
	
	/** Atributo que debe ser distinto para cada versión compilada que tengamos de esta clase */
	private static final long serialVersionUID = -2367293112703535038L;

	/** Metodo Constructor */
	public PdfReportServlet() {}
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        byte[] content = (byte[]) request.getSession().getAttribute(ConstanteWeb.REPORTE_PDF);
        response.setContentType("application/pdf");
        response.setContentLength(content.length);
        response.getOutputStream().write(content);
    }

}