package njuse.ffff.ui.ver2.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import njuse.ffff.ui.component.BasicButtonExListener;
import njuse.ffff.ui.component.ButtonEx;
import njuse.ffff.ui.component.LabelEx;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.UIConfig;
import njuse.ffff.ui.ver2.UIConfigNotifier;

public class TitleBar extends PanelEx implements UIConfigNotifier {

	private static final long serialVersionUID = 1L;

	private LabelEx titleLabel;
	private ButtonEx minimize;
	private ButtonEx exit;

	private Frame window;

	public TitleBar(Frame w) {
		super(new BorderLayout(0, 0));

		window = w;

		PanelEx utilPanel = new PanelEx(new BorderLayout(0, 0));	// 搜索面板+控制按钮
		PanelEx ctrlPanel = new PanelEx(new GridLayout(1, 0));	// 右上控制按钮面板（最小化 关闭）
		utilPanel.setOpaque(false);
		ctrlPanel.setOpaque(false);

		minimize = new ButtonEx();
		minimize.setOpaque(false);
		minimize.setFocusable(false);

		exit = new ButtonEx();
		exit.setOpaque(false);
		exit.setFocusable(false);
		BasicButtonExListener listener = new BasicButtonExListener(exit);
		listener.setInColor(Color.RED);
		listener.setInPressColor(new Color(200, 0, 0));
		listener.setOutPressColor(null);
		exit.setButtonExListener(listener);
		minimize.setIcon(new ImageIcon("./img/btn/min.png"));
		exit.setIcon(new ImageIcon("./img/btn/exit.png"));

		initAction();

		Dimension btnSize = new Dimension(40, 40);
		minimize.setPreferredSize(btnSize);
		exit.setPreferredSize(btnSize);

		ctrlPanel.add(minimize);
		ctrlPanel.add(exit);
		utilPanel.add(ctrlPanel, BorderLayout.EAST);

		// TODO
		utilPanel.add(new SearchField());

		titleLabel = new LabelEx("label_title");
		titleLabel.setOpaque(false);
		titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 10));

		setUIConfig();

		add(titleLabel);
		add(utilPanel, BorderLayout.EAST);

	}

	private void initAction() {
		minimize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.setExtendedState(JFrame.ICONIFIED);
			}
		});

		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.dispose();
			}
		});

	}

	public void setTitle(String text) {
		titleLabel.setText(text);
	}

	@Override
	public void notifyChange() {
		setUIConfig();
	}

	private void setUIConfig() {
		setBackground(UIConfig.TitleBgColor);

		titleLabel.setForeground(UIConfig.TitleForeColor);
		titleLabel.setFont(UIConfig.TitleFont);
	}
}
