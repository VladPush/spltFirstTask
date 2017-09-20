package com.fx;

import com.FilesAndEntries.FileFinder;
import com.FilesAndEntries.FileReader;
import com.Syntax.Dir;
import com.Syntax.Ext;
import com.Syntax.Txt;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.*;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

public class ControllerSample extends treeViewHandler {

    private FileFinder fileFinder = new FileFinder();

    @FXML()
    public TreeView<String> tree = new TreeView<>();

    @FXML()
    public Label warningLabel;

    @FXML()
    public TextField textfieldDir;

    @FXML()
    public TextArea textfieldText;

    @FXML()
    public TextField textfieldExt;

    @FXML()
    public Button button;

    @FXML()
    public TabPane tabPane;

    Dir dir = new Dir();
    Ext ext = new Ext();
    Txt txt = new Txt();

    public void initialize() {
        /*Потеря фокуса на поле ввода*/
        textfieldDir.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) {
                warningLabel.setText(dir.check(textfieldDir.getText()));
                if (dir.status)
                    fileFinder.foolDirName = dir.correct(textfieldDir.getText()); //  C://files              //         //NASTYADELL/filesD
                letButtonWork();
            }
        });

        textfieldText.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) {
                warningLabel.setText(txt.check(textfieldText.getText()));
                if (txt.status)
                    FileReader.stringFromUser = txt.correct(textfieldText.getText()); // "Это город Москва."
                letButtonWork();
            }
        });

        textfieldExt.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) {
                warningLabel.setText(ext.check(textfieldExt.getText()));
                if (ext.status)
                    fileFinder.foolExtension  = ext.correct(textfieldExt.getText());
                letButtonWork();
            }
        });

        tree.setShowRoot(false);
        button.setDisable(true);
    }

    /*Поиск и построение дерева по архиву Path !кнопка!*/
    public void buildTree(MouseEvent mouseEvent)  {
        TreeItem<String> root = new TreeItem<>();

            /*Обход директории и поиск вхождений*/
            try {
                for (Path s : fileFinder.FindTheEntries(dir.typeOfDir)){
                    int i=1;
                    buildBranch(s, root, i);
                }
                if (root.isLeaf()) {
                    warningLabel.setText("No entries.");
                }else{
                    warningLabel.setText("Done.");
                }
            }catch (Exception e){
                warningLabel.setText("Directory is missing: " + e.getMessage());
            }

        tree.setRoot(root);
    }

    /*Восстановление path из клика по элементу treeView  !контекстное меню на treeItem!*/
    public void ContextMenuEvent(ContextMenuEvent contextMenuEvent) {
        File file = new File(returnPath(tree, fileFinder.rootDirname).toUri());

        if (file.isDirectory()){
            warningLabel.setText("It's a directory!");
        }else{
            TextArea textArea = null;
            Tab tab = new Tab();
            tab.setText(file.toPath().getFileName().toString());
            try{
                textArea =  new ReadToTab().moderator(file);
            }catch (IOException | InvalidFormatException e){ warningLabel.setText("Error: " + e.getMessage());}

            tab.setContent(textArea);
            tabPane.getTabs().add(tab);
        }
    }

    private void letButtonWork(){
        if (ext.status && dir.status && txt.status)
            button.setDisable(false);
        else
            button.setDisable(true);
    }

   /* public static Charset guessCharset(InputStream is) throws IOException {
        return Charset.forName(new TikaEncodingDetector().guessEncoding(is));
    }*/
}



/*    public void ContextMenuEvent(ContextMenuEvent contextMenuEvent) {
        File file = new File(returnPath(tree, fileFinder.rootDirname).toUri());

        if (file.isDirectory()){
            warningLabel.setText("It's a directory!");
        }else{

            TextArea textArea = new TextArea();
            Tab tab = new Tab();
            tab.setText(file.toPath().getFileName().toString());

            try (FileInputStream fileInputStream = new FileInputStream(file)) {

                if (file.getName().endsWith(".log") | file.getName().endsWith(".txt")) {

                    BufferedReader buff = new BufferedReader(new InputStreamReader(fileInputStream, "windows-1251"));
                    String line;
                    while ((line = buff.readLine()) != null) {
                        textArea.appendText(line);
                        if (!(line.trim().isEmpty())) {
                            textArea.appendText("\n");
                        }
                    }

                } else if (file.getName().endsWith(".docx")) {

                    XWPFDocument document = new XWPFDocument(fileInputStream);
                    List<XWPFParagraph> paragraphs = document.getParagraphs();
                    for (XWPFParagraph para : paragraphs) { //по линиям разбиваем
                        textArea.appendText(para.getText());
                    }

                } else if (file.getName().endsWith(".xlsx")) {

                    Workbook wb = WorkbookFactory.create(fileInputStream);
                    Sheet sheet = wb.getSheetAt(0);
                    Iterator<Row> rows = sheet.rowIterator();
                    while (rows.hasNext()) {
                        Row row = rows.next();
                        Iterator<Cell> cell = row.cellIterator();
                        while (cell.hasNext()) {
                            textArea.appendText(cell.next().toString());
                            textArea.appendText(" ");
                        }
                        textArea.appendText("\n");
                    }
                }
            }catch (IOException | InvalidFormatException e){ warningLabel.setText("Error: " + e.getMessage());}

            tab.setContent(textArea);
            tabPane.getTabs().add(tab);

        }
    }*/















       /* try {
            TextArea textArea = new TextArea();
            Tab tab = new Tab();
            tab.setText("jfvkf.docx");

            File file = new File("C:\\files\\jfvkf.docx");
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());

            XWPFDocument document = new XWPFDocument(fis);

            List<XWPFParagraph> paragraphs = document.getParagraphs();

            System.out.println("Total no of paragraph "+paragraphs.size());
            for (XWPFParagraph para : paragraphs) {
                System.out.println(para.getText());
                textArea.appendText(para.getText());
                System.out.println(para.getText());
                if (!(para.getText().trim().isEmpty())){
                    textArea.appendText("\n");
                }
            }

            tab.setContent(textArea);
            tabPane.getTabs().add(tab);

            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/


/*    public void buildBranch(Path path, TreeItem<String> root, int i) {
        finish:
        while (i<path.getNameCount()){
            if (!(root.getChildren().isEmpty())) { //если рута не пустая то заходим в неё
                for (TreeItem<String> item : root.getChildren()){ // начинаем обходить директорию

                    if (item.getValue().toString().equals(path.subpath(i,i+1).toString())) //если есть то шагаем вглубь
                    {
                        buildBranch(path, item ,++i);
                        break finish;
                    }
                }
                makeBranch(path.subpath(i,i+1).toString(),root); // если нету такой папки, то добавляем
            } else {
                makeBranch(path.subpath(i,i+1).toString(), root); // создание первого элемента в директории
            }
        }
    }*/

/*    public void buildBranch(Path path, TreeItem<String> root, int i) {
        finish:
        while (i<path.getNameCount()){
            if (!(root.getChildren().isEmpty())) { //если рута не пустая то заходим в неё
                for (TreeItem<String> item : root.getChildren()){ // начинаем обходить директорию

                    if (item.getValue().toString().equals(path.subpath(i,i+1).toString())) //если есть то шагаем вглубь
                    {
                        buildBranch(path, item ,++i);
                        break finish;
                    }
                }
                makeBranch(path.subpath(i,i+1).toString(),root); // если нету такой папки, то добавляем и шагаем вглубь
                //   buildBranch(path, root,i);
                //   break finish;
            } else {
                makeBranch(path.subpath(i,i+1).toString(), root); // создание первого элемента в директории
            }
        }
    }
*/


/*
    public void buildBranch(String[] arrPath, TreeItem<String> root, int i) {
        finish:
        while (i<=arrPath.length-1){
            if (!(root.getChildren().isEmpty())) { //если рута не пустая то заходим в неё
                for (TreeItem<String> item : root.getChildren()){ // начинаем обходить директорию

                    if (item.getValue().toString().equals(arrPath[i])) //если есть то шагаем вглубь
                    {
                        buildBranch(arrPath, item ,++i);
                        break finish;
                    }
                }
                makeBranch(arrPath[i],root); // если нету такой папки, то добавляем и шагаем вглубь
                buildBranch(arrPath, root,i);
                break finish;
            } else {
                makeBranch(arrPath[i], root); // создание первого элемента в директории
            }
        }
    }*/
