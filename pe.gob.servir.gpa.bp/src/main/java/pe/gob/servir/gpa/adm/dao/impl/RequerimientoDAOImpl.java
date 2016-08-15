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

import pe.gob.servir.gpa.adm.dao.RequerimientoDAOLocal;
import pe.gob.servir.gpa.model.dto.RequerimientoDTO;
import pe.gob.servir.gpa.util.ConstanteEJB;

@Stateless
public class RequerimientoDAOImpl implements RequerimientoDAOLocal {

	/** Metodo Constructor */
	public RequerimientoDAOImpl() 
	{
	}

	ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);

	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(RequerimientoDAOImpl.class);

	public RequerimientoDTO registrarRequerimiento(
			RequerimientoDTO requerimientoDTO) throws Exception 
	{
		Connection conn = null;
		CallableStatement call = null;

		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(propGPA
					.getString(ConstanteEJB.CONEXION_SERVIR));
			conn = ds.getConnection();


			String sql = "begin PKG_REQUERIMIENTOS.SP_REGISTRAR_REQUERIMIENTO(?,?,?,?,?,?,?,?,?,?); end;";
			call = conn.prepareCall(sql);
			
			if (requerimientoDTO.getRequerimientoNumero()!=null && !requerimientoDTO.getRequerimientoNumero().equals("")) 
			{
				call.setString(1, requerimientoDTO.getRequerimientoNumero());
			}else{
				call.setNull(1,OracleTypes.NULL);
			}
			
			
			if(requerimientoDTO.getMontoTotal() != null && requerimientoDTO.getMontoTotal()>=0)
			{
				call.setInt(2,requerimientoDTO.getMontoTotal());
			}
			else
			{
				call.setNull(2,OracleTypes.NULL);
			}
			
			
			if(requerimientoDTO.getIndicadorAnulado()!=null && requerimientoDTO.getIndicadorAnulado()>=0)
			{
				call.setShort(3,requerimientoDTO.getIndicadorAnulado());
			}
			else
			{
				call.setNull(3,OracleTypes.NULL);	
			}
			
			
			if(requerimientoDTO.getIndicadorAprobado()!=null && requerimientoDTO.getIndicadorAprobado()>=0)
			{
				call.setShort(4,requerimientoDTO.getIndicadorAprobado());
			}
			else
			{
				call.setNull(4,OracleTypes.NULL);	
			}
			
			if(requerimientoDTO.getFechaAprobacion()!=null)
			{
				call.setDate(5, requerimientoDTO.getFechaAprobacion());
			}
			else
			{
				call.setNull(5,OracleTypes.NULL);
			}
			
			
			if(requerimientoDTO.getIndicadorActivo()!=null && requerimientoDTO.getIndicadorActivo()>=0)
			{
				call.setShort(6,requerimientoDTO.getIndicadorActivo());
			}
			else
			{
				call.setNull(6,OracleTypes.NULL);	
			}
			
			if(requerimientoDTO.getCodigoUsuarioRegistro()!=null && !requerimientoDTO.getCodigoUsuarioRegistro().equals(""))
			{
				call.setString(7,requerimientoDTO.getCodigoUsuarioRegistro());
			}
			else
			{
				call.setNull(7,OracleTypes.NULL);		
			}
			
			if(requerimientoDTO.getIpRegistro() != null && !requerimientoDTO.getIpRegistro().equals(""))
			{
				call.setString(8,requerimientoDTO.getIpRegistro());
			}
			else
			{
				call.setNull(8,OracleTypes.NULL);		
			}
			
			if(requerimientoDTO.getEstado().getId()!= null && requerimientoDTO.getEstado().getId()>=0)
			{
				call.setInt(9,requerimientoDTO.getEstado().getId());
			}
			else
			{
				call.setNull(9,OracleTypes.NULL);
			}

			call.registerOutParameter(10, OracleTypes.INTEGER);
			call.execute();
			Integer identificador=(Integer)call.getObject(10);
				try 
				{
					requerimientoDTO.setId(identificador.intValue());
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}
			
		}
		catch (NoResultException nre) 
		{
			requerimientoDTO = null;
			log.error(nre.getMessage(), nre);

		}
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		}
		finally 
		{
			try 
			{
				conn.close();
			}
			catch (Exception e) 
			{
				log.error(e.getMessage(), e);
			}
		}
			
		return requerimientoDTO;

	}

	public List<RequerimientoDTO> buscarRequerimiento(Integer requerimientoId,
			Integer estadoId) throws Exception 
	{	
		List<RequerimientoDTO> listaRequerimiento= null;
		RequerimientoDTO requerimientoDTO = null;		
		Connection conn = null;
		CallableStatement call = null;

		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(propGPA
					.getString(ConstanteEJB.CONEXION_SERVIR));

			conn = ds.getConnection();

			String sql = "begin PKG_REQUERIMIENTOS.SP_BUSCAR_REQUERIMIENTO(?,?,?);end;";
			call = conn.prepareCall(sql);

			if (requerimientoId != null && requerimientoId>=0)
			{
				call.setInt(1,requerimientoId);
			}
			else 
			{
				call.setNull(1, OracleTypes.NULL);
			}

			if (estadoId!= null) 
			{
				call.setInt(2, estadoId);
			} 
			else 
			{
				call.setNull(2, OracleTypes.NULL);
			}

			call.registerOutParameter(3, OracleTypes.CURSOR);
			call.execute();
			ResultSet rs = (ResultSet) call.getObject(3);
			listaRequerimiento= new ArrayList<RequerimientoDTO>();

			while (rs.next()) {
				requerimientoDTO = new RequerimientoDTO();				

				try 
				{
					requerimientoDTO.setId(rs.getInt(1));
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);				
				}
				
								
				listaRequerimiento.add(requerimientoDTO);
			}
			rs.close();
		}
		catch (NoResultException nre) 
		{
			listaRequerimiento= null;
			log.error(nre.getMessage(), nre);
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		} 
		finally 
		{
			try 
			{
				conn.close();
			}
			catch (Exception e) 
			{
				log.error(e.getMessage(), e);
			}
		}
		return listaRequerimiento;
	}
		
}
