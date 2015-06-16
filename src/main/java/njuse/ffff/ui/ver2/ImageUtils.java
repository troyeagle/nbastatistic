package njuse.ffff.ui.ver2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.swing.ImageIcon;

import njuse.ffff.util.SvgConverter;
import njuse.ffff.util.TeamNameAndAbbr;

import org.apache.batik.transcoder.TranscoderException;

/**
 * 获取图片的工具类
 * 
 * @author Li
 *
 */
public class ImageUtils {

	private static String dataPath = "CSEIII data/迭代一数据";
	private static final String teamPath = "CSEIII data/迭代一数据/teams";
	private static final String playerPath = "CSEIII data/迭代一数据/players";
	private static final String portraitPath = "CSEIII data/迭代一数据/players/portrait";
	private static final String actionPath = "CSEIII data/迭代一数据/players/action";

	/**
	 * 获取球队的队徽
	 * 
	 * @param teamName
	 * @return
	 */
	public static ImageIcon getTeamIcon(String teamName) {
		if (teamName.length() > 3) {
			String abbr = TeamNameAndAbbr.getInstance().getAbbr(teamName);
			if (abbr == null) {
				teamName = abbr;
			}
		}
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
		return teamPath;
	}

	public static String getPlayerPath() {
		return playerPath;
	}

	public static String getPlayerImgPath() {
		return portraitPath;
	}

	public static String getPlayerActionPath() {
		return actionPath;
	}

	public static String getDataPath() {
		return dataPath;
	}
}
