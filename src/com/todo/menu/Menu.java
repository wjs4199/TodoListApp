package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("< ToDoList ���� ��ɾ� ���� �ȳ��� >");
        System.out.println("1. �׸� �߰� - add ");
        System.out.println("2. �׸� ���� - del ");
        System.out.println("3. �׸� ����  - edit ");
        System.out.println("4. ��ü ��� - ls ");
        System.out.println("5. ī�װ� ��� - ls_cate ");
        System.out.println("6. ���� �� ���� - ls_name ");
        System.out.println("7. ���� ���� ���� - ls_name_desc ");
        System.out.println("8. ��¥ �� ���� - ls_date ");
        System.out.println("9. ��¥ ���� ���� - ls_date_desc ");
        System.out.println("10. �Ϸ� �� ��� - ls_comp ");
        System.out.println("11. �˻� - find ");
        System.out.println("12. ī�װ� �˻� - find_cate ");
        System.out.println("13. �׸� �Ϸ��ϱ� - comp ");
        System.out.println("14. ���� - exit \n");
    }
    
    public static void prompt() {
    	System.out.print("Command > ");
    }
}
