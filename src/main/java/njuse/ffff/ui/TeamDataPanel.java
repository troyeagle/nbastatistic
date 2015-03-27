package njuse.ffff.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import njuse.ffff.presenter.ControllerService;
import njuse.ffff.presenter.UIController;

@SuppressWarnings("serial")
public class TeamDataPanel extends JPanel{
	private final int teamDataPanel_width = 1100;
	private final int teamDataPanel_height = 700;
	
	private ControllerService uiController;
	
	private Color background = new Color(99,43,142);
	private Color blue_light = new Color(46,117,182);
	private Color blue_light_changed = new Color(93,169,238);
	private String img_label_search_URL = "picture/transfer/label_search_small.jpg";
	private String img_label_search_changed_URL = "picture/transfer/label_search_changed_small.jpg";
	private String img_data_arrow_URL = "picture/arrow/data_arrow.jpg";
	private Image img_action;
	
	private JTextField text_searchInfo;
	private JLabel label_search;
	private JLabel label_team_data1;
	private JLabel label_team_data2;
	private JLabel label_team_data3;
	private JLabel label_team_return;
	private JLabel label_data_arrow;
	private JLabel label_team_name;
	
	private int currentTable = 0;//当前显示的表格编号
	
	private DefaultTableModel tableModel_teamInfo1_total;
	private JTable table_teamInfo1_total;
	private JScrollPane scrollPane_teamInfo1_total;
	
	private DefaultTableModel tableModel_teamInfo1_average;
	private JTable table_teamInfo1_average;
	private JScrollPane scrollPane_teamInfo1_average;
	
	private DefaultTableModel tableModel_teamInfo2_total;
	private JTable table_teamInfo2_total;
	private JScrollPane scrollPane_teamInfo2_total;
	
	private DefaultTableModel tableModel_teamInfo2_average;
	private JTable table_teamInfo2_average;
	private JScrollPane scrollPane_teamInfo2_average;
	
	private DefaultTableModel tableModel_teamInfo3_1;
	private JTable table_teamInfo3_1;
	private JScrollPane scrollPane_teamInfo3_1;

	private DefaultTableModel tableModel_teamInfo3_2;
	private JTable table_teamInfo3_2;
	private JScrollPane scrollPane_teamInfo3_2;
	
	private DefaultTableModel tableModel_teamInfo1_ratio;
	private JTable table_teamInfo1_ratio;
	private JScrollPane scrollPane_teamInfo1_ratio;

	private DefaultTableModel tableModel_teamInfo2_ratio;
	private JTable table_teamInfo2_ratio;
	private JScrollPane scrollPane_teamInfo2_ratio;
	
	public TeamDataPanel(){
		setOpaque(false);
		this.setSize(teamDataPanel_width, teamDataPanel_height);
		this.setBackground(background);
		this.setVisible(true);
		
		uiController = UIController.getInstance();
		
		//球队姓名
		label_team_name = new JLabel();
		label_team_name.setOpaque(true);
		label_team_name.setBackground(background);
		label_team_name.setForeground(Color.WHITE);
		label_team_name.setFont(new FontUIResource("DialogInput", Font.BOLD, 50));
		label_team_name.setBounds(108, 62, 600, 44);
		
		//便捷搜索框
		JLabel label_searchDialog = new JLabel();
		label_searchDialog.setOpaque(true);
		label_searchDialog.setBackground(Color.gray);
		label_searchDialog.setBounds(850, 40, 210, 32);
		
		text_searchInfo = new JTextField("输入球队名/球员名");
		text_searchInfo.setBounds(2, 2, 176, 28);
		text_searchInfo.setBorder(new EmptyBorder(0,0,0,0));
		text_searchInfo.setForeground(Color.gray);
		text_searchInfo.setFont(new FontUIResource("DialogInput", Font.BOLD, 18));
		text_searchInfo.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent arg0) {
				//当输入信息为空
				if(text_searchInfo.getText().equals("")){
					text_searchInfo.setText("输入球队名/球员名");
				}
			}
			public void focusGained(FocusEvent arg0) {
				//消去默认初始的提示文字
				if(text_searchInfo.getText().equals("输入球队名/球员名")){
					text_searchInfo.setText("");
				}
			}
		});
		
		label_search = new JLabel();
		label_search.setOpaque(true);
		label_search.setBounds(180, 4, 24, 24);
		ImageIcon image = new ImageIcon(img_label_search_URL);
        label_search.setIcon(image);
        label_search.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				ImageIcon image = new ImageIcon(img_label_search_URL);
				label_search.setIcon(image);
			}
			public void mouseEntered(MouseEvent arg0) {
				ImageIcon image_changed = new ImageIcon(img_label_search_changed_URL);
				label_search.setIcon(image_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				// TODO 跳转到搜索结果界面
			}
		});
        
        label_searchDialog.setLayout(null);
        label_searchDialog.add(text_searchInfo);
        label_searchDialog.add(label_search);
        
        //左侧数据引导框1
        label_team_data1 = new JLabel(" 各种数据1");
        label_team_data1.setOpaque(true);
        label_team_data1.setBackground(blue_light);
        label_team_data1.setForeground(Color.WHITE);
        label_team_data1.setFont(new FontUIResource("DialogInput", Font.BOLD, 24));
        label_team_data1.setBounds(0, 206, 138, 46);
        label_team_data1.addMouseListener(new MouseListener() {
        	public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				label_team_data1.setBackground(blue_light);
			}
			public void mouseEntered(MouseEvent arg0) {
				label_team_data1.setBackground(blue_light_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				displayTables(1);
			}
		});
        
        //左侧数据引导框2
        label_team_data2 = new JLabel(" 各种数据2");
        label_team_data2.setOpaque(true);
        label_team_data2.setBackground(blue_light);
        label_team_data2.setForeground(Color.WHITE);
        label_team_data2.setFont(new FontUIResource("DialogInput", Font.BOLD, 24));
        label_team_data2.setBounds(0, 282, 138, 46);
        label_team_data2.addMouseListener(new MouseListener() {
        	public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				label_team_data2.setBackground(blue_light);
			}
			public void mouseEntered(MouseEvent arg0) {
				label_team_data2.setBackground(blue_light_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				displayTables(2);
			}
		});
        
        //左侧数据引导框3
        label_team_data3 = new JLabel(" 各种数据3");
        label_team_data3.setOpaque(true);
        label_team_data3.setBackground(blue_light);
        label_team_data3.setForeground(Color.WHITE);
        label_team_data3.setFont(new FontUIResource("DialogInput", Font.BOLD, 24));
        label_team_data3.setBounds(0, 358, 138, 46);
        label_team_data3.addMouseListener(new MouseListener() {
        	public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				label_team_data3.setBackground(blue_light);
			}
			public void mouseEntered(MouseEvent arg0) {
				label_team_data3.setBackground(blue_light_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				displayTables(3);
			}
		});
        
        //左侧返回球员简介界面框
        label_team_return = new JLabel(" 返回");
        label_team_return.setOpaque(true);
        label_team_return.setBackground(blue_light);
        label_team_return.setForeground(Color.WHITE);
        label_team_return.setFont(new FontUIResource("DialogInput", Font.BOLD, 24));
        label_team_return.setBounds(0, 434, 90, 46);
        label_team_return.addMouseListener(new MouseListener() {
        	public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				label_team_return.setBackground(blue_light);
			}
			public void mouseEntered(MouseEvent arg0) {
				label_team_return.setBackground(blue_light_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				//跳转到球队介绍界面
				uiController.changeToTeamProfilePanel();
			}
		});
        
        //数据显示指向箭头
        label_data_arrow = new JLabel();
        label_data_arrow.setOpaque(true);
        label_data_arrow.setSize(23, 46);
        ImageIcon icon_data_arrow = new ImageIcon(img_data_arrow_URL);
        label_data_arrow.setIcon(icon_data_arrow);
        
        //表格显示数据
        
		
		this.setLayout(null);
		this.add(label_team_name);
		this.add(label_searchDialog);
		this.add(label_team_data1);
		this.add(label_team_data2);
		this.add(label_team_data3);
		this.add(label_team_return);
		this.add(label_data_arrow);
	}
	
	//设置球队姓名
	public void setName(String player_name){
		label_team_name.setText(player_name);
	}
	
	//设置表格数据
	public void setData(String[] properties1,String[] properties1_2,String[] properties2
			,String[] properties2_2,String[] properties3,String[] properties3_2
			,Object[][] values_total1,Object[][] values_average1,Object[][] values_ratio1
			,Object[][] values_total2,Object[][] values_average2,Object[][] values_ratio2
			,Object[][] values3_1,Object[][] values3_2){
		//球员数据表格1
		tableModel_teamInfo1_total = new DefaultTableModel(values_total1,properties1);
		table_teamInfo1_total = new JTable(tableModel_teamInfo1_total){
			public Component prepareRenderer(TableCellRenderer renderer,int row,int column){
				Component c=super.prepareRenderer(renderer,row,column);
				if(c instanceof JComponent){
					((JComponent)c).setOpaque(false);
				}
				return c;
			}
		};
		table_teamInfo1_total.setOpaque(false);
		table_teamInfo1_total.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table_teamInfo1_total.setForeground(Color.WHITE);
		table_teamInfo1_total.setSelectionForeground(Color.CYAN);

		table_teamInfo1_total.getTableHeader().setBackground(background);
		table_teamInfo1_total.getTableHeader().setForeground(Color.WHITE);
		table_teamInfo1_total.getTableHeader().addMouseListener (new MouseAdapter() { 
            public void mouseReleased (MouseEvent e) {  
                if (! e.isShiftDown())  
                	table_teamInfo1_total.clearSelection();
                //获取点击的列索引  
                int pick = table_teamInfo1_total.getTableHeader().columnAtPoint(e.getPoint());  
                //设置选择模型 
                table_teamInfo1_total.addColumnSelectionInterval (pick, pick);
            }  
        });
		
		scrollPane_teamInfo1_total = new JScrollPane(table_teamInfo1_total);
		scrollPane_teamInfo1_total.setOpaque(false);
		scrollPane_teamInfo1_total.getViewport().setOpaque(false);
		scrollPane_teamInfo1_total.setBounds(200, 160, 650, 135);
		
		//球员数据表格2
		tableModel_teamInfo2_total = new DefaultTableModel(values_total2,properties2);
		table_teamInfo2_total = new JTable(tableModel_teamInfo2_total){
			public Component prepareRenderer(TableCellRenderer renderer,int row,int column){
				Component c=super.prepareRenderer(renderer,row,column);
				if(c instanceof JComponent){
					((JComponent)c).setOpaque(false);
				}
				return c;
			}
		};
		table_teamInfo2_total.setOpaque(false);
		table_teamInfo2_total.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table_teamInfo2_total.setForeground(Color.WHITE);
		table_teamInfo2_total.setSelectionForeground(Color.CYAN);

		table_teamInfo2_total.getTableHeader().setBackground(background);
		table_teamInfo2_total.getTableHeader().setForeground(Color.WHITE);
		table_teamInfo2_total.getTableHeader().addMouseListener (new MouseAdapter() { 
            public void mouseReleased (MouseEvent e) {  
                if (! e.isShiftDown())  
                	table_teamInfo2_total.clearSelection();
                //获取点击的列索引  
                int pick = table_teamInfo2_total.getTableHeader().columnAtPoint(e.getPoint());  
                //设置选择模型 
                table_teamInfo2_total.addColumnSelectionInterval (pick, pick);
            }  
        });
		
		scrollPane_teamInfo2_total = new JScrollPane(table_teamInfo2_total);
		scrollPane_teamInfo2_total.setOpaque(false);
		scrollPane_teamInfo2_total.getViewport().setOpaque(false);
		scrollPane_teamInfo2_total.setBounds(200, 160, 650, 135);
		
		//球员数据表格3
		tableModel_teamInfo3_1 = new DefaultTableModel(values3_1,properties3);
		table_teamInfo3_1 = new JTable(tableModel_teamInfo3_1){
			public Component prepareRenderer(TableCellRenderer renderer,int row,int column){
				Component c=super.prepareRenderer(renderer,row,column);
				if(c instanceof JComponent){
					((JComponent)c).setOpaque(false);
				}
				return c;
			}
		};
		table_teamInfo3_1.setOpaque(false);
		table_teamInfo3_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table_teamInfo3_1.setForeground(Color.WHITE);
		table_teamInfo3_1.setSelectionForeground(Color.CYAN);

		table_teamInfo3_1.getTableHeader().setBackground(background);
		table_teamInfo3_1.getTableHeader().setForeground(Color.WHITE);
		table_teamInfo3_1.getTableHeader().addMouseListener (new MouseAdapter() { 
            public void mouseReleased (MouseEvent e) {  
                if (! e.isShiftDown())  
                	table_teamInfo3_1.clearSelection();
                //获取点击的列索引  
                int pick = table_teamInfo3_1.getTableHeader().columnAtPoint(e.getPoint());  
                //设置选择模型 
                table_teamInfo3_1.addColumnSelectionInterval (pick, pick);
            }  
        });
		
		scrollPane_teamInfo3_1 = new JScrollPane(table_teamInfo3_1);
		scrollPane_teamInfo3_1.setOpaque(false);
		scrollPane_teamInfo3_1.getViewport().setOpaque(false);
		scrollPane_teamInfo3_1.setBounds(200, 160, 550, 135);
		
		
		//球员数据表格1
		tableModel_teamInfo1_average = new DefaultTableModel(values_average1,properties1);
		table_teamInfo1_average = new JTable(tableModel_teamInfo1_average){
			public Component prepareRenderer(TableCellRenderer renderer,int row,int column){
				Component c=super.prepareRenderer(renderer,row,column);
				if(c instanceof JComponent){
					((JComponent)c).setOpaque(false);
				}
				return c;
			}
		};
		table_teamInfo1_average.setOpaque(false);
		table_teamInfo1_average.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table_teamInfo1_average.setForeground(Color.WHITE);
		table_teamInfo1_average.setSelectionForeground(Color.CYAN);

		table_teamInfo1_average.getTableHeader().setBackground(background);
		table_teamInfo1_average.getTableHeader().setForeground(Color.WHITE);
		table_teamInfo1_average.getTableHeader().addMouseListener (new MouseAdapter() { 
            public void mouseReleased (MouseEvent e) {  
                if (! e.isShiftDown())  
                	table_teamInfo1_average.clearSelection();
                //获取点击的列索引  
                int pick = table_teamInfo1_average.getTableHeader().columnAtPoint(e.getPoint());  
                //设置选择模型 
                table_teamInfo1_average.addColumnSelectionInterval (pick, pick);
            }  
        });
		
		scrollPane_teamInfo1_average = new JScrollPane(table_teamInfo1_average);
		scrollPane_teamInfo1_average.setOpaque(false);
		scrollPane_teamInfo1_average.getViewport().setOpaque(false);
		scrollPane_teamInfo1_average.setBounds(200, 320, 650, 135);
		
		//球员数据表格2
		tableModel_teamInfo2_average = new DefaultTableModel(values_average2,properties2);
		table_teamInfo2_average = new JTable(tableModel_teamInfo2_average){
			public Component prepareRenderer(TableCellRenderer renderer,int row,int column){
				Component c=super.prepareRenderer(renderer,row,column);
				if(c instanceof JComponent){
					((JComponent)c).setOpaque(false);
				}
				return c;
			}
		};
		table_teamInfo2_average.setOpaque(false);
		table_teamInfo2_average.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table_teamInfo2_average.setForeground(Color.WHITE);
		table_teamInfo2_average.setSelectionForeground(Color.CYAN);

		table_teamInfo2_average.getTableHeader().setBackground(background);
		table_teamInfo2_average.getTableHeader().setForeground(Color.WHITE);
		table_teamInfo2_average.getTableHeader().addMouseListener (new MouseAdapter() { 
            public void mouseReleased (MouseEvent e) {  
                if (! e.isShiftDown())  
                	table_teamInfo2_average.clearSelection();
                //获取点击的列索引  
                int pick = table_teamInfo2_average.getTableHeader().columnAtPoint(e.getPoint());  
                //设置选择模型 
                table_teamInfo2_average.addColumnSelectionInterval (pick, pick);
            }  
        });
		
		scrollPane_teamInfo2_average = new JScrollPane(table_teamInfo2_average);
		scrollPane_teamInfo2_average.setOpaque(false);
		scrollPane_teamInfo2_average.getViewport().setOpaque(false);
		scrollPane_teamInfo2_average.setBounds(200, 320, 650, 135);
		
		//球员数据表格3
		tableModel_teamInfo3_2 = new DefaultTableModel(values3_2,properties3);
		table_teamInfo3_2 = new JTable(tableModel_teamInfo3_2){
			public Component prepareRenderer(TableCellRenderer renderer,int row,int column){
				Component c=super.prepareRenderer(renderer,row,column);
				if(c instanceof JComponent){
					((JComponent)c).setOpaque(false);
				}
				return c;
			}
		};
		table_teamInfo3_2.setOpaque(false);
		table_teamInfo3_2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table_teamInfo3_2.setForeground(Color.WHITE);
		table_teamInfo3_2.setSelectionForeground(Color.CYAN);

		table_teamInfo3_2.getTableHeader().setBackground(background);
		table_teamInfo3_2.getTableHeader().setForeground(Color.WHITE);
		table_teamInfo3_2.getTableHeader().addMouseListener (new MouseAdapter() { 
            public void mouseReleased (MouseEvent e) {  
                if (! e.isShiftDown())  
                	table_teamInfo3_2.clearSelection();
                //获取点击的列索引  
                int pick = table_teamInfo3_2.getTableHeader().columnAtPoint(e.getPoint());  
                //设置选择模型 
                table_teamInfo3_2.addColumnSelectionInterval (pick, pick);
            }  
        });
		
		scrollPane_teamInfo3_2 = new JScrollPane(table_teamInfo3_2);
		scrollPane_teamInfo3_2.setOpaque(false);
		scrollPane_teamInfo3_2.getViewport().setOpaque(false);
		scrollPane_teamInfo3_2.setBounds(200, 350, 550, 155);
		
		//球员数据表格1_2
		tableModel_teamInfo1_ratio = new DefaultTableModel(values_ratio1,properties1_2);
		table_teamInfo1_ratio = new JTable(tableModel_teamInfo1_ratio){
			public Component prepareRenderer(TableCellRenderer renderer,int row,int column){
				Component c=super.prepareRenderer(renderer,row,column);
				if(c instanceof JComponent){
					((JComponent)c).setOpaque(false);
				}
				return c;
			}
		};
		table_teamInfo1_ratio.setOpaque(false);
		table_teamInfo1_ratio.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table_teamInfo1_ratio.setForeground(Color.WHITE);
		table_teamInfo1_ratio.setSelectionForeground(Color.CYAN);
	
		table_teamInfo1_ratio.getTableHeader().setBackground(background);
		table_teamInfo1_ratio.getTableHeader().setForeground(Color.WHITE);
		table_teamInfo1_ratio.getTableHeader().addMouseListener (new MouseAdapter() { 
	        public void mouseReleased (MouseEvent e) {  
	            if (! e.isShiftDown())  
	            	table_teamInfo1_ratio.clearSelection();
	            //获取点击的列索引  
	            int pick = table_teamInfo1_ratio.getTableHeader().columnAtPoint(e.getPoint());  
	            //设置选择模型 
	            table_teamInfo1_ratio.addColumnSelectionInterval (pick, pick);
	        }  
	    });
		
		scrollPane_teamInfo1_ratio = new JScrollPane(table_teamInfo1_ratio);
		scrollPane_teamInfo1_ratio.setOpaque(false);
		scrollPane_teamInfo1_ratio.getViewport().setOpaque(false);
		scrollPane_teamInfo1_ratio.setBounds(200, 480, 550, 135);
				
		//球员数据表格2_2
		tableModel_teamInfo2_ratio = new DefaultTableModel(values_ratio2,properties2_2);
		table_teamInfo2_ratio = new JTable(tableModel_teamInfo2_ratio){
			public Component prepareRenderer(TableCellRenderer renderer,int row,int column){
				Component c=super.prepareRenderer(renderer,row,column);
				if(c instanceof JComponent){
					((JComponent)c).setOpaque(false);
				}
				return c;
			}
		};
		table_teamInfo2_ratio.setOpaque(false);
		table_teamInfo2_ratio.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table_teamInfo2_ratio.setForeground(Color.WHITE);
		table_teamInfo2_ratio.setSelectionForeground(Color.CYAN);

		table_teamInfo2_ratio.getTableHeader().setBackground(background);
		table_teamInfo2_ratio.getTableHeader().setForeground(Color.WHITE);
		table_teamInfo2_ratio.getTableHeader().addMouseListener (new MouseAdapter() { 
            public void mouseReleased (MouseEvent e) {  
                if (! e.isShiftDown())  
                	table_teamInfo1_average.clearSelection();
                //获取点击的列索引  
                int pick = table_teamInfo2_ratio.getTableHeader().columnAtPoint(e.getPoint());  
                //设置选择模型 
                table_teamInfo2_ratio.addColumnSelectionInterval (pick, pick);
            }  
        });
		
		scrollPane_teamInfo2_ratio = new JScrollPane(table_teamInfo2_ratio);
		scrollPane_teamInfo2_ratio.setOpaque(false);
		scrollPane_teamInfo2_ratio.getViewport().setOpaque(false);
		scrollPane_teamInfo2_ratio.setBounds(200, 480, 550, 135);
	}
	
	public void displayTables(int number){
		label_data_arrow.setLocation(138, 76*number+130);
		switch(currentTable){
		case 1:
			this.remove(scrollPane_teamInfo1_total);
			this.remove(scrollPane_teamInfo1_average);
			this.remove(scrollPane_teamInfo1_ratio);
			break;
		case 2:
			this.remove(scrollPane_teamInfo2_total);
			this.remove(scrollPane_teamInfo2_average);
			this.remove(scrollPane_teamInfo2_ratio);
			break;
		case 3:
			this.remove(scrollPane_teamInfo3_1);
			this.remove(scrollPane_teamInfo3_2);
			break;
		}
		this.repaint();
		switch(number){
		case 1:
			this.add(scrollPane_teamInfo1_total,0);
			this.add(scrollPane_teamInfo1_average,0);
			this.add(scrollPane_teamInfo1_ratio,0);
			break;
		case 2:
			this.add(scrollPane_teamInfo2_total,0);
			this.add(scrollPane_teamInfo2_average,0);
			this.add(scrollPane_teamInfo2_ratio,0);
			break;
		case 3:
			this.add(scrollPane_teamInfo3_1,0);
			this.add(scrollPane_teamInfo3_2,0);
			break;
		}
		currentTable = number;
	}
}
