package com.FilesAndEntries;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class FileFinder {

    public static String foolExtension;
    public static String foolDirName;
    public  ArrayList<Path> addressOfEntry= new ArrayList<>();
    String rootDirname;
    boolean haveExtension;
    int index;

    public CheckSyntax checkSyntax = new CheckSyntax();

    public void FindTheEntries(Boolean typeOfDir) {
        try{
            int strategy = checkSyntax.checkSyntaxOfInput();
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

        System.out.println("Корневая директория: "+ rootDirname);

        if (!addressOfEntry.isEmpty()){
            System.out.println("Адреса файлов со вхождениями:");
            for(Path s: addressOfEntry){
                System.out.println(s.toString().substring(index));
            }
        }else{
            if (haveExtension) {
            System.out.println("В файлах не найдено вхождений искомой строки.");
            }else{
            System.out.println("Файлов удовлетворяющим условию поиска не найдено.");
            }
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
            File file = path.toFile();
            checkExtension(file);
            return FileVisitResult.CONTINUE;
        }
    }

    private void walkNetworkFileTree(File curDir) throws IOException {

        File[] filesList = curDir.listFiles();
        try{
            for(File file : filesList){
                if(file.isDirectory())
                    walkNetworkFileTree(new File(file.toURI()));
                if(file.isFile()){
                    checkExtension(file);
                }
            }
        }catch (NullPointerException e){
            System.out.println("Директория пуста.");
        }
    }

    /*Если файл с нужным расширением начинаем поиск по содержимому*/
    private void checkExtension(File file) throws IOException{

        /*Находим индекс и имя корневой директории*/
        index = file.toPath().toString().indexOf( "\\", 3);
        rootDirname = file.toPath().toString().substring(0,index);

        if (new File(file.toURI()).getName().endsWith(foolExtension)){
            haveExtension=true;
            if (new FileReader().readFile(file.toPath())) {
                addressOfEntry.add(file.toPath());
            }
        }
    }
}
