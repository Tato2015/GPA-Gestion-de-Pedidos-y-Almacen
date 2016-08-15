package pe.gob.servir.gpa.bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.NamingException;


public class Test3DArray 
{
	public Connection conexionDataSource() throws NamingException, SQLException
	{					
		try 
		{						
			 Class.forName ("oracle.jdbc.driver.OracleDriver");
		     Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","SERVIR", "123456");
		     return conn;
		}
		catch (Exception e) 
		{
		System.out.println("Error : "+e.getMessage());
		return null;
		}		
			
	}
	
	public static void main (String[]args) throws NamingException, SQLException
	{
		Test3DArray t = new Test3DArray(); 
		if(t.conexionDataSource()!=null)
		{
			System.out.println("Se conectó");
		}
		else
		{
			System.out.println("ERROR DE CONEXION");
		}
		
		
//		ArrayList<ArrayList<ArrayList<Integer>>> data3D = new ArrayList<ArrayList<ArrayList<Integer>>>();
////		int [][][]arreglo3D = new int[2][5][1];
//		
//		for(int i=0;i<2;i++)
//		{
//			int a=0;
//			data3D.add(new ArrayList<ArrayList<Integer>>());			
//			for(int j=0;j<5;j++)
//			{
//				data3D.get(i).add(new ArrayList<Integer>());	
//				data3D.get(i).get(j).add(i+j);
//				System.out.print(data3D.get(i).get(j).get(0));
//				a =a+ data3D.get(i).get(j).get(0);			
//			}
//			
//			System.out.println(a+10);
//			System.out.println();
//		}
//		
////		for(int i=0;i<2;i++)
////		{					
////			for(int j=0;j<5;j++)
////			{
////				arreglo3D[i][j][0]=i+j;
////				System.out.print(i+"+"+j+" = "+arreglo3D[i][j][0]+"   ");
////				
////			}
////			System.out.println();
////		}
//
//
//		
//	}
		
//		public static Integer getAnioSistema() {
			
//			return c.get(Calendar);
//		}
		int[][] arregloCantidadProductos= new int[1][3];
		
		for(int fila = 0;fila<1;fila++)
		{
			for(int columna=0;columna<3;columna++)
			{
				arregloCantidadProductos[fila][columna] = fila+columna;
				System.out.print(arregloCantidadProductos[fila][columna]+"  ");
			}
		}
		
		
		
		
		
	}
	
	
	
	
}
