package njuse.ffff.ui.ver2;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import javax.swing.UIManager;

public class UIConfig {

	/** 标题字体 */
	public static Font TitleFont;
	/** 标题栏背景色 */
	public static Color TitleBgColor;
	/** 标题栏前景色 */
	public static Color TitleForeColor;
	/** 副标题字体 */
	public static Font SubTitleFont;
	

	/**
	 * 初始化设定
	 */
	public static void initialize() {
		System.setProperty("awt.useSystemAAFontSettings", "on");
		System.setProperty("swing.aatext", "true");

		Font f;
		try {
			f = Font.createFont(Font.TRUETYPE_FONT, new File("./font/DengXian.ttf"));
		} catch (FontFormatException | IOException e) {
			f = UIManager.getDefaults().getFont("JLabel");
		}
		UIConfig.TitleFont = f.deriveFont(Font.PLAIN, 30);
		UIConfig.SubTitleFont = f.deriveFont(Font.PLAIN, 18);

		TitleBgColor = Color.GRAY;
		TitleForeColor = Color.WHITE;
	}
}
