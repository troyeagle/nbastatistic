package njuse.ffff.ui.ver2.component.table;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * 可合并列头UI
 * 
 * @author Brad.Wu
 * @version 1.0
 */
public class GroupableTableHeaderUI extends BasicTableHeaderUI {

	/*
	 * （非 Javadoc）
	 * 
	 * @see javax.swing.plaf.basic.BasicTableHeaderUI#paint(java.awt.Graphics,
	 * javax.swing.JComponent)
	 */
	@Override
	public void paint(Graphics g, JComponent c) {
		if (header.getColumnModel().getColumnCount() <= 0) { return; }
		boolean ltr = header.getComponentOrientation().isLeftToRight();

		Rectangle clip = g.getClipBounds();
		Point left = clip.getLocation();
		Point right = new Point(clip.x + clip.width - 1, clip.y);
		TableColumnModel cm = header.getColumnModel();
		int cMin = header.columnAtPoint(ltr ? left : right);
		int cMax = header.columnAtPoint(ltr ? right : left);
		// This should never happen.
		if (cMin == -1) {
			cMin = 0;
		}
		// If the table does not have enough columns to fill the view we'll get -1.
		// Replace this with the index of the last column.
		if (cMax == -1) {
			cMax = cm.getColumnCount() - 1;
		}

		// TableColumn draggedColumn = header.getDraggedColumn();
		int columnWidth;
		// Rectangle cellRect = header.getHeaderRect(ltr ? cMin : cMax);
		TableColumn aColumn;
		// if (ltr) {
		// for (int column = cMin; column <= cMax; column++) {
		// aColumn = cm.getColumn(column);
		// columnWidth = aColumn.getWidth();
		// cellRect.width = columnWidth;
		// // if (aColumn != draggedColumn) {
		// paintCell(g, cellRect, column);
		// // }
		// cellRect.x += columnWidth;
		// }
		// } else {
		// for (int column = cMax; column >= cMin; column--) {
		// aColumn = cm.getColumn(column);
		// columnWidth = aColumn.getWidth();
		// cellRect.width = columnWidth;
		// // if (aColumn != draggedColumn) {
		// paintCell(g, cellRect, column);
		// // }
		// cellRect.x += columnWidth;
		// }
		// }
		GroupableTableHeader gHeader = (GroupableTableHeader) header;
		for (int row = 0, rowCount = gHeader.getRowCount(); row < rowCount; row++) {
			Rectangle cellRect = gHeader.getHeaderRect(row, ltr ? cMin : cMax);
			if (ltr) {
				for (int column = cMin; column <= cMax; column++) {
					Group group = gHeader.getGroup(row, column);
					cellRect.width = 0;
					for (int from = group.getColumn(), to = from + group.getColumnSpan() - 1; from <= to; from++) {
						aColumn = cm.getColumn(from);
						columnWidth = aColumn.getWidth();
						cellRect.width += columnWidth;
					}
					cellRect.y = gHeader.getYOfGroup(group);
					cellRect.height = gHeader.getHeightOfGroup(group);
					paintCell(g, cellRect, row, column);
					cellRect.x += cellRect.width;
					column += group.getColumnSpan() - 1;
				}
			} else {
				for (int column = cMax; column >= cMin; column--) {
					Group group = gHeader.getGroup(row, column);
					cellRect.width = 0;
					for (int from = group.getColumn(), to = from + group.getColumnSpan() - 1; from <= to; from++) {
						aColumn = cm.getColumn(from);
						columnWidth = aColumn.getWidth();
						cellRect.width += columnWidth;
					}
					paintCell(g, cellRect, row, column);
					cellRect.x += cellRect.width;
					column -= group.getColumnSpan() - 1;
				}
			}
		}
		// Remove all components in the rendererPane.
		rendererPane.removeAll();
	}

	/**
	 * 描画指定行列
	 * 
	 * @param g
	 * @param cellRect
	 * @param rowIndex
	 * @param columnIndex
	 */
	private void paintCell(Graphics g, Rectangle cellRect, int rowIndex, int columnIndex) {
		Component component = getHeaderRenderer(rowIndex, columnIndex);
		rendererPane.paintComponent(g, component, header, cellRect.x, cellRect.y,
				cellRect.width,
				cellRect.height, true);
	}

	/**
	 * 获取指定行列的描述组件
	 * 
	 * @param rowIndex
	 * @param columnIndex
	 * @return
	 */
	private Component getHeaderRenderer(int rowIndex, int columnIndex) {
		GroupableTableHeader gHeader = (GroupableTableHeader) header;
		Group group = gHeader.getGroup(rowIndex, columnIndex);
		TableCellRenderer renderer = header.getDefaultRenderer();
		return renderer.getTableCellRendererComponent(header.getTable(),
				group.getHeaderValue(),
				false, false, -1, columnIndex);
	}

	/**
	 * 获取列头的高度
	 * 
	 * @return
	 */
	private int getHeaderHeight() {
		int height = 0;
		int tempHeight = 0;
		GroupableTableHeader gHeader = (GroupableTableHeader) header;
		TableColumnModel cm = header.getColumnModel();
		for (int column = 0, columnCount = cm.getColumnCount(); column < columnCount; column++) {
			tempHeight = 0;
			List<Group> groups = gHeader.getGroupsAtColumn(column);
			for (Group group : groups) {
				TableCellRenderer renderer = gHeader.getDefaultRenderer();
				Component comp = renderer.getTableCellRendererComponent(header.getTable(),
						group
								.getHeaderValue(), false, false, -1, column);
				int rendererHeight = comp.getPreferredSize().height;
				tempHeight += rendererHeight;
			}
			height = Math.max(height, tempHeight);
		}
		return height;
	}

	private Dimension createHeaderSize(long width) {
		// TableColumnModel columnModel = header.getColumnModel();
		// None of the callers include the intercell spacing, do it here.
		if (width > Integer.MAX_VALUE) {
			width = Integer.MAX_VALUE;
		}
		return new Dimension((int) width, getHeaderHeight());
	}

	/**
	 * Return the minimum size of the header. The minimum width is the sum of
	 * the minimum widths of
	 * each column (plus inter-cell spacing).
	 */
	public Dimension getMinimumSize(JComponent c) {
		long width = 0;
		Enumeration<TableColumn> enumeration = header.getColumnModel().getColumns();
		while (enumeration.hasMoreElements()) {
			TableColumn aColumn = (TableColumn) enumeration.nextElement();
			width = width + aColumn.getMinWidth();
		}
		return createHeaderSize(width);
	}

	/**
	 * Return the preferred size of the header. The preferred height is the
	 * maximum of the preferred
	 * heights of all of the components provided by the header renderers. The
	 * preferred width is the
	 * sum of the preferred widths of each column (plus inter-cell spacing).
	 */
	public Dimension getPreferredSize(JComponent c) {
		long width = 0;
		Enumeration<TableColumn> enumeration = header.getColumnModel().getColumns();
		while (enumeration.hasMoreElements()) {
			TableColumn aColumn = (TableColumn) enumeration.nextElement();
			width = width + aColumn.getPreferredWidth();
		}
		return createHeaderSize(width);
	}

	/**
	 * Return the maximum size of the header. The maximum width is the sum of
	 * the maximum widths of
	 * each column (plus inter-cell spacing).
	 */
	public Dimension getMaximumSize(JComponent c) {
		long width = 0;
		Enumeration<TableColumn> enumeration = header.getColumnModel().getColumns();
		while (enumeration.hasMoreElements()) {
			TableColumn aColumn = (TableColumn) enumeration.nextElement();
			width = width + aColumn.getMaxWidth();
		}
		return createHeaderSize(width);
	}
}