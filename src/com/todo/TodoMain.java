package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean isList = false;
		boolean quit = false;
		TodoUtil.loadList(l, "todolist.txt");
		Menu.displaymenu();
		
		do {
			Menu.prompt();
			isList = false;
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

			case "ls_name_asc":
				System.out.println("���� ������ �����Ͽ����ϴ�.");
				l.sortByName();
				isList = true;
				break;

			case "ls_name_desc":
				System.out.println("���� �������� �����Ͽ����ϴ�.");
				l.sortByName();
				l.reverseList();
				isList = true;
				break;
				
			case "ls_date":
				l.sortByDate();
				isList = true;
				break;

			case "exit":
				quit = true;
				TodoUtil.saveList(l, "todolist.txt");
				break;
				
			case "help":
				Menu.displaymenu();
				break;

			default:
				System.out.println("��Ȯ�� ����� �Է����ּ���! (���� - help)\n");
				break;
			}
			
			if(isList) l.listAll();
		} while (!quit);
	}
}
