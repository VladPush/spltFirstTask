package com.Syntax;

public class Dir implements Syntax {

    public boolean status;
    public boolean typeOfDir; // 0 - this pc 1 - server pc
    @Override
    public String check(String input) {
        if (input.trim().matches("^([A-Za-z]:/([\\wа-яА-Я]{1,50}/{0,1}){1,25})") |  input.trim().matches("^(//([\\wа-яА-Я]{1,50}/{0,1}){1,25})") |
           input.trim().matches("^(([\\wа-яА-Я]{1,50}/{0,1}){1,25})")) {
            status=true;
            if (input.trim().matches("^(//([\\wа-яА-Я]{1,50}/{0,1}){1,25})")){
                typeOfDir=false;
            }else{
                typeOfDir=true;
            }
        }else{
            status=false;
        }
        return getMessage();
    }

    @Override
    public String correct(String input) {

        if (input.trim().matches("^(([\\wа-яА-Я]{1,50}/{0,1}){1,25})")) {
            return "C:/" + input.trim();
        } else {
            return input.trim();}
    }

    @Override
    public String getMessage() {
        if (status){
            return "Correct directory input.";
        }else{
            return "Некорректная директория поиска.\nНапр. //SERVER/folder | D:/root/folder";
        }

    }




}


