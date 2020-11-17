# JavaTodoList
## 프로그램 주제
Calender를 활용한 To-do list
## 핵심기능
사용자의 일정관리 및 To-do list 생성.
## 힘들었던 점 => 어떻게 해결했는지
1. 사용자가 list를 추가할 때마다 같은 name을 가진 checkBox, Panel, 삭제 Button을 생성하고 list를 판넬에 붙이는 방식을 사용함.
   list삭제 시 같은 라인의 list를 삭제해야하는데 해당 버튼의 parents를 삭제하면 최근에 추가한 판넬이 삭제되는 문제가 있었음.
   => 개별 판넬을 각각 Component 배열에 넣어 각각 한 배열에 세 요소가 들어있게하고 삭제 Button의 parent panel을 삭제함.
   
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


## 보완해야 하는 점
1. Day Button의 function이 많아 코드가 길어짐.
2. list를 load시 Boolean부분 앞을 가져올 때 '+'사용해 사용자가 list에 '+'를 사용 시 list를 온전하게 못 불러올 수 있음.
3. Day Button에 일정이 겹치면 Text가 겹침. 

## 유튜브 링크
링크:
## JAVADOC
[API로!](JavaTodoList/Personal_pro/doc/index.html)
