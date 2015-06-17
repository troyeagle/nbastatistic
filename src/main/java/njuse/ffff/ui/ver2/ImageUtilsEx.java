package njuse.ffff.ui.ver2;

import java.awt.Image;
import java.util.HashMap;

import javax.swing.ImageIcon;

import njuse.ffff.util.TeamNameAndAbbr;

/**
 * 管理ImageIcon的类，加入了cache（？
 *
 * @author Li
 */
public class ImageUtilsEx extends ImageUtils {

	private static HashMap<String, ImageIcon> imageMap;

	public static final char ORIGIN = 'O';
	public static final char XL = 'X';
	public static final char L = 'L';
	public static final char M = 'M';
	public static final char S = 'S';
	public static final char MS = 'T';

	private static final String NULL = "null:";

	static {
		imageMap = new HashMap<>();

		ImageIcon nullImg = new ImageIcon("img/no_image.png");
		imageMap.put(NULL + ORIGIN, nullImg);
		imageMap.put(NULL + XL, zoom(nullImg, XL));
		imageMap.put(NULL + L, zoom(nullImg, L));
		imageMap.put(NULL + M, zoom(nullImg, M));
		imageMap.put(NULL + MS, zoom(nullImg, MS));
		imageMap.put(NULL + S, zoom(nullImg, S));

		nullImg = new ImageIcon("img/no_image_action.png");
		imageMap.put(NULL + "A" + ORIGIN, nullImg);
		imageMap.put(NULL + "A" + L, zoom(nullImg, L));
		imageMap.put(NULL + "A" + M, zoom(nullImg, M));
		imageMap.put(NULL + "A" + S, zoom(nullImg, S));
	}

	private static ImageIcon zoom(ImageIcon icon, char size) {
		int h = 0;
		switch (size) {
		case ORIGIN:
			return icon;
		case XL:
			h = 200;
			break;
		case L:
			h = 160;
			break;
		case M:
			h = 90;
			break;
		case S:
			h = 20;
			break;
		case MS:
			h = 30;
			break;
		}
		int w = h * icon.getIconWidth() / icon.getIconHeight();

		return new ImageIcon(icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));
	}

	private static ImageIcon zoomAction(ImageIcon icon, char size) {
		int w = 0;
		switch (size) {
		case ORIGIN:
		case XL:
			return icon;
		case L:
			w = 330;
			break;
		case M:
			w = 220;
			break;
		case S:
			w = 110;
			break;
		}
		int h = w * icon.getIconWidth() / icon.getIconWidth();
		return new ImageIcon(icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH));

	}

	public static ImageIcon getTeamIcon(String teamName) {
		return getTeamIcon(teamName, ORIGIN);
	}

	public static ImageIcon getTeamIcon(String teamName, char size) {
		if (teamName.length() > 3) {
			String abbr = TeamNameAndAbbr.getInstance().getAbbr(teamName);
			teamName = abbr == null ? teamName : abbr;
		}
		String key = teamName + ":T" + size;

		ImageIcon icon = imageMap.get(key);
		if (icon == null) {
			icon = ImageUtils.getTeamIcon(teamName);

			if (icon == null)
				return imageMap.get(NULL + size);

			icon = zoom(icon, size);
			imageMap.put(key, icon);
		}
		return icon;
	}

	public static ImageIcon getPlayerImg(String playerName) {
		return getPlayerImg(playerName, ORIGIN);
	}

	public static ImageIcon getPlayerImg(String playerName, char size) {
		String key = playerName + ":I" + size;

		ImageIcon icon = imageMap.get(key);
		if (icon == null) {
			icon = ImageUtils.getPlayerImg(playerName);

			if (icon == null)
				return imageMap.get(NULL + size);

			icon = zoom(icon, size);
			imageMap.put(key, icon);
		}
		return icon;
	}

	public static ImageIcon getPlayerAction(String playerName) {
		return getPlayerAction(playerName, ORIGIN);
	}

	public static ImageIcon getPlayerAction(String playerName, char size) {
		String key = playerName + ":A" + size;

		ImageIcon icon = imageMap.get(key);
		if (icon == null) {
			icon = ImageUtils.getPlayerAction(playerName);

			if (icon == null)
				return imageMap.get(NULL + "A" + size);

			icon = zoomAction(icon, size);
			imageMap.put(key, icon);
		}
		return icon;
	}
}
