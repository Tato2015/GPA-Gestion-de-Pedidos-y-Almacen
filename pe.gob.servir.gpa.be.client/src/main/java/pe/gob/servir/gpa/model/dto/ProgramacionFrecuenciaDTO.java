package pe.gob.servir.gpa.model.dto;

import java.io.Serializable;


public class ProgramacionFrecuenciaDTO  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8733215679284714787L;

	/**Identificador y clave primaria de la tabla TBL_PROGRAMACION_PRODUCTO */
	 private Long idProgramacionFrecuencia;
	 
	 /**año de la programacion */
	private ProgramacionDTO programacion;
	
	/**mes de programacion */
	private Integer anioProgramado;
	
	private Integer mesProgramado;


	/**indicador Activo*/	
	private Integer indicadorActivo;
	
	/**Identificador de la tabla TBL_ESTADO */
	private EstadoDTO estado;
	
	/** Entidad con Datos de Auditoria del Registro*/
	private AuditoriaDTO auditoria;
	
	/**
	 * Metodo Constructor
	 */
	public ProgramacionFrecuenciaDTO() {}

	/** Metodo que permite obtener idProgramacionFrecuencia
	 * @param idProgramacionFrecuencia, tipo Long
	 */
	public Long getIdProgramacionFrecuencia() {
		return idProgramacionFrecuencia;
	}

	/**
	 * Permite actualizar idProgramacionFrecuencia
	 * @param idProgramacionFrecuencia, tipo Long
	 */
	public void setIdProgramacionFrecuencia(Long idProgramacionFrecuencia) {
		this.idProgramacionFrecuencia = idProgramacionFrecuencia;
	}

	/** Metodo que permite obtener programacion
	 * @param programacion, tipo ProgramacionDTO
	 */
	public ProgramacionDTO getProgramacion() {
		return programacion;
	}

	/**
	 * Permite actualizar programacion
	 * @param programacion, tipo ProgramacionDTO
	 */
	public void setProgramacion(ProgramacionDTO programacion) {
		this.programacion = programacion;
	}

	/** Metodo que permite obtener anioProgramado
	 * @param anioProgramado, tipo Integer
	 */
	public Integer getAnioProgramado() {
		return anioProgramado;
	}

	/**
	 * Permite actualizar anioProgramado
	 * @param anioProgramado, tipo Integer
	 */
	public void setAnioProgramado(Integer anioProgramado) {
		this.anioProgramado = anioProgramado;
	}

	/** Metodo que permite obtener mesProgramado
	 * @param mesProgramado, tipo Integer
	 */
	public Integer getMesProgramado() {
		return mesProgramado;
	}

	/**
	 * Permite actualizar mesProgramado
	 * @param mesProgramado, tipo Integer
	 */
	public void setMesProgramado(Integer mesProgramado) {
		this.mesProgramado = mesProgramado;
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
