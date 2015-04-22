package njuse.ffff.ui.ver2.component;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Arrays;
import java.util.List;

import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.UIConfig;
import njuse.ffff.ui.ver2.UIConfigNotifier;

public class TabBar extends PanelEx implements UIConfigNotifier {

	private static final long serialVersionUID = 1L;

	private SwitchButtonGroup group;

	private SwitchButton activeBtn;

	private Color activeColor;
	
	public static final int CENTER = FlowLayout.CENTER;
	public static final int LEFT = FlowLayout.LEFT;
	public static final int RIGHT = FlowLayout.RIGHT;

	public TabBar(String... titles) {
		super(new FlowLayout(0, 20, 0));
		setOpaque(false);

		group = new SwitchButtonGroup();
		group.addSwitchListener(new SwitchListener() {
			@Override
			public void actionPerformed(SwitchEvent e) {
				switchHandle();
			}
		});

		addTabs(titles);

		if (titles.length > 0)
			switchTo(0);
	}

	public synchronized int getActiveTabIndex() {
		return group.getActiveIndex();
	}

	public synchronized String getActiveTabTitle() {
		if (activeBtn == null)
			return null;
		return activeBtn.getName();
	}

	public synchronized void switchTo(int index) {
		group.switchTo(index);
		switchHandle();
	}

	private void switchHandle() {
		if (activeBtn != null)
			activeBtn.setOpaque(false);
		activeBtn = group.getActiveButton();
		if (activeColor != null) {
			activeBtn.setOpaque(true);
		}
	}

	public synchronized void switchTo(String title) {
		SwitchButton[] buttons = group.getButtons();
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i].getName().equals(title)) {
				switchTo(i);
				break;
			}
		}
	}

	public void addTab(String title) {
		addTab(title, group.getButtonsCount());
	}

	public void addTab(String title, int index) {
		if (containsTab(title))
			return;

		TabLabelButton tab = new TabLabelButton(title);
		tab.setName(title);
		tab.setBackground(activeColor);
		tab.setOpaque(false);
		tab.setFont(UIConfig.SubTitleFont);
		tab.setForeground(Color.WHITE);
		tab.setSelected(false);
		add(tab, index);

		group.addButton(tab);
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
		for (SwitchButton tab : group.getButtons()) {
			if (tab.getName().equals(title))
				return true;
		}
		return false;
	}

	public void setSpace(int width) {
		setLayout(new FlowLayout(FlowLayout.LEFT, width, 0));
	}

	@Override
	public void notifyChange() {

	}

	public void addSwitchListener(SwitchListener l) {
		group.addSwitchListener(l);
	}

	public void removeSwitchListener(SwitchListener l) {
		group.removeSwitchListener(l);
	}

	public void setActiveColor(Color c) {
		if (activeColor != c) {
			activeColor = c;
			if (c != null) {
				Arrays.asList(group.getButtons()).forEach(btn -> {
					btn.setBackground(c);
				});
				activeBtn.setOpaque(true);
			} else if (activeBtn != null) {
				activeBtn.setOpaque(false);
			}
		}

	}
	
	public void setAlignment(int align) {
		((FlowLayout) getLayout()).setAlignment(align);
	}
}
