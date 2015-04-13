package njuse.ffff.ui.component;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BasicButtonExListener extends MouseAdapter implements IButtonExListener {
	// TODO
	private ButtonEx button;

	private Color outColor = null;
	private Color inColor = new Color(255, 255, 255, 63);
	private Color outPressColor = new Color(255, 255, 255, 127);
	private Color inPressColor = new Color(255, 255, 255, 191);

	private int status = 0;	// 鼠标移入+1 鼠标按下+2 鼠标移出-1 鼠标松开-2
	
	private final int OUT = 0;
	private final int IN = 1;
	private final int OUT_PRESS = 2;
	private final int IN_PRESS = 3;

	public BasicButtonExListener(ButtonEx button) {
		this.button = button;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		status += 1;
		switch (status) {
		case IN:
			button.setBackgroundMaskColor(inColor);
			break;
		case IN_PRESS:
			button.setBackgroundMaskColor(inPressColor);
			break;
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		status -= 1;
		switch (status) {
		case OUT:
			button.setBackgroundMaskColor(outColor);
			break;
		case OUT_PRESS:
			button.setBackgroundMaskColor(outPressColor);
			break;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		status += 2;
		button.setBackgroundMaskColor(inPressColor);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		status -= 2;
		switch (status) {
		case OUT:
			button.setBackgroundMaskColor(outColor);
			break;
		case IN:
			button.setBackgroundMaskColor(inColor);
			break;
		}
	}
	
	public void setInColor(Color inColor) {
		this.inColor = inColor;
	}
	
	public void setOutColor(Color outColor) {
		this.outColor = outColor;
	}
	
	public void setInPressColor(Color inPressColor) {
		this.inPressColor = inPressColor;
	}
	
	public void setOutPressColor(Color outPressColor) {
		this.outPressColor = outPressColor;
	}
}
