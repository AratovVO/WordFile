package org.ringSearcher;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    Map<Integer, String> map;

    public Parser(Map<Integer, String> map) {
        this.map = map;
    }

    public static Pattern getDefaultPattern() {
        return Pattern.compile("(\\d{2}\\.\\d{2}\\.\\d{4})" +
                "(?:,\\s*Протокол\\s*№\\s*(\\S+)" +
                "\\s*к)?(?:\\s*Аттестату\\s*" +
                "№\\s*(\\S+))" +
                "?\\s*до\\s*" +
                "(\\d{2}\\.\\d{2}\\.\\d{4})");
    }

    public int findNumber(int ringNumber) {
        // В параметры метода передается номер кольца, который затем будет проверен на наличие
        // Если метод вернет -1, значит такой номер не найден, в случае успеха вернет найденный номер
//        try {
//            if (ringNumber > 374 || ringNumber < 1) {
//                throw new NullPointerException();
//            }
            StringBuilder sb = new StringBuilder("Зав.№");
            String regex = sb.append(ringNumber).toString();
            Pattern pattern = Pattern.compile("\\s*" + regex + "\\s*");

            for (Map.Entry<Integer, String> entry : map.entrySet()) {
                Matcher match = pattern.matcher(entry.getValue());
                if (match.find()) {
//                System.out.println(entry.getValue()); //строка для отладки
                    return entry.getKey();
                }
            }
//        }
//        catch(NullPointerException e){
//            System.out.println("Кольцо с номером " + ringNumber + " не надйено");
//        }
        return -1;
    }

    public static List<Integer> scanner() {
        // Метод нужен для того чтобы пользователь ввел с клавиатуры номера искомых колец.
        // После ввода строки она разбивается на массив по пробелу, после чего каждый символ парсится
        // и помещается в лист типа Integer
        List<Integer> list = new ArrayList<>();
        Pattern regex = Pattern.compile("\\s");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите номера колец: ");
        String ringNumbers = scanner.nextLine();
        Matcher match = regex.matcher(ringNumbers);
        if (ringNumbers.length() >= 1 && ringNumbers.length() <= 3) {
            list.add(Integer.parseInt(ringNumbers));
        }
        if (match.find()) {
            String[] array = ringNumbers.split(String.valueOf(regex));
            for (String value : array) {
                if (value.isEmpty()) {
                    continue;
                }
                if(Integer.parseInt(value) > 374 || Integer.parseInt(value) < 1){
                    System.out.println("Кольцо с номером " + Integer.parseInt(value) + " не найдено");
                    continue;
                }
                list.add(Integer.parseInt(value));
            }
//                System.out.println(Arrays.toString(array)); // строка для отладки
        }
//        if(match.find()){
//            String[] array = ringNumbers.split(String.valueOf(regex));
//            Arrays.stream(array).filter(value -> {
//                if(value.isEmpty())  return false;
//            })
//        }

        if (ringNumbers.equals("\n")) scanner.close();
        return list;
    }

    public static String setDate() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Введите дату начала испытания: ");
        return scan.nextLine();
    }

    public static void isOverdue(String startDate, String lastDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date1 = LocalDate.parse(lastDate, formatter);
        LocalDate date2 = LocalDate.parse(startDate, formatter);
        long def = ChronoUnit.DAYS.between(date2, date1);
        if (def <= 30) {
            System.out.println("\u001B[31m" + "!!!!!!!!!!!!Кольцо вышло из аттестации!!!!!!!!!!!!!!".toUpperCase()
                    + "\u001B[0m");
        }
    }
}

