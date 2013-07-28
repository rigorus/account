package ru.sirius.account.ui.utils.multispan;

import java.awt.Dimension;

public interface CellAttribute {

    public void addColumn();

    public void addRow();

    public void insertRow(int row);

    public Dimension getSize();

//    public void setSize(Dimension size);

    public void moveRow(int start, int end, int to);

    public void removeRow(int row);
}
