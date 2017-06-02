package com.jow_app.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by diego on 4/9/17.
 */

public class FormatUtils {

    public static String transformDate(String dueDateDoAPI){
        // Data atual
        Calendar dataAtual = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if (dueDateDoAPI.equals("amanhã")){
            dataAtual.roll(Calendar.DAY_OF_MONTH, true);
        }
        else if(!dueDateDoAPI.isEmpty() && !dueDateDoAPI.equals("hoje")){
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
        String date = sdf.format(dataAtual.getTime());
        return date;
    }

    public static Map<String, Integer> createMAp(){
        Map<String, Integer> mapConversor = new HashMap<String, Integer>();

        mapConversor.put("domingo", 1);
        mapConversor.put("segunda", 2);
        mapConversor.put("terça", 3);
        mapConversor.put("quarta", 4);
        mapConversor.put("quinta", 5);
        mapConversor.put("sexta", 6);
        mapConversor.put("sábado", 7);

        return mapConversor;
    }



    public static Long getProjectId(String key){
        Map<String, Long> projectsMap = new HashMap<String, Long>();
        projectsMap.put("Eu", 173141082942628L);
        projectsMap.put("Startup Lab", 236631540813714L);
        projectsMap.put("Dentro da História", 237648867350653L);
        projectsMap.put("Graduação", 270298945788209L);
        projectsMap.put("jow", 303196165594677L);
        projectsMap.put("Startup Weekend 2017", 236631540813709L);
        projectsMap.put("Discipuluz", 173141082942571L);

        return projectsMap.get(key);
    }
}
