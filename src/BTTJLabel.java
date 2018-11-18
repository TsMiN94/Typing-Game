import java.awt.*;
import javax.swing.*;

public class BTTJLabel extends JLabel{
   public BTTJLabel(String labelname,Font font,int width,int height,int locationX,int locationY) {
      super(labelname);
      setSize(width,height);
      setLocation(locationX,locationY);
      setFont(font);
   }
}