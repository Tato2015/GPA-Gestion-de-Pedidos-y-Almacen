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

import pe.gob.servir.gpa.adm.dao.PersonaDAOLocal;
import pe.gob.servir.gpa.model.dto.AreaDTO;
import pe.gob.servir.gpa.model.dto.EstadoDTO;
import pe.gob.servir.gpa.model.dto.PersonaDTO;
import pe.gob.servir.gpa.model.dto.TipoDTO;
import pe.gob.servir.gpa.model.dto.UbigeoDTO;
import pe.gob.servir.gpa.util.ConstanteEJB;

@Stateless
public class PersonaDAOImpl implements PersonaDAOLocal 
{

	public PersonaDAOImpl() {
	}

	ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);
	final static Logger log = Logger.getLogger(PersonaDAOImpl.class);

	public List<PersonaDTO> buscarPersona(Integer padreId,String apellidoPaterno,String apellidoMaterno,Integer estadoId) throws Exception 
	{
		List<PersonaDTO> listaPersona= null;
		PersonaDTO personaDTO = null;		
		Connection conn = null;
		CallableStatement call = null;
		try 
		{
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup(propGPA
					.getString(ConstanteEJB.CONEXION_SERVIR));

			conn = ds.getConnection();

			String sql = "begin PKG_PERSONA.SP_BUSCAR_PERSONA(?,?,?,?,?);end;";
			call = conn.prepareCall(sql);

			if(padreId != null && padreId>=0)
			{
				call.setInt(1, padreId);
			}
			else
			{
				call.setNull(1,OracleTypes.NULL);
			}
			
			if(apellidoPaterno!=null && !apellidoPaterno.equals(""))
			{
				call.setString(2, apellidoPaterno);
			}
			else
			{
				call.setNull(2,OracleTypes.NULL);
			}
			
			if(apellidoMaterno !=null && !apellidoMaterno.equals(""))
			{
				call.setString(3,apellidoMaterno);
			}
			else
			{
				call.setNull(3,OracleTypes.NULL);
			}
			
			if(estadoId != null && estadoId>=0)
			{
				call.setInt(4, estadoId);				
			}
			else
			{
				call.setNull(4, OracleTypes.NULL);
			}
			
			call.registerOutParameter(5, OracleTypes.CURSOR);
			call.execute();
			ResultSet rs = (ResultSet) call.getObject(5);
			listaPersona = new ArrayList<PersonaDTO>();
			while (rs.next()) 
			{
			personaDTO = new PersonaDTO();
			
			
				try 
				{
				personaDTO.setId(rs.getInt(1));
					
				} 
				catch (Exception e) 
				{
				log.error(e.getMessage(),e);
				System.out.println(rs.getInt(1));
				}
				
				
				try 
				{
				personaDTO.setApellidoPaterno(rs.getString(2));	
				}
				catch (Exception e) 
				{
				log.error(e.getMessage(),e);
				System.out.println(rs.getString(2));
				}
				
				
				try 
				{
				personaDTO.setApellidoMaterno(rs.getString(3));	
				}
				catch (Exception e) 
				{
				log.error(e.getMessage(),e);
				System.out.println(rs.getString(3));
				}
	
				
				try 
				{
				personaDTO.setNombres(rs.getString(4));	
				}
				catch (Exception e) 
				{
				log.error(e.getMessage(),e);
				System.out.println(rs.getString(4));
				}
				
				
				personaDTO.setArea(new AreaDTO());
				try 
				{
				personaDTO.getArea().setId(rs.getInt(5));
				}
				catch (Exception e) 
				{
				log.error(e.getMessage(),e);
				System.out.println(rs.getInt(5));
				}
				
				personaDTO.setTipoGenero(new TipoDTO());
				try 
				{
				personaDTO.getTipoGenero().setId(6);	
				}
				catch (Exception e) 
				{
				log.error(e.getMessage(),e);
				System.out.println(rs.getInt(6));
				}
				
				try 
				{
				personaDTO.setFechaNacimiento(rs.getDate(7));	
				}
				catch (Exception e) 
				{
				log.error(e.getMessage(),e);
				System.out.println(rs.getDate(7));
				}
				
				personaDTO.setTipoDocumento(new TipoDTO());
				try 
				{
				personaDTO.getTipoDocumento().setId(8);	
				}
				catch (Exception e) 
				{
				log.error(e.getMessage(),e);
				System.out.println(rs.getInt(8));				
				}
				
				try 
				{
				personaDTO.setNumeroDocumentoIdentidad(rs.getString(9));	
				}
				catch (Exception e) 
				{
				log.error(e.getMessage(),e);
				System.out.println(rs.getString(9));
				}
				
				
				try 
				{
				personaDTO.setEmailPrincipal(rs.getString(10));	
				}
				catch (Exception e) 
				{
				log.error(e.getMessage(),e);
				System.out.println(rs.getString(10));
				}
				
				try 
				{
				personaDTO.setEmailSecundario(rs.getString(11));						
				}
				catch (Exception e) 
				{
				log.error(e.getMessage(),e);
				System.out.println(rs.getString(11));
				}
				
				try 
				{
				personaDTO.setTelefono(rs.getString(12));	
				}
				catch (Exception e) 
				{
				log.error(e.getMessage(),e);
				System.out.println(rs.getString(12));
				}
				
				try 
				{
				personaDTO.setCelular(rs.getString(13));	
				}
				catch (Exception e) 
				{
				log.error(e.getMessage(),e);
				System.out.println(rs.getString(13));
				}
				
				
				personaDTO.setUbigeo(new UbigeoDTO());
				try 
				{
				personaDTO.getUbigeo().setId(rs.getInt(14));	
				}
				catch (Exception e) 
				{
				log.error(e.getMessage(),e);
				System.out.println(rs.getInt(14));
				}
				
				
				try 
				{
				personaDTO.setDireccion(rs.getString(15));
				}
				catch (Exception e) 
				{
				log.error(e.getMessage(),e);
				System.out.println(rs.getString(15));
				}
								
				try 
				{
				personaDTO.setDireccionReferencia(rs.getString(16));	
				}
				catch (Exception e) 
				{
				log.error(e.getMessage(),e);
				System.out.println(rs.getString(16));
				}
								
				
				personaDTO.setTipoProfesion(new TipoDTO());
				try 
				{
				personaDTO.getTipoProfesion().setId(17);
				}
				catch (Exception e) 
				{
				log.error(e.getMessage(),e);
				System.out.println(rs.getInt(17));
				}
				
				
				try 
				{
				personaDTO.setDescripcionOtraProfesion(rs.getString(18));	
				}
				catch (Exception e) 
				{
				log.error(e.getMessage(),e);
				System.out.println(rs.getString(18));
				}
				
				
				try 
				{
				personaDTO.setIndicadorActivo(rs.getShort(19));	
				}
				catch (Exception e) 
				{
				log.error(e.getMessage(),e);
				System.out.println(rs.getShort(19));
				}
				
				
				try 
				{
				personaDTO.setCodigoUsuarioRegistro(rs.getString(20));	
				}
				catch (Exception e) 
				{
				log.error(e.getMessage(),e);
				System.out.println(rs.getString(20));
				}
				
				
				try 
				{
				personaDTO.setIpRegistro(rs.getString(21));	
				}
				catch (Exception e) 
				{
				log.error(e.getMessage(),e);
				System.out.println(rs.getString(21));
				}
				
				try 
				{
				personaDTO.setFechaRegistro(rs.getDate(22));	
				}
				catch (Exception e) 
				{
				log.error(e.getMessage(),e);
				System.out.println(rs.getDate(22));
				}
				
				try 
				{
				personaDTO.setCodigoUsuarioModificacion(rs.getString(23));	
				}
				catch (Exception e) 
				{
				log.error(e.getMessage(),e);
				System.out.println(rs.getString(23));
				}
				
				try 
				{
				personaDTO.setIpModificacion(rs.getString(24));	
				}
				catch (Exception e) 
				{
				log.error(e.getMessage(),e);
				System.out.println(rs.getString(24));
				}
				
				
				try 
				{
				personaDTO.setFechaModificacion(rs.getDate(25));	
				}
				catch (Exception e) 
				{
				log.error(e.getMessage(),e);
				System.out.println(rs.getDate(25));
				}
				
				personaDTO.setEstado(new EstadoDTO());
				try 
				{
				personaDTO.getEstado().setId(rs.getInt(26));	
				}
				catch (Exception e) 
				{
				log.error(e.getMessage(),e);
				System.out.println(rs.getInt(2));
				}				
				listaPersona.add(personaDTO);
			}								
			rs.close();
		}
		catch (NoResultException nre) 
		{
			listaPersona = null;
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

		return listaPersona;

	}

		
}
