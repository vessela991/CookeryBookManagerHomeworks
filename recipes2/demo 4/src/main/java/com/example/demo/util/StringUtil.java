package com.example.demo.util;

public class StringUtil {
    public String StringRemoveBrackets(String str) {
        if (str == null) {
            return "";
        }
        String r1 = str.replace("[", "");
        return r1.replace("]", "");
    }
}
