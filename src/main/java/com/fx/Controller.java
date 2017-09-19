package com.fx;

import com.FilesAndEntries.FileFinder;
import com.FilesAndEntries.FileReader;
import com.Syntax.Dir;
import com.Syntax.Ext;
import com.Syntax.Syntax;
import com.Syntax.Txt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Controller  extends  treeViewHandler {

    public ArrayList<Path> address = new ArrayList<>();

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

    Dir dir = new Dir();
    Ext ext = new Ext();
    Txt txt = new Txt();

    public void initialize() {

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
    public void ButtonActive(ActionEvent actionEvent) {

        if (txt.status && dir.status && ext.status){
            FileFinder fileFinder = new FileFinder();
            FileReader.stringFromUser =  textfieldText.getText() ; // "Это город Москва."
            fileFinder.checkSyntax.dirName =textfieldDir.getText();           //  C://files              //         //NASTYADELL/filesD
            fileFinder.checkSyntax.extension = textfieldExt.getText();

            fileFinder.FindTheEntries(dir.typeOfDir);
            address = fileFinder.addressOfEntry;
        }else{
            warningLabel.setText("Incorrect input.");
        }

        /*поиск*/
        TreeItem<String> root = new TreeItem<>();
        root.setExpanded(true);

        SortByLenght(address);
        for (Path s : address) {
            int i=0;
            buildBranch(s, root, i);
        }

        tree.setRoot(root);
        if (root.getChildren().isEmpty()){
            warningLabel.setText("Вхождений не найдено.");
        }
    }



    /*Восстановление path из клика по элементу treeView  !контекстное меню на treeItem!*/
    public void ContextMenuEvent(ContextMenuEvent contextMenuEvent) {
        System.out.println(returnPath(tree));
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
