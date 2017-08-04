package com.imooc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.imooc.entity.File;
import com.imooc.util.Util;

/**
 * �ļ�������,ʵ�ֿͻ����ļ�����������ݵ��ϴ�
 * @author Qiong
 *
 */
public class FileService {
	private Connection conn = null;
	private PreparedStatement ptmt = null;
	private ResultSet rs = null;
	//�ϴ��ļ�
	public void savefile(File file) {
		String sql = (" insert into tb_file(filename,fcontent) values(?,?)");
		try {
			conn = Util.getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, file.getFname());
			ptmt.setBytes(2, file.getFcontent());
			ptmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(conn, ptmt, rs);
		}
	}
}
