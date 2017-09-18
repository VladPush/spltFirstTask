package com.fx;

import com.Syntax.Dir;
import com.Syntax.Syntax;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Controller  {

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

    public ArrayList<Path> address = new ArrayList<>();
    WarningLabel warningLabelclass = new WarningLabel();


    public void initialize() {
        Dir dir = new Dir();

        textfieldDir.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) {
                /*Проверка правописания*/
                if (dir.check(textfieldDir.getText())){
                    warningLabel.setText("OK");
                }else{
                    warningLabel.setText(dir.getMessage());
                }
            }
        });

        textfieldText.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) {
                /*Проверка правописания*/
                if (true){
                    warningLabelclass.setTextIsNotFine(true);
                }else{
                    warningLabelclass.setTextIsNotFine(false);
                }
            }
        });

        textfieldExt.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) {
                /*Проверка правописания*/
                if (true){
                    warningLabelclass.setExtIsNotFine(true);

                }else{
                    warningLabelclass.setExtIsNotFine(false);
                }

            }
        });


    }

    /*Поиск и построение дерева по кнопке*/
    public void trySearch(ActionEvent actionEvent) {

        /*Path path1 = Paths.get("files/files1/text/text1.txt");
        Path path2 = Paths.get("filesD/files2/text2.txt");
        Path path3 = Paths.get("files/files2/text/necopy/text3.txt");
        Path path4 = Paths.get("files/files3/text/text4.txt");
        Path path5 = Paths.get("files2/text/text5.txt");
        Path path6 = Paths.get("files/files3/text/text6/text6.txt");
        Path path7 = Paths.get("AC.txt");
        Path path8 = Paths.get("fileX/filesXX/textXXX/text8.txt");
        Path path9 = Paths.get("files/Dfiles4/text/text6/text7.txt");
        Path path0 = Paths.get("files/files2/text/copy/copy/text3.txt");
        Path pathp = Paths.get("files/files2/text/textTTT.txt");
        Path patho = Paths.get("AB.txt");


        address.add(path6);
        address.add(path8);
        address.add(pathp);
        address.add(path2);
        address.add(path5);
        address.add(path7);
        address.add(patho);
        address.add(path2);
        address.add(path5);
        address.add(path1);
        address.add(path3);
        address.add(path4);
        address.add(path9);
        address.add(path0);*/


        try {
            /*поиск*/
            getTree(address);
        } catch (MalformedURLException | URISyntaxException e) {
            e.printStackTrace();
        }
    }


    /*Построение дерева вниз */
    public TreeItem<String> makeBranch(String text, TreeItem<String> parent) {
        TreeItem<String> item = new TreeItem<>(text);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }


    /*Восстановление path из клика по элементу treeView */
    public void buildPath(ContextMenuEvent contextMenuEvent) {
        StringBuilder pathBuilder = new StringBuilder();
        if (tree.getSelectionModel().getSelectedItem().getChildren().isEmpty()) {
            for (TreeItem<String> item = tree.getSelectionModel().getSelectedItem(); item.getValue() != null; item = item.getParent()) {
                pathBuilder.insert(0, item.getValue());
                pathBuilder.insert(0, "/");
            }
        } else {
            System.out.println("Это директория... Выпускайте Кракена!");
        }

        Path path = Paths.get(pathBuilder.toString());


        System.out.println(path);
    }



    public void getTree(ArrayList<Path> pathArray) throws MalformedURLException, URISyntaxException {
        TreeItem<String> root = new TreeItem<>();
        SortByLenght(pathArray);
        for (Path s : pathArray) {
            int i=0;
            buildBranch(s, root, i);
        }

        tree.setRoot(root);
        tree.setShowRoot(false);
        root.setExpanded(true);
       if (root.getChildren().isEmpty()){
           warningLabel.setText(warningLabelclass.showMessageEmptySearch());
       }
    }

    /*Строим Tree по массиву Path*/
    public void buildBranch(Path path, TreeItem<String> root, int i) {

        stopCountPath:
        while (i<path.getNameCount()){
            if (!(root.getChildren().isEmpty())) { //если рута не пустая то заходим в неё
                for (TreeItem<String> item : root.getChildren()){ // начинаем обходить директорию
                    if (item.getValue().toString().equals(path.subpath(i,i+1).toString())) //если есть уже такой элемент то шагаем вглубь
                    {
                        buildBranch(path, item ,++i);
                        break stopCountPath;
                    }
                }
            }
            makeBranch(path.subpath(i,i+1).toString(), root); // создание элемента в директории
        }
    }


    /*Сортировка массива Path по длине*/
    private ArrayList SortByLenght(ArrayList tempList) {
        for(int i=0;i<tempList.size();i++)
        {
            for(int j=0;j<tempList.size();j++)
            {
                Path temp;
                if(tempList.get(i).toString().length() > tempList.get(j).toString().length())
                {
                    temp=(Path)tempList.get(i);
                    tempList.set(i, tempList.get(j));
                    tempList.set(j,temp);
                }
            }
        }
        return tempList;
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
