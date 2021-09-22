package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[항목추가]\n"
				+ "항목 이름 > ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("중복된 이름이 있습니다!");
			return;
		}
		
		sc.nextLine();
		System.out.print("내용 > ");
		desc = sc.nextLine();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		System.out.println("");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		
		System.out.print("\n"
				+ "[항목 삭제]\n"
				+ "삭제할 항목 이름 > ");
		String title = sc.next();
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("선택하신 항목이 삭제되었습니다!");
				break;
			}
		}
		System.out.println("");
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[항목 수정]\n"
				+ "수정을 원하는 항목 이름 > ");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("해당하는 항목 이름이 존재하지 않습니다.");
			return;
		}

		System.out.print("새로운 항목 이름 >");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("중복된 이름이 있습니다!");
			return;
		}
		
		sc.nextLine();
		System.out.print("내용 > ");
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("항목이 수정되었습니다!");
			}
		}
		System.out.println("");

	}

	public static void listAll(TodoList l) {
		System.out.println("[전체 목록]");
		for (TodoItem item : l.getList()) {
			System.out.println("[ " + item.getTitle() + " ] " + item.getDesc() + " - " + item.getCurrent_date());
		}
		System.out.println("");
	}
	
	// 여기서부터 step2 추가
	public static void saveList(TodoList l, String filename) {
		Writer w;
		try {
			w = new FileWriter(filename);
			
			for(TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();
			System.out.println("모든 데이터가 저장되었습니다.\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadList(TodoList l, String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			
			int count = 0;
			String temp_line;
			while((temp_line = br.readLine()) != null) {
				count ++;
				
				StringTokenizer st = new StringTokenizer(temp_line, "##");
				String name = st.nextToken();
				String desc = st.nextToken();
				String date = st.nextToken();
				
				TodoItem item = new TodoItem(name, desc, date);
				l.addItem(item);
			}
			
			br.close();
			System.out.println(count + "개의 항목을 읽었습니다.");
			
		} catch (FileNotFoundException e) {
			System.out.println(filename + " 파일이 없습니다.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
