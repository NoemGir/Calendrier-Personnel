package GestionTache;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Tache {
	
	public static void main(String[] args) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(Calendar.YEAR, 2019);
		System.out.println(calendar.getTime());
	}
}
