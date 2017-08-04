package com.imooc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.imooc.entity.User;
import com.imooc.util.Util;

public class UserService {
	private Connection conn = null;
	private PreparedStatement ptmt = null;
	private ResultSet rs = null;
	//ע��
	public void register(User user) {
		String sql = " insert into tb_user(username,password) values(?,?)";
		try {
			conn = Util.getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, user.getUsername());
			ptmt.setString(2, user.getPassword());
			ptmt.execute();
		} catch (SQLException e) {
			System.out.println("�û��������ظ�");
			e.printStackTrace();
		} finally {
			Util.closeAll(conn, ptmt, rs);
		}
	}
	//��½
	public Boolean login(User user) {
		String sql = " select username,password from tb_user where username=? and password=?";
		try {
			conn = Util.getConnection();
			ptmt = conn.prepareStatement(sql);
			ptmt.setString(1, user.getUsername());
			ptmt.setString(2, user.getPassword());
			rs = ptmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Util.closeAll(conn, ptmt, rs);
		}
			return false;
	}
}
