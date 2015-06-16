package njuse.ffff.ui.ver2.component.table;

import java.awt.Component;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 * 可以合并的列头
 * 
 * @author Brad.Wu
 * @version 1.0
 */
@SuppressWarnings("serial")
public class GroupableTableHeader extends JTableHeader {
	private int rowCount = 0;
	private int columnCount = 0;
	private List<Group> groups = new ArrayList<Group>();

	public GroupableTableHeader() {
		// 这个是必须的, 因为如果可以拖动列的位置, 那么一切都完蛋了.
		// 如果你想实现这个功能, 那么只能你自己去做了, 我可不想做这个, 看上去超烦的
		this.setReorderingAllowed(false);
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see javax.swing.table.JTableHeader#updateUI()
	 */
	@Override
	public void updateUI() {
		setUI(new GroupableTableHeaderUI());
	}

	/*
	 * 获取指定行列的位置
	 */
	public Rectangle getHeaderRect(int row, int column) {
		Rectangle r = new Rectangle();
		TableColumnModel cm = getColumnModel();

		Group group = this.getGroup(row, column);
		r.height = getHeight();

		if (column < 0) {
			// x = width = 0;
			if (!getComponentOrientation().isLeftToRight()) {
				r.x = getWidthInRightToLeft();
			}
		} else if (column >= cm.getColumnCount()) {
			if (getComponentOrientation().isLeftToRight()) {
				r.x = getWidth();
			}
		} else {
			for (int i = 0; i < group.getColumn(); i++) {
				r.x += cm.getColumn(i).getWidth();
			}
			for (int i = group.getColumn(), j = group.getColumn() + group.getColumnSpan() - 1; i < j; i++) {
				r.width += cm.getColumn(i).getWidth();
			}
			if (!getComponentOrientation().isLeftToRight()) {
				r.x = getWidthInRightToLeft() - r.x - r.width;
			}
			// r.width = cm.getColumn(column).getWidth();
		}
		return r;
	}

	/**
	 * 获取Group的Y位置
	 * 
	 * @param group
	 * @return
	 */
	public int getYOfGroup(Group group) {
		int row = group.getRow();
		TableCellRenderer renderer = this.getDefaultRenderer();
		Component comp = renderer.getTableCellRendererComponent(getTable(),
				group.getHeaderValue(),
				false, false, group.getRow(), group.getColumn());
		return row * comp.getPreferredSize().height;
	}

	/**
	 * 获取Group的高度
	 * 
	 * @param group
	 * @return
	 */
	public int getHeightOfGroup(Group group) {
		int rowSpan = group.getRowSpan();
		TableCellRenderer renderer = this.getDefaultRenderer();
		Component comp = renderer.getTableCellRendererComponent(getTable(),
				group.getHeaderValue(),
				false, false, group.getRow(), group.getColumn());
		return rowSpan * comp.getPreferredSize().height;
	}

	private int getWidthInRightToLeft() {
		if ((table != null) && (table.getAutoResizeMode() != JTable.AUTO_RESIZE_OFF)) { return table
				.getWidth(); }
		return super.getWidth();
	}

	/**
	 * 增加Group
	 * 
	 * @param group
	 */
	public void addGroup(Group group) {
		groups.add(group);
		int row = group.getRow();
		int rowSpan = group.getRowSpan();
		rowCount = Math.max(rowCount, row + rowSpan);
		int column = group.getColumn();
		int columnSpan = group.getColumnSpan();
		columnCount = Math.max(columnCount, column + columnSpan);
	}

	/**
	 * 移除所有Group
	 */
	public void removeAllGroups() {
		groups.clear();
	}

	/**
	 * 获取所有的Group
	 * 
	 * @return
	 */
	public List<Group> getGroups() {
		List<Group> list = new ArrayList<Group>();
		list.addAll(groups);
		return list;
	}

	/**
	 * 获取指定列上的Group
	 * 
	 * @param columnIndex
	 * @return
	 */
	public List<Group> getGroupsAtColumn(int columnIndex) {
		List<Group> list = new ArrayList<Group>();
		for (Group group : groups) {
			int minColumnIndex = group.getColumn();
			int maxColumnIndex = minColumnIndex + group.getColumnSpan() - 1;
			if (minColumnIndex <= columnIndex && maxColumnIndex >= columnIndex) {
				list.add(group);
			}
		}
		return list;
	}

	/**
	 * 获取指定行上的所有Group
	 * 
	 * @param rowIndex
	 * @return
	 */
	public List<Group> getGroupsAtRow(int rowIndex) {
		List<Group> list = new ArrayList<Group>();
		for (Group group : groups) {
			int minRowIndex = group.getRow();
			int maxRowIndex = minRowIndex + group.getRowSpan() - 1;
			if (minRowIndex <= rowIndex && maxRowIndex >= rowIndex) {
				list.add(group);
			}
		}
		return list;
	}

	/**
	 * 获取行数
	 * 
	 * @return
	 */
	public int getRowCount() {
		return this.rowCount;
	}

	/**
	 * @return Returns the columnCount.
	 */
	public int getColumnCount() {
		return this.columnCount;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see javax.swing.table.JTableHeader#setTable(javax.swing.JTable)
	 */
	@Override
	public void setTable(JTable table) {
		super.setColumnModel(table.getColumnModel());
		super.setTable(table);
	}

	/**
	 * 获取指定行列的Group
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public Group getGroup(int row, int column) {
		for (Group group : groups) {
			int rowIndex = group.getRow();
			int columnIndex = group.getColumn();
			int rowSpan = group.getRowSpan();
			int columnSpan = group.getColumnSpan();
			if (rowIndex <= row && rowIndex + rowSpan > row && columnIndex <= column
					&& columnIndex + columnSpan > column)
				return group;
		}
		return null;
	}

	/**
	 * (非 Javadoc)
	 * 
	 * @see javax.swing.table.JTableHeader#createDefaultRenderer()
	 */
	@Override
	protected TableCellRenderer createDefaultRenderer() {
		return new SimpleTableHeaderRenderer();
	}
}