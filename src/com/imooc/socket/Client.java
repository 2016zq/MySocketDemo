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
		System.out.println("******欢迎使用imooc上传器*******");
		System.out.println("1 用户登录  ，2 用户注册 ，3 退出");
		System.out.println("***************************");
		System.out.println("请选择：》》》》》》》》");
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
				System.out.println("欢迎下次使用，再见！");
				System.exit(0);
			default:
				System.out.println(" 输入有误");
				System.exit(0);
			}
		} catch(InputMismatchException e) {
			System.out.println("输入格式有误！请重新输入！");
			scan.next();
			mainMenu();
			return;
		}
	}
	
	//登陆
	public void showLogin() {
		int count = 0;//记录连续登陆的次数
		User user = new User();
		CommendTransfer transfer = new CommendTransfer();
		while(true) {
			if (count >= 3) {
				System.out.println("您已经三次输入失败，再见!");
				System.exit(0);
			}
			System.out.println("欢迎使用登陆");
			System.out.println("请输入用户名:");
			user.setUsername(scan.next());
			System.out.println("请输入密码:");
			user.setPassword(scan.next());
			transfer.setCmd("login");
			transfer.setData(user);
			count++;
			try {
				socket = new Socket("localhost",1346);
				sendData(transfer);//发送到服务端
				transfer = getData();//接收服务端响应
				System.out.println(transfer.getResult());
				System.out.println("***********************");
				//如果登陆成功就跳出循环
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
	
	//注册
	public void showRegister() {
		User user = new User();
		CommendTransfer transfer = new CommendTransfer();
		System.out.println("欢迎注册!");
		while(true) {
			System.out.println("请输入用户名:");
			user.setUsername(scan.next());
			System.out.println("请输入密码:");
			user.setPassword(scan.next());
			System.out.println("请再次输入密码:");
			String rePassword = scan.next();
			if(!user.getPassword().equals(rePassword)) {
				System.out.println("两次输入的密码不一致！");
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
	
	//上传文件
	public void showUploadFile() {
		File file = new File();
		FileInputStream fis = null;
		System.out.println("请输入上传的绝对路径 如：(e://imooc//dog.jpg)");
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
	
	//发送对象到客户端
	public void sendData(CommendTransfer transfer) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(transfer);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//接受客户端响应发送的对象
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
	
	//关闭资源
	public void closeAll() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
