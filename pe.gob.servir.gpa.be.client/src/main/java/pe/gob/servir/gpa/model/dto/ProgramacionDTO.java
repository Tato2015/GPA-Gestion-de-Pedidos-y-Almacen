package pe.gob.servir.gpa.model.dto;

import java.io.Serializable;
import java.util.Date;

public class ProgramacionDTO  extends PadreDTO implements Serializable {

	private static final long serialVersionUID = 8328544262167998214L;
	 
	private String descripcionProgramacion;	
	private TipoDTO tipoPedido;	
	private TipoDTO tipoPeriodo;
	private Date fechaInicioRegistro;	
	private Date fechaFinRegistro;	
	private Integer indicadorAprobacion;				
	private EstadoDTO estado;
	private Integer mesesDuracionPeriodo;
		
	public ProgramacionDTO() 
	{
		
	}


	public String getDescripcionProgramacion() {
		return descripcionProgramacion;
	}


	public void setDescripcionProgramacion(String descripcionProgramacion) {
		this.descripcionProgramacion = descripcionProgramacion;
	}


	public TipoDTO getTipoPedido() {
		return tipoPedido;
	}


	public void setTipoPedido(TipoDTO tipoPedido) {
		this.tipoPedido = tipoPedido;
	}


	public TipoDTO getTipoPeriodo() {
		return tipoPeriodo;
	}


	public void setTipoPeriodo(TipoDTO tipoPeriodo) {
		this.tipoPeriodo = tipoPeriodo;
	}


	public Date getFechaInicioRegistro() {
		return fechaInicioRegistro;
	}


	public void setFechaInicioRegistro(Date fechaInicioRegistro) {
		this.fechaInicioRegistro = fechaInicioRegistro;
	}


	public Date getFechaFinRegistro() {
		return fechaFinRegistro;
	}


	public void setFechaFinRegistro(Date fechaFinRegistro) {
		this.fechaFinRegistro = fechaFinRegistro;
	}


	public Integer getIndicadorAprobacion() {
		return indicadorAprobacion;
	}


	public void setIndicadorAprobacion(Integer indicadorAprobacion) {
		this.indicadorAprobacion = indicadorAprobacion;
	}


	public EstadoDTO getEstado() {
		return estado;
	}


	public void setEstado(EstadoDTO estado) {
		this.estado = estado;
	}


	public Integer getMesesDuracionPeriodo() {
		return mesesDuracionPeriodo;
	}


	public void setMesesDuracionPeriodo(Integer mesesDuracionPeriodo) {
		this.mesesDuracionPeriodo = mesesDuracionPeriodo;
	}

	
		
	
}
