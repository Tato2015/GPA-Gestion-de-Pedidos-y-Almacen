/**
 * <ul>
 * <li>Copyright 2015 Organismo Supervisor de las Contrataciones del Estado -
 * OSCE. Todos los derechos reservados.</li></ul>
 * Objeto		: UsuarioRolDAOImpl.java
 * Descripcion	: Clase tipo interfaces de UsuarioRol que 
 * 				  persiste con la base de datos.
 * Autor		: Consultora  - P & P BMS (P & P Business Management Solutions)
 * ----------------------------------------------------------------------------
 * Modificaciones
 * Codigo	Fecha		Nombre			Descripcion
 * ----------------------------------------------------------------------------
 * 
 */

package pe.gob.servir.gpa.adm.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.persistence.NoResultException;
import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;

import pe.gob.servir.gpa.adm.dao.TipoDAOLocal;
import pe.gob.servir.gpa.model.dto.EstadoDTO;
import pe.gob.servir.gpa.model.dto.TipoDTO;
import pe.gob.servir.gpa.util.ConstanteEJB;

@Stateless
public class TipoDAOImpl implements TipoDAOLocal {

	/** Metodo Constructor */
	public TipoDAOImpl() 
	{
	}

	ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);

	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(TipoDAOImpl.class);

	public List<TipoDTO> buscarTipo(Integer identificadorTipoPadre,
			Integer identificadorEstado) throws Exception {
		List<TipoDTO> listaTipo = null;
		TipoDTO tipoDTO = null;		
		Connection conn = null;
		CallableStatement call = null;

		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(propGPA
					.getString(ConstanteEJB.CONEXION_SERVIR));

			conn = ds.getConnection();

			String sql = "begin PKG_TIPO.SP_BUSCAR_TIPO(?,?,?);end;";
			call = conn.prepareCall(sql);

			if (identificadorTipoPadre != null) {
				if (identificadorTipoPadre != 0) {
					call.setInt(1, identificadorTipoPadre);
				} else {
					call.setNull(1, OracleTypes.NULL);
				}
			} else {
				call.setNull(1, OracleTypes.NULL);
			}

			if (identificadorEstado != null) {
				if (identificadorEstado != 0) {
					call.setInt(2, identificadorEstado);
				} else {
					call.setNull(2, OracleTypes.NULL);
				}
			} else {
				call.setNull(2, OracleTypes.NULL);
			}

			call.registerOutParameter(3, OracleTypes.CURSOR);
			call.execute();
			ResultSet rs = (ResultSet) call.getObject(3);
			listaTipo = new ArrayList<TipoDTO>();

			while (rs.next()) {
				tipoDTO = new TipoDTO();				

				try {
					tipoDTO.setId(rs.getInt(1));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					System.out.println(rs.getInt(1));
				}
				
				tipoDTO.setPadre(new TipoDTO());
				
				try {
					tipoDTO.getPadre().setId(rs.getInt(2));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					System.out.println(rs.getInt(2));
				}


				try {
					tipoDTO.setCodigoTipo(rs.getString(3));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					System.out.println(rs.getString(4));
				}

				try {
					tipoDTO.setDescripcionTipo(rs.getString(4));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					System.out.println(rs.getString(5));
				}

				try {
					tipoDTO.setAbreviaturaTipo(rs.getString(5));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					System.out.println(rs.getString(6));
				}

				try {
					tipoDTO.setIndicadorActivo(rs.getShort(6));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					System.out.println(rs.getInt(7));
				}

				try {
					tipoDTO.setCodigoUsuarioRegistro(rs.getString(7));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					System.out.println(rs.getString(8));
				}

				try {
					tipoDTO.setIpRegistro(rs.getString(8));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}

				try {
					tipoDTO.setFechaRegistro(rs.getDate(9));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}

				try {
					tipoDTO.setCodigoUsuarioModificacion(rs.getString(10));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}

				try {
					tipoDTO.setIpModificacion(rs.getString(11));
				} catch (Exception e) {
					log.error(e.getMessage(), e);

				}

				try {
					tipoDTO.setFechaModificacion(rs.getDate(12));
				} catch (Exception e) {
					log.error(e.getMessage(), e);

				}

				tipoDTO.setEstado(new EstadoDTO());

				try {
					tipoDTO.getEstado().setId(rs.getInt(13));
				} catch (Exception e) {
					log.error(e.getMessage(), e);

				}

				try {
					tipoDTO.getEstado().setDescripcionEstado(rs.getString(14));

				} catch (Exception e) {
					log.error(e.getMessage(), e);

				}
						
				try {
					tipoDTO.getPadre().setDescripcionTipo(rs.getString(15));
				} catch (Exception e) {
					log.error(e.getMessage(), e);

				}
				
				listaTipo.add(tipoDTO);
			}
			rs.close();
		} catch (NoResultException nre) {
			listaTipo = null;
			log.error(nre.getMessage(), nre);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		return listaTipo;
	}

	//Método que recibe como parámetro un objeto de la clase TipoDTO, el objeto tipoDTO tienes 
	// los valores seteados para enviarlos como parámetro al store procedure  PKG_TIPO.SP_REGISTRAR_TIPO()
//	
	public TipoDTO registrarTipo(TipoDTO tipoDTO) throws Exception {
		Connection conn = null;
		CallableStatement call = null;

		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(propGPA
					.getString(ConstanteEJB.CONEXION_SERVIR));
			conn = ds.getConnection();

			//7 parametros
			String sql = "begin PKG_TIPO.SP_REGISTRAR_TIPO(?,?,?,?,?,?,?); end;";
			call = conn.prepareCall(sql);
			
			if (tipoDTO.getPadre().getId()!= null && tipoDTO.getPadre().getId()>=0 ) {
				call.setInt(1, tipoDTO.getPadre().getId());
			}else{
				call.setNull(1,OracleTypes.NULL);
			}
			
			if (tipoDTO.getDescripcionTipo()!= null && tipoDTO.getDescripcionTipo()!="") {
				call.setString(2, tipoDTO.getDescripcionTipo());
			}else{
				call.setNull(2,OracleTypes.NULL);
			}
			
			if (tipoDTO.getAbreviaturaTipo()!= null && tipoDTO.getAbreviaturaTipo()!="") {
				call.setString(3, tipoDTO.getAbreviaturaTipo());
			}else{
				call.setNull(3,OracleTypes.NULL);
			}
			
			if (tipoDTO.getCodigoUsuarioRegistro()!= null && tipoDTO.getCodigoUsuarioRegistro()!="") {
				call.setString(4, tipoDTO.getCodigoUsuarioRegistro());
			}else{
				call.setNull(4,OracleTypes.NULL);
			}
			
			if (tipoDTO.getIpRegistro() != null && tipoDTO.getIpRegistro()!="") {
				call.setString(5, tipoDTO.getIpRegistro());
			}else{
				call.setNull(5,OracleTypes.NULL);
			}
							
			if (tipoDTO.getEstado().getId()!= null && tipoDTO.getEstado().getId()>=1) {
				call.setInt(6, tipoDTO.getEstado().getId());
			}else{
				call.setNull(6,OracleTypes.NULL);
			}
			
			call.registerOutParameter(7, OracleTypes.INTEGER);
			call.execute();
			Integer identificador=(Integer)call.getObject(7);
				try {
					tipoDTO.setId(identificador.intValue());
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			
		} catch (NoResultException nre) {
			tipoDTO = null;
			log.error(nre.getMessage(), nre);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
			
		return tipoDTO;
	}


	public Boolean actualizarTipo(TipoDTO tipoDTO) throws Exception {
		
		Connection conn = null;
		CallableStatement call = null;
		try {
			InitialContext ic = new InitialContext();
			
			DataSource ds = (DataSource) ic.lookup(propGPA.getString(ConstanteEJB.CONEXION_SERVIR));

			conn = ds.getConnection();

			String sql = "begin PKG_TIPO.SP_ACTUALIZAR_TIPO (?,?,?,?,?,?,?,?,?); end;";
			call = conn.prepareCall(sql);
			
			if (tipoDTO.getId()!= null && tipoDTO.getId()>=0 ) {
				call.setInt(1, tipoDTO.getId());
			}else{
				call.setNull(1,OracleTypes.NULL);
			}
			
			
			if (tipoDTO.getPadre().getId()!= null && tipoDTO.getPadre().getId()>=0 ) {
				call.setInt(2, tipoDTO.getPadre().getId());
			}else{
				call.setNull(2,OracleTypes.NULL);
			}
			
			if (tipoDTO.getDescripcionTipo()!= null && tipoDTO.getDescripcionTipo()!="") {
				call.setString(3, tipoDTO.getDescripcionTipo());
			}else{
				call.setNull(3,OracleTypes.NULL);
			}
			
			if (tipoDTO.getAbreviaturaTipo()!= null && tipoDTO.getAbreviaturaTipo()!="") {
				call.setString(4, tipoDTO.getAbreviaturaTipo());
			}else{
				call.setNull(4,OracleTypes.NULL);
			}
			
			if (tipoDTO.getIndicadorActivo()!= null && tipoDTO.getIndicadorActivo()>=0) {
				call.setShort(5, tipoDTO.getIndicadorActivo());
			}else{
				call.setNull(5,OracleTypes.NULL);
			}
			
			
			if (tipoDTO.getCodigoUsuarioModificacion()!= null && tipoDTO.getCodigoUsuarioModificacion()!="") {
				call.setString(6, tipoDTO.getCodigoUsuarioModificacion());
			}else{
				call.setNull(6,OracleTypes.NULL);
			}
			
			if (tipoDTO.getIpModificacion() != null && tipoDTO.getIpModificacion()!="") {
				call.setString(7, tipoDTO.getIpModificacion());
			}else{
				call.setNull(7,OracleTypes.NULL);
			}
			
						
			if (tipoDTO.getEstado().getId()!= null && tipoDTO.getEstado().getId()>=1) {
				call.setInt(8, tipoDTO.getEstado().getId());
			}else{
				call.setNull(8,OracleTypes.NULL);
			}
			
			call.registerOutParameter(9, OracleTypes.INTEGER);
			call.execute();
			//Integer identificador=(Integer)call.getObject(9);
			
		} catch (NoResultException nre) {
			tipoDTO = null;
			log.error(nre.getMessage(), nre);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return true;
	}


	// @Override
	// public List<TipoDTO> buscarTipoPadre() throws Exception {
	// // TODO Auto-generated method stub
	// return null;
	// }
	
	

}
