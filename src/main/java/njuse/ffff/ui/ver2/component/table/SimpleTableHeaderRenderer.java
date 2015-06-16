package njuse.ffff.ui.ver2.component.table;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTable;

import sun.swing.table.DefaultTableCellHeaderRenderer;

@SuppressWarnings("restriction")
public class SimpleTableHeaderRenderer extends DefaultTableCellHeaderRenderer {
	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table,
			Object value, boolean hasFocus, boolean isSelected, int row, int column)
	{
		Component c = super.getTableCellRendererComponent(table, value, isSelected,
				hasFocus, row, column);
		((JComponent) c).setBorder(BorderFactory.createLineBorder(
				table.getGridColor(), table.getIntercellSpacing().width));
		return c;
	}
}
