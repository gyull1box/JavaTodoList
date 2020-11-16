package scheduller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

public class Gui_Calender extends JFrame implements ActionListener {
	private JPanel upPanel, sPanel, dayPanel, dayOfWeek;
	private JButton[] day = new JButton[HOLI * VERTI];
	private JButton BfYear, BfMonth, NxtMonth, NxtYear, toDay, startB, finishB;
	private JTextField Year, Month;
	private JLabel[] week;
	private JLabel y, m, atta;
	private Calendar cal = Calendar.getInstance(); 
	private Calendar cc = Calendar.getInstance();
	private static final int HOLI = 7;
	private static final int VERTI = 6;
	private boolean sf;
	private String sY, sM, sD, fY, fM, fD, StartName, read;
	private BufferedWriter bw;
	private Scanner sc = null;
	private static final String FONT = "돋움";
	private static final int FONT_SIZE = 13;
	private final String ww[] = { "일", "월", "화", "수", "목", "금", "토" };

	/**
	 * 사용자가 보고있는 Calender의 년,월 정보 TextField, TextLabel. 
	 * (Default: 현재 년도, 월)
	 */
	private void viewDay() {
		Year = new JTextField();
		Year.setText(String.valueOf(cal.get(Calendar.YEAR)));
		Year.setFocusable(false);
		Year.setEditable(false);
		Year.setBorder(BorderFactory.createEmptyBorder());
		y = new JLabel("년");

		Month = new JTextField();
		Month.setText(String.valueOf(cal.get(Calendar.MONTH) + 1));
		Month.setEditable(false);
		Month.setFocusable(false);
		Month.setBorder(BorderFactory.createEmptyBorder());
		m = new JLabel("월");
	}

	/**
	 * Calender 날짜 이동 버튼, Year, Month inform panel
	 */
	private void sPanel() {
		sPanel = new JPanel();
		sPanel.setLayout(new FlowLayout());

		viewDay();
		BfYear = new JButton("<<");
		BfYear.setFocusable(false);
		BfMonth = new JButton("<");
		BfMonth.setFocusable(false);
		NxtMonth = new JButton(">");
		NxtMonth.setFocusable(false);
		NxtYear = new JButton(">>");
		NxtYear.setFocusable(false);

		sPanel.add(BfYear);
		sPanel.add(BfMonth);
		sPanel.add(Year);
		sPanel.add(y);
		sPanel.add(Month);
		sPanel.add(m);
		sPanel.add(NxtMonth);
		sPanel.add(NxtYear);
	}

	/**
	 * 요일 panel
	 */
	private void dayOfWeekLabel() {
		dayOfWeek = new JPanel();
		dayOfWeek.setLayout(new GridLayout(1, HOLI));

		week = new JLabel[7];
		for (int i = 0; i < week.length; ++i) {
			week[i] = new JLabel();
			week[i].setText(ww[i]);
			week[i].setFont(new Font(FONT, Font.BOLD, FONT_SIZE));
			week[i].setHorizontalAlignment(SwingUtilities.CENTER);
			dayOfWeek.add(week[i]);
		}
		week[0].setForeground(Color.RED);
		week[6].setForeground(Color.BLUE);
	}

	/**
	 * 사용자가 보는 Calender의 Year, Month, DayofWeek panel. 
	 */
	private void upPanel() {
		upPanel = new JPanel();
		upPanel.setLayout(new BorderLayout());

		sPanel();
		upPanel.add(sPanel, BorderLayout.NORTH);

		dayOfWeekLabel();
		upPanel.add(dayOfWeek, BorderLayout.SOUTH);

		toDay = new JButton("Today!!");
		toDay.setFocusPainted(false);
		sPanel.add(toDay, BorderLayout.PAGE_END);
	}

	/**
	 * 상단의 년도, 월 정보를 토대로 년도 월에 해당하는 일 버튼배치, 각 버튼의 listener 선언.
	 */
	private void dayButton() {
		day = new JButton[VERTI * HOLI];

		cal.set(Calendar.YEAR, Integer.parseInt(Year.getText()));
		cal.set(Calendar.MONTH, Integer.parseInt(Month.getText()) - 1);
		cal.set(Calendar.DATE, 1);

		int firstDay = cal.get(Calendar.DAY_OF_WEEK);
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		if (firstDay == 1) {
			for (int i = 0; i < day.length; ++i) {
				day[i] = new JButton(String.valueOf(i + 1));

				if (cal.get(Calendar.DAY_OF_WEEK) == 7) {
					day[i].setForeground(Color.BLUE);
				}
				if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
					day[i].setForeground(Color.RED);
				}

				cal.add(Calendar.DATE, 1);

				if (i >= lastDay) {
					day[i] = new JButton();
					day[i].setEnabled(false);
				}
			}
		} else {
			int d = 0;

			for (int i = 0; i < day.length; ++i) {
				if (i < firstDay - 1) {
					day[i] = new JButton();
					day[i].setEnabled(false);
				} else {
					day[i] = new JButton(String.valueOf(++d));

					if (cal.get(Calendar.DAY_OF_WEEK) == 7) {
						day[i].setForeground(Color.BLUE);
					}
					if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
						day[i].setForeground(Color.RED);
					}
					cal.add(Calendar.DATE, 1);
					if (d > lastDay) {
						day[i] = new JButton();
						day[i].setEnabled(false);
					}
				}

			}
		}
		for (int i = 0; i < day.length; ++i) {
			int nowY = cc.get(Calendar.YEAR);
			int nowM = cc.get(Calendar.MONTH) + 1;
			int nowD = cc.get(Calendar.DATE);
			day[i].getText();
			day[i].setBackground(Color.white);

			if (day[i].getText().isEmpty() == true) {
				day[i].setBackground(Color.lightGray);
			}

			day[i].setHorizontalAlignment(SwingConstants.LEFT);
			day[i].setVerticalAlignment(SwingConstants.NORTH);
			day[i].setFocusable(false);
			day[i].addMouseListener(new MouseInputListener() {

				@Override
				public void mouseMoved(MouseEvent e) {
				}

				@Override
				public void mouseDragged(MouseEvent e) {
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mousePressed(MouseEvent e) {
					if (SwingUtilities.isRightMouseButton(e)) {
						if (!sf) {
							startB = (JButton) e.getSource();
							sY = Year.getText();
							sM = Month.getText();
							sD = startB.getText();
							sf = true;
							JOptionPane.showMessageDialog(null, "종료일을 클릭해주세요.");
							return;
						}
						finishB = (JButton) e.getSource();
						fY = Year.getText();
						fM = Month.getText();
						fD = finishB.getText();
						Calendar sC = Calendar.getInstance();
						Calendar fC = Calendar.getInstance();

						sC.set(Integer.parseInt(sY), Integer.parseInt(sM), Integer.parseInt(sD));
						fC.set(Integer.parseInt(fY), Integer.parseInt(fM), Integer.parseInt(fD));

						if (fC.before(sC)) {
							JOptionPane.showMessageDialog(null, "시작-종료일을 확인해주세요.");
							return;
						}
						try {
							StartName = JOptionPane.showInputDialog("시작할 일정명을 입력하세요.");
						} catch (NullPointerException nu) {
							nu.printStackTrace();
						}

						JLabel finishL = new JLabel(" << " + StartName);
						finishL.setForeground(Color.MAGENTA);
						finishB.setVisible(false);
						finishB.add(finishL);
						finishB.setVisible(true);

						JLabel startL = new JLabel(" >> " + StartName);
						startL.setForeground(Color.MAGENTA);
						startB.setVisible(false);
						startB.add(startL);
						startB.setVisible(true);

						File f = new File("ScheDule");
						if (!f.exists()) {
							f.mkdir();
						}
						try {
							bw = new BufferedWriter(
									new FileWriter("ScheDule/start_" + sY + "_" + sM + "_" + sD + ".txt"));
							bw.write(StartName);
							bw.close();
							bw = new BufferedWriter(
									new FileWriter("ScheDule/finish_" + fY + "_" + fM + "_" + fD + ".txt"));
							bw.write(StartName);
						} catch (Exception ea) {
							ea.printStackTrace();
						} finally {
							try {
								bw.close();
							} catch (IOException ea) {
								ea.printStackTrace();
							}
						}

						sf = false;
						return;
					}
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					JButton j = (JButton) e.getSource();
					String name = Year.getText() + "-" + Month.getText() + "-" + j.getText();
					File f = new File("ScheDule/" + name + ".txt");

					if (e.getClickCount() == 2) {
						if (!f.exists()) {
							new AddSchedule().setTitle(name);
						} else {
							new LoadSchedule(name).setTitle(name);
						}
					}
				}
			});

			if (Year.getText().equals(String.valueOf(nowY)) && Month.getText().equals(String.valueOf(nowM))
					&& day[i].getText().equals(String.valueOf(nowD))) {
				day[i].setBackground(Color.PINK);
			}
		}

		File ff[] = new File("ScheDule").listFiles();

		for (File a : ff) {
			if (!a.getName().contains("-"))
				continue;
			String[] n = a.getName().split("-|\\.");
			for (JButton b : day) {
				if (Year.getText().equals(n[0]) && Month.getText().equals(n[1]) && b.getText().equals(n[2])) {
					JLabel l = new JLabel(" Check ME :D");
					b.add(l);
				}
			}
		}

		for (File a : ff) {
			if (!a.getName().contains("_"))
				continue;

			String[] n = a.getName().split("_|\\.|\\\\");
			for (JButton b : day) {
				try {
					if (Year.getText().equals(n[1]) && Month.getText().equals(n[2]) && b.getText().equals(n[3])) {
						sc = new Scanner(a);
						while (sc.hasNext()) {
							read = sc.nextLine();
						}
						if (n[0].equals("start")) {
							atta = new JLabel(">> " + read);
						} else if (n[0].equals("finish")) {
							atta = new JLabel(" << " + read);
						}
						atta.setForeground(Color.MAGENTA);
						b.add(atta);
						b.setVisible(true);
					}
				} catch (FileNotFoundException f) {
					System.out.println("File이 없습니다.");
				} finally {
					try {
						if (null != sc) {
							sc.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 1 ~12월 까지만 보여주도록 설정 (12월 이후: 다음해 1월, 1월 이전: 이전해 12월로 설정 )
	 */
	private void MonthLimit() {
		if (Integer.parseInt(Month.getText()) > 12) {
			Month.setText(String.valueOf(1));
			Year.setText(String.valueOf(Integer.parseInt(Year.getText()) + 1));
		}
		if (Integer.parseInt(Month.getText()) < 1) {
			Month.setText(String.valueOf(12));
			Year.setText(String.valueOf(Integer.parseInt(Year.getText()) - 1));
		}
	}

	/**
	 * Calendar의 Day Button이 붙는 panel
	 */
	private void dayPanel() {
		dayPanel = new JPanel();
		dayPanel.setLayout(new GridLayout(VERTI, HOLI));
		dayButton();
		for (int i = 0; i < HOLI * VERTI; ++i) {
			dayPanel.add(day[i]);
		}
	}

	/**
	 * Calender의 Year,Month 이동 시 dayPanel을 update
	 */
	private void btnVisible() {
		this.dayPanel.removeAll();
		this.dayPanel.setVisible(false);
		dayPanel();
		this.add(dayPanel, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == BfYear) {
			Year.setText(String.valueOf(Integer.parseInt(Year.getText()) - 1));
		}
		if (e.getSource() == BfMonth) {
			Month.setText(String.valueOf(Integer.parseInt(Month.getText()) - 1));
			MonthLimit();
		}
		if (e.getSource() == NxtMonth) {
			Month.setText(String.valueOf(Integer.parseInt(Month.getText()) + 1));
			MonthLimit();
		}
		if (e.getSource() == NxtYear) {
			Year.setText(String.valueOf(Integer.parseInt(Year.getText()) + 1));
		}
		if (e.getSource() == toDay) {
			Year.setText(String.valueOf(cc.get(Calendar.YEAR)));
			Month.setText(String.valueOf(cc.get(Calendar.MONTH) + 1));
		}
		btnVisible();
	}

	/**
	 * Scheduller에서 Calender를 담당하는 Gui
	 * @author SoHee Jeon
	 */
	public Gui_Calender() {
		setTitle("스케줄러");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 500);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		upPanel();
		add(upPanel, BorderLayout.NORTH);
		BfYear.addActionListener(this);
		BfMonth.addActionListener(this);
		NxtMonth.addActionListener(this);
		NxtYear.addActionListener(this);
		toDay.addActionListener(this);
		dayPanel();
		add(dayPanel, BorderLayout.CENTER);

		setVisible(true);
	}

	public static void main(String[] args) {
		new Gui_Calender();
	}
}
