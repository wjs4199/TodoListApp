package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.PreparedStatement;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	// ������ �߰�
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
		if(list.addItem(t)>0) {
			System.out.println("�߰��Ǿ����ϴ�.");
		}
		
		System.out.println("");
	}

	// ������ ���� 
	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		
		System.out.print("\n"
				+ "[�׸� ����]\n"
				+ "������ �׸� ��ȣ > ");
		int num = sc.nextInt();
		
		if(l.deleteItem(num)>0) {
			System.out.println("�����Ǿ����ϴ�.");
		}
		
		System.out.println("");
	}

	// ������Ʈ - �� ���� �Է�
	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[�׸� ����]\n"
				+ "������ ���ϴ� �׸� ��ȣ > ");
		int num = sc.nextInt();
		
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
		String new_desc = sc.nextLine().trim();
		
		System.out.print("�� �������� > ");
		String new_due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(new_title, new_desc, new_category, new_due_date);
		t.setId(num);
		if(l.updateItem(t) > 0) 
			System.out.println("�����Ǿ����ϴ�.\n");
	}

	// ��ü����� ����ϴ� �Լ�
	public static void listAll(TodoList l) {
		System.out.printf("[��ü ���, �� %d��]\n", l.getCount());
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
		System.out.println("");
	}
	
	//���Ĺ���� ���� ���� �Ķ���ͷ� �޾Ƽ� ����Ʈ�� ����ϴ� �Լ�
	public static void listAll(TodoList l, String orderby, int ordering) {
		if(orderby.equals("ls_completed")) {
			int count = 0;
			for (TodoItem item : l.getCompletedList()) {
				System.out.println(item.toStringCompleted());
				count++;
			}
			System.out.printf("�� %d���� �׸��� ã�ҽ��ϴ�.\n\n",count);
		} 
		else {
			System.out.printf("[��ü ���, �� %d��]\n", l.getCount());
			for (TodoItem item : l.getOrderedList(orderby, ordering)) {
				System.out.println(item.toString());
			}
			System.out.println("");
		}
	}
	
	// ��� ī�װ� ��������
	public static void listCateAll(TodoList l){
			int count =0;
			for(String item: l.getCategories()) {
				System.out.print(item + " ");
				count++;
			}
			System.out.printf("\n�� %d���� ī�װ��� ��ϵǾ� �ֽ��ϴ�.\n\n", count);
		}
	
	/// keyword ã��
	public static void findList(TodoList l, String keyword) {
			int count =0;
			for (TodoItem item : l.getList(keyword)) {
				System.out.println(item.toString());
				count++;
			}
			System.out.printf("�� %d���� �׸��� ã�ҽ��ϴ�.\n\n", count);
		}

	// ī�װ� �˻��ϱ�
	public static void findCateList(TodoList l, String cate) {
			int count =0;
			for(TodoItem item : l.getListCategory(cate)) {
				System.out.println(item.toString());
				count ++;
			}
			System.out.println();
		}
	
	// �׸� �Ϸ��Ű��
	public static void completeItem(TodoList l, int number) {
		if(l.completeItem(number) > 0) {
			System.out.println("������ �׸��� �Ϸ� ǥ���߽��ϴ�.\n");
		}
	}
	
}
