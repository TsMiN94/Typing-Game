import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Time;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class BTTGameOverPanel extends JPanel {
	private ImageIcon icon;
	private int GameScore, succeedCnt, insertCnt;
	private BTTFrame bttframe;
	private Vector<String> v;
	private Vector<String> idv;
	private Vector<String> scorev;
	private BufferedWriter bw;
	private Rank rank;
	private String id;
	private int cnt;
	public BTTGameOverPanel(int GameScore, int succeedCnt, int insertCnt, BTTFrame bttframe, Vector<String> v) {
		this.v = v;
		this.bttframe = bttframe;
		this.GameScore = GameScore;
		this.succeedCnt = succeedCnt;
		this.insertCnt = insertCnt;
		setLayout(null);
		icon = new ImageIcon("Img/gameover.png");
		rank = new Rank();
		rank.readFile("text/id.txt", "text/score.txt");
		idv = rank.getIdv();
		scorev = rank.getScorev();
		System.out.println("만들어짐");
		ImageIcon icon1 = new ImageIcon("Img/menu2.png");
		ImageIcon icon2 = new ImageIcon("Img/exit2.png");
		BTTJButton menubt = new BTTJButton("Img/menu.png", 200, 100, 550, 800);
		menubt.setRolloverIcon(icon1);
		menubt.setBorderPainted(false);
		menubt.setContentAreaFilled(false);
		menubt.setFocusPainted(false);
		BTTJButton exitbt = new BTTJButton("Img/exit.png", 200, 100, 820, 800);
		exitbt.setRolloverIcon(icon2);
		exitbt.setBorderPainted(false);
		exitbt.setContentAreaFilled(false);
		exitbt.setFocusPainted(false);
		JTextField ta = new JTextField(); // ID
		ta.setBounds(350, 650,800, 100);
		ta.setFont(new Font("Gothic", Font.BOLD, 70));
		cnt = 1;
		for (int i = 0; i < scorev.size(); i++) {
			if (Integer.parseInt(scorev.get(i)) > GameScore)
				cnt++; // 등수
		}
		if (cnt > 5) {
			ta.setText("순위권진입 실패!!");
			ta.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ta.setText("아이디 입력 실패!");
				}
			});
		} else {
			ta.setText("순위권에 진입! id 입력!");
			ta.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					id = ta.getText();// 입력한 id 넘겨주기
					System.out.println(id);
					if (id.length() > 10) {
						ta.setText("아이디를 더 짧게 하십시오.");
						return;
					}
					try {
						Thread.sleep(1000);
						cnt -= 1;
						idv.add(cnt, id);
						scorev.add(cnt, Integer.toString(GameScore));
						rank.writeFile("text/id.txt", "text/score.txt");
						rank.setIdv(idv);
						rank.setScorev(scorev);
						rank.closeWriteFiles();
						ta.setText("아이디 입력 완료!");
					} catch (Exception w) {
					}
				}
			});
		}
		menubt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("메뉴패널 새로 붙인다");
				bttframe.setContentPane(new BTTMenuPanel(bttframe));
				bttframe.revalidate();
				bttframe.repaint();
			}
		});
		exitbt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		JLabel score = new JLabel("점수 : " + Integer.toString(GameScore));
		score.setFont(new Font("Gothic", Font.ITALIC, 40));
		score.setForeground(Color.WHITE);
		double p;
		if (succeedCnt != 0 && insertCnt != 0)
			p = ((100 * succeedCnt) / insertCnt);
		else
			p = 0.0;
		JLabel percent = new JLabel("정답률 : " + Double.toString(p) + "%");
		percent.setFont(new Font("Gothic", Font.ITALIC, 40));
		percent.setForeground(Color.WHITE);
		try {
			bw = new BufferedWriter(new FileWriter("Practice.txt", true));
			for (int i = 0; i < v.size(); i++) {
				String extraWord = v.get(i);
				bw.write(extraWord);
				bw.newLine();
			}
			bw.close();
		} catch (Exception e) {
			System.out.println("경로가 이상합니다.");
			return;
		}
		score.setOpaque(true);
		score.setBackground(new Color(255, 0, 0, 0));
		percent.setOpaque(true);
		percent.setBackground(new Color(255, 0, 0, 0));
		score.setBounds(650, 450, 300, 100);
		percent.setBounds(650, 500, 300, 100);
		ta.setFocusable(true);
		ta.requestFocus();
		add(ta);
		add(menubt);
		add(exitbt);
		add(score);
		add(percent);
		setSize(1500, 1000);
		setVisible(true);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(icon.getImage(), 0, 0, 1500, 1000, this);
	}
}