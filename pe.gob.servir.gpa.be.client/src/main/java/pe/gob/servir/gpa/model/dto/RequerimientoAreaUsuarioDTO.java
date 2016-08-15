package pe.gob.servir.gpa.model.dto;

import java.io.Serializable;

import javax.persistence.Embedded;

public class RequerimientoAreaUsuarioDTO implements Serializable {


	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4961927231651974185L;
	
	private Long idRequerimientoAreaUsuario;
	private	RequerimientoUsuarioDTO requerimientoUsuario;
	private	RequerimientoAreaDTO requerimientoArea;
	private Integer indicadorActivo;
	private EstadoDTO estado;
	
	/** Clase auditoria*/
	@Embedded
	private AuditoriaDTO auditoria;
	
	public RequerimientoAreaUsuarioDTO() {}

	/** Metodo que permite obtener idRequerimientoAreaUsuario
	 * @param idRequerimientoAreaUsuario, tipo Long
	 */
	public Long getIdRequerimientoAreaUsuario() {
		return idRequerimientoAreaUsuario;
	}

	/**
	 * Permite actualizar idRequerimientoAreaUsuario
	 * @param idRequerimientoAreaUsuario, tipo Long
	 */
	public void setIdRequerimientoAreaUsuario(Long idRequerimientoAreaUsuario) {
		this.idRequerimientoAreaUsuario = idRequerimientoAreaUsuario;
	}

	/** Metodo que permite obtener requerimientoUsuario
	 * @param requerimientoUsuario, tipo RequerimientoUsuarioDTO
	 */
	public RequerimientoUsuarioDTO getRequerimientoUsuario() {
		return requerimientoUsuario;
	}

	/**
	 * Permite actualizar requerimientoUsuario
	 * @param requerimientoUsuario, tipo RequerimientoUsuarioDTO
	 */
	public void setRequerimientoUsuario(RequerimientoUsuarioDTO requerimientoUsuario) {
		this.requerimientoUsuario = requerimientoUsuario;
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

	
}
