package com.imooc.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) {
		try {
			@SuppressWarnings("resource")
			ServerSocket seversocket = new ServerSocket(1346);
			Socket socket = null;
			System.out.println("�������Ѿ��������ȴ��û�����");
			//ѭ�������ȴ��ͻ��˵�����
			while(true) {
				socket = seversocket.accept();
				System.out.println("��ӭ����");
				ServiceThread thread = new ServiceThread(socket);
				thread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
