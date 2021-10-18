package my;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.ImageIcon;
import javax.swing.JTextPane;

public class Files {
	private final String INFOR = "information.txt";
	private final String ALCOHOL = "alcohol.txt";
	private final String ALCINF = "alcoholInformation.txt";
	private final String HELP = "help.txt";
	private final String CONFIRMALCOHOL = "confirmAlcohol.txt";
	private final String CONFIRMNAME = "confirmname.txt";
	private final String CONFIRMALCINF = "confirmAlcoholInformation.txt";
	private final String CONFIRMHELP = "confirmhelp.txt";

	// 회원가입 정보 저장
	void save(String name, String password, String PhoneNumber) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(INFOR, true));
			pw.print(name + ",");
			pw.print(password + ",");
			pw.println(PhoneNumber);
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

	// 회원가입 정보 불러오기
	public List<String> load() {
		BufferedReader br = null;
		List<String> list = new ArrayList<>();
		try {
			br = new BufferedReader(new FileReader(INFOR));

			String line;
			while ((line = br.readLine()) != null) {
				list.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	//탈퇴하고자하는 사람의 이름을 갖고 있다, 이거 빼고 써주기
	public List<String> searchbye(String line) {
		BufferedReader br = null;
		List<String> list = new ArrayList<>();
		try {
			br = new BufferedReader(new FileReader(INFOR));
			String bye;
			while((bye = br.readLine()) != null) {
				if(!line.equals(bye)) {
					list.add(bye);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	//탈퇴 후 남은 회원들의 정보를 다시 텍스트파일에 써주는 역할
	public void infsave(List<String> list) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(INFOR));
			for (int i = 0; i < list.size(); i++) {
				String[] getInf = list.get(i).split(",");
				String getId = getInf[0];
				String getPw = getInf[1];
				String getPn = getInf[2];
				pw.print(getId + ",");
				pw.print(getPw + ",");
				pw.println(getPn);  
			}
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

	// 관리자가 배너 추가할 때 파일 저장 기능(제목, 이미지)
	public void alcoholsave(String name, File file) {
		String path = ".\\alcohol\\" + name;
		File Folder = new File(path);

		if (!Folder.exists()) {
			Folder.mkdir();
		}

		PrintWriter pw = null;
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try {
			// 추가할 때 텍스트필드에 입력한 이름 alcohol.txt 에 저장
			pw = new PrintWriter(new FileWriter(ALCOHOL, true));
			pw.println(name);
			pw.flush();

			fis = new FileInputStream(file);
			fos = new FileOutputStream(path + "\\sajin.jpg");

			bis = new BufferedInputStream(fis);
			bos = new BufferedOutputStream(fos);

			// 파일 바이트로 저장하는 법
			int bytesRead = 0;
			byte[] buffer = new byte[(int) file.length()];
			while ((bytesRead = bis.read(buffer, 0, (int) file.length())) != -1) {
				bos.write(buffer, 0, (int) file.length());
			}
			bos.flush();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 저장 되어있는 술의 이름과 그에 맞는 사진 해쉬맵에 담아서 로드
	public HashMap<String, ImageIcon> alcoholload() {
		BufferedReader br = null;
		HashMap<String, ImageIcon> aclMap = new HashMap<>();
		try {
			br = new BufferedReader(new FileReader(ALCOHOL));

			String line;
			while ((line = br.readLine()) != null) {
				ImageIcon icon = new ImageIcon(".\\alcohol\\" + line + "\\sajin.jpg");
				aclMap.put(line, icon);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return aclMap;
	}

	// 유저가 술 배너를 추가하고 싶을 때 씀(관리자의 컨펌을 받기위한 폴더로 저장이 됨)
	public void useralcoholsave(String name, String id, File file) {
		String path = ".\\Confirm";
		File Folder = new File(path);

		if (!Folder.exists()) {
			Folder.mkdir();
		}

		PrintWriter pwalcohol = null;
		PrintWriter pwname = null;
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try {
			// 추가할 때 텍스트필드에 입력한 이름 alcohol.txt 에 저장
			pwalcohol = new PrintWriter(new FileWriter(CONFIRMALCOHOL, true));
			pwname = new PrintWriter(new FileWriter(CONFIRMNAME, true));
			pwalcohol.print(name + ",");
			pwalcohol.println(id);
			pwalcohol.flush();

			pwname.print(name + ",");
			pwname.println(id);
			pwname.flush();

			fis = new FileInputStream(file);
			fos = new FileOutputStream(path + "\\" + name + ".jpg");

			bis = new BufferedInputStream(fis);
			bos = new BufferedOutputStream(fos);

			// 파일 바이트로 저장하는 법
			int bytesRead = 0;
			byte[] buffer = new byte[(int) file.length()];
			while ((bytesRead = bis.read(buffer, 0, (int) file.length())) != -1) {
				bos.write(buffer, 0, (int) file.length());
			}
			bos.flush();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pwalcohol != null) {
				pwalcohol.close();
			}
			if (pwname != null) {
				pwname.close();
			}
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 확인 받고나서 확인 받은 술이름을 텍스트에서 지우기 위해 사용함
	public List<String> reload(String getAlcohol) {
		BufferedReader br = null;
		List<String> list = new ArrayList<>();
		try {
			br = new BufferedReader(new FileReader(CONFIRMALCOHOL));

			String line; // alcohol, id
			while ((line = br.readLine()) != null) {
				String[] getInf = line.split(",");
				String getNm = getInf[0];
				if (!getAlcohol.equals(getNm)) {
					list.add(line);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	// 컨펌을 받은 후 컨펌 받지 않은 친구들도 있을 수 있으니까 그것을 다시 컨펌텍스트에 써주는 작업
	public void resave(List<String> confirmsave) {
		PrintWriter pwalcohol = null;
		try {
			pwalcohol = new PrintWriter(new FileWriter(CONFIRMALCOHOL));
			for (int i = 0; i < confirmsave.size(); i++) {
				String[] getInf = confirmsave.get(i).split(",");
				String getAlc = getInf[0];
				pwalcohol.write(getAlc + "\n");
			}
			pwalcohol.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pwalcohol != null) {
				pwalcohol.close();
			}
		}
	}

	// 술 배너 들어가서 술 정보 텍스트에어리어에 있는 값들을 파일에 저장함
	public void inforsave(String alcname, JTextPane tp) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(".\\alcohol\\" + alcname + "\\" + ALCINF));
			pw.print(tp.getText());
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

	// 파일에 있는 값들을 텍스트페인에 로드시킬 때 사용함
	public String inforload(String alcname) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try {
			br = new BufferedReader(new FileReader(".\\alcohol\\" + alcname + "\\" + ALCINF));

			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	// 사용자가 글을 수정할 경우 세이브되는 임시 파일
	public void waitinforsave(String alcname, JTextPane tp) {
		PrintWriter pw = null;
		String path = ".\\alcohol\\" + alcname + "\\confirm";
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}
		try {
			pw = new PrintWriter(new FileWriter(path + "\\" + CONFIRMALCINF));
			pw.print(tp.getText());
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

	// 관리자가 사용자가 수정한 글을 허용했을 때 사용자가 쓴 글을 불러오기 위함
	public String waitinforload(String alcname) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try {
			br = new BufferedReader(new FileReader(".\\alcohol\\" + alcname + "\\confirm\\" + CONFIRMALCINF));

			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	// 텍스트페인 수정에 관여한 사람들의 아이디를 저장함
	public void helpsave(String alcname, String id) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(".\\alcohol\\" + alcname + "\\" + HELP, true));
			pw.println(id);
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

	// 저장한 '수정에 도움을 준 사람들' 리스트를 로드할 때 사용
	public Set<String> helpload(String alcname) {
		BufferedReader br = null;
		Set<String> people = new TreeSet<>();
		try {
			br = new BufferedReader(new FileReader(".\\alcohol\\" + alcname + "\\" + HELP));

			String line;
			while ((line = br.readLine()) != null) {
				people.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return people;
	}

	// 사용자가 글 수정했을때 '도움을 준사람들'에 추가되기 전에 저장되는 텍스트
	public void waithelpsave(String alcname, String id) {
		PrintWriter pw = null;
		String path = ".\\alcohol\\" + alcname + "\\confirm\\";
		try {
			pw = new PrintWriter(new FileWriter(path + CONFIRMHELP, true));
			pw.println(id);
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

	// 관리자가 허용 시 컨펌헬프를 읽어와서 헬프에 추가해주는 내용
	public void resavehelp(String alcname) {
		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			br = new BufferedReader(new FileReader(".\\alcohol\\" + alcname + "\\confirm\\" + CONFIRMHELP));
			pw = new PrintWriter(new FileWriter(".\\alcohol\\" + alcname + "\\" + HELP, true));
			String line;
			line = br.readLine();
			pw.println(line);
			pw.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// 배너 추가할 때 컨펌받기 위한 술의 이름과 이미지를 로드할 때 사용
	public HashMap<String, ImageIcon> waitalcoholload() {
		BufferedReader br = null;
		HashMap<String, ImageIcon> waitaclMap = new HashMap<>();
		try {
			br = new BufferedReader(new FileReader(CONFIRMALCOHOL));

			String line;
			while ((line = br.readLine()) != null) {
				String[] getInf = line.split(",");
				String getAlc = getInf[0];
//				String getId = getInf[1];
				ImageIcon icon = new ImageIcon(".\\Confirm\\" + getAlc + ".jpg");
				waitaclMap.put(getAlc, icon);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return waitaclMap;
	}

	// 관리자가 술 배너를 삭제하려고 할 때 삭제하고싶은 술 이름을 텍스트에서 지우고 남은 술 이름들을 가진 리스트
	public List<String> deletealcohol(String alcname) {
		BufferedReader br = null;
		List<String> returnlist = new ArrayList<>();
		try {
			br = new BufferedReader(new FileReader(ALCOHOL));
			String line;
			while ((line = br.readLine()) != null) {
				if (!alcname.equals(line)) {
					returnlist.add(line);
				} 
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnlist;
	}

	// 삭제하고 싶은 술을 빼고 난 술 이름 리스트를 다시 알코올 텍스트에 써 주기 위해
	public void resavealcohol(List<String> deletewant) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(ALCOHOL));
			for (int i = 0; i < deletewant.size(); i++) {
				pw.println(deletewant.get(i));
			}
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

	// 관리자가 배너를 허용하려고 할 때 허용하는 술의 이름을 가져오기 위함(컨펌알코올 텍스트에서)
	public String loadwantAlcohol() {
		BufferedReader br = null;
		String getAlcohol = null;
		try {
			br = new BufferedReader(new FileReader(CONFIRMALCOHOL));
			String line = br.readLine();
			String[] getInf = line.split(",");
			String getAlcName = getInf[0];
			getAlcohol = getAlcName;

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return getAlcohol;
	}

	// confirmname텍스트 값 전부 가져옴
	public List<String> loadconfirmname() {
		BufferedReader br = null;
		List<String> loadname = new ArrayList<>();
		try {
			br = new BufferedReader(new FileReader(CONFIRMNAME));
			String line;

			while ((line = br.readLine()) != null) {
				loadname.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return loadname;
	}

	// confirmalcohol 값 전부 가져옴
	public List<String> loadconfirmAlcohol() {
		BufferedReader br = null;
		List<String> loadname = new ArrayList<>();
		try {
			br = new BufferedReader(new FileReader(CONFIRMALCOHOL));
			String line;

			while ((line = br.readLine()) != null) {
				String[] getInf = line.split(",");
				String getId = getInf[1];
				loadname.add(getId);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return loadname;
	}

	// 써져 있는 글을 그 술의 alcoholInformation에 써주기 위함
	public void resaveInformation(String alcname, String content) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(".\\alcohol\\" + alcname + "\\" + ALCINF));
			pw.write(content);
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

}
