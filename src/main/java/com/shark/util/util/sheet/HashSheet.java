package com.shark.util.util.sheet;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;

import java.util.Collection;
import java.util.List;

public class HashSheet extends AbstractSheet {

    private Table<Integer, Integer, Cell> data;
    private int rowTailIndex;
    private int columnTailIndex;

    public HashSheet() {
        init();
    }

    public HashSheet(String... columnNames) {
        init();
        this.appendColumn(columnNames);
    }

    public void init() {
        data = HashBasedTable.create();
        rowTailIndex = -1;
        columnTailIndex = -1;
    }

    @Override
    public Sheet appendRow(Object... values) {
        for (int columnIndex = 0; columnIndex < values.length; columnIndex++) {
            if (columnIndex > columnTailIndex) break;

            Object value = values[columnIndex];
            data.put(rowTailIndex + 1, columnIndex, new Cell(rowTailIndex + 1, columnIndex, value));
            rowTailIndex++;
        }
        return this;
    }

    @Override
    public Sheet appendColumn(String... names) {
        for (String name : names) {
            data.put(0, columnTailIndex + 1, new Cell(0, columnTailIndex + 1, name));
            columnTailIndex++;
        }
        return this;
    }

    @Override
    public Sheet insertRow(int index, Object... values) {
        return this;
    }

    @Override
    public Sheet insertBeforeRow(int index, Object... values) {
        return this;
    }

    @Override
    public Sheet insertAfterRow(int index, Object... values) {
        return this;
    }

    @Override
    public Sheet insertColumn(int index, String... names) {
        return this;
    }

    @Override
    public Sheet insertBeforeColumn(int index, String... names) {
        return this;
    }

    @Override
    public Sheet insertAfterColumn(int index, String... names) {
        return this;
    }

    @Override
    public Sheet removeRow(int... index) {
        return this;
    }

    @Override
    public Sheet removeColumn(int... index) {
        return this;
    }

    @Override
    public Sheet addCell(Cell... cells) {
        for (Cell cell : cells) {
            data.put(cell.getRowIndex(), cell.getColumnIndex(), cell);
        }
        return this;
    }

    @Override
    public List<Cell> row(int index) {
        List<Cell> cells = Lists.newArrayList();
        for (int columnIndex = 0; columnIndex <= columnTailIndex; columnIndex++) {
            cells.add(data.get(index, columnIndex));
        }
        return cells;
    }

    @Override
    public List<List<Cell>> allRow() {
        List<List<Cell>> rows = Lists.newArrayList();
        for (int rowIndex = 1; rowIndex < rowTailIndex; rowIndex++) {
            rows.add(row(rowIndex));
        }
        return rows;
    }

    @Override
    public List<Cell> column(int index) {
        List<Cell> cells = Lists.newArrayList();
        for (int rowIndex = 0; rowIndex < rowTailIndex; rowIndex++) {
            cells.add(data.get(rowIndex, index));
        }
        return cells;
    }

    @Override
    public Collection<Cell> cells() {
        return data.values();
    }

    @Override
    public Cell cell(int rowIndex, int columnIndex) {
        return data.get(rowIndex, columnIndex);
    }

}
