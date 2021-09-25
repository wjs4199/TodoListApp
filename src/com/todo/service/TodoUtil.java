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
		
		String title, desc, category, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n" + "[�׸��߰�]\n"+"ī�װ� > ");
		category = sc.next();
		
		System.out.print("�׸� �̸� > ");
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("�ߺ��� �̸��� �ֽ��ϴ�!\n");
			return;
		}
		
		sc.nextLine();
		System.out.print("���� > ");
		desc = sc.nextLine();
		
		System.out.print("��������(yyyy/mm/dd) > ");
		due_date = sc.next();
		
		TodoItem t = new TodoItem(title, desc, category, due_date);
		list.addItem(t);
		System.out.println("");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		
		System.out.print("\n"
				+ "[�׸� ����]\n"
				+ "������ �׸� ��ȣ > ");
		int num = sc.nextInt();
		
		int count = 0; 
		for (TodoItem item : l.getList()) {
			if (num == (count+1)) {
				System.out.println(((count+1) + ". [ " + item.getCategory() + " ] " + item.getTitle() + " - " + item.getDesc() + " - " + item.getDue_date() + " - " + item.getCurrent_date()));
				System.out.print("�� �׸��� �����Ͻðڽ��ϱ�? (y/n) > ");
				String answer = sc.next();
				if(answer.equals("y")) {
					l.deleteItem(item);
					System.out.println("�����Ͻ� �׸��� �����Ǿ����ϴ�!");
					break;
				} else {
					break;
				}
			}
			count ++;
		}
		System.out.println("");
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[�׸� ����]\n"
				+ "������ ���ϴ� �׸� ��ȣ > ");
		int num = sc.nextInt();
		
		int count = 0;
		for (TodoItem item : l.getList()) {
			if(num == count+1) {
				System.out.println(((count+1) + ". [ " + item.getCategory() + " ] " + item.getTitle() + " - " + item.getDesc() + " - " + item.getDue_date() + " - " + item.getCurrent_date()));
				System.out.print("�� ī�װ� �̸� >");
				String new_category = sc.next().trim();
				
				System.out.print("�� �׸� �̸� >");
				String new_title = sc.next().trim();
				
				if (l.isDuplicate(new_title)) {
					System.out.println("�ߺ��� �̸��� �ֽ��ϴ�!\n");
					return;
				}
				
				sc.nextLine();
				System.out.print("�� ���� > ");
				String new_description = sc.nextLine().trim();
				
				System.out.print("�� �������� > ");
				String new_due_date = sc.nextLine().trim();
				
				int count2 = 0; 
				for (TodoItem i : l.getList()) {
					if (num == (count2+1)) {
						l.deleteItem(i);
						TodoItem t = new TodoItem(new_title, new_description, new_category, new_due_date);
						l.addItem(t);
						System.out.println("�׸��� �����Ǿ����ϴ�!");
					}
					count2 ++;
				}
				System.out.println("");
				return;
			}
			count ++;
		}

		System.out.println("�ش��ϴ� �׸� ��ȣ�� �������� �ʽ��ϴ�.\n");
			return;

	}

	public static void listAll(TodoList l) {
		int count = 0;
		for (TodoItem item : l.getList()) {
			count++;
		}
		System.out.println("[��ü ���, �� " + count + "��]");
		count = 0;
		for (TodoItem item : l.getList()) {
			System.out.println(((count+1) + ". [ " + item.getCategory() + " ] " + item.getTitle() + " - " + item.getDesc() + " - " + item.getDue_date() + " - " + item.getCurrent_date()));
			count++;
		}
		System.out.println("");
	}
	
	// ���⼭���� step2 �߰�
	
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
				String cate = st.nextToken();
				String due = st.nextToken();
				
				TodoItem item = new TodoItem(name, desc, date, cate, due);
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
}
