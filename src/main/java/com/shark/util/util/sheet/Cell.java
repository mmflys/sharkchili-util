package com.shark.util.util.sheet;

public class Cell {

    private int rowIndex;

    private int columnIndex;

    private Object value;

    public Cell() {
    }

    public Cell(int rowIndex, int columnIndex, Object value) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.value = value;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public Cell setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
        return this;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public Cell setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
        return this;
    }

    public Object getValue() {
        return value;
    }

    public Cell setValue(Object value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "rowIndex=" + rowIndex +
                ", columnIndex=" + columnIndex +
                ", value=" + value +
                '}';
    }
}
