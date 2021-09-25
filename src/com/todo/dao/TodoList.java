package com.todo.dao;

import java.util.*;

import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

import java.util.Collections;

public class TodoList {
	private List<TodoItem> list;

	public TodoList() {
		this.list = new ArrayList<TodoItem>();
	}

	public void addItem(TodoItem t) {
		list.add(t);
	}

	public void deleteItem(TodoItem t) {
		list.remove(t);
	}

	void editItem(TodoItem t, TodoItem updated) {
		int index = list.indexOf(t);
		list.remove(index);
		list.add(updated);
	}

	public ArrayList<TodoItem> getList() {
		return new ArrayList<TodoItem>(list);
	}
	
	public void sortByName() {
		Collections.sort(list, new TodoSortByName());

	}
	
	public void sortByDate() {
		System.out.println("날짜 순으로 정렬하였습니다.");
		Collections.sort(list, new TodoSortByDate());
	}

	
	public void listAll() {
		System.out.println("[전체 목록]");
		int count =0;
		for (TodoItem item : list) {
			System.out.println(((count+1) + ". [ " + item.getCategory() + " ] " + item.getTitle() + " - " + item.getDesc() + " - " + item.getDue_date() + " - " + item.getCurrent_date()));
			count++;
		}
		System.out.println("");
	}
	
	public void reverseList() {
		Collections.reverse(list);
	}
	
	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}

	public Boolean isDuplicate(String title) {
		for (TodoItem item : list) {
			if (title.equals(item.getTitle())) return true;
		}
		return false;
	}
	
	public void findKeyword(String keyword) {
		int count = 0;
		int find = 0;
		for (TodoItem item : list) {
			if(item.getTitle().indexOf(keyword) > -1 || item.getDesc().indexOf(keyword) > -1 ) {
				find++;
				System.out.println(((count+1) + ". [ " + item.getCategory() + " ] " + item.getTitle() + " - " + item.getDesc() + " - " + item.getDue_date() + " - " + item.getCurrent_date()));
			}
			count++;
		}
		System.out.println("총 " + find + "개의 항목을 찾았습니다.\n");
	}
	
	public void findCategory(String keyword) {
		int count = 0;
		int find = 0;
		for (TodoItem item : list) {
			if(item.getCategory().indexOf(keyword) > -1) {
				find++;
				System.out.println(((count+1) + ". [ " + item.getCategory() + " ] " + item.getTitle() + " - " + item.getDesc() + " - " + item.getDue_date() + " - " + item.getCurrent_date()));
			}
			count++;
		}
		System.out.println("총 " + find + "개의 항목을 찾았습니다.\n");
	}

	public void listCategory() {
		HashSet<String> categoryList = new HashSet();
		int count = 0;
		boolean aleadyExist = false;
		
		for (TodoItem item : list) {
			for (String cate : categoryList) {
				if(item.getCategory().equals(cate)) {
					aleadyExist = true;
					break;
				}
				aleadyExist = false;
			}
			if(!aleadyExist) {
				categoryList.add(item.getCategory());
				count++;
			} 
		}
		
		int cate_count = 0;
		for(String cate : categoryList) {
			cate_count++;
			if(cate_count == count) {
				System.out.print(cate);
			} else {
				System.out.print(cate+ " / ");
			}
		}
		
		System.out.println("\n총 " + count + "개의 카테고리가 등록되어 있습니다.\n");
	}

	
}
