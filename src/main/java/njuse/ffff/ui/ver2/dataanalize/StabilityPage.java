package njuse.ffff.ui.ver2.dataanalize;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;

import njuse.ffff.ui.component.LabelEx;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.UIConfig;

public class StabilityPage extends PanelEx {
	private static final long serialVersionUID = 1L;

	LabelEx average;
	LabelEx finished;
	LabelEx avgSample;
	LabelEx var;
	LabelEx varSample;
	LabelEx varSample2;
	LabelEx conclusion;

	public StabilityPage(String type) {
		super(new GridLayout(6, 1));
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

		LabelEx label = createLabel("上赛季");
		label.setFont(UIConfig.TitleFont);
		add(label);

		PanelEx grid = new PanelEx(new GridLayout(1, 2, 20, 0));
		grid.setOpaque(false);
		add(grid);

		PanelEx panel = createPanel();
		label = createLabel("场均" + type + "：");
		average = createLabel2();
		panel.add(label);
		panel.add(average);
		grid.add(panel);

		panel = createPanel();
		label = createLabel(type + "方差：");
		var = createLabel2();
		panel.add(label);
		panel.add(var);
		grid.add(panel);

		panel = createPanel();
		label = createLabel("本赛季，该球员已完成");
		panel.add(label);
		finished = createLabel2();
		panel.add(finished);
		label = createLabel("场比赛");
		panel.add(label);
		add(panel);

		grid = new PanelEx(new GridLayout(1, 2, 40, 0));
		grid.setOpaque(false);
		add(grid);

		panel = createPanel();
		label = createLabel("场均" + type + "（抽样）：");
		avgSample = createLabel2();
		panel.add(label);
		panel.add(avgSample);
		grid.add(panel);

		panel = createPanel();
		label = createLabel(type + "方差：");
		varSample = createLabel2();
		panel.add(label);
		panel.add(varSample);
		grid.add(panel);

		panel = createPanel();
		label = createLabel("若本赛季结束时，" + type + "比上赛季稳定，抽样方差≤");
		varSample2 = createLabel2();
		panel.add(label);
		panel.add(varSample2);
		add(panel);

		panel = createPanel();
		label = createLabel("结论：该球员" + type);
		conclusion = createLabel2();
		panel.add(label);
		panel.add(conclusion);
		add(panel);
	}

	private LabelEx createLabel(String text) {
		LabelEx label = new LabelEx(text);
		label.setFont(UIConfig.ContentFont);
		label.setForeground(Color.BLACK);
		return label;
	}

	private LabelEx createLabel2() {
		LabelEx label = new LabelEx("data");
		label.setFont(UIConfig.ContentFont);
		label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		label.setForeground(UIConfig.ThemeColor);
		return label;
	}

	private PanelEx createPanel() {
		PanelEx panel = new PanelEx(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panel.setOpaque(false);
		return panel;
	}
}
