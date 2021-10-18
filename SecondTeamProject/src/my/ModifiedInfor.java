package my;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ModifiedInfor extends JDialog {
	private Files io;
	private SignIn si;
	//로드, 세이브 누를떄 세이브
	public ModifiedInfor(String alcname, AdminInterface adminInterface, String id) {
		io = new Files();
		JTextPane tp = new JTextPane();
		tp.setCaretPosition(0);
		tp.setPreferredSize(new Dimension(400, 400));
		String content = io.inforload(alcname);
		tp.setText(content);
		
//		tp.getDocument()
		JScrollPane scrl = new JScrollPane(tp);
		scrl.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		
		JPanel btnpnl = new JPanel();
		JButton btnLoad = new JButton("사진 넣기");
		btnLoad.addActionListener(new ActionListener() {
			JFileChooser chooseImage = new JFileChooser();
			@Override
			public void actionPerformed(ActionEvent e) {
				// 열리는 곳 위치 조정
				chooseImage.setCurrentDirectory(new File("C:\\Users\\namki\\Desktop\\javaproject\\두번째 프로젝트\\image"));

				FileNameExtensionFilter ff = new FileNameExtensionFilter("이미지 파일", "jpg", "png");
				chooseImage.setFileFilter(ff);

				// "모든 파일" 텝 없애는 방법
				chooseImage.setAcceptAllFileFilterUsed(false);
				// 파일 여러개 선택하거나 하나만 선택할 수 있게 하는 방법
				chooseImage.setMultiSelectionEnabled(false);

				int result = chooseImage.showSaveDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File file = chooseImage.getSelectedFile();
					BufferedImage image = changeimage(file);
					Image scale = image.getScaledInstance(80, 80, Image.SCALE_DEFAULT);
					tp.insertIcon(new ImageIcon(scale));
					pack();
				}
				
			}
			private BufferedImage changeimage(File file) {
				BufferedImage image = null;
				try {
					image = ImageIO.read(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return image;
			}
		});
		
		JButton btnOk = new JButton("적용");
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				io = new Files();
				//정보, 아이디
				io.inforsave(alcname ,tp);
				io.helpsave(alcname, id);
				//로드하기
				adminInterface.tp.setText(io.inforload(alcname));
				adminInterface.people = io.helpload(alcname);
				adminInterface.thankslbl.setText("도움을 주신 분들 : " + adminInterface.people);
				//도움을 주신 분들에 넣기
				
				dispose();
			}
		});
		
		btnpnl.add(btnLoad);
		btnpnl.add(btnOk);
		
		add(scrl);
		add(btnpnl, "South");
		showGUI();
	}

	public ModifiedInfor(String alcname, UserInterface userInterface, String id) {
		io = new Files();
		JTextPane tp = new JTextPane();
		tp.setCaretPosition(0);
		tp.setPreferredSize(new Dimension(400, 400));
		String content = io.inforload(alcname);
		tp.setText(content);
		
//		tp.getDocument()
		JScrollPane scrl = new JScrollPane(tp);
		scrl.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		
		JPanel btnpnl = new JPanel();
		JButton btnLoad = new JButton("사진 넣기");
		btnLoad.addActionListener(new ActionListener() {
			JFileChooser chooseImage = new JFileChooser();
			@Override
			public void actionPerformed(ActionEvent e) {
				// 열리는 곳 위치 조정
				chooseImage.setCurrentDirectory(new File("C:\\Users\\namki\\Desktop\\javaproject\\두번째 프로젝트\\image"));

				FileNameExtensionFilter ff = new FileNameExtensionFilter("이미지 파일", "jpg", "png");
				chooseImage.setFileFilter(ff);

				// "모든 파일" 텝 없애는 방법
				chooseImage.setAcceptAllFileFilterUsed(false);
				// 파일 여러개 선택하거나 하나만 선택할 수 있게 하는 방법
				chooseImage.setMultiSelectionEnabled(false);

				int result = chooseImage.showSaveDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File file = chooseImage.getSelectedFile();
					BufferedImage image = changeimage(file);
					Image scale = image.getScaledInstance(80, 80, Image.SCALE_DEFAULT);
					tp.insertIcon(new ImageIcon(scale));
					pack();
				}
				
			}
			private BufferedImage changeimage(File file) {
				BufferedImage image = null;
				try {
					image = ImageIO.read(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return image;
			}
		});
		
		JButton btnOk = new JButton("적용");
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				io = new Files();
				//정보, 아이디
				io.waitinforsave(alcname ,tp);
				io.waithelpsave(alcname, id);
				JOptionPane.showMessageDialog(ModifiedInfor.this, "검토 후 적용됩니다.");
				//로드하기
				userInterface.tp.setText(io.inforload(alcname));
				//도움을 주신 분들에 넣기
				userInterface.people = io.helpload(alcname);
				dispose();
			}
		});
		
		btnpnl.add(btnLoad);
		btnpnl.add(btnOk);
		
		add(scrl);
		add(btnpnl, "South");
		showGUI();
	}

	private void showGUI() {
		setModal(true);
		pack();
		setVisible(true);
	}
}
