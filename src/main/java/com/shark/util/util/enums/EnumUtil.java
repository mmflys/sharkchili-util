package com.shark.util.util.enums;

import com.shark.util.util.enums.Status;
import com.shark.util.util.enums.StatusCode;

public class EnumUtil {
    /**
     * Get enum instance inherit from {@link Status} by field(name) value
     * @param enumC class of enum
     * @param name name
     * @param <T> enum
     * @return A instance of enum
     */
    public static  <T extends Status> T getEnumByName(Class<T> enumC, String name){
        for (T t : enumC.getEnumConstants()) {
            if (t.getName().equals(name)){
                return t;
            }
        }
        return null;
    }

    /**
     * Get enum instance  inherit from {@link StatusCode} by code value
     * @param enumC class of enum
     * @param code int value mapping to special enum instance
     * @param <T> enum
     * @return A instance of enum
     */
    public static <T extends StatusCode> T getEnumByCode(Class<T> enumC, int code){
        for (T t : enumC.getEnumConstants()) {
            if (t.getCode()==code){
                return t;
            }
        }
        return null;
    }

}
