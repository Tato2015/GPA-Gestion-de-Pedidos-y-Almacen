
package pe.gob.servir.gpa.servlet;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.captcha.Captcha;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.servlet.CaptchaServletUtil;
import nl.captcha.text.renderer.DefaultWordRenderer;

import org.apache.log4j.Logger;

/** Servlet implementation class SimpleCaptcha  */
@WebServlet("/Simple.jpg")
public class SimpleCaptcha extends HttpServlet {
	
	/** Atributo que debe ser distinto para cada version compilada que tengamos de esta clase */
	private static final long serialVersionUID = 1338042394712806917L;
	
	/** Variable estatica que identifica el log de la clase*/
	final static Logger log = Logger.getLogger(RecursosServlet.class);
	
	/** Instancia   */
    public SimpleCaptcha() {
        super();
 
    }
    /**
	 * Metodo que permite obtener el codigo captcha
	 * @param request, Objeto tipo Request, tipo HttpServletRequest
	 * @param response, Objeto response, Tipo HttpServletResponse
	 * @throws ServletException, excepcion de proposito general, tipo ServletException
	 */
	@SuppressWarnings("static-access")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Color> colors = new ArrayList<Color> ();
        colors.add(Color.black);
   try{
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache"); 
        response.setDateHeader("Expires", 0); 
        response.setCharacterEncoding("UTF-8");
        
        List<Font> fonts = new ArrayList<Font>();
        fonts.add(new Font("Arial", Font.ITALIC, 40));
         
        Captcha captcha = new Captcha.Builder(140, 60)
                .addText(new DefaultWordRenderer(colors, fonts))
                .addBackground(new GradiatedBackgroundProducer())
                .gimp()
                .addBorder()
                .build();
 
     
        CaptchaServletUtil.writeImage(response, captcha.getImage());
        
        request.setCharacterEncoding("UTF-8");
     
        request.getSession().setAttribute(captcha.NAME, captcha);
    
   } catch (Exception e) {
		log.error(e.getMessage(), e);
	}
   }



}
