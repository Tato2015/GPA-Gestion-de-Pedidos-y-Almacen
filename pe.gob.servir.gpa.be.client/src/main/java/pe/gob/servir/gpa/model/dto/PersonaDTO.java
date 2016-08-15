package pe.gob.servir.gpa.model.dto;

import java.io.Serializable;
import java.sql.Date;

public class PersonaDTO extends PadreDTO implements Serializable 
{

	private static final long serialVersionUID = -6585157319889184360L;
	
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombres;
	private AreaDTO area;
	private TipoDTO tipoGenero;
	private Date fechaNacimiento;
	private TipoDTO tipoDocumento;
	private String numeroDocumentoIdentidad;
	private String emailPrincipal;
	private String emailSecundario;
	private String telefono;
	private String celular;
	private UbigeoDTO ubigeo;
	private String direccion;
	private String direccionReferencia;
	private TipoDTO tipoProfesion;
	private String descripcionOtraProfesion;
	private EstadoDTO estado;
	
	public PersonaDTO()
	{
		
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public AreaDTO getArea() {
		return area;
	}

	public void setArea(AreaDTO area) {
		this.area = area;
	}

	public TipoDTO getTipoGenero() {
		return tipoGenero;
	}

	public void setTipoGenero(TipoDTO tipoGenero) {
		this.tipoGenero = tipoGenero;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public TipoDTO getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDTO tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumentoIdentidad() {
		return numeroDocumentoIdentidad;
	}

	public void setNumeroDocumentoIdentidad(String numeroDocumentoIdentidad) {
		this.numeroDocumentoIdentidad = numeroDocumentoIdentidad;
	}

	public String getEmailPrincipal() {
		return emailPrincipal;
	}

	public void setEmailPrincipal(String emailPrincipal) {
		this.emailPrincipal = emailPrincipal;
	}

	public String getEmailSecundario() {
		return emailSecundario;
	}

	public void setEmailSecundario(String emailSecundario) {
		this.emailSecundario = emailSecundario;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public UbigeoDTO getUbigeo() {
		return ubigeo;
	}

	public void setUbigeo(UbigeoDTO ubigeo) {
		this.ubigeo = ubigeo;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDireccionReferencia() {
		return direccionReferencia;
	}

	public void setDireccionReferencia(String direccionReferencia) {
		this.direccionReferencia = direccionReferencia;
	}

	public TipoDTO getTipoProfesion() {
		return tipoProfesion;
	}

	public void setTipoProfesion(TipoDTO tipoProfesion) {
		this.tipoProfesion = tipoProfesion;
	}

	public String getDescripcionOtraProfesion() {
		return descripcionOtraProfesion;
	}

	public void setDescripcionOtraProfesion(String descripcionOtraProfesion) {
		this.descripcionOtraProfesion = descripcionOtraProfesion;
	}

	public EstadoDTO getEstado() {
		return estado;
	}

	public void setEstado(EstadoDTO estado) {
		this.estado = estado;
	}
	

}