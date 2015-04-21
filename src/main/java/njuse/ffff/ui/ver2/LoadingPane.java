package njuse.ffff.ui.ver2;

import java.awt.BorderLayout;
import java.awt.Color;

import njuse.ffff.ui.component.LabelEx;
import njuse.ffff.ui.component.PanelEx;

public class LoadingPane extends PanelEx {

	private static final long serialVersionUID = 1L;

	public LoadingPane() {
		super(new BorderLayout());
		setBackground(new Color(128, 128, 128, 128));
		
		// TODO
		LabelEx loading = new LabelEx("Loading...", LabelEx.CENTER);
		loading.setFont(UIConfig.TitleFont);
		loading.setForeground(Color.WHITE);
		
		add(loading);
	}
}
