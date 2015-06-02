package njuse.ffff.ui.ver2.component;

import java.awt.Color;
import java.awt.Font;

import javax.swing.plaf.ScrollBarUI;

public class TableViewUI {

	protected Font headerFont;
	protected Color headerFgColor;
	protected Color headerBgColor;

	protected Font tableFont;
	protected Color tableFgColor;

	protected Color selectionFgColor;
	protected Color selectionBgColor;

	protected ScrollBarUI scrollBarUI;

	protected Color rowColor1;
	protected Color rowColor2;
	
	protected Color FgColor;
	protected Color BgColor;

	public Color getRowColor1() {
		return rowColor1;
	}

	public Color getRowColor2() {
		return rowColor2;
	}

	public ScrollBarUI getScrollBarUI() {
		return scrollBarUI;
	}

	public Font getHeaderFont() {
		return headerFont;
	}

	public Color getHeaderFgColor() {
		return headerFgColor;
	}

	public Color getHeaderBgColor() {
		return headerBgColor;
	}

	public Font getTableFont() {
		return tableFont;
	}

	public Color getTableFgColor() {
		return tableFgColor;
	}

	public Color getSelectionFgColor() {
		return selectionFgColor;
	}

	public Color getSelectionBgColor() {
		return selectionBgColor;
	}
}
