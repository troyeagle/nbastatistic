package njuse.ffff.ui.ver2;

import java.awt.Color;
import java.awt.Font;

import njuse.ffff.ui.ver2.component.TableView;

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

	public static Font TableFont;

	public static Color TableSelectionBgColor;

	public static Color TableSelectionFgColor;

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

		Font f = new Font("Dengxian", Font.PLAIN, 0);
		//		try {
		//			f = Font.createFont(Font.TRUETYPE_FONT, new File("./font/DengXian.ttf"));
		//		} catch (FontFormatException | IOException e) {
		//			f = UIManager.getDefaults().getFont("JLabel");
		//		}
		TitleFont = f.deriveFont(Font.PLAIN, 30);
		SubTitleFont = f.deriveFont(Font.PLAIN, 18);
		ContentFont = f.deriveFont(Font.PLAIN, 20);
		TableFont = f.deriveFont(Font.PLAIN, 16);
		SmallFont = f.deriveFont(Font.PLAIN, 15);

		TitleBgColor = Color.GRAY;
		TitleForeColor = Color.WHITE;

		TableSelectionBgColor = new Color(255, 255, 255, 150);
		TableSelectionFgColor = Color.BLACK;
		TableFgColor = Color.WHITE;

		TableHeaderBgColor = new Color(255, 255, 255, 192);

		TableView.setDefaultTableViewUI(new FlatTableviewUI());
	}
}
