package pe.gob.servir.gpa.service.ejb;
import java.util.List;

import pe.gob.servir.gpa.model.dto.AreaDTO;
public interface AreaServiceLocal {
		
	public List<AreaDTO> buscarArea(Integer areaPadre,String descripcionArea) throws Exception;
//	public AreaDTO obtenerArea(Integer identificadorArea) throws Exception;

}

