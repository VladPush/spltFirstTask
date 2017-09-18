package com.Syntax;

public class Ext implements Syntax  {

    boolean status;

    @Override
    public boolean check(String input) {
        if (input.trim().matches("[a-z]{1,50}") | input.trim().isEmpty()) {
            return status = true;
        }else{
            return status = false;
        }
    }

    @Override
    public String correct(String input) {
        String result;
        if (input.trim().isEmpty()){
            return ".log";}
        else{
            return "."+ input.trim();}
    }

    @Override
    public String getMessage() {
        return "Некорректное расширение.\nПрим. txt | log";
    }
}
