package pe.gob.servir.gpa.model.dto;

import java.io.Serializable;

public class TipoDTO extends PadreDTO implements Serializable{

    //Atributo que debe ser distinto para cada version compilada que tengamos de esta clase 
    private static final long serialVersionUID = 1152573980528799678L;
    
    // Identificador del codigo padre recursiva. 
    private TipoDTO padre;
     
    // Identificador de tipo.
    private String codigoTipo;

    // Descripcion del registro tipo.
    private String descripcionTipo;

    // Abreviatura del registro tipo.
    private String abreviaturaTipo;
       
    // Entidad con Datos de Estado del Registro
    private EstadoDTO estado;
                 
    /**
     * Metodo Constructor
     */
    public TipoDTO()
    {
    }
    

	public TipoDTO getPadre() {
		return padre;
	}

	public void setPadre(TipoDTO padre) {
		this.padre = padre;
	}

	/**
	 * Metodo que permite obtener codigoTipo.
	 * @param codigoTipo, tipo String
	 */
	public String getCodigoTipo() {
		return codigoTipo;
	}



	/**
	 * Permite actualizar codigoTipo.
	 * @param codigoTipo, tipo String
	 */
	public void setCodigoTipo(String codigoTipo) {
		this.codigoTipo = codigoTipo;
	}



	/**
	 * Metodo que permite obtener descripcionTipo.
	 * @param descripcionTipo, tipo String
	 */
	public String getDescripcionTipo() {
		return descripcionTipo;
	}

	/**
	 * Permite actualizar descripcionTipo.
	 * @param descripcionTipo, tipo String
	 */
	public void setDescripcionTipo(String descripcionTipo) {
		this.descripcionTipo = descripcionTipo;
	}

	/**
	 * Metodo que permite obtener abreviaturaTipo.
	 * @param abreviaturaTipo, tipo String
	 */
	public String getAbreviaturaTipo() {
		return abreviaturaTipo;
	}

	/**
	 * Permite actualizar abreviaturaTipo.
	 * @param abreviaturaTipo, tipo String
	 */
	public void setAbreviaturaTipo(String abreviaturaTipo) {
		this.abreviaturaTipo = abreviaturaTipo;
	}

	public EstadoDTO getEstado() {
		return estado;
	}

	public void setEstado(EstadoDTO estado) {
		this.estado = estado;
	}

 }
