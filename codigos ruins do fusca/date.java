import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.*;
import java.util.Map;
import java.util.HashMap;

public class date {

	public static void main(String[] args) {



	}



	public String transformDate(String dueDateDoAPI){

		// Data atual
		Calendar dataAtual = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		if (dueDateDoAPI.equals("amanhã")){
			dataAtual.roll(Calendar.DAY_OF_MONTH, true);
		}else {
			// Mapa de conversão
			Map<String, Integer> mapConversor = createMAp();

			// pega dia da semana atual
			int weekDayAtual = dataAtual.get(Calendar.DAY_OF_WEEK);

			// pega dia da semana da entrada convertido pra numero
			int weekDayDueDate = mapConversor.get(dueDateDoAPI);

			// seta calendar com a diferença entre os dois
			// TODO: ARRUMAR VIRADA DE MÊS

			if (weekDayAtual < weekDayDueDate){
				dataAtual.add(Calendar.DAY_OF_MONTH, weekDayDueDate - weekDayAtual);
			}else {
				dataAtual.add(Calendar.DAY_OF_MONTH, (7 - weekDayAtual) + weekDayDueDate);
			}
		}

		String date = sdf.format(dataAtual);
		return date;
	}

	public Map<String, Integer> createMAp(){
		Map<String, Integer> mapConversor = new HashMap<String, Integer>();

		mapConversor.put("segunda", 1);
		mapConversor.put("terça", 2);
		mapConversor.put("quarta", 3);
		mapConversor.put("quinta", 4);
		mapConversor.put("sexta", 5);
		mapConversor.put("sábado", 6);
		mapConversor.put("domingo", 7);

		return mapConversor;
	}

}
