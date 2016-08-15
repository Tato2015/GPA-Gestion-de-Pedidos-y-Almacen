package pe.gob.servir.gpa.service.ejb.impl;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.log4j.Logger;
import pe.gob.servir.gpa.adm.dao.ProgramacionDAOLocal;
import pe.gob.servir.gpa.model.dto.ProgramacionDTO;
import pe.gob.servir.gpa.service.ejb.ProgramacionServiceLocal;
import pe.gob.servir.gpa.util.ConstanteEJB;


@Stateless
public class ProgramacionServiceImpl implements ProgramacionServiceLocal{

	
	
	/** Metodo Constructor 	 */
	public ProgramacionServiceImpl() {}

	
	
	 /** Atributo del programacion properties, donde se almacenas propiedades de la aplicacion*/
	ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);
	
	@EJB
	private ProgramacionDAOLocal programacionDAOLocal;
		
	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(ProgramacionServiceImpl.class);

		
	public List<ProgramacionDTO> buscarProgramacion(Integer anio,
			Integer aprobacion,Integer estado,
			Integer programacionPedido, Integer programacionPeriodo)
			throws Exception 
	{
		List<ProgramacionDTO> listaProgramacion=null;				
		try 
		{
			listaProgramacion=programacionDAOLocal.buscarProgramacion(anio, aprobacion, estado, programacionPedido,programacionPeriodo);
		} catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		}
		return listaProgramacion;
	}


	public ProgramacionDTO registrarProgramacion(ProgramacionDTO programacionDTO)
			throws Exception 
	{
		try {
			programacionDTO = programacionDAOLocal.registrarProgramacion(programacionDTO);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}			
		return programacionDTO;
	}


	public Boolean actualizarProgramacion(ProgramacionDTO programacionDTO)
			throws Exception 
	{
		try 
		{
			programacionDAOLocal.actualizarProgramacion(programacionDTO);
			return true;
			
		} catch (Exception e) 
		{
			log.error(e.getMessage(),e);
		}
		return false;			
	}


	public List<ProgramacionDTO> buscarProgramacionXMesActual(Integer mesActual) 
	{
		List<ProgramacionDTO> listaProgramacion=null;				
		try 
		{
			listaProgramacion=programacionDAOLocal.buscarProgramacionXMesActual(mesActual);
		} catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		}
		return listaProgramacion;
		
	}
	
	
	

}
