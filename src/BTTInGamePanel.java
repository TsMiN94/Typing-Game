
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.logging.Level;
import java.io.*;

public class BTTInGamePanel extends JPanel {
	private ImageIcon icon, icon2;
	private JLabel label;
	private Word words;
	private JPanel ingamePanel = this;
	private Map<DownPanel, labelDownThread> h;
	private IngameText textarea;
	private int life, GameScore, succeedCnt, insertCnt, item, level;
	private boolean levelFlag = false;
	private BTTGameOverPanel over;
	private Vector<String> v; // �Է��������ߴ� �ܾ ����
	private MyThread th;
	private String userText, wordSelect;
	private LevelPanel lp;
	private Set<DownPanel> keys;
	private Iterator<DownPanel> it;
	private DownPanel temp, temp2;
	private BTTFrame bttframe;
	private Vector<JLabel> vl;
	private SoundClass sound;
	private ScoreCheckThread SCth;

	public BTTInGamePanel(BTTFrame bttframe, String wordSelect) {
		this.bttframe = bttframe;
		this.wordSelect = wordSelect;
		life = 5;

		icon2 = new ImageIcon("Img/life.png");
		h = Collections.synchronizedMap(new HashMap<DownPanel, labelDownThread>());

		v = new Vector<String>();
		this.setLayout(null);

		vl = new Vector<JLabel>();
		for (int i = 0; i < life; i++) {
			JLabel life = new JLabel(icon2);
			vl.add(life);
		}
		for (int i = 0; i < life; i++) {
			JLabel t = vl.get(i);
			t.setBounds(50 + (i * 70), 750, 120, 150);
			t.setOpaque(false);
			ingamePanel.add(t);
		}

		level = 1;
		GameScore = 0;
		succeedCnt = insertCnt = 0;
		words = new Word(wordSelect);
		icon = new ImageIcon("Img/InGame1.png");

		textarea = new IngameText();
		textarea.setHorizontalAlignment((int) JTextField.CENTER_ALIGNMENT);
		textarea.addActionListener(new ActionListener() {
			@Override
			public synchronized void actionPerformed(ActionEvent e) {
				insertCnt++; // �ܾ��Է¸��� ī��Ʈ ����
				Set<DownPanel> keys = h.keySet();
				Iterator<DownPanel> it = keys.iterator();
				Iterator<DownPanel> it1;
				userText = textarea.getText();
				while (it.hasNext()) { // HashMap�� �ִ� ��� �ܾ �˻�
					temp = it.next(); // ù��° key���� �ް�
					String str = temp.getWord(); // �׾ȿ� �ִ� �ܾ �ް�
					if (userText.equals(str)) { // HashMap �ȿ��ִ� �ܾ��� �������Է��� �ܾ�
						// ����
						// �ߺ����� ó�� ��ǥ�˻� �ڵ� �ʿ�0
						if (temp.getitem() == 5) {
							sound = new SoundClass("music/item.wav");
							sound.play2();
							// h.get(temp).interrupt(); // �ش緹�̺� �ٿ���� ����
							th.item = true;
							it1 = keys.iterator();
							while (it1.hasNext()) {
//								ImageIcon deleteIcon = new ImageIcon("Img/delete.gif");
//								JLabel deleteEffect = new JLabel(deleteIcon);
//								deleteEffect.setOpaque(true);
//								deleteEffect.setBounds(0, 0, 180, 230);
								GameScore += (100 + userText.length() * 10) + (1 * 50);
								temp2 = it1.next();
//								temp2.add(deleteEffect);
//								ingamePanel.revalidate();
								labelDownThread l = h.get(temp2); // flag���� 1�� �־�
								l.setDownFlag(1); // ���ͷ�Ʈ���� �����带����
								l.interrupt();
								h.remove(temp2);
								remove(temp2);
								System.out.println("���������� ���� ������ �Ͼ");
								it1 = keys.iterator();
							}
							synchronized (th) {
								th.notify();
							}
							System.out.println("�������������� �����Ϸ�");
							succeedCnt++;
							System.out.println(succeedCnt);
							textarea.setText(""); // textarea �ʱ�ȭ
							ingamePanel.revalidate();// ȭ�� �ٽ� �׸�
							return;
						} else {
							succeedCnt++;
							// System.out.println(succeedCnt);
//							ImageIcon deleteIcon = new ImageIcon("Img/delete.gif");
//							JLabel deleteEffect = new JLabel(deleteIcon);
//							deleteEffect.setOpaque(true);
//							deleteEffect.setBounds(0, 0, 180, 230);
//							temp2.add(deleteEffect);
//							ingamePanel.revalidate();
							labelDownThread l = h.get(temp); // flag���� 1�� �־�
							l.setDownFlag(1); // ���ͷ�Ʈ���� �����带����
							l.interrupt(); // �ش緹�̺� �ٿ���� ����
							h.remove(temp); // HashMap���� �ش緹�̺� ��ü ����
							remove(temp); // ingamePanel���� �ش� ���̺� ���ֱ�
							GameScore += (100 + userText.length() * 10) + (1 * 50); // ����up
							textarea.setText(""); // textarea �ʱ�ȭ
							repaint(); // ȭ�� �ٽ� �׸�
							break;
						}
					}
				}
				textarea.setText("");
			}
		});

		add(textarea);
		th = new MyThread(words);
		SCth = new ScoreCheckThread();
		setSize(1500, 1000);
		setVisible(true);
		SCth.start();
		th.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		String s = new String(Integer.toString(GameScore));
		g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Gothic", Font.BOLD, 50));
		g.drawString(s, 1200, 50);
		g.drawString("Level ", 670, 50);
		g.drawString(Integer.toString(level), 800, 50);
	}

	class ScoreCheckThread extends Thread {
		@Override
		public void run() {
			System.out.println("Level Check ������ ������ ... ");
			while (true) {
				try {
					sleep(1000);
					// System.out.println();
				} catch (InterruptedException e) {
				}
				if (GameScore >= (1000 * level * 2.5) && level != 5) {
					System.out.println("level Up ����! ");
					level += 1;
					if (level >= 5)
						level = 5;
					levelFlag = true;
					String str = "Level " + Integer.toString(level);
					JLabel level = new JLabel(str);
					level.setOpaque(false);
					level.setForeground(Color.white);
					level.setHorizontalAlignment(SwingConstants.CENTER);
					level.setVerticalAlignment(SwingConstants.CENTER);
					level.setFont(new Font("Gothic", Font.BOLD, 60));
					level.setBounds(500, 350, 500, 100);
					ingamePanel.add(level);
					System.out.println("level Up ���̺� ����");
					ingamePanel.repaint();
					try {
						int i = 0;
						Thread.sleep(1000);
						for (i = 3; i > 0; i--) {
							level.setText(Integer.toString(i));
							Thread.sleep(1000);
							ingamePanel.repaint();
						}
					} catch (InterruptedException e1) {
						return;
					}
					synchronized (th) {
						th.notify();
						System.out.println("th�����带 �������ϴ�.");
						Set<DownPanel> keys2 = h.keySet();
						Iterator<DownPanel> it2 = keys2.iterator();
						System.out.println("while ��");
						while (it2.hasNext()) {
							System.out.println("while ��");
							DownPanel panel = it2.next();
							labelDownThread laDownThread = h.get(panel);
							synchronized (laDownThread) {
								laDownThread.notify();
							}
						}
					}
					ingamePanel.remove(level);
					System.out.println("level ���̺� �����߽��ϴ�.");
					repaint();
				}
			}
		}
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
				System.out.println("���� ��ΰ� �߸��Ǿ����ϴ�.");
				System.exit(0);
			}
		}
		public String getRandomWord() {
			int size = wordVector.size();
			int index = (int) (Math.random() * size);
			return wordVector.get(index);
		}
	}
	class MyThread extends Thread {
		private Word threadWords;
		private DownPanel panel;
		private boolean item = false;
		private int overFlag = 0;

		public MyThread(Word words) {
			this.threadWords = words;
		}
		@Override
		public synchronized void run() {
			while (true) {
				try {
					if (item) {
						th.wait();
						item = false;
						Thread.sleep(700);
					}
					if (levelFlag) {
						th.interrupt();
						System.out.println("�ܾ��� �����带 �������׽��ϴ�.");
						Set<DownPanel> keys = h.keySet();
						Iterator<DownPanel> it = keys.iterator();
						while (it.hasNext()) {
							DownPanel panel = it.next();
							h.get(panel).interrupt();
							System.out.println("�ؽ����ִ� ��� �����带 ����Ű���ֽ��ϴ�.");
						}
						System.out.println("�ؽ����ִ� ��� �������⸦ �Ϸ��߽��ϴ�.");
						System.out.println("levelFlag�� �ٽ� false������ ����ϴ�.");
						levelFlag = false;
					}
					if (overFlag == 1) {
						this.interrupt();
					}
					Thread.sleep(2000);
					int x = ((int) (Math.random() * (ingamePanel.getWidth() - 200)));
					String randomStr = threadWords.getRandomWord();
					panel = new DownPanel(randomStr, x, 1); // �������� �г� ����
					ingamePanel.add(panel);
					labelDownThread ldt = new labelDownThread(panel); // ���̺�����
					// ���������
					System.out.println("DownLabel�� ������ ������ �����޽��ϴ�.");
					h.put(panel, ldt); // �ؽ��ʿ� ���� ������� �ش� �������带 ��´�.
					textarea.setFocusable(true);
					textarea.requestFocus();
					ldt.start();
					System.out.println("�����带 �����մϴ�");
				} catch (InterruptedException e) {
					try {
						if (overFlag == 0) {
							this.wait();
						} else if (overFlag == 1) {
							Set<DownPanel> key = h.keySet();
							Iterator<DownPanel> it2 = key.iterator();
							while (it2.hasNext()) { // �ؽ��� ����Ǿ� �ִ� �� ���� ȭ�鿡 ���̴� ���

								h.get(it2.next()).interrupt();
							}
							over = new BTTGameOverPanel(GameScore, succeedCnt, insertCnt, bttframe, v);
							bttframe.setContentPane(over);

							return;
						}
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}

				}

			}
		}
	}
	class labelDownThread extends Thread {
		private DownPanel panel;
		private ImgLabel label;
		private int downFlag = 0;
		public labelDownThread(DownPanel panel) {
			this.panel = panel;
		}
		public int getDownFlag() {
			return downFlag;
		}
		public void setDownFlag(int downFlag) {
			this.downFlag = downFlag;
		}
		@Override
		public void run() {
			int x = ((int) (Math.random() * 100) + 100); // �ӵ������ϱ�����
			int y = 10; // �������� ����
			item = 0;
			// System.out.println(x);
			while (true) {
				panel.setLocation(panel.getLocation().x, panel.getLocation().y + y);
				if (panel.getLocation().y >= 580) { // �ٽÿö󰡱� ���� ����
					y = y * -1;
					x = x - 100; // �ö󰥶� �ӵ�
					if (x < 10) // x���� �ʹ� ������ ���԰� ������ �ö󰡴� �ӵ��� ���������� ����
						x = 50;
					panel.remove(panel.getImgLabel()); // ���� �̹��� ����� ���ο� �̹����� ����
					item = (int) (Math.random() * 5 + 1); // item �� ���������� ����������
					panel.setItem(item);
					if (panel.getitem() != 5) // item �� ������ ������� �̹����� item�̹����κ���
						label = new ImgLabel("Img/tong3.png");
					else
						label = new ImgLabel("Img/lion.png"); // item
					label.setSize(180, 205);
					label.setLocation(0, 25);
					panel.add(label);
					repaint();
				}
				else if (panel.getLocation().y < 100 && y < 0) { // ������α���
					// �ö�������
					v.add(panel.getWord());// �Է¸��ߴ� �ܾ���� v �� ���δ�.
					labelDownThread a = h.get(panel);
					if (a != null) {
						a.setDownFlag(1);
						a.interrupt();
					}
					ingamePanel.remove(panel);
					h.remove(panel);
					System.out.println("������ ���δ�");
					life -= 1;
					if (vl.size() != 0) {
						JLabel t = vl.get(0);
						ingamePanel.remove(t);
						vl.remove(0);
					}
					if (life == 0) {
						Set<DownPanel> key = h.keySet();
						Iterator<DownPanel> it2 = key.iterator();
						while (it2.hasNext()) { // �ؽ��� ����Ǿ� �ִ� �� ���� ȭ�鿡 ���̴� ���
							labelDownThread l = h.get(it2.next());
							l.setDownFlag(1);
							l.interrupt();
						}
						th.overFlag = 1; // �����带 �����Ű������
					}
					this.interrupt();
				}

				try {
					x = x - ((level - 1) * 50);// level ��ŭ �ӵ� ����
					if (x < 0)
						x = 100;
					Thread.sleep(x); // ���Ӽӵ�
				} catch (InterruptedException e) {
					if (downFlag == 1) {
//						try {
//							Thread.sleep(500);
//						} catch (InterruptedException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
						System.out.println(this.currentThread().getName() + "�� �׾���.");
						return;
					} else if (downFlag == 0) {
						try {
							System.out.println("labelDownThread is Waiting!");
							synchronized (this) {
								this.wait();
							}
						} catch (InterruptedException e1) {
						}
					}

				} // while �ȿ��ִ� try catch
				ingamePanel.revalidate();
				repaint();
			} // while
		}// run
	}
}