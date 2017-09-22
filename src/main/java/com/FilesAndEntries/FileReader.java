package com.FilesAndEntries;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

public class FileReader {

    public static String stringFromUser;
    private String[] findText =  strToArrOfStr(stringFromUser);
    private Integer findTextLenght = findText.length-1;
    private Integer q=0;

    public boolean readFile(Path path, String extension) throws Exception {
            if (extension.equals(".log") | extension.equals(".txt")){
                try (BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream(path.toFile()), "windows-1251"))) {  // НАСТРОИТЬ буфер
                    String line;
                    while ((line = buff.readLine()) != null) {
                        if (compareArrayOfWords(line)){
                            return true;
                        }
                    }
                }
            }else if (extension.equals(".docx")){
                XWPFDocument document = new XWPFDocument(new BufferedInputStream(new FileInputStream(path.toFile())));
                List<XWPFParagraph> paragraphs = document.getParagraphs();
                for (XWPFParagraph para : paragraphs) { //по линиям разбиваем
                    if (compareArrayOfWords(para.getText())) {
                        return true;
                    }
                }
            }else if (extension.equals(".xlsx")) {
                Workbook wb = WorkbookFactory.create(path.toFile());
                for (Sheet sheet : wb) {
                    Iterator<Row> rows = sheet.rowIterator();
                    while (rows.hasNext()) {
                        Row row = rows.next();
                        Iterator<Cell> cell = row.cellIterator();
                        while(cell.hasNext()) {
                            if (compareArrayOfWords(cell.next().toString())){
                                return true;
                            }
                        }
                    }
                }
            }
        return false;
    }


    private boolean compareArrayOfWords(String liner){
        String[] s = strToArrOfStr(liner);
        for (int j = 0; j < s.length; j++) {
            if (s[j].equals(findText[q])) {
                if (q.equals(findTextLenght)) {
                    return true;
                }
                q++;
            } else {
                q = 0;
            }
        }
        return false;
    }

    private String[] strToArrOfStr(String string){
        return string.trim().split("[ ]+");
    }

}

