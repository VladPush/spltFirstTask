package com.Syntax;

public class Dir implements Syntax {

    boolean status;

    @Override
    public boolean check(String input) {
        if (input.trim().matches("^([A-Za-z]://([\\wа-яА-Я]{1,50}/{0,1}){1,25})") |  input.trim().matches("^(//([\\wа-яА-Я]{1,50}/{0,1}){1,25})") |
           input.trim().matches("^(([\\wа-яА-Я]{1,50}/{0,1}){1,25})")) {
            return status=true;
        }else{
            return status=false;
        }
    }


    @Override
    public String getMessage() {
        return "Некорректная директория поиска.\nНапр. //SERVER/folder | D://root/folder";
    }

    @Override
    public String correct(String input) {


        if (input.trim().matches("^(([\\wа-яА-Я]{1,50}/{0,1}){1,25})")) {
            return "C://" + input.trim();
        } else {
            return input.trim();}

    }


}


