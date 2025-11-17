package com.base.common.util;
/**
 * @author : AnhNT
 * @since : 11/10/2022, Thu
 */
public class Util {
    public static boolean validate(Object... args) {
        for (Object arg : args) {
            if (!validate(arg)) {
                return false;
            }
        }
        return true;
    }

    public static boolean validate(Object arg) {
        return arg != null;
    }

    //check if all args is not null and not empty
    public static boolean validateString(String... args) {
        for (String arg : args) {
            if (!validateString(arg)) {
                return false;
            }
        }
        return true;
    }

    public static boolean validateString(String arg) {
        return arg != null && !arg.trim().isEmpty();
    }

}
