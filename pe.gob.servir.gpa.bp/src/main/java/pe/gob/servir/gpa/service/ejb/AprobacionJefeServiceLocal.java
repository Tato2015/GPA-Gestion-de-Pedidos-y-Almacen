package pe.gob.servir.gpa.service.ejb;
import java.util.Date;
import java.util.List;

import pe.gob.servir.gpa.model.dto.AprobacionJefeDTO;


public interface AprobacionJefeServiceLocal {

	public List<AprobacionJefeDTO> buscarAprobacionJefe(Integer idProgramacion,Integer idEstado,Date fechaInicio,Date fechaFin) throws Exception;
	
	public boolean actualizarAprobacionJefe(Integer idAprobacion, Integer idEstado);

	public void registraAprobacionJefe(AprobacionJefeDTO aprobacionJefeDTO);
	
	
	

}
