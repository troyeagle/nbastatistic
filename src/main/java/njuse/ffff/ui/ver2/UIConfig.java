package njuse.ffff.ui.ver2;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

public class UIConfig {

	/** 标题字体 */
	public static Font TitleFont;
	/** 标题栏背景色 */
	public static Color TitleBgColor;
	/** 标题栏前景色 */
	public static Color TitleForeColor;
	/** 副标题字体 */
	public static Font SubTitleFont;
	/** 正文字体 */
	public static Font ContentFont;

	public static Color TableSelectionBgColor;

	public static Color TableSelectionFgColor;

	//	public static Color TableBgColor;

	public static Color TableFgColor;

	public static Color TableHeaderBgColor;

	public static Color TableHeaderFgColor;
	
	public static Font SmallFont;

	/**
	 * 初始化设定
	 */
	public static void initialize() {
		System.setProperty("awt.useSystemAAFontSettings", "on");
		System.setProperty("swing.aatext", "true");

		// 初始化表格属性
		UIManager.put("Table.background", new ColorUIResource(new Color(255, 255, 255, 64)));
		UIManager.put("Table.alternateRowColor", new Color(255, 255, 255, 128));

		Font f;
		try {
			f = Font.createFont(Font.TRUETYPE_FONT, new File("./font/DengXian.ttf"));
		} catch (FontFormatException | IOException e) {
			f = UIManager.getDefaults().getFont("JLabel");
		}
		TitleFont = f.deriveFont(Font.PLAIN, 30);
		SubTitleFont = f.deriveFont(Font.PLAIN, 18);
		ContentFont = f.deriveFont(Font.PLAIN, 20);
		SmallFont = f.deriveFont(Font.PLAIN, 15);

		TitleBgColor = Color.GRAY;
		TitleForeColor = Color.WHITE;

		TableSelectionBgColor = new Color(255, 255, 255, 150);
		TableSelectionFgColor = Color.BLACK;
		TableFgColor = Color.WHITE;

		TableHeaderBgColor = new Color(255, 255, 255, 192);
	}
}
