package com.imooc.entity;

import java.io.Serializable;

/*
 * �ļ�ʵ����
 */
public class File implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int fid;
	private String fname;
	private byte[] fcontent;
	private String username;//// �û���������鿴ĳ���û��ϴ����ļ�
	
	//���췽��
	public File() {
		super();
	}
	public File(int fid,String fname,byte[] fcontent) {
		super();
		this.setFid(fid);
		this.setFname(fname);
		this.setFcontent(fcontent);
	}
	
	//getter��setter����
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
