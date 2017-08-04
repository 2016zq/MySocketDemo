package com.imooc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.imooc.entity.File;
import com.imooc.util.Util;

/**
 * 文件服务类,实现客户端文件向服务器数据的上传
 * @author Qiong
 *
 */
public class FileService {
	private Connection conn = null;
	private PreparedStatement ptmt = null;
	private ResultSet rs = null;
	//上传文件
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
