package njuse.ffff.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SelectPanel extends JPanel{
	private final int selectPanel_width = 410;
	private final int selectPanel_height = 150;
	
	Color grey = new Color(151,151,152);
	Color blue = new Color(170,200,228);
	Color blue_changed = new Color(77,159,235);
	
	ArrayList<String> item_selected = new ArrayList<String>();
	
	public SelectPanel(){
		this.setSize(selectPanel_width, selectPanel_height);
		this.setBackground(grey);
		
		JLabel label_background = new JLabel();
		label_background.setOpaque(true);
		label_background.setBackground(blue);
		label_background.setBounds(5, 5, 400, 140);
		label_background.setLayout(null);
		
		Item item11 = new Item("得分");
		item11.setLocation(5, 5);
		label_background.add(item11);
		
		Item item12 = new Item("篮板");
		item12.setLocation(85, 5);
		label_background.add(item12);
		
		Item item13 = new Item("助攻");
		item13.setLocation(165, 5);
		label_background.add(item13);
		
		Item item14 = new Item("得分/篮板/助攻");
		item14.setLocation(245, 5);
		label_background.add(item14);
		
		Item item21 = new Item("盖帽");
		item21.setLocation(5, 45);
		label_background.add(item21);
		
		Item item22 = new Item("抢断");
		item22.setLocation(85, 45);
		label_background.add(item22);
		
		Item item23 = new Item("犯规");
		item23.setLocation(165, 45);
		label_background.add(item23);
		
		Item item24 = new Item("失误");
		item24.setLocation(245, 45);
		label_background.add(item24);
		
		Item item25 = new Item("分钟");
		item25.setLocation(325, 45);
		label_background.add(item25);
		
		Item item31 = new Item("效率");
		item31.setLocation(5, 85);
		label_background.add(item31);
		
		Item item32 = new Item("投篮");
		item32.setLocation(85, 85);
		label_background.add(item32);
		
		Item item33 = new Item("三分");
		item33.setLocation(165, 85);
		label_background.add(item33);
		
		Item item34 = new Item("罚球");
		item34.setLocation(245, 85);
		label_background.add(item34);
		
		Item item35 = new Item("两双");
		item35.setLocation(325, 85);
		label_background.add(item35);
		
		this.setLayout(null);
		this.add(label_background);
		this.repaint();
	}
	
	public ArrayList<String> getBoxSelected(){
		return item_selected;
	}
	
	class Item extends JPanel{
		private int width = 30;
		private int height = 30;
		JCheckBox checkBox;
		JLabel label_content;
		String Content;
		
		public Item(String content){
			width = width+20*content.length();
			this.setSize(width, height);
			this.setBackground(blue);
			this.Content = content;
			
			checkBox = new JCheckBox();
			checkBox.setOpaque(true);
			checkBox.setBounds(8, 7, 18, 15);
			checkBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					// TODO 自动生成的方法存根
					if(checkBox.isSelected()){
						item_selected.add(Content);
					}
					else{
						item_selected.remove(Content);
					}
				}
			});
			
			label_content = new JLabel(content);
			label_content.setOpaque(true);
			label_content.setBackground(blue);
			label_content.setForeground(Color.BLACK);
			label_content.setBounds(30, 0, width-30, 30);
			
			this.setLayout(null);
			this.add(checkBox);
			this.add(label_content);
		}
	}
}