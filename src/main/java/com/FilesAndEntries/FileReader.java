package com.FilesAndEntries;

import java.io.*;
import java.nio.file.Path;

public class FileReader {

    public static String stringFromUser;
    String[] findText =  strToArrOfStr(stringFromUser);
    Integer findTextLenght = findText.length-1;

    public boolean readFile(Path path) throws IOException {

        try (BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream(path.toFile()), "windows-1251"))) {  // НАСТРОИТЬ буфер
            //  String encoding = new InputStreamReader(new FileInputStream(path.toFile())).getEncoding(); //такой подход почему то помещает в line неверную информацию на кириллице
            Integer q = 0;
            String line;
            while ((line = buff.readLine()) != null) {
                String[] s = strToArrOfStr(line);
                for (int j = 0; j < s.length; j++) {
                    if (s[j].equals(findText[q])) {
                        if (q == findTextLenght) {
                            return true;
                        }
                        q++;
                    } else {
                        q = 0;
                    }
                }
            }
        }
        return false;
    }

    private String[] strToArrOfStr(String string){
        return string.trim().split("[ ]+");
    }
}



/*while (!eof) {
                    if ( buff.read() == -1){
                        eof = true;
                    }else{
                        buff.read(buffer, 0, BUFFER_SIZE);
                        for (char s:buffer) {
                        bufferFinish[k++]= s;
                        }
                    }
                }*/
