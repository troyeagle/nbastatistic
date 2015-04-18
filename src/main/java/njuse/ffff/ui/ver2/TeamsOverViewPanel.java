package njuse.ffff.ui.ver2;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTable;

import njuse.ffff.ui.component.PanelEx;
import njuse.ffff.ui.ver2.component.SwitchButton;
import njuse.ffff.ui.ver2.component.SwitchButtonGroup;
import njuse.ffff.ui.ver2.component.SwitchEvent;
import njuse.ffff.ui.ver2.component.SwitchListener;
import njuse.ffff.ui.ver2.component.TableView;
import njuse.ffff.uiservice.TeamsOverviewService;

public class TeamsOverViewPanel extends PanelEx implements TeamsOverviewService,
		UIConfigNotifier {

	private static final long serialVersionUID = 1L;

	private PanelEx dataPanel;
	private PanelEx controlPanel;

	private TableView dataTable;

	SwitchButton tableView;
	SwitchButton picView;

	// TODO
	private String[] columns;

	public TeamsOverViewPanel() {
		super(new BorderLayout());

		setOpaque(false);

		dataPanel = new PanelEx(new CardLayout());
		dataPanel.setOpaque(false);

		tableView = new SwitchButton(new ImageIcon("./img/btn/tableview.png"));
		tableView.setName("tableView");
		tableView.setEnabled(false);
		tableView.setBackground(new Color(255, 255, 255, 64));

		picView = new SwitchButton(new ImageIcon("./img/btn/picview.png"));
		picView.setName("picView");
		picView.setEnabled(false);
		picView.setBackground(new Color(255, 255, 255, 64));

		SwitchButtonGroup group = new SwitchButtonGroup();
		group.addButton(tableView);
		group.addButton(picView);
		group.addSwitchListener(new SwitchListener() {
			@Override
			public void actionPerformed(SwitchEvent e) {
				((CardLayout) dataPanel.getLayout()).show(dataPanel, e.getSource().getName());
			}
		});

		PanelEx switchPanel = new PanelEx(new FlowLayout(FlowLayout.LEFT));
		switchPanel.setOpaque(false);
		switchPanel.add(tableView);
		switchPanel.add(picView);

		controlPanel = new PanelEx(new BorderLayout());
		controlPanel.setOpaque(false);
		controlPanel.add(switchPanel);

		PanelEx contentPanel = new PanelEx(new BorderLayout());
		contentPanel.setOpaque(false);
		contentPanel.add(dataPanel);
		contentPanel.add(controlPanel, BorderLayout.NORTH);

		add(contentPanel);

		setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

		//		dataTable = new TableView(new Object[][] { { "12", 1 }, { "34", 2 } }, new String[] {
		//				"c1", "c2" });
		//		dataPanel.add("tableView", dataTable);

		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		columns = new String[] { "c1", "c2" };
		setTeamsInfo(new Object[][] { { "12", 1 }, { "34", 2 } });
	}

	@Override
	public void setTeamsInfo(Object[][] values) {
		if (dataTable == null) {
			dataTable = new TableView(values, columns);
			setUIConfig();

			dataPanel.add("tableView", dataTable);

			tableView.setEnabled(true);
			if (!picView.isEnabled()) {
				tableView.doClick();
			}
		} else {
			dataTable.setTable(values);
		}
	}

	private void setTeamsIcon() {
		
	}
	
	@Override
	public void notifyChange() {
		setUIConfig();
	}

	private void setUIConfig() {
		setTableUIConfig();
	}

	private void setTableUIConfig() {
		dataTable.setTableFont(UIConfig.ContentFont);
		dataTable.setHeaderFont(UIConfig.ContentFont);
		dataTable.setRowHeight(UIConfig.ContentFont.getSize() + 5);
		dataTable.setForeground(Color.WHITE);
		dataTable.setSelectionBgColor(UIConfig.TableSelectionBgColor);
		dataTable.setSelectionFgColor(UIConfig.TableSelectionFgColor);
		dataTable.setTableFgColor(UIConfig.TableFgColor);
		dataTable.setHeaderBgColor(UIConfig.TableHeaderBgColor);
		dataTable.setHeaderFgColor(UIConfig.TableHeaderFgColor);
	}
	
	@Override
	public void paint(Graphics g) {
		if (dataTable != null) {
			JTable table = dataTable.getTable();
			if (table != null) {
				int mode = table.getWidth() <= dataTable.getWidth() ? JTable.AUTO_RESIZE_ALL_COLUMNS
						: JTable.AUTO_RESIZE_OFF;
				if (table.getAutoResizeMode() != mode)
					table.setAutoResizeMode(mode);
			}
		}
		super.paint(g);
	}
}
