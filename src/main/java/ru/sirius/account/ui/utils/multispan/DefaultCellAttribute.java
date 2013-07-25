package ru.sirius.account.ui.utils.multispan;

import java.awt.*;

public final class DefaultCellAttribute
        implements CellAttribute, CellSpan, ColoredCell, CellFont {

    //
    // !!!! CAUTION !!!!!
    // these values must be synchronized to Table data
    //
    protected int rowSize;
    protected int columnSize;
    protected int[][][] span;                   // CellSpan
    protected Color[][] foreground;             // ColoredCell
    protected Color[][] background;             //
    protected Font[][] font;                   // CellFont

    public DefaultCellAttribute() {
        this(0, 0);
    }

    public DefaultCellAttribute(int numRows, int numColumns) {
        columnSize = numColumns;
        rowSize = numRows;
        setSize();
    }

    protected void initValue() {
        for (int i = 0; i < span.length; i++) {
            for (int j = 0; j < span[i].length; j++) {
                span[i][j][CellSpan.COLUMN] = 1;
                span[i][j][CellSpan.ROW] = 1;
            }
        }
    }

    //
    // CellSpan
    //
    @Override
    public int[] getSpan(int row, int column) {
        if (isOutOfBounds(row, column)) {
            int[] ret_code = {1, 1};
            return ret_code;
        }
        return span[row][column];
    }

    @Override
    public void setSpan(int[] span, int row, int column) {
        if (isOutOfBounds(row, column)) {
            return;
        }
        this.span[row][column] = span;
    }

    @Override
    public boolean isVisible(int row, int column) {
        if (isOutOfBounds(row, column)) {
            return false;
        }
        if ((span[row][column][CellSpan.COLUMN] < 1)
                || (span[row][column][CellSpan.ROW] < 1)) {
            return false;
        }
        return true;
    }

    @Override
    public void combine(int[] rows, int[] columns) {
        if (isOutOfBounds(rows, columns)) {
            return;
        }
        int rowSpan = rows.length;
        int columnSpan = columns.length;
        int startRow = rows[0];
        int startColumn = columns[0];
        for (int i = 0; i < rowSpan; i++) {
            for (int j = 0; j < columnSpan; j++) {
                if ((span[startRow + i][startColumn + j][CellSpan.COLUMN] != 1)
                        || (span[startRow + i][startColumn + j][CellSpan.ROW] != 1)) {
                    //System.out.println("can't combine");
                    return;
                }
            }
        }
        for (int i = 0, ii = 0; i < rowSpan; i++, ii--) {
            for (int j = 0, jj = 0; j < columnSpan; j++, jj--) {
                span[startRow + i][startColumn + j][CellSpan.COLUMN] = jj;
                span[startRow + i][startColumn + j][CellSpan.ROW] = ii;
                //System.out.println("r " +ii +"  c " +jj);
            }
        }
        span[startRow][startColumn][CellSpan.COLUMN] = columnSpan;
        span[startRow][startColumn][CellSpan.ROW] = rowSpan;

    }

    @Override
    public void split(int row, int column) {
        if (isOutOfBounds(row, column)) {
            return;
        }
        int columnSpan = span[row][column][CellSpan.COLUMN];
        int rowSpan = span[row][column][CellSpan.ROW];
        for (int i = 0; i < rowSpan; i++) {
            for (int j = 0; j < columnSpan; j++) {
                span[row + i][column + j][CellSpan.COLUMN] = 1;
                span[row + i][column + j][CellSpan.ROW] = 1;
            }
        }
    }

    //
    // ColoredCell
    //
    @Override
    public Color getForeground(int row, int column) {
        if (isOutOfBounds(row, column)) {
            return null;
        }
        return foreground[row][column];
    }

    @Override
    public void setForeground(Color color, int row, int column) {
        if (isOutOfBounds(row, column)) {
            return;
        }
        foreground[row][column] = color;
    }

    @Override
    public void setForeground(Color color, int[] rows, int[] columns) {
        if (isOutOfBounds(rows, columns)) {
            return;
        }
        setValues(foreground, color, rows, columns);
    }

    @Override
    public Color getBackground(int row, int column) {
        if (isOutOfBounds(row, column)) {
            return null;
        }
        return background[row][column];
    }

    @Override
    public void setBackground(Color color, int row, int column) {
        if (isOutOfBounds(row, column)) {
            return;
        }
        background[row][column] = color;
    }

    @Override
    public void setBackground(Color color, int[] rows, int[] columns) {
        if (isOutOfBounds(rows, columns)) {
            return;
        }
        setValues(background, color, rows, columns);
    }
    //

    //
    // CellFont
    //
    @Override
    public Font getFont(int row, int column) {
        if (isOutOfBounds(row, column)) {
            return null;
        }
        return font[row][column];
    }

    @Override
    public void setFont(Font font, int row, int column) {
        if (isOutOfBounds(row, column)) {
            return;
        }
        this.font[row][column] = font;
    }

    @Override
    public void setFont(Font font, int[] rows, int[] columns) {
        if (isOutOfBounds(rows, columns)) {
            return;
        }
        setValues(this.font, font, rows, columns);
    }
    //

    //
    // CellAttribute
    //
    @Override
    public void addColumn() {
        if (!increment("column")) {
            return;
        }

        int[][][] oldSpan = span;
        span = new int[rowSize][columnSize][2];
        System.arraycopy(oldSpan, 0, span, 0, rowSize);
        Color[][] oldForeground = foreground;
        foreground = new Color[rowSize][columnSize];
        System.arraycopy(oldForeground, 0, foreground, 0, rowSize);
        Color[][] oldBackground = background;
        background = new Color[rowSize][columnSize];
        System.arraycopy(oldBackground, 0, background, 0, rowSize );
        Font[][] oldFont = font;
        font = new Font[rowSize][columnSize];
        System.arraycopy(oldFont, 0, font, 0, rowSize);
        for (int i = 0; i < rowSize; i++) {
            span[i][columnSize - 1][CellSpan.COLUMN] = 1;
            span[i][columnSize - 1][CellSpan.ROW] = 1;
        }
    }

    @Override
    public void addRow() {
        if (!increment("row")) {
            return;
        }

        int[][][] oldSpan = span;
        span = new int[rowSize][columnSize][2];        
        System.arraycopy(oldSpan, 0, span, 0, rowSize - 1);
        Color[][] oldForeground = foreground;
        foreground = new Color[rowSize][columnSize];
        System.arraycopy(oldForeground, 0, foreground, 0, rowSize - 1);
        Color[][] oldBackground = background;
        background = new Color[rowSize][columnSize];
        System.arraycopy(oldBackground, 0, background, 0, rowSize - 1);
        Font[][] oldFont = font;
        font = new Font[rowSize][columnSize];
        System.arraycopy(oldFont, 0, font, 0, rowSize - 1);
        
        for (int i = 0; i < columnSize; i++) {
            span[rowSize - 1][i][CellSpan.COLUMN] = 1;
            span[rowSize - 1][i][CellSpan.ROW] = 1;
        }
    }

    @Override
    public void insertRow(int row) {

        if( !increment("row")){
            return;
        }
        
        int[][][] oldSpan = span;
        span = new int[rowSize][columnSize][2];
        Color[][] oldForeground = foreground;
        foreground = new Color[rowSize][columnSize];
        Color[][] oldBackground = background;
        background = new Color[rowSize][columnSize];
        Font[][] oldFont = font;
        font = new Font[rowSize][columnSize];
        
        if (0 < row) {
            System.arraycopy(oldSpan, 0, span, 0, row );
            System.arraycopy(oldForeground, 0, foreground, 0, row );
            System.arraycopy(oldBackground, 0, background, 0, row );
            System.arraycopy(oldFont, 0, font, 0, row );
        }
        System.arraycopy(oldSpan, row, span, row + 1 , rowSize - row - 1);
        System.arraycopy(oldForeground, row, foreground, row + 1, rowSize - row - 1);
        System.arraycopy(oldBackground, row, background, row + 1, rowSize - row - 1);
        System.arraycopy(oldFont, row, font, row + 1, rowSize - row - 1);
        
        for (int i = 0; i < columnSize; i++) {
            span[row][i][CellSpan.COLUMN] = 1;
            span[row][i][CellSpan.ROW] = 1;
        }
    }

    @Override
    public Dimension getSize() {
        return new Dimension(rowSize, columnSize);
    }

    private boolean increment(String what){
        
        switch(what){
            case "column": ++columnSize; break;
            case "row": ++rowSize; break;   
        }

        if (columnSize > 0 && rowSize > 0 && span == null) {
            setSize();
        }        
        return span != null;
    }
    
    public void setSize() {
        if( columnSize == 0 || rowSize == 0) return;
        
        span = new int[rowSize][columnSize][2];   // 2: COLUMN,ROW
        foreground = new Color[rowSize][columnSize];
        background = new Color[rowSize][columnSize];
        font = new Font[rowSize][columnSize];
        initValue();
    }

    /*
     public void changeAttribute(int row, int column, Object command) {
     }

     public void changeAttribute(int[] rows, int[] columns, Object command) {
     }
     */
    protected boolean isOutOfBounds(int row, int column) {
        if ((row < 0) || (rowSize <= row)
                || (column < 0) || (columnSize <= column)) {
            return true;
        }
        return false;
    }

    protected boolean isOutOfBounds(int[] rows, int[] columns) {
        for (int i = 0; i < rows.length; i++) {
            if ((rows[i] < 0) || (rowSize <= rows[i])) {
                return true;
            }
        }
        for (int i = 0; i < columns.length; i++) {
            if ((columns[i] < 0) || (columnSize <= columns[i])) {
                return true;
            }
        }
        return false;
    }

    protected void setValues(Object[][] target, Object value,
            int[] rows, int[] columns) {
        for (int i = 0; i < rows.length; i++) {
            int row = rows[i];
            for (int j = 0; j < columns.length; j++) {
                int column = columns[j];
                target[row][column] = value;
            }
        }
    }
}
