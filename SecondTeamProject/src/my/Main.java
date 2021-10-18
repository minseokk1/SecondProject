package my;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * 회원가입 : 회원정보(입력) - 이름, 아이디, 비밀번호, (이메일) ->  로그인(출력) : ID, Password 
 * 술 종류 창 : 양주,위스키,사케,소주,와인,맥주....
 * 입력된 파일 출력 : 특정 술의 정보를 담은 파일(이름, 종류, 특징, 대표 술, 같이 먹기 좋은 안주....)
 *					  수정 - 추가하고 싶은 정보를 추가할 수 있는 버튼  
 * 사용자 화면 : 이미지 누르면 뜨는 정보 파일 창 + 수정 + 참가 id 나타내는 라벨 - 수정 가능 
 * 수정한 활동 알려주는 방법 1) 관리자로 로그인했을 때, 추가되거나 수정된 내용 알림 바로 뜨게 하기(파일 이동)
 * 							 2) 관리자로 로그인했을 때, JOptionPane로 다이얼로그 띄우기 
 *							 3) 별로 도움안될 때, 거부하기 버튼 추가 
 *							 4) 관리자가 허용했을 때, 사용자에게 수정 허용 여부를 알려주기 
 * 관리자 화면 : 술 종류 창 + 술 종류 추가할 수 있는 버튼 + 수정된 정보 허용해주는 버튼 - 수정 허용, 거부만 가능
 */

public class Main extends JFrame {
	static Main main;
	public Main() {
		//Look and Fill 이란 라이브러리 받아서 적용만 시키면 스킨처럼 이쁘게 무언가 입힐 수 있다.
		
		// 이미지 넣을 곳
		JPanel imagepnl = new JPanel();
		imagepnl.setLayout(new BoxLayout(imagepnl, BoxLayout.Y_AXIS));
		imagepnl.setBackground(Color.white);
		imagepnl.add(Box.createVerticalGlue());
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image logo = toolkit.getImage("image/Logo.jpg");
		Image scale = logo.getScaledInstance(250, 250, Image.SCALE_DEFAULT);
		JLabel imglbl = new JLabel(new ImageIcon(scale));
		imglbl.setAlignmentX(0.5F);
		imagepnl.add(imglbl);
		imagepnl.add(Box.createVerticalGlue());
		
		// 로그인이랑 회원가입 버튼 
		JPanel btnpnl = new JPanel();
		JButton signIn = new JButton("login");
		JButton signUp = new JButton("Sign Up");
		JButton bye = new JButton("회원 탈퇴");
		
		signIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new SignIn();
			}
		});
		signUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new SignUp();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
		});
		bye.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new GetOut();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnpnl.add(signIn);
		btnpnl.add(signUp);
		btnpnl.add(bye);
		
		add(imagepnl, "Center");
		add(btnpnl, "South");
		
		showGUI();
	}
	private void showGUI() {
		setSize(500, 500);
//		pack();
		// 켰을 때 위치
		setLocation(650, 350);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	public static void main(String[] args) {
		main = new Main();
	}

}
