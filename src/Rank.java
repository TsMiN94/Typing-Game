import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JPanel;

public class Rank {
	private BTTRankPanel bttrankpanel;
	private Vector<String> idv;
	private Vector<String> scorev;
	private Vector<BTTJLabel> labelv;
	private FileReader nrfin;
	private FileReader srfin;
	private BufferedReader nrbr;
	private BufferedReader srbr;
	private BufferedWriter nwbr;
	private BufferedWriter swbr;
	private FileWriter nwfin;
	private FileWriter swfin;
	
	public Rank() {
		idv = new Vector<String>();
		scorev = new Vector<String>();
		labelv = new Vector<BTTJLabel>();
	}
	
	public void readFile(String namefile, String scorefile) {
		String id;
		String score;
		try {
			nrfin = new FileReader(namefile);
			srfin = new FileReader(scorefile);
			nrbr = new BufferedReader(nrfin);
			srbr = new BufferedReader(srfin);
			while ((id = nrbr.readLine()) != null && (score = srbr.readLine()) != null) {
				idv.add(id);
				scorev.add(score);
			}
		} catch (IOException e) {
			System.out.println("파일 벡터에 넣기 실패.");
			System.exit(0);
		}
	}
	public void setBTTRankPanel(JPanel bttrankpanel) {
		this.bttrankpanel = (BTTRankPanel) bttrankpanel;
	}
	public void writeFile(String namefile, String scorefile) {
		try {
			nwfin = new FileWriter(namefile);
			swfin = new FileWriter(scorefile);
			nwbr = new BufferedWriter(nwfin);
			swbr = new BufferedWriter(swfin);
		} catch (IOException e) {
			System.out.println("파일 쓰기 실패");
		}
	}
	public void closeReadFiles() {
		try {
			nrfin.close();
			srfin.close();
			nrbr.close();
			srbr.close();	
		} catch (IOException e) {
			System.out.println("파일 닫기 실패 read");
		}
	}
	public void closeWriteFiles() {
		try {
			for(int i=0; i < 5 ; i++){
				try {
					System.out.println(idv.get(i));
					nwbr.write(idv.get(i));
					swbr.write(scorev.get(i));
					nwbr.newLine();
					swbr.newLine();
				} catch (IOException e) {
					return;
				}
			}
			nwbr.close();
			swbr.close();
		}catch(IOException e) {
			System.out.println("파일 닫기 실패 write");
		}
	}
	public void setLabelVector(Vector<BTTJLabel> labelv) {
		this.labelv = labelv;
	}
	public Vector<String> getIdv() {
		return idv;
	}
	public Vector<String> getScorev() {
		return scorev;
	}
	public void setIdv(Vector<String> idv) {
		this.idv = idv;
	}
	public void setScorev(Vector<String> scorev) {
		this.scorev = scorev;
	}

}