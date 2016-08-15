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

import pe.gob.servir.gpa.model.dto.ProgramacionAreasDTO;
import pe.gob.servir.gpa.service.ejb.ProgramacionAreasServiceLocal;
import pe.gob.servir.gpa.util.UBaseManage;

@ManagedBean(name="programacionAreasBean")
@ViewScoped
public class ProgramacionAreasBean extends UBaseManage implements Serializable 
{
	private static final long serialVersionUID = -5438162644770038618L;
	
	/** permite capturar los erroes de ejecucion **/
	private Logger log = Logger.getLogger(ProgramacionAreasBean.class);
	@EJB
	private ProgramacionAreasServiceLocal programacionAreasServiceLocal;
	
	/** Variable contexto */
	private RequestContext context;
	/**Variable programacionAreasDTO*/	   
	private List<ProgramacionAreasDTO>listaProgramacionAreas;
	private Integer programacionId;
	private Integer areaId;	
	
	
	
	
	
	//Constructor vacío de la clase ProgramacionAreasBean
	public ProgramacionAreasBean()
	{
	
	}
	
	@PostConstruct
	public void Inicializando()
	{
		this.listaProgramacionAreas = new ArrayList<ProgramacionAreasDTO>();
		//Prueba para buscar|
		this.programacionId = null;
		this.areaId = null;
	}
	
	
	//Método que permite obtener la lista de las programaciones áreas, pasando como parámetro el id de la programación
	//y el id del área.
	public void buscarProgramacionAreas()
	{
		try 
		{
			this.listaProgramacionAreas = new ArrayList<ProgramacionAreasDTO>();
			List<ProgramacionAreasDTO>listaReal= programacionAreasServiceLocal.buscarProgramacionAreas(this.programacionId, this.areaId);
			if(listaReal != null && listaReal.size() >0)
			{
				for(ProgramacionAreasDTO objeto : listaReal)
				{
					//Se setea a la lista listaProgramacionAreas todos los objetos de la tabla
					//ProgramacionAreas con el id de programacionId. 
						this.listaProgramacionAreas.add(objeto);						
				}				
			}	
					
		} catch (Exception e) 
		{
			log.error(e.getMessage(),e);
		}
	}
	
	
	

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public ProgramacionAreasServiceLocal getProgramacionAreasServiceLocal() {
		return programacionAreasServiceLocal;
	}

	public void setProgramacionAreasServiceLocal(
			ProgramacionAreasServiceLocal programacionAreasServiceLocal) {
		this.programacionAreasServiceLocal = programacionAreasServiceLocal;
	}

	public RequestContext getContext() {
		return context;
	}

	public void setContext(RequestContext context) {
		this.context = context;
	}

	public List<ProgramacionAreasDTO> getListaProgramacionAreas() {
		return listaProgramacionAreas;
	}

	public void setListaProgramacionAreas(
			List<ProgramacionAreasDTO> listaProgramacionAreas) {
		this.listaProgramacionAreas = listaProgramacionAreas;
	}

	public Integer getProgramacionId() {
		return programacionId;
	}

	public void setProgramacionId(Integer programacionId) {
		this.programacionId = programacionId;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
		
	
}
