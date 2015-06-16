package njuse.ffff.ui.ver2.dataanalize;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import njuse.ffff.presenter.analysisController.AnalysisController;
import njuse.ffff.ui.component.ButtonEx;
import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.UIConfig;
import njuse.ffff.ui.ver2.component.TableView;
import njuse.ffff.util.BasicPlayerInfo;

public class SearchPanel extends PanelEx {
	private static final long serialVersionUID = 1L;

	private JTextField textField;
	private TableView resultTable;
	private ISearchable s;

	public SearchPanel(ISearchable s) {
		super(new BorderLayout(40, 40));
		setOpaque(false);
		setFocusable(true);
		setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

		this.s = s;

		add(createSearchField(), BorderLayout.NORTH);
		resultTable = createSearchTable();
		resultTable.setVisible(false);
		add(resultTable);
	}

	private boolean textFieldNotEdited = true;

	private PanelEx createSearchField() {
		PanelEx searchArea = new PanelEx();
		searchArea.setOpaque(false);
		searchArea.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

		PanelEx fieldPanel = new PanelEx(new BorderLayout(0, 0));
		fieldPanel.setBackground(Color.WHITE);
		fieldPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));

		ButtonEx searchButton = new ButtonEx("搜索");
		searchButton.setFont(UIConfig.TitleFont);
		searchButton.setForeground(Color.WHITE);
		searchButton.setBackground(Color.GRAY);
		searchButton.setBorder(BorderFactory.createEmptyBorder(5, 40, 5, 40));
		fieldPanel.add(searchButton, BorderLayout.EAST);
		searchButton.addActionListener(e -> {
			String text = textField.getText().trim();
			if (!text.isEmpty() && !(text.equals("请输入球员名") && textFieldNotEdited)) {
				searchButton.requestFocusInWindow();
				searchAction();
			}
		});

		textField = new JTextField("请输入球员名");
		textField.setColumns(20);
		textField.setForeground(Color.LIGHT_GRAY);
		textField.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
		textField.setFont(UIConfig.TitleFont);
		fieldPanel.add(textField);

		textField.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				textFieldNotEdited = textField.getText().isEmpty();
				if (textFieldNotEdited) {
					textField.setText("请输入球员名");
					textField.setForeground(Color.LIGHT_GRAY);
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				if (textField.getText().equals("请输入球员名") && textFieldNotEdited) {
					textField.setText("");
					textField.setForeground(Color.BLACK);
				}
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

		searchArea.add(fieldPanel);

		return searchArea;
	}

	private TableView createSearchTable() {
		TableView table = new TableView(null, new String[] { "球员名" });
		table.getTable().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int[] p = table.getSelectedCellLocation();
				if (p[0] >= 0)
					switchToPlayer((BasicPlayerInfo) table.getValueAt(p[0], 0));
			}
		});
		return table;
	}

	private void searchAction() {
		BasicPlayerInfo[] names = AnalysisController.getInstance().searchAnalysisPlayer(
				textField.getText());
		Object[][] data = new Object[names.length][1];
		for (int i = 0; i < names.length; i++)
			data[i][0] = names[i];
		resultTable.setVisible(true);
		resultTable.setTable(data);
	}

	protected void switchToPlayer(BasicPlayerInfo info) {
		s.resultAction(info);
	}
}
