package my;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;

public class ConfirmLylics extends JDialog {
	Files io;

	public ConfirmLylics(AdminInterface adminInterface, String id, String alcname) {
		io = new Files();
		
		JPanel mainpnl = new JPanel();
		mainpnl.setLayout(new BoxLayout(mainpnl, BoxLayout.Y_AXIS));
		
		JPanel lblpnl = new JPanel();
		JLabel lbl = new JLabel("수정을 원하는 글 내용!");
		lblpnl.add(lbl);
		
		JTextPane tp = new JTextPane();
		tp.setEditable(false);
		String content = io.waitinforload(alcname);
		tp.setText(content);
		tp.setCaretPosition(0);
		tp.setPreferredSize(new Dimension(400, 400));
		
		JScrollPane scrl = new JScrollPane(tp);
		scrl.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JPanel btnpnl = new JPanel();
		JButton Ok = new JButton("허용");
		Ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//컨펌알코올인폴메이션txt랑 컨펌헬프 동시 처리
				String pathinf = ".\\alcohol\\" + alcname + "\\confirm\\confirmAlcoholInformation.txt";
				String pathhelp = ".\\alcohol\\" + alcname + "\\confirm\\confirmhelp.txt";
				File f = new File(pathinf);
				File f2 = new File(pathhelp);
				io.resaveInformation(alcname, content);
				io.resavehelp(alcname);
				f.delete();
				f2.delete();
				dispose();
				
				adminInterface.tp.setText(io.inforload(alcname));
				adminInterface.people = io.helpload(alcname);
				adminInterface.thankslbl.setText("도움을 주신 분들 : " + adminInterface.people);
			}
		});
		JButton No = new JButton("거부");
		No.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String pathinf = ".\\alcohol\\" + alcname + "\\confirm\\confirmAlcoholInformation.txt";
				String pathhelp = ".\\alcohol\\" + alcname + "\\confirm\\confirmhelp.txt";

				File f = new File(pathinf);
				File f2 = new File(pathhelp);
				
				f.delete();
				f2.delete();
				dispose();
			}
		});
		btnpnl.add(Ok);
		btnpnl.add(No);
		
		mainpnl.add(lblpnl);
		mainpnl.add(scrl);
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
