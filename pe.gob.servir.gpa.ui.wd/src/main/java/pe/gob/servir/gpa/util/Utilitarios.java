package pe.gob.servir.gpa.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utilitarios {

	public Utilitarios() {

	}

	public static Integer getAnioSistema() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.YEAR);
	}
	
	//Se suma 1 porque la iteración inicia en:
	//0 para Enero 
	//1 para Febrero etc.
	public static Integer getMesSistema()
	{
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.MONTH)+1;
	}
	
	public static String getFechaHoraSistema() {
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
		String s = sdf2.format(new Date());
		return s;
	}
	
	public static String getFechaSistema() {
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		String s = sdf2.format(new Date());
		return s;
	}

	public static void main(String []args)
	{
	//	Utilitarios u = new Utilitarios();
//		System.out.println(u.getFechaSistema());
	}
	
}
