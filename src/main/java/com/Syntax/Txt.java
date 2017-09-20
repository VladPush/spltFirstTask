package com.Syntax;

public class Txt implements Syntax  {

    public boolean status;

    @Override
    public String check(String input) {
        if (!(input.trim().isEmpty())) {
             status = true;
        }else{
             status = false;
        }
        return getMessage();
    }

    @Override
    public String correct(String input) {

        return input.trim();
    }

    @Override
    public String getMessage() {
        if (status){
            return "Correct text input.";
        }else{
            return "Введите текст для поиска.";
        }
    }
}