package njuse.ffff.ui.ver2;

import java.io.File;
import java.nio.file.Paths;

import javax.swing.ImageIcon;

public class ImageUtils {

	private static String dataPath = "CSEIII data/迭代一数据";
	private static final String teamPath = "teams";
	private static final String playerPath = "players";
	private static final String portraitPath = "portriait";
	private static final String actionPath = "action";

	public static ImageIcon getTeamIcon(String teamName) {
		return getImg(teamName, dataPath, teamPath);
	}

	public static ImageIcon getPlayerImg(String playerName) {
		return getImg(playerName, dataPath, playerPath, portraitPath);
	}

	public static ImageIcon getPlayerAction(String playerName) {
		return getImg(playerName, dataPath, playerPath, actionPath);
	}

	private static ImageIcon getImg(String pngName, String... paths) {
		String fileName = pngName;
		if (!fileName.toLowerCase().endsWith(".png"))
			fileName += ".png";
		String pathFolder = Paths.get("", paths).toString();
//		return Paths.get(pathFolder, fileName).toString();
		String pngFileName = Paths.get(pathFolder, fileName).toString();
		// TODO
		return null;
	}

}
