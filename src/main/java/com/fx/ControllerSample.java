package com.fx;

import com.FilesAndEntries.FileFinder;
import com.FilesAndEntries.FileReader;
import com.Syntax.*;
import com.sun.org.apache.xerces.internal.xs.StringList;
import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.any23.encoding.TikaEncodingDetector;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import javax.swing.text.Position;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ControllerSample extends  treeViewHandler {

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
                warningLabel.setText(dir.check(textfieldDir.getText())); }
        });

        textfieldText.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) {
                warningLabel.setText(txt.check(textfieldText.getText())); }
        });

        textfieldExt.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) {
                warningLabel.setText(ext.check(textfieldExt.getText())); }
        });

        tree.setShowRoot(false);
    }

    /*Поиск и построение дерева по архиву Path !кнопка!*/
    public void Clicked(MouseEvent mouseEvent)  {
        TreeItem<String> root = new TreeItem<>();
        if (txt.status && dir.status && ext.status){

            warningLabel.setTextFill(Color.BLACK);

            FileReader.stringFromUser = txt.correct(textfieldText.getText()); // "Это город Москва."
            fileFinder.foolDirName    = dir.correct(textfieldDir.getText());  //  C://files              //         //NASTYADELL/filesD
            fileFinder.foolExtension  = ext.correct(textfieldExt.getText());

            /*Обход директории и поиск вхождений*/
            try {
                for (Path s : fileFinder.FindTheEntries(dir.typeOfDir)){
                    int i=1;
                    buildBranch(s, root, i);
                }
                if (root.isLeaf()) {
                    warningLabel.setText("No entries");
                }else{
                    warningLabel.setText("Done.");
                }
            }catch (NoSuchFieldException | IOException | InvocationTargetException e){
                warningLabel.setText("Directory is missing: " + e.getMessage());
            }
        }else{
            warningLabel.setTextFill(Color.RED);
        }

        root.setExpanded(true);
        tree.setRoot(root);
    }

    /*Восстановление path из клика по элементу treeView  !контекстное меню на treeItem!*/
    public void ContextMenuEvent(ContextMenuEvent contextMenuEvent) {

        /*возвращаем файл по Path который дает returnPath, помещаем его в поток,поток помещаем в символьный поток, буферизируем.*/
        try (BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream(returnPath(tree,fileFinder.rootDirname).toFile()), "windows-1251"))) {// НАСТРОИТЬ буфер

            TextArea textArea = new TextArea();
            Tab tab = new Tab();
            tab.setText(returnPath(tree,fileFinder.rootDirname).getFileName().toString());
            String line;

            while ((line = buff.readLine()) != null) {
                textArea.appendText(line);
                System.out.println(line);
                if (!(line.trim().isEmpty())){
                    textArea.appendText("\n");
                }
            }
            tab.setContent(textArea);
            tabPane.getTabs().add(tab);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        try {
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
        }


    }

    public static Charset guessCharset(InputStream is) throws IOException {
        return Charset.forName(new TikaEncodingDetector().guessEncoding(is));
    }

}


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
