package org.ringSearcher;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        TableReader readedColumn1 = new TableReader(7, "C:\\Users\\User\\Desktop\\Form IO.docx");
        TableReader readedColumn2 = new TableReader(6, "C:\\Users\\User\\Desktop\\Form IO.docx");

        Map<Integer, String> map1 = TableReader.readedMap(readedColumn1);
        Map<Integer, String> map2 = TableReader.readedMap(readedColumn2);
        Parser parser = new Parser(map2);
        List<Integer> ringNumbers = Parser.scanner();
        while (true) {
            for (Integer i : ringNumbers) {
                int index = parser.findNumber(i);
                Pattern defaultPattern = Parser.getDefaultPattern();
                Matcher match = defaultPattern.matcher(map1.get(index));
                if (index != -1) {
                    if (match.matches()) {
                        System.out.println("-испытательное кольцо №" + i + " сведения об аттестации: " +
                                "протокол №" + match.group(2) + " к аттестату " + match.group(3) + ", дата аттестации: "
                                + match.group(1) + " до " + match.group(4));
                    } else {
                        System.out.println("Кольцо не найдено");
                    }
                }
            }
            ringNumbers.clear();
            ringNumbers = Parser.scanner();
        }
    }
}
