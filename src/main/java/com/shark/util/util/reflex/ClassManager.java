package com.shark.util.util.reflex;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Provide global metadata of Class
 */
public class ClassManager {

    private static Map<Class, ClassMetadata> METADATA;

    public static void saveClassInfo(Class... classes) {
        checkNull();
        for (Class aClass : classes) {
            METADATA.put(aClass, new ClassMetadata(aClass));
        }
    }

    public static ClassMetadata getClassInfo(Class c) {
        checkNull();
        ClassMetadata metadata = METADATA.get(c);
        if (metadata == null) {
            saveClassInfo(c);
            metadata = METADATA.get(c);
        }
        return metadata;
    }

    private static void checkNull() {
        if (METADATA == null) {
            METADATA = Maps.newHashMap();
        }
    }

}
