package njuse.ffff.ui.ver2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import njuse.ffff.presenter.playerController.PlayerInfoController;
import njuse.ffff.ui.component.LabelEx;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.uiservice.PlayerProfileService;

public class PlayerProfilePane extends PanelEx implements PlayerProfileService {

	private static final long serialVersionUID = 1L;

	private final String[] labelsName = { "身高", "体重", "编号", "生日",
			"年龄", "经历", "学校" };

	private LabelEx portrait;	// 照片

	private LabelEx nameLabel;	// 名字
	private LabelEx positionLabel;	// 位置
	private LabelEx teamLabel;

	private LabelEx[] properties;	// 其他各项属性 

	public PlayerProfilePane() {
		super(new BorderLayout(10, 10));
		setBackground(UIConfig.HeadPanelBgColor);
		setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));

		portrait = new LabelEx();
		portrait.setOpaque(false);

		nameLabel = new LabelEx();
		nameLabel.setOpaque(false);
		nameLabel.setForeground(Color.BLACK);
		nameLabel.setFont(UIConfig.TitleFont);

		positionLabel = new LabelEx();
		positionLabel.setOpaque(false);
		positionLabel.setForeground(Color.BLACK);
		positionLabel.setFont(UIConfig.SubTitleFont);

		teamLabel = new LabelEx();
		teamLabel.setOpaque(false);
		teamLabel.setForeground(Color.BLACK);
		teamLabel.setFont(UIConfig.ContentFont);

		teamLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!teamLabel.getText().isEmpty())
					UIEventManager.notify(UIEventType.SWITCH, "球队详情:" + teamLabel.getText());
			}
		});

		PanelEx namePane = new PanelEx(new GridLayout(3, 1));
		namePane.setOpaque(false);
		namePane.add(nameLabel);
		namePane.add(positionLabel);
		namePane.add(teamLabel);
		namePane.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

		PanelEx propertyPane = new PanelEx(new GridLayout(4, 2, 20, 0));
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

		add(portrait, BorderLayout.WEST);
		add(detailPane);
	}

	public void setPlayer(String id) {
		PlayerInfoController.getInstance().setPlayerProfilePanel(this, id);
	}

	@Override
	public void setProfile(String name, String position, String number, String height,
			String weight, String birthday, String age, String exp, String school,
			String team) {
		ImageIcon icon = ImageUtilsEx.getPlayerImg(name, ImageUtilsEx.XL);
		portrait.setIcon(icon);

		nameLabel.setText(name);
		positionLabel.setText(position);
		teamLabel.setText(team);

		String[] h = height.split("-");
		if (h.length > 1) {
			height = h[0] + " 英尺 ";
			if (!h[1].equals("0"))
				height += h[1] + " 英寸";
			weight += " 磅";
		}
		String[] properties = { height, weight, number, birthday, age, exp, school };
		for (int i = 0; i < properties.length; i++) {
			this.properties[i].setText(labelsName[i] + "　" + properties[i]);
		}
	}

}
