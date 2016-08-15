package pe.gob.servir.gpa.service.ejb;

import java.util.List;

import pe.gob.servir.gpa.model.dto.RequerimientoUsuarioDTO;

public interface RequerimientoUsuarioServiceLocal 
{

	public List<RequerimientoUsuarioDTO>buscarRequerimientoUsuarioDTO(Integer requerimientoUsuarioId,Integer requerimientoId,Integer usuarioId,Integer programacionAreasId,Integer estadoId,Short indicadorEnviadoAlEncargado,Short indicadorRequerimientoRecepcionEncargado)throws Exception;
	
	public RequerimientoUsuarioDTO registrarRequerimientoUsuarioDTO(RequerimientoUsuarioDTO requerimientoUsuarioDTO)throws Exception;
	
	public boolean actualizarRequerimientoUsuario(RequerimientoUsuarioDTO  requerimientoUsuarioDTO)throws Exception;
		
}
