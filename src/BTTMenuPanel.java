import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.*;


public class BTTMenuPanel extends JPanel{
   private ImageIcon icon,icon2;
   private BTTFrame bttframe;
   private BTTMenuPanel menupanel= this;
   private Point location;
   private BTTselectNote note;
   private Vector<BTTJButton> buttonv = new Vector<BTTJButton>();
   private String wordSelect;
   public BTTMenuPanel(BTTFrame bttframe){
      icon =new ImageIcon("Img/back1.gif");
      icon2 = new ImageIcon("Img/BackTest.png");
      setLayout(null);
      this.bttframe = bttframe;
      BTTJButton gameStartBt = new BTTJButton("Img/play.png",400,100,950,460);
      BTTJButton fileIoBt = new BTTJButton("Img/settings.png",400,100,950,585);
      BTTJButton rankBt = new BTTJButton("Img/scores.png",400,100,950,710);
      BTTRankPanel rankpanel = new BTTRankPanel(1200,800,150,100,this);  
      gameStartBt.setRolloverIcon(new ImageIcon("Img/play2.png"));
      gameStartBt.setBorderPainted(false);
      gameStartBt.setContentAreaFilled(false);
      gameStartBt.setFocusPainted(false);
      fileIoBt.setRolloverIcon(new ImageIcon("Img/settings2.png"));
      fileIoBt.setBorderPainted(false);
      fileIoBt.setContentAreaFilled(false);
      fileIoBt.setFocusPainted(false);
      rankBt.setRolloverIcon(new ImageIcon("Img/scores2.png"));
      rankBt.setBorderPainted(false);
      rankBt.setContentAreaFilled(false);
      rankBt.setFocusPainted(false);
      buttonv.add(gameStartBt);
      buttonv.add(fileIoBt);
      buttonv.add(rankBt);
      add(gameStartBt);
      add(fileIoBt);
      add(rankBt);
      this.wordSelect = "text/words.txt";
      gameStartBt.addActionListener(new MyActionListener());
      fileIoBt.addActionListener(new MyActionListener() {
         @Override
         public void actionPerformed(ActionEvent e){
            note = new BTTselectNote(bttframe, menupanel);
            note.setSize(300,550);
            note.setLocationRelativeTo(bttframe);
            note.setVisible(true);
            wordSelect = note.getWordSelect();
         }
      });
      rankBt.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            unvisibleButtons();
            addComponent(rankpanel);
            repaint();
         }
      });
   }
   public void addComponent(Component component) {
      add(component);
   }
   public void visibleButtons() {
      for(int i=0;i<buttonv.size();i++) {
         buttonv.get(i).setVisible(true);
      }
   }
   public void unvisibleButtons() {
      for(int i=0;i<buttonv.size();i++) {
         buttonv.get(i).setVisible(false);
      }   
   }
   public JPanel getBTTMenuPanel() {
      return this;
   }
   public void noteEnd(){
      note = null;
   }
   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
      g.drawImage(icon2.getImage(), -30,-30 ,1000,220,this);
   }
   class MyActionListener implements ActionListener {
         @Override
         public void actionPerformed(ActionEvent e) {
            bttframe.setContentPane(new BTTInGamePanel(bttframe,wordSelect));   
            bttframe.invalidate();
         }
   }
}