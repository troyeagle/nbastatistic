package njuse.ffff.ui.ver1;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.FontUIResource;

@SuppressWarnings("serial")
public class TitlePanel extends JPanel{
	private final int titlePanel_width = 100;
	private final int titlePanel_height = 700;
	
	private Color background = new Color(169,199,249);
	private Color light_blue_changed = new Color(154,188,244);
	
	private JLabel label_exit;
	
	public TitlePanel(){
		this.setSize(titlePanel_width, titlePanel_height);
		this.setBackground(background);
		this.setVisible(true);
		
		label_exit = new JLabel("退出系统");
		label_exit.setOpaque(true);
		label_exit.setBackground(background);
		label_exit.setFont(new FontUIResource("DialogInput", Font.BOLD, 20));
		label_exit.setBounds(5, 540, 90, 40);
		label_exit.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {
				label_exit.setBackground(background);;
			}
			public void mouseEntered(MouseEvent arg0) {
				label_exit.setBackground(light_blue_changed);
			}
			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
			}
		});
		
		this.setLayout(null);
		this.add(label_exit);
	}
}
