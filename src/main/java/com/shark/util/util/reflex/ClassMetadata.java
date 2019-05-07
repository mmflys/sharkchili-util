package com.shark.util.util.reflex;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

public class ClassMetadata {
    private Class c;
    private Map<String, Field> fieldMap;
    private Map<String, Method> methodMap;

    public ClassMetadata(Class c) {
        this.c = c;
        init();
    }

    public void init() {
        // Save field
        for (Field field : c.getDeclaredFields()) {
            fieldMap.put(field.getName(), field);
        }
        // Save method
        for (Method method : c.getDeclaredMethods()) {
            methodMap.put(method.getName(), method);
        }
    }

    public Field getField(String name) {
        return fieldMap.get(name);
    }

    public Method getMethod(String name) {
        return methodMap.get(name);
    }

    public Collection<Field> getFields() {
        return fieldMap.values();
    }

    public Collection<Method> getMethods() {
        return methodMap.values();
    }

    public Class getType() {
        return this.c;
    }

    public Map<String, Field> getFieldMap() {
        return fieldMap;
    }

    public Map<String, Method> getMethodMap() {
        return methodMap;
    }
}
