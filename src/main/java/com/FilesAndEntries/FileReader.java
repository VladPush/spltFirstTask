package com.FilesAndEntries;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.*;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

public class FileReader {

    public static String stringFromUser;
    String[] findText =  strToArrOfStr(stringFromUser);
    Integer findTextLenght = findText.length-1;

    public boolean readFile(Path path) throws IOException {
            if (new File(path.toUri()).getName().endsWith(".docx")){
                readDocFile(path.toString());
            }else


        try (BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream(path.toFile()), "windows-1251"))) {  // НАСТРОИТЬ буфер
            //  String encoding = new InputStreamReader(new FileInputStream(path.toFile())).getEncoding(); //такой подход почему то помещает в line неверную информацию на кириллице
            Integer q = 0;
            String line;
            while ((line = buff.readLine()) != null) {
                String[] s = strToArrOfStr(line);
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
            }
        }
        return false;
    }

    private String[] strToArrOfStr(String string){
        return string.trim().split("[ ]+");
    }

    public  void readDocFile(String fileName)   {

        try {
            File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());

            XWPFDocument document = new XWPFDocument(fis);

            List<XWPFParagraph> paragraphs = document.getParagraphs();

            System.out.println("Total no of paragraph "+paragraphs.size());
            for (XWPFParagraph para : paragraphs) {
                System.out.println(para.getText());
                System.out.println("\n");
            }


            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
