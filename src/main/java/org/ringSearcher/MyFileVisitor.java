package org.ringSearcher;

import info.debatty.java.stringsimilarity.Jaccard;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyFileVisitor extends SimpleFileVisitor<Path> {
    Path path = Paths.get("\\\\fs2\\LAB\\Obmen\\Испытательная лаборатория\\Система Менеджмента ИЛ\\Формы лаборатории");
    List<Path> fileNames = new ArrayList<>();
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
//        System.out.println("Обход директории: " + dir);
        if(dir.equals(path)){
            return FileVisitResult.CONTINUE;
        } else {
            return FileVisitResult.SKIP_SUBTREE;
        }
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
//        System.out.println("Имя текущего файла: " + file.getFileName());
        fileNames.add(file.getFileName());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
//        System.out.println("Ошибка при обращении к файлу/директории: " + file.getFileName());
        return FileVisitResult.TERMINATE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
//        System.out.println("Обход директории " + dir + " завершен");
        return FileVisitResult.CONTINUE;
    }

    public List<Path> getFileNames() {
        return fileNames;
    }
}

class UtilFileSearcher {
    private UtilFileSearcher(){
    }
    public static void main(String[] args) throws IOException {
        MyFileVisitor mfv = new MyFileVisitor();
        Jaccard jac = new Jaccard();
        Pattern pattern = Pattern.compile("~\\$рма");
        Path path = Paths.get("\\\\fs2\\LAB\\Obmen\\Испытательная лаборатория\\Система Менеджмента ИЛ\\Формы лаборатории");
        Files.walkFileTree(path,mfv);
        System.out.println(getFileNames(path));
        System.out.println(similarName(getFileNames(path)));
    }
    private static String similarName(List <String> listOfNames){
        Jaccard jac = new Jaccard();
        String str = "Форма по ИО.docx";
        return listOfNames.stream()
                .filter(el -> jac.similarity(el,str) > 0.5)
                .findFirst().orElse("Совпадений не обнаружено");

    }
    private static List<String> getFileNames(Path path) throws IOException {
        MyFileVisitor mfv = new MyFileVisitor();
        Files.walkFileTree(path,mfv);
        Pattern pattern = Pattern.compile("~\\$рма");
        return mfv.getFileNames().stream().map(Path::toString)
                .filter(el -> {
                    Matcher matcher = pattern.matcher(el);
                    return !matcher.find();
                })
                .toList();
    }
    public static String getFileName(Path path) throws IOException {
        return similarName(getFileNames(path));
    }
}
