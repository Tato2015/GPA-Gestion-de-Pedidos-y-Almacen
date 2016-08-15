package pe.gob.servir.gpa.service.ejb.impl;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import pe.gob.servir.gpa.adm.dao.RequerimientoDAOLocal;
import pe.gob.servir.gpa.model.dto.RequerimientoDTO;
import pe.gob.servir.gpa.service.ejb.RequerimientoServiceLocal;
import pe.gob.servir.gpa.util.ConstanteEJB;

@Stateless
public class RequerimientoServiceImpl implements RequerimientoServiceLocal 
{

	/** Metodo Constructor */
	public RequerimientoServiceImpl() {
	}

	@EJB
	private RequerimientoDAOLocal requerimientoDAOLocal;
	
	ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);

	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(RequerimientoServiceImpl.class);

	public RequerimientoDTO registrarRequerimiento(
			RequerimientoDTO requerimientoDTO) throws Exception 
	{
		try 
		{
			requerimientoDTO = requerimientoDAOLocal.registrarRequerimiento(requerimientoDTO);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		}					
		return requerimientoDTO;
	}

	public List<RequerimientoDTO> buscarRequerimiento(Integer requerimientoId,
			Integer estadoId) throws Exception 
	{
		List<RequerimientoDTO> listaRequerimiento = null;
		try 
		{
			listaRequerimiento = requerimientoDAOLocal.buscarRequerimiento(requerimientoId, estadoId);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage());
		}
		return listaRequerimiento;
	}

	
	
	

}
