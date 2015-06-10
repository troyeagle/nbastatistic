package njuse.ffff.ui.ver2.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import njuse.ffff.ui.component.PanelEx;

public class TableView extends PanelEx {

	private static final long serialVersionUID = 1L;

	private static TableViewUI ui;

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
		setTableViewUI(ui);
	}

	public void setTableViewUI(TableViewUI ui) {
		if (ui != null) {
			setHeaderFont(ui.getHeaderFont());
			setHeaderFgColor(ui.getHeaderFgColor());
			setHeaderBgColor(ui.getHeaderBgColor());

			setTableFont(ui.getTableFont());
			setTableFgColor(ui.getTableFgColor());
			setRowHeight(ui.getTableFont().getSize() + 5);

			setSelectionFgColor(ui.getSelectionFgColor());
			setSelectionBgColor(ui.getSelectionBgColor());

			setScrollBarUI(ui.getScrollBarUI());
		}
	}

	private void setScrollBarUI(ScrollBarUI ui) {
		if (ui != null) {
			try {
				Method m = ui.getClass().getMethod("createUI", JComponent.class);
				view.getHorizontalScrollBar().setUI((ScrollBarUI) m.invoke(null, this));
				view.getVerticalScrollBar().setUI((ScrollBarUI) m.invoke(null, this));
			} catch (Exception e) {}
		}
	}

	public void setTable(Object[][] values) {
		((DefaultTableModel) table.getModel()).setDataVector(values, columnNames);
		table.clearSelection();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableUtils.FitTableColumns(table);
		repaint();
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
		try {
			int index = columnModel.getColumnIndex(columnName);
			TableColumn column = columnModel.getColumn(index);
			Integer[] keys = hiddenColumns.keySet().toArray(new Integer[0]);
			Arrays.sort(keys);	// 防止因顺序出现问题？
			for (int i : keys) {
				if (index >= i) {
					index++;
				}
			}
			hiddenColumns.put(index, column);
			columnModel.removeColumn(column);
			TableUtils.FitTableColumns(table);
		} catch (Exception e) {}
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
		int dec = 0;	// 需要减少的量
		for (int i : hiddenColumns.keySet()) {
			if (index > i)
				dec++;
		}
		index -= dec;
		columnModel.addColumn(c);
		columnModel.moveColumn(columnModel.getColumnCount() - 1, index);
		TableUtils.FitTableColumns(table);
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
		PanelEx corner2 = new PanelEx(new BorderLayout());
		corner.setBackground(c);
		corner2.setBorder(BorderFactory.createEmptyBorder(1, 0, 1, 0));
		corner2.setOpaque(false);
		corner2.add(corner);
		view.setCorner(JScrollPane.UPPER_RIGHT_CORNER, corner2);
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

	@Override
	public void paint(Graphics g) {
		int mode = table.getWidth() <= getWidth()
				? JTable.AUTO_RESIZE_ALL_COLUMNS
				: JTable.AUTO_RESIZE_OFF;
		if (table.getAutoResizeMode() != mode) {
			table.setAutoResizeMode(mode);
			if (mode == JTable.AUTO_RESIZE_OFF)
				TableUtils.FitTableColumns(table);
		} else {
			super.paint(g);
		}
	}

	public static void setDefaultTableViewUI(TableViewUI ui) {
		TableView.ui = ui;
		if (ui != null) {
			UIManager.put("Table.background", new ColorUIResource(ui.getRowColor1()));
			UIManager.put("Table.alternateRowColor", ui.getRowColor2());
		}
	}
}
