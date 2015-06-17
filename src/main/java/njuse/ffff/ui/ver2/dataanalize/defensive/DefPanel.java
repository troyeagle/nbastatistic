package njuse.ffff.ui.ver2.dataanalize.defensive;

import java.awt.Color;
import java.awt.GridLayout;

import njuse.ffff.ui.component.LabelEx;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.UIConfig;

public class DefPanel extends PanelEx {
	private static final long serialVersionUID = 1L;

	LabelEx dws;
	LabelEx rank;
	LabelEx description;

	public DefPanel() {
		super(new GridLayout(3, 1));
		setOpaque(false);

		dws = new LabelEx("DWS");
		dws.setFont(UIConfig.ContentFont);
		dws.setForeground(Color.BLACK);
		add(dws);

		rank = new LabelEx("rank");
		rank.setFont(UIConfig.ContentFont);
		rank.setForeground(Color.BLACK);
		add(rank);

		description = new LabelEx("description");
		description.setFont(UIConfig.ContentFont);
		description.setVerticalAlignment(LabelEx.BOTTOM);
		description.setForeground(Color.BLACK);

		add(description);
	}

	public void setDWS(double dws) {
		this.dws.setText(String.valueOf(dws));
	}

	public void setRank(int rank) {
		this.rank.setText(String.valueOf(rank));
	}

	public void setDescription(String text) {
		description.setText(text);
	}
}
