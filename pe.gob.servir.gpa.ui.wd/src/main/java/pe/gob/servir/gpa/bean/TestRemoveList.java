package pe.gob.servir.gpa.bean;

import java.util.ArrayList;
import java.util.List;

public class TestRemoveList {

	
	public static void main(String[] args) 
	{
	List<String>arregloString = new ArrayList<>();
	arregloString.add("g");
	arregloString.add("e");
	arregloString.add("r");
	arregloString.add("a");
	arregloString.add("l");
	arregloString.add("d");
	arregloString.add("o");
	
	for (String string : arregloString) {
		System.out.println(string);
	}
	
	arregloString.remove(0);
	for (String string : arregloString) {
		System.out.println(string);
	}
	
	}

}
