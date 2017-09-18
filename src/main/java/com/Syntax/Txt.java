package com.Syntax;

public class Txt implements Syntax  {

    @Override
    public boolean check(String input) {
        return false;
    }

    @Override
    public String correct(String input) {
        return null;
    }

    @Override
    public String getMessage() {
        return "Введите текст для поиска.";
    }
}