package njuse.ffff.ui.ver2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import njuse.ffff.presenter.teamController.TeamInfoController;
import njuse.ffff.ui.component.LabelEx;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.uiservice.TeamProfileService;

public class TeamProfilePane extends PanelEx implements TeamProfileService {

	private static final long serialVersionUID = 1L;

	private static final String[] labelsName = { "位置", "联盟", "副联盟", "主场", "创建时间" };

	private LabelEx teamIcon;

	private LabelEx nameLabel;
	private LabelEx abbrLabel;

	private LabelEx[] properties;

	public TeamProfilePane() {
		super(new BorderLayout(10, 10));
		setBackground(Color.LIGHT_GRAY);
		setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));

		teamIcon = new LabelEx();
		teamIcon.setOpaque(false);

		nameLabel = new LabelEx();
		nameLabel.setOpaque(false);
		nameLabel.setForeground(Color.BLACK);
		nameLabel.setFont(UIConfig.TitleFont);

		abbrLabel = new LabelEx();
		abbrLabel.setOpaque(false);
		abbrLabel.setForeground(Color.BLACK);
		abbrLabel.setFont(UIConfig.SubTitleFont);

		PanelEx namePane = new PanelEx(new GridLayout(3, 1));
		namePane.setOpaque(false);
		namePane.add(nameLabel);
		namePane.add(abbrLabel);
		PanelEx p = new PanelEx();
		p.setOpaque(false);
		namePane.add(p);
		namePane.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

		PanelEx propertyPane = new PanelEx(new GridLayout(3, 2, 20, 0));
		propertyPane.setOpaque(false);
		propertyPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		properties = new LabelEx[labelsName.length];
		for (int i = 0; i < labelsName.length; i++) {
			properties[i] = new LabelEx();
			properties[i].setOpaque(false);
			properties[i].setName(labelsName[i]);
			properties[i].setFont(UIConfig.ContentFont);
			properties[i].setForeground(Color.BLACK);
			propertyPane.add(properties[i]);
		}

		PanelEx detailPane = new PanelEx(new BorderLayout(20, 0));
		detailPane.setOpaque(false);
		detailPane.add(propertyPane);
		detailPane.add(namePane, BorderLayout.WEST);

		add(teamIcon, BorderLayout.WEST);
		add(detailPane);
	}

	public void setTeam(String name) {
		TeamInfoController.getInstance().setTeamProfilePanel(this, name);
	}

	@Override
	public void setProfile(String name, String abbr, String location, String league,
			String subleague, String homeCourt, String foundYear) {
		ImageIcon icon = ImageUtils.getTeamIcon(abbr);
		if (icon == null)
			icon = new ImageIcon("./img/no_image.png");
		Image img = icon.getImage();
		int imgWidth = 200;
		int imgHeight = (int) (icon.getIconHeight() / ((double) icon.getIconWidth()) * 200);
		icon = new ImageIcon(img.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH));
		teamIcon.setIcon(icon);

		nameLabel.setText(name);
		abbrLabel.setText(abbr);

		String[] properties = { location, league, subleague, homeCourt, foundYear };
		for (int i = 0; i < properties.length; i++) {
			this.properties[i].setText(labelsName[i] + "　　" + properties[i]);
		}
	}

}
