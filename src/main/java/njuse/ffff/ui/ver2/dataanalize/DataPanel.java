package njuse.ffff.ui.ver2.dataanalize;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComboBox;

import njuse.ffff.ui.component.ButtonEx;
import njuse.ffff.ui.component.LabelEx;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.ImageUtilsEx;
import njuse.ffff.ui.ver2.UIConfig;
import njuse.ffff.ui.ver2.component.TabBar;
import njuse.ffff.util.BasicPlayerInfo;

public abstract class DataPanel extends PanelEx {

	private static final long serialVersionUID = 1L;

	protected TabBar tabbar;

	protected PanelEx dataPanel;

	protected LabelEx icon;
	protected LabelEx name;
	protected LabelEx loc;
	protected JComboBox<String> seasonList;
	protected PanelEx seasonPanel;

	public DataPanel(String... tabs) {
		super(new CardLayout());
		setOpaque(false);

		initSearchPanel();
		initPlayerDataPanel(tabs);
	}

	private void initSearchPanel() {
		add("search", new SearchPanel(result -> {
			switchToPlayerData(result);
		}));
	}

	private void initPlayerDataPanel(String[] tabs) {
		PanelEx playerDataPanel = new PanelEx(new BorderLayout());
		playerDataPanel.setOpaque(false);

		PanelEx headPanel = new PanelEx(new BorderLayout());
		headPanel.setBackground(UIConfig.HeadPanelBgColor);

		icon = new LabelEx();
		icon.setOpaque(false);

		name = new LabelEx("Name");
		name.setOpaque(false);
		name.setFont(UIConfig.TitleFont);
		name.setForeground(Color.BLACK);
		name.setPreferredSize(new Dimension(300, 70));

		loc = new LabelEx("Loc");
		loc.setOpaque(false);
		loc.setFont(UIConfig.SubTitleFont);
		loc.setForeground(Color.BLACK);
		PanelEx detailPanel = new PanelEx(new GridLayout(2, 1));
		detailPanel.setOpaque(false);
		detailPanel.add(name);
		detailPanel.add(loc);

		LabelEx season = new LabelEx("赛季：");
		season.setOpaque(false);
		season.setFont(UIConfig.TitleFont);
		season.setForeground(Color.BLACK);

		seasonList = new JComboBox<String>();
		seasonList.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		seasonList.setFont(UIConfig.TitleFont);

		seasonPanel = new PanelEx(new FlowLayout(FlowLayout.LEFT));
		seasonPanel.setOpaque(false);
		seasonPanel.add(season);
		seasonPanel.add(seasonList);

		PanelEx infoPanel = new PanelEx(new FlowLayout(FlowLayout.LEFT));
		infoPanel.setOpaque(false);
		infoPanel.add(detailPanel);
		infoPanel.add(seasonPanel);

		PanelEx playerPanel = new PanelEx(new BorderLayout());
		playerPanel.setOpaque(false);
		playerPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 0, 40));
		playerPanel.add(icon, BorderLayout.WEST);
		playerPanel.add(infoPanel);
		headPanel.add(playerPanel);

		ButtonEx backButton = new ButtonEx("<");
		backButton.setOpaque(false);
		backButton.setFont(new Font("Consolas", Font.PLAIN, 60));
		backButton.setForeground(Color.WHITE);
		backButton.addActionListener(e -> {
			((CardLayout) getLayout()).show(this, "search");
		});
		headPanel.add(backButton, BorderLayout.WEST);

		playerDataPanel.add(headPanel, BorderLayout.NORTH);

		PanelEx tabPanel = new PanelEx(new BorderLayout());
		tabPanel.setOpaque(false);
		playerDataPanel.add(tabPanel);

		tabbar = new TabBar(tabs);
		tabbar.setOpaque(true);
		tabbar.setBackground(UIConfig.TitleBgColor);
		tabbar.setAlignment(TabBar.CENTER);
		tabbar.addSwitchListener(e -> {
			((CardLayout) dataPanel.getLayout()).show(dataPanel, e.getSource().getName());
		});
		tabPanel.add(tabbar, BorderLayout.NORTH);

		dataPanel = new PanelEx(new CardLayout());
		dataPanel.setOpaque(false);
		dataPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		tabPanel.add(dataPanel);

		add(playerDataPanel, "data");
	}

	protected void switchToPlayerData(BasicPlayerInfo info) {
		playerInfo = info;
		String name = info.toString();
		this.name.setText(info.toString());

		Icon icon = ImageUtilsEx.getPlayerImg(name, ImageUtilsEx.L);
		this.icon.setIcon(icon);
		// TODO

		tabbar.switchTo(0);
		((CardLayout) getLayout()).show(this, "data");
	}

	private BasicPlayerInfo playerInfo;
	public void setSeasons(String[] seasons) {
		seasonList.removeAllItems();
		for (String season : seasons)
			seasonList.addItem(season);
		seasonList.setSelectedIndex(0);
	}
	
	public String getSelectedSeason() {
		return (String) seasonList.getSelectedItem();
	}
	
	public BasicPlayerInfo getPlayerInfo() {
		return playerInfo;
	}
}
