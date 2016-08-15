/**
 * <ul>
 * <li>Copyright 2015 Organismo Supervisor de las Contrataciones del Estado -
 * OSCE. Todos los derechos reservados.</li></ul>
 * Objeto		: UsuarioRolServiceImpl.java
 * Descripcion	: Clase tipo interfaces de UsuarioRol que 
 * 				  persiste con la base de datos.
 * Autor		: Consultora  - P & P BMS (P & P Business Management Solutions)
 * ----------------------------------------------------------------------------
 * Modificaciones
 * Codigo	Fecha		Nombre			Descripcion
 * ----------------------------------------------------------------------------
 * 
 */

package pe.gob.servir.gpa.service.ejb.impl;

import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import pe.gob.servir.gpa.adm.dao.ProgramacionAreasDAOLocal;
import pe.gob.servir.gpa.model.dto.ProgramacionAreasDTO;
import pe.gob.servir.gpa.service.ejb.ProgramacionAreasServiceLocal;
import pe.gob.servir.gpa.util.ConstanteEJB;

@Stateless
public class ProgramacionAreasServiceImpl implements ProgramacionAreasServiceLocal 
{

	/** Metodo Constructor */
	public ProgramacionAreasServiceImpl() {
	}

	@EJB
	private ProgramacionAreasDAOLocal programacionAreasDAOLocal;
	
	ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);

	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(ProgramacionAreasServiceImpl.class);

	public ProgramacionAreasDTO registrarProgramacionAreas(
			ProgramacionAreasDTO programacionAreasDTO) throws Exception 
	{
		try 
		{
			programacionAreasDTO = programacionAreasDAOLocal.registrarProgramacionAreas(programacionAreasDTO);			
		} catch (Exception e) 
		{
			log.error(e.getMessage(),e);
		}
		return programacionAreasDTO;
	}

	

	public List<ProgramacionAreasDTO> buscarProgramacionAreas(
			Integer programacionId, Integer areaId) throws Exception {
		List<ProgramacionAreasDTO>listaProgramacionAreas = null;
		try 
		{
			listaProgramacionAreas = programacionAreasDAOLocal.buscarProgramacionAreas(programacionId, areaId);
		} catch (Exception e) 
		{
			log.error(e.getMessage(),e);
		}
		
		return listaProgramacionAreas;
	}



	public Boolean actualizarProgramacionAreas(
			ProgramacionAreasDTO programacionAreasDTO) throws Exception {
		try 
		{
			programacionAreasDAOLocal.actualizarProgramacionAreas(programacionAreasDTO);
			return true;
		}
		catch (Exception e) 
		{
		log.error(e.getMessage(),e);
		}
		
		return false;
		
	}



	public Boolean eliminarProgramacionAreas(Integer programacionAreaId)
			throws Exception 
	{
		try 
		{
			programacionAreasDAOLocal.eliminarProgramacionAreas(programacionAreaId);
			return true;
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(),e);
		}
		return false;
	}



	

	

}
