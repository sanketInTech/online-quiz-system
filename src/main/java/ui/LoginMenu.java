package ui;

import model.User;
import service.UserService;
import util.InputUtil;

public class LoginMenu extends BaseMenu {
    private final UserService userService;

    public LoginMenu() {
        this.userService = new UserService();
    }

    @Override
    protected void showMenu() {
        showHeader("ONLINE QUIZ SYSTEM - LOGIN");
        System.out.println("1. Login");
        System.out.println("0. Exit");
    }

    @Override
    public void handleChoice() {
        int choice = getMenuChoice(1);
        
        switch (choice) {
            case 1:
                login();
                break;
            case 0:
                System.out.println("\nThank you for using Online Quiz System. Goodbye!");
                isRunning = false;
                break;
            default:
                showError("Invalid choice. Please try again.");
        }
    }

    private void login() {
        System.out.println("\n--- Login ---");
        String username = InputUtil.getStringInput("Username: ");
        String password = InputUtil.getPassword("Password: ");
        
        User user = userService.authenticate(username, password);
        
        if (user != null) {
            showMessage("Login successful! Welcome, " + user.getUsername() + "!");
            
            // Redirect based on user role
            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                AdminMenu adminMenu = new AdminMenu(user);
                adminMenu.display();
            } else {
                UserMenu userMenu = new UserMenu(user);
                userMenu.display();
            }
        } else {
            showError("Invalid username or password. Please try again.");
        }
    }
}
