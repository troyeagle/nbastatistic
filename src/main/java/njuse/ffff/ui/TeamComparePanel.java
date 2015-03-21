package njuse.ffff.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import njuse.ffff.presenter.ControllerService;
import njuse.ffff.presenter.UIController;

//1）设置表格透明度
//2）playerProfilePanel界面布局
//3）快捷搜索透明度，进入效果

@SuppressWarnings("serial")
public class TeamComparePanel extends JPanel{
	private final int teamComparePanel_width = 1100;
	private final int teamComparePanel_height = 700;
	
	private ControllerService uiController;
	private JPanel panel;
	private int tableDisplay;//0代表总数据，1代表平均数据

	private Color background = new Color(99,43,142);
	private Color blue_light = new Color(46,117,182);
	private Color blue_light_changed = new Color(93,169,238);
	private String img_arrow_up_URL = "picture/arrow/arrow_up.jpg";
	private String img_arrow_up_changed_URL = "picture/arrow/arrow_up_changed.jpg";
	private String img_arrow_right_URL = "picture/arrow/arrow_right.jpg";
	private String img_arrow_right_changed_URL = "picture/arrow/arrow_right_changed.jpg";
	
	private DefaultTableModel tableModel_teamCompare_total;
	private JTable table_teamCompare_total;
	private JScrollPane scrollPane_teamCompare_total;
	
	private DefaultTableModel tableModel_teamCompare_average;
	private JTable table_teamCompare_average;
	private JScrollPane scrollPane_teamCompare_average;
	
	private JLabel label_arrow_up;
	private JLabel label_arrow_right;
	
	private JLabel label_data_total;
	private JLabel label_data_average;
	
	public TeamComparePanel(){
		this.setSize(teamComparePanel_width, teamComparePanel_height);
		this.setBackground(background);
		this.setVisible(true);
		
		uiController = UIController.getInstance();
		panel = this;
		
		//上指向箭头，返回搜索界面
		label_arrow_up = new JLabel();
		label_arrow_up.setOpaque(true);
		label_arrow_up.setBounds(1026, 14, 40, 40);
		ImageIcon icon_up = new ImageIcon(img_arrow_up_URL);
		label_arrow_up.setIcon(icon_up);
		label_arrow_up.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				ImageIcon icon_up = new ImageIcon(img_arrow_up_URL);
				label_arrow_up.setIcon(icon_up);
			}
			public void mouseEntered(MouseEvent arg0) {
				ImageIcon icon_up_changed = new ImageIcon(img_arrow_up_changed_URL);
				label_arrow_up.setIcon(icon_up_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				//跳转到搜索界面
				uiController.setSearchPanel();
			}
		});
		
		//向上翻页提示
		JLabel label_up_info = new JLabel("搜索界面");
		label_up_info.setOpaque(true);
		label_up_info.setBackground(background);
		label_up_info.setForeground(Color.WHITE);
		label_up_info.setFont(new FontUIResource("DialogInput", Font.BOLD, 20));
		label_up_info.setBounds(935, 20, 100, 28);
		
		//上方标题栏
		JLabel label_title = new JLabel("球队信息横向比较");
		label_title.setOpaque(true);
		label_title.setBackground(getBackground());
		label_title.setForeground(Color.WHITE);
		label_title.setFont(new FontUIResource("DialogInput", Font.BOLD, 40));
		label_title.setBounds(176, 42, 400, 44);
		
		//向右翻页提示
		JLabel label_right_info = new JLabel("球员信息横向比较");
		label_right_info.setOpaque(true);
		label_right_info.setBackground(background);
		label_right_info.setForeground(Color.WHITE);
		label_right_info.setFont(new FontUIResource("DialogInput", Font.BOLD, 20));
		label_right_info.setBounds(850, 68, 176, 28);
		
		//向右翻页标志
		label_arrow_right = new JLabel();
		label_arrow_right.setOpaque(true);
		label_arrow_right.setBounds(1026, 62, 40, 40);
		ImageIcon icon_right = new ImageIcon(img_arrow_right_URL);
		label_arrow_right.setIcon(icon_right);
		label_arrow_right.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				ImageIcon icon_right = new ImageIcon(img_arrow_right_URL);
				label_arrow_right.setIcon(icon_right);
			}
			public void mouseEntered(MouseEvent arg0) {
				ImageIcon icon_right_changed = new ImageIcon(img_arrow_right_changed_URL);
				label_arrow_right.setIcon(icon_right_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				//跳转到"球员信息横向比较"界面
				uiController.setPlayerComparePanel();
			}
		});
		
		//总数据显示
		label_data_total = new JLabel(" 总数据");
		label_data_total.setOpaque(true);
		label_data_total.setBackground(blue_light_changed);
		label_data_total.setForeground(Color.WHITE);
		label_data_total.setFont(new FontUIResource("DialogInput", Font.BOLD, 30));
		label_data_total.setBounds(0, 25, 132, 45);
		label_data_total.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {
				//显示球队总数据表格
				if(tableDisplay==1){
					label_data_total.setBackground(blue_light_changed);
					label_data_average.setBackground(blue_light);
					panel.remove(scrollPane_teamCompare_average);
					panel.add(scrollPane_teamCompare_total);
					tableDisplay = 0;
				}
			}
		});
		
		//平均数据显示
		label_data_average = new JLabel("平均数据");
		label_data_average.setOpaque(true);
		label_data_average.setBackground(blue_light);
		label_data_average.setForeground(Color.WHITE);
		label_data_average.setFont(new FontUIResource("DialogInput", Font.BOLD, 30));
		label_data_average.setBounds(0, 85, 132, 45);
		label_data_average.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {
				//显示球队评价数据表格
				if(tableDisplay==0){
					label_data_average.setBackground(blue_light_changed);
					label_data_total.setBackground(blue_light);
					panel.remove(scrollPane_teamCompare_total);
					panel.add(scrollPane_teamCompare_average);
					tableDisplay = 1;
				}
			}
		});
		
		this.setLayout(null);
		this.add(label_arrow_up);
		this.add(label_up_info);
		this.add(label_title);
		this.add(label_right_info);
		this.add(label_arrow_right);
		this.add(label_data_total);
		this.add(label_data_average);
	}
	
	public void setTeamsInfo(String[] properties_total,Object[][] values_total,String[] propertices_average,Object[][] values_average){
		//球队信息比较表格----总数据
		tableModel_teamCompare_total = new DefaultTableModel(values_total,properties_total){
			public boolean isCellEditable(int row, int column)
            {
                return false;
            }
		};
		table_teamCompare_total = new JTable(tableModel_teamCompare_total){
			public Component prepareRenderer(TableCellRenderer renderer,int row,int column){
				Component c=super.prepareRenderer(renderer,row,column);
				if(c instanceof JComponent){
					((JComponent)c).setOpaque(false);
				}
				return c;
			}
		};
		
		table_teamCompare_total.setFont(new FontUIResource("DialogInput", Font.PLAIN, 12));
		//设置点击表头自动排序
		table_teamCompare_total.setAutoCreateRowSorter(true);
		table_teamCompare_total.setOpaque(false);
		table_teamCompare_total.setForeground(Color.WHITE);
		table_teamCompare_total.setSelectionForeground(Color.CYAN);
		//第一方案
		table_teamCompare_total.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		//第二方案
			//实现表头折行显示
//				HeaderRendererHh renderer = new HeaderRendererHh();
//				TableColumnModel cmodel = table_teamCompare_total.getColumnModel();
//				for (int i = 0; i < cmodel.getColumnCount(); i++) {
//				    cmodel.getColumn(i).setHeaderRenderer(renderer);
//				}
		TableColumn firstColumn = table_teamCompare_total.getColumnModel().getColumn(0);
		firstColumn.setPreferredWidth(100);
		firstColumn.setMaxWidth(100);
		firstColumn.setMinWidth(100);

		table_teamCompare_total.getTableHeader().setFont(new FontUIResource("DialogInput", Font.PLAIN, 11));
		table_teamCompare_total.getTableHeader().setBackground(background);
		table_teamCompare_total.getTableHeader().setForeground(Color.WHITE);
		table_teamCompare_total.setColumnSelectionAllowed (true);
		table_teamCompare_total.setRowSelectionAllowed (false);
		table_teamCompare_total.getTableHeader().addMouseListener (new MouseAdapter() {  
             public void mouseReleased (MouseEvent e) {  
                 if (! e.isShiftDown())  
                	 table_teamCompare_total.clearSelection();  
                 //获取点击的列索引  
                 int pick = table_teamCompare_total.getTableHeader().columnAtPoint(e.getPoint());  
                 //设置选择模型 
                 table_teamCompare_total.addColumnSelectionInterval (pick, pick);
             }  
         });
		
		scrollPane_teamCompare_total = new JScrollPane(table_teamCompare_total);
		scrollPane_teamCompare_total.setOpaque(false);
		scrollPane_teamCompare_total.getViewport().setOpaque(false);
		scrollPane_teamCompare_total.setBounds(25, 160, 1050, 515);
		
		this.add(scrollPane_teamCompare_total);
		tableDisplay = 0;
		
		//球队信息比较表格----平均数据
		tableModel_teamCompare_average = new DefaultTableModel(values_average,propertices_average){
			public boolean isCellEditable(int row, int column)
            {
                return false;
            }
		};
		table_teamCompare_average = new JTable(tableModel_teamCompare_average){
			public Component prepareRenderer(TableCellRenderer renderer,int row,int column){
				Component c=super.prepareRenderer(renderer,row,column);
				if(c instanceof JComponent){
					((JComponent)c).setOpaque(false);
				}
				return c;
			}
		};
		
		table_teamCompare_average.setFont(new FontUIResource("DialogInput", Font.PLAIN, 12));
		//设置点击表头自动排序
		table_teamCompare_average.setAutoCreateRowSorter(true);
		table_teamCompare_average.setOpaque(false);
		table_teamCompare_average.setForeground(Color.WHITE);
		table_teamCompare_average.setSelectionForeground(Color.CYAN);
		//第一方案
		table_teamCompare_average.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		//第二方案
			//实现表头折行显示
//		HeaderRendererHh renderer2 = new HeaderRendererHh();
//		TableColumnModel cmodel2 = table_teamCompare_total.getColumnModel();
//		for (int i = 0; i < cmodel2.getColumnCount(); i++) {
//		    cmodel2.getColumn(i).setHeaderRenderer(renderer2);
//		}
		TableColumn firstColumn_average = table_teamCompare_average.getColumnModel().getColumn(0);
		firstColumn_average.setPreferredWidth(100);
		firstColumn_average.setMaxWidth(100);
		firstColumn_average.setMinWidth(100);

		table_teamCompare_average.getTableHeader().setFont(new FontUIResource("DialogInput", Font.PLAIN, 11));
		table_teamCompare_average.getTableHeader().setBackground(background);
		table_teamCompare_average.getTableHeader().setForeground(Color.WHITE);
		table_teamCompare_average.setColumnSelectionAllowed (true);
		table_teamCompare_average.setRowSelectionAllowed (false);
		table_teamCompare_average.getTableHeader().addMouseListener (new MouseAdapter() {  
             public void mouseReleased (MouseEvent e) {  
                 if (! e.isShiftDown())  
                	 table_teamCompare_average.clearSelection();  
                 //获取点击的列索引  
                 int pick = table_teamCompare_average.getTableHeader().columnAtPoint(e.getPoint());  
                 //设置选择模型 
                 table_teamCompare_average.addColumnSelectionInterval (pick, pick);
             }  
         });
		
		scrollPane_teamCompare_average = new JScrollPane(table_teamCompare_average);
		scrollPane_teamCompare_average.setOpaque(false);
		scrollPane_teamCompare_average.getViewport().setOpaque(false);
		scrollPane_teamCompare_average.setBounds(25, 160, 1050, 515);
	}
}
