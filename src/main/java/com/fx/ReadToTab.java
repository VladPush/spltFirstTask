package com.fx;

import javafx.scene.control.TextArea;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.*;
import java.util.Iterator;
import java.util.List;

public class ReadToTab {
    TextArea textArea;


    TextArea moderator(File file)throws IOException, InvalidFormatException{
        textArea=new TextArea();
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            String nameOfFile = file.getName();
            if (nameOfFile.endsWith(".log") | nameOfFile.endsWith(".txt")) {
                read(new BufferedReader(new InputStreamReader(fileInputStream, "windows-1251")));

            } else if (nameOfFile.endsWith(".docx")) {
                read(new XWPFDocument(fileInputStream));

            } else if (nameOfFile.endsWith(".xlsx")) {
                read(WorkbookFactory.create(fileInputStream));
            }
        }
    return textArea;
    }

    /*txt log*/
    private void read(BufferedReader buff) throws IOException {
        String line;
        while ((line = buff.readLine()) != null) {
            textArea.appendText(line);
            if (!(line.trim().isEmpty())) {
                textArea.appendText("\n");
            }

        }
    }

    /*docx*/
    private void read(XWPFDocument document){
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        for (XWPFParagraph para : paragraphs) {
            textArea.appendText(para.getText());
        }
    }

    /*xlsx*/
    private void read(Workbook wb) {
        for (Sheet sheet : wb) {
            Iterator<Row> rows = sheet.rowIterator();
            while (rows.hasNext()) {
                Iterator<Cell> cell = rows.next().cellIterator();
                while (cell.hasNext()) {
                    textArea.appendText(cell.next().toString() + " ");
                }
                textArea.appendText("\n");
            }
        }
    }

}
