package com.Syntax;

public class Ext implements Syntax  {

    public  boolean status;

    @Override
    public String check(String input) {
        input=input.trim();
        if (input.matches("[a-z]{1,50}") | input.isEmpty()) {
             status = true;
        }else{
             status = false;
        }

        return getMessage();
    }

    @Override
    public String correct(String input) {
        input=input.trim();
        if (input.isEmpty()){
            return ".log";}
        else{
            return "." + input;}
    }

    @Override
    public String getMessage() {
        if (status){
            return "Def. .log";
        }else{
            return "Incorrect extension.\nE. g. txt | log";
        }

    }
}
