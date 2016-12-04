package Base;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.geom.Arc2D;

public class ProgressCellRenderer extends JProgressBar implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Integer progress = 0;
        if(value instanceof Integer) {
            progress = (int) value;
        } else if(value instanceof Double){
            progress = Math.toIntExact(Math.round((double) value));
        } else {
            progress = Math.round((Float) value);
        }
        setValue(progress);
        return this;
    }
}
