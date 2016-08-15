package pe.gob.servir.gpa.adm.dao;
import java.util.Date;
import java.util.List;

import pe.gob.servir.gpa.model.dto.AprobacionJefeDTO;


public interface AprobacionJefeDAOLocal {

	public List<AprobacionJefeDTO> buscarAprobacionJefe(Integer idProgramacion,Integer idEstado,Date fechaInicio,Date fechaFin) throws Exception;
	
	public boolean actualizarAprobacionJefe(Integer idAprobacion, Integer idEstado);
	
	public void registrarAprobacionJefe(AprobacionJefeDTO aprobacionJefeDTO);

	
	
	
	
	

}
