package com.FilesAndEntries;

public class CheckSyntax {

    public   String extension;
    public  String dirName;

         Integer checkSyntaxOfInput(){

        if (extension.trim().matches("[a-z]{1,50}") | extension.trim().isEmpty())
        {
            if (extension.trim().isEmpty()){
                FileFinder.foolExtension=".log";}
            else{
                FileFinder.foolExtension ="."+ extension.trim();}

            if (!FileReader.stringFromUser.isEmpty()) {
                if (dirName.trim().matches("^([A-Za-z]://([\\wа-яА-Я]{1,50}/{0,1}){1,25})")) {
                    FileFinder.foolDirName = dirName.trim();
                    return 1;
                } else if (dirName.trim().matches("^(//([\\wа-яА-Я]{1,50}/{0,1}){1,25})")) {
                    FileFinder.foolDirName = dirName.trim();
                    return 2;
                } else if (dirName.trim().matches("^(([\\wа-яА-Я]{1,50}/{0,1}){1,25})")) {
                    FileFinder.foolDirName = "C://" + dirName.trim();
                    return 3;
                } else {
                    System.out.println("Ошибка ввода директории поиска.");
                    return 0;
                }

            }
            else {
                System.out.println("Ошибка ввода строки для поиска.");
                return 0;
            }
        }
        else
        {
            System.out.println("Ошибка ввода расширения.");
            return 0;
        }
    }
}
