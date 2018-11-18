import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class DownPanel extends JPanel {
	private JLabel label;
	private ImgLabel imglabel;
	private String word;
	private int item;
	public DownPanel(String word,int x,int item)
	{
		this.word = word;
		this.item= item;
		setLayout(null);
		setBounds(x + 50, 100, 180, 230); // 내려가는panel
		setBackground(new Color(255,0,0,0));
		imglabel = new ImgLabel("IMG/tong1.png"); //이미지를 붙인 레이블
		imglabel.setSize(180, 205);
		imglabel.setLocation(0,25);
		label = new JLabel(word); // 랜덤하게 단어받은 레이블
		label.setFont(new Font("Gothic", Font.ITALIC, 27));
		label.setForeground(Color.white);
		label.setSize(180,25);
		label.setHorizontalAlignment(JLabel.CENTER); // 위치center값으로 고정
		this.label = label;
		this.imglabel = imglabel;
		add(label);
		add(imglabel);
	}
	public int getitem(){
		return item;
	}
	
	public void setItem(int item){
		this.item = item;
	}
	public String getWord(){
		return word;
	}
	public ImgLabel getImgLabel()
	{
		return imglabel;
	}
}
