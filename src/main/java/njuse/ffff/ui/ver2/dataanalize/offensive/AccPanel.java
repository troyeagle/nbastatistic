package njuse.ffff.ui.ver2.dataanalize.offensive;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import njuse.ffff.ui.component.LabelEx;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.UIConfig;
import njuse.ffff.ui.ver2.component.TableView;

public class AccPanel extends PanelEx {
	private static final long serialVersionUID = 1L;

	private static final String[] header = {
			"两分", "0-3尺", "3-10尺", "10-16尺", "16尺–三分线", "三分"
	};

	private TableView table;
	private LabelEx description;

	public AccPanel() {
		super(new GridLayout(2, 1, 20, 20));
		setOpaque(false);
		
		PanelEx tablePanel = new PanelEx(new BorderLayout());
		tablePanel.setOpaque(false);
		add(tablePanel);
		
		LabelEx title = new LabelEx("各距离段命中率分布");
		title.setOpaque(false);
		title.setFont(UIConfig.ContentFont);
		tablePanel.add(title, BorderLayout.NORTH);

		table = new TableView(null, header);
		tablePanel.add(table);

		description = new LabelEx("description");
		description.setFont(UIConfig.ContentFont);
		description.setVerticalAlignment(LabelEx.BOTTOM);
		description.setForeground(Color.BLACK);

		PanelEx panel = new PanelEx(new BorderLayout());
		panel.setOpaque(false);
		add(panel);

		panel.add(description);
	}

	public void setData(Object[][] data) {
		table.setTable(data);
	}

	public void setDescription(String text) {
		description.setText(text);
	}
}
