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
public class PlayerDataPanel extends JPanel{
	private final int playerDataPanel_width = 1100;
	private final int playerDataPanel_height = 700;
	
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
	private JLabel label_player_data1;
	private JLabel label_player_data2;
	private JLabel label_player_data3;
	private JLabel label_player_data4;
	private JLabel label_player_return;
	private JLabel label_data_arrow;
	private JLabel label_player_name;
	
	private int currentTable = 0;//当前显示的表格编号
	
	private DefaultTableModel tableModel_playerInfo1_total;
	private JTable table_playerInfo1_total;
	private JScrollPane scrollPane_playerInfo1_total;
	
	private DefaultTableModel tableModel_playerInfo1_average;
	private JTable table_playerInfo1_average;
	private JScrollPane scrollPane_playerInfo1_average;
	
	private DefaultTableModel tableModel_playerInfo2_total;
	private JTable table_playerInfo2_total;
	private JScrollPane scrollPane_playerInfo2_total;
	
	private DefaultTableModel tableModel_playerInfo2_average;
	private JTable table_playerInfo2_average;
	private JScrollPane scrollPane_playerInfo2_average;
	
	private DefaultTableModel tableModel_playerInfo3_total;
	private JTable table_playerInfo3_total;
	private JScrollPane scrollPane_playerInfo3_total;

	private DefaultTableModel tableModel_playerInfo3_average;
	private JTable table_playerInfo3_average;
	private JScrollPane scrollPane_playerInfo3_average;
	
	private DefaultTableModel tableModel_playerInfo4_total;
	private JTable table_playerInfo4_total;
	private JScrollPane scrollPane_playerInfo4_total;

	private DefaultTableModel tableModel_playerInfo4_average;
	private JTable table_playerInfo4_average;
	private JScrollPane scrollPane_playerInfo4_average;
	
	public PlayerDataPanel(){
		setOpaque(false);
		this.setSize(playerDataPanel_width, playerDataPanel_height);
		this.setBackground(background);
		this.setVisible(true);
		
		uiController = UIController.getInstance();
		
		//球员姓名
		label_player_name = new JLabel();
		label_player_name.setOpaque(true);
		label_player_name.setBackground(background);
		label_player_name.setForeground(Color.WHITE);
		label_player_name.setFont(new FontUIResource("DialogInput", Font.BOLD, 50));
		label_player_name.setBounds(108, 62, 600, 44);
		
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
        label_player_data1 = new JLabel(" 各种数据1");
        label_player_data1.setOpaque(true);
        label_player_data1.setBackground(blue_light);
        label_player_data1.setForeground(Color.WHITE);
        label_player_data1.setFont(new FontUIResource("DialogInput", Font.BOLD, 24));
        label_player_data1.setBounds(0, 206, 138, 46);
        label_player_data1.addMouseListener(new MouseListener() {
        	public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				label_player_data1.setBackground(blue_light);
			}
			public void mouseEntered(MouseEvent arg0) {
				label_player_data1.setBackground(blue_light_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				displayTables(1);
			}
		});
        
        //左侧数据引导框2
        label_player_data2 = new JLabel(" 各种数据2");
        label_player_data2.setOpaque(true);
        label_player_data2.setBackground(blue_light);
        label_player_data2.setForeground(Color.WHITE);
        label_player_data2.setFont(new FontUIResource("DialogInput", Font.BOLD, 24));
        label_player_data2.setBounds(0, 282, 138, 46);
        label_player_data2.addMouseListener(new MouseListener() {
        	public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				label_player_data2.setBackground(blue_light);
			}
			public void mouseEntered(MouseEvent arg0) {
				label_player_data2.setBackground(blue_light_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				displayTables(2);
			}
		});
        
        //左侧数据引导框3
        label_player_data3 = new JLabel(" 各种数据3");
        label_player_data3.setOpaque(true);
        label_player_data3.setBackground(blue_light);
        label_player_data3.setForeground(Color.WHITE);
        label_player_data3.setFont(new FontUIResource("DialogInput", Font.BOLD, 24));
        label_player_data3.setBounds(0, 358, 138, 46);
        label_player_data3.addMouseListener(new MouseListener() {
        	public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				label_player_data3.setBackground(blue_light);
			}
			public void mouseEntered(MouseEvent arg0) {
				label_player_data3.setBackground(blue_light_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				displayTables(3);
			}
		});
        
        //左侧数据引导框4
        label_player_data4 = new JLabel(" 各种数据4");
        label_player_data4.setOpaque(true);
        label_player_data4.setBackground(blue_light);
        label_player_data4.setForeground(Color.WHITE);
        label_player_data4.setFont(new FontUIResource("DialogInput", Font.BOLD, 24));
        label_player_data4.setBounds(0, 434, 138, 46);
        label_player_data4.addMouseListener(new MouseListener() {
        	public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				label_player_data4.setBackground(blue_light);
			}
			public void mouseEntered(MouseEvent arg0) {
				label_player_data4.setBackground(blue_light_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				displayTables(4);
			}
		});
        
        //左侧返回球员简介界面框
        label_player_return = new JLabel(" 返回");
        label_player_return.setOpaque(true);
        label_player_return.setBackground(blue_light);
        label_player_return.setForeground(Color.WHITE);
        label_player_return.setFont(new FontUIResource("DialogInput", Font.BOLD, 24));
        label_player_return.setBounds(0, 510, 90, 46);
        label_player_return.addMouseListener(new MouseListener() {
        	public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				label_player_return.setBackground(blue_light);
			}
			public void mouseEntered(MouseEvent arg0) {
				label_player_return.setBackground(blue_light_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				//跳转到球员介绍界面
				uiController.changeToPlayerProfilePanel();
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
		this.add(label_player_name);
		this.add(label_searchDialog);
		this.add(label_player_data1);
		this.add(label_player_data2);
		this.add(label_player_data3);
		this.add(label_player_data4);
		this.add(label_player_return);
		this.add(label_data_arrow);
	}
	
	//设置球员姓名和动作图像
	public void setAction(String player_name,Image img_action){
		label_player_name.setText(player_name);
		this.img_action = img_action;
		this.repaint();
	}
	
	//显示背景标志
	public void paint(Graphics g){
		g.drawImage(img_action, 670, 0, null);
		super.paint(g);
	}
	
	//设置表格数据
	public void setData(String[] properties1,String[] properties2,String[] properties3,String[] properties4
			,Object[][] values_total1,Object[][] values_total2,Object[][] values_total3,Object[][] values_total4
			,Object[][] values_average1,Object[][] values_average2,Object[][] values_average3,Object[][] values_average4){
		//球员数据表格1
		tableModel_playerInfo1_total = new DefaultTableModel(values_total1,properties1);
		table_playerInfo1_total = new JTable(tableModel_playerInfo1_total){
			public Component prepareRenderer(TableCellRenderer renderer,int row,int column){
				Component c=super.prepareRenderer(renderer,row,column);
				if(c instanceof JComponent){
					((JComponent)c).setOpaque(false);
				}
				return c;
			}
		};
		table_playerInfo1_total.setOpaque(false);
		table_playerInfo1_total.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table_playerInfo1_total.setForeground(Color.WHITE);
		table_playerInfo1_total.setSelectionForeground(Color.CYAN);

		table_playerInfo1_total.getTableHeader().setBackground(background);
		table_playerInfo1_total.getTableHeader().setForeground(Color.WHITE);
		table_playerInfo1_total.getTableHeader().addMouseListener (new MouseAdapter() { 
            public void mouseReleased (MouseEvent e) {  
                if (! e.isShiftDown())  
                	table_playerInfo1_total.clearSelection();
                //获取点击的列索引  
                int pick = table_playerInfo1_total.getTableHeader().columnAtPoint(e.getPoint());  
                //设置选择模型 
                table_playerInfo1_total.addColumnSelectionInterval (pick, pick);
            }  
        });
		
		scrollPane_playerInfo1_total = new JScrollPane(table_playerInfo1_total);
		scrollPane_playerInfo1_total.setOpaque(false);
		scrollPane_playerInfo1_total.getViewport().setOpaque(false);
		scrollPane_playerInfo1_total.setBounds(200, 160, 650, 155);
		
		//球员数据表格2
		tableModel_playerInfo2_total = new DefaultTableModel(values_total2,properties2);
		table_playerInfo2_total = new JTable(tableModel_playerInfo2_total){
			public Component prepareRenderer(TableCellRenderer renderer,int row,int column){
				Component c=super.prepareRenderer(renderer,row,column);
				if(c instanceof JComponent){
					((JComponent)c).setOpaque(false);
				}
				return c;
			}
		};
		table_playerInfo2_total.setOpaque(false);
		table_playerInfo2_total.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table_playerInfo2_total.setForeground(Color.WHITE);
		table_playerInfo2_total.setSelectionForeground(Color.CYAN);

		table_playerInfo2_total.getTableHeader().setBackground(background);
		table_playerInfo2_total.getTableHeader().setForeground(Color.WHITE);
		table_playerInfo2_total.getTableHeader().addMouseListener (new MouseAdapter() { 
            public void mouseReleased (MouseEvent e) {  
                if (! e.isShiftDown())  
                	table_playerInfo2_total.clearSelection();
                //获取点击的列索引  
                int pick = table_playerInfo2_total.getTableHeader().columnAtPoint(e.getPoint());  
                //设置选择模型 
                table_playerInfo2_total.addColumnSelectionInterval (pick, pick);
            }  
        });
		
		scrollPane_playerInfo2_total = new JScrollPane(table_playerInfo2_total);
		scrollPane_playerInfo2_total.setOpaque(false);
		scrollPane_playerInfo2_total.getViewport().setOpaque(false);
		scrollPane_playerInfo2_total.setBounds(200, 160, 650, 155);
		
		//球员数据表格3
		tableModel_playerInfo3_total = new DefaultTableModel(values_total3,properties3);
		table_playerInfo3_total = new JTable(tableModel_playerInfo3_total){
			public Component prepareRenderer(TableCellRenderer renderer,int row,int column){
				Component c=super.prepareRenderer(renderer,row,column);
				if(c instanceof JComponent){
					((JComponent)c).setOpaque(false);
				}
				return c;
			}
		};
		table_playerInfo3_total.setOpaque(false);
		table_playerInfo3_total.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table_playerInfo3_total.setForeground(Color.WHITE);
		table_playerInfo3_total.setSelectionForeground(Color.CYAN);

		table_playerInfo3_total.getTableHeader().setBackground(background);
		table_playerInfo3_total.getTableHeader().setForeground(Color.WHITE);
		table_playerInfo3_total.getTableHeader().addMouseListener (new MouseAdapter() { 
            public void mouseReleased (MouseEvent e) {  
                if (! e.isShiftDown())  
                	table_playerInfo3_total.clearSelection();
                //获取点击的列索引  
                int pick = table_playerInfo3_total.getTableHeader().columnAtPoint(e.getPoint());  
                //设置选择模型 
                table_playerInfo3_total.addColumnSelectionInterval (pick, pick);
            }  
        });
		
		scrollPane_playerInfo3_total = new JScrollPane(table_playerInfo3_total);
		scrollPane_playerInfo3_total.setOpaque(false);
		scrollPane_playerInfo3_total.getViewport().setOpaque(false);
		scrollPane_playerInfo3_total.setBounds(200, 160, 550, 155);
		
		//球员数据表格4
		tableModel_playerInfo4_total = new DefaultTableModel(values_total4,properties4);
		table_playerInfo4_total = new JTable(tableModel_playerInfo4_total){
			public Component prepareRenderer(TableCellRenderer renderer,int row,int column){
				Component c=super.prepareRenderer(renderer,row,column);
				if(c instanceof JComponent){
					((JComponent)c).setOpaque(false);
				}
				return c;
			}
		};
		table_playerInfo4_total.setOpaque(false);
		table_playerInfo4_total.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table_playerInfo4_total.setForeground(Color.WHITE);
		table_playerInfo4_total.setSelectionForeground(Color.CYAN);

		table_playerInfo4_total.getTableHeader().setBackground(background);
		table_playerInfo4_total.getTableHeader().setForeground(Color.WHITE);
		table_playerInfo4_total.getTableHeader().addMouseListener (new MouseAdapter() { 
            public void mouseReleased (MouseEvent e) {  
                if (! e.isShiftDown())  
                	table_playerInfo4_total.clearSelection();
                //获取点击的列索引  
                int pick = table_playerInfo4_total.getTableHeader().columnAtPoint(e.getPoint());  
                //设置选择模型 
                table_playerInfo4_total.addColumnSelectionInterval (pick, pick);
            }  
        });
		
		scrollPane_playerInfo4_total = new JScrollPane(table_playerInfo4_total);
		scrollPane_playerInfo4_total.setOpaque(false);
		scrollPane_playerInfo4_total.getViewport().setOpaque(false);
		scrollPane_playerInfo4_total.setBounds(200, 160, 450, 155);
		
		//球员数据表格1
		tableModel_playerInfo1_average = new DefaultTableModel(values_average1,properties1);
		table_playerInfo1_average = new JTable(tableModel_playerInfo1_average){
			public Component prepareRenderer(TableCellRenderer renderer,int row,int column){
				Component c=super.prepareRenderer(renderer,row,column);
				if(c instanceof JComponent){
					((JComponent)c).setOpaque(false);
				}
				return c;
			}
		};
		table_playerInfo1_average.setOpaque(false);
		table_playerInfo1_average.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table_playerInfo1_average.setForeground(Color.WHITE);
		table_playerInfo1_average.setSelectionForeground(Color.CYAN);

		table_playerInfo1_average.getTableHeader().setBackground(background);
		table_playerInfo1_average.getTableHeader().setForeground(Color.WHITE);
		table_playerInfo1_average.getTableHeader().addMouseListener (new MouseAdapter() { 
            public void mouseReleased (MouseEvent e) {  
                if (! e.isShiftDown())  
                	table_playerInfo1_average.clearSelection();
                //获取点击的列索引  
                int pick = table_playerInfo1_average.getTableHeader().columnAtPoint(e.getPoint());  
                //设置选择模型 
                table_playerInfo1_average.addColumnSelectionInterval (pick, pick);
            }  
        });
		
		scrollPane_playerInfo1_average = new JScrollPane(table_playerInfo1_average);
		scrollPane_playerInfo1_average.setOpaque(false);
		scrollPane_playerInfo1_average.getViewport().setOpaque(false);
		scrollPane_playerInfo1_average.setBounds(200, 350, 650, 155);
		
		//球员数据表格2
		tableModel_playerInfo2_average = new DefaultTableModel(values_average2,properties2);
		table_playerInfo2_average = new JTable(tableModel_playerInfo2_average){
			public Component prepareRenderer(TableCellRenderer renderer,int row,int column){
				Component c=super.prepareRenderer(renderer,row,column);
				if(c instanceof JComponent){
					((JComponent)c).setOpaque(false);
				}
				return c;
			}
		};
		table_playerInfo2_average.setOpaque(false);
		table_playerInfo2_average.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table_playerInfo2_average.setForeground(Color.WHITE);
		table_playerInfo2_average.setSelectionForeground(Color.CYAN);

		table_playerInfo2_average.getTableHeader().setBackground(background);
		table_playerInfo2_average.getTableHeader().setForeground(Color.WHITE);
		table_playerInfo2_average.getTableHeader().addMouseListener (new MouseAdapter() { 
            public void mouseReleased (MouseEvent e) {  
                if (! e.isShiftDown())  
                	table_playerInfo2_average.clearSelection();
                //获取点击的列索引  
                int pick = table_playerInfo2_average.getTableHeader().columnAtPoint(e.getPoint());  
                //设置选择模型 
                table_playerInfo2_average.addColumnSelectionInterval (pick, pick);
            }  
        });
		
		scrollPane_playerInfo2_average = new JScrollPane(table_playerInfo2_average);
		scrollPane_playerInfo2_average.setOpaque(false);
		scrollPane_playerInfo2_average.getViewport().setOpaque(false);
		scrollPane_playerInfo2_average.setBounds(200, 350, 650, 155);
		
		//球员数据表格3
		tableModel_playerInfo3_average = new DefaultTableModel(values_average3,properties3);
		table_playerInfo3_average = new JTable(tableModel_playerInfo3_average){
			public Component prepareRenderer(TableCellRenderer renderer,int row,int column){
				Component c=super.prepareRenderer(renderer,row,column);
				if(c instanceof JComponent){
					((JComponent)c).setOpaque(false);
				}
				return c;
			}
		};
		table_playerInfo3_average.setOpaque(false);
		table_playerInfo3_average.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table_playerInfo3_average.setForeground(Color.WHITE);
		table_playerInfo3_average.setSelectionForeground(Color.CYAN);

		table_playerInfo3_average.getTableHeader().setBackground(background);
		table_playerInfo3_average.getTableHeader().setForeground(Color.WHITE);
		table_playerInfo3_average.getTableHeader().addMouseListener (new MouseAdapter() { 
            public void mouseReleased (MouseEvent e) {  
                if (! e.isShiftDown())  
                	table_playerInfo3_average.clearSelection();
                //获取点击的列索引  
                int pick = table_playerInfo3_average.getTableHeader().columnAtPoint(e.getPoint());  
                //设置选择模型 
                table_playerInfo3_average.addColumnSelectionInterval (pick, pick);
            }  
        });
		
		scrollPane_playerInfo3_average = new JScrollPane(table_playerInfo3_average);
		scrollPane_playerInfo3_average.setOpaque(false);
		scrollPane_playerInfo3_average.getViewport().setOpaque(false);
		scrollPane_playerInfo3_average.setBounds(200, 350, 550, 155);
		
		//球员数据表格4
		tableModel_playerInfo4_average = new DefaultTableModel(values_average4,properties4);
		table_playerInfo4_average = new JTable(tableModel_playerInfo4_average){
			public Component prepareRenderer(TableCellRenderer renderer,int row,int column){
				Component c=super.prepareRenderer(renderer,row,column);
				if(c instanceof JComponent){
					((JComponent)c).setOpaque(false);
				}
				return c;
			}
		};
		table_playerInfo4_average.setOpaque(false);
		table_playerInfo4_average.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table_playerInfo4_average.setForeground(Color.WHITE);
		table_playerInfo4_average.setSelectionForeground(Color.CYAN);

		table_playerInfo4_average.getTableHeader().setBackground(background);
		table_playerInfo4_average.getTableHeader().setForeground(Color.WHITE);
		table_playerInfo4_average.getTableHeader().addMouseListener (new MouseAdapter() { 
            public void mouseReleased (MouseEvent e) {  
                if (! e.isShiftDown())  
                	table_playerInfo1_average.clearSelection();
                //获取点击的列索引  
                int pick = table_playerInfo4_average.getTableHeader().columnAtPoint(e.getPoint());  
                //设置选择模型 
                table_playerInfo4_average.addColumnSelectionInterval (pick, pick);
            }  
        });
		
		scrollPane_playerInfo4_average = new JScrollPane(table_playerInfo4_average);
		scrollPane_playerInfo4_average.setOpaque(false);
		scrollPane_playerInfo4_average.getViewport().setOpaque(false);
		scrollPane_playerInfo4_average.setBounds(200, 350, 450, 155);
	}
	
	public void displayTables(int number){
		label_data_arrow.setLocation(138, 76*number+130);
		switch(currentTable){
		case 1:
			this.remove(scrollPane_playerInfo1_total);
			this.remove(scrollPane_playerInfo1_average);
			break;
		case 2:
			this.remove(scrollPane_playerInfo2_total);
			this.remove(scrollPane_playerInfo2_average);
			break;
		case 3:
			this.remove(scrollPane_playerInfo3_total);
			this.remove(scrollPane_playerInfo3_average);
			break;
		case 4:
			this.remove(scrollPane_playerInfo4_total);
			this.remove(scrollPane_playerInfo4_average);
			break;
		}
		this.repaint();
		switch(number){
		case 1:
			this.add(scrollPane_playerInfo1_total,0);
			this.add(scrollPane_playerInfo1_average,0);
			break;
		case 2:
			this.add(scrollPane_playerInfo2_total,0);
			this.add(scrollPane_playerInfo2_average,0);
			break;
		case 3:
			this.add(scrollPane_playerInfo3_total,0);
			this.add(scrollPane_playerInfo3_average,0);
			break;
		case 4:
			this.add(scrollPane_playerInfo4_total,0);
			this.add(scrollPane_playerInfo4_average,0);
			break;
		}
		currentTable = number;
	}
}
