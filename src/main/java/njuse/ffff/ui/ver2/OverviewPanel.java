package njuse.ffff.ui.ver2;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;

import njuse.ffff.ui.component.ButtonEx;
import njuse.ffff.ui.component.LabelEx;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.component.SwitchButton;
import njuse.ffff.ui.ver2.component.SwitchButtonGroup;
import njuse.ffff.ui.ver2.component.TableView;
import njuse.ffff.uiservice.OverviewService;

public abstract class OverviewPanel extends PanelEx implements OverviewService {
	private static final long serialVersionUID = 1L;

	protected final String[] avgHeader;
	protected final String[] totalHeader;

	protected JComboBox<String> seasonList;

	protected PanelEx dataPanel;
	protected PanelEx switchPanel;

	protected SwitchButton avgView;
	protected SwitchButton totalView;
	protected SwitchButtonGroup dataGroup;

	protected TableView avgTable;
	protected TableView totalTable;

	public OverviewPanel(String[] avgHeader, String[] totalHeader) {
		super(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		setOpaque(false);

		this.avgHeader = avgHeader;
		this.totalHeader = totalHeader;

		// 赛季选择
		seasonList = new JComboBox<String>();
		seasonList.setFont(UIConfig.TitleFont);
		LabelEx seasonLabel = new LabelEx("赛季：");
		seasonLabel.setFont(UIConfig.TitleFont);
		PanelEx seasonPanel = new PanelEx(new FlowLayout(FlowLayout.LEFT, 10, 5));
		seasonPanel.setOpaque(false);
		seasonPanel.add(seasonLabel);
		seasonPanel.add(seasonList);

		add(seasonPanel, BorderLayout.NORTH);

		avgTable = new TableView(new Object[0][0], avgHeader);
		totalTable = new TableView(new Object[0][0], totalHeader);

		// 按钮区
		avgView = new SwitchButton("平均数据", new ImageIcon("./img/btn/tableview.png"));
		avgView.setName("avgView");
		setButtonUI(avgView);

		totalView = new SwitchButton("总数据", new ImageIcon("./img/btn/tableview.png"));
		totalView.setName("totalView");
		setButtonUI(totalView);

		dataGroup = new SwitchButtonGroup();
		dataGroup.addButton(totalView);
		dataGroup.addButton(avgView);
		dataGroup.addSwitchListener(e -> {
			SwitchButton type = dataGroup.getActiveButton();
			if (type != null) {
				((CardLayout) dataPanel.getLayout()).show(dataPanel, type.getName());
			}
		});

		switchPanel = new PanelEx(new FlowLayout(FlowLayout.LEFT, 10, 5));
		switchPanel.setOpaque(false);
		switchPanel.add(totalView);
		switchPanel.add(avgView);

		// 数据区
		dataPanel = new PanelEx(new CardLayout());
		dataPanel.setOpaque(false);
		dataPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		dataPanel.add("totalView", totalTable);
		dataPanel.add("avgView", avgTable);

		PanelEx contentPanel = new PanelEx(new BorderLayout());
		contentPanel.setOpaque(false);
		contentPanel.add(dataPanel);
		contentPanel.add(switchPanel, BorderLayout.NORTH);

		add(contentPanel);

		dataGroup.switchTo(0);
	}

	@Override
	public void setSeasons(String[] seasons) {
		String selectedSeason = getSelectedSeason();
		seasonList.removeAllItems();
		boolean existed = false;
		for (String season : seasons) {
			seasonList.addItem(season);
			if (!existed && selectedSeason != null && season.equals(selectedSeason)) {
				seasonList.setSelectedItem(season);
				existed = true;
			}
		}
		if (!existed) {
			seasonList.setSelectedIndex(0);
		}
	}

	@Override
	public String getSelectedSeason() {
		return (String) seasonList.getSelectedItem();
	}

	@Override
	public void setAvgInfo(Object[][] data) {
		avgTable.setTable(data);
	}

	@Override
	public void setTotalInfo(Object[][] data) {
		totalTable.setTable(data);
	}

	protected void setButtonUI(ButtonEx button) {
		button.setBackground(UIConfig.ThemeColor);
		button.setForeground(Color.WHITE);
		button.setFont(UIConfig.SubTitleFont);
	}
}
