import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class DialogPanel extends JPanel{
	private ImageIcon icon;
	public DialogPanel(ImageIcon icon){
		this.icon = icon;
	}
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(icon.getImage(), 0, 0, this);
	}
}
