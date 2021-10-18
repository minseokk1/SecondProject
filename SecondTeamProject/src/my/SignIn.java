package my;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignIn extends JDialog {
	private Files io;
	JTextField idtf;

	public SignIn() {
		io = new Files();
		// 정보 판넬
		JPanel mainpnl = new JPanel();
		mainpnl.setLayout(new BoxLayout(mainpnl, BoxLayout.Y_AXIS));

		// 아이디
		JPanel idpnl = new JPanel();
		JLabel idlbl = new JLabel("아이디");
		idlbl.setPreferredSize(new Dimension(100, 30));
		idtf = new JTextField(15);

		idpnl.add(idlbl);
		idpnl.add(idtf);

		// 비밀번호
		JPanel pwpnl = new JPanel();
		JLabel pwlbl = new JLabel("비밀번호");
		pwlbl.setPreferredSize(new Dimension(100, 30));
		JPasswordField pwtf = new JPasswordField(15);

		pwpnl.add(pwlbl);
		pwpnl.add(pwtf);

		mainpnl.add(idpnl);
		mainpnl.add(pwpnl);

		// 로그인 버튼
		JPanel btnpnl = new JPanel();
		JButton joinbtn = new JButton("로그인");
		JButton idsearchbtn = new JButton("ID찾기");
		joinbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> infor = new ArrayList<>();
				infor = io.load();
				String[] getInfor = new String[2]; // 아이디, 비밀번호
				String[] getId = new String[infor.size()]; // id 전부
				String[] getPw = new String[infor.size()]; // 비밀번호 전부
				
				for (int i = 0; i < infor.size(); i++) {
					getInfor = infor.get(i).split(","); // 아이디 비밀번호 잘라서
					getId[i] = getInfor[0]; // 아이디 넣기
					getPw[i] = getInfor[1]; // 비밀번호 넣기
				}

				int a = 0;
				for (int i = 0; i < getId.length; i++) {
					if (!idtf.getText().equals(getId[i])) {
						a++;
					} else {
						break;
					}
				}
				if (idtf.getText().isEmpty()) {
					JOptionPane.showMessageDialog(SignIn.this, "아이디를 입력하세요");
				} else if (a == getId.length) {
					JOptionPane.showMessageDialog(SignIn.this, "아이디가 존재하지 않습니다");
				} else if (!new String(pwtf.getPassword()).equals(getPw[a])) {
					JOptionPane.showMessageDialog(SignIn.this, "비밀번호가 틀렸습니다");
				} else {
					JOptionPane.showMessageDialog(SignIn.this, "로그인 되었습니다");
					dispose();
					Main.main.dispose();
					if(idtf.getText().equals("Admin")) {
						new AdminKinds(idtf.getText());
					} else {
						new UserKinds(idtf.getText());
					}

				}
			}
		});
		
		idsearchbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new IDSearch();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
		});

		btnpnl.add(joinbtn);
		btnpnl.add(idsearchbtn);

		add(mainpnl, "North");
		add(btnpnl, "South");

		showGUI();
	}

	private void showGUI() {
		setModal(true);
		pack();
		setLocation(650, 350);
		setVisible(true);
	}
}
