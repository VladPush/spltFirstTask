package com.fx;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class treeViewHandler {

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


    /*Создание ветви дерева*/
    public  TreeItem<String> makeBranch(String text, TreeItem<String> parent) {
        TreeItem<String> item = new TreeItem<>(text);
        item.setExpanded(true);
        parent.getChildren().add(item);
        return item;
    }


    /*Восстановление path из клика по элементу treeView */
    public Path returnPath(TreeView<String> treeview, String rootDir){
        StringBuilder pathBuilder = new StringBuilder();

        if (treeview.getSelectionModel().getSelectedItem().getChildren().isEmpty()) {
            for (TreeItem<String> item = treeview.getSelectionModel().getSelectedItem(); item.getValue() != null; item = item.getParent()) {
                pathBuilder.insert(0, item.getValue());
                pathBuilder.insert(0, "/");
            }
            pathBuilder.insert(0,rootDir);
        } else {
            System.out.println("Это директория... Выпускайте Кракена!");
        }
        return Paths.get(pathBuilder.toString());
    }
}
