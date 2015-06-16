package njuse.ffff.ui.ver2.component;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import njuse.ffff.ui.ver2.component.table.SimpleTableCellRenderer;
import njuse.ffff.ui.ver2.component.table.SimpleTableHeaderRenderer;
import njuse.ffff.ui.ver2.component.table.ModifiedTableModel;

public class TableUtils {

	public static JTable createTable(Object[][] value, Object[] columns) {
		DefaultTableModel dtm = new ModifiedTableModel(value, columns);

		JTable table = new JTable(dtm);
		table.setOpaque(false);
		table.setFocusable(false);
		table.setGridColor(Color.GRAY);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		table.setDefaultRenderer(Object.class, new SimpleTableCellRenderer());

		table.setAutoCreateRowSorter(true);

		return table;
	}

	public static void setTableHeader(JTable table) {
		JTableHeader header = table.getTableHeader();

		header.setReorderingAllowed(false);
		header.setResizingAllowed(false);
		header.setOpaque(false);

		header.setDefaultRenderer(new SimpleTableHeaderRenderer());
	}

	public static void FitTableColumns(JTable table) {
		JTableHeader header = table.getTableHeader();
		int rowCount = table.getRowCount();

		TableColumnModel columns = table.getColumnModel();
		int columnCount = columns.getColumnCount();

		for (int i = 0; i < columnCount; i++) {
			TableColumn column = table.getColumnModel().getColumn(i);
			int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
			JComponent cell = (JComponent) table.getTableHeader().getDefaultRenderer()
					.getTableCellRendererComponent(table, column.getIdentifier()
							, false, false, -1, col);
			int width = (int) cell.getPreferredSize().getWidth() + cell.getInsets().left
					+ cell.getInsets().right;
			for (int row = 0; row < rowCount; row++) {
				int preferedWidth = (int) table.getCellRenderer(row, col)
						.getTableCellRendererComponent(table, table.getValueAt(row, col),
								false, false, row, col).getPreferredSize().getWidth()
						+ table.getIntercellSpacing().width;
				width = Math.max(width, preferedWidth);
			}
			header.setResizingColumn(column);
			column.setWidth(width);
		}
	}
}
