package com.training.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.training.model.BeverageMember;

public class BeverageMemberDao {

	private static BeverageMemberDao beverageMemberDao = new BeverageMemberDao();

	private BeverageMemberDao() {
	}
	
	public static BeverageMemberDao getBeverageMemberDao_Instance() {
		return beverageMemberDao;
	}
	
	
	public List<BeverageMember> queryAllBeverageMember(){
		List<BeverageMember> allMembersList=new ArrayList<>();
		
		try(Connection connectDB=DBConnectionFactory.getOracleDBConnection();
				Statement stmt=connectDB.createStatement()){
			String sql="select IDENTIFICATION_NO, PASSWORD, CUSTOMER_NAME "
					   + "from BEVERAGE_MEMBER";
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				String customerName=rs.getString("CUSTOMER_NAME");
				String userName=rs.getString("IDENTIFICATION_NO");
				String password=rs.getString("PASSWORD");
				BeverageMember beverageMember=new BeverageMember(customerName,userName,password);
				allMembersList.add(beverageMember);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return allMembersList;
	}
	
	public BeverageMember queryBeverageMember(String username) {
		BeverageMember queryBeverageMember=null;
		try(Connection connectDB=DBConnectionFactory.getOracleDBConnection()){
			String sql="select IDENTIFICATION_NO, PASSWORD, CUSTOMER_NAME "
					   + "from BEVERAGE_MEMBER "
					   + "where IDENTIFICATION_NO in ?";
			try(PreparedStatement pstmt=connectDB.prepareStatement(sql)){
				pstmt.setString(1, username);
				ResultSet rs=pstmt.executeQuery();
				while(rs.next()) {
					String customerName=rs.getString("CUSTOMER_NAME");
					String userName=rs.getString("IDENTIFICATION_NO");
					String password=rs.getString("PASSWORD");
					queryBeverageMember=new BeverageMember(customerName,userName,password);
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}	
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return queryBeverageMember;
	}
	
	public BeverageMember queryBeverageMember(BeverageMember beverageMember) {
		BeverageMember queryBeverageMember=null;
		try(Connection connectDB=DBConnectionFactory.getOracleDBConnection()){
			String sql="select IDENTIFICATION_NO, PASSWORD, CUSTOMER_NAME "
					   + "from BEVERAGE_MEMBER "
					   + "where IDENTIFICATION_NO in ? and PASSWORD in ?";
			try(PreparedStatement pstmt=connectDB.prepareStatement(sql)){
				pstmt.setString(1, beverageMember.getUserName());
				pstmt.setString(2, beverageMember.getPassword());
				ResultSet rs=pstmt.executeQuery();
				while(rs.next()) {
					String customerName=rs.getString("CUSTOMER_NAME");
					String userName=rs.getString("IDENTIFICATION_NO");
					String password=rs.getString("PASSWORD");
					queryBeverageMember=new BeverageMember(customerName,userName,password);
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}	
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return queryBeverageMember;
	}
	
	public boolean createBeverageMember(BeverageMember beverageMember) {
		boolean createResult=false;
		try(Connection connectDB=DBConnectionFactory.getOracleDBConnection()){
			connectDB.setAutoCommit(false);
			String sql="insert into BEVERAGE_MEMBER(IDENTIFICATION_NO, PASSWORD, CUSTOMER_NAME)"
					   + "values(?,?,?)";
			try(PreparedStatement pstmt=connectDB.prepareStatement(sql)){
				int number=1;
				pstmt.setString(number++, beverageMember.getUserName());
				pstmt.setString(number++, beverageMember.getPassword());
				pstmt.setString(number++, beverageMember.getCustomerName());
				int record=pstmt.executeUpdate();
				if(record>=1) createResult=true;
				connectDB.commit();
			}catch(SQLException e) {
				connectDB.rollback();
				e.printStackTrace();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return createResult;
	}
	
	public int updateBeverageMember(BeverageMember beverageMember) {
		int updateRecord=0;
		try(Connection connectDB=DBConnectionFactory.getOracleDBConnection()){
			connectDB.setAutoCommit(false);
			String sql="update BEVERAGE_MEMBER "
					   + "set PASSWORD=?, CUSTOMER_NAME=? "
					   + "where IDENTIFICATION_NO in ?";
			try(PreparedStatement pstmt=connectDB.prepareStatement(sql)){
				int number=1;
				pstmt.setString(number++, beverageMember.getPassword());
				pstmt.setString(number++, beverageMember.getCustomerName());
				pstmt.setString(number++, beverageMember.getUserName());
				updateRecord=pstmt.executeUpdate();
				connectDB.commit();
			}catch(SQLException e) {
				connectDB.rollback();
				e.printStackTrace();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return updateRecord;
	}
	
	public int deleteBeverageMember(String userName) {
		int deleteRecord=0;
		String renewUserName="'" + userName + "'";
		try(Connection connectDB=DBConnectionFactory.getOracleDBConnection();
				Statement stmt=connectDB.createStatement()){
			connectDB.setAutoCommit(false);
			String sql="delete from BEVERAGE_MEMBER "
					   + "where IDENTIFICATION_NO in "+ renewUserName;
			deleteRecord=stmt.executeUpdate(sql);
			connectDB.commit();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return deleteRecord;
	}
}
