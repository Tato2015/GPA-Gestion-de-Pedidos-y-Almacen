/**
 * <ul>
 * <li>Copyright 2015 Organismo Supervisor de las Contrataciones del Estado -
 * OSCE. Todos los derechos reservados.</li></ul>
 * Objeto		: UsuarioRolDAOImpl.java
 * Descripcion	: Clase aprobacionJefe interfaces de UsuarioRol que 
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
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.persistence.NoResultException;
import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;

import pe.gob.servir.gpa.adm.dao.AprobacionJefeDAOLocal;
import pe.gob.servir.gpa.model.dto.AprobacionJefeDTO;
import pe.gob.servir.gpa.model.dto.AuditoriaDTO;
import pe.gob.servir.gpa.model.dto.EstadoDTO;
import pe.gob.servir.gpa.model.dto.RequerimientoAreaDTO;
import pe.gob.servir.gpa.model.dto.UsuarioDTO;
import pe.gob.servir.gpa.util.ConstanteEJB;

@Stateless
public class AprobacionJefeDAOImpl implements AprobacionJefeDAOLocal {

	/** Metodo Constructor */
	public AprobacionJefeDAOImpl() {
	}

	ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);

	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(AprobacionJefeDAOImpl.class);

	public List<AprobacionJefeDTO> buscarAprobacionJefe(Integer idProgramacion,
			Integer idEstado, Date fechaInicio, Date fechaFin) throws Exception {

		List<AprobacionJefeDTO> listaAprobacionJefe = null;
		AprobacionJefeDTO aprobacionJefeDTO = null;
		AuditoriaDTO auditoriaDTO = null;
		Connection conn = null;
		CallableStatement call = null;

		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(propGPA
					.getString(ConstanteEJB.CONEXION_SERVIR));

			conn = ds.getConnection();

			String sql = "begin PKG_APROBACION.SP_BUSCAR_APROBACION(?,?,?,?,?);end;";
			call = conn.prepareCall(sql);

			if (idProgramacion != null) {
				if (idProgramacion != 0) {
					call.setInt(1, idProgramacion);
				} else {
					call.setNull(1, OracleTypes.NULL);
				}
			} else {
				call.setNull(1, OracleTypes.NULL);
			}

			if (idEstado != null) {
				if (idEstado != 0) {
					call.setInt(2, idEstado);
				} else {
					call.setNull(2, OracleTypes.NULL);
				}
			} else {
				call.setNull(2, OracleTypes.NULL);
			}

			if (fechaInicio != null) {
				call.setDate(3, new java.sql.Date(fechaInicio.getTime()));
			} else {
				call.setNull(3, OracleTypes.NULL);
			}

			if (fechaFin != null) {
				call.setDate(4, new java.sql.Date(fechaFin.getTime()));
			} else {
				call.setNull(4, OracleTypes.NULL);
			}

			call.registerOutParameter(5, OracleTypes.CURSOR);
			call.execute();

			ResultSet rs = (ResultSet) call.getObject(5);
			listaAprobacionJefe = new ArrayList<AprobacionJefeDTO>();

			while (rs.next()) {
				aprobacionJefeDTO = new AprobacionJefeDTO();
				auditoriaDTO = new AuditoriaDTO();
				aprobacionJefeDTO.setIdentificadorAprobacion(rs.getInt(1));
				aprobacionJefeDTO.setRequerimientoArea(new RequerimientoAreaDTO());
//					aprobacionJefeDTO.getRequerimientoArea()
//							.setIdRequerimientoArea(rs.getLong(2));

				aprobacionJefeDTO.setUsuario(new UsuarioDTO());
					aprobacionJefeDTO.getUsuario().setId(
							rs.getInt(3));
					aprobacionJefeDTO.setIndicadorAprobado(rs.getInt(4));
					aprobacionJefeDTO.setIndicadorActivo(rs.getInt(5));
					auditoriaDTO.setUsuarioRegistro(rs.getString(6));
					auditoriaDTO.setDescripcionIpRegistro(rs.getString(7));
					auditoriaDTO.setFechaRegistro(rs.getDate(8));
					auditoriaDTO.setUsuarioModificacion(rs.getString(9));
					auditoriaDTO.setDescripcionIpModificacion(rs.getString(10));
					auditoriaDTO.setFechaModificacion(rs.getDate(11));
				aprobacionJefeDTO.setEstado(new EstadoDTO());
					aprobacionJefeDTO.getEstado().setId(
							rs.getInt(12));
					aprobacionJefeDTO.setFechaAtendido(rs.getDate(13));
					aprobacionJefeDTO.setAuditoria(auditoriaDTO);
				listaAprobacionJefe.add(aprobacionJefeDTO);
			}
			rs.close();

		} catch (NoResultException nre) {
			listaAprobacionJefe = null;
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

		return listaAprobacionJefe;
	}

	public boolean actualizarAprobacionJefe(Integer idAprobacion,
			Integer idEstado) {

		Connection conn = null;
		CallableStatement call = null;

		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(propGPA
					.getString(ConstanteEJB.CONEXION_SERVIR));
			conn = ds.getConnection();

			String sql = "begin PKG_APROBACION.SP_APROBAR (?,?,?); end;";
			call = conn.prepareCall(sql);

			if (idAprobacion != null && idAprobacion != 0) {
				call.setInt(1, idAprobacion);
			} else {
				call.setNull(1, OracleTypes.NULL);
			}

			if (idEstado != null && idEstado != 0) {
				call.setInt(2, idEstado);
			} else {
				call.setNull(2, OracleTypes.NULL);
			}

			call.registerOutParameter(3, OracleTypes.INTEGER);
			call.execute();
			//Integer id = (Integer) call.getObject(3);

		} catch (NoResultException nre) {			
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

	public void registrarAprobacionJefe(AprobacionJefeDTO aprobacionJefeDTO) {
		Connection conn = null;
		CallableStatement call = null;
		try {
			InitialContext ic = new InitialContext();
			
			DataSource ds = (DataSource) ic.lookup(propGPA.getString(ConstanteEJB.CONEXION_SERVIR));

			conn = ds.getConnection();

			String sql = "begin PKG_APROBACION.SP_REGISTRAR_APROBACION(?,?,?,?,?,?); end;";
			call = conn.prepareCall(sql);
//			call.setLong(1, aprobacionJefeDTO.getRequerimientoArea().getIdRequerimientoArea());
			call.setLong(2, aprobacionJefeDTO.getUsuario().getId());
			call.setString(3, aprobacionJefeDTO.getAuditoria().getUsuarioRegistro());
			call.setString(4, aprobacionJefeDTO.getAuditoria().getDescripcionIpRegistro());
			call.setInt(5, aprobacionJefeDTO.getEstado().getId());
			
			call.registerOutParameter(6, OracleTypes.INTEGER);
			call.execute();
			Integer identificador=(Integer)call.getObject(6);
			
				try {
					aprobacionJefeDTO.setIdentificadorAprobacion(identificador);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			
		} catch (NoResultException nre) {
			aprobacionJefeDTO = null;
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
		
	}
	
	
}
