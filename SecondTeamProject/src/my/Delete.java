package my;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Delete extends JDialog {
	private Files io;
	private HashMap<String, ImageIcon> deletehapMap;
	private List<JPanel> pnllist;
	private JPanel delhappnl;
	
	public Delete(AdminKinds adminKinds, String id) {
		io = new Files();
		
		JPanel mainpnl = new JPanel();
		mainpnl.setLayout(new BoxLayout(mainpnl, BoxLayout.Y_AXIS));
		
		JPanel lblpnl = new JPanel();
		JLabel lbl = new JLabel("삭제 목록");
		lblpnl.add(lbl);
		
		delhappnl = new JPanel();
		delhappnl.setLayout(new GridLayout(0, 5));
		
		JPanel okpnl = new JPanel();
		JButton okbtn = new JButton("적용");
		okpnl.add(okbtn);
		
		okbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				adminKinds.addInforAlc(id);
				adminKinds.pack();
				dispose();
			}
		});
		
		addInforAlc();
		
		mainpnl.add(lblpnl);
		mainpnl.add(delhappnl);
		mainpnl.add(okpnl);
		add(mainpnl);
		
		showGUI();
	}

	protected void addInforAlc() {
		delhappnl.removeAll();
		// 한번 더 갱신해주기
		deletehapMap = io.alcoholload();

		pnllist = new ArrayList<>();

		Set<String> keySet = deletehapMap.keySet();
		Iterator<String> iter = keySet.iterator();
		for (int i = 0; i < deletehapMap.size(); i++) {
			JPanel alcpnl = new JPanel();
			alcpnl.setLayout(new BoxLayout(alcpnl, BoxLayout.Y_AXIS));
			String key = iter.next();
			ImageIcon value = deletehapMap.get(key);
			Image scale = value.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);
			JLabel lbl = new JLabel(key);
			JLabel img = new JLabel(new ImageIcon(scale));
			alcpnl.add(lbl);
			alcpnl.add(img);
			delhappnl.add(alcpnl);
			pnllist.add(alcpnl);
		}
		delhappnl.revalidate();
		delhappnl.repaint();

		// 반복문으로 전부 구현, 패널이 각각의 값을 가지기 위해 , 이렇게하면 사진까지 클릭범위에 못넣는다.
		for (int i = 0; i < deletehapMap.size(); i++) {
			pnllist.get(i).addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					JPanel pnl = (JPanel) e.getSource();
					JLabel lbl = new JLabel();
					lbl = (JLabel) pnl.getComponent(0);
					String name = lbl.getText();
					//술이름이랑 사진 주고 허용 거부 띄워주는 다이얼로그 하나 만들기
					new OkorNo(name, Delete.this);
				}
			});
		}
	}

	private void showGUI() {
		setModal(true);
		pack();
		setVisible(true);
	}
}
