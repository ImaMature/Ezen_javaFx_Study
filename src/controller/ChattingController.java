package controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import dao.MemberDao;
import domain.Member;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChattingController implements Initializable{

	
	
	//1. 클라이언트 소켓 선언
	Socket socket;
	//2. 클라이언트 시작 메소드
	public void clientstart() {
		//멀티 스레드 [스레드풀 X]
		Thread thread = new Thread() {
			@Override
			public void run() {
				//접속하기
				try {
					socket = new Socket("127.168.102.50",1234); //서버 소켓에 바인딩된 ip와 port
					send(loginid + "님이 채팅방에 입장하셨습니다.");
					receive();
				} catch (Exception e) {
					
				}
				
			}
		};
		thread.start(); //run 메소드 실행 (스레드풀이 아닌 경우 직접 실행)
	}
	//3. 클라이언트 종료 메소드
	public void clientstop() {
		try {
			socket.close(); //소켓 닫기 
		} catch (Exception e) {
			
		}
	}
	//4. 메시지 보내기 메소드
	public void send(String msg) { //입력버튼을 누르거나 혹은 텍스트 창에서 엔터눌렀을때
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					OutputStream outputStream = socket.getOutputStream(); //1. 출력스트림
					outputStream.write(msg.getBytes()); // 인수로 받은 메시지를 바이트형 변환해서 내보내기
					outputStream.flush(); //메모리 초기화
				} catch (Exception e) {
					
				}
			}
		};
		thread.start(); //Thread thread = new Thread() {}; 와 한 객체
	}
	
	//5. 메시지 받기 메소드
	public void receive() {
		while(true) {
			try {
				InputStream inputStream = socket.getInputStream(); //1. 입력 스트림
				byte[] bytes = new byte[100]; // 바이트 배열 선언
				inputStream.read(bytes); //2. 입력스트림 내 바이트를 읽어오기
				String msg = new String(bytes); // 바이트배열 -> 문자열 형변환
				Platform.runLater(() -> {txtclient.appendText(msg);}); //받아온 메시지를 txtarea에 띄우기
				//appendText 텍스트추가
			} 
			catch (Exception e) {
				
			}
		}
	}
	
	private String loginid = MainpageController.getinstance().getloginid(); //로그인된 아이디의 라벨값 가져오는 메소드 실행해서 loginid에 저장
	
	//6. 입력버튼을 눌렀을 때
	@FXML
	void msgsend (ActionEvent event) {
		//메시지 보내기
		send(loginid + " : " + txtcontents.getText() + "\n"); //꼭 가져올때 +"\n"해야됨.
		//보내기 후
		txtcontents.setText("");
		txtcontents.requestFocus();
		
	}
	
	//7. 엔터를 눌렀을 때
		@FXML
		void send (ActionEvent event) {
			//메시지 보내기
			send(loginid + " : " + txtcontents.getText() + "\n"); //꼭 가져올때 +"\n"해야됨.
			//보내기 후
			txtcontents.setText("");
			txtcontents.requestFocus();
			
		}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txtclient.setEditable(false); //사용자가 건들지 못하게 수정 금지화
		txtcontents.setDisable(true); //접속하기 전에 사용 금지
		btninput.setDisable(true); //사용 금지
		
	}
	
	@FXML
	private TextArea txtclient; //텍스트 에어리어
	
	@FXML
	private TextField txtcontents; //텍스트 필드
	
	@FXML
	private Button btninput;
	
	@FXML
	private Button btnconnect;
	
	@FXML
	void connect (ActionEvent event) {
		if(btnconnect.getText().equals("접속하기")) {
			//1. 클라이언트 실행
			clientstart();
			//2. 접속 메시지 전달
			Platform.runLater(() -> {
				txtclient.appendText("---[채팅방 접속]---\n");});
			//3. 컨트롤 내용 변경
			btnconnect.setText("나가기");
			txtcontents.setDisable(false); //사용하게 만들기
			txtcontents.requestFocus(); //마우스 포인터(커서, |)를 이동 버튼을 누르면 꺼졌다 켜졌다가 함
			btninput.setDisable(false); //사용가능
		}else {
			//1. 클라이언트 종료
			clientstop();
			//2. 퇴장 메시지 전달
			Platform.runLater(() -> {txtclient.appendText("---[채팅방 퇴장]---\n");});
			//3. 컨트롤 내용 변경
			btnconnect.setText("접속하기");
			txtcontents.setDisable(true);
			btninput.setDisable(true);
		}
	}
}
