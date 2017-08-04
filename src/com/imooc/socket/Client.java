package com.imooc.socket;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.imooc.entity.File;
import com.imooc.entity.User;
import com.imooc.util.CommendTransfer;

public class Client {
	private Scanner scan = new Scanner(System.in);
	private Socket socket = null;
	
	public static void main(String[] args) {
		Client client = new Client();
		client.mainMenu();
	}
	public void mainMenu() {
		System.out.println("******��ӭʹ��imooc�ϴ���*******");
		System.out.println("1 �û���¼  ��2 �û�ע�� ��3 �˳�");
		System.out.println("***************************");
		System.out.println("��ѡ�񣺡���������������");
		try {
			int choose = scan.nextInt();
			switch(choose) {
			case 1:
				showLogin();
				break;
			case 2:
				showRegister();
				break;
			case 3:
				System.out.println("��ӭ�´�ʹ�ã��ټ���");
				System.exit(0);
			default:
				System.out.println(" ��������");
				System.exit(0);
			}
		} catch(InputMismatchException e) {
			System.out.println("�����ʽ�������������룡");
			scan.next();
			mainMenu();
			return;
		}
	}
	
	//��½
	public void showLogin() {
		int count = 0;//��¼������½�Ĵ���
		User user = new User();
		CommendTransfer transfer = new CommendTransfer();
		while(true) {
			if (count >= 3) {
				System.out.println("���Ѿ���������ʧ�ܣ��ټ�!");
				System.exit(0);
			}
			System.out.println("��ӭʹ�õ�½");
			System.out.println("�������û���:");
			user.setUsername(scan.next());
			System.out.println("����������:");
			user.setPassword(scan.next());
			transfer.setCmd("login");
			transfer.setData(user);
			count++;
			try {
				socket = new Socket("localhost",1346);
				sendData(transfer);//���͵������
				transfer = getData();//���շ������Ӧ
				System.out.println(transfer.getResult());
				System.out.println("***********************");
				//�����½�ɹ�������ѭ��
				if(transfer.isFlag()) {
					break;
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		closeAll();
		showUploadFile();
	}
	
	//ע��
	public void showRegister() {
		User user = new User();
		CommendTransfer transfer = new CommendTransfer();
		System.out.println("��ӭע��!");
		while(true) {
			System.out.println("�������û���:");
			user.setUsername(scan.next());
			System.out.println("����������:");
			user.setPassword(scan.next());
			System.out.println("���ٴ���������:");
			String rePassword = scan.next();
			if(!user.getPassword().equals(rePassword)) {
				System.out.println("������������벻һ�£�");
				System.out.println("**************");
				continue;
			}
			transfer.setCmd("register");
			transfer.setData(user);
			try {
				socket = new Socket("localhost",1346);
				sendData(transfer);
				transfer = getData();
				System.out.println(transfer.getResult());
				System.out.println("***********************");
				if(transfer.isFlag()) {
					break;
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		closeAll();
		mainMenu();
	}
	
	//�ϴ��ļ�
	public void showUploadFile() {
		File file = new File();
		FileInputStream fis = null;
		System.out.println("�������ϴ��ľ���·�� �磺(e://imooc//dog.jpg)");
		String path = scan.next();
		String fname = path.substring(path.lastIndexOf("/") + 1);
		file.setFname(fname);
		try {
			fis = new FileInputStream(path);
			byte[] fcontent = new byte[fis.available()];
			file.setFcontent(fcontent);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		CommendTransfer transfer = new CommendTransfer();
		transfer.setCmd("uploadFile");
		transfer.setData(file);
		try {
			socket = new Socket("localhost",1346);
			sendData(transfer);
			transfer = getData();
			System.out.println(transfer.getResult());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
	}
	
	//���Ͷ��󵽿ͻ���
	public void sendData(CommendTransfer transfer) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(transfer);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//���ܿͻ�����Ӧ���͵Ķ���
	public CommendTransfer getData() {
		CommendTransfer transfer = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			transfer =(CommendTransfer)ois.readObject();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return transfer;
	}
	
	//�ر���Դ
	public void closeAll() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
