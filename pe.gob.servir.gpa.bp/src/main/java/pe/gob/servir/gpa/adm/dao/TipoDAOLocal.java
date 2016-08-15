package pe.gob.servir.gpa.adm.dao;
import java.util.List;
import pe.gob.servir.gpa.model.dto.TipoDTO;

public interface TipoDAOLocal {

	public List<TipoDTO> buscarTipo(Integer identificadorTipoPadre,Integer identificadorTipo) throws Exception;

	public TipoDTO registrarTipo(TipoDTO tipoDTO) throws Exception;

	public Boolean actualizarTipo(TipoDTO tipoDTO) throws Exception;



}
