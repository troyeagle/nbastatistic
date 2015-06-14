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
	
	public static Color TextColor;
	
	public static Color HeadPanelBgColor;
	
	public static Color ThemeColor;

	static {
		initialize();
	}
	/**
	 * 初始化设定
	 */
	private static void initialize() {
		ThemeColor = new Color(210, 71, 38);

		Font f = new Font("DengXian", Font.PLAIN, 0);
		//		try {
		//			f = Font.createFont(Font.TRUETYPE_FONT, new File("./font/DengXian.ttf"));
		//		} catch (FontFormatException | IOException e) {
		//			f = UIManager.getDefaults().getFont("JLabel");
		//		}
		TitleFont = f.deriveFont(Font.PLAIN, 30);
		SubTitleFont = f.deriveFont(Font.PLAIN, 18);
		ContentFont = f.deriveFont(Font.PLAIN, 20);
		TableFont = f.deriveFont(Font.PLAIN, 16);
		SmallFont = f.deriveFont(Font.PLAIN, 14);

		TitleBgColor = new Color(0, 122, 204);
		TitleForeColor = Color.WHITE;
		
		TextColor = Color.BLACK;

		TableHeaderFgColor = Color.WHITE;
		TableHeaderBgColor = Color.DARK_GRAY.brighter();

		TableSelectionBgColor = TitleBgColor;
		TableSelectionFgColor = Color.WHITE;
		TableFgColor = Color.BLACK;


		TableView.setDefaultTableViewUI(new FlatTableviewUI());
		
		HeadPanelBgColor = Color.LIGHT_GRAY;
	}
}
