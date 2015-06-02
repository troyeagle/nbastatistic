package njuse.ffff.ui.ver2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import njuse.ffff.presenter.playerController.PlayerFilterController;
import njuse.ffff.ui.component.LabelEx;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.component.SwitchButton;
import njuse.ffff.ui.ver2.component.SwitchButtonGroup;
import njuse.ffff.ui.ver2.component.TableView;
import njuse.ffff.ui.ver2.component.WrapLayout;
import njuse.ffff.uiservice.PlayerFilterViewService;

public class PlayerFilterPane extends PanelEx implements PlayerFilterViewService {

	private static final long serialVersionUID = 1L;

	private static final String[] tableHeader = { "", "姓名", "场均得分",
			"场均篮板", "场均助攻", "得分/篮板/助攻", "场均盖帽", "场均抢断",
			"场均犯规", "场均失误", "分钟", "效率", "投篮命中率", "三分命中率",
			"罚球命中率", "两双"
	};

	private TableView resTable;

	private static final int location = 0;
	private static final int league = 1;
	private static final int condition = 2;

	private SwitchButtonGroup[] groups;

	public PlayerFilterPane() {
		super(new BorderLayout(0, 20));
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

		groups = new SwitchButtonGroup[3];
		resTable = new TableView(new Object[0][], tableHeader);
		resTable.getTable().setCursor(new Cursor(Cursor.HAND_CURSOR));
		resTable.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int[] p = resTable.getSelectedCellLocation();
				if (p[0] >= 0) {
					Object v = resTable.getValueAt(p[0], 1);
					UIEventManager.notify(UIEventType.SWITCH, "球员详情:" + v);
				}
			}
		});

		PanelEx filterPanel = new PanelEx(new BorderLayout());
		filterPanel.setOpaque(false);

		ImageIcon checked = new ImageIcon("./img/btn/checked.png");
		ImageIcon unchecked = new ImageIcon("./img/btn/unchecked.png");

		String[][] titleStr = {
				{
						//"全部",
						"前锋", "中锋", "后卫" },
				{
						//"全部", 
						"东部", "西部" },
				{ "得分", "篮板", "助攻", "得分/篮板/助攻", "盖帽", "抢断", "犯规",
						"失误", "分钟", "效率", "投篮", "三分", "罚球", "两双" }
		};
		String[] typeStr = { "位置：", "联盟：", "条件：" };

		PanelEx[] conditions = new PanelEx[3];
		for (int i = 0; i < titleStr.length; i++) {
			groups[i] = new SwitchButtonGroup();

			conditions[i] = new PanelEx(new WrapLayout(WrapLayout.LEFT));
			conditions[i].setOpaque(false);

			LabelEx typeLabel = new LabelEx(typeStr[i]);
			typeLabel.setOpaque(false);
			typeLabel.setFont(UIConfig.SubTitleFont);
			typeLabel.setForeground(Color.WHITE);
			conditions[i].add(typeLabel);

			String[] optionStr = titleStr[i];
			for (String str : optionStr) {
				SwitchButton btn = new SwitchButton(str, unchecked);
				btn.setName(str);
				btn.setActiveIcon(checked);
				btn.setFont(UIConfig.SubTitleFont);
				btn.setForeground(Color.WHITE);
				btn.setBackground(new Color(255, 255, 255, 64));
				groups[i].addButton(btn);
				conditions[i].add(btn);
			}
			if (i != condition)
				groups[i].switchTo(0);
		}
		for (SwitchButtonGroup group : groups) {
			group.addSwitchListener(e -> getResult());
		}

		filterPanel.add(conditions[0], BorderLayout.NORTH);
		filterPanel.add(conditions[1]);
		filterPanel.add(conditions[2], BorderLayout.SOUTH);

		add(filterPanel, BorderLayout.NORTH);
		add(resTable);
	}

	private void getResult() {
		PlayerFilterController.getInstance().setPlayerFilterResult(this);
	}

	@Override
	public String[] getFilters() {
		List<String> filterList = new ArrayList<>();
		if (groups[location].getActiveIndex() >= 0) {
			String loc = "位置:" + groups[location].getActiveButton().getName();
			filterList.add(loc);
		}
		if (groups[league].getActiveIndex() >= 0) {
			String leag = "联盟:" + groups[league].getActiveButton().getName();
			filterList.add(leag);
		}
		if (groups[condition].getActiveIndex() >= 0) {
			filterList.add(groups[condition].getActiveButton().getName());
		}
		return filterList.toArray(new String[0]);
	}

	@Override
	public void setResult(Object[][] data) {
		resTable.setTable(data);
	}
}
