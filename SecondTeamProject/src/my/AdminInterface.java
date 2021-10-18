package my;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

public class AdminInterface extends JDialog {
	Files io;
	JTextPane tp;
	Set<String> people;
	JLabel thankslbl;
	
	public AdminInterface(String alcname, String id) {
		io = new Files();
		//왼쪽 술 이름과 사진
		JPanel picAndNamepnl = new JPanel();
		ImageIcon img = new ImageIcon(".\\" + alcname + "\\sajin.jpg"); 
		Image scale = img.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);
		JLabel namelbl = new JLabel(alcname);
		JLabel piclbl = new JLabel(new ImageIcon(scale));
		
		picAndNamepnl.add(namelbl);
		picAndNamepnl.add(piclbl);

		//오른쪽 텍스트 에어리어
		tp = new JTextPane();
		tp.setEditable(false);
		String content = io.inforload(alcname);
		tp.setText(content);
		//커서 포지션 지정해주는 명령어 >> 텍스트펜 위치 맨위로 보이게 됨
		tp.setCaretPosition(0);
		tp.setPreferredSize(new Dimension(400, 400));
		JScrollPane scrl = new JScrollPane(tp);
		//스크롤펜에 스크롤바를 어떻게 달거냐   
		scrl.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		//아랫쪽 수정 버튼과 수정한 사람들 리스트
		JPanel btnAndNamepnl = new JPanel();
		JButton btnMod = new JButton("수정");
		btnMod.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ModifiedInfor(alcname, AdminInterface.this, id);
			}
		});
		
		JButton btnOk = new JButton("확인");
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ConfirmLylics(AdminInterface.this, id, alcname);
			}
		});
		people = new TreeSet<>();
		people = io.helpload(alcname);
		thankslbl = new JLabel("도움을 주신 분들 : " + people);
		
		btnAndNamepnl.add(btnMod);
		btnAndNamepnl.add(btnOk);
		btnAndNamepnl.add(thankslbl);
		
		//넣기
		add(picAndNamepnl, "North");
		add(scrl);
		add(btnAndNamepnl, "South");
		
		showGUI();
	}

	private void showGUI() {
		setModal(true);
		pack();
		setVisible(true);
	}
}
