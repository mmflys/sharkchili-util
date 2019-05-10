package com.shark.util.util.convert;

import com.google.common.collect.Lists;
import com.shark.util.util.reflex.ClassManager;
import com.shark.util.util.reflex.ClassMetadata;
import com.shark.util.util.sheet.Cell;
import com.shark.util.util.sheet.HashSheet;
import com.shark.util.util.sheet.Sheet;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Convert {@link Sheet} to {@link Object} and convert {@link Object} to {@link Sheet}
 *
 * @param <T> Type of Object
 */
public class SheetToObj<T> implements Converter<Sheet, T> {

    @Override
    public List<T> convert(Sheet sheet, T t) {
        List<T> objects = Lists.newArrayList();
        // Get all field
        Class c = t.getClass();
        ClassManager.saveClassInfo(t.getClass());
        Map<String, Field> fieldMap = ClassManager.getClassInfo(c).getFieldMap();
        // Set value
        for (List<Cell> cells : sheet.allData()) {
            for (Cell cell : cells) {
                try {
                    if (cell.getRowIndex() == 1) continue;

                    T object = (T) c.newInstance();
                    Object fieldValue = cell.getValue();
                    String fieldName = sheet.cell(0, cell.getColumnIndex()).getValue().toString();
                    Field field = fieldMap.get(fieldName);
                    // Set value
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

    @Override
    public Sheet convert(T... ts) {
        Class c = ts.getClass();
        ClassMetadata metadata = ClassManager.getClassInfo(c);
        // Sheet
        Sheet sheet = new HashSheet();
        List<Field> fields = Lists.newArrayList(metadata.getFields());
        for (int columnIndex = 0; columnIndex < fields.size(); columnIndex++) {
            Field field = fields.get(columnIndex);
            String fieldName = field.getName();
            sheet.appendColumn(fieldName);
            // Add cell
            for (int rowIndex = 0; rowIndex < ts.length; rowIndex++) {
                T t = ts[rowIndex];
                try {
                    Object fieldValue = field.get(t);
                    sheet.addCell(new Cell(rowIndex, columnIndex, fieldValue));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return sheet;
    }
}
