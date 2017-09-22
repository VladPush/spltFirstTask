package com.Syntax;

public class Dir implements Syntax {

    public boolean status;
    public boolean typeOfDir; // 0 - server  pc 1 - this pc
    @Override
    public String check(String input) {
        input=input.trim();
        if (input.matches("^([A-Za-z]:/([\\wа-яА-Я]{1,50}/{0,1}){1,25}[/]{0,1})") | input.matches("^(//([\\wа-яА-Я]{1,50}/{0,1}){1,25})") |
                input.matches("^(([\\wа-яА-Я]{1,50}/{0,1}){1,25}[/]{0,1})")) {
            status = true;
            if (input.matches("^(//([\\wа-яА-Я]{1,50}/{0,1}){1,25})")) {
                typeOfDir = false;
            } else {
                typeOfDir = true;
            }
        } else {
            status = false;
        }
        return getMessage();
    }

    @Override
    public String correct(String input) {
    input=input.trim();
        if (input.matches("^(([\\wа-яА-Я]{1,50}/{0,1}){1,25})")) {
            return "C:/" + input;
        } else {
            return input;
        }
    }

    @Override
    public String getMessage() {
        if (status){
            return null;
        }else{
            return "Incorrect direction.\nE. g. //SERVER/folder | D:/root/folder/";
        }
    }

}


