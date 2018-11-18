import javax.swing.*;
import java.awt.*;
public class LevelPanel extends JPanel {
	private ImageIcon icon;
	public LevelPanel(){
	 icon = new ImageIcon("Img/LevelUp.png");
	setBackground(new Color(255, 0, 0, 0)); 
	setSize(1500,1000);
	setVisible(true);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(icon.getImage(),280,300,1000,135,this);
		
	}
}
