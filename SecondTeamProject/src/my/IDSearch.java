package my;

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
import javax.swing.text.MaskFormatter;

public class IDSearch extends JDialog {
	private Files io;

	public IDSearch() throws ParseException {
		io = new Files();
		// 전체 값이 다들어있는 거
		JPanel mainpnl = new JPanel();
		mainpnl.setLayout(new BoxLayout(mainpnl, BoxLayout.Y_AXIS));

		JPanel pnlN = new JPanel();
		JLabel lblN = new JLabel("Phone Number");
		MaskFormatter formatterPhone = new MaskFormatter("010-####-####");
		formatterPhone.setPlaceholderCharacter('ㅁ');

		JFormattedTextField tfN = new JFormattedTextField(formatterPhone);
		pnlN.add(lblN);
		pnlN.add(tfN);

		JPanel btnpnl = new JPanel();
		JButton btnOK = new JButton("찾기");

		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> inFor = new ArrayList<>();
				inFor = io.load();
				// infor에 한 줄에 들어있는 정보를 ,로 잘라서 보관해줄 곳
				String[] getinFor = new String[3];
				// 폰넘버만 넣을 곳
				String[] getPN = new String[inFor.size()];
				// 아이디만 넣을 곳
				String[] getID = new String[inFor.size()];

				// getPN에 폰넘버가 저장되게하는 for문
				for (int i = 0; i < getPN.length; i++) {
					getinFor = inFor.get(i).split(",");
					getID[i] = getinFor[0];
					getPN[i] = getinFor[2];
				}
				String id = null;
				int a = 0;
				for (int i = 0; i < getPN.length; i++) {
					if (tfN.getText().equals(getPN[i])) {
						id = getID[i];
						JOptionPane.showMessageDialog(IDSearch.this, "아이디 : " + id + " 입니다.");
						a++;
						break;
					}
				}
				if (a != 1) {
					JOptionPane.showMessageDialog(IDSearch.this, "일치하는 정보가 없습니다.");
				}

			}
		});

		btnpnl.add(btnOK);

		add(pnlN);
		add(btnpnl, "South");

		showGUI();
	}

	private void showGUI() {
		setModal(true);
		pack();
		setVisible(true);
	}

}
