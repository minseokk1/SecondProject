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
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

//회원가입창
public class SignUp extends JDialog {
	private Files io;

	public SignUp() throws ParseException {
		io = new Files();
		List<JTextField> listInfor = new ArrayList<>();

		// 정보 판넬
		JPanel mainpnl = new JPanel();
		mainpnl.setLayout(new BoxLayout(mainpnl, BoxLayout.Y_AXIS));
		// 아이디
		JPanel idpnl = new JPanel();
		JLabel idlbl = new JLabel("아이디");
		idlbl.setPreferredSize(new Dimension(100, 30));
		JTextField idtf = new JTextField(15);
		JButton btnsame = new JButton("중복확인");

		idpnl.add(idlbl);
		idpnl.add(idtf);
		idpnl.add(btnsame);

		// 비밀번호
		JPanel pwpnl = new JPanel();
		JLabel pwlbl = new JLabel("비밀번호");
		pwlbl.setPreferredSize(new Dimension(100, 30));
		JPasswordField pwtf = new JPasswordField(15);
		JLabel bk = new JLabel();
		bk.setPreferredSize(new Dimension(85, 30));

		pwpnl.add(pwlbl);
		pwpnl.add(pwtf);
		pwpnl.add(bk);

		// 비밀번호 확인
		JPanel confirmpnl = new JPanel();
		JLabel confirmlbl = new JLabel("비밀번호 확인");
		confirmlbl.setPreferredSize(new Dimension(100, 30));
		JPasswordField confirmtf = new JPasswordField(15);
		JLabel bk2 = new JLabel();
		bk2.setPreferredSize(new Dimension(85, 30));

		confirmpnl.add(confirmlbl);
		confirmpnl.add(confirmtf);
		confirmpnl.add(bk2);

		// 연락처
		JPanel pnlpn = new JPanel();
		MaskFormatter formatterPhone = new MaskFormatter("010-****-****");
		formatterPhone.setPlaceholderCharacter('*');
		JLabel pnlbl = new JLabel("연락처");
		pnlbl.setPreferredSize(new Dimension(100, 30));
		JFormattedTextField pntf = new JFormattedTextField(formatterPhone);
		pntf.setColumns(15);

		JLabel bk3 = new JLabel();
		bk3.setPreferredSize(new Dimension(85, 30));

		pnlpn.add(pnlbl);
		pnlpn.add(pntf);
		pnlpn.add(bk3);

		// 아이디, 비밀번호, 비밀번호 확인을 한 리스트에 넣기
		listInfor.add(idtf);
		listInfor.add(pwtf);
		listInfor.add(confirmtf);
		listInfor.add(pntf);

		mainpnl.add(idpnl);
		mainpnl.add(pwpnl);
		mainpnl.add(confirmpnl);
		mainpnl.add(pnlpn);

		// 가입 버튼
		JPanel joinpnl = new JPanel();
		JButton joinbtn = new JButton("가입");
		joinbtn.setEnabled(false);
		
		// 중복확인
		btnsame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int length = listInfor.size();
				// 입력한 값 받아두는 용도의 변수
				String[] infs = new String[length];
				for (int i = 0; i < length; i++) {
					infs[i] = listInfor.get(i).getText();
				}
				List<String> infor = new ArrayList<>();
				infor = io.load();
				String[] getInfor = new String[3]; // 아이디, 비밀번호
				String[] getId = new String[infor.size()]; // id 전부

				for (int i = 0; i < infor.size(); i++) {
					getInfor = infor.get(i).split(","); // 아이디 비밀번호 잘라서
					getId[i] = getInfor[0]; // 아이디 넣기
				}
				int a = 0;
				for (int i = 0; i < getId.length; i++) {
					if (idtf.getText().equals(getId[i])) {
						JOptionPane.showMessageDialog(SignUp.this, "같은 아이디가 있습니다.");
						a++;
						break;
					}
				}
				for (int i = 0; i < getId.length; i++) {
					if (a != 1) {
						if (infs[0].isEmpty()) {
							JOptionPane.showMessageDialog(SignUp.this, "아이디를 입력하세요.");
							break;
						} else if (checkIDMethod(infs[0]) == 1) {
							JOptionPane.showMessageDialog(SignUp.this, "아이디는 특수문자 포함이 불가합니다");
							break;
						} else if (infs[0].length() < 4 || infs[0].length() > 12) {
							JOptionPane.showMessageDialog(SignUp.this, "아이디는 4자리 이상 , 12자리 이하만 입력 가능합니다.");
							break;
						} else {
							JOptionPane.showMessageDialog(SignUp.this, "사용가능한 아이디 입니다.");
							btnsame.setEnabled(false);
							joinbtn.setEnabled(true);
							idtf.setEnabled(false);
							break;
						}
					}
				}
			}
		});
		
		//가입버튼
		joinbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int length = listInfor.size();
				// 입력한 값 받아두는 용도의 변수
				String[] infs = new String[length];
				for (int i = 0; i < length; i++) {
					infs[i] = listInfor.get(i).getText();
				}

				// 중복 쳐내기
				List<String> infor = new ArrayList<>();
				infor = io.load();
				String[] getInfor = new String[3]; // 아이디, 비밀번호
				String[] getId = new String[infor.size()]; // id 전부
				String[] getPw = new String[infor.size()]; // 비밀번호 전부
				String[] getPn = new String[infor.size()]; // 전화번호 전부

				for (int i = 0; i < infor.size(); i++) {
					getInfor = infor.get(i).split(","); // 아이디 비밀번호 잘라서
					getId[i] = getInfor[0]; // 아이디 넣기
					getPw[i] = getInfor[1]; // 비밀번호 넣기
					getPn[i] = getInfor[2]; // 전화번호넣기
				}

				int a = 0;
				for (int i = 0; i < getId.length; i++) {
					if (idtf.getText().equals(getId[i])) {
						JOptionPane.showMessageDialog(SignUp.this, "같은 아이디가 있습니다.");
						a++;
						break;
					}
				}

				for (int i = 0; i < getPw.length; i++) {
					if (infs[1].length() < 4 || infs[1].length() > 12) {
						JOptionPane.showMessageDialog(SignUp.this, "비밀번호는 4자리 이상, 12자리 이하만 입력 가능합니다.");
						a++;
						break;
					} else if (checkPWDMethod(infs[1]) == 1) {
						JOptionPane.showMessageDialog(SignUp.this, "특수문자는 !@#만 포함 가능합니다.");
					}
				}

				// 중복이 있을땐 실행 x 없을땐 일반 실행
				if (a != 1 && a != 2) {
					if (infs[0].isEmpty()) {
						JOptionPane.showMessageDialog(SignUp.this, "아이디를 입력하세요");
					} else if (infs[1].isEmpty()) {
						JOptionPane.showMessageDialog(SignUp.this, "비밀번호를 입력하세요");
					} else if (infs[3].equals("010-****-****")) {
						JOptionPane.showMessageDialog(SignUp.this, "전화번호를 입력하세요");
					} else if (!infs[1].equals(infs[2])) {
						JOptionPane.showMessageDialog(SignUp.this, "비밀번호가 일치하지 않습니다");
					} else {
						JOptionPane.showMessageDialog(SignUp.this, "가입되었습니다.");
						io.save(infs[0], infs[1], infs[3]);
						idtf.setText("");
						pwtf.setText("");
						confirmtf.setText("");
						pntf.setText("");
						dispose();
					}
				}
			}
		});

		joinpnl.add(joinbtn);

		add(mainpnl, "North");
		add(joinpnl, "South");

		showGUI();
	}

	// 아이디 특수문자 불포함 메소드
	private int checkIDMethod(String id) {
		int check = 0;
		char alpha;
		int code;
		for (int i = 0; i < id.length(); i++) {
			alpha = id.charAt(i);
			code = (int) alpha;
			if (code >= 0 && code <= 47 || code >= 58 && code <= 63 || code >= 91 && code <= 96
					|| code >= 123 && code <= 127) {
				check = 1;
			}
		}
		return check;
	}

	// 비밀번호 특수문자 포함 메소드
	private int checkPWDMethod(String pwd) {
		int check = 0;
		char alpha;
		int code;
		for (int i = 0; i < pwd.length(); i++) {
			alpha = pwd.charAt(i);
			code = (int) alpha;
			if (code >= 0 && code <= 32 || code >= 36 && code <= 47 || code >= 58 && code <= 63
					|| code >= 91 && code <= 96 || code >= 123 && code <= 127) {
				check = 1;
			}
		}
		return check;
	}

	private void showGUI() {
		setModal(true);
		pack();
		setLocation(650, 350);
		setVisible(true);
	}
}
