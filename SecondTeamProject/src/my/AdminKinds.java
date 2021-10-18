package my;

/* 술 종류 창 : 양주,위스키,사케,소주,와인,맥주....
* 입력된 파일 출력 : 특정 술의 정보를 담은 파일(이름, 종류, 특징, 대표 술, 같이 먹기 좋은 안주....)
*					  수정 - 추가하고 싶은 정보를 추가할 수 있는 버튼  
*/
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AdminKinds extends JFrame {
	CreateAlcohol ca;
	Files io;
	JLabel imagelbl = new JLabel();
	JTextField idtf;
	private JPanel alchap;
	private HashMap<String, ImageIcon> alcMap;
	// 클릭을 이름과 사진 둘다 주기 위해
	private List<JPanel> pnllist;

	public AdminKinds(String id) {

		// 메인 판넬
		JPanel mainpnl = new JPanel();
		mainpnl.setLayout(new BoxLayout(mainpnl, BoxLayout.Y_AXIS));

		// 이름 판넬
		JPanel whopnl = new JPanel();
		JLabel wholbl = new JLabel();
		JButton logout = new JButton("로그아웃");
		logout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.main = new Main();
				dispose();
			}
		});
		
		wholbl.setText(id + "  __Welcome Administor");
		whopnl.add(wholbl);
		whopnl.add(logout);
		
		// 술 판넬
		JPanel kindpnl = new JPanel();
		JLabel kindlbl = new JLabel("Kinds Of Alcohol");
		kindpnl.add(kindlbl);

		alchap = new JPanel();

		alchap.setLayout(new GridLayout(0, 5));
		io = new Files();

		addInforAlc(id);
		// 술 추가 버튼
		JPanel addpnl = new JPanel();
		JButton addalcbtn = new JButton("Add Your Favourite Alchol");
		addpnl.add(addalcbtn);

		// 허용, 거부 버튼
		JPanel btnpnl = new JPanel();
		JButton btnConfirm = new JButton("확인");
		JButton btnDelete = new JButton("삭제");
		btnConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Confirm(AdminKinds.this, id);
			}
		});
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Delete(AdminKinds.this, id);
			}
		});
		btnpnl.add(btnConfirm);
		btnpnl.add(btnDelete);

		addalcbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new CreateAlcohol(AdminKinds.this, id);
			}
		});

		mainpnl.add(whopnl);
		mainpnl.add(kindpnl);
		mainpnl.add(alchap);
		mainpnl.add(addpnl);
		mainpnl.add(btnpnl);

		add(mainpnl);
		showGUI();

		// 들어왔을 때 배너 컨펌할 게 있으면 JOptionPane 호출하기
		File f = new File(".\\Confirm\\");
		if (f.listFiles().length != 0) {
			JOptionPane.showMessageDialog(AdminKinds.this, "확인할 추가요소가 있습니다");
		}

		File alcohol = new File(".\\alcohol");
		// 파일 이름가져오기
		File[] array = alcohol.listFiles();
		String[] alcName = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			alcName[i] = array[i].getName();
		}

		// 정보 수정 요청이 들어온 술 이름들을 뽑아서 수정 요청이 있다고 알림
		List<String> plzlist = new ArrayList<>();
		for (int i = 0; i < alcName.length; i++) {
			File ff = new File(".\\alcohol\\" + alcName[i] + "\\confirm");
			if (ff.listFiles().length != 0) {	
				plzlist.add(alcName[i]);
			}
		}
		if (plzlist.isEmpty()) {
		} else {
			JOptionPane.showMessageDialog(AdminKinds.this, plzlist.toString() + "에 정보수정 요청이 있습니다.");
		}
	}

	public void addInforAlc(String id) {
		// 넣는순간 다시 이 메소드를 돌리기 떄문에 alchap엔 원래 있던 값 + 한번더 쓰게 됨
		// 그러면 두번이 출력되므로 있던 값들을 전부 삭제해주고 다시 씀
		alchap.removeAll();
		// 한번 더 갱신해주기
		alcMap = io.alcoholload();

		pnllist = new ArrayList<>();

		Set<String> keySet = alcMap.keySet();
		Iterator<String> iter = keySet.iterator();
		for (int i = 0; i < alcMap.size(); i++) {
			JPanel alcpnl = new JPanel();
			alcpnl.setLayout(new BoxLayout(alcpnl, BoxLayout.Y_AXIS));
			String key = iter.next();
			ImageIcon value = alcMap.get(key);
			Image scale = value.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);
			JLabel lbl = new JLabel(key);
			JLabel img = new JLabel(new ImageIcon(scale));
			alcpnl.add(lbl);
			alcpnl.add(img);
			alchap.add(alcpnl);
			pnllist.add(alcpnl);
		}
		alchap.revalidate();
		alchap.repaint();

		// 반복문으로 전부 구현, 패널이 각각의 값을 가지기 위해 , 이렇게하면 사진까지 클릭범위에 못넣는다.
		for (int i = 0; i < alcMap.size(); i++) {
			pnllist.get(i).addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					JPanel pnl = (JPanel) e.getSource();
					JLabel lbl = new JLabel();
					lbl = (JLabel) pnl.getComponent(0);
					String name = lbl.getText();
					new AdminInterface(name, id);
				}
			});
		}
	}

	private void showGUI() {
		pack();
		setVisible(true);
	}

}
