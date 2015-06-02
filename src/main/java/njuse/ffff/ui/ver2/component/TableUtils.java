package njuse.ffff.ui.ver2.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import sun.swing.table.DefaultTableCellHeaderRenderer;

@SuppressWarnings("restriction")
public class TableUtils {

	public static JTable createTable(Object[][] value, Object[] columns) {
		DefaultTableModel dtm = new DefaultTableModel(value, columns) {
			private static final long	serialVersionUID	= 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				Class<?> returnValue;
				if (columnIndex >= 0 && columnIndex < getColumnCount()) {
					try {
						returnValue = getValueAt(0, columnIndex).getClass();
						if (returnValue.equals(Boolean.class))
							returnValue = Object.class;
					} catch (Exception e) {
						returnValue = Object.class;
					}
				} else {
					returnValue = Object.class;
				}
				return returnValue;
			}
		};

		JTable table = new JTable(dtm);
		table.setOpaque(false);
		table.setFocusable(false);
		table.setIntercellSpacing(new Dimension(2, 2));
		table.setGridColor(Color.GRAY);
		table.setShowGrid(false);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dtm);
		table.setRowSorter(sorter);

		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			private static final long	serialVersionUID	= 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value,
					boolean isSelected, boolean hasFocus, int row, int column) {
				JLabel c = (JLabel) super.getTableCellRendererComponent(
						table, value, isSelected, hasFocus, row, column);
				if (!Number.class.isAssignableFrom(table.getModel().getColumnClass(
						column))) {
					c.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
				}
				return c;
			}
		});

		return table;
	}

	public static void setTableHeader(JTable table) {
		JPopupMenu menu = new JPopupMenu();
		JMenuItem item = new JMenuItem();
		item.add(new JCheckBox("test3"));
		menu.add(item);

		JTableHeader header = table.getTableHeader();
		header.setComponentPopupMenu(menu);

		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					JPopupMenu menu = header.getComponentPopupMenu();
					menu.show(header, e.getX(), e.getY());
				}
			}
		});

		DefaultTableCellRenderer dtr = new DefaultTableCellRenderer() {
			private static final long	serialVersionUID	= 1L;

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
				c.setBorder(BorderFactory.createEmptyBorder());
				return c;
			}
		};
		table.setDefaultRenderer(Object.class, dtr);

		header.setReorderingAllowed(false);
		header.setResizingAllowed(false);
		header.setOpaque(false);

		header.setDefaultRenderer(new DefaultTableCellHeaderRenderer() {

			private static final long	serialVersionUID	= 1L;

			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean hasFocus, boolean isSelected, int row, int column)
			{
				Component c = super.getTableCellRendererComponent(table, value, isSelected,
						hasFocus, row, column);
				((JComponent) c).setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
				return c;
			}
		});
	}

	public static void FitTableColumns(JTable table) {
		JTableHeader header = table.getTableHeader();
		int rowCount = table.getRowCount();

		TableColumnModel columns = table.getColumnModel();
		int columnCount = columns.getColumnCount();

		for (int i = 0; i < columnCount; i++) {
			TableColumn column = table.getColumnModel().getColumn(i);
			int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
			int width = (int) table.getTableHeader().getDefaultRenderer()
					.getTableCellRendererComponent(table, column.getIdentifier()
							, false, false, -1, col).getPreferredSize().getWidth();
			for (int row = 0; row < rowCount; row++) {
				int preferedWidth = (int) table.getCellRenderer(row, col)
						.getTableCellRendererComponent(table,
								table.getValueAt(row, col), false, false, row, col)
						.getPreferredSize().getWidth();
				width = Math.max(width, preferedWidth);
			}
			header.setResizingColumn(column); // 此行很重要
			column.setWidth(width + table.getIntercellSpacing().width);
		}
	}
}
