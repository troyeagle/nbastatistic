package njuse.ffff.ui.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

@SuppressWarnings("serial")
public class MyInfoTable extends JTable{
	
	private Color background = new Color(99,43,142);
	private MyInfoTable infoTable;
	
	public MyInfoTable(TableModel dm) {
		super(dm, null, null);
		infoTable = this;
		
		JTableHeader jtableheader = getTableHeader();
		jtableheader.addMouseListener(new MouseAdapter() {
			public void mouseReleased (MouseEvent e) {  
                if (! e.isShiftDown())  
                	infoTable.clearSelection();
                //获取点击的列索引  
                int pick = infoTable.getTableHeader().columnAtPoint(e.getPoint());  
                //设置选择模型 
                infoTable.addColumnSelectionInterval (pick, pick);
	        }  
		});

		this.setOpaque(false);
//		this.setBackground(background);
		this.setForeground(Color.WHITE);
		this.setSelectionForeground(Color.CYAN);
		//设置表头自动长度
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.setBorder(new EmptyBorder(0,0,0,0));

		jtableheader.setOpaque(true);
		jtableheader.setBackground(background);
		jtableheader.setForeground(Color.WHITE);
		this.repaint();
	}
	
	/**
	 * 设置单元格为透明
	 */
	public Component prepareRenderer(TableCellRenderer renderer,int row,int column){
		Component c=super.prepareRenderer(renderer,row,column);
		if(c instanceof JComponent){
			((JComponent)c).setOpaque(false);
		}
		return c;
	}

}
