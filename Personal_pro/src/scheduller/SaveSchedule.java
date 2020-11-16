package scheduller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

public class SaveSchedule {
	private File f = null;
	private BufferedWriter wr = null;
	/**
	 * To-do list panel을 Text File형식으로 save
	 * @param map : HashMap에 list를 Key, 체크유무는 Value로 저장.
	 * @param name : 날짜 형식의 File name. 해당하는 날짜에 File을 저장
	 * @author SoHee Jeon
	 */
	public SaveSchedule(HashMap<String, Boolean> map, String name) {
		f = new File("ScheDule");
		if(!f.isDirectory()) {
			f.mkdir();
		}
		try {
		String filePath = "ScheDule/"+name + ".txt";
		wr = new BufferedWriter(new FileWriter(filePath));
		
	for(Map.Entry<String, Boolean> m : map.entrySet()) {
		String k = m.getKey();
		String v = "+" + m.getValue().toString();
			wr.write(k + v + "\n");
		}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "파일을 저장할 수 없습니다.");
		} finally {
			try {
			if(null != wr) {
			wr.close();
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

