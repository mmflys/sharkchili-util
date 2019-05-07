package com.shark.util.util.sheet;

import java.util.Collection;
import java.util.List;

public interface Sheet {

    public Sheet appendRow(Object... values);

    public Sheet appendColumn(String... names);

    public Sheet insertRow(int index, Object... values);

    public Sheet insertBeforeRow(int index, Object... values);

    public Sheet insertAfterRow(int index, Object... values);

    public Sheet insertColumn(int index, String... names);

    public Sheet insertBeforeColumn(int index, String... names);

    public Sheet insertAfterColumn(int index, String... names);

    public Sheet removeRow(int... index);

    public Sheet removeColumn(int... index);

    public Sheet addCell(Cell... cells);

    public List<Cell> row(int index);

    public List<List<Cell>> allRow();

    public List<Cell> column(int index);

    public Collection<Cell> cells();

    public Cell cell(int rowIndex, int columnIndex);
}
