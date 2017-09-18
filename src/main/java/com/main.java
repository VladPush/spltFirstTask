package com;

import com.FilesAndEntries.*;
import com.FilesAndEntries.FileReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));//обязательно   /

        Scene scene = new Scene(root);//в себе заключает дерево компонентов 600, 600);
        scene.getStylesheets().add(0, "style.css");
        primaryStage.setTitle("Text finder");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String args[]) {

        launch(args);


        FileFinder fileFinder = new FileFinder();

        FileReader.stringFromUser =  "Это город Москва.";

        fileFinder.checkSyntax.dirName = "C://files";           //  C://files              //         //NASTYADELL/filesD
        fileFinder.checkSyntax.extension = "txt";

        fileFinder.FindTheEntries();
    }
}

//кодировка когда word - слетает.
//русские символы в названиях файлов и пробелы проверить
//сообщения централизовать

/*
long start = System.currentTimeMillis();
            System.out.println(System.currentTimeMillis() - start);
* */

          /*  InputStream in=null;
            FileOutputStream fOut=null;

            try{
                URL remoteFile=new URL("file:////NASTYADELL//files//ttr.txt");

                File file = new File(remoteFile.toURI());
                if(file.exists()){
                    System.out.println("true");}
                else
                {
                    System.out.println("false");
                }

                URLConnection fileStream=remoteFile.openConnection();

                System.out.println(fileStream.getContentType());

                // Open output and input streams
                fOut=new FileOutputStream("xxx.txt");

                in=fileStream.getInputStream();

                // Save
                int data;
                while((data=in.read())!=-1){
                    fOut.write(data);
                }

            } catch (Exception e){
                e.printStackTrace();
            } finally{
                try{
                    in.close();
                    fOut.close();
                } catch(Exception e){e.printStackTrace();}
            }*/