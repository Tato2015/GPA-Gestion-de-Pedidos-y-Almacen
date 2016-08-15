package pe.gob.servir.gpa.model.dto;

import java.io.Serializable;

public class UbigeoDTO extends PadreDTO implements Serializable 
{

	private static final long serialVersionUID = 4664525132037899809L;

	private UbigeoDTO ubigeoPadre;
	private String descripcionUbigeo;
	private String codigoUbigeo;
	private String codigoReferencia;
	
	public UbigeoDTO()
	{
		
	}
	
	public UbigeoDTO getUbigeoPadre() {
		return ubigeoPadre;
	}
	public void setUbigeoPadre(UbigeoDTO ubigeoPadre) {
		this.ubigeoPadre = ubigeoPadre;
	}
	public String getDescripcionUbigeo() {
		return descripcionUbigeo;
	}
	public void setDescripcionUbigeo(String descripcionUbigeo) {
		this.descripcionUbigeo = descripcionUbigeo;
	}
	public String getCodigoUbigeo() {
		return codigoUbigeo;
	}
	public void setCodigoUbigeo(String codigoUbigeo) {
		this.codigoUbigeo = codigoUbigeo;
	}
	public String getCodigoReferencia() {
		return codigoReferencia;
	}
	public void setCodigoReferencia(String codigoReferencia) {
		this.codigoReferencia = codigoReferencia;
	}
	

}