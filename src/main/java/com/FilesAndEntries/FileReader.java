package com.FilesAndEntries;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

public class FileReader {

    public static String stringFromUser;
    String[] findText =  strToArrOfStr(stringFromUser);
    Integer findTextLenght = findText.length-1;
    String line;
    Integer q=0;

    public boolean readFile(Path path, String extension) throws Exception {

            if (extension.equals(".log") | extension.equals(".txt")){
                try (BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream(path.toFile()), "windows-1251"))) {  // НАСТРОИТЬ буфер
                    while ((line = buff.readLine()) != null) {
                        if (compareArrayOfWords(line)){
                            return true;
                        }
                    }
                }
            }else if (extension.equals(".docx")){
                XWPFDocument document = new XWPFDocument(new FileInputStream(new File(path.toString())));
                List<XWPFParagraph> paragraphs = document.getParagraphs();
                for (XWPFParagraph para : paragraphs) { //по линиям разбиваем
                    if (compareArrayOfWords(para.getText())){
                        return true;
                    }
                }
            }else if (extension.equals(".xlsx")) {
                Workbook wb = WorkbookFactory.create(new File(path.toString()));
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
                if (q == findTextLenght) {
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

/*работает для docx*/
 /*   public static void readDocFile(String fileName)   {

        try {
            File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());

            XWPFDocument document = new XWPFDocument(fis);

            List<XWPFParagraph> paragraphs = document.getParagraphs();

            System.out.println("Total no of paragraph "+paragraphs.size());
            for (XWPFParagraph para : paragraphs) {
                System.out.println(para.getText());
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

/*while (!eof) {
                    if ( buff.read() == -1){
                        eof = true;
                    }else{
                        buff.read(buffer, 0, BUFFER_SIZE);
                        for (char s:buffer) {
                        bufferFinish[k++]= s;
                        }
                    }
                }*/




/*работающий код для xlsx*/
/*
try {
        File file = new File(fileName);
        Workbook wb = WorkbookFactory.create(file);
        Sheet sheet = wb.getSheetAt(0);
        Iterator<Row> rows = sheet.rowIterator();
        while (rows.hasNext()) {
        Row row = rows.next();

        Iterator<Cell> cell = row.cellIterator();
        while(cell.hasNext()) {
        System.out.println(cell.next());
        }
        }
        }catch (IOException | InvalidFormatException e){}*/
