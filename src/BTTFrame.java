import java.awt.*;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class BTTFrame extends JFrame{
   private Container bttcontantpane;
   private SoundClass backSound ;
   private boolean isLoop = true;
   public BTTFrame(){
      setTitle("Block The Tongs Game");
      bttcontantpane = getContentPane();
      backSound = new SoundClass("music/main.wav");
      setContentPane(new BTTMenuPanel(this));
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //setResizable(false);
      setSize(1500,1000);
      setVisible(true);
      backSound.play();
   }
   public static void main(String[] args) {
      new BTTFrame();
   }
}