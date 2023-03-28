package com.training.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.training.model.Goods;
import com.training.model.GoodsOrder;

public class FrontendDao {

	private static FrontendDao frontendDao = new FrontendDao();

	private FrontendDao() {
	}

	public static FrontendDao getFrontendDao_Instance() {
		return frontendDao;
	}

//	public  String assemble(Set<String> set) {
//		return set.stream()
//				  .collect(Collectors.joining(",","(",")"));
//	}

	public String assemble(Set<Goods> set) {
		return set.stream().map(goods -> goods.getGoodsID().toString())
						   .collect(Collectors.joining(",", "(", ")"));
	}
	
	public List<Goods> queryAllGoods() {
		List<Goods> queryAllGoods = new ArrayList<>();

		try (Connection connectDB = DBConnectionFactory.getOracleDBConnection();
				Statement stmt = connectDB.createStatement()) {
			String sql = "select GOODS_ID, GOODS_NAME, DESCRIPTION, PRICE, "
						 + "QUANTITY, IMAGE_NAME, STATUS "
						 + "from BEVERAGE_GOODS";
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

	public List<Goods> searchGoods(String searchKeyword) {
		List<Goods> searchList = new ArrayList<>();
		try (Connection connectDB = DBConnectionFactory.getOracleDBConnection()) {
			String sql = "select GOODS_ID, GOODS_NAME, DESCRIPTION, PRICE, "
						 + "QUANTITY, IMAGE_NAME, STATUS "
						 + "from BEVERAGE_GOODS "
						 + "where regexp_like(GOODS_NAME,?,'i')";
			try (PreparedStatement pstmt = connectDB.prepareStatement(sql)) {
				int number = 1;
				pstmt.setString(number++, searchKeyword);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					BigDecimal goodsID = rs.getBigDecimal("GOODS_ID");
					String goodsName = rs.getString("GOODS_NAME");
					String goodsDescription = rs.getString("DESCRIPTION");
					int goodsPrice = rs.getInt("PRICE");
					int goodsQuantity = rs.getInt("QUANTITY");
					String goodsImageName = rs.getString("IMAGE_NAME");
					String goodsStatus = rs.getString("STATUS");
					Goods goods = new Goods(goodsID, goodsName, goodsDescription, goodsPrice, goodsQuantity,
							goodsImageName, goodsStatus);
					searchList.add(goods);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return searchList;
	}

	public List<Goods> searchGoods(String searchKeyword, int start, int end) {
		List<Goods> searchList = new ArrayList<>();
		try (Connection connectDB = DBConnectionFactory.getOracleDBConnection()) {
			String sql = "select * from (" 
						 + "select ROWNUM ROW_NUM, GOODS_ID, GOODS_NAME, DESCRIPTION, PRICE, "
						 + "QUANTITY, IMAGE_NAME, STATUS " + "from BEVERAGE_GOODS " 
						 + "where regexp_like(GOODS_NAME,?,'i')"
						 + ")"
						 + "where ROW_NUM between ? and ?";
			try (PreparedStatement pstmt = connectDB.prepareStatement(sql)) {
				int number = 1;
				pstmt.setString(number++, searchKeyword);
				pstmt.setInt(number++, start);
				pstmt.setInt(number++, end);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					BigDecimal goodsID = rs.getBigDecimal("GOODS_ID");
					String goodsName = rs.getString("GOODS_NAME");
					String goodsDescription = rs.getString("DESCRIPTION");
					int goodsPrice = rs.getInt("PRICE");
					int goodsQuantity = rs.getInt("QUANTITY");
					String goodsImageName = rs.getString("IMAGE_NAME");
					String goodsStatus = rs.getString("STATUS");
					Goods goods = new Goods(goodsID, goodsName, goodsDescription, goodsPrice, goodsQuantity,
							goodsImageName, goodsStatus);
					searchList.add(goods);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return searchList;
	}

	public Goods searchGoodsID(String frontEndgoodsID) {
		Goods goods = null;
		String renewGoodsID = "'" + frontEndgoodsID + "'";
		try (Connection connectDB = DBConnectionFactory.getOracleDBConnection();
				Statement stmt = connectDB.createStatement()) {
			String sql = "select GOODS_ID, GOODS_NAME, DESCRIPTION, PRICE, "
						 + "QUANTITY, IMAGE_NAME, STATUS "
						 + "from BEVERAGE_GOODS "
						 + "where GOODS_ID in " + renewGoodsID;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				BigDecimal goodsID = rs.getBigDecimal("GOODS_ID");
				String goodsName = rs.getString("GOODS_NAME");
				String goodsDescription = rs.getString("DESCRIPTION");
				Integer goodsPrice = rs.getBigDecimal("PRICE").intValue();
				Integer goodsQuantity = rs.getBigDecimal("Quantity").intValue();
				String goodsImageName = rs.getString("IMAGE_NAME");
				String goodsStatus = rs.getString("STATUS");
				goods = new Goods(goodsID, goodsName, goodsDescription, goodsPrice, goodsQuantity, goodsImageName,
						goodsStatus);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return goods;
	}
	
	public Map<Goods,Integer> renewGoodsInfo(Map<Goods,Integer> shoppingCartGoods) {
		Map<Goods,Integer> renew=new LinkedHashMap<>();
		try (Connection connectDB = DBConnectionFactory.getOracleDBConnection();
				Statement stmt = connectDB.createStatement()) {
			String sql = "select GOODS_ID, GOODS_NAME, DESCRIPTION, PRICE, "
						 + "QUANTITY, IMAGE_NAME, STATUS "
						 + "from BEVERAGE_GOODS "
						 + "where GOODS_ID in " + assemble(shoppingCartGoods.keySet());
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Goods goods=null;
				BigDecimal goodsID = rs.getBigDecimal("GOODS_ID");
				String goodsName = rs.getString("GOODS_NAME");
				String goodsDescription = rs.getString("DESCRIPTION");
				Integer goodsPrice = rs.getBigDecimal("PRICE").intValue();
				Integer goodsQuantity = rs.getBigDecimal("Quantity").intValue();
				String goodsImageName = rs.getString("IMAGE_NAME");
				String goodsStatus = rs.getString("STATUS");
				goods = new Goods(goodsID, goodsName, goodsDescription, goodsPrice, 
								  goodsQuantity, goodsImageName, goodsStatus);
				renew.put(goods,shoppingCartGoods.get(goods));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return renew;
	}
	

	public Set<Goods> eachPageGoods(int start, int end) {
		Set<Goods> pageGoods = new LinkedHashSet<>();
		try (Connection connectDB = DBConnectionFactory.getOracleDBConnection()) {
			String sql = "select * from ("
						 + "select ROWNUM ROW_NUM, GOODS_ID, GOODS_NAME, DESCRIPTION, "
						 + "PRICE, QUANTITY, IMAGE_NAME, STATUS " 
						 + "from BEVERAGE_GOODS " 
						 + ")"
						 + "where ROW_NUM between ? and ?";
			try (PreparedStatement pstmt = connectDB.prepareStatement(sql)) {
				pstmt.setInt(1, start);
				pstmt.setInt(2, end);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					BigDecimal goodsID = rs.getBigDecimal("GOODS_ID");
					String goodsName = rs.getString("GOODS_NAME");
					String goodsDescription = rs.getString("DESCRIPTION");
					Integer goodsPrice = rs.getInt("PRICE");
					Integer goodsQuantity = rs.getInt("QUANTITY");
					String goodsImageName = rs.getString("IMAGE_NAME");
					String goodsStatus = rs.getString("STATUS");
					Goods goods = new Goods(goodsID, goodsName, goodsDescription, goodsPrice, goodsQuantity,
							goodsImageName, goodsStatus);
					pageGoods.add(goods);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pageGoods;
	}

	public boolean createBeverageOrder(Map<Goods, Integer> orderGoods_CheckAndRenew, String customerID) {
		boolean createResult = false;
		try (Connection connectDB = DBConnectionFactory.getOracleDBConnection()) {
			connectDB.setAutoCommit(false);
			String sql = "insert into BEVERAGE_ORDER(" 
						 + "ORDER_ID, ORDER_DATE, CUSTOMER_ID, "
						 + "GOODS_ID, GOODS_BUY_PRICE, BUY_QUANTITY)"
						 + "values(BEVERAGE_ORDER_SEQ.NEXTVAL,TO_DATE(?,'YYYY-MM-DD HH24:MI:SS'),?,?,?,?)";
			try (PreparedStatement pstmt = connectDB.prepareStatement(sql)) {
				Set<Goods> set = orderGoods_CheckAndRenew.keySet();
				Iterator<Goods> iterator = set.iterator();
				while (iterator.hasNext()) {
					int number = 1;
					Goods goods = iterator.next();
					pstmt.setString(number++, GoodsOrder.formatDate(new Date()));
					pstmt.setString(number++, customerID);
					pstmt.setBigDecimal(number++, goods.getGoodsID());
					pstmt.setBigDecimal(number++, new BigDecimal(goods.getGoodsPrice()));
					pstmt.setBigDecimal(number++, new BigDecimal(orderGoods_CheckAndRenew.get(goods)));
					pstmt.addBatch();
				}
				int[] createData = pstmt.executeBatch();
				createResult = true;
				connectDB.commit();
			} catch (SQLException e) {
				connectDB.rollback();
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return createResult;
	}

	public boolean updateBeverageGoods(Set<Goods> updateGoods) {
		boolean updateResult=false;
		try(Connection connectDB=DBConnectionFactory.getOracleDBConnection()){
			connectDB.setAutoCommit(false);
			String sql="update BEVERAGE_GOODS set QUANTITY=? where GOODS_ID=?";
			try(PreparedStatement pstmt=connectDB.prepareStatement(sql)){
				for(Goods goods : updateGoods) {
					int number=1;
					pstmt.setBigDecimal(number++, new BigDecimal(goods.getGoodsQuantity()));
					pstmt.setBigDecimal(number++, goods.getGoodsID());
					pstmt.addBatch();
				}
				int[] updateRows=pstmt.executeBatch();
				updateResult=true;
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

}
