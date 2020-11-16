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
	 * To-do list panel�� Text File�������� save
	 * @param map : HashMap�� list�� Key, üũ������ Value�� ����.
	 * @param name : ��¥ ������ File name. �ش��ϴ� ��¥�� File�� ����
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
			JOptionPane.showMessageDialog(null, "������ ������ �� �����ϴ�.");
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

