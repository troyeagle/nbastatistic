package njuse.ffff.ui.ver2.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

public class SwitchButtonGroup {

	private List<SwitchButton> buttonList;
	private int activeIndex;

	private ActionListener switchListener;

	private List<SwitchListener> listenerList;

	public SwitchButtonGroup() {
		activeIndex = -1;
		buttonList = new Vector<>();
		listenerList = new Vector<>();

		switchListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SwitchButton b = (SwitchButton) e.getSource();
				if (!b.isActive()) {
					for (int i = 0; i < buttonList.size(); i++) {
						if (buttonList.get(i) == b) {
							switchTo(i);
							return;
						}
					}
				}
			}
		};
	}

	public void addButton(SwitchButton button) {
		if (!contains(button)) {
			buttonList.add(button);

			button.addActionListener(switchListener);
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
				buttonList.get(activeIndex).setActive(false);
			activeIndex = index;
			buttonList.get(index).setActive(true);

			SwitchEvent event = new SwitchEvent(buttonList.get(index));
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
