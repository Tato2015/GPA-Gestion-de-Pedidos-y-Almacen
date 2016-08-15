///**
// * <ul>
// * <li>Copyright 2015 Organismo Supervisor de las Contrataciones del Estado -
// * OSCE. Todos los derechos reservados.</li></ul>
// * Objeto		: UsuarioRolDAOImpl.java
// * Descripcion	: Clase aprobacionJefe interfaces de UsuarioRol que 
// * 				  persiste con la base de datos.
// * Autor		: Consultora  - P & P BMS (P & P Business Management Solutions)
// * ----------------------------------------------------------------------------
// * Modificaciones
// * Codigo	Fecha		Nombre			Descripcion
// * ----------------------------------------------------------------------------
// * 
// */
//
//package pe.gob.servir.gpa.service.ejb.impl;
//
//
//import java.sql.CallableStatement;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.ResourceBundle;
//
//import javax.ejb.EJB;
//import javax.ejb.Stateless;
//import javax.naming.InitialContext;
//import javax.persistence.NoResultException;
//import javax.sql.DataSource;
//
//import oracle.jdbc.OracleTypes;
//
//import org.apache.log4j.Logger;
//
//import pe.gob.servir.gpa.adm.dao.AprobacionJefeDAOLocal;
//import pe.gob.servir.gpa.model.dto.AprobacionJefeDTO;
//import pe.gob.servir.gpa.model.dto.AreaDTO;
//import pe.gob.servir.gpa.model.dto.AuditoriaDTO;
//import pe.gob.servir.gpa.model.dto.EstadoDTO;
//import pe.gob.servir.gpa.model.dto.ProgramacionDTO;
//import pe.gob.servir.gpa.model.dto.RequerimientoAreaDTO;
//import pe.gob.servir.gpa.model.dto.TipoDTO;
//import pe.gob.servir.gpa.model.dto.UsuarioDTO;
//import pe.gob.servir.gpa.service.ejb.AprobacionJefeServiceLocal;
//import pe.gob.servir.gpa.util.ConstanteEJB;
//
//@Stateless
//public class AprobacionJefeServiceImpl implements AprobacionJefeServiceLocal {
//
//	/** Metodo Constructor */
//	public AprobacionJefeServiceImpl() {
//	}
//
//	@EJB 
//	private AprobacionJefeDAOLocal aprobacionJefeDAOLocal;
//
//	ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);
//
//	/** Atributo que registra en el log del servidor */
//	final static Logger log = Logger.getLogger(AprobacionJefeServiceImpl.class);
//
//	public List<AprobacionJefeDTO> buscarAprobacionJefe(
//			Integer idProgramacion, Integer idEstado, Date fechaInicio,
//			Date fechaFin) throws Exception {
//		
//		List<AprobacionJefeDTO> listaAprobacionJefe = null;
//		AprobacionJefeDTO aprobacionJefeDTO = null;
//		AuditoriaDTO auditoriaDTO = null;
//		Connection conn = null;
//		CallableStatement call = null;
//		
//			try {
//				InitialContext ic = new InitialContext();
//				DataSource ds = (DataSource) ic.lookup(propGPA
//						.getString(ConstanteEJB.CONEXION_SERVIR));
//
//				conn = ds.getConnection();
//				
//				String sql = "begin PKG_APROBACION.SP_BUSCAR_APROBACION(?,?,?,?,?);end;";
//				call = conn.prepareCall(sql);
//				
//				
//				if(idProgramacion != null){
//					if (idProgramacion != 0) {
//						call.setInt(1, idProgramacion);
//					}else{
//						call.setNull(1, OracleTypes.NULL);					
//					}					
//					}else{
//					call.setNull(1, OracleTypes.NULL);
//			}
//
//				
//				
//				
//				if(idEstado != null){
//					if (idEstado != 0) {
//						call.setInt(2, idEstado);
//					}else{
//						call.setNull(2, OracleTypes.NULL);					
//					}					
//				}else{
//						call.setNull(2, OracleTypes.NULL);
//				}
//				
//
//				if (fechaInicio != null) {
//					call.setDate(3, new java.sql.Date(fechaInicio.getTime()));
//				} else {
//					call.setNull(3, OracleTypes.NULL);
//				}
//				
//
//				if (fechaFin != null) {
//					call.setDate(4, new java.sql.Date(fechaFin.getTime()));
//				} else {
//					call.setNull(4, OracleTypes.NULL);
//				}
//				
//				call.registerOutParameter(5, OracleTypes.CURSOR);
//				call.execute();
//				
//				ResultSet rs = (ResultSet) call.getObject(5);
//				listaAprobacionJefe = new ArrayList<AprobacionJefeDTO>();
//				
//				
//				while (rs.next()) {
//					aprobacionJefeDTO = new AprobacionJefeDTO();
//					auditoriaDTO = new AuditoriaDTO();
//					
//					try {
//						aprobacionJefeDTO.setIdentificadorAprobacion(rs.getInt(1));
//					} catch (Exception e) {
//						log.error(e.getMessage(), e);
//						System.out.println(rs.getInt(1));
//					}
//					
//					aprobacionJefeDTO.setRequerimientoArea(new RequerimientoAreaDTO());
//					
//					try {
//						aprobacionJefeDTO.getRequerimientoArea().setIdRequerimientoArea(rs.getLong(2));
//					} catch (Exception e) {
//						log.error(e.getMessage(), e);
//						System.out.println(rs.getInt(2));
//					}
//					
//					aprobacionJefeDTO.setUsuario(new UsuarioDTO());
//					
//					try {
//						aprobacionJefeDTO.getUsuario().setIdentificadorUsuario(rs.getLong(3));
//					} catch (Exception e) {
//						log.error(e.getMessage(), e);
//					}
//					
//					try {
//						aprobacionJefeDTO.setIndicadorAprobado(rs.getInt(4));
//					} catch (Exception e) {
//						log.error(e.getMessage(), e);
//					}
//					try {
//						aprobacionJefeDTO.setIndicadorActivo(rs.getInt(5));
//					} catch (Exception e) {
//						log.error(e.getMessage(), e);
//					}																						
//			
//					
//					try {
//						auditoriaDTO.setUsuarioRegistro(rs.getString(6));
//					} catch (Exception e) {
//						log.error(e.getMessage(), e);
//					}
//					
//					try {
//						auditoriaDTO.setDescripcionIpRegistro(rs.getString(7));
//					} catch (Exception e) {
//						log.error(e.getMessage(), e);
//					}
//
//					try {
//						auditoriaDTO.setFechaRegistro(rs.getDate(8));
//					} catch (Exception e) {
//						log.error(e.getMessage(), e);
//					}
//
//					try {
//						auditoriaDTO.setUsuarioModificacion(rs.getString(9));
//					} catch (Exception e) {
//						log.error(e.getMessage(), e);
//						System.out.println(rs.getString(10));
//					}
//
//					try {
//						auditoriaDTO.setDescripcionIpModificacion(rs.getString(10));						
//					} catch (Exception e) {
//						log.error(e.getMessage(), e);
//						System.out.println(rs.getString(11));
//					}
//
//					try {
//						auditoriaDTO.setFechaModificacion(rs.getDate(11));						
//					} catch (Exception e) {
//						log.error(e.getMessage(), e);
//					}
//														
//					aprobacionJefeDTO.setEstado(new EstadoDTO());
//
//					try {
//						aprobacionJefeDTO.getEstado().setIdentificadorEstado(rs.getInt(12));						
//					} catch (Exception e) {
//						log.error(e.getMessage(), e);
//					}
//					
//					try {
//						aprobacionJefeDTO.setFechaAtendido(rs.getDate(13));						
//					} catch (Exception e) {
//						log.error(e.getMessage(), e);
//					}
//					
//					aprobacionJefeDTO.getRequerimientoArea().setProgramacion(new ProgramacionDTO());
//									
//					try {
//						aprobacionJefeDTO.getRequerimientoArea().getProgramacion().setDescripcionProgramacion(rs.getString(14));						
//					} catch (Exception e) {
//						log.error(e.getMessage(), e);
//					}
//									
//					aprobacionJefeDTO.getRequerimientoArea().setUsuario(new UsuarioDTO());					
//					aprobacionJefeDTO.getRequerimientoArea().getUsuario().setArea(new AreaDTO());					
//										
//					try {
//						aprobacionJefeDTO.getRequerimientoArea().getUsuario().getArea().setDescripcionArea(rs.getString(15));						
//					} catch (Exception e) {
//						log.error(e.getMessage(), e);
//					}
//					
//					aprobacionJefeDTO.getRequerimientoArea().setTipoRequerimiento(new TipoDTO());
//					
//					try {
//						aprobacionJefeDTO.getRequerimientoArea().getTipoRequerimiento().setDescripcionTipo(rs.getString(16));						
//					} catch (Exception e) {
//						log.error(e.getMessage(), e);										
//					}
//					try {
//						aprobacionJefeDTO.getEstado().setDescripcionEstado(rs.getString(17));						
//					} catch (Exception e) {
//						log.error(e.getMessage(), e);
//					}
//					aprobacionJefeDTO.setTipoRequerimiento(new TipoDTO());			
//					aprobacionJefeDTO.getTipoRequerimiento().setIdentificadorTipo(rs.getInt(19));
//					aprobacionJefeDTO.setAuditoria(auditoriaDTO);
//					listaAprobacionJefe.add(aprobacionJefeDTO);
//				}
//				rs.close();
//
//			} catch (NoResultException nre) {
//				listaAprobacionJefe = null;
//				log.error(nre.getMessage(), nre);
//			} catch (Exception e) {
//				log.error(e.getMessage(), e);
//			} finally {
//				try {
//					conn.close();
//				} catch (Exception e) {
//					log.error(e.getMessage(), e);
//				}
//			}
//
//		return listaAprobacionJefe;
//	}
//
//	public boolean actualizarAprobacionJefe(Integer idAprobacion,
//			Integer idEstado) {
//		
//		try {
//			aprobacionJefeDAOLocal.actualizarAprobacionJefe(idAprobacion, idEstado);
//			return true;
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//		}		
//		return false;
//	}
//
//
//
//	public void registraAprobacionJefe(AprobacionJefeDTO aprobacionJefeDTO) {
//		try {
//			aprobacionJefeDAOLocal.registrarAprobacionJefe(aprobacionJefeDTO);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		
//	}
//
//	
//
//
//	
//
//}
