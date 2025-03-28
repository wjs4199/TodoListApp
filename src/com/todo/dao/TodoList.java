package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.StringTokenizer;

import com.todo.DbConnect;


import java.util.Collections;

public class TodoList {
	Connection conn;
	
	private List<TodoItem> list;

	public TodoList() {
		this.list = new ArrayList<TodoItem>();
		this.conn = DbConnect.getConnection();
	}

	 //************************* add / update / delete *************************//
	
	//데이터 추가하는 함수
	public int addItem(TodoItem t) {
		String sql = "insert into list (title, memo, current_date, due_date, budget, importance)" 
				+ " values (?,?,?,?,?,?);";
		String sqlCategory = "insert into category (cate)" 
				+ " values (?);";
		
		PreparedStatement pstmt;
		int count = 0;
		
		try { 
			//list 테이블에 넣기
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCurrent_date());
			pstmt.setString(4, t.getDue_date());
			pstmt.setInt(5, t.getBudget());
			pstmt.setInt(6, t.getImportance());
			count = pstmt.executeUpdate();
			
			pstmt = conn.prepareStatement(sqlCategory);
			pstmt.setString(1, t.getCategory());
			pstmt.executeUpdate();
			
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int deleteItem(int index) {
		String sql = "delete from list where id=?;";
		String sqlCategory = "delete from category where id=?;";
		
		PreparedStatement pstmt;
		int count =0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			count = pstmt.executeUpdate();
			
			//카테고리 테이블 해당데이터 삭제
			pstmt = conn.prepareStatement(sqlCategory);
			pstmt.setInt(1, index);
			pstmt.executeUpdate();
			
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	//정보를 수정하여 업데이트 하는 함수
	public int updateItem(TodoItem t) {
		String sql = "update list set title=?, memo=?, current_date=?, due_date=?, budget=?, importance=?"
				+ " where id = ?;";
		String sqlCategory = "update category set cate=?"
				+ " where id = ?;";
		
		PreparedStatement pstmt;
		int count =0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCurrent_date());
			pstmt.setString(4, t.getDue_date());
			pstmt.setInt(5, t.getBudget());
			pstmt.setInt(6, t.getImportance());
			pstmt.setInt(7, t.getId());
			count = pstmt.executeUpdate();
			
			//카테고리 테이블 업데이트
			pstmt = conn.prepareStatement(sqlCategory);
			pstmt.setString(1, t.getCategory());
			pstmt.setInt(2, t.getId());
			pstmt.executeUpdate();
			
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	
	//***************** list 가져오기/ 변수따라 오름or내림 차순으로 정렬 *****************//
	
	//리스트를 가져다 주는 함수
	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list INNER JOIN category on " 
					+ "list.id = category.id;";
					//"SELECT * FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("cate");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int budget = rs.getInt("budget");
				int importance = rs.getInt("importance");
				TodoItem t = new TodoItem(title, description, category, due_date, budget, importance );
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	///정령하기 원하는 내용을 파라미터로 받아 query에서 가져오는 함수
	public ArrayList<TodoItem> getListOrdered(String orderby, int ordering){
			ArrayList<TodoItem> list =  new ArrayList<TodoItem>();
			Statement stmt;
			try {
				stmt = conn.createStatement();
				String sql = "SELECT * FROM list INNER JOIN category on " 
						+ "list.id = category.id "
						+ "ORDER BY " + orderby;
				if(ordering == 0) 
					sql += " desc";
				ResultSet rs = stmt.executeQuery(sql);
				while(rs.next()) {
					int id = rs.getInt("id");
					String category = rs.getString("cate");
					String title = rs.getString("title");
					String description = rs.getString("memo");
					String due_date = rs.getString("due_date");
					String current_date = rs.getString("current_date");
					int budget = rs.getInt("budget");
					int importance = rs.getInt("importance");
					TodoItem t = new TodoItem(title, description, category, due_date, budget, importance);
					t.setId(id);
					t.setCurrent_date(current_date);
					list.add(t);
				}
				stmt.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
			return list;
	}
		
	//******************************* find 정렬 *******************************//	
	
	/// keyword를 포함하고 있는 title과 memo를 찾아서 그 항목 내보내기
	public ArrayList<TodoItem> getListKeyword (String keyword) {
			ArrayList<TodoItem> list = new ArrayList<TodoItem>();
			PreparedStatement pstmt;
			keyword = "%" + keyword + "%";
			try {
				String sql = "SELECT * FROM list INNER JOIN category on "
						+ "list.id = category.id " 
						+ "WHERE title like ? or memo like ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, keyword);
				pstmt.setString(2, keyword);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					int id = rs.getInt("id");
					String category = rs.getString("cate");
					String title = rs.getString("title");
					String description = rs.getString("memo");
					String due_date = rs.getString("due_date");
					String current_date = rs.getString("current_date");
					int budget = rs.getInt("budget");
					int importance = rs.getInt("importance");
					TodoItem t = new TodoItem(title, description, category, due_date, budget, importance);
					t.setId(id);
					t.setCurrent_date(current_date);
					list.add(t);
				}
				pstmt.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
			return list;
	}
	
	///db에서 카테고리가 keyword인것만 불러와 보여줌
	public ArrayList<TodoItem> getListCategory(String keyword) {
			ArrayList<TodoItem> list =new ArrayList<TodoItem>();
			PreparedStatement pstmt;
			try {
				String sql = "SELECT * FROM list INNER JOIN category on "
						+ "list.id = category.id " 
						+ "WHERE cate = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,keyword);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					int id = rs.getInt("id");
					String category = rs.getString("cate");
					String title = rs.getString("title");
					String description = rs.getString("memo");
					String due_date = rs.getString("due_date");
					String current_date = rs.getString("current_date");
					int budget = rs.getInt("budget");
					int importance = rs.getInt("importance");
					TodoItem t = new TodoItem(title, description, category, due_date, budget, importance);
					t.setId(id);
					t.setCurrent_date(current_date);
					list.add(t);
				}
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return list;
	}
	
	//******************************* 특정내용만 정렬 *******************************//	
	
	///db에서 카테고리만 
	public ArrayList<String> getCategories() {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT cate FROM category";
			ResultSet rs =stmt.executeQuery(sql);
			while(rs.next()) {
				String category = rs.getString("cate");
				list.add(category);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}


	// 완료된 항목만 리스트로 
	public ArrayList<TodoItem> getCompletedList() {
		ArrayList<TodoItem> list =  new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list INNER JOIN category on "
					+ "list.id = category.id " 
					+ "WHERE is_completed like 1";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("cate");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int budget = rs.getInt("budget");
				int importance = rs.getInt("importance");
				TodoItem t = new TodoItem(title, description, category, due_date, budget, importance);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	//*********************************** 기타 ***********************************//	
	
	public void listAll() {
		System.out.println("[전체 목록]");
		int count =0;
		for (TodoItem item : list) {
			System.out.println(((count+1) + ". [ " + item.getCategory() + " ] " + item.getTitle() + " - " + item.getDesc() + " - " + item.getDue_date() + " - " + item.getCurrent_date() + " - " + item.getBudget() + "원 - " + item.getImportance()));
			count++;
		}
		System.out.println("");
	}

	//저장된 db의 데이타 갯수를 가져오는 함수
	public int getCount() {
			Statement stmt;
			int count = 0;
			try {
				stmt = conn.createStatement();
				String sql = "SELECT count(id) FROM list;";
				ResultSet rs = stmt.executeQuery(sql);
				rs.next();
				count = rs.getInt("count(id)");
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return count;
	}
		
	public Boolean isDuplicate(String title) {
		for (TodoItem item : list) {
			if (title.equals(item.getTitle())) return true;
		}
		return false;
	}
	
	///완료시키는 함수
	public int completeItem(int number) {
			String sql = "update list set is_completed=?" + " where id=?;";
			PreparedStatement pstmt;
			int count=0;
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, 1);
				pstmt.setInt(2, number);
				count = pstmt.executeUpdate();
				pstmt.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
			return count;
		}
	
	//초기데이터 이전시키는 함수
	public void importData(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into list (title, memo, current_date, due_date, budget, importance)"
					+ " values (?,?,?,?,?,?);";
			//String sqlLastID = "select id from list WHERE title like ?;";
			String sqlCategory = "insert into category (cate)" 
					+ " values (?);";
			
			int records = 0;
			int countCate = 0;
			
			while((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String title = st.nextToken();
				String description = st.nextToken();
				String category = st.nextToken();
				String current_date = st.nextToken();
				String due_date = st.nextToken();
				String budget = st.nextToken();
				String importance = st.nextToken();
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, description);
				pstmt.setString(3, current_date);
				pstmt.setString(4, due_date);
				pstmt.setInt(5, Integer.parseInt(budget));
				pstmt.setInt(6, Integer.parseInt(importance));
				int count = pstmt.executeUpdate();
				if(count > 0) records++;
				
				
				//category 테이블에 넣기(중복 안되는 것만)
				pstmt = conn.prepareStatement(sqlCategory);
				pstmt.setString(1, category);
				countCate = pstmt.executeUpdate();
				
				pstmt.close();
			}
			System.out.println(records + " records read!!");
			br.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
