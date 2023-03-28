package com.training.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.training.formbean.CreateGoodsForm;
import com.training.formbean.UpdateGoodsForm;
import com.training.model.Goods;
import com.training.model.GoodsOrder;

public class BackendDao {

	private static BackendDao backendDao = new BackendDao();

	private BackendDao() {}

	public static BackendDao getBackendDao_Instance() {
		return backendDao;
	}
	
	public String assembleSql(Map<String,Object> fieldsMap) {
		StringBuilder addSql=new StringBuilder();
		int record=0;
		Set<String> set=fieldsMap.keySet();
		for(String fieldName:set) {
			switch(fieldName) {
				case "goodsID" :
					if(record==0) {
						addSql.append("GOODS_ID in "+fieldsMap.get(fieldName)+" ");
						record++;
					}else
						addSql.append("and GOODS_ID in "+fieldsMap.get(fieldName)+" ");
					break;
				case "goodsName" :
					if(record==0) {
						addSql.append("regexp_like(GOODS_NAME,'"+fieldsMap.get(fieldName)+"','i') ");
						record++;
					}else 
						addSql.append("and regexp_like(GOODS_NAME,'"+fieldsMap.get(fieldName)+"','i') ");
					break;
				case "priceStart" :
					if(record==0) {
						addSql.append("PRICE between "+fieldsMap.get(fieldName)+" ");
						record++;
					}else 
						addSql.append("and PRICE between "+fieldsMap.get(fieldName)+" ");
					break;
				case "priceEnd" :
					Integer queryStart=(Integer)fieldsMap.get(fieldName);
					if(queryStart!=0) 
						addSql.append("and "+fieldsMap.get(fieldName)+" ");
					break;
				case "priceQuantity" :
					if(record==0) {
						addSql.append("QUANTITY <= "+fieldsMap.get(fieldName)+" ");
						record++;
					}else 
						addSql.append("and QUANTITY <= "+fieldsMap.get(fieldName)+" ");
					break;
				case "status" :
					if(record==0) {
						addSql.append("STATUS in "+fieldsMap.get(fieldName)+" ");
						record++;
					}else
						addSql.append("and STATUS in "+fieldsMap.get(fieldName)+" ");
					break;
				case "priceSort" :
					addSql.append("order by PRICE "+fieldsMap.get(fieldName));
					break;
			}
		}
		return addSql.toString();
	}
	

	public List<Goods> queryAllGoods() {
		List<Goods> queryAllGoods = new ArrayList<>();

		try (Connection connectDB = DBConnectionFactory.getOracleDBConnection();
				Statement stmt = connectDB.createStatement()) {
			String sql = "select GOODS_ID, GOODS_NAME, DESCRIPTION, PRICE, "
							  + "QUANTITY, IMAGE_NAME, STATUS "+
						 "from BEVERAGE_GOODS";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				BigDecimal goodsID = rs.getBigDecimal("GOODS_ID");
				String goodsName = rs.getString("GOODS_NAME");
				String goodsDescription = rs.getString("DESCRIPTION");
				Integer goodsPrice = rs.getBigDecimal("PRICE").intValue();
				Integer goodsQuantity = rs.getBigDecimal("Quantity").intValue();
				String goodsImageName = rs.getString("IMAGE_NAME");
				String goodsStatus = rs.getString("STATUS");
				Goods eachGoods = new Goods(goodsID, goodsName, goodsDescription, goodsPrice, 
											goodsQuantity,goodsImageName, goodsStatus);
				queryAllGoods.add(eachGoods);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return queryAllGoods;
	}
	
	public Goods queryGoodsID(String goodsSelectedID) {
		Goods goods=null;
		String renewGoodsID="'" + goodsSelectedID + "'";
		try(Connection connectDB=DBConnectionFactory.getOracleDBConnection();
				Statement stmt=connectDB.createStatement()){
			String sql="select GOODS_ID, GOODS_NAME, DESCRIPTION, PRICE, "
					   + "QUANTITY, IMAGE_NAME, STATUS "
					   + "from BEVERAGE_GOODS "
					   + "where GOODS_ID in " + renewGoodsID;
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
			BigDecimal goodsID = rs.getBigDecimal("GOODS_ID");
			String goodsName = rs.getString("GOODS_NAME");
			String goodsDescription = rs.getString("DESCRIPTION");
			Integer goodsPrice = rs.getBigDecimal("PRICE").intValue();
			Integer goodsQuantity = rs.getBigDecimal("Quantity").intValue();
			String goodsImageName = rs.getString("IMAGE_NAME");
			String goodsStatus = rs.getString("STATUS");
			goods = new Goods(goodsID, goodsName, goodsDescription, goodsPrice, 
										goodsQuantity,goodsImageName, goodsStatus);
				
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return goods;
	}
	

	public boolean updateGoods(UpdateGoodsForm goods) {
		boolean updateResult=false;
		
		try(Connection connectDB=DBConnectionFactory.getOracleDBConnection()){
			connectDB.setAutoCommit(false);
			String sql="update BEVERAGE_GOODS "
						+ "set PRICE=? "
						+ ",QUANTITY=(select Quantity from BEVERAGE_GOODS where GOODS_ID in ?)+? "
						+ ",STATUS=? "
						+ "where GOODS_ID in ?";
			int number=1;
			try(PreparedStatement pstmt=connectDB.prepareStatement(sql)){
				pstmt.setBigDecimal(number++, new BigDecimal(goods.getGoodsPrice()));
				pstmt.setBigDecimal(number++, goods.getGoodsID());
				pstmt.setBigDecimal(number++, new BigDecimal(goods.getGoodsQuantity()));
				pstmt.setString(number++, goods.getGoodsStatus());
				pstmt.setBigDecimal(number++, goods.getGoodsID());
				int updateAmount=pstmt.executeUpdate();
				if(updateAmount>0) updateResult=true;
				connectDB.commit();
			}catch(SQLException e) {
				connectDB.rollback();
				e.printStackTrace();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return updateResult;
	}

	public int createGoods(CreateGoodsForm goods) {
		int goodsID=0;
		try(Connection connectDB=DBConnectionFactory.getOracleDBConnection()){
			connectDB.setAutoCommit(false);
			String sql="insert into BEVERAGE_GOODS(GOODS_ID,GOODS_NAME,PRICE,QUANTITY,IMAGE_NAME,STATUS) "
						+ "values(BEVERAGE_GOODS_SEQ.NEXTVAL,?,?,?,?,?)";
			String[] column=new String[] {"GOODS_ID"};
			int number=1;
			try(PreparedStatement pstmt=connectDB.prepareStatement(sql,column)){
				pstmt.setString(number++, goods.getGoodsName());
				pstmt.setBigDecimal(number++, new BigDecimal(goods.getGoodsPrice()));
				pstmt.setBigDecimal(number++, new BigDecimal(goods.getGoodsQuantity()));
				pstmt.setString(number++, goods.getGoodsImage().getFileName());
				pstmt.setString(number++, goods.getGoodsStatus());
				pstmt.executeUpdate();
				ResultSet rs=pstmt.getGeneratedKeys();
				ResultSetMetaData rsm=rs.getMetaData();
				while(rs.next()) {
					for(int i=1;i<=rsm.getColumnCount();i++) {
						goodsID=rs.getBigDecimal(i).intValue();
					}
				}
				connectDB.commit();
			}catch(SQLException e) {
				connectDB.rollback();
				e.printStackTrace();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return goodsID;
	}

	public Set<GoodsOrder> querySalesReport(String queryStartDate,String queryEndDate){
		Set<GoodsOrder> queryOrder=new LinkedHashSet<>();
		try(Connection connectDB=DBConnectionFactory.getOracleDBConnection();){
			String sql="select O.ORDER_ID, M.CUSTOMER_NAME, O.ORDER_DATE, G.GOODS_NAME, "
				        + "O.GOODS_BUY_PRICE,  O.BUY_QUANTITY, O.GOODS_BUY_PRICE*O.BUY_QUANTITY BUY_AMOUNT "
				        + "from BEVERAGE_ORDER O join BEVERAGE_MEMBER M "
				        + "on O.CUSTOMER_ID = M.IDENTIFICATION_NO " 
				        + "join BEVERAGE_GOODS G "
				        + "on O.GOODS_ID = G.GOODS_ID "
				        + "where ORDER_DATE between TO_DATE(?,'YYYY-MM-DD HH24:MI:SS') and TO_DATE(?,'YYYY-MM-DD HH24:MI:SS') "
				        + "order by ORDER_ID";
			try(PreparedStatement pstmt=connectDB.prepareStatement(sql)){
				pstmt.setString(1, queryStartDate+=" 00:00:00");
				pstmt.setString(2, queryEndDate+=" 23:59:59");
				ResultSet rs=pstmt.executeQuery();
				while(rs.next()) {
					BigDecimal orderID=rs.getBigDecimal("ORDER_ID");
					Date orderDate=new Date(rs.getTimestamp("ORDER_DATE").getTime());
					String customerName=rs.getString("CUSTOMER_NAME");
					String goodsName=rs.getString("GOODS_NAME");
					Integer goodsBuyPrice=rs.getInt("GOODS_BUY_PRICE");
					Integer buyQuantity=rs.getInt("BUY_QUANTITY");
					Integer buyAmount=rs.getInt("BUY_AMOUNT");
					queryOrder.add(new GoodsOrder(orderID,orderDate,customerName,
														  goodsName,goodsBuyPrice,
														  buyQuantity,buyAmount));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return queryOrder;
	}
	
	public List<Goods> queryAllWithCondition(Map<String,Object> fieldsMap){
		List<Goods> goodsList=new ArrayList<>();
		try(Connection connectDB=DBConnectionFactory.getOracleDBConnection();
				Statement stmt=connectDB.createStatement()){
			String sql="select ROWNUM ROW_NUM, GOODS_ID, GOODS_NAME, DESCRIPTION, "
						+ "PRICE, QUANTITY, IMAGE_NAME, STATUS "
						+ "from BEVERAGE_GOODS "
						+ "where "+assembleSql(fieldsMap);
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				BigDecimal goodsID = rs.getBigDecimal("GOODS_ID");
				String goodsName = rs.getString("GOODS_NAME");
				String goodsDescription = rs.getString("DESCRIPTION");
				Integer goodsPrice = rs.getBigDecimal("PRICE").intValue();
				Integer goodsQuantity = rs.getBigDecimal("Quantity").intValue();
				String goodsImageName = rs.getString("IMAGE_NAME");
				String goodsStatus = rs.getString("STATUS");
				Goods eachGoods = new Goods(goodsID, goodsName, goodsDescription, goodsPrice, 
											goodsQuantity,goodsImageName, goodsStatus);
				goodsList.add(eachGoods);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return goodsList;
	}

	public List<Goods> queryGoodsWithCondition(Map<String,Object> fieldsMap,int start,int end){
		List<Goods> goodsList=new ArrayList<>();
		try(Connection connectDB=DBConnectionFactory.getOracleDBConnection();){
			String sql="select * from ("
					+ "select ROWNUM ROW_NUM, GOODS_ID, GOODS_NAME, DESCRIPTION, "
					+ "PRICE, QUANTITY, IMAGE_NAME, STATUS "
					+ "from BEVERAGE_GOODS "
					+ "where "+assembleSql(fieldsMap)
					+ ")"
					+ "where ROW_NUM between ? and ?";

			try(PreparedStatement pstmt=connectDB.prepareStatement(sql)){
				pstmt.setInt(1, start);
				pstmt.setInt(2, end);
				ResultSet rs=pstmt.executeQuery();
				while(rs.next()) {
					BigDecimal goodsID = rs.getBigDecimal("GOODS_ID");
					String goodsName = rs.getString("GOODS_NAME");
					String goodsDescription = rs.getString("DESCRIPTION");
					Integer goodsPrice = rs.getBigDecimal("PRICE").intValue();
					Integer goodsQuantity = rs.getBigDecimal("Quantity").intValue();
					String goodsImageName = rs.getString("IMAGE_NAME");
					String goodsStatus = rs.getString("STATUS");
					Goods eachGoods = new Goods(goodsID, goodsName, goodsDescription, goodsPrice, 
												goodsQuantity,goodsImageName, goodsStatus);
					goodsList.add(eachGoods);
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return goodsList;
	}
	
	public Set<Goods> eachPageGoods(int start,int end){
		Set<Goods> pageGoods=new LinkedHashSet<>();
		try(Connection connectDB=DBConnectionFactory.getOracleDBConnection()){
			String sql="select * from ("
						+ "select ROWNUM ROW_NUM, GOODS_ID, GOODS_NAME, DESCRIPTION, "
						+ "PRICE, QUANTITY, IMAGE_NAME, STATUS "
						+ "from BEVERAGE_GOODS "
						+ ")"
						+ "where ROW_NUM between ? and ?";
			try(PreparedStatement pstmt=connectDB.prepareStatement(sql)){
				pstmt.setInt(1, start);
				pstmt.setInt(2, end);
				ResultSet rs=pstmt.executeQuery();
				while(rs.next()) {
					BigDecimal goodsID=rs.getBigDecimal("GOODS_ID");
					String goodsName=rs.getString("GOODS_NAME");
					String goodsDescription=rs.getString("DESCRIPTION");
					Integer goodsPrice=rs.getInt("PRICE");
					Integer goodsQuantity=rs.getInt("QUANTITY");
					String goodsImageName=rs.getString("IMAGE_NAME");
					String goodsStatus=rs.getString("STATUS");
					Goods goods=new Goods(goodsID,goodsName,goodsDescription,goodsPrice,
											goodsQuantity,goodsImageName,goodsStatus);
					pageGoods.add(goods);
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return pageGoods;
	}

}
