package com.todo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
		Connection con = null;
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		
		//db와 연결
		con = DbConnect.getConnection();
		
		//초기 데이터 이전됨
		l.importData("todolist.txt");
		
		boolean isList = false;
		boolean quit = false;
		Menu.displaymenu();
		
		do {
			Menu.prompt();
			String choice = sc.next();
		
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;
				
			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;

			case "ls_name":
				System.out.println("제목 순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "title", 1);
				break;

			case "ls_name_desc":
				System.out.println("제목 역순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "title", 0);
				break;
				
			case "ls_date":
				System.out.println("날짜 순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "due_date", 1);
				break;
				
			case "ls_date_desc":
				System.out.println("날짜 역순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "due_date", 0);
				break;
				
			case "ls_importance":
				System.out.println("중요도 순으로 정렬하였습니다.");
				TodoUtil.listAll(l, "importance", 1);
				break;
				
			case "ls_comp":
				System.out.println("완료된 항목만 출력합니다.");
				TodoUtil.listAll(l,"ls_completed", 0);
				break;
				
			case "find":
				String keyword = sc.nextLine().trim();
				TodoUtil.findList(l, keyword);
				break;
				
			case "find_cate":
				String cate = sc.nextLine().trim();
				TodoUtil.findCateList(l, cate);
				break;
				
			case "comp":
				String numbers = sc.nextLine();
				TodoUtil.completeItem(l,numbers);
				break;

			case "exit":
				quit = true;
				break;
				
			case "help":
				Menu.displaymenu();
				break;

			default:
				System.out.println("정확한 명령을 입력해주세요! (도움말 - help)\n");
				break;
			}
			
			if(isList) l.listAll();
		} while (!quit);
	}
}
