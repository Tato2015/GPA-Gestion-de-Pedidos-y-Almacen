/**
 * <ul>
 * <li>Copyright 2015 Organismo Supervisor de las Contrataciones del Estado -
 * OSCE. Todos los derechos reservados.</li> 
 * </ul>
 * Objeto: SistemaContextBean.java
 * Descripción: Clase de declaraciones de variables estaticos Finales
 * Fecha de Creación: 14/08/2015
 * ----------------------------------------------------------------------------
 * Modificaciones
 * Código	Fecha		Nombre			Descripción
 * ----------------------------------------------------------------------------
 * 
 */

package pe.gob.servir.gpa.bean;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import pe.gob.servir.gpa.util.UBaseManage;

@ManagedBean(name = "sistemaContextBean")
@ApplicationScoped
public class SistemaContextBean extends UBaseManage implements Serializable {

	private static final long serialVersionUID = 5573219202769647536L;

	public static final String VISTA_PERSONA = "VistaPersona";
	public static final String VISTA_USUARIO = "VistaAdministrarUsuario";
	public static final String VISTA_REQUERIMIENTO_USUARIO_S = "VistaUsuarioSuministro";
	public static final String VISTA_REQUERIMIENTO_USUARIO_E = "VistaUsuarioExtraordinario";
	public static final String VISTA_REQUERIMIENTO_AREA_C = "VistaAreaComestible";
	public static final String VISTA_REQUERIMIENTO_AREA_S = "VistaAreaSuministro";
	public static final String VISTA_PRODUCTO = "VistaProducto";
	public static final String VISTA_TIPO = "VistaTipo";
	public static final String VISTA_PROGRAMACION = "VistaProgramacion";
	public static final String VISTA_APROBACION_JEFE = "VistaAprobacionJefe";
	public static final String VISTA_PECOSA = "VistaPecosa";
	public static final String VISTA_CONSULTA_REQUERIMIENTO = "VistaConsultaRequerimiento";
	public static final String VISTA_GESTION_ALMACEN= "VistaGestionAlmacen";
	public static final String VISTA_ALERTAS= "VistaAlertas";
	public static final String VISTA_REPORTES= "VistaReportes";
	public static final String VISTA_CONSULTA_REQUERIMIENTOS_USUARIO= "VistaConsultaRequerimientosUsuario";

	
	
	public static String getVistaTipo() {
		return VISTA_TIPO;
	}


	public static String VistaConsultaRequerimientosUsuario() {
		return VISTA_CONSULTA_REQUERIMIENTOS_USUARIO;
}
	
	
	public static String VistaReportes() {
		return VISTA_REPORTES;
	}
	
	
	public static String VistaAlertas() {
		return VISTA_ALERTAS;
	}
	
	
	
	public static String VistaGestionAlmacen() {
		return VISTA_GESTION_ALMACEN;
	}

	
	public static String VistaConsultaRequerimiento() {
		return VISTA_CONSULTA_REQUERIMIENTO;
	}
	
	public static String VistaPecosa() {
		return VISTA_PECOSA;
	}
	
	public static String VistaPersona() {
		return VISTA_PERSONA;
	}

	public static String VistaAdministrarUsuario() {
		return VISTA_USUARIO;
	}

	public static String VistaUsuarioSuministro() {
		return VISTA_REQUERIMIENTO_USUARIO_S;
	}
	public static String VistaAprobacionJefe() {
		return VISTA_APROBACION_JEFE;
	}
	
	
	public static String VistaUsuarioExtraordinario() {
		return VISTA_REQUERIMIENTO_USUARIO_E;
	}
	
	public static String VistaAreaSuministro() {
		return VISTA_REQUERIMIENTO_AREA_S;
	}

	public static String VistaAreaComestible() {
		return VISTA_REQUERIMIENTO_AREA_C;
	}
	
	public static String VistaProgramacion() {
		return VISTA_PROGRAMACION;
	}

	public static String VistaProducto() {
		return VISTA_PRODUCTO;
	}
	public static String VistaTipo() {
		return VISTA_TIPO;
	}

	public SistemaContextBean() {

	}

}
