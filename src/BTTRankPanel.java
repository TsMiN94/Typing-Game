import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;
import java.io.*;

public class BTTRankPanel extends JPanel {
   private ImageIcon icon;
   private ImageIcon back;
   private BTTMenuPanel bttmenupanel;
   private BTTRankPanel bttrankpanel=this;
   private Rank rank;
   private Vector<BTTJLabel> labelv = new Vector<BTTJLabel>();
   private int width;
   private int height;
   private int labelY=100;
   private int labelX=200;
   private String labeltxt;
   private Vector<String> idv;
   private Vector<String> scorev;
   
   public BTTRankPanel(int width, int height, int x, int y, BTTMenuPanel bttmenupanel) {
      this.bttmenupanel = bttmenupanel;
      setLayout(null);
      setSize(width, height);
      this.width = width;
      this.height = height;
      icon = new ImageIcon("Img/rankPanel.png");
      Font font = new Font("랭크바", Font.BOLD, 50);
      BTTJLabel ranklabel = new BTTJLabel("", font,width-200, 100,labelX,labelY);
      BTTJButton backbtn = new BTTJButton("Img/Back.png",200,100, width - 250, height -200);
      backbtn.setBorderPainted(false);
      backbtn.setContentAreaFilled(false);
      backbtn.setFocusPainted(false);
      add(backbtn);
      labeltxt = String.format("RANK%11s","");//15
      labeltxt += String.format("ID%15s", "");//17      
      labeltxt += String.format("SCORE%15s", "");//20
      ranklabel.setText(labeltxt);
      rank = new Rank();
      rank.setBTTRankPanel(bttrankpanel);
      rank.setLabelVector(labelv);
      rank.readFile("text/id.txt","text/score.txt");
      idv = rank.getIdv();
      scorev = rank.getScorev();
      addv();
      labelv.add(ranklabel);
      addLabel();
      backbtn.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
             bttmenupanel.visibleButtons();
             bttmenupanel.remove(getBTTRankPanel());
             bttmenupanel.repaint();
          }
       });
      setBackground(Color.YELLOW);
      setLocation(x, y);
      setVisible(true);
   }
   public void addv() {
	   for(int i=0;i<5;i++) {
		   labelv.add(createLabel(i+1, idv.get(i), scorev.get(i)));
	   }
   }
   public void addLabel() {
      for(int i=0;i<labelv.size();i++) {
         this.add(labelv.get(i));
      }
   }
   public BTTRankPanel getBTTRankPanel() {
      return this;
   }
   public BTTJLabel createLabel(int num,String id, String score) {
      Font labelfont = new Font("라벨폰트", Font.BOLD, 30);
      labelY +=100;
      String rank;
      rank = Integer.toString(num);
      int length = id.length();
      labeltxt = String.format("%15s","");
      labeltxt += String.format(rank+"%18s","");
      labeltxt += String.format(id+"%"+(35-(length*4))+"s","");
      labeltxt += score;
      BTTJLabel newLabel = new BTTJLabel(labeltxt, labelfont ,width-200, 100, labelX,labelY);
      return newLabel;
   }
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(),this);
   }
}