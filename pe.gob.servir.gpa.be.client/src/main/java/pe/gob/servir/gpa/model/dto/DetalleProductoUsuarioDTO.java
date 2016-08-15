package pe.gob.servir.gpa.model.dto;

import java.io.Serializable;

import javax.persistence.Embedded;

public class DetalleProductoUsuarioDTO implements Serializable {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7180339156751985640L;
	
	private Long identificadorDetalleProducto;
	private ProgramacionFrecuenciaDTO programacionFrecuencia;
	private	DetalleRequerimientoUsuarioDTO detalleRequerimientoUsuario;
	private ProductoDTO producto;
	private Double cantidad;
	private Integer indicadorActivo;
	private EstadoDTO estado;
	/** Clase auditoria*/
	@Embedded
	private AuditoriaDTO auditoria;
	
	public DetalleProductoUsuarioDTO() {}

	/** Metodo que permite obtener identificadorDetalleProducto
	 * @param identificadorDetalleProducto, tipo Long
	 */
	public Long getIdentificadorDetalleProducto() {
		return identificadorDetalleProducto;
	}

	/**
	 * Permite actualizar identificadorDetalleProducto
	 * @param identificadorDetalleProducto, tipo Long
	 */
	public void setIdentificadorDetalleProducto(Long identificadorDetalleProducto) {
		this.identificadorDetalleProducto = identificadorDetalleProducto;
	}

	/** Metodo que permite obtener programacionFrecuencia
	 * @param programacionFrecuencia, tipo ProgramacionFrecuenciaDTO
	 */
	public ProgramacionFrecuenciaDTO getProgramacionFrecuencia() {
		return programacionFrecuencia;
	}

	/**
	 * Permite actualizar programacionFrecuencia
	 * @param programacionFrecuencia, tipo ProgramacionFrecuenciaDTO
	 */
	public void setProgramacionFrecuencia(
			ProgramacionFrecuenciaDTO programacionFrecuencia) {
		this.programacionFrecuencia = programacionFrecuencia;
	}

	/** Metodo que permite obtener detalleRequerimientoUsuario
	 * @param detalleRequerimientoUsuario, tipo DetalleRequerimientoUsuarioDTO
	 */
	public DetalleRequerimientoUsuarioDTO getDetalleRequerimientoUsuario() {
		return detalleRequerimientoUsuario;
	}

	/**
	 * Permite actualizar detalleRequerimientoUsuario
	 * @param detalleRequerimientoUsuario, tipo DetalleRequerimientoUsuarioDTO
	 */
	public void setDetalleRequerimientoUsuario(
			DetalleRequerimientoUsuarioDTO detalleRequerimientoUsuario) {
		this.detalleRequerimientoUsuario = detalleRequerimientoUsuario;
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

	/** Metodo que permite obtener cantidad
	 * @param cantidad, tipo Double
	 */
	public Double getCantidad() {
		return cantidad;
	}

	/**
	 * Permite actualizar cantidad
	 * @param cantidad, tipo Double
	 */
	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
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
