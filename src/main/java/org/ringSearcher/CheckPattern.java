package org.ringSearcher;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckPattern { // этот класс проверяет соотвествие строк из файла заданному паттерну
    public static void main(String[] args) throws IOException {
        TableReader readedColumn1 = new TableReader(7, "\\\\fs2\\LAB\\Obmen\\Испытательная лаборатория\\Система Менеджмента ИЛ\\Формы лаборатории\\Форма по ИО.docx");
        TableReader readedColumn2 = new TableReader(6, "\\\\fs2\\LAB\\Obmen\\Испытательная лаборатория\\Система Менеджмента ИЛ\\Формы лаборатории\\Форма по ИО.docx");
        Map<Integer, String> map1 = TableReader.readedMap(readedColumn1);
        for (Map.Entry<Integer, String> entry : map1.entrySet()){
            Pattern defaultPattern = Parser.getDefaultPattern();
            Matcher match = defaultPattern.matcher(entry.getValue());
            if(match.matches()){
                System.out.println(entry.getKey() + " Паттерн совпал");
                System.out.println(entry.getValue());
            } else {
                System.out.println(entry.getKey() + " " + entry.getValue());
                System.out.println("Паттерн не совпал");
            }
        }
    }
}
