package njuse.ffff.ui.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.Collator;
import java.util.Arrays;
import java.util.Locale;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class MyTable extends JTable{
	private static final long serialVersionUID = -153399578952753090L;
	/** 需要排序的列。 */
	private int sortColumn;

	/** 行对象。 */
	private Row rows[];

	/** 排序方式。 */
	private boolean ascending;

	/** 向上图标。 */
	final static Icon upIcon = new UpDownArrow(0);

	/** 向下图标。 */
	final static Icon downIcon = new UpDownArrow(1);

	/** 要排序的列。 */
	private int sortableColumns[];

	private DefaultTableModelListener tableModelListener = new DefaultTableModelListener();
	
	private Color background = new Color(99,43,142);
	
	private MyTable myTable;

	public MyTable() {
		super();
		setRowSorter(new TableRowSorter<TableModel>(getTableModel()));
	}

	public MyTable(TableModel dm) {
		super(dm, null, null);
		myTable = this;
		
		JTableHeader jtableheader = getTableHeader();
		jtableheader.addMouseListener(new MouseAdapter() {
			public void mouseReleased (MouseEvent e) {  
                if (! e.isShiftDown())  
                	myTable.clearSelection();
                //获取点击的列索引  
                int pick = myTable.getTableHeader().columnAtPoint(e.getPoint());  
                //设置选择模型 
                myTable.addColumnSelectionInterval (pick, pick);
	        }  
			
			public void mouseClicked(MouseEvent event) {
				if (event.getSource() == getTableHeader()) {
					getTableHeader().removeMouseListener(this);
					int i = columnAtPoint(event.getPoint());
					int j = convertColumnIndexToModel(i);

					// 转换出用户想排序的列和底层数据的列，然后判断
					if (!columnIsSortable(j)) {
						return;
					}
					if (j == sortColumn) {
						ascending = !ascending;
					} else {
						ascending = true;
						sortColumn = j;
					}

					EventQueue.invokeLater(new Runnable() {
						public void run() {
							sort();
						}
					});

					getTableHeader().addMouseListener(this);
				}
			}
		});
		jtableheader.setDefaultRenderer(createHeaderRenderer());
		if (getRowCount() > 0) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					reinitialize();
				}
			});
		}
		// setRowSorter(new TableRowSorter<TableModel>(defModel));
		this.setOpaque(false);
		this.setForeground(Color.WHITE);
		this.setSelectionForeground(Color.CYAN);
		//设置表头自动长度
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		jtableheader.setBackground(background);
		jtableheader.setForeground(Color.WHITE);
		this.repaint();
	}

	private boolean columnIsSortable(int i) {
		if (rows != null) {
			if (sortableColumns != null) {
				for (int j = 0; j < sortableColumns.length; j++) {
					if (i == sortableColumns[j]) {
						return true;
					}
				}

			} else {
				return true;
			}
		}
		return false;
	}

	public synchronized TableModel getTableModel() {
		return getModel();
	}

	/**
	 * <BR>
	 * <UL>
	 * 根据表头排序。
	 * <LI>根据表的表头进行排序。</LI>
	 * </UL>
	 * 
	 * @param 无
	 *            。
	 * @return 无。
	 * @throws 无
	 *             。
	 */
	public void sort() {
		DefaultTableModel tableModel = (DefaultTableModel) getTableModel();
		if (rows == null) {
		} else {
			tableModel.removeTableModelListener(tableModelListener);
			// 分组排序。
			Arrays.sort(rows);
			// 重新设置数据。
			resetData();
			tableModel.fireTableDataChanged();
			tableModel.addTableModelListener(tableModelListener);
			revalidate();
			repaint();
		}
	}
	
	/**
	 * <BR>
	 * <UL>
	 * 重新设置数据。
	 * <LI>重新设置数据。</LI>
	 * </UL>
	 * 
	 * @param 无
	 *            。
	 * @return 无。
	 * @throws 无
	 *             。
	 */
	@SuppressWarnings("unchecked")
	public void resetData() {
		DefaultTableModel tableModel = (DefaultTableModel) getTableModel();
		Vector data = new Vector(tableModel.getRowCount());

		for (int i = 0; i < tableModel.getRowCount(); i++) {

			final Vector vv = new Vector(tableModel.getColumnCount());
			for (int j = 0; j < tableModel.getColumnCount(); j++) {
				vv.add(tableModel.getValueAt(i, j));
			}
			data.add(vv);
		}

		for (int i = 0; i < rows.length; i++) {
			if (rows[i].index != i) {
				final Vector vv = (Vector) data.get(rows[i].index);
				for (int j = 0; j < tableModel.getColumnCount(); j++) {
					tableModel.setValueAt(vv.get(j), i, j);
				}
			}
		}
	}

	/**
	 * <BR>
	 * <UL>
	 * 对table内容重新初始化。
	 * <LI>对table内容重新初始化。</LI>
	 * </UL>
	 * 
	 * @param 无
	 *            。
	 * @return 无。
	 * @throws 无
	 *             。
	 */
	public synchronized void reinitialize() {
		rows = null;
		if (getRowCount() > 0) {
			rows = new Row[getRowCount()];
			for (int i = 0; i < rows.length; i++) {
				rows[i] = new Row();
				rows[i].index = i;
			}
		}
	}

	/**
	 * <BR>
	 * <UL>
	 * 得到一个单元格渲染器。
	 * <LI>得到一个单元格渲染器。</LI>
	 * </UL>
	 * 
	 * @param 无
	 *            。
	 * @return defaultHeaderRenderer 表头渲染器。
	 * @throws 无
	 *             。
	 */
	private TableCellRenderer createHeaderRenderer() {
		DefaultTableCellRenderer defaultHeaderRenderer = new SortHeaderRenderer();
		defaultHeaderRenderer.setHorizontalAlignment(0);
		defaultHeaderRenderer.setHorizontalTextPosition(2);
		return defaultHeaderRenderer;
	}

	/**
	 * <BR>
	 * <UL>
	 * 利用正则表达式判断一个字符是否是数字。
	 * <LI>利用正则表达式判断一个字符是否是数字。</LI>
	 * </UL>
	 * 
	 * @param str
	 *            字符串。
	 * @return 是否是数字。
	 * @throws 无
	 *             。
	 */
	private boolean isNumeric(String str) {
		if ("".equals(str.trim())) {
			return false;
		}
		// 是否是int型。
		boolean isInt = false;
		// 是否是float型。
		boolean isFloat = false;

		// 匹配int行。
		Pattern pattern = Pattern.compile("[0-9]*");
		// 匹配结果。
		isInt = pattern.matcher(str).matches();

		// 匹配float型。
		pattern = Pattern.compile("^[+-]?([0-9]*\\.?[0-9]+|[0-9]+\\.?[0-9]*)([eE][+-]?[0-9]+)?$");
		// 匹配结果。
		isFloat = pattern.matcher(str).matches();

		// 是否是数字。
		return isInt | isFloat;
	}
	
	/**
	 * 设置单元格为透明
	 */
	public Component prepareRenderer(TableCellRenderer renderer,int row,int column){
		Component c=super.prepareRenderer(renderer,row,column);
		if(c instanceof JComponent){
			((JComponent)c).setOpaque(false);
		}
		return c;
	}

	/**
	 * <BR>
	 * 功能：表头渲染器。
	 * <UL>
	 * <LI>功能：内部类，表头渲染器。</LI>
	 * </UL>
	 * <BR>
	 * 
	 * <B>利用例:</B><BR>
	 * 
	 * <PRE>
	 * 
	 * 
	 * </PRE>
	 * 
	 * <BR>
	 * 
	 * <B>修订履历</B><BR>
	 * 
	 * <PRE>
	 * 
	 * 
	 * </PRE>
	 * 
	 * @author Zaki
	 * @since 1.00
	 */
	private class SortHeaderRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = -8059140121463202150L;

		public Component getTableCellRendererComponent(JTable jtable,
				Object obj, boolean flag, boolean flag1, int i, int j) {
			if (jtable != null) {
				JTableHeader jtableheader = jtable.getTableHeader();
				if (jtableheader != null) {
					setForeground(jtableheader.getForeground());
					setBackground(jtableheader.getBackground());
					setFont(jtableheader.getFont());
				}
			}
			setText(obj != null ? obj.toString() : "");
			int k = jtable.convertColumnIndexToModel(j);
			if (k == sortColumn) {
				setIcon(ascending ? upIcon : downIcon);
			} else {
				setIcon(null);
			}
			setBorder(UIManager.getBorder("TableHeader.cellBorder"));
			return this;
		}
	}

	private class Row implements Comparable<Object> {
		private TableModel tableModel = getTableModel();

		private int index;

		public int compareTo(Object obj) {
			Row row = (Row) obj;
			Collator cnCollator = Collator.getInstance(Locale.getDefault());

			if (tableModel.getRowCount() < row.index) {
				// 出现脏读直接返回，防止程序继续执行时出错。
				return -1;
			}

			Object obj1 = tableModel.getValueAt(index, sortColumn);
			Object obj2 = tableModel.getValueAt(row.index, sortColumn);
			double num1;
			double num2;
			if (ascending) {
				if (!(obj1 instanceof Comparable<?>)) {
					return -1;
				}
				if (!(obj2 instanceof Comparable<?>)) {
					return 1;
				} else {
					if (isNumeric(String.valueOf(obj1))
							&& isNumeric(String.valueOf(obj2))) {
						num1 = Double.parseDouble(String.valueOf(obj1));
						num2 = Double.parseDouble(String.valueOf(obj2));
						if (num1 > num2) {
							return 1;
						} else {
							return -1;
						}
					}else if((String.valueOf(obj1)).equals("NaN")){
						return -1;
					}else if((String.valueOf(obj2)).equals("NaN")){
						return 1;
					}
					else {
						return cnCollator.compare(obj1, obj2);
					}
				}
			}
			if (!(obj1 instanceof Comparable<?>)) {
				return 1;
			}
			if (!(obj2 instanceof Comparable<?>)) {
				return -1;
			} else {
				if (isNumeric(String.valueOf(obj1))
						&& isNumeric(String.valueOf(obj2))) {
					num1 = Double.parseDouble(String.valueOf(obj1));
					num2 = Double.parseDouble(String.valueOf(obj2));//TODO
					if (num1 > num2) {
						return -1;
					} else {
						return 1;
					}
				}else if((String.valueOf(obj1)).equals("NaN")){
					return 1;
				}else if((String.valueOf(obj2)).equals("NaN")){
					return -1;
				} 
				else {
					return cnCollator.compare(obj2, obj1);
				}
			}
		}
	}

	/**
	 * <BR>
	 * 功能：table模型内部监听类。
	 * <UL>
	 * <LI>功能：table模型内容监听类。</LI>
	 * </UL>
	 * <BR>
	 * 
	 * <B>利用例:</B><BR>
	 * 
	 * <PRE>
	 * 
	 * 
	 * </PRE>
	 * 
	 * <BR>
	 * 
	 * <B>修订履历</B><BR>
	 * 
	 * <PRE>
	 * 
	 * 
	 * </PRE>
	 * 
	 * @since 1.00
	 */
	private class DefaultTableModelListener implements TableModelListener {
		public void tableChanged(TableModelEvent e) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					reinitialize();
				}
			});
		}
	}
}
