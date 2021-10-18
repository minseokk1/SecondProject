package my;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OkorNo extends JDialog {
	private Files io;

	public OkorNo(String alcname, Confirm confirm) {
		io = new Files();

		JPanel picAndNamepnl = new JPanel();
		ImageIcon img = new ImageIcon(".\\Confirm\\" + alcname + ".jpg");
		Image scale = img.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);
		JLabel namelbl = new JLabel(alcname);
		JLabel piclbl = new JLabel(new ImageIcon(scale));

		picAndNamepnl.add(namelbl);
		picAndNamepnl.add(piclbl);

		JPanel btnpnl = new JPanel();
		JButton btnOk = new JButton("허용");
		JButton btnNo = new JButton("거부");

		btnpnl.add(btnOk);
		btnpnl.add(btnNo);
		btnOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String getAlcohol = io.loadwantAlcohol();
				
				File f = new File(".\\Confirm\\" + getAlcohol + ".jpg");
				io.alcoholsave(f.getName().substring(0, f.getName().length() - 4), f);

				// 컨펌네임 값으로 허용되었다 보내기
				/**/
				f.delete();
				// 지우고자 하는 줄만 안 읽고 리턴해주는 리스트를 만들고
				List<String> confirmsave = new ArrayList<>();
				confirmsave = io.reload(getAlcohol);
				// 다시 컨펌알코올에 써주는 작업
				io.resave(confirmsave);
				confirm.addInforAlc();
				confirm.pack();
				dispose();
			}
		});
		btnNo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//컨펌폴더 안에 있는 사진, 컨펌알코올텍스트에 이름 지우기
				String getAlcohol = io.loadwantAlcohol();
				File f = new File(".\\Confirm\\" + getAlcohol + ".jpg");
				f.delete();
				
				List<String> Afterdelete = new ArrayList<>();
				Afterdelete = io.reload(getAlcohol);
				io.resave(Afterdelete);
				
				//컨펌네임으로 거부되었다 보내기
				/**/
				confirm.addInforAlc();
				confirm.pack();
				dispose();
			}
		});
		add(picAndNamepnl);
		add(btnpnl, "South");
		showGUI();
	}

	//삭제할 때
	public OkorNo(String alcname, Delete delete) {
		io = new Files();

		JPanel picAndNamepnl = new JPanel();
		ImageIcon img = new ImageIcon(".\\alcohol\\" + alcname + "\\sajin.jpg");
		Image scale = img.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT);
		JLabel namelbl = new JLabel(alcname);
		JLabel piclbl = new JLabel(new ImageIcon(scale));

		picAndNamepnl.add(namelbl);
		picAndNamepnl.add(piclbl);

		JPanel btnpnl = new JPanel();
		JButton btnDelete = new JButton("삭제");

		btnpnl.add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String path = ".\\alcohol\\" + alcname;
				File f = new File(path + "\\sajin.jpg");
				File f2 = new File(path + "\\help.txt");
				File f3 = new File(path + "\\alcoholInformation.txt");
				File f4 = new File(path + "\\confirmAlcoholInformation.txt");
				File f5 = new File(path + "\\confirmhelp.txt");
				File f6 = new File(path);
				// alcohol에 있는 그 이름도 없애야 한다.
				// 지울꺼만 뺴고 다시 읽어주는 것을 받을 리스트
				List<String> deletewant = new ArrayList<>();
				deletewant = io.deletealcohol(alcname);
				// 파일을 딜리트 해줬다. >> 폴더를 지우려면 안에 파일이 비어있어야 해서
				// 안에 사진을 먼저 지우고 폴더도 없애달라는 명령어를 썼다.
				if(f.exists()) {
					f.delete();
				}
				if(f2.exists()) {
					f2.delete();
				}
				if(f3.exists()) {
					f3.delete();
				}
				if(f4.exists()) {
					f4.delete();
				}
				if(f5.exists()) {
					f5.delete();
				}
				f6.delete();
				
				// 알코올.txt 에 다시 삭제 값 빼고 전부를 써주는 작업
				io.resavealcohol(deletewant);

				delete.addInforAlc();
				delete.pack();
				dispose();
			}
		});
		add(picAndNamepnl);
		add(btnpnl, "South");
		showGUI();
	}

	private void showGUI() {
		setModal(true);
		pack();
		setVisible(true);
	}
}
