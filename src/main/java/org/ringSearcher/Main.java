package org.ringSearcher;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        Path defaultPath = Paths.get("\\\\fs2\\LAB\\Obmen\\Испытательная лаборатория\\Система Менеджмента ИЛ\\Формы лаборатории\\");
        String path = defaultPath + "\\" +UtilFileSearcher.getFileName(defaultPath);
        System.out.println(path);
        TableReader readedColumn1 =
                new TableReader(7, path);
        TableReader readedColumn2 =
                new TableReader(6, path);
        Map<Integer, String> map1 = TableReader.readedMap(readedColumn1);
        Map<Integer, String> map2 = TableReader.readedMap(readedColumn2);
        Parser parser = new Parser(map2);
        String startExpirimentDate = Parser.setDate();
        List<Integer> ringNumbers = Parser.scanner();
        while (true) {
            for (Integer i : ringNumbers) {
                int index = parser.findNumber(i);
                Pattern defaultPattern = Parser.getDefaultPattern();
                Matcher match = defaultPattern.matcher(map1.get(index));
                if (index != -1) {
                    if (match.matches()) {
                        Parser.isOverdue(startExpirimentDate, match.group(4));
                        System.out.println("испытательное кольцо №" + i + " – сведения об аттестации: " +
                                "протокол №" + match.group(2) + " к аттестату №" + match.group(3) + ", дата аттестации: "
                                + match.group(1) + " до " + match.group(4) + ";");
                    }
                }
            }
            ringNumbers.clear();
            ringNumbers = Parser.scanner();
        }
    }
}
