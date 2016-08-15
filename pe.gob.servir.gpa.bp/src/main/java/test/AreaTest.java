package test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.OracleTypes;
import pe.gob.servir.gpa.model.dto.AreaDTO;
import pe.gob.servir.gpa.model.dto.EstadoDTO;

public class AreaTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception 
	{
		
		
			AreaTest a = new AreaTest();
			List<AreaDTO>lista = new ArrayList<AreaDTO>();
			lista = a.buscarArea(null, null);
			for (AreaDTO areaDTO : lista) 
			{
				System.out.println(areaDTO.getId()+"  "+areaDTO.getAreaPadre().getId()+" "+areaDTO.getCodigoArea()+" "+areaDTO.getDescripcionArea());
			}
			
		
	
	
	}
	
	public List<AreaDTO> buscarArea(Integer areaPadre,String descripcionArea) throws Exception 
	{
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SERVIR","123456");		
		List<AreaDTO> listaArea=null;
		AreaDTO areaDTO=null;
		CallableStatement call = null;						
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
				
			}
						
			areaDTO.setAreaPadre(new AreaDTO());
			try 
			{
				areaDTO.getAreaPadre().setId(rs.getInt(2));
			} catch (Exception e) 
			{
				
			}
			
			try 
			{
				areaDTO.setCodigoArea(rs.getString(3));
			} catch (Exception e) 
			{
				
			}
			
			try 
			{
				areaDTO.setDescripcionArea(rs.getString(4));
			} catch (Exception e) 
			{
				
			}
			
			try 
			{
				areaDTO.setAbreviaturaArea(rs.getString(5));
			} catch (Exception e) 
			{
				
			}
			
			try 
			{
				areaDTO.setIndicadorActivo(rs.getShort(6));
			} catch (Exception e) 
			{
				
			}
			
			try 
			{
				areaDTO.setCodigoUsuarioRegistro(rs.getString(7));
			} catch (Exception e) 
			{
				
			}
			
			try 
			{
				areaDTO.setIpRegistro(rs.getString(8));
			} catch (Exception e) 
			{
				
			}
			
			try 
			{
				areaDTO.setFechaRegistro(rs.getDate(9));
			} catch (Exception e) 
			{
				
			}
			
			try 
			{
				areaDTO.setCodigoUsuarioModificacion(rs.getString(10));
			} catch (Exception e) 
			{
				
			}
			
			try 
			{
				areaDTO.setIpModificacion(rs.getString(11));
			} catch (Exception e) 
			{
				
			}
			
			try 
			{
				areaDTO.setFechaModificacion(rs.getDate(12));
			} catch (Exception e) 
			{
				
			}
						
			areaDTO.setEstado(new EstadoDTO());
			try 
			{
				areaDTO.getEstado().setId(rs.getInt(13));
			} catch (Exception e) 
			{
				
			}
								
			listaArea.add(areaDTO);				
		}
		rs.close();	
	
		return listaArea;
	}

}
