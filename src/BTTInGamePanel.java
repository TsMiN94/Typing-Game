
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
	private Vector<String> v; // 입력하지못했던 단어를 삽입
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
				insertCnt++; // 단어입력마다 카운트 증가
				Set<DownPanel> keys = h.keySet();
				Iterator<DownPanel> it = keys.iterator();
				Iterator<DownPanel> it1;
				userText = textarea.getText();
				while (it.hasNext()) { // HashMap에 있는 모든 단어를 검사
					temp = it.next(); // 첫번째 key값을 받고
					String str = temp.getWord(); // 그안에 있는 단어를 받고
					if (userText.equals(str)) { // HashMap 안에있는 단어들과 유저가입력한 단어
						// 대조
						// 중복예외 처리 좌표검사 코드 필요0
						if (temp.getitem() == 5) {
							sound = new SoundClass("music/item.wav");
							sound.play2();
							// h.get(temp).interrupt(); // 해당레이블 다운스레드 중지
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
								labelDownThread l = h.get(temp2); // flag값을 1로 주어
								l.setDownFlag(1); // 인터럽트에서 스레드를삭제
								l.interrupt();
								h.remove(temp2);
								remove(temp2);
								System.out.println("아이템으로 인한 삭제가 일어남");
								it1 = keys.iterator();
							}
							synchronized (th) {
								th.notify();
							}
							System.out.println("아이템으로인한 삭제완료");
							succeedCnt++;
							System.out.println(succeedCnt);
							textarea.setText(""); // textarea 초기화
							ingamePanel.revalidate();// 화면 다시 그림
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
							labelDownThread l = h.get(temp); // flag값을 1로 주어
							l.setDownFlag(1); // 인터럽트에서 스레드를삭제
							l.interrupt(); // 해당레이블 다운스레드 중지
							h.remove(temp); // HashMap에서 해당레이블 객체 삭제
							remove(temp); // ingamePanel에서 해당 테이블 없애기
							GameScore += (100 + userText.length() * 10) + (1 * 50); // 점수up
							textarea.setText(""); // textarea 초기화
							repaint(); // 화면 다시 그림
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
			System.out.println("Level Check 스레드 도는중 ... ");
			while (true) {
				try {
					sleep(1000);
					// System.out.println();
				} catch (InterruptedException e) {
				}
				if (GameScore >= (1000 * level * 2.5) && level != 5) {
					System.out.println("level Up 감지! ");
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
					System.out.println("level Up 레이블 장착");
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
						System.out.println("th스레드를 깨웠습니다.");
						Set<DownPanel> keys2 = h.keySet();
						Iterator<DownPanel> it2 = keys2.iterator();
						System.out.println("while 전");
						while (it2.hasNext()) {
							System.out.println("while 후");
							DownPanel panel = it2.next();
							labelDownThread laDownThread = h.get(panel);
							synchronized (laDownThread) {
								laDownThread.notify();
							}
						}
					}
					ingamePanel.remove(level);
					System.out.println("level 레이블 삭제했습니다.");
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
				System.out.println("파일 경로가 잘못되었습니다.");
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
						System.out.println("단어만드는 스레드를 중지시켰습니다.");
						Set<DownPanel> keys = h.keySet();
						Iterator<DownPanel> it = keys.iterator();
						while (it.hasNext()) {
							DownPanel panel = it.next();
							h.get(panel).interrupt();
							System.out.println("해쉬에있는 모든 스레드를 대기시키고있습니다.");
						}
						System.out.println("해쉬에있는 모든 스레드대기를 완료했습니다.");
						System.out.println("levelFlag를 다시 false값으로 줬습니다.");
						levelFlag = false;
					}
					if (overFlag == 1) {
						this.interrupt();
					}
					Thread.sleep(2000);
					int x = ((int) (Math.random() * (ingamePanel.getWidth() - 200)));
					String randomStr = threadWords.getRandomWord();
					panel = new DownPanel(randomStr, x, 1); // 내려가는 패널 제작
					ingamePanel.add(panel);
					labelDownThread ldt = new labelDownThread(panel); // 레이블내리는
					// 스레드생성
					System.out.println("DownLabel을 생성후 스레드 장착햇습니다.");
					h.put(panel, ldt); // 해쉬맵에 만든 스레드와 해당 랜덤워드를 담는다.
					textarea.setFocusable(true);
					textarea.requestFocus();
					ldt.start();
					System.out.println("스레드를 시작합니다");
				} catch (InterruptedException e) {
					try {
						if (overFlag == 0) {
							this.wait();
						} else if (overFlag == 1) {
							Set<DownPanel> key = h.keySet();
							Iterator<DownPanel> it2 = key.iterator();
							while (it2.hasNext()) { // 해쉬에 저장되어 있는 즉 현재 화면에 보이는 모든

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
			int x = ((int) (Math.random() * 100) + 100); // 속도랜덤하기위해
			int y = 10; // 내려가는 단위
			item = 0;
			// System.out.println(x);
			while (true) {
				panel.setLocation(panel.getLocation().x, panel.getLocation().y + y);
				if (panel.getLocation().y >= 580) { // 다시올라가기 위한 예외
					y = y * -1;
					x = x - 100; // 올라갈때 속도
					if (x < 10) // x값이 너무 낮아져 집게가 빠르게 올라가는 속도를 일정값으로 제어
						x = 50;
					panel.remove(panel.getImgLabel()); // 원래 이미지 지우고 새로운 이미지를 삽입
					item = (int) (Math.random() * 5 + 1); // item 이 나오기위한 랜덤값변수
					panel.setItem(item);
					if (panel.getitem() != 5) // item 값 변수가 있을경우 이미지를 item이미지로변경
						label = new ImgLabel("Img/tong3.png");
					else
						label = new ImgLabel("Img/lion.png"); // item
					label.setSize(180, 205);
					label.setLocation(0, 25);
					panel.add(label);
					repaint();
				}
				else if (panel.getLocation().y < 100 && y < 0) { // 데드라인까지
					// 올라왔을경우
					v.add(panel.getWord());// 입력못했던 단어들은 v 에 모인다.
					labelDownThread a = h.get(panel);
					if (a != null) {
						a.setDownFlag(1);
						a.interrupt();
					}
					ingamePanel.remove(panel);
					h.remove(panel);
					System.out.println("라이프 깍인다");
					life -= 1;
					if (vl.size() != 0) {
						JLabel t = vl.get(0);
						ingamePanel.remove(t);
						vl.remove(0);
					}
					if (life == 0) {
						Set<DownPanel> key = h.keySet();
						Iterator<DownPanel> it2 = key.iterator();
						while (it2.hasNext()) { // 해쉬에 저장되어 있는 즉 현재 화면에 보이는 모든
							labelDownThread l = h.get(it2.next());
							l.setDownFlag(1);
							l.interrupt();
						}
						th.overFlag = 1; // 스레드를 종료시키기위해
					}
					this.interrupt();
				}

				try {
					x = x - ((level - 1) * 50);// level 만큼 속도 제어
					if (x < 0)
						x = 100;
					Thread.sleep(x); // 게임속도
				} catch (InterruptedException e) {
					if (downFlag == 1) {
//						try {
//							Thread.sleep(500);
//						} catch (InterruptedException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
						System.out.println(this.currentThread().getName() + "는 죽었다.");
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

				} // while 안에있는 try catch
				ingamePanel.revalidate();
				repaint();
			} // while
		}// run
	}
}