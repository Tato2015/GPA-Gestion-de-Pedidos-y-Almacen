package pe.gob.servir.gpa.model.dto;

import java.io.Serializable;

public class DetalleRequerimientoUsuarioProductoDTO extends PadreDTO implements Serializable
{
	private static final long serialVersionUID = -1472539540652928855L;
	
	private RequerimientoUsuarioDTO requerimientoUsuario;
	private ProductoDTO producto;
	private Integer cantidad;
	private EstadoDTO estado;
	
	public DetalleRequerimientoUsuarioProductoDTO()
	{
		
	}

	public RequerimientoUsuarioDTO getRequerimientoUsuario() {
		return requerimientoUsuario;
	}

	public void setRequerimientoUsuario(RequerimientoUsuarioDTO requerimientoUsuario) {
		this.requerimientoUsuario = requerimientoUsuario;
	}

	public ProductoDTO getProducto() {
		return producto;
	}

	public void setProducto(ProductoDTO producto) {
		this.producto = producto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public EstadoDTO getEstado() {
		return estado;
	}

	public void setEstado(EstadoDTO estado) {
		this.estado = estado;
	}
	
	
	
	
	
}
