package com.shark.util.util.sheet;

import java.util.Collection;
import java.util.List;

/**
 * Represent a sheet of Excel
 */
public interface Sheet {

    /**
     * Append a row data
     *
     * @param values Row datas
     * @return This
     */
    public Sheet appendRow(Object... values);

    /**
     * Append a column name
     *
     * @param names Column names
     * @return This
     */
    public Sheet appendColumn(String... names);

    /**
     * Insert a row data in the index
     *
     * @param index  Location to insert
     * @param values Column names
     * @return This
     */
    public Sheet insertRow(int index, Object... values);

    /**
     * Insert a row data before the index
     *
     * @param index  Location to insert
     * @param values Column names
     * @return This
     */
    public Sheet insertBeforeRow(int index, Object... values);

    /**
     * Insert a row data after the index
     *
     * @param index  Location to insert after this
     * @param values Row data
     * @return This
     */
    public Sheet insertAfterRow(int index, Object... values);

    /**
     * Insert a column name in the index
     *
     * @param index Location to insert
     * @param names Column names
     * @return This
     */
    public Sheet insertColumn(int index, String... names);

    /**
     * Insert a row data before the index
     *
     * @param index Location to insert before this
     * @param names Column names
     * @return This
     */
    public Sheet insertBeforeColumn(int index, String... names);

    /**
     * Insert a row data after the index
     *
     * @param index Location to insert after this
     * @param names Column names
     * @return This
     */
    public Sheet insertAfterColumn(int index, String... names);

    /**
     * Remove rows according to specified index
     *
     * @param indexes Row indexes Which to be remove
     * @return This
     */
    public Sheet removeRow(int... indexes);

    /**
     * Remove column name according to specified index
     *
     * @param indexes Column indexes Which to be remove
     * @return This
     */
    public Sheet removeColumn(int... indexes);

    /**
     * Add cells
     *
     * @param cells Which to be added
     * @return
     */
    public Sheet addCell(Cell... cells);

    /**
     * Get cells according to specified index
     *
     * @param index Which is row index
     * @return List of Cell
     */
    public List<Cell> row(int index);

    /**
     * Get all data exclude column name.
     *
     * @return A list of list of Cell.
     */
    public List<List<Cell>> allData();

    /**
     * Get rows from start index to end index.
     *
     * @param rowStartIndex Start index.
     * @param rowEndIndex   End index.
     * @return A list of list of Cell.
     */
    public List<List<Cell>> rows(int rowStartIndex, int rowEndIndex);

    /**
     * Get all rows include column name.
     *
     * @return A list of list of Cell.
     */
    public List<List<Cell>> allRow();

    /**
     * Get cells according to specified column index
     *
     * @param index Which is column index
     * @return This
     */
    public List<Cell> column(int index);

    /**
     * Get all cells
     *
     * @return Collection of cell
     */
    public Collection<Cell> cells();

    /**
     * Get cell according to specified row index and column index.
     *
     * @param rowIndex    Row index
     * @param columnIndex Column index
     * @return A instance of Cell
     */
    public Cell cell(int rowIndex, int columnIndex);
}
