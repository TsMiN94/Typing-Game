import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class BTTselectNote extends JDialog {
	private BTTFrame bttframe;
	private BTTMenuPanel menupanel;
	private BTTnotePad note;
	private BTTJButton bt1, bt2, bt3, bt4;
	private MyAction li;
	private String wordSelect;
	private ImageIcon icon;
	
	public BTTselectNote(BTTFrame bttframe, BTTMenuPanel menupanel) {
		super(bttframe, "SelectNote", true);
		setLayout(null);
		icon = new ImageIcon("Img/notePad.png");
		
		
		this.wordSelect = "text/words.txt";
		this.bttframe =bttframe;
		this.menupanel = menupanel;
		li = new MyAction();
		JLabel label = new JLabel("단어장을 선택해주세요.");
		bt1 = new BTTJButton("Img/kor.png", 200, 60, 54, 125);
		bt2 = new BTTJButton("Img/eng.png", 200, 60, 54, 225);
		bt3 = new BTTJButton("Img/wrong.png", 200, 60, 54, 325);
		bt4 = new BTTJButton("Img/out.png", 200, 60, 54, 425);

		bt1.setRolloverIcon(new ImageIcon("Img/kor2.png"));
		bt1.setBorderPainted(false);
		bt1.setContentAreaFilled(false);
		bt1.setFocusPainted(false);
		bt1.addActionListener(li);
		bt2.setRolloverIcon(new ImageIcon("Img/eng2.png"));
		bt2.setBorderPainted(false);
		bt2.setContentAreaFilled(false);
		bt2.setFocusPainted(false);
		bt2.addActionListener(li);
		bt3.setRolloverIcon(new ImageIcon("Img/wrong2.png"));
		bt3.setBorderPainted(false);
		bt3.setContentAreaFilled(false);
		bt3.setFocusPainted(false);
		bt3.addActionListener(li);
		bt4.setRolloverIcon(new ImageIcon("Img/out2.png"));
		bt4.setBorderPainted(false);
		bt4.setContentAreaFilled(false);
		bt4.setFocusPainted(false);
		bt4.addActionListener(li);

		label.setBounds(28, 20, 300, 100);
		label.setFont(new Font("Gothic", Font.BOLD, 25));
		label.setForeground(Color.white);
		DialogPanel dp = new DialogPanel(icon);
		dp.setLayout(null);
		dp.setBounds(-13,-5,300,550);
		
		dp.add(label);
		dp.add(bt1);
		dp.add(bt2);
		dp.add(bt3);
		dp.add(bt4);
		add(dp);
	}
	@Override
	public void paintComponents(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponents(g);
		g.drawImage(icon.getImage(), 0, 0, this);
	}
	
	public String getWordSelect(){
		return wordSelect;
	}
	
	class MyAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == bt1) {
				note = new BTTnotePad(bttframe, menupanel,1);
				note.setSize(400, 350);
				note.setLocationRelativeTo(bttframe);
				note.setVisible(true);
				wordSelect = note.getwordSelect();
			}
			else if (e.getSource() == bt2){
				note = new BTTnotePad(bttframe, menupanel,2);
				note.setSize(400, 350);
				note.setLocationRelativeTo(bttframe);
				note.setVisible(true);
				wordSelect = note.getwordSelect();
			}
			else if(e.getSource() == bt3){
				note = new BTTnotePad(bttframe, menupanel,3);
				note.setSize(400, 350);
				note.setLocationRelativeTo(bttframe);
				note.setVisible(true);
				wordSelect = note.getwordSelect();
			}
			else if(e.getSource() == bt4) {
				System.out.println("나가기!");
				setVisible(false);
			}
			else{
				System.out.println("여기서 오류!");
			}
		}
	}
}
