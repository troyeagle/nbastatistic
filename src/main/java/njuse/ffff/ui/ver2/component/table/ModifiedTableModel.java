package njuse.ffff.ui.ver2.component.table;

import javax.swing.table.DefaultTableModel;

public class ModifiedTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	private boolean editable = false;

	public ModifiedTableModel() {
		super();
	}

	public ModifiedTableModel(Object[][] values, Object[] columns) {
		super(values, columns);
	}

	public void setCellEditable(boolean b) {
		editable = b;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return editable;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		Class<?> returnValue;
		if (columnIndex >= 0 && columnIndex < getColumnCount()) {
			try {
				Object value = null;

				int index = 0;
				do {
					value = getValueAt(index++, columnIndex);
				} while (value == null);

				returnValue = value.getClass();
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
}
