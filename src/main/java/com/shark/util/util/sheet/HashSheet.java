package com.shark.util.util.sheet;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;

import java.util.Collection;
import java.util.List;

/**
 * A hash sheet implement sheet through {@link HashBasedTable}
 */
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

    private void init() {
        data = HashBasedTable.create();
        rowTailIndex = -1;
        columnTailIndex = -1;
    }

    @Override
    public Sheet appendRow(Object... values) {
        insertRow(rowTailIndex + 1, values);
        return this;
    }

    @Override
    public Sheet appendColumn(String... names) {
        insertColumn(columnTailIndex + 1, names);
        return this;
    }

    @Override
    public Sheet insertRow(int index, Object... values) {
        // Move backward
        if (index >= 0 && index <= rowTailIndex) {
            List<List<Cell>> afterRows = rows(index, rowTailIndex);
            for (List<Cell> rowCells : afterRows) {
                for (Cell cell : rowCells) {
                    cell.setRowIndex(cell.getRowIndex() + 1);
                    this.data.put(cell.getRowIndex(), cell.getColumnIndex(), cell);
                }
            }
        }

        // Insert cell
        for (int columnIndex = 0; columnIndex < values.length; columnIndex++) {
            if (columnIndex > columnTailIndex) break;

            Object value = values[columnIndex];
            data.put(index, columnIndex, new Cell(index, columnIndex, value));
        }
        rowTailIndex++;
        return this;
    }

    @Override
    public Sheet insertBeforeRow(int index, Object... values) {
        insertRow(index - 1, values);
        return this;
    }

    @Override
    public Sheet insertAfterRow(int index, Object... values) {
        insertRow(index + 1, values);
        return this;
    }

    @Override
    public Sheet insertColumn(int index, String... names) {
        // Move backward
        if (index >= 0 && index <= columnTailIndex) {
            List<List<Cell>> afterRows = rows(index, rowTailIndex);
            for (List<Cell> rowCells : afterRows) {
                for (Cell cell : rowCells) {
                    cell.setColumnIndex(cell.getColumnIndex() + names.length);
                    this.data.put(cell.getRowIndex(), cell.getColumnIndex(), cell);
                }
            }
        }

        // Insert column
        boolean isEmpty = data.isEmpty();
        // Insert column name.
        for (String name : names) {
            data.put(0, index, new Cell(0, index, name));
            columnTailIndex++;
            index++;
        }
        // Row index ++
        if (isEmpty) {
            rowTailIndex++;
        }
        return this;
    }

    @Override
    public Sheet insertBeforeColumn(int index, String... names) {
        insertColumn(index - 1, names);
        return this;
    }

    @Override
    public Sheet insertAfterColumn(int index, String... names) {
        insertColumn(index + 1, names);
        return this;
    }

    @Override
    public Sheet removeRow(int... indexes) {
        for (int index : reCalculateIndex(uniqueAndSort(indexes))) {
            removeRow(index);
        }
        return this;
    }

    private void removeRow(int index) {
        // Remove
        for (int columnIndex = 0; columnIndex <= columnTailIndex; columnIndex++) {
            this.data.remove(index, columnIndex);
        }
        // Move forward
        if (index >= 0 & index < rowTailIndex) {
            List<List<Cell>> rows = rows(index + 1, rowTailIndex);
            rows.forEach(row -> row.forEach(cell -> {
                cell.setRowIndex(cell.getRowIndex() - 1);
                this.data.put(cell.getRowIndex(), cell.getColumnIndex(), cell);
            }));
            removeRow(rowTailIndex);
        } else {
            rowTailIndex--;
        }
    }

    private List<Integer> uniqueAndSort(int[] indexes) {
        List<Integer> uniqueIndex = Lists.newArrayList();
        for (int index : indexes) {
            if (!uniqueIndex.contains(index)) {
                uniqueIndex.add(index);
            }
        }
        uniqueIndex.sort(Integer::compareTo);
        return uniqueIndex;
    }

    private List<Integer> reCalculateIndex(List<Integer> indexes) {
        indexes.sort(Integer::compareTo);
        for (int i = 0; i < indexes.size(); i++) {
            indexes.set(i, indexes.get(i) - i);
        }
        return indexes;
    }

    @Override
    public Sheet removeColumn(int... indexes) {
        for (int index : reCalculateIndex(uniqueAndSort(indexes))) {
            removeColumn(index);
        }
        return this;
    }

    private void removeColumn(int index) {
        // Remove
        for (int rowIndex = 0; rowIndex < rowTailIndex; rowIndex++) {
            this.data.remove(rowIndex, index);
        }
        // Move forward
        if (index >= 0 && index < columnTailIndex) {
            for (int rowIndex = 0; rowIndex <= rowTailIndex; rowIndex++) {
                for (int columnIndex = index + 1; columnIndex <= columnTailIndex; columnIndex++) {
                    Cell cell = this.data.get(rowIndex, columnIndex);
                    cell.setColumnIndex(cell.getColumnIndex() - 1);
                    this.data.put(cell.getRowIndex(), cell.getColumnIndex(), cell);
                }
            }
            removeColumn(columnTailIndex);
        } else {
            columnTailIndex--;
        }
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
            Cell cell = data.get(index, columnIndex);
            if (cell != null) {
                cells.add(cell);
            }
        }
        return cells;
    }

    @Override
    public List<List<Cell>> allData() {
        return rows(1, rowTailIndex);
    }

    @Override
    public List<List<Cell>> allRow() {
        return rows(0, rowTailIndex);
    }

    @Override
    public List<List<Cell>> rows(int rowStartIndex, int rowEndIndex) {
        rowEndIndex = rowEndIndex > rowTailIndex ? rowTailIndex : rowEndIndex;
        List<List<Cell>> rows = Lists.newArrayList();
        for (int rowIndex = rowStartIndex; rowIndex <= rowEndIndex; rowIndex++) {
            rows.add(row(rowIndex));
        }
        return rows;
    }

    @Override
    public List<Cell> column(int index) {
        List<Cell> cells = Lists.newArrayList();
        for (int rowIndex = 0; rowIndex <= rowTailIndex; rowIndex++) {
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof HashSheet) {
            HashSheet sheet = new HashSheet();
            return this.data.equals(sheet.data) && this.rowTailIndex == sheet.rowTailIndex && this.columnTailIndex == sheet.columnTailIndex;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (List<Cell> cells : allRow()) {
            for (Cell cell : cells) {
                builder.append(cell.getValue()).append("  ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}