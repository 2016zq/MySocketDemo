package com.imooc.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.imooc.entity.File;
import com.imooc.entity.User;
import com.imooc.service.FileService;
import com.imooc.service.UserService;
import com.imooc.util.CommendTransfer;

public class ServiceThread extends Thread {
	private Socket socket = null;
	private ObjectInputStream ois = null;//����������
	private ObjectOutputStream oos = null;//���������
	private UserService us = new UserService();//�û�ҵ�����
	private FileService fs = new FileService();//�ļ�ҵ�����
	//���췽��
	public ServiceThread() {
	}
	public ServiceThread(Socket socket) {
		this.setSocket(socket);
	}
	//getter��setter����
	public Socket getSocket() {
		return this.socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			CommendTransfer transfer = (CommendTransfer)ois.readObject();
			transfer = excute(transfer);
			oos.writeObject(transfer);
			oos.flush();
		} catch (ClassNotFoundException e) {
				e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public CommendTransfer excute(CommendTransfer transfer) {
		String cmd = transfer.getCmd();
		if(cmd.equals("login")) {
			User user = (User)transfer.getData();
			boolean flag = us.login(user);
			transfer.setFlag(flag);
			if(flag) {
				transfer.setResult("��½�ɹ�!");
			}
			else {
				transfer.setResult("�û��������벻��ȷ��");
			}
		}
		else if(cmd.equals("register")){
			User user = (User)transfer.getData();
			us.register(user);//ע��
			boolean flag = us.login(user);//��ѯ���ݿ��Ƿ���ע�����Ϣ
			transfer.setFlag(flag);
			if(flag) {
				transfer.setResult("ע��ɹ�!");
			}else {
				transfer.setResult("ע��ʧ�ܣ�");
			}
		}
		else if(cmd.equals("uploadFile")) {
			File file = (File)transfer.getData();
			fs.savefile(file);
			transfer.setResult("�ϴ��ɹ�!");
		}
		return transfer;
	}
}


