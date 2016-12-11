/*
 * Copyright (c) 2016 Matrix Studios.
 *  All code is licensed under the WTFPL.
 *  A copy of this license is available with this software or at http://wtfpl.net/
 *
 */

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class UpdatableTableModel extends AbstractTableModel {
    private ArrayList<Job> jobs;
    private String[] columnNames = {"Job Name", "Duration", "Skills", "Progress", "Suspend", "Cancel"};

    public UpdatableTableModel() { jobs = new ArrayList<>(); }

    @Override
    public int getRowCount() {
        if(jobs == null) { return 0; }
        return jobs.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Job j = jobs.get(rowIndex);
        Object value = null;
        switch (columnIndex) {
            case 0:
                value = j.name;
                break;
            case 1:
                value = String.format("%.2f seconds", j.getDuration());
                break;
            case 2:
                value = j.getSkills();
                break;
            case 3:
                value = j.getProgress();
                break;
            case 4:
                value = "Suspend/Resume";
                break;
            case 5:
                value = "Cancel";
                break;
        }
        return value;
    }

    public void addJob(Job j) {
        jobs.add(j);
        fireTableRowsInserted(jobs.size() - 1, jobs.size() - 1);
    }
    public void removeJob(Job j) {
        jobs.remove(j);
        fireTableRowsDeleted(jobs.size() - 1, jobs.size() - 1);
    }

    protected void updateStatus(Job j) {
        int row = jobs.indexOf(j);
        float p = (float) j.getProgress();
        setValueAt(p, row, 3);
        fireTableCellUpdated(row, 3);
    }

    public void handleClick(int c, int r) {
        Job j = jobs.get(r);
        switch (c) {
            case 4:
                j.toggleFlag();
                break;
            case 5:
                j.stop();
                break;
        }
    }
}
