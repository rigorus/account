package ru.sirius.account.ui.utils.multispan;

import java.util.Enumeration;
import java.util.Vector;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

public class AttributiveCellTableModel extends DefaultTableModel {

    protected CellAttribute attributiveModel;

    public AttributiveCellTableModel() {
        this((Vector) null, 0);
    }

    public AttributiveCellTableModel(int numRows, int numColumns) {
        Vector names = new Vector(numColumns);
        names.setSize(numColumns);
        setColumnIdentifiers(names);
        dataVector = new Vector();
        setNumRows(numRows);
        attributiveModel = new DefaultCellAttribute(numRows, numColumns);
    }

    public AttributiveCellTableModel(Vector columnNames, int numRows) {
        setColumnIdentifiers(columnNames);
        dataVector = new Vector();
        setNumRows(numRows);
        attributiveModel = new DefaultCellAttribute(numRows, columnNames != null ? columnNames.size() : 0);
    }

    public AttributiveCellTableModel(Object[] columnNames, int numRows) {
        this(convertToVector(columnNames), numRows);
    }

    public AttributiveCellTableModel(Vector data, Vector columnNames) {
        setDataVector(data, columnNames);
    }

    public AttributiveCellTableModel(Object[][] data, Object[] columnNames) {
        setDataVector(data, columnNames);
    }

    @Override
    public void setDataVector(Vector newData, Vector columnNames) {
        if (newData == null) {
            throw new IllegalArgumentException("setDataVector1() - Null parameter");
        }
        
        super.setDataVector(dataVector, columnNames);
        dataVector = newData;
        attributiveModel = new DefaultCellAttribute(dataVector.size(), columnIdentifiers.size());

        newRowsAdded(new TableModelEvent(this, 0, getRowCount() - 1,
                TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
    }

    @Override
    public void addColumn(Object columnName, Vector columnData) {
        if (columnName == null) {
            throw new IllegalArgumentException("addColumn() - null parameter");
        }
        columnIdentifiers.addElement(columnName);
        int index = 0;
        Enumeration enumeration = dataVector.elements();
        while (enumeration.hasMoreElements()) {
            Object value;
            if ((columnData != null) && (index < columnData.size())) {
                value = columnData.elementAt(index);
            } else {
                value = null;
            }
            ((Vector) enumeration.nextElement()).addElement(value);
            index++;
        }

        //
        attributiveModel.addColumn();

        fireTableStructureChanged();
    }

    @Override
    public void addRow(Vector rowData) {
        if (rowData == null) {
            rowData = new Vector(getColumnCount());
        } else {
            rowData.setSize(getColumnCount());
        }
        dataVector.addElement(rowData);

        //
        attributiveModel.addRow();

        newRowsAdded(new TableModelEvent(this, getRowCount() - 1, getRowCount() - 1,
                TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
    }

    @Override
    public void insertRow(int row, Vector rowData) {
        if (rowData == null) {
            rowData = new Vector(getColumnCount());
        } else {
            rowData.setSize(getColumnCount());
        }

        dataVector.insertElementAt(rowData, row);

        //
        attributiveModel.insertRow(row);

        newRowsAdded(new TableModelEvent(this, row, row,
                TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
    }

    @Override
    public void moveRow(int start, int end, int to) {
        attributiveModel.moveRow(start, end, to);
        super.moveRow(start, end, to);
    }
    
    @Override
    public void removeRow(int row) {
        attributiveModel.removeRow(row);
        super.removeRow(row);
    }
    
    public CellAttribute getCellAttribute() {
        return attributiveModel;
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

//    public void setCellAttribute(CellAttribute newCellAtt) {
//        int numColumns = getColumnCount();
//        int numRows = getRowCount();
//        if ((newCellAtt.getSize().width != numColumns)
//                || (newCellAtt.getSize().height != numRows)) {
//            
//            newCellAtt.setSize(new Dimension(numRows, numColumns));
//        }
//        cellAtt = newCellAtt;
//        fireTableDataChanged();
//    }

    /*
     public void changeCellAttribute(int row, int column, Object command) {
     cellAtt.changeAttribute(row, column, command);
     }

     public void changeCellAttribute(int[] rows, int[] columns, Object command) {
     cellAtt.changeAttribute(rows, columns, command);
     }
     */
}
