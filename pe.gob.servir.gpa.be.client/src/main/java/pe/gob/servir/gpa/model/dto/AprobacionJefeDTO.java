package pe.gob.servir.gpa.model.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embedded;

public class AprobacionJefeDTO implements Serializable {

	private static final long serialVersionUID = 795552688555036658L;

	// Identificador de la tabla TBL_APROBACION
	private Integer identificadorAprobacion;

	// Identificador de la tabla TBL_REQUERIMIENTO
	private RequerimientoAreaDTO requerimientoArea;
	
	private TipoDTO tipoRequerimiento;

	// Identificador y clave primaria TBL_USUARIO.
	private UsuarioDTO usuario;

	//
	private Integer indicadorAprobado;

	// Indicador de registro activo: 1: Activo - 0: Inactivo.
	private Integer indicadorActivo;

	// Entidad con Datos de Auditoria del Registro
	@Embedded
	private AuditoriaDTO auditoria;

	// Identificador de la tabla TBL_ESTADO
	private EstadoDTO estado;
	
	//Fecha en la cuál se ha aprobado el requerimiento
	private Date fechaAtendido;
	
	private Boolean isAprobar;

	



	/** Metodo que permite obtener identificadorAprobacion
	 * @param identificadorAprobacion, tipo Integer
	 */
	public Integer getIdentificadorAprobacion() {
		return identificadorAprobacion;
	}

	/**
	 * Permite actualizar identificadorAprobacion
	 * @param identificadorAprobacion, tipo Integer
	 */
	public void setIdentificadorAprobacion(Integer identificadorAprobacion) {
		this.identificadorAprobacion = identificadorAprobacion;
	}

	/** Metodo que permite obtener requerimientoArea
	 * @param requerimientoArea, tipo RequerimientoAreaDTO
	 */
	public RequerimientoAreaDTO getRequerimientoArea() {
		return requerimientoArea;
	}

	/**
	 * Permite actualizar requerimientoArea
	 * @param requerimientoArea, tipo RequerimientoAreaDTO
	 */
	public void setRequerimientoArea(RequerimientoAreaDTO requerimientoArea) {
		this.requerimientoArea = requerimientoArea;
	}

	/** Metodo que permite obtener usuario
	 * @param usuario, tipo UsuarioDTO
	 */
	public UsuarioDTO getUsuario() {
		return usuario;
	}

	/**
	 * Permite actualizar usuario
	 * @param usuario, tipo UsuarioDTO
	 */
	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	/** Metodo que permite obtener indicadorAprobado
	 * @param indicadorAprobado, tipo Integer
	 */
	public Integer getIndicadorAprobado() {
		return indicadorAprobado;
	}

	/**
	 * Permite actualizar indicadorAprobado
	 * @param indicadorAprobado, tipo Integer
	 */
	public void setIndicadorAprobado(Integer indicadorAprobado) {
		this.indicadorAprobado = indicadorAprobado;
	}


	/** Metodo que permite obtener indicadorActivo
	 * @param indicadorActivo, tipo Integer
	 */
	public Integer getIndicadorActivo() {
		return indicadorActivo;
	}

	/**
	 * Permite actualizar indicadorActivo
	 * @param indicadorActivo, tipo Integer
	 */
	public void setIndicadorActivo(Integer indicadorActivo) {
		this.indicadorActivo = indicadorActivo;
	}

	/** Metodo que permite obtener auditoria
	 * @param auditoria, tipo AuditoriaDTO
	 */
	public AuditoriaDTO getAuditoria() {
		return auditoria;
	}

	/**
	 * Permite actualizar auditoria
	 * @param auditoria, tipo AuditoriaDTO
	 */
	public void setAuditoria(AuditoriaDTO auditoria) {
		this.auditoria = auditoria;
	}

	/** Metodo que permite obtener estado
	 * @param estado, tipo EstadoDTO
	 */
	public EstadoDTO getEstado() {
		return estado;
	}

	/**
	 * Permite actualizar estado
	 * @param estado, tipo EstadoDTO
	 */
	public void setEstado(EstadoDTO estado) {
		this.estado = estado;
	}

	/** Metodo que permite obtener fechaAtendido
	 * @param fechaAtendido, tipo Date
	 */
	public Date getFechaAtendido() {
		return fechaAtendido;
	}

	/**
	 * Permite actualizar fechaAtendido
	 * @param fechaAtendido, tipo Date
	 */
	public void setFechaAtendido(Date fechaAtendido) {
		this.fechaAtendido = fechaAtendido;
	}

	/** Metodo que permite obtener isAprobar
	 * @param isAprobar, tipo Boolean
	 */
	public Boolean getIsAprobar() {
		return isAprobar;
	}

	/**
	 * Permite actualizar isAprobar
	 * @param isAprobar, tipo Boolean
	 */
	public void setIsAprobar(Boolean isAprobar) {
		this.isAprobar = isAprobar;
	}

	/** Metodo que permite obtener tipoRequerimiento
	 * @param tipoRequerimiento, tipo TipoDTO
	 */
	public TipoDTO getTipoRequerimiento() {
		return tipoRequerimiento;
	}

	/**
	 * Permite actualizar tipoRequerimiento
	 * @param tipoRequerimiento, tipo TipoDTO
	 */
	public void setTipoRequerimiento(TipoDTO tipoRequerimiento) {
		this.tipoRequerimiento = tipoRequerimiento;
	}

	
}
