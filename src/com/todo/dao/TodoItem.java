package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
	
	private int id;
    private String title;
    private String desc;
    private String category;
    private String current_date;
    private String due_date;
    private int is_completed = 0;
    private int budget = 0;
    private int importance = 5;
    
  //***************************** 생성자 *****************************//

    //list에서 가져올때
	public TodoItem(String title, String desc, String category, String due_date){
        this.title=title;
        this.desc=desc;
        this.category = category;
        this.due_date = due_date;
    }
	
	public TodoItem(String title, String desc, String category, String due_date, int budget, int importance){
        this.title=title;
        this.desc=desc;
        this.category = category;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        this.current_date= format.format(new Date());
        this.due_date = due_date;
        this.budget = budget;
        this.importance = importance;
    }
	//***************************** getter / setter *****************************//
	
    public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getIs_completed() {
		return is_completed;
	}

	public void setIs_completed(int is_completed) {
		this.is_completed = is_completed;
	}

	public int getBudget() {
		return budget;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public int getImportance() {
		return importance;
	}

	public void setImportance(int importance) {
		this.importance = importance;
	}
	
	//***************************** toString() 관련 *****************************//

	public String toString() {
		return id + " ["+category+"] " + title + " - " + desc + " - " + due_date + " - " + current_date + " - " + budget + "원 - " + importance;
	}
	
	public String toStringCompleted() {
		return id + " ["+category+"] " + title + "[V] - " + desc + " - " + due_date + " - " + current_date + " - " + budget + "원 - " + importance;
	}
}

	