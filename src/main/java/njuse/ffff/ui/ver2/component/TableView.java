package njuse.ffff.ui.ver2.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import njuse.ffff.ui.component.PanelEx;

public class TableView extends PanelEx {

	private static final long serialVersionUID = 1L;

	private JTable table;
	private JScrollPane view;
	private TableColumnModel columnModel;

	private Map<Integer, TableColumn> hiddenColumns;

	private final String[] columnNames;

	public TableView(Object[][] values, String[] columns) {
		super(new BorderLayout());

		hiddenColumns = new HashMap<>();
		columnNames = columns;

		setOpaque(false);
		add(createView(createTable(values, columns)));

		columnModel = table.getColumnModel();
	}

	public JTable setTable(Object[][] values) {
		((DefaultTableModel) table.getModel()).setDataVector(values, columnNames);
		table.clearSelection();
		return table;
	}

	private JTable createTable(Object[][] values, String[] columns) {
		table = TableUtils.createTable(values, columns);
		TableUtils.setTableHeader(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		return table;
	}

	private JScrollPane createView(JTable table) {
		view = new JScrollPane(table);
		view.setOpaque(false);
		view.setColumnHeader(new JViewport());
		view.getColumnHeader().setOpaque(false);
		view.getViewport().setOpaque(false);
		view.setBorder(BorderFactory.createEmptyBorder());

		TableUtils.FitTableColumns(table);
		return view;
	}

	public void setDisplay(String columnName, boolean visible) {
		if (visible)
			show(columnName);
		else
			hide(columnName);
	}

	public void hide(String columnName) {
		int index = columnModel.getColumnIndex(columnName);
		TableColumn column = columnModel.getColumn(index);
		for (int i : hiddenColumns.keySet()) {
			if (index >= i) {
				index++;
			}
		}
		hiddenColumns.put(index, column);
		columnModel.removeColumn(column);
	}

	public void show(String columnName) {
		TableColumn c = null;
		int index = -1;
		// 查找
		for (int i = 0; i < columnNames.length; i++) {
			if (columnNames[i].equals(columnName)) {
				c = hiddenColumns.remove(index = i);
				break;
			}
		}
		if (index == -1 || c == null)
			return;	//未找到

		// 确认插入位置(?
		for (int i : hiddenColumns.keySet()) {
			if (index > i)
				index--;
		}
		columnModel.addColumn(c);
		columnModel.moveColumn(columnModel.getColumnCount() - 1, index);
	}

	public List<String> getColumnNames() {
		return Arrays.asList(columnNames);
	}

	public String getColumnName(int index) {
		return columnNames[index];
	}

	public void setSelectionFgColor(Color c) {
		table.setSelectionForeground(c);
	}

	public void setSelectionBgColor(Color c) {
		table.setSelectionBackground(c);
	}

	public void setTableFgColor(Color c) {
		table.setForeground(c);
	}

	public void setTableFont(Font f) {
		table.setFont(f);
		TableUtils.FitTableColumns(table);
	}

	public void setRowHeight(int height) {
		table.setRowHeight(height);
	}

	public void setHeaderFont(Font f) {
		table.getTableHeader().setFont(f);
	}

	public void setHeaderFgColor(Color c) {
		table.getTableHeader().setForeground(c);
	}

	public void setHeaderBgColor(Color c) {
		table.getTableHeader().setBackground(c);
		PanelEx corner = new PanelEx();
		corner.setBackground(c);
		view.setCorner(JScrollPane.UPPER_RIGHT_CORNER, corner);
	}

	public JTable getTable() {
		return table;
	}

	@Override
	public synchronized void addMouseListener(MouseListener l) {
		super.addMouseListener(l);
		table.addMouseListener(l);
	}

	@Override
	public synchronized void removeMouseListener(MouseListener l) {
		super.removeMouseListener(l);
		table.removeMouseListener(l);
	}

	public int[] getSelectedCellLocation() {
		int[] res = new int[2];

		res[0] = table.getSelectedRow();
		res[1] = table.getSelectedColumn();

		return res;
	}
	
	public Object getValueAt(int row, int column) {
		return table.getValueAt(row, column);
	}
}
