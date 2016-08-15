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
import pe.gob.servir.gpa.adm.dao.TipoDAOLocal;
import pe.gob.servir.gpa.model.dto.TipoDTO;
import pe.gob.servir.gpa.service.ejb.TipoServiceLocal;
import pe.gob.servir.gpa.util.ConstanteEJB;

@Stateless
public class TipoServiceImpl implements TipoServiceLocal {

	/** Metodo Constructor */
	public TipoServiceImpl() {
	}

	@EJB
	private TipoDAOLocal tipoDAOLocal;
	
	ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);

	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(TipoServiceImpl.class);

	public List<TipoDTO> buscarTipo(Integer identificadorTipoPadre,
			Integer identificadorEstado) throws Exception {
		List<TipoDTO> listaTipo = null;		
		try {
			listaTipo = tipoDAOLocal.buscarTipo(identificadorTipoPadre, identificadorEstado);
		} catch (Exception e) {
		log.error(e.getMessage());
		}

		return listaTipo;
	}

	//Método que recibe como parámetro un objeto de la clase TipoDTO, el objeto tipoDTO tienes 
	// los valores seteados para enviarlos como parámetro al store procedure  PKG_TIPO.SP_REGISTRAR_TIPO()

	public TipoDTO registrarTipo(TipoDTO tipoDTO) throws Exception {		
		try {
			tipoDTO = tipoDAOLocal.registrarTipo(tipoDTO);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}			
		return tipoDTO;
	}


	public Boolean actualizarTipo(TipoDTO tipoDTO) throws Exception {
		try {
			tipoDAOLocal.actualizarTipo(tipoDTO);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}		
		return false;
	}


	// @Override
	// public List<TipoDTO> buscarTipoPadre() throws Exception {
	// // TODO Auto-generated method stub
	// return null;
	// }
	
	

}
