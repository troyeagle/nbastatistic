package njuse.ffff.ui.ver2.dataanalize.offensive;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import njuse.ffff.ui.component.LabelEx;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.UIConfig;
import njuse.ffff.ui.ver2.component.TableView;

public class AttackPanel extends PanelEx {

	TableView table;
	LabelEx description;

	private static final long serialVersionUID = 1L;

	public AttackPanel() {
		super(new GridLayout(2, 1));
		setOpaque(false);

		table = new TableView(null, new String[] { "真实投篮命中率", "联盟排名",
				"进攻贡献值", "联盟排名" });
		add(table);

		description = new LabelEx("description");
		description.setFont(UIConfig.ContentFont);
		description.setForeground(Color.BLACK);

		PanelEx panel = new PanelEx(new BorderLayout());
		panel.setOpaque(false);
		add(panel);

		panel.add(description, BorderLayout.NORTH);
	}

	public void setData(Object[][] data) {
		table.setTable(data);
	}

	public void setDescription(String text) {
		description.setText(text);
	}
}
