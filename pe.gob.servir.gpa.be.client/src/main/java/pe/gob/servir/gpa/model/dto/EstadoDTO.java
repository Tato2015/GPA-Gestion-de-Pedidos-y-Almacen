
 
package pe.gob.servir.gpa.model.dto;

import java.io.Serializable;

public class EstadoDTO extends PadreDTO implements Serializable{


    /**
	 * 
	 */
	private static final long serialVersionUID = -2382829803269640629L;

		

    /**Descripcion del registro Estado.*/    
    private String descripcionEstado;
    
    /**Abreviatura del registro Estado.*/    
    private String abreviaturaEstado;
          
    /**
     * Metodo Constructor
     */
    public EstadoDTO(){
    }

    /**
	 * Metodo que permite obtener descripcionEstado.
	 * @param descripcionEstado, tipo String
	 */
	public String getDescripcionEstado() {
		return descripcionEstado;
	}

	/**
	 * Permite actualizar descripcionEstado.
	 * @param descripcionEstado, tipo String
	 */
	public void setDescripcionEstado(String descripcionEstado) {
		this.descripcionEstado = descripcionEstado;
	}

	/**
	 * Metodo que permite obtener abreviaturaEstado.
	 * @param abreviaturaEstado, tipo String
	 */
	public String getAbreviaturaEstado() {
		return abreviaturaEstado;
	}

	/**
	 * Permite actualizar abreviaturaEstado.
	 * @param abreviaturaEstado, tipo String
	 */
	public void setAbreviaturaEstado(String abreviaturaEstado) {
		this.abreviaturaEstado = abreviaturaEstado;
	}
    
    
}
