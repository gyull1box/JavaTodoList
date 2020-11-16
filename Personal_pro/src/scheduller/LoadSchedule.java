package scheduller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class LoadSchedule extends JFrame {
	private JTextArea list;
	private Scanner sc = null;
	private ArrayList<String >kv = new ArrayList<>();
	private JPanel sv_listPanel, boardPanel, textPanel, listPanel = null;
	private JButton delete, save = null;
	private JCheckBox chkBox = null;
	private File f = null;
	private String b4;
	private HashMap<String, Boolean> map = new HashMap<String,Boolean>();
	private ArrayList<Component> c2 = new ArrayList<>();
	private static final int MAX_VIEW = 10;
	private static final String FONT = "돋움";
	private static final int FONT_SIZE = 13;
	/**
	 * 저장 된 To-do list File을 불러옴
	 * @param name : 날짜 형식의 File name. 해당하는 날짜 File을 불러옴.
	 * @author SoHee Jeon 
	 */
	public LoadSchedule(String name) {
		setLayout(new BorderLayout());
		setSize(300,500);
		setLocationRelativeTo(null);
		
		boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(MAX_VIEW, 1));
		add(boardPanel, BorderLayout.CENTER);
		try {
			f = new File("ScheDule/" +name + ".txt");
			sc = new Scanner(f);
			while(sc.hasNextLine()) {
				kv.add(sc.nextLine());
			}
			
			popAddList();
			add(textPanel, BorderLayout.NORTH);
			setVisible(true);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "파일이 없습니다.");
		} finally { 
			if(null != sc) {
				sc.close();
			}
		}
	}
	/**
	 * File 내용, check 유무 정보를가져와 list panel 생성
	 */
	private void attatchFrame() {		
		for(String a: kv) {
			sv_listPanel = new JPanel();
			sv_listPanel.setLayout(new BorderLayout());
			sv_listPanel.setBackground(Color.white);
			sv_listPanel.setSize(new Dimension(200, 30));
			
			String boxName = a.substring(0, a.indexOf("+"));
			String boxStat = a.substring(a.indexOf("+")+1);
			boolean b = false;
			if(boxStat.equals("true")) {
				b = true;
			}
			chkBox = new JCheckBox(boxName, b);
			chkBox.setBackground(Color.white);
			chkBox.setSize(new Dimension(50, 50));
			
			delete = new JButton(" X ");
			delete.setBackground(Color.white);
			delete.setFocusPainted(false);
			delete.setBorderPainted(false);
			
			sv_listPanel.add(chkBox, BorderLayout.WEST);
			sv_listPanel.add(delete, BorderLayout.EAST);
			
			Component [] sv_c = sv_listPanel.getComponents();
			
			for(int i = 0; i < sv_c.length; ++i) {
				c2.add(sv_c[i]);
			}

			delete.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					for(Component cc : sv_c) {
						if(cc instanceof JButton) {
							boardPanel.remove(cc.getParent());
						}
						if(cc instanceof JCheckBox) {
							c2.remove(((JCheckBox) cc));
						}
					}
					boardPanel.revalidate();
					boardPanel.repaint();
				}
			});
			boardPanel.add(sv_listPanel);
		}
	}
	/**
	 * 저장된 To-do list 나열하는 panel
	 */
	private void popAddList() {
		attatchFrame();

		textPanel = new JPanel();
		textPanel.setLayout(new BorderLayout());
		list = new JTextArea(" + To do List");
		list.setEditable(false);
		list.setFont(new Font(FONT, Font.BOLD, FONT_SIZE));
		
		save = new JButton("Save");
		save.setSize(new Dimension(10,10));
		add(save, BorderLayout.SOUTH);
		textPanel.add(list, BorderLayout.CENTER);
		textPanel.add(save, BorderLayout.EAST);
		list.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
			list.setEditable(true);
				if(list.getText().equals(" + To do List")) {
					list.setText("");
				}
				list.setFocusable(true);
				}
			});
		
			list.addKeyListener(new KeyListener() {
		
				@Override
				public void keyTyped(KeyEvent e) {
				}
		
				@Override
				public void keyReleased(KeyEvent e) {			
				}
		
				@Override
				public void keyPressed(KeyEvent e) {
					b4 = list.getText().trim();
					if(e.getKeyCode() != KeyEvent.VK_ENTER) {
						return;
					}
					list.setText("");
					if(b4.equals("+ To do List") || b4.isEmpty()) {
						JOptionPane.showMessageDialog(null, "일정을 입력하세요.");
						return;
					}
					newPanel();
				}
			});
	
	save.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String name = getTitle();
		for(Component cc : c2) {
			if(cc instanceof JCheckBox) {
				// KeyMap에 체크 유무를 Key로, a를 value로 가져와 저장,,?
				boolean check = ((JCheckBox)cc).isSelected();
				String line = ((JCheckBox) cc).getText().replace("\n", "");
				map.put(line, check);
			}
		}
		new SaveSchedule(map, name);
		}
	});
}
	/**
	 * 새로 입력한 To-do list 나열하는 panel
	 */
	private void newPanel() {
		listPanel = new JPanel();
		listPanel.setLayout(new BorderLayout());
		listPanel.setBackground(Color.white);
		
		chkBox = new JCheckBox(b4);
		chkBox.setBackground(Color.white);
		chkBox.setSize(new Dimension(50, 50));
		listPanel.setSize(new Dimension(200, 30));

		delete = new JButton(" X ");
		delete.setBackground(Color.white);
		delete.setFocusPainted(false);
		delete.setBorderPainted(false);
		
		listPanel.add(chkBox, BorderLayout.WEST);
		listPanel.add(delete, BorderLayout.EAST);
		boardPanel.add(listPanel);
		
		Component [] c = listPanel.getComponents();
		for(int i = 0; i < c.length; ++i) {
			c2.add(c[i]);
		}
		
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(Component cc : c) {
					if(cc instanceof JButton) {
						boardPanel.remove(cc.getParent());
					}
					if(cc instanceof JCheckBox) {
						c2.remove(((JCheckBox) cc));
					}
				}
				boardPanel.revalidate();
				boardPanel.repaint();
			}
		});
	}
}
