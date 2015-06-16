package njuse.ffff.ui.ver2.dataanalize;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;

import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.UIConfig;
import njuse.ffff.ui.ver2.component.SwitchButtonGroup;
import njuse.ffff.ui.ver2.component.TabLabelButton;

public class DataAnalizePanel extends PanelEx {
	private static final long serialVersionUID = 1L;

	private SwitchButtonGroup tabGroup;
	private PanelEx contentPanel;

	public DataAnalizePanel() {
		super(new BorderLayout());
		setOpaque(false);

		contentPanel = new PanelEx(new CardLayout());
		contentPanel.setOpaque(false);
		add(contentPanel);

		contentPanel.add("球员进攻分析", new OffensiveDataPanel());
		contentPanel.add("球员防守分析", new DefensiveDataPanel());
		contentPanel.add("球员数据稳定性", new PlayerDataStabilityPanel());
		contentPanel.add("联盟进攻风格", new LeagueOffenseStylePanel());

		tabGroup = new SwitchButtonGroup();
		tabGroup.addSwitchListener(e -> {
			((CardLayout) contentPanel.getLayout()).show(contentPanel, e.getSource()
					.getText());
		});
		PanelEx tabBar = createTabBar();
		add(tabBar, BorderLayout.WEST);
	}

	private PanelEx createTabBar() {
		PanelEx tabArea = new PanelEx(new FlowLayout(FlowLayout.CENTER, 0, 0));
		tabArea.setBackground(Color.GRAY);

		PanelEx tabBar = new PanelEx(new GridLayout(0, 1));
		tabBar.setOpaque(false);
		tabBar.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		tabArea.add(tabBar);

		String[] tabsName = { "球员进攻分析", "球员防守分析", "球员数据稳定性", "联盟进攻风格" };
		for (String tabName : tabsName) {
			TabLabelButton tabBtn = createTabButton(tabName);
			tabBar.add(tabBtn);
			tabGroup.addButton(tabBtn);
		}
		tabGroup.switchTo(0);

		return tabArea;
	}

	private TabLabelButton createTabButton(String text) {
		TabLabelButton tab = new TabLabelButton(text, TabLabelButton.LEFT, 4);
		tab.setBackground(Color.GRAY);
		tab.setForeground(Color.WHITE);
		tab.setActiveBgColor(new Color(210, 210, 210));
		tab.setActiveFgColor(UIConfig.ThemeColor);
		tab.setHorizontalAlignment(TabLabelButton.LEFT);
		tab.setFont(UIConfig.ContentFont);
		tab.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 60));
		return tab;
	}
}
