package com.imooc.entity;

import java.io.Serializable;

/*
 * 用户实体类
 */
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String username;
	private String password;
	
	//构造方法
	public User() {
		super();
	}
	public User(String username,String password) {
		super();
		this.setUsername(username);
		this.setPassword(password);
	}
	public User(int id,String username,String password) {
		super();
		this.setId(id);
		this.setUsername(username);
		this.setPassword(password);
	}
	
	//getter和setter方法
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
