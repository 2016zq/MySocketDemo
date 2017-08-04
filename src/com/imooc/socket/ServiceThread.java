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
	private ObjectInputStream ois = null;//对象输入流
	private ObjectOutputStream oos = null;//对象输出流
	private UserService us = new UserService();//用户业务对象
	private FileService fs = new FileService();//文件业务对象
	//构造方法
	public ServiceThread() {
	}
	public ServiceThread(Socket socket) {
		this.setSocket(socket);
	}
	//getter和setter方法
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
				transfer.setResult("登陆成功!");
			}
			else {
				transfer.setResult("用户名或密码不正确！");
			}
		}
		else if(cmd.equals("register")){
			User user = (User)transfer.getData();
			us.register(user);//注册
			boolean flag = us.login(user);//查询数据库是否有注册的信息
			transfer.setFlag(flag);
			if(flag) {
				transfer.setResult("注册成功!");
			}else {
				transfer.setResult("注册失败！");
			}
		}
		else if(cmd.equals("uploadFile")) {
			File file = (File)transfer.getData();
			fs.savefile(file);
			transfer.setResult("上传成功!");
		}
		return transfer;
	}
}


