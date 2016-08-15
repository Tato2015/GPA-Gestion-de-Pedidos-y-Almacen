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

import pe.gob.servir.gpa.adm.dao.PerfilDAOLocal;
import pe.gob.servir.gpa.model.dto.EstadoDTO;
import pe.gob.servir.gpa.model.dto.PerfilDTO;
import pe.gob.servir.gpa.util.ConstanteEJB;

@Stateless
public class PerfilDAOImpl implements PerfilDAOLocal {

	/** Metodo Constructor */
	public PerfilDAOImpl() 
	{
	}

	ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);

	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(PerfilDAOImpl.class);

	public List<PerfilDTO> buscarPerfil(Integer perfilId,
			Integer estadoId) throws Exception 
	{
		List<PerfilDTO> listaPerfil= null;
		PerfilDTO perfilDTO = null;		
		Connection conn = null;
		CallableStatement call = null;

		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(propGPA
					.getString(ConstanteEJB.CONEXION_SERVIR));

			conn = ds.getConnection();

			String sql = "begin PKG_PERFILES.SP_BUSCAR_PERFILES(?,?,?);end;";
			call = conn.prepareCall(sql);

			if(perfilId != null && perfilId>=0)
			{
				call.setInt(1,perfilId);
			}
			else
			{
				call.setNull(1,OracleTypes.NULL);
			}

			if(estadoId !=null && estadoId>=0)
			{
				call.setInt(2,estadoId);
			}
			else
			{
				call.setNull(2,OracleTypes.NULL);
			}
			
			call.registerOutParameter(3, OracleTypes.CURSOR);
			call.execute();
			ResultSet rs = (ResultSet) call.getObject(3);
			listaPerfil= new ArrayList<PerfilDTO>();

			while (rs.next()) {
				perfilDTO = new PerfilDTO();				

				try {
					perfilDTO.setId(rs.getInt(1));
					
				} 
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getInt(1));
				}
											
				try 
				{
					perfilDTO.setDescripcionPerfil(rs.getString(2));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getInt(2));
				}
				
				try 
				{
					call.setShort(3,perfilDTO.getIndicadorActivo());
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getInt(3));
				}
							
				try {
					perfilDTO.setCodigoUsuarioRegistro(rs.getString(4));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
					System.out.println(rs.getString(4));
				}

				try 
				{
					perfilDTO.setIpRegistro(rs.getString(5));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}

				try
				{
					perfilDTO.setFechaRegistro(rs.getDate(6));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}

				try 
				{
					perfilDTO.setCodigoUsuarioModificacion(rs.getString(7));
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}

				try 
				{
					perfilDTO.setIpModificacion(rs.getString(8));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);

				}

				try 
				{
					perfilDTO.setFechaModificacion(rs.getDate(9));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);

				}

				perfilDTO.setEstado(new EstadoDTO());

				try 
				{
					perfilDTO.getEstado().setId(rs.getInt(10));
				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);
				}

				try 
				{
					perfilDTO.getEstado().setDescripcionEstado(rs.getString(11));

				}
				catch (Exception e) 
				{
					log.error(e.getMessage(), e);

				}
						
				listaPerfil.add(perfilDTO);
			}
			rs.close();
		}
		catch (NoResultException nre) 
		{
			listaPerfil= null;
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

		return listaPerfil;
	}

	
	
	

}
