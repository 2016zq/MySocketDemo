package com.imooc.entity;

import java.io.Serializable;

/*
 * 文件实体类
 */
public class File implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int fid;
	private String fname;
	private byte[] fcontent;
	private String username;//// 用户名，方便查看某个用户上传的文件
	
	//构造方法
	public File() {
		super();
	}
	public File(int fid,String fname,byte[] fcontent) {
		super();
		this.setFid(fid);
		this.setFname(fname);
		this.setFcontent(fcontent);
	}
	
	//getter和setter方法
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public byte[] getFcontent() {
		return fcontent;
	}
	public void setFcontent(byte[] fcontent) {
		this.fcontent = fcontent;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
