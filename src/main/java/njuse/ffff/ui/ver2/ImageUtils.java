package njuse.ffff.ui.ver2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.swing.ImageIcon;

import njuse.ffff.util.SvgConverter;

import org.apache.batik.transcoder.TranscoderException;

/**
 * 获取图片的工具类
 * 
 * @author Li
 *
 */
public class ImageUtils {

	private static String dataPath = "CSEIII data/迭代一数据";
	private static final String teamPath = "teams";
	private static final String playerPath = "players";
	private static final String portraitPath = "portrait";
	private static final String actionPath = "action";

	/**
	 * 获取球队的队徽
	 * 
	 * @param teamName
	 * @return
	 */
	public static ImageIcon getTeamIcon(String teamName) {
		return getImgFromPngOrSvg(teamName, getTeamImgPath());
	}

	/**
	 * 获取球员的肖像
	 * 
	 * @param playerName
	 * @return
	 */
	public static ImageIcon getPlayerImg(String playerName) {
		return getImgFromPngOrSvg(playerName, getPlayerImgPath());
	}

	/**
	 * 获取球员的动作照片
	 * 
	 * @param playerName
	 * @return
	 */
	public static ImageIcon getPlayerAction(String playerName) {
		return getImgFromPngOrSvg(playerName, getPlayerActionPath());
	}

	private static ImageIcon getImgFromPngOrSvg(String name, String path) {
		String pngName = name;
		if (!pngName.toLowerCase().endsWith(".png"))
			pngName += ".png";
		File pngFile = Paths.get(path, pngName).toFile();

		if (!pngFile.exists()) {	// 不存在png文件，则在目录下寻找svg文件
			String svgName = name;
			if (svgName.toLowerCase().endsWith(".png")) {
				svgName = svgName.substring(0, svgName.length() - 4);
			}
			svgName += ".svg";
			File svgFile = Paths.get(path, svgName).toFile();
			try {
				SvgConverter.convertToPng(svgFile, pngFile);
			} catch (IOException | TranscoderException e) {
				return null;	// 文件不存在
			}
		}
		ImageIcon image = new ImageIcon(pngFile.getPath());
		return image;
	}

	public static String getTeamImgPath() {
		return Paths.get(dataPath, teamPath).toString();
	}

	public static String getPlayerPath() {
		return Paths.get(dataPath, playerPath).toString();
	}

	public static String getPlayerImgPath() {
		return Paths.get(getPlayerPath(), portraitPath).toString();
	}

	public static String getPlayerActionPath() {
		return Paths.get(getPlayerPath(), actionPath).toString();
	}

	public static String getDataPath() {
		return dataPath;
	}

	public static void setDataPath(String path) {
		dataPath = path;
	}
}
