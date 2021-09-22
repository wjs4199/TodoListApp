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
				+ "[�׸��߰�]\n"
				+ "�׸� �̸� > ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("�ߺ��� �̸��� �ֽ��ϴ�!");
			return;
		}
		
		sc.nextLine();
		System.out.print("���� > ");
		desc = sc.nextLine();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		System.out.println("");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		
		System.out.print("\n"
				+ "[�׸� ����]\n"
				+ "������ �׸� �̸� > ");
		String title = sc.next();
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("�����Ͻ� �׸��� �����Ǿ����ϴ�!");
				break;
			}
		}
		System.out.println("");
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[�׸� ����]\n"
				+ "������ ���ϴ� �׸� �̸� > ");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("�ش��ϴ� �׸� �̸��� �������� �ʽ��ϴ�.");
			return;
		}

		System.out.print("���ο� �׸� �̸� >");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("�ߺ��� �̸��� �ֽ��ϴ�!");
			return;
		}
		
		sc.nextLine();
		System.out.print("���� > ");
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("�׸��� �����Ǿ����ϴ�!");
			}
		}
		System.out.println("");

	}

	public static void listAll(TodoList l) {
		System.out.println("[��ü ���]");
		for (TodoItem item : l.getList()) {
			System.out.println("[ " + item.getTitle() + " ] " + item.getDesc() + " - " + item.getCurrent_date());
		}
		System.out.println("");
	}
	
	// ���⼭���� step2 �߰�
	public static void saveList(TodoList l, String filename) {
		Writer w;
		try {
			w = new FileWriter(filename);
			
			for(TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();
			System.out.println("��� �����Ͱ� ����Ǿ����ϴ�.\n");
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
			System.out.println(count + "���� �׸��� �о����ϴ�.");
			
		} catch (FileNotFoundException e) {
			System.out.println(filename + " ������ �����ϴ�.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
