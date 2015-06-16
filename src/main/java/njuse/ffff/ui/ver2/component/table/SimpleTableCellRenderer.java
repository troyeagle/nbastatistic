package njuse.ffff.ui.ver2.component.table;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class SimpleTableCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel c = (JLabel) super.getTableCellRendererComponent(table,
				value, isSelected, hasFocus, row, column);
		if (c.getText().endsWith("%")) {
			c.setHorizontalAlignment(JLabel.RIGHT);
		} else {
			c.setHorizontalAlignment(JLabel.CENTER);
		}
		return c;
	}
}
