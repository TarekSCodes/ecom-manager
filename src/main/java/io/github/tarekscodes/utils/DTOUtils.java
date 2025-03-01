package io.github.tarekscodes.utils;

public class DTOUtils {
    
    private  DTOUtils() {

    }

    public static String sanitizeString(String value) {

        return (value == null || "null".equals(value)) ? "" : value;
    }
}
