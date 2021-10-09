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
        System.out.println("5. 카테고리 목록 - ls_cate ");
        System.out.println("6. 제목 순 정렬 - ls_name ");
        System.out.println("7. 제목 역순 정렬 - ls_name_desc ");
        System.out.println("8. 날짜 순 정렬 - ls_date ");
        System.out.println("9. 날짜 역순 정렬 - ls_date_desc ");
        System.out.println("10. 완료 된 목록 - ls_comp ");
        System.out.println("11. 검색 - find ");
        System.out.println("12. 카테고리 검색 - find_cate ");
        System.out.println("13. 항목 완료하기 - comp ");
        System.out.println("14. 종료 - exit \n");
    }
    
    public static void prompt() {
    	System.out.print("Command > ");
    }
}
