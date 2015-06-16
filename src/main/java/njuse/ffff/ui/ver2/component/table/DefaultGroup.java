package njuse.ffff.ui.ver2.component.table;

/**
 * 默认Group实现
 * 
 * @author Brad.Wu
 * @version 1.0
 */
public class DefaultGroup implements Group {
	private int row = 0;
	private int column = 0;
	private int rowSpan = 1;
	private int columnSpan = 1;
	private Object headerValue = null;

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.eplat.realty.view.component.table.Group#getRow()
	 */
	public int getRow() {
		return this.row;
	}

	/**
	 * @param row
	 *            要设置的 row。
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.eplat.realty.view.component.table.Group#getColumn()
	 */
	public int getColumn() {
		return this.column;
	}

	/**
	 * @param column
	 *            要设置的 column。
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.eplat.realty.view.component.table.Group#getColumnSpan()
	 */
	public int getColumnSpan() {
		return this.columnSpan;
	}

	/**
	 * @param columnSpan
	 *            要设置的 columnSpan。
	 */
	public void setColumnSpan(int columnSpan) {
		this.columnSpan = columnSpan;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.eplat.realty.view.component.table.Group#getRowSpan()
	 */
	public int getRowSpan() {
		return this.rowSpan;
	}

	/**
	 * @param rowSpan
	 *            要设置的 rowSpan。
	 */
	public void setRowSpan(int rowSpan) {
		this.rowSpan = rowSpan;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see com.eplat.realty.view.component.table.Group#getHeaderValue()
	 */
	public Object getHeaderValue() {
		return this.headerValue;
	}

	/**
	 * @param headerValue
	 *            要设置的 headerValue。
	 */
	public void setHeaderValue(Object headerValue) {
		this.headerValue = headerValue;
	}
}
