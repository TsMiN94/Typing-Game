import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

public class BTTJButton extends JButton{
	public BTTJButton(String buttonname,int sizeX,int sizeY,int locationX,int locationY) {
		super(new ImageIcon(buttonname));
		setSize(sizeX,sizeY);
		setLocation(locationX,locationY);
	}
}
