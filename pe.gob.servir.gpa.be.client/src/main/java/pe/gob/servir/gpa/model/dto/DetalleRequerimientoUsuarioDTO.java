package pe.gob.servir.gpa.model.dto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Embedded;

public class DetalleRequerimientoUsuarioDTO implements Serializable {

	
	private static final long serialVersionUID = -2452136402020488208L;
	
	private Long idDetalleRequerimientoUsuario;
	private RequerimientoUsuarioDTO requerimientoUsuario;
	private	TipoDTO familia;
	private ProductoDTO producto;
	private Double cantidadTotal;
	private Double costoTotal;
	private Double cantidadEntregada;
	private Integer indicadorAprobado;
	private Integer indicadorCerrado;
	private Integer indicadorActivo;
	private EstadoDTO estado;
	private List<DetalleProductoUsuarioDTO> listaDetalleProducto;
	/** Clase auditoria*/
	@Embedded
	private AuditoriaDTO auditoria;
	
	public DetalleRequerimientoUsuarioDTO() {}

	/** Metodo que permite obtener idDetalleRequerimientoUsuario
	 * @param idDetalleRequerimientoUsuario, tipo Long
	 */
	public Long getIdDetalleRequerimientoUsuario() {
		return idDetalleRequerimientoUsuario;
	}

	/**
	 * Permite actualizar idDetalleRequerimientoUsuario
	 * @param idDetalleRequerimientoUsuario, tipo Long
	 */
	public void setIdDetalleRequerimientoUsuario(Long idDetalleRequerimientoUsuario) {
		this.idDetalleRequerimientoUsuario = idDetalleRequerimientoUsuario;
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

	/** Metodo que permite obtener indicadorCerrado
	 * @param indicadorCerrado, tipo Integer
	 */
	public Integer getIndicadorCerrado() {
		return indicadorCerrado;
	}

	/**
	 * Permite actualizar indicadorCerrado
	 * @param indicadorCerrado, tipo Integer
	 */
	public void setIndicadorCerrado(Integer indicadorCerrado) {
		this.indicadorCerrado = indicadorCerrado;
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

	/** Metodo que permite obtener listaDetalleProducto
	 * @param listaDetalleProducto, tipo List<DetalleProductoDTO>
	 */
	public List<DetalleProductoUsuarioDTO> getListaDetalleProducto() {
		return listaDetalleProducto;
	}

	/**
	 * Permite actualizar listaDetalleProducto
	 * @param listaDetalleProducto, tipo List<DetalleProductoDTO>
	 */
	public void setListaDetalleProducto(
			List<DetalleProductoUsuarioDTO> listaDetalleProducto) {
		this.listaDetalleProducto = listaDetalleProducto;
	}

	/** Metodo que permite obtener cantidadEntregada
	 * @param cantidadEntregada, tipo Double
	 */
	public Double getCantidadEntregada() {
		return cantidadEntregada;
	}

	/**
	 * Permite actualizar cantidadEntregada
	 * @param cantidadEntregada, tipo Double
	 */
	public void setCantidadEntregada(Double cantidadEntregada) {
		this.cantidadEntregada = cantidadEntregada;
	}

	
	
	
}
