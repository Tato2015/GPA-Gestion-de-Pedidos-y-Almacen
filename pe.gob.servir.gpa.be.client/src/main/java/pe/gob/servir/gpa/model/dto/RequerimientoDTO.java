package pe.gob.servir.gpa.model.dto;

import java.io.Serializable;
import java.sql.Date;

public class RequerimientoDTO extends PadreDTO implements Serializable 
{
	private static final long serialVersionUID = -7454491057818362719L;
	
	private String requerimientoNumero;
	private Integer montoTotal;
	private Short indicadorAnulado;
	private Short indicadorAprobado;
	private Date fechaAprobacion;
	private EstadoDTO estado;
	
	public RequerimientoDTO()
	{
		
	}

	public String getRequerimientoNumero() {
		return requerimientoNumero;
	}

	public void setRequerimientoNumero(String requerimientoNumero) {
		this.requerimientoNumero = requerimientoNumero;
	}

	public Integer getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(Integer montoTotal) {
		this.montoTotal = montoTotal;
	}

	public Short getIndicadorAnulado() {
		return indicadorAnulado;
	}

	public void setIndicadorAnulado(Short indicadorAnulado) {
		this.indicadorAnulado = indicadorAnulado;
	}

	public Short getIndicadorAprobado() {
		return indicadorAprobado;
	}

	public void setIndicadorAprobado(Short indicadorAprobado) {
		this.indicadorAprobado = indicadorAprobado;
	}

	public Date getFechaAprobacion() {
		return fechaAprobacion;
	}

	public void setFechaAprobacion(Date fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}

	public EstadoDTO getEstado() {
		return estado;
	}

	public void setEstado(EstadoDTO estado) {
		this.estado = estado;
	}
	
}