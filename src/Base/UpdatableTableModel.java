package Base;

import Classes.Job;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class UpdatableTableModel extends AbstractTableModel {
    private ArrayList<Job> jobs;

    public UpdatableTableModel() { jobs = new ArrayList<>(); }

    @Override
    public int getRowCount() {
        if(jobs == null) { return 0; }
        return jobs.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int column) {
        String name = "??";
        switch (column) {
            case 0:
                name = "Job Name";
                break;
            case 1:
                name = "Duration";
                break;
            case 2:
                name = "Skills";
                break;
            case 3:
                name = "Progress";
                break;
            case 4:
                name = "Actions";
                break;
        }
        return name;
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
                value = j.getDuration();
                break;
            case 2:
                value = j.getSkills();
                break;
            case 3:
                value = j.getProgress();
                break;
        }
        return value;
    }

    public void addJob(Job j) {
        jobs.add(j);
        fireTableRowsInserted(jobs.size() - 1, jobs.size() - 1);
    }

    protected void updateStatus(Job j) {
        int row = jobs.indexOf(j);
        float p = (float) j.getProgress();
        setValueAt(p, row, 3);
        fireTableCellUpdated(row, 3);
        fireTableDataChanged();
    }
}
