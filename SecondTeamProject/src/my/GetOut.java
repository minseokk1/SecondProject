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

public class GetOut extends JDialog {
	Files io;

	public GetOut() throws ParseException {
		io = new Files();
		
		// 정보 판넬
		JPanel mainpnl = new JPanel();
		mainpnl.setLayout(new BoxLayout(mainpnl, BoxLayout.Y_AXIS));

		// 아이디
		JPanel idpnl = new JPanel();
		JLabel idlbl = new JLabel("아이디");
		idlbl.setPreferredSize(new Dimension(100, 30));
		JTextField idtf = new JTextField(15);

		idpnl.add(idlbl);
		idpnl.add(idtf);

		// 비밀번호
		JPanel pwpnl = new JPanel();
		JLabel pwlbl = new JLabel("비밀번호");
		pwlbl.setPreferredSize(new Dimension(100, 30));
		JPasswordField pwtf = new JPasswordField(15);

		pwpnl.add(pwlbl);
		pwpnl.add(pwtf);

		JPanel pnlpn = new JPanel();
		MaskFormatter formatterPhone = new MaskFormatter("010-****-****");
		formatterPhone.setPlaceholderCharacter('*');
		JLabel pnlbl = new JLabel("연락처");
		pnlbl.setPreferredSize(new Dimension(100, 30));
		JFormattedTextField pntf = new JFormattedTextField(formatterPhone);
		pntf.setColumns(15);
		
		pnlpn.add(pnlbl);
		pnlpn.add(pntf);
		
		// 버튼
		JPanel btnpnl = new JPanel();
		JButton bye = new JButton("탈퇴");
		bye.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//맞게 쳤는지, 있는 정보인지 알아내는 과정
				List<String> right = new ArrayList<>();
				right = io.load();
				String [] getInf = new String[3];
				String [] getId = new String[right.size()];
				String [] getPw = new String[right.size()];
				String [] getPn = new String[right.size()];
				for(int i = 0; i < right.size(); i++) {
					getInf = right.get(i).split(",");
					getId[i] = getInf[0];
					getPw[i] = getInf[1];
					getPn[i] = getInf[2];
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
					JOptionPane.showMessageDialog(GetOut.this, "아이디를 입력하세요");
				} else if (a == getId.length) {
					JOptionPane.showMessageDialog(GetOut.this, "아이디가 존재하지 않습니다");
				} else if (!new String(pwtf.getPassword()).equals(getPw[a])) {
					JOptionPane.showMessageDialog(GetOut.this, "비밀번호가 틀렸습니다");
				} else if(!pntf.getText().equals(getPn[a])){
					JOptionPane.showMessageDialog(GetOut.this, "전화번호가 틀렸습니다");
				} else{
					//탈퇴과정
					List<String> list = new ArrayList<>();
					String line = idtf.getText() + "," + pwtf.getText() + "," + pntf.getText();
					list = io.searchbye(line);
					io.infsave(list);
					JOptionPane.showMessageDialog(GetOut.this, "탈퇴 되었습니다.");
					dispose();
				}
				}
		});
		btnpnl.add(bye);

		mainpnl.add(idpnl);
		mainpnl.add(pwpnl);
		mainpnl.add(pnlpn);
		mainpnl.add(btnpnl);
		
		add(mainpnl);

		showGUI();
	}

	private void showGUI() {
		setModal(true);
		pack();
		setVisible(true);
	}
}
