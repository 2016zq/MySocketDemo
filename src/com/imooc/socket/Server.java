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
			System.out.println("服务器已经启动，等待用户连接");
			//循环监听等待客户端的连接
			while(true) {
				socket = seversocket.accept();
				System.out.println("欢迎您！");
				ServiceThread thread = new ServiceThread(socket);
				thread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
