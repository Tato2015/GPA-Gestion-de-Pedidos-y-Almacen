/**
 * <ul>
 * <li>Copyright 2015 Organismo Supervisor de las Contrataciones del Estado -
 * OSCE. Todos los derechos reservados.</li>
 * </ul>
 * Objeto: AuditoriaDTO.java
 * Descripcion: Clase que transporta datos de la Entidad AuditoriaDTO hacia la Capa de presentacion
 * @Autor 		            : Consultora  - P & P BMS (P & P Business Management Solutions)
 * @version                 : 1.0
 * @sinse 					: 2015
 * ----------------------------------------------------------------------------
 * Modificaciones
 * Codigo	Fecha		Nombre			Descripcion
 * ----------------------------------------------------------------------------
 * 
 */

package pe.gob.servir.gpa.model.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;

@Embeddable
public class AuditoriaDTO implements Serializable {

	/**
	 * Atributo que debe ser distinto para cada versión compilada que tengamos
	 * de esta clase
	 */
	private static final long serialVersionUID = 2144486805438611161L;

	/** Identificador del Usuario que realizo el registro*/
	private String usuarioRegistro;

	/** Numero de IP del ordenador de registro. */
	private String descripcionIpRegistro;
	
	/** Fecha en que se realizo el registro.*/
	private Date fechaRegistro;	

	/** Identificador del Usuario que modifico el registro.*/
	private String usuarioModificacion;
	
	/** Numero de IP del ordenador de modificacion. */
	private String descripcionIpModificacion;
	
	/** Fecha en que se modifico el registro.*/
	private Date fechaModificacion;

	/**
	 * Metodo que permite obtener usuarioRegistro.
	 * @param usuarioRegistro, tipo String
	 */
	public String getUsuarioRegistro() {
		return usuarioRegistro;
	}

	/**
	 * Permite actualizar usuarioRegistro.
	 * @param usuarioRegistro, tipo String
	 */
	public void setUsuarioRegistro(String usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}

	/**
	 * Metodo que permite obtener descripcionIpRegistro.
	 * @param descripcionIpRegistro, tipo String
	 */
	public String getDescripcionIpRegistro() {
		return descripcionIpRegistro;
	}

	/**
	 * Permite actualizar descripcionIpRegistro.
	 * @param descripcionIpRegistro, tipo String
	 */
	public void setDescripcionIpRegistro(String descripcionIpRegistro) {
		this.descripcionIpRegistro = descripcionIpRegistro;
	}

	/**
	 * Metodo que permite obtener fechaRegistro.
	 * @param fechaRegistro, tipo Date
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * Permite actualizar fechaRegistro.
	 * @param fechaRegistro, tipo Date
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * Metodo que permite obtener usuarioModificacion.
	 * @param usuarioModificacion, tipo String
	 */
	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}

	/**
	 * Permite actualizar usuarioModificacion.
	 * @param usuarioModificacion, tipo String
	 */
	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	/**
	 * Metodo que permite obtener descripcionIpModificacion.
	 * @param descripcionIpModificacion, tipo String
	 */
	public String getDescripcionIpModificacion() {
		return descripcionIpModificacion;
	}

	/**
	 * Permite actualizar descripcionIpModificacion.
	 * @param descripcionIpModificacion, tipo String
	 */
	public void setDescripcionIpModificacion(String descripcionIpModificacion) {
		this.descripcionIpModificacion = descripcionIpModificacion;
	}

	/**
	 * Metodo que permite obtener fechaModificacion.
	 * @param fechaModificacion, tipo Date
	 */
	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	/**
	 * Permite actualizar fechaModificacion.
	 * @param fechaModificacion, tipo Date
	 */
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}	

}
