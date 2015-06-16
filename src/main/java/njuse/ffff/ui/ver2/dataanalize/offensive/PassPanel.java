package njuse.ffff.ui.ver2.dataanalize.offensive;

import njuse.ffff.ui.ver2.dataanalize.RatioPanel;

import org.jfree.data.category.DefaultCategoryDataset;

public class PassPanel extends RatioPanel {
	private static final long serialVersionUID = 1L;

	private static final String[] header = { "助攻率", "场均助攻", "每36分钟助攻", "排名" };

	DefaultCategoryDataset dataset;

	public PassPanel() {
		super(header);
		
		// TODO
	}
}
