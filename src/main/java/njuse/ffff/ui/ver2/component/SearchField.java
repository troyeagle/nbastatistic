package njuse.ffff.ui.ver2.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

import njuse.ffff.ui.component.ButtonEx;
import njuse.ffff.ui.component.ComponentExUtilities;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.UIConfig;
import njuse.ffff.ui.ver2.UIEventManager;
import njuse.ffff.ui.ver2.UIEventType;

public class SearchField extends PanelEx  {

	private static final long serialVersionUID = 1L;

	private JTextField textField;
	private ButtonEx searchButton;

	private boolean focused;

	public SearchField() {
		super(new BorderLayout());
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

		textField = new JTextField();
		textField.setColumns(12);
		textField.setForeground(Color.BLACK);
		textField.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				focused = false;
				repaint();
			}

			@Override
			public void focusGained(FocusEvent e) {
				focused = true;
				repaint();
			}
		});
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					searchButton.doClick();
				}
			}
		});

		PanelEx fieldPanel = new PanelEx(new BorderLayout(0, 0));
		fieldPanel.setBackground(Color.WHITE);
		fieldPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
		fieldPanel.add(textField);

		add(fieldPanel);

		searchButton = new ButtonEx(new ImageIcon("./img/btn/search.png"));
		searchButton.setBorder(BorderFactory.createEmptyBorder());
		searchButton.setBackground(Color.LIGHT_GRAY);
		fieldPanel.add(searchButton, BorderLayout.EAST);
		searchButton.addActionListener(e -> {
			String text = textField.getText().trim();
			if (!text.isEmpty()) {
				searchButton.requestFocusInWindow();
				UIEventManager.notify(UIEventType.SEARCH, text);
			}
		});

		setUIConfig();
	}

	public String getText() {
		return textField.getText();
	}

	@Override
	public void paint(Graphics g) {
		ComponentExUtilities.setGraphicsAntiAlias((Graphics2D) g);
		super.paint(g);
		if (textField.getText().isEmpty() && !focused) {
			Graphics g2 = g.create();
			g2.setFont(textField.getFont());
			g2.setColor(Color.LIGHT_GRAY);
			g2.drawString("输入球队名/球员名", 14, 26);
		}
	}

	private void setUIConfig() {
		textField.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 5));
		textField.setFont(UIConfig.SubTitleFont);
	}
}
