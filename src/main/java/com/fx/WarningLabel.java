package com.fx;

public class WarningLabel {

    public boolean extIsNotFine;
    public boolean dirIsNotFine;
    public boolean textIsNotFine;


    public void setExtIsNotFine(boolean extIsNotFine) {
        this.extIsNotFine = extIsNotFine;
    }

    public void setDirIsNotFine(boolean dirIsNotFine) {

        this.dirIsNotFine = dirIsNotFine;
    }

    public void setTextIsNotFine(boolean textIsNotFine) {

        this.textIsNotFine = textIsNotFine;
    }

    public String showMessageIncorrectInter(){
        String message=null;

        if (extIsNotFine){
            message+="Некорректное расширение. (Пример ввода txt)\n";
        }
        if (dirIsNotFine){
            message+="Некорректная директория поиска. (Пример ввода //SERVER/folder root/folder)\n";
        }
        if (textIsNotFine){
            message+="Введите текст для поиска.\n";
        }


        return message;
    }

    public String showMessageEmptySearch(){
     return "Поиск не нашел вхождений." ;
    }

}
