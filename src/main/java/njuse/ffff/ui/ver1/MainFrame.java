package njuse.ffff.ui.ver1;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainFrame extends JFrame{
	private final int mainFrame_width = 1100;
	private final int mainFrame_height = 700;
	private int mainFrame_x;
	private int mainFrame_y;
	
	@SuppressWarnings("unused")
	private boolean startDrag = false;
	private Point p = null;
	
	private JPanel panel_current = null;
	
	public MainFrame(){
		this.setSize(mainFrame_width, mainFrame_height);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		this.setUndecorated(true);
		this.setVisible(true);
		
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screen = toolkit.getScreenSize();
		mainFrame_x = (screen.width - mainFrame_width)/2;
		mainFrame_y = (screen.height - mainFrame_height)/2-16;
		this.setLocation(mainFrame_x, mainFrame_y);
		
		//鼠标拖动
		addMouseListener(new MouseAdapter() { 
			public void mousePressed(MouseEvent e) { 
				startDrag = true; 
				p = e.getPoint(); 
			} 

			public void mouseReleased(MouseEvent e) { 
				startDrag = false; 
			} 
		}); 
		
		addMouseMotionListener(new MouseMotionAdapter() { 
			public void mouseDragged(MouseEvent e) { 
				Point p1 = e.getPoint(); 
				Point p2 = getLocation(null); 
				p2.x += p1.x - p.x; 
				p2.y += p1.y - p.y; 
				setLocation(p2); 
			} 
		});
		
		//初始显示为搜索界面
		SearchPanel s_panel = new SearchPanel();
		s_panel.setLocation(0, 0);
		
		this.setLayout(null);
		this.add(s_panel);
		this.repaint();
		
		panel_current = s_panel;
	}
	
	/**
	 * 改变当前显示界面
	 * @param panel
	 */
	public void changePanel(JPanel panel){
		panel.setLocation(0, 0);
		this.remove(panel_current);
		this.add(panel);
		this.repaint();
		panel_current = panel;
	}

	public void switchToPanel(JPanel panel) {
		panel.setLocation(0, 0);
		this.remove(panel_current);
		this.add(panel);
		this.repaint();
		panel_current = panel;
	}
}
