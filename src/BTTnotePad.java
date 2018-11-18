
import javax.swing.*;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class BTTnotePad extends JDialog implements ActionListener {
	private JLabel label;
	private Word words;
	private String state,wordSelect;
	private Scanner sc;
	private BufferedWriter bw;
	private BTTMenuPanel menupanel;
	private BTTJButton bt1, bt2, bt3,bt4;
	private JScrollPane scp;
	private JList<String> wordList;
	private int noteSelect ;
	private ImageIcon icon ;
	private DialogPanel dp;
	public BTTnotePad(BTTFrame bttframe, BTTMenuPanel menupanel,int noteSelect) {
		super(bttframe, "NotePad", true);
		try {
			icon = new ImageIcon("Img/notePad2.png");
			dp = new DialogPanel(icon);
			this.noteSelect = noteSelect;
			this.menupanel = menupanel;
			setLayout(null);
			state = null;
			sc = new Scanner(System.in);
			
			if(noteSelect == 1)
				wordSelect = "text/words.txt";
			else if (noteSelect ==2)
				wordSelect = "text/English.txt";
			else
				wordSelect = "text/Practice.txt";
				
			words = new Word(wordSelect);
			wordList = new JList<String>(words.getvectorWord());
			scp = new JScrollPane(wordList);
			bt1 = new BTTJButton("Img/ExtraWord.png", 150, 60, 230, 25);
			bt2 = new BTTJButton("Img/DeleteWord.png", 150, 60, 230, 125);
			bt3 = new BTTJButton("Img/select.png", 150, 60, 230, 225);
			bt1.setRolloverIcon(new ImageIcon("Img/ExtraWord2.png"));
			bt2.setRolloverIcon(new ImageIcon("Img/DeleteWord2.png"));
			bt3.setRolloverIcon(new ImageIcon("Img/select2.png"));
			
			bt1.setBorderPainted(false);
			bt1.setContentAreaFilled(false);
			bt1.setFocusPainted(false);
			bt2.setBorderPainted(false);
			bt2.setContentAreaFilled(false);
			bt2.setFocusPainted(false);
			bt3.setBorderPainted(false);
			bt3.setContentAreaFilled(false);
			bt3.setFocusPainted(false);
			
			bt1.addActionListener(this);
			bt2.addActionListener(this);
			bt3.addActionListener(this);
			
			dp.setBounds(-13,-5,400,350);
			dp.setLayout(null);
			dp.add(bt1);
			dp.add(bt2);
			dp.add(bt3);
			add(dp);
			scp.setBounds(19, 13, 180, 290);
			dp.add(scp);
		} catch (Exception e) {
			return;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bt1) {
			String extraWord = JOptionPane.showInputDialog("추가 하고싶은 단어를 입력하세요 .");
			extraWordInsert(extraWord);
		} else if (e.getSource() == bt2) {
			String deleteWord = JOptionPane.showInputDialog("삭제 하고싶은 단어를 입력하세요 .");
			findDeleteWord(deleteWord);
		} else if (e.getSource() == bt3) {
			state = "exit";
			setVisible(false);
		}
		
		System.out.println("갱신한다");
		dp.remove(scp);
		dp.remove(wordList);
		Word words1 = new Word(wordSelect);
		wordList = new JList<String>(words1.getvectorWord());
		scp = new JScrollPane(wordList);
		scp.setBounds(19, 13, 180, 290);
		dp.add(scp);
		this.revalidate();
		repaint();
	}

	public String getwordSelect(){
		return wordSelect;
	}
	
	public void extraWordInsert(String extraWord) {
		try {
			bw = new BufferedWriter(new FileWriter(wordSelect, true));
			bw.write(extraWord);
			bw.newLine();
			bw.close();
		} catch (Exception e) {
			System.out.println("경로가 이상합니다.");
			return;
		}
	}

	public void findDeleteWord(String deleteWord) {
		Vector<String> v = words.getvectorWord();
		String temp;
		for (int i = 0; i < v.size(); i++)
			if (v.get(i).equals(deleteWord)) {
				v.remove(i);
				break;
			}
		try {
			bw = new BufferedWriter(new FileWriter(wordSelect));
			for (int i = 0; i < v.size(); i++) {
				temp = v.get(i);
				bw.write(temp);
				bw.newLine();
			}
			bw.flush();
			bw.close();
		} catch (Exception e) {
			return;
		}
	}

	public String getState() {
		return state;
	}

	class Word {
		private Vector<String> wordVector = new Vector<String>();
		private String s;

		public Word(String fileName) {
			try {
				FileReader fin = new FileReader(fileName);
				BufferedReader br = new BufferedReader(fin);
				while ((s = br.readLine()) != null) {
					wordVector.add(s); 
				}
				fin.close();
				br.close();
			} catch (IOException e) {
				System.out.println("파일 경로가 잘못되었습니다.");
				System.exit(0);
			}
		}

		private Vector<String> getvectorWord() {
			return wordVector;
		}

		public String getRandomWord() {
			int size = wordVector.size();
			int index = (int) (Math.random() * size);
			return wordVector.get(index);
		}
	}

}
