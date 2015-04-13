package njuse.ffff.ui.ver2;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;

public class TitleBar extends JPanel implements UIConfigNotifier {

	private static final long serialVersionUID = 1L;

	private List<TabLabel> tabs;	// 标签集合

	public TitleBar() {
		this(new String[0]);
	}

	public TitleBar(String... titles) {
		super(new FlowLayout(0, 30, 0));
		setOpaque(false);

		tabs = new Vector<>();

		addTabs(titles);

		setSelected(0);
	}

	public int getSelectedTabIndex() {
		for (int i = 0; i < tabs.size(); i++)
			if (tabs.get(i).isSelected())
				return i;
		return -1;
	}

	public String getSelectedTabTitle() {
		for (TabLabel tab : tabs)
			if (tab.isSelected())
				return tab.getName();
		return null;
	}

	public String setSelected(int index) {
		if (index > tabs.size())
			return null;
		for (int i = 0; i < tabs.size(); i++) {
			tabs.get(i).setSelected(index == i);
			if (i == index)
				return tabs.get(i).getName();
		}
		return null;
	}

	public void setSelected(String title) {
		tabs.forEach(tab -> {
			tab.setSelected(tab.getName().equals(title));
		});
	}

	public void addTab(String title) {
		addTab(title, tabs.size());
	}

	public void addTabs(String... titles) {
		addTabs(Arrays.asList(titles));
	}

	public void addTabs(List<String> titles) {
		titles.forEach(title -> {
			addTab(title);
		});
	}

	public boolean containsTab(String title) {
		for (TabLabel tab : tabs) {
			if (tab.getName().equals(title))
				return true;
		}
		return false;
	}

	public void addTab(String title, int index) {
		if (containsTab(title))
			return;

		TabLabel label = new TabLabel(title);
		label.setName(title);
		label.setOpaque(false);
		label.setFont(UIConfig.SubTitleFont);
		label.setSelected(false);
		add(label);

		tabs.add(label);

		// 添加监听
		label.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setSelected(title);
			}
		});
	}

	@Override
	public void notifyChange() {
		
	}

}
