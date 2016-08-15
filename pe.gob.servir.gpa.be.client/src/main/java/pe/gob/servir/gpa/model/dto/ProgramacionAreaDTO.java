
 
package pe.gob.servir.gpa.model.dto;

import java.io.Serializable;

import javax.persistence.Embedded;



public class ProgramacionAreaDTO  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 57388475711051765L;

	/**Identificador y clave primaria de la tabla TBL_PROGRAMACION_AREA */
    private Integer identificadorProgramacionArea;

    /**Identificador de la tabla TBL_AREA */
    private AreaDTO area;
    
    /**Identificador de la tabla TBL_PROGRAMACION */
    private ProgramacionDTO programacion;

    /**Indicador de registro activo: 1: Activo - 0: Inactivo.*/    
    private Integer indicadorActivo;
    
    /** Entidad con Datos de Auditoria del Registro*/
    @Embedded
    private AuditoriaDTO auditoria;

    private EstadoDTO estado;
    /**
     * Metodo Constructor
     */
    public ProgramacionAreaDTO(){
    }
	/** Metodo que permite obtener identificadorProgramacionArea
	 * @param identificadorProgramacionArea, tipo Integer
	 */
	public Integer getIdentificadorProgramacionArea() {
		return identificadorProgramacionArea;
	}
	/**
	 * Permite actualizar identificadorProgramacionArea
	 * @param identificadorProgramacionArea, tipo Integer
	 */
	public void setIdentificadorProgramacionArea(
			Integer identificadorProgramacionArea) {
		this.identificadorProgramacionArea = identificadorProgramacionArea;
	}
	/** Metodo que permite obtener area
	 * @param area, tipo AreaDTO
	 */
	public AreaDTO getArea() {
		return area;
	}
	/**
	 * Permite actualizar area
	 * @param area, tipo AreaDTO
	 */
	public void setArea(AreaDTO area) {
		this.area = area;
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
    
	
}
