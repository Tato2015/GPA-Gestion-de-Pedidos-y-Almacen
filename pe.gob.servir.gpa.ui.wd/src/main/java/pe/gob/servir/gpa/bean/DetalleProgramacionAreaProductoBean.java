package pe.gob.servir.gpa.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import pe.gob.servir.gpa.model.dto.DetalleProgramacionAreaProductoDTO;
import pe.gob.servir.gpa.service.ejb.DetalleProgramacionAreaProductoServiceLocal;
import pe.gob.servir.gpa.util.UBaseManage;

@ManagedBean(name="detalleProgramacionAreaProductoBean")
@ViewScoped
public class DetalleProgramacionAreaProductoBean extends UBaseManage implements Serializable
{
	private static final long serialVersionUID = -452943131314010555L;
	/** permite capturar los erroes de ejecucion **/
	private Logger log = Logger.getLogger(DetalleProgramacionAreaProductoBean.class);
	
	@EJB
	private DetalleProgramacionAreaProductoServiceLocal detalleProgramacionAreaProductoServiceLocal;
	
	/** Variable contexto */
	private RequestContext context;
	/**Variable detalleProgramacionAreaProductoDTO*/	   
	private List<DetalleProgramacionAreaProductoDTO>listaDetalleProgramacionAreaProducto;
	private Integer programacionAreasId;
	private Integer productoDisponibleId;
	
	//Constructor vacío
	public DetalleProgramacionAreaProductoBean()
	{
		
	}
	
	@PostConstruct
	public void Inicializando()
	{
		this.programacionAreasId =null;
		this.productoDisponibleId = null;
		this.listaDetalleProgramacionAreaProducto = new ArrayList<>();
	}
	
	public void resetearAtributosDetalleProgramacionAreaProductos()
	{
		this.programacionAreasId =null;
		this.productoDisponibleId = null;
		this.listaDetalleProgramacionAreaProducto = new ArrayList<>();
	}
	
	
	public void buscarProductosXArea()
	{
		try 
		{
			this.listaDetalleProgramacionAreaProducto = new ArrayList<DetalleProgramacionAreaProductoDTO>();
			List<DetalleProgramacionAreaProductoDTO>listaReal = detalleProgramacionAreaProductoServiceLocal.buscarDetalleProgramacionAreaProducto(programacionAreasId, productoDisponibleId);
			
			if(listaReal!=null && listaReal.size()>0)
			{
				for(DetalleProgramacionAreaProductoDTO objeto:listaReal)
				{
					//Se setea a la lista listaDetalleProgramacionAreaProducto todos los objetos de la tabla
					//DetalleProgramacionAreaProducto con el id de programacionAreasId. 
					this.listaDetalleProgramacionAreaProducto.add(objeto);
				}
			}
			
			
		} catch (Exception e) 
		{
			log.error(e.getMessage(),e);
		}
	}
	
	
	
	
	//Métodos de acceso

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public DetalleProgramacionAreaProductoServiceLocal getDetalleProgramacionAreaProductoServiceLocal() {
		return detalleProgramacionAreaProductoServiceLocal;
	}

	public void setDetalleProgramacionAreaProductoServiceLocal(
			DetalleProgramacionAreaProductoServiceLocal detalleProgramacionAreaProductoServiceLocal) {
		this.detalleProgramacionAreaProductoServiceLocal = detalleProgramacionAreaProductoServiceLocal;
	}

	public RequestContext getContext() {
		return context;
	}

	public void setContext(RequestContext context) {
		this.context = context;
	}

	public List<DetalleProgramacionAreaProductoDTO> getListaDetalleProgramacionAreaProducto() {
		return listaDetalleProgramacionAreaProducto;
	}

	public void setListaDetalleProgramacionAreaProducto(
			List<DetalleProgramacionAreaProductoDTO> listaDetalleProgramacionAreaProducto) {
		this.listaDetalleProgramacionAreaProducto = listaDetalleProgramacionAreaProducto;
	}

	public Integer getProgramacionAreasId() {
		return programacionAreasId;
	}

	public void setProgramacionAreasId(Integer programacionAreasId) {
		this.programacionAreasId = programacionAreasId;
	}

	public Integer getProductoDisponibleId() {
		return productoDisponibleId;
	}

	public void setProductoDisponibleId(Integer productoDisponibleId) {
		this.productoDisponibleId = productoDisponibleId;
	}
			
	
	
}
