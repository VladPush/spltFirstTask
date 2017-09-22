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

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class ControllerSample extends treeViewHandler {

    private FileFinder fileFinder = new FileFinder();

    @FXML()
    public TreeView<String> tree;

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
        tree.setTooltip(new Tooltip("To open file click RMB."));
        tree.setShowRoot(false);
        button.setDisable(true);
    }

    /*Поиск и построение дерева по архиву Path !кнопка!*/
    public void buildTree(MouseEvent mouseEvent)  {
        TreeItem<String> root = new TreeItem<>();
            /*Обход директории и поиск вхождений*/
            try {
                for (Path s : fileFinder.FindTheEntries(dir.typeOfDir)){
                    int i;
                    if (dir.typeOfDir) {
                         i = 1;
                    }else{
                         i = 0;}
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
    public void ContextMenu(ContextMenuEvent contextMenuEvent) {
        Path filePath = returnPath(tree, fileFinder.rootDirname);
        String fileName = filePath.getFileName().toString();
        File file = filePath.toFile();

        if (file.isFile()) {
            boolean allowOpen = true;
            for (Tab tab : tabPane.getTabs()) {
                if (tab.getText().equals(fileName)) {
                    allowOpen = false;
                    break;
                }
            }
            if (allowOpen) {
                TextArea textArea = null;
                Tab tab = new Tab();
                tab.setText(fileName);
                try {
                    textArea = new ReadToTab().moderator(file);
                } catch (IOException | InvalidFormatException e) {
                    warningLabel.setText("Error: " + e.getMessage());
                }

                textArea.setWrapText(true);
                tab.setContent(textArea);
                tabPane.getTabs().add(tab);
            } else {
                warningLabel.setText("Already open!");
            }
        } else {
            warningLabel.setText("It's a directory!");
        }
    }

    private void letButtonWork(){
        if (ext.status && dir.status && txt.status)
            button.setDisable(false);
        else
            button.setDisable(true);
    }

}