package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("< ToDoList 관리 명령어 사용법 안내서 >");
        System.out.println("1. 항목 추가 - add ");
        System.out.println("2. 항목 삭제 - del ");
        System.out.println("3. 항목 수정  - edit ");
        System.out.println("4. 전체 목록 - ls ");
        System.out.println("5. 제목 순 정렬 - ls_name_asc ");
        System.out.println("6. 제목 역순 정렬 - ls_name_desc ");
        System.out.println("7. 날짜순 정렬 - ls_date ");
        System.out.println("8. 종료 - exit \n");
    }
    
    public static void prompt() {
    	System.out.print("Command > ");
    }
}
