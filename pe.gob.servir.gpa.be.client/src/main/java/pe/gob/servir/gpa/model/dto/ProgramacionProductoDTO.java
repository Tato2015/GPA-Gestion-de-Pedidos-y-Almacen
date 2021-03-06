
 
package pe.gob.servir.gpa.model.dto;

import java.io.Serializable;

import javax.persistence.Embedded;



public class ProgramacionProductoDTO  implements Serializable{


    /**
	 * 
	 */
	private static final long serialVersionUID = -7656796198294042195L;

	
	
	/**Identificador y clave primaria de la tabla TBL_PROGRAMACION_PRODUCTO */
    private Integer identificadorProgramacionProducto;
   
    /**Identificador de la tabla TBL_PROGRAMACION */
    private ProgramacionDTO programacion;

    /**Identificador de la tabla TBL_PRODUCTO */
    private ProductoDTO producto;
    
    /**Indicador de registro activo: 1: Activo - 0: Inactivo.*/    
    private Integer indicadorActivo;
    
    /** Entidad con Datos de Auditoria del Registro*/
    @Embedded
    private AuditoriaDTO auditoria;

    private EstadoDTO estado;
    /**
     * Metodo Constructor
     */
    public ProgramacionProductoDTO(){
    }
	/** Metodo que permite obtener identificadorProgramacionProducto
	 * @param identificadorProgramacionProducto, tipo Integer
	 */
	public Integer getIdentificadorProgramacionProducto() {
		return identificadorProgramacionProducto;
	}
	/**
	 * Permite actualizar identificadorProgramacionProducto
	 * @param identificadorProgramacionProducto, tipo Integer
	 */
	public void setIdentificadorProgramacionProducto(
			Integer identificadorProgramacionProducto) {
		this.identificadorProgramacionProducto = identificadorProgramacionProducto;
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
	/** Metodo que permite obtener producto
	 * @param producto, tipo ProductoDTO
	 */
	public ProductoDTO getProducto() {
		return producto;
	}
	/**
	 * Permite actualizar producto
	 * @param producto, tipo ProductoDTO
	 */
	public void setProducto(ProductoDTO producto) {
		this.producto = producto;
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
