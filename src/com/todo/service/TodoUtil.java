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
	// 데이터 추가
	public static void createItem(TodoList list) {
		
		String title, desc, category, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n" + "[항목추가]\n"+"카테고리 > ");
		category = sc.next();
		
		System.out.print("항목 이름 > ");
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("중복된 이름이 있습니다!\n");
			return;
		}
		
		sc.nextLine();
		System.out.print("내용 > ");
		desc = sc.nextLine();
		
		System.out.print("마감일자(yyyy/mm/dd) > ");
		due_date = sc.next();
		
		TodoItem t = new TodoItem(title, desc, category, due_date);
		if(list.addItem(t)>0) {
			System.out.println("추가되었습니다.");
		}
		
		System.out.println("");
	}

	// 데이터 삭제 
	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		
		System.out.print("\n"
				+ "[항목 삭제]\n"
				+ "삭제할 항목 번호 > ");
		int num = sc.nextInt();
		
		if(l.deleteItem(num)>0) {
			System.out.println("삭제되었습니다.");
		}
		
		System.out.println("");
	}

	// 업데이트 - 새 내용 입력
	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[항목 수정]\n"
				+ "수정을 원하는 항목 번호 > ");
		int num = sc.nextInt();
		
		System.out.print("새 카테고리 이름 >");
		String new_category = sc.next().trim();
		
		System.out.print("새 항목 이름 >");
		String new_title = sc.next().trim();
		
		if (l.isDuplicate(new_title)) {
			System.out.println("중복된 이름이 있습니다!\n");
			return;
		}
		
		sc.nextLine();
		System.out.print("새 내용 > ");
		String new_desc = sc.nextLine().trim();
		
		System.out.print("새 마감일자 > ");
		String new_due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(new_title, new_desc, new_category, new_due_date);
		t.setId(num);
		if(l.updateItem(t) > 0) 
			System.out.println("수정되었습니다.\n");
	}

	// 전체목록을 출력하는 함수
	public static void listAll(TodoList l) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
		System.out.println("");
	}
	
	//정렬방법에 대한 것을 파라미터로 받아서 리스트를 출력하는 함수
	public static void listAll(TodoList l, String orderby, int ordering) {
		if(orderby.equals("ls_completed")) {
			int count = 0;
			for (TodoItem item : l.getCompletedList()) {
				System.out.println(item.toStringCompleted());
				count++;
			}
			System.out.printf("총 %d개의 항목을 찾았습니다.\n\n",count);
		} 
		else {
			System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
			for (TodoItem item : l.getOrderedList(orderby, ordering)) {
				System.out.println(item.toString());
			}
			System.out.println("");
		}
	}
	
	// 모든 카테고리 내보내기
	public static void listCateAll(TodoList l){
			int count =0;
			for(String item: l.getCategories()) {
				System.out.print(item + " ");
				count++;
			}
			System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n\n", count);
		}
	
	/// keyword 찾기
	public static void findList(TodoList l, String keyword) {
			int count =0;
			for (TodoItem item : l.getList(keyword)) {
				System.out.println(item.toString());
				count++;
			}
			System.out.printf("총 %d개의 항목을 찾았습니다.\n\n", count);
		}

	// 카테고리 검색하기
	public static void findCateList(TodoList l, String cate) {
			int count =0;
			for(TodoItem item : l.getListCategory(cate)) {
				System.out.println(item.toString());
				count ++;
			}
			System.out.println();
		}
	
	// 항목 완료시키기
	public static void completeItem(TodoList l, int number) {
		if(l.completeItem(number) > 0) {
			System.out.println("선택한 항목을 완료 표시했습니다.\n");
		}
	}
	
}
