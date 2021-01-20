package com.example.demo.util;

import org.springframework.stereotype.Component;

@Component
public class StringUtil {
    public String removeBrackets(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("[", "").replace("]", "");
    }
}
