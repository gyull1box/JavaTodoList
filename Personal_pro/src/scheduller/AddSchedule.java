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
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class AddSchedule extends JFrame {
	private JTextArea list;
	private JButton delete, save;
	private JPanel boardPanel, listPanel, textPanel;
	private JCheckBox chkBox;
	private String b4;
	private HashMap<String, Boolean> map = new HashMap<String, Boolean>();
	private static final String FONT = "돋움";
	private static final int FONT_SIZE = 20;
	/**
	 * TextArea에 To-do list 작성하는 Gui
	 */
	private void popAddList() {
		textPanel = new JPanel();
		textPanel.setLayout(new BorderLayout());
		list = new JTextArea(" + To do List");
		list.setEditable(false);
		list.setFont(new Font(FONT, Font.BOLD, FONT_SIZE));

		save = new JButton("Save");
		save.setSize(new Dimension(10, 10));
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
				list.setText("");
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
				if (e.getKeyCode() != KeyEvent.VK_ENTER) {
					return;
				}
				list.setText("");
				if (b4.equals("+ To do List") || b4.isEmpty()) {
					JOptionPane.showMessageDialog(null, "일정을 입력하세요.");
					return;
				}
				newPanel();
			}
		});
	}
/**
 * 작성한 Text를 checkbox + userList + delete Button순으로 나열하는 panel
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

		Component[] c = listPanel.getComponents();
		ArrayList<Component> c2 = new ArrayList<>();
		for (int i = 0; i < c.length; ++i) {
			c2.add(c[i]);
		}

		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (Component cc : c) {
					if (cc instanceof JButton) {
						boardPanel.remove(cc.getParent());
					}
					if (cc instanceof JCheckBox) {
						c2.remove(((JCheckBox) cc));
					}
				}
				boardPanel.revalidate();
				boardPanel.repaint();
			}
		});

		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = getTitle();
				for (Component cc : c2) {
					if (cc instanceof JCheckBox) {
						// KeyMap에 체크 유무를 Key로, a를 value로 가져와 저장,,?
						boolean check = ((JCheckBox) cc).isSelected();
						String line = ((JCheckBox) cc).getText().replace("\n", "");

						map.put(line, check);
					}
				}
				new SaveSchedule(map, name);
				
			}
		});

	}
	/**
	 * 사용자의 To-do list panel
	 * Title name : To-do list save file name
	 * @author SoHee Jeon
	 */
	public AddSchedule() {
		setLayout(new BorderLayout());
		setSize(300, 500);
		setLocationRelativeTo(null);

		popAddList();
		add(textPanel, BorderLayout.NORTH);

		boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(10, 1));
		add(boardPanel, BorderLayout.CENTER);

		setVisible(true);
	}
}
