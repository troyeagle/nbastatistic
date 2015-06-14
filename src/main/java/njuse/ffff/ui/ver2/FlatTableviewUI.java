package njuse.ffff.ui.ver2;

import java.awt.Color;

import njuse.ffff.ui.ver2.component.TableViewUI;

import com.sun.java.swing.plaf.windows.WindowsScrollBarUI;

@SuppressWarnings("restriction")
public class FlatTableviewUI extends TableViewUI {

	public FlatTableviewUI() {
		rowColor1 = new Color(200, 200, 200);
		rowColor2 = new Color(185, 185, 185);
		
		headerFont = UIConfig.SmallFont;
		headerFgColor = UIConfig.TableHeaderFgColor;
		headerBgColor = UIConfig.TableHeaderBgColor;
		
		tableFont = UIConfig.TableFont;
		tableFgColor = UIConfig.TableFgColor;
		
		selectionFgColor = UIConfig.TableSelectionFgColor;
		selectionBgColor = UIConfig.TableSelectionBgColor;
		
		scrollBarUI = new WindowsScrollBarUI();
	}
}
