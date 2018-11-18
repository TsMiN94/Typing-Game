import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ImgLabel extends JLabel{
	private ImageIcon icon ;
	public ImgLabel(String img)
	{
		setLayout(null);
		icon =new ImageIcon (img);
	}
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
	}
}
