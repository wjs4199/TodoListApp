package com.todo.service;

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
		for (TodoItem item : l.getList()) {
			System.out.println("[ " + item.getTitle() + " ] " + item.getDesc() + " - " + item.getCurrent_date());
		}
		System.out.println("");
	}
}
