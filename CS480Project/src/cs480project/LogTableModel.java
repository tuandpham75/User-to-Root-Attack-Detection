/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs480project;

import java.util.List;
 
import javax.swing.table.AbstractTableModel;
 
/**
 * A table model implementation for a list of Employee objects.
 * @author www.codejava.net
 *
 */
public class LogTableModel extends AbstractTableModel {
    private static final int COLUMN_MONTH = 0;
    private static final int COLUMN_DAY = 1;
    private static final int COLUMN_TIME = 2;
    private static final int COLUMN_USER = 3;
    private static final int COLUMN_DRIVE = 4;
    private static final int COLUMN_EVENT = 5;
    
    private String[] columnNames = {"Month", "Day", "Time", "User", "Drive", "Event"};
    private List<Log> listLog;
     
    public LogTableModel(List<Log> listLog) {
        this.listLog = listLog;
         
        int indexCount = 1;
        for (Log log : listLog) {
            log.setRowid(indexCount++);
        }
    }
 
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
 
    @Override
    public int getRowCount() {
        return listLog.size();
    }
     
    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }
     
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (listLog.isEmpty()) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }
 
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Log log = listLog.get(rowIndex);
        Object returnValue = null;
         
        switch (columnIndex) {
        case COLUMN_MONTH:
            returnValue = log.getMonth();
            break;
        case COLUMN_DAY:
            returnValue = log.getDay();
            break;
        case COLUMN_TIME:
            returnValue = log.getTime();
            break;
        case COLUMN_USER:
            returnValue = log.getUser();
            break;
        case COLUMN_DRIVE:
            returnValue = log.getDrive();
            break;
        case COLUMN_EVENT:
            returnValue = log.getEvent();
            break;
        default:
            throw new IllegalArgumentException("Invalid column index");
        }
         
        return returnValue;
    }
 
}