package pe.gob.servir.gpa.service.ejb;
import java.util.List;
import pe.gob.servir.gpa.model.dto.TipoDTO;

public interface TipoServiceLocal {

	public List<TipoDTO> buscarTipo(Integer identificadorTipoPadre,Integer identificadorTipo) throws Exception;

	public TipoDTO registrarTipo(TipoDTO tipoDTO) throws Exception;

	public Boolean actualizarTipo(TipoDTO tipoDTO) throws Exception;

}
