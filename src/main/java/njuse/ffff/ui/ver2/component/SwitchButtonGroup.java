package njuse.ffff.ui.ver2.component;

import java.util.List;
import java.util.Vector;

public class SwitchButtonGroup {

	private List<SwitchButton> buttonList;
	private int activeIndex;

	private ConditionChangeListener switchListener;

	private List<SwitchListener> listenerList;

	public SwitchButtonGroup() {
		activeIndex = -1;
		buttonList = new Vector<>();
		listenerList = new Vector<>();

		switchListener = new ConditionChangeListener() {
			@Override
			public void actionPerformed(ConditionChangeEvent e) {
				int destination = -1;
				if (e.isActive()) {
					for (int i = 0; i < buttonList.size(); i++) {
						if (buttonList.get(i) == e.getSource()) {
							destination = i;
							break;
						}
					}
				}
				switchTo(destination);
			}
		};
		//		switchListener = new ActionListener() {
		//
		//			@Override
		//			public void actionPerformed(ActionEvent e) {
		//				SwitchButton b = (SwitchButton) e.getSource();
		//				if (!b.isActive()) {
		//					for (int i = 0; i < buttonList.size(); i++) {
		//						if (buttonList.get(i) == b) {
		//							switchTo(i);
		//							return;
		//						}
		//					}
		//				}
		//			}
		//		};
	}

	public void addButton(SwitchButton button) {
		if (!contains(button)) {
			buttonList.add(button);

			button.addConditionChangeListener(switchListener);
			if (button.isActive()) {
				if (activeIndex == -1) {
					switchTo(buttonList.size() - 1);
				} else {
					button.setActive(false);
				}
			}
		}
	}

	public void switchTo(int index) {
		if (activeIndex != index) {
			if (activeIndex != -1)
				buttonList.get(activeIndex).setStatus(false);
			activeIndex = index;

			SwitchButton source = null;
			if (index != -1) {
				source = buttonList.get(index);
				source.setStatus(true);
			}

			SwitchEvent event = new SwitchEvent(source);
			listenerList.forEach(l -> {
				l.actionPerformed(event);
			});
		}
	}

	public void switchTo(String name) {
		for (int i = 0; i < buttonList.size(); i++)
			if (buttonList.get(i).getName().equals(name)) {
				switchTo(i);
				break;
			}
	}

	public int getActiveIndex() {
		return activeIndex;
	}

	public SwitchButton getActiveButton() {
		if (activeIndex == -1)
			return null;
		return buttonList.get(activeIndex);
	}

	public SwitchButton[] getButtons() {
		return buttonList.toArray(new SwitchButton[0]);
	}

	public int getButtonsCount() {
		return buttonList.size();
	}

	public void addSwitchListener(SwitchListener l) {
		listenerList.add(l);
	}

	public void removeSwitchListener(SwitchListener l) {
		listenerList.remove(l);
	}

	public boolean contains(SwitchButton button) {
		return buttonList.contains(button);
	}

	public boolean contains(String title) {
		for (SwitchButton btn : buttonList) {
			if (btn.getName().equals(title))
				return true;
		}
		return false;
	}
}
