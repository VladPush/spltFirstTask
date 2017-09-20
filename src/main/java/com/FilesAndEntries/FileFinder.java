package com.FilesAndEntries;

import java.io.File;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class FileFinder {

    public  String foolExtension;
    public  String foolDirName;
    public  ArrayList<Path> addressOfEntry;
    public String rootDirname;
    int index;


    public ArrayList<Path> FindTheEntries(Boolean typeOfDir) throws Exception{
        addressOfEntry = new ArrayList<>();
        if (typeOfDir) {
            walkFileTree();
        } else {
            walkNetworkFileTree(new File(foolDirName)); }
        SortByLength(addressOfEntry);
        return addressOfEntry;
    }

    private void walkFileTree() throws Exception
    {
        Files.walkFileTree(Paths.get(foolDirName), new MyFileVisitor());
    }


    /*Специальная версия класса SimpleFileVisitor,  в которой переопределяется метод visitFile()*/
    private  class  MyFileVisitor extends SimpleFileVisitor<Path>
    {
        public FileVisitResult visitFile(Path path, BasicFileAttributes attributes)  {

            File file = path.toFile();
            try {
                checkExtension(file);
            } catch (Exception e) {
                e.printStackTrace(); }

            return FileVisitResult.CONTINUE;
        }
    }


    private void walkNetworkFileTree(File curDir) throws Exception {

        File[] filesList = curDir.listFiles();
        for(File file : filesList){
            if(file.isDirectory())
                walkNetworkFileTree(new File(file.toURI()));
            if(file.isFile()){
                checkExtension(file);
            }
        }

    }


    /*Если файл с нужным расширением начинаем поиск по содержимому*/
    private void checkExtension(File file) throws Exception{

        /*Находим индекс и имя корневой директории*/
        index = file.toPath().toString().indexOf( "\\", 3);
        rootDirname = file.toPath().toString().substring(0,index);

        if (new File(file.toURI()).getName().endsWith(foolExtension)){
            if (new FileReader().readFile(file.toPath(),foolExtension)) {
                addressOfEntry.add(file.toPath());
            }
        }
    }


    /*Сортировка массива Path по длине*/
    private void SortByLength(ArrayList tempList) {
        for(int i=0;i<tempList.size();i++) {
            for(int j=0;j<tempList.size();j++) {
                if(tempList.get(i).toString().length() > tempList.get(j).toString().length()) {
                    Path temp=(Path)tempList.get(i);
                    tempList.set(i, tempList.get(j));
                    tempList.set(j,temp);
                }
            }
        }
    }
}
