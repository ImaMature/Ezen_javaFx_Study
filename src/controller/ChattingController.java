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

	
	
	//1. Ŭ���̾�Ʈ ���� ����
	Socket socket;
	//2. Ŭ���̾�Ʈ ���� �޼ҵ�
	public void clientstart() {
		//��Ƽ ������ [������Ǯ X]
		Thread thread = new Thread() {
			@Override
			public void run() {
				//�����ϱ�
				try {
					socket = new Socket("127.168.102.50",1234); //���� ���Ͽ� ���ε��� ip�� port
					send(loginid + "���� ä�ù濡 �����ϼ̽��ϴ�.");
					receive();
				} catch (Exception e) {
					
				}
				
			}
		};
		thread.start(); //run �޼ҵ� ���� (������Ǯ�� �ƴ� ��� ���� ����)
	}
	//3. Ŭ���̾�Ʈ ���� �޼ҵ�
	public void clientstop() {
		try {
			socket.close(); //���� �ݱ� 
		} catch (Exception e) {
			
		}
	}
	//4. �޽��� ������ �޼ҵ�
	public void send(String msg) { //�Է¹�ư�� �����ų� Ȥ�� �ؽ�Ʈ â���� ���ʹ�������
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					OutputStream outputStream = socket.getOutputStream(); //1. ��½�Ʈ��
					outputStream.write(msg.getBytes()); // �μ��� ���� �޽����� ����Ʈ�� ��ȯ�ؼ� ��������
					outputStream.flush(); //�޸� �ʱ�ȭ
				} catch (Exception e) {
					
				}
			}
		};
		thread.start(); //Thread thread = new Thread() {}; �� �� ��ü
	}
	
	//5. �޽��� �ޱ� �޼ҵ�
	public void receive() {
		while(true) {
			try {
				InputStream inputStream = socket.getInputStream(); //1. �Է� ��Ʈ��
				byte[] bytes = new byte[100]; // ����Ʈ �迭 ����
				inputStream.read(bytes); //2. �Է½�Ʈ�� �� ����Ʈ�� �о����
				String msg = new String(bytes); // ����Ʈ�迭 -> ���ڿ� ����ȯ
				Platform.runLater(() -> {txtclient.appendText(msg);}); //�޾ƿ� �޽����� txtarea�� ����
				//appendText �ؽ�Ʈ�߰�
			} 
			catch (Exception e) {
				
			}
		}
	}
	
	private String loginid = MainpageController.getinstance().getloginid(); //�α��ε� ���̵��� �󺧰� �������� �޼ҵ� �����ؼ� loginid�� ����
	
	//6. �Է¹�ư�� ������ ��
	@FXML
	void msgsend (ActionEvent event) {
		//�޽��� ������
		send(loginid + " : " + txtcontents.getText() + "\n"); //�� �����ö� +"\n"�ؾߵ�.
		//������ ��
		txtcontents.setText("");
		txtcontents.requestFocus();
		
	}
	
	//7. ���͸� ������ ��
		@FXML
		void send (ActionEvent event) {
			//�޽��� ������
			send(loginid + " : " + txtcontents.getText() + "\n"); //�� �����ö� +"\n"�ؾߵ�.
			//������ ��
			txtcontents.setText("");
			txtcontents.requestFocus();
			
		}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txtclient.setEditable(false); //����ڰ� �ǵ��� ���ϰ� ���� ����ȭ
		txtcontents.setDisable(true); //�����ϱ� ���� ��� ����
		btninput.setDisable(true); //��� ����
		
	}
	
	@FXML
	private TextArea txtclient; //�ؽ�Ʈ �����
	
	@FXML
	private TextField txtcontents; //�ؽ�Ʈ �ʵ�
	
	@FXML
	private Button btninput;
	
	@FXML
	private Button btnconnect;
	
	@FXML
	void connect (ActionEvent event) {
		if(btnconnect.getText().equals("�����ϱ�")) {
			//1. Ŭ���̾�Ʈ ����
			clientstart();
			//2. ���� �޽��� ����
			Platform.runLater(() -> {
				txtclient.appendText("---[ä�ù� ����]---\n");});
			//3. ��Ʈ�� ���� ����
			btnconnect.setText("������");
			txtcontents.setDisable(false); //����ϰ� �����
			txtcontents.requestFocus(); //���콺 ������(Ŀ��, |)�� �̵� ��ư�� ������ ������ �����ٰ� ��
			btninput.setDisable(false); //��밡��
		}else {
			//1. Ŭ���̾�Ʈ ����
			clientstop();
			//2. ���� �޽��� ����
			Platform.runLater(() -> {txtclient.appendText("---[ä�ù� ����]---\n");});
			//3. ��Ʈ�� ���� ����
			btnconnect.setText("�����ϱ�");
			txtcontents.setDisable(true);
			btninput.setDisable(true);
		}
	}
}
