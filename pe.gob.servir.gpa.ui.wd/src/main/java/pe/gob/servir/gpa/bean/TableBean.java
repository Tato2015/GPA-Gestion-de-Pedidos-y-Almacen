package pe.gob.servir.gpa.bean;

	import javax.faces.bean.ManagedBean;
	import javax.faces.bean.RequestScoped;
	import java.util.ArrayList;
	import java.util.List;

	@ManagedBean(name="tableBean")
	@RequestScoped
	public class TableBean {
	    private List<String> rowNames = new ArrayList<String>();
	    private List<String> colNames = new ArrayList<String>();
	    private ArrayList<ArrayList<ArrayList<String>>> data3D = new ArrayList<ArrayList<ArrayList<String>>>();

	    public TableBean() {
	        rowNames.add("row1");
	        rowNames.add("row2");

	        for(int i=0;i<6;i++)
	        {
	        	colNames.add("Columna "+i);
	        }
//	        colNames.add("col1");
//	        colNames.add("col2");
//	        colNames.add("col3");

	        // Setup 3 dimensional structure
	        for (int i = 0; i < rowNames.size(); i++) {
	            data3D.add(new ArrayList<ArrayList<String>>());
	            for (int j = 0; j < colNames.size(); j++) {
	                data3D.get(i).add(new ArrayList<String>());
	            }
	        }

	        // row 1, col 1, 3 items
	        data3D.get(0).get(0).add("item1");
	        data3D.get(0).get(0).add("item2");
	        data3D.get(0).get(0).add("item3");

	        // row 1, col 2, 1 items
	        data3D.get(0).get(1).add("item1");

	        // row 1, col 3, 2 items
	        data3D.get(0).get(2).add("item1");
	        data3D.get(0).get(2).add("item2");

	        // row 2, col 1, 2 item
	        data3D.get(1).get(0).add("item1");
	        data3D.get(1).get(0).add("item2");

	        // row 2, col 2, 1 item
	        data3D.get(1).get(1).add("item1");
	    }

	    public List<String> getRowNames() { return rowNames; }
	    public void setRowNames(List<String> rowNames) { this.rowNames = rowNames; }

	    public List<String> getColNames() { return colNames; }
	    public void setColNames(List<String> colNames) { this.colNames = colNames; }

	    public ArrayList<ArrayList<ArrayList<String>>> getData3D() { return data3D; }
	    public void setData3D(ArrayList<ArrayList<ArrayList<String>>> data3D) { this.data3D = data3D; }
	}

