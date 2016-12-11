/*
 * Copyright (c) 2016 Matrix Studios.
 *  All code is licensed under the WTFPL.
 *  A copy of this license is available with this software or at http://wtfpl.net/
 *
 */

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ProgressCellRenderer extends JProgressBar implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Integer progress = 0;
        setStringPainted(true);
        if(value instanceof Integer) {
            progress = (int) value;
        } else if(value instanceof Double){
            progress = Math.toIntExact(Math.round((double) value));
        } else {
            progress = Math.round((Float) value);
        }
        setString(String.format("%d%%", progress));
        setValue(progress);
        return this;
    }
}
