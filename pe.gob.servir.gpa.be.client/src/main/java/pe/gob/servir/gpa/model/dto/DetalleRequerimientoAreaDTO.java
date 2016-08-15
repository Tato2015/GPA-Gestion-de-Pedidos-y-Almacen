package pe.gob.servir.gpa.model.dto;

import java.io.Serializable;


import javax.persistence.Embedded;

public class DetalleRequerimientoAreaDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5787107777490049559L;
	
	private Long idDetalleRequerimientoArea;
	private RequerimientoAreaDTO requerimientoArea;
	private	TipoDTO familia;
	private ProductoDTO producto;
	private Double cantidadTotal;
	private Double costoTotal;
	private Integer indicadorActivo;
	private EstadoDTO estado;
	/** Clase auditoria*/
	@Embedded
	private AuditoriaDTO auditoria;
	
	public DetalleRequerimientoAreaDTO() {}

	/** Metodo que permite obtener idDetalleRequerimientoArea
	 * @param idDetalleRequerimientoArea, tipo Long
	 */
	public Long getIdDetalleRequerimientoArea() {
		return idDetalleRequerimientoArea;
	}

	/**
	 * Permite actualizar idDetalleRequerimientoArea
	 * @param idDetalleRequerimientoArea, tipo Long
	 */
	public void setIdDetalleRequerimientoArea(Long idDetalleRequerimientoArea) {
		this.idDetalleRequerimientoArea = idDetalleRequerimientoArea;
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

	/** Metodo que permite obtener familia
	 * @param familia, tipo TipoDTO
	 */
	public TipoDTO getFamilia() {
		return familia;
	}

	/**
	 * Permite actualizar familia
	 * @param familia, tipo TipoDTO
	 */
	public void setFamilia(TipoDTO familia) {
		this.familia = familia;
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

	/** Metodo que permite obtener cantidadTotal
	 * @param cantidadTotal, tipo Double
	 */
	public Double getCantidadTotal() {
		return cantidadTotal;
	}

	/**
	 * Permite actualizar cantidadTotal
	 * @param cantidadTotal, tipo Double
	 */
	public void setCantidadTotal(Double cantidadTotal) {
		this.cantidadTotal = cantidadTotal;
	}

	/** Metodo que permite obtener costoTotal
	 * @param costoTotal, tipo Double
	 */
	public Double getCostoTotal() {
		return costoTotal;
	}

	/**
	 * Permite actualizar costoTotal
	 * @param costoTotal, tipo Double
	 */
	public void setCostoTotal(Double costoTotal) {
		this.costoTotal = costoTotal;
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
