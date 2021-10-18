package my;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CreateAlcohol extends JDialog {
	private Files io;
	private JTextField nametf;
	private JLabel img;
	private File file;
	private HashMap<String, ImageIcon> alcMap;

	// 관리자 꺼
	public CreateAlcohol(AdminKinds kinds, String id) {
		JPanel addpnl = new JPanel();
		nametf = new JTextField(15);
		img = new JLabel("사진 들어갈 곳");
		addpnl.add(nametf);
		addpnl.add(img);

		JPanel btnpnl = new JPanel();
		JButton load = new JButton("load");
		load.addActionListener(new addAlcohol());

		JButton save = new JButton("save");
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				io = new Files();

				alcMap = new HashMap<>();
				alcMap = io.alcoholload(); // 이미 여기엔 모든 값들이 들어있다.
				// key값 가져오기위한 셋
				Set<String> keySet = alcMap.keySet();
				Iterator<String> iter = keySet.iterator();
				// alcohol 리스트만 불러와서 중복 확인용
				String[] getAlcName = new String[alcMap.size()]; // 술 이름 전부

				for (int i = 0; i < alcMap.size(); i++) {
					String key = iter.next();
					getAlcName[i] = key; // 술 이름 넣기
				}

				int a = 0;
				for (int i = 0; i < getAlcName.length; i++) {
					if (nametf.getText().equals(getAlcName[i])) {
						JOptionPane.showMessageDialog(CreateAlcohol.this, "같은 술이 있당");
						a++;
						break;
					}
				}
				// 중복이 있을땐 실행 x 없을땐 일반 실행
				if (a == 1) {
				} else if (nametf.getText().isEmpty()) {
					JOptionPane.showMessageDialog(CreateAlcohol.this, "이름을 적어줭");
				} else if (img.getText().equals("사진 들어갈 곳")) {
					JOptionPane.showMessageDialog(CreateAlcohol.this, "사진을 넣어줭");
				} else {
					io.alcoholsave(nametf.getText(), file);
					kinds.addInforAlc(id);
					kinds.pack();
					dispose();
				}

			}
		});

		btnpnl.add(load);
		btnpnl.add(save);

		add(addpnl);
		add(btnpnl, "South");

		showGUI();
	}

	// 유저 꺼
	public CreateAlcohol(UserKinds userKinds, String id) {
		JPanel addpnl = new JPanel();
		nametf = new JTextField(15);
		img = new JLabel("사진 들어갈 곳");
		addpnl.add(nametf);
		addpnl.add(img);

		JPanel btnpnl = new JPanel();
		JButton load = new JButton("load");
		load.addActionListener(new addAlcohol());

		JButton save = new JButton("save");
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				io = new Files();

				alcMap = new HashMap<>();
				alcMap = io.alcoholload(); // 이미 여기엔 모든 값들이 들어있다.
				// key값 가져오기위한 셋
				Set<String> keySet = alcMap.keySet();
				Iterator<String> iter = keySet.iterator();
				// alcohol 리스트만 불러와서 중복 확인용
				String[] getAlcName = new String[alcMap.size()]; // 술 이름 전부

				for (int i = 0; i < alcMap.size(); i++) {
					String key = iter.next();
					getAlcName[i] = key; // 술 이름 넣기
				}

				int a = 0;
				for (int i = 0; i < getAlcName.length; i++) {
					if (nametf.getText().equals(getAlcName[i])) {
						JOptionPane.showMessageDialog(CreateAlcohol.this, "같은 술이 있당");
						a++;
						break;
					}
				}
				// 중복이 있을땐 실행 x 없을땐 일반 실행
				if (a == 1) {
				} else if (nametf.getText().isEmpty()) {
					JOptionPane.showMessageDialog(CreateAlcohol.this, "이름을 적어줭");
				} else if (img.getText().equals("사진 들어갈 곳")) {
					JOptionPane.showMessageDialog(CreateAlcohol.this, "사진을 넣어줭");
				} else {
					// 여기를 세이브만 하는 무언가를 만들어야 한다 경로도 따로 만들어야해서 파일스에 따로 해야할 듯
					io.useralcoholsave(nametf.getText(), id, file);
					
					JOptionPane.showMessageDialog(CreateAlcohol.this, "검토 후 추가 될 예정입니다");
					dispose();
				}

			}
		});

		btnpnl.add(load);
		btnpnl.add(save);

		add(addpnl);
		add(btnpnl, "South");

		showGUI();
		showGUI();
	}

	private void showGUI() {
		setModal(true);
		pack();
		setVisible(true);
	}

	class addAlcohol implements ActionListener {
		JFileChooser chooseImage = new JFileChooser();

		// 열리는 곳 지정 가능
		@Override
		public void actionPerformed(ActionEvent e) {
			io = new Files();
			// 열리는 곳 위치 조정
			chooseImage.setCurrentDirectory(new File("C:\\Users\\namki\\Desktop\\javaproject\\두번째 프로젝트\\image"));

			FileNameExtensionFilter ff = new FileNameExtensionFilter("이미지 파일", "jpg", "png");
			chooseImage.setFileFilter(ff);

			// "모든 파일" 텝 없애는 방법
			chooseImage.setAcceptAllFileFilterUsed(false);
			// 파일 여러개 선택할 수 있게 하는 방법
			chooseImage.setMultiSelectionEnabled(true);

			int result = chooseImage.showOpenDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
				file = chooseImage.getSelectedFile();
				BufferedImage image = changeimage(file);
				Image scale = image.getScaledInstance(80, 80, Image.SCALE_DEFAULT);
				img.setIcon(new ImageIcon(scale));
				img.setText("");
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

	}
}
