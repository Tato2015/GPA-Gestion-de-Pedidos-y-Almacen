
package pe.gob.servir.gpa.adm.dao.impl;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;

import pe.gob.servir.gpa.adm.dao.AreaDAOLocal;
import pe.gob.servir.gpa.model.dto.AreaDTO;
import pe.gob.servir.gpa.model.dto.EstadoDTO;
import pe.gob.servir.gpa.util.ConstanteEJB;


@Stateless
public class AreaDAOImpl implements AreaDAOLocal{

	/** Metodo Constructor 	 */
	public AreaDAOImpl() {}
	
	
	
	/** Definicion del contexto de persistencia para el acceso al modelo de datos*/
	@PersistenceContext(unitName="pe.gob.servir.gpa.bp")
	private	EntityManager	em;
	
	 /** Atributo del tipo properties, donde se almacenas propiedades de la aplicacion*/
		ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);
	
	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(AreaDAOImpl.class);

	public List<AreaDTO> buscarArea(Integer areaPadre,String descripcionArea) throws Exception 
	{
		List<AreaDTO> listaArea=null;
		AreaDTO areaDTO=null;
		Connection conn = null;
		CallableStatement call = null;
		try 
		{
			InitialContext ic = new InitialContext();		
			DataSource ds = (DataSource) ic.lookup(propGPA.getString(ConstanteEJB.CONEXION_SERVIR));
			conn = ds.getConnection();
			String sql = "begin PKG_AREA.SP_BUSCAR_AREA(?,?,?); end;";
			call = conn.prepareCall(sql);
			
			if (areaPadre != null && areaPadre>=0) 
			{
					call.setInt(1, areaPadre);			
			} else 
			{
				call.setNull(1, OracleTypes.NULL);
			}
			
			if (descripcionArea != null && !descripcionArea.equals("")) 
			{
				call.setString(2, descripcionArea);
				
			} else 
			{
				call.setNull(2, OracleTypes.NULL);
			}
															
			call.registerOutParameter(3, OracleTypes.CURSOR);			
			call.execute();
			ResultSet rs = (ResultSet) call.getObject(3);
			listaArea=new ArrayList<AreaDTO>();
			while (rs.next()) {
				areaDTO = new AreaDTO();				
				try 
				{
					areaDTO.setId(rs.getInt(1));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
							
				areaDTO.setAreaPadre(new AreaDTO());
				try 
				{
					areaDTO.getAreaPadre().setId(rs.getInt(2));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					areaDTO.setCodigoArea(rs.getString(3));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					areaDTO.setDescripcionArea(rs.getString(4));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					areaDTO.setAbreviaturaArea(rs.getString(5));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					areaDTO.setIndicadorActivo(rs.getShort(6));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					areaDTO.setCodigoUsuarioRegistro(rs.getString(7));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					areaDTO.setIpRegistro(rs.getString(8));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					areaDTO.setFechaRegistro(rs.getDate(9));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					areaDTO.setCodigoUsuarioModificacion(rs.getString(10));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					areaDTO.setIpModificacion(rs.getString(11));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
				
				try 
				{
					areaDTO.setFechaModificacion(rs.getDate(12));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
							
				areaDTO.setEstado(new EstadoDTO());
				try 
				{
					areaDTO.getEstado().setId(rs.getInt(13));
				} catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
									
				listaArea.add(areaDTO);				
			}
			rs.close();
			
		} catch (NoResultException nre) 
		{
			listaArea = null;
			log.error(nre.getMessage(), nre);
		} catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		} finally 
		{
			try 
			{
				conn.close();
			} catch (Exception e) 
			{
				log.error(e.getMessage(), e);
			}
		}
		return listaArea;
	}

	
//	
//	public AreaDTO obtenerArea(Integer identificadorArea) throws Exception {
//		AreaDTO area=null;
//		Connection conn = null;
//		CallableStatement call = null;
//		try {
//			InitialContext ic = new InitialContext();
//			
//			DataSource ds = (DataSource) ic.lookup(propGPA.getString(ConstanteEJB.CONEXION_SERVIR));
//
//			conn = ds.getConnection();
//
//			String sql = "begin PKG_AREA.SP_OBTENER_AREA(?,?); end;";
//			call = conn.prepareCall(sql);
//			
//			if (identificadorArea != null && identificadorArea!=0) {
//					call.setInt(1, identificadorArea);
//				
//			} else {
//				call.setNull(1, OracleTypes.NULL);
//			}
//			
//			call.registerOutParameter(2, OracleTypes.CURSOR);
//			
//			call.execute();
//			ResultSet rs = (ResultSet) call.getObject(5);
//			while (rs.next()) {
//				area = new AreaDTO();
//				try {
//					area.setIdentificadorArea(rs.getInt(1));
//				} catch (Exception e) {
//					log.error(e.getMessage(), e);
//				}
//				try {
//					area.setIdentificadorPadre(rs.getInt(2));
//				} catch (Exception e) {
//					log.error(e.getMessage(), e);
//				}
//				
//				try {
//					area.setCodigoArea(rs.getString(3));
//				} catch (Exception e) {
//					log.error(e.getMessage(), e);
//				}
//				try {
//					area.setDescripcionArea(rs.getString(4));
//				} catch (Exception e) {
//					log.error(e.getMessage(), e);
//				}
//				try {
//					area.setAbreviaturaArea(rs.getString(5));
//				} catch (Exception e) {
//					log.error(e.getMessage(), e);
//				}
//				
//				try {
//					area.setIndicadorActivo(rs.getInt(6));
//				} catch (Exception e) {
//					log.error(e.getMessage(), e);
//				}
//				
//				
//				area.setAuditoria(new AuditoriaDTO());
//				try {
//					area.getAuditoria().setUsuarioRegistro(rs.getString(7));
//				} catch (Exception e) {
//					log.error(e.getMessage(), e);
//				}
//				
//				try {
//					area.getAuditoria().setDescripcionIpRegistro(rs.getString(8));
//				} catch (Exception e) {
//					log.error(e.getMessage(), e);
//				}
//				
//				try {
//					area.getAuditoria().setFechaRegistro(rs.getDate(9));
//				} catch (Exception e) {
//					log.error(e.getMessage(), e);
//				}
//				
//				try {
//					area.getAuditoria().setUsuarioModificacion(rs.getString(10));
//				} catch (Exception e) {
//					log.error(e.getMessage(), e);
//				}
//				
//				try {
//					area.getAuditoria().setDescripcionIpModificacion(rs.getString(11));
//				} catch (Exception e) {
//					log.error(e.getMessage(), e);
//				}
//				
//				try {
//					area.getAuditoria().setFechaModificacion(rs.getDate(12));
//				} catch (Exception e) {
//					log.error(e.getMessage(), e);
//				}
//				
//				area.setEstado(new EstadoDTO());
//				try {
//					area.getEstado().setIdentificadorEstado(rs.getInt(13));
//				} catch (Exception e) {
//					log.error(e.getMessage(), e);
//				}
//				
//				
//				try {
//					area.getEstado().setDescripcionEstado(rs.getString(14));
//				} catch (Exception e) {
//					log.error(e.getMessage(), e);
//				}
//				
//			}
//			rs.close();
//			
//		} catch (NoResultException nre) {
//			area = null;
//			log.error(nre.getMessage(), nre);
//
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//		} finally {
//			try {
//				conn.close();
//			} catch (Exception e) {
//				log.error(e.getMessage(), e);
//			}
//		}
//		return area;
//	}
//	
//	

}
