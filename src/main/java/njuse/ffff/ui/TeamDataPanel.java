package njuse.ffff.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;

import njuse.ffff.presenter.teamController.TeamInfoController;
import njuse.ffff.presenterService.teamService.TeamInfoService;
import njuse.ffff.ui.table.MyInfoTable;

@SuppressWarnings("serial")
public class TeamDataPanel extends JPanel{
	private final int teamDataPanel_width = 1100;
	private final int teamDataPanel_height = 700;
	
//	private ControllerService uiController;
	private TeamInfoService teamInfoController;
	
	private Color background = new Color(99,43,142);
	private Color blue_light = new Color(46,117,182);
	private Color blue_light_changed = new Color(93,169,238);
	private String img_label_search_URL = "picture/transfer/label_search_small.jpg";
	private String img_label_search_changed_URL = "picture/transfer/label_search_changed_small.jpg";
	private String img_data_arrow_URL = "picture/arrow/data_arrow.jpg";
	
	private JTextField text_searchInfo;
	private JLabel label_search;
	private JLabel label_team_data1;
	private JLabel label_team_data2;
	private JLabel label_team_data3;
	private JLabel label_team_return;
	private JLabel label_data_arrow;
	private JLabel label_team_name;
	
	private JLabel label_data_total;
	private JLabel label_data_average;
	private JLabel label_data_ratio;
	
	private int currentTable = 0;//当前显示的表格编号
	
	private DefaultTableModel tableModel_teamInfo1_total;
	private MyInfoTable table_teamInfo1_total;
	private JScrollPane scrollPane_teamInfo1_total;
	
	private DefaultTableModel tableModel_teamInfo1_average;
	private MyInfoTable table_teamInfo1_average;
	private JScrollPane scrollPane_teamInfo1_average;
	
	private DefaultTableModel tableModel_teamInfo2_total;
	private MyInfoTable table_teamInfo2_total;
	private JScrollPane scrollPane_teamInfo2_total;
	
	private DefaultTableModel tableModel_teamInfo2_average;
	private MyInfoTable table_teamInfo2_average;
	private JScrollPane scrollPane_teamInfo2_average;
	
	private DefaultTableModel tableModel_teamInfo3_1;
	private MyInfoTable table_teamInfo3_1;
	private JScrollPane scrollPane_teamInfo3_1;

	private DefaultTableModel tableModel_teamInfo3_2;
	private MyInfoTable table_teamInfo3_2;
	private JScrollPane scrollPane_teamInfo3_2;
	
	private DefaultTableModel tableModel_teamInfo1_ratio;
	private MyInfoTable table_teamInfo1_ratio;
	private JScrollPane scrollPane_teamInfo1_ratio;

	private DefaultTableModel tableModel_teamInfo2_ratio;
	private MyInfoTable table_teamInfo2_ratio;
	private JScrollPane scrollPane_teamInfo2_ratio;
	
	public TeamDataPanel(){
		setOpaque(false);
		this.setSize(teamDataPanel_width, teamDataPanel_height);
		this.setBackground(background);
		this.setVisible(true);
		
//		uiController = UIController.getInstance();
		teamInfoController = TeamInfoController.getInstance();
		
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
        label_team_data1 = new JLabel(" 投篮数据");
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
        label_team_data2 = new JLabel(" 各项数据");
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
        label_team_data3 = new JLabel(" 其他数据");
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
//				uiController.changeToTeamProfilePanel();
				teamInfoController.changeToTeamProfilePanel();
			}
		});
        
        //数据显示指向箭头
        label_data_arrow = new JLabel();
        label_data_arrow.setOpaque(true);
        label_data_arrow.setSize(23, 46);
        ImageIcon icon_data_arrow = new ImageIcon(img_data_arrow_URL);
        label_data_arrow.setIcon(icon_data_arrow);
        
        //表格显示数据
        label_data_total = new JLabel("总数据");
        label_data_total.setOpaque(true);
        label_data_total.setBackground(background);
        label_data_total.setForeground(Color.white);
        label_data_total.setFont(new FontUIResource("DialogInput", Font.BOLD, 15));
        label_data_total.setBounds(260, 180, 50, 18);
        
        label_data_average = new JLabel("平均数据");
        label_data_average.setOpaque(true);
        label_data_average.setBackground(background);
        label_data_average.setForeground(Color.white);
        label_data_average.setFont(new FontUIResource("DialogInput", Font.BOLD, 15));
        label_data_average.setBounds(260, 320, 70, 18);
        
        label_data_ratio = new JLabel("数据率");
        label_data_ratio.setOpaque(true);
        label_data_ratio.setBackground(background);
        label_data_ratio.setForeground(Color.white);
        label_data_ratio.setFont(new FontUIResource("DialogInput", Font.BOLD, 15));
        label_data_ratio.setBounds(260, 460, 50, 18);
		
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
		//球队数据表格1
		tableModel_teamInfo1_total = new DefaultTableModel(values_total1,properties1);
		table_teamInfo1_total = new MyInfoTable(tableModel_teamInfo1_total);
		
		scrollPane_teamInfo1_total = new JScrollPane(table_teamInfo1_total);
		scrollPane_teamInfo1_total.setOpaque(false);
		scrollPane_teamInfo1_total.getViewport().setOpaque(false);
		int width1 = 3+table_teamInfo1_total.getColumnModel().getColumn(0).getWidth()*table_teamInfo1_total.getColumnCount();
		int height1 = 20*(1+values_total1.length);
		scrollPane_teamInfo1_total.setBounds(240, 200, width1, height1);
		
		//球队数据表格2
		tableModel_teamInfo2_total = new DefaultTableModel(values_total2,properties2);
		table_teamInfo2_total = new MyInfoTable(tableModel_teamInfo2_total);
		
		scrollPane_teamInfo2_total = new JScrollPane(table_teamInfo2_total);
		scrollPane_teamInfo2_total.setOpaque(false);
		scrollPane_teamInfo2_total.getViewport().setOpaque(false);
		int width2 = 3+table_teamInfo2_total.getColumnModel().getColumn(0).getWidth()*table_teamInfo2_total.getColumnCount();
		int height2 = 20*(1+values_total2.length);
		scrollPane_teamInfo2_total.setBounds(240, 200, width2, height2);
		
		//球队数据表格3
		tableModel_teamInfo3_1 = new DefaultTableModel(values3_1,properties3);
		table_teamInfo3_1 = new MyInfoTable(tableModel_teamInfo3_1);
		
		scrollPane_teamInfo3_1 = new JScrollPane(table_teamInfo3_1);
		scrollPane_teamInfo3_1.setOpaque(false);
		scrollPane_teamInfo3_1.getViewport().setOpaque(false);
		int width3 = 3+table_teamInfo3_1.getColumnModel().getColumn(0).getWidth()*table_teamInfo3_1.getColumnCount();
		int height3 = 20*(1+values3_1.length);
		scrollPane_teamInfo3_1.setBounds(240, 200, width3, height3);
		
		
		//球队数据表格1
		tableModel_teamInfo1_average = new DefaultTableModel(values_average1,properties1);
		table_teamInfo1_average = new MyInfoTable(tableModel_teamInfo1_average);
		
		scrollPane_teamInfo1_average = new JScrollPane(table_teamInfo1_average);
		scrollPane_teamInfo1_average.setOpaque(false);
		scrollPane_teamInfo1_average.getViewport().setOpaque(false);
		int width4 = 3+table_teamInfo1_average.getColumnModel().getColumn(0).getWidth()*table_teamInfo1_average.getColumnCount();
		int height4 = 20*(1+values_average1.length);
		scrollPane_teamInfo1_average.setBounds(240, 340, width4, height4);
		
		//球队数据表格2
		tableModel_teamInfo2_average = new DefaultTableModel(values_average2,properties2);
		table_teamInfo2_average = new MyInfoTable(tableModel_teamInfo2_average);
		
		scrollPane_teamInfo2_average = new JScrollPane(table_teamInfo2_average);
		scrollPane_teamInfo2_average.setOpaque(false);
		scrollPane_teamInfo2_average.getViewport().setOpaque(false);
		int width5 = 3+table_teamInfo2_average.getColumnModel().getColumn(0).getWidth()*table_teamInfo2_average.getColumnCount();
		int height5 = 20*(1+values_average2.length);
		scrollPane_teamInfo2_average.setBounds(240, 340, width5, height5);
		
		//球队数据表格3
		tableModel_teamInfo3_2 = new DefaultTableModel(values3_2,properties3_2);
		table_teamInfo3_2 = new MyInfoTable(tableModel_teamInfo3_2);
		
		scrollPane_teamInfo3_2 = new JScrollPane(table_teamInfo3_2);
		scrollPane_teamInfo3_2.setOpaque(false);
		scrollPane_teamInfo3_2.getViewport().setOpaque(false);
		int width6 = 3+table_teamInfo3_2.getColumnModel().getColumn(0).getWidth()*table_teamInfo3_2.getColumnCount();
		int height6 = 20*(1+values3_2.length);
		scrollPane_teamInfo3_2.setBounds(240, 340, width6, height6);
		
		//球队数据表格1_2
		tableModel_teamInfo1_ratio = new DefaultTableModel(values_ratio1,properties1_2);
		table_teamInfo1_ratio = new MyInfoTable(tableModel_teamInfo1_ratio);
		
		scrollPane_teamInfo1_ratio = new JScrollPane(table_teamInfo1_ratio);
		scrollPane_teamInfo1_ratio.setOpaque(false);
		scrollPane_teamInfo1_ratio.getViewport().setOpaque(false);
		int width7 = 3+table_teamInfo1_ratio.getColumnModel().getColumn(0).getWidth()*table_teamInfo1_ratio.getColumnCount();
		int height7 = 20*(1+values_ratio1.length);
		scrollPane_teamInfo1_ratio.setBounds(240, 480, width7, height7);
				
		//球队数据表格2_2
		tableModel_teamInfo2_ratio = new DefaultTableModel(values_ratio2,properties2_2);
		table_teamInfo2_ratio = new MyInfoTable(tableModel_teamInfo2_ratio);
		
		scrollPane_teamInfo2_ratio = new JScrollPane(table_teamInfo2_ratio);
		scrollPane_teamInfo2_ratio.setOpaque(false);
		scrollPane_teamInfo2_ratio.getViewport().setOpaque(false);
		int width8 = 3+table_teamInfo2_ratio.getColumnModel().getColumn(0).getWidth()*table_teamInfo2_ratio.getColumnCount();
		int height8 = 20*(1+values_ratio2.length);
		scrollPane_teamInfo2_ratio.setBounds(240, 480, width8, height8);
	}
	
	public void displayTables(int number){
		label_data_arrow.setLocation(138, 76*number+130);
		switch(currentTable){
		case 1:
			this.remove(scrollPane_teamInfo1_total);
			this.remove(scrollPane_teamInfo1_average);
			this.remove(scrollPane_teamInfo1_ratio);
			this.remove(label_data_total);
			this.remove(label_data_average);
			this.remove(label_data_ratio);
			break;
		case 2:
			this.remove(scrollPane_teamInfo2_total);
			this.remove(scrollPane_teamInfo2_average);
			this.remove(scrollPane_teamInfo2_ratio);
			this.remove(label_data_total);
			this.remove(label_data_average);
			this.remove(label_data_ratio);
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
			this.add(label_data_total,0);
			this.add(label_data_average,0);
			this.add(label_data_ratio,0);
			break;
		case 2:
			this.add(scrollPane_teamInfo2_total,0);
			this.add(scrollPane_teamInfo2_average,0);
			this.add(scrollPane_teamInfo2_ratio,0);
			this.add(label_data_total,0);
			this.add(label_data_average,0);
			this.add(label_data_ratio,0);
			break;
		case 3:
			this.add(scrollPane_teamInfo3_1,0);
			this.add(scrollPane_teamInfo3_2,0);
			break;
		}
		currentTable = number;
	}
}
