package com.FilesAndEntries;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

import static com.FilesAndEntries.CheckSyntax.checkSyntaxOfInput;

public class FileFinder {
    public static String foolExtension;
    public static String foolDirName;
    public static  ArrayList<Path> addressOfEntry= new ArrayList<>(); //

    public void FindTheEntries( ) {
            try{
                int strategy = checkSyntaxOfInput();
                if (strategy == 1 | strategy == 3)
                {
                    walkFileTree();
                }
                else if (strategy == 2)
                {
                    walkNetworkFileTree(new File(foolDirName));
                }
            }catch (IOException e){
                System.out.printf("Системная ошибка: "+ e.getMessage());
            }

        System.out.println("\n");
            for(Path s: addressOfEntry){
                System.out.println(s.toString());
            }
    }


    private void walkFileTree() throws IOException
    {
        Files.walkFileTree(Paths.get(foolDirName), new MyFileVisitor());
    }

    /*Специальная версия класса SimpleFileVisitor,  в которой переопределяется метод visitFile()*/
    private  class  MyFileVisitor extends SimpleFileVisitor<Path>
    {
        public FileVisitResult visitFile(Path path, BasicFileAttributes attributes) throws IOException
        {

            /*Если файл с нужным расширением начинаем поиск по содержимому*/
            if (new File(path.toUri()).getName().endsWith(foolExtension))
            {
                boolean IsEntry = new FileReader().readFile(path);
                if (IsEntry){
                addressOfEntry.add(path);
                }
            }

            return FileVisitResult.CONTINUE;
        }
    }

    private void walkNetworkFileTree(File curDir) throws IOException {

        File[] filesList = curDir.listFiles();
        for(File f : filesList){
            if(f.isDirectory())
                walkNetworkFileTree(new File(f.toURI()));
            if(f.isFile()){

                if (new File(f.toURI()).getName().endsWith(foolExtension)){
                    boolean IsEntry = new FileReader().readFile(f.toPath());
                    if (IsEntry){
                        addressOfEntry.add(f.toPath());
                    }
                }
            }
        }
    }




}
