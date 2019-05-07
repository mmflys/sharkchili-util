package com.shark.util.util.convert;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.shark.util.util.reflex.ClassManager;
import com.shark.util.util.sheet.Cell;
import com.shark.util.util.sheet.Sheet;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class SheetToObj<T> implements Converter<Sheet, T> {

    @Override
    public List<T> convert(Sheet sheet, T t) {
        List<T> objects = Lists.newArrayList();
        // Get all field
        Class c = t.getClass();
        ClassManager.saveClassInfo(t.getClass());
        Map<String, Field> fieldMap = ClassManager.getClassInfo(c).getFieldMap();
        // Set value
        for (List<Cell> cells : sheet.allRow()) {
            for (Cell cell : cells) {
                try {
                    if (cell.getRowIndex() == 1) continue;

                    T object = (T) c.newInstance();
                    Object fieldValue = cell.getValue();
                    String fieldName = sheet.cell(0, cell.getColumnIndex()).getValue().toString();
                    Field field = fieldMap.get(fieldName);

                    if (field != null) {
                        field.setAccessible(true);
                        field.set(object, fieldValue);
                    }
                    objects.add(object);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return objects;
    }
}
