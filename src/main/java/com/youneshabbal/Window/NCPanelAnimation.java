package com.youneshabbal.Window;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class NCPanelAnimation extends JPanel implements ActionListener {
	private static final int LINE_WIDTH = 13;
	public Timer timer;
	private int lineX;
	private int lineY1;
	private int lineY2;
	private int deltaY = 1;
	private int heightCenter;
	private int index;
	private Color animationColor;
	private int HEIGHT;
	private int WIDTH;
	public NCPanelAnimation() {
	}
	public NCPanelAnimation(JPanel panel, int index, Color animationColor) {
		this.animationColor = animationColor;
		this.index = index;
		HEIGHT = panel.getHeight();
		WIDTH = panel.getWidth();
		setSize(WIDTH, HEIGHT);
		lineX = WIDTH / 2;
		heightCenter = HEIGHT / 2;
		lineY2 = heightCenter + index;
		lineY1 = heightCenter - index;
		timer = new Timer(10, this);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		g2D.setPaint(animationColor);
		BasicStroke stroke = new BasicStroke(LINE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
		g2D.setStroke(stroke);
		g2D.drawLine(lineX, lineY1 + 20, lineX, lineY2 - 20);
		g2D.drawLine(lineX - 20, lineY1 + 50, lineX - 20, lineY2 - 50);
		g2D.drawLine(lineX + 20, lineY1 + 50, lineX + 20, lineY2 - 50);
		g2D.drawLine(lineX - 40, lineY1 + 30, lineX - 40, lineY2 - 30);
		g2D.drawLine(lineX + 40, lineY1 + 30, lineX + 40, lineY2 - 30);
		g2D.drawLine(lineX - 60, lineY1 + 40, lineX - 60, lineY2 - 40);
		g2D.drawLine(lineX + 60, lineY1 + 40, lineX + 60, lineY2 - 40);
		g2D.drawLine(lineX - 80, lineY1 + 60, lineX - 80, lineY2 - 60);
		g2D.drawLine(lineX + 80, lineY1 + 60, lineX + 80, lineY2 - 60);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (lineY1 < index || lineY1 > heightCenter - 60) {
			deltaY *= -1;
		}
		lineY1 += deltaY;
		lineY2 -= deltaY;
		repaint();
	}
}
