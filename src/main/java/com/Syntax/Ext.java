package com.Syntax;

public class Ext implements Syntax  {

    public  boolean status;

    @Override
    public String check(String input) {
        if (input.trim().matches("[a-z]{1,50}") | input.trim().isEmpty()) {
             status = true;
        }else{
             status = false;
        }

        return getMessage();
    }

    @Override
    public String correct(String input) {
        if (input.trim().isEmpty()){
            return ".log";}
        else{
            return "." + input.trim();}
    }

    @Override
    public String getMessage() {
        if (status){
            return "Correct extension input. (Def. .log)";
        }else{
            return "Некорректное расширение.\nПрим. txt | log";
        }

    }
}
