package org.ringSearcher;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.ReadOnlyFileSystemException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TableReader {
    private final int targetColumnIndex;
    private final String path;


    public TableReader(int targetColumnIndex, String path) {
        this.targetColumnIndex = targetColumnIndex;
        this.path = path;
    }

    public int getTargetColumnIndex() {
        return targetColumnIndex;
    }

    public String getPath() {
        return path;
    }


    public static Map<Integer, String> readedMap(TableReader readedColumn) { // метод считывает таблицу из файла
        Map<Integer, String> map = new TreeMap<>();
        int counter = 0;
        try (FileInputStream fis = new FileInputStream(readedColumn.getPath())) {
            XWPFDocument doc = new XWPFDocument(fis);
            List<XWPFTable> tables = doc.getTables();
            for (XWPFTable table : tables) {
                List<XWPFTableRow> rows = table.getRows();
                for (XWPFTableRow row : rows) {
                    List<XWPFTableCell> cells = row.getTableCells();
                    if (readedColumn.getTargetColumnIndex() < cells.size()) {
                        XWPFTableCell target = cells.get(readedColumn.targetColumnIndex);
                        map.put(counter,target.getText());
                        counter++;
//                        System.out.println("Порядковый номер ячейки: " + counter++);
//                        System.out.println(target.getText() + "\t");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        } catch (ReadOnlyFileSystemException | IOException e) {
            System.out.println("Нечитаемый файл");
        }
        for(int i = 0; i < 4;i++){
            map.put(i,"");
        }
        return map; // где ключ - порядковый номер ячейки, а значение - строка хранящаяся в этой ячейке
    }
}
