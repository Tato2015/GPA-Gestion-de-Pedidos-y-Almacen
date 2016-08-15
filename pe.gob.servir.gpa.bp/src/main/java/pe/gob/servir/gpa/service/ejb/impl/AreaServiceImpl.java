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

package pe.gob.servir.gpa.service.ejb.impl;


import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import pe.gob.servir.gpa.adm.dao.AreaDAOLocal;
import pe.gob.servir.gpa.model.dto.AreaDTO;
import pe.gob.servir.gpa.service.ejb.AreaServiceLocal;
import pe.gob.servir.gpa.util.ConstanteEJB;


@Stateless
public class AreaServiceImpl implements AreaServiceLocal{

	/** Metodo Constructor 	 */
	public AreaServiceImpl() {}
	
	@EJB
	private AreaDAOLocal areaDAOLocal;
	
	

	
	 /** Atributo del tipo properties, donde se almacenas propiedades de la aplicacion*/
		ResourceBundle propGPA = ResourceBundle.getBundle(ConstanteEJB.PROPERTIES);
	
	/** Atributo que registra en el log del servidor */
	final static Logger log = Logger.getLogger(AreaServiceImpl.class);

	public List<AreaDTO> buscarArea(Integer areaPadre,String descripcionArea) throws Exception 
	{
		List<AreaDTO> listaArea=null;
		
		try 
		{
			listaArea=areaDAOLocal.buscarArea(areaPadre,descripcionArea);
				
		} catch (Exception e) 
		{
			log.error(e.getMessage(), e);
		}
		return listaArea;
	}

	
//	
//	public AreaDTO obtenerArea(Integer identificadorArea) throws Exception {
//		AreaDTO area=null;
//		try {
//			area=areaDAOLocal.obtenerArea(identificadorArea);
//			
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//		}
//		return area;
//	}
//	
//	

}
