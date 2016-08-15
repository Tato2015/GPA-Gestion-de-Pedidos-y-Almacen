package pe.gob.servir.gpa.service.ejb;
import java.sql.Connection;
import java.util.List;
import pe.gob.servir.gpa.model.dto.RequerimientoAreaDTO;
public interface RequerimientoAreaServiceLocal 
{
		
	public List<RequerimientoAreaDTO>buscarRequerimientoArea(Integer requerimientoAreaId,Integer areaId,Short indicadorEnviadoAlJefe,Short indicadorRequeRecibidoDelJefe,Integer programacionId)throws Exception;
	
	public RequerimientoAreaDTO registrarRequerimientoArea(RequerimientoAreaDTO requerimientoAreaDTO)throws Exception;
	
	public boolean actualizarRequerimientoAreaDTO(RequerimientoAreaDTO requerimientoAreaDTO)throws Exception;	
	
	public Connection conexionDataSource()throws Exception;
	
	public void cerrarConexionDataSource()throws Exception;
	
	
}
