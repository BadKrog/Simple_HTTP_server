package server.logics;

public class Logics {
    private static double percentLowerCase =0;
    private static double percentUpperCase =0;

    private static final String NAME = "<h4>Работу выполнил: Зубреев Антон Игоревич</h4>";
    private static final String GROUP = "<h4>Номер группы: ИКБО-02-18</h4>";
    private static final String NUMBER = "<h4>Номер индивидуального задания: 7</h4>";
    private static final String TASK = "<h4>Текст индивидуального задания: Дан текст. Определите процентное отношение строчных и прописных\n" +
            "букв к общему числу символов в нем. На вход поступает текст в виде строки.</h4>";


    private static void countUp(String input){
        percentLowerCase = 0;
        percentUpperCase = 0;

        int lowerCase = 0;
        int upperCase = 0;
        for(int i=0; i<input.length(); i++){
            if(Character.isLowerCase(input.charAt(i))) lowerCase++;
            if(Character.isUpperCase(input.charAt(i))) upperCase++;
        }

        percentLowerCase = (double) lowerCase/input.length() * 100;
        percentUpperCase = (double) upperCase/input.length() * 100;
    }

    private static double getPercentLowerCase() {
        return percentLowerCase;
    }

    private static double getPercentUpperCase() {
        return percentUpperCase;
    }

    public static String getBody(String request){
        countUp(request);

        String echo = "<h4>Серверу была передана строка: "+request+"</h4>";
        String lower = "<h4>Процентное соотношения строчных букв: "+ getPercentLowerCase() +"%</h4>";
        String upper = "<h4>Процентное соотношения прописных букв: "+ getPercentUpperCase() +"%</h4>";

       String answer = NAME+GROUP+NUMBER+TASK+echo+lower+upper;
        return answer;
    }
}
