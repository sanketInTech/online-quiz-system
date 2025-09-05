package ui;

import util.InputUtil;

public abstract class BaseMenu implements Menu {
    protected static final int EXIT_OPTION = 0;
    protected boolean isRunning = true;

    @Override
    public void display() {
        isRunning = true;
        while (isRunning) {
            showMenu();
            handleChoice();
        }
    }

    protected abstract void showMenu();

    protected int getMenuChoice(int maxOption) {
        return InputUtil.getIntInput("Enter your choice (0 to go back): ", 0, maxOption);
    }

    protected void showHeader(String title) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("  " + title);
        System.out.println("=".repeat(50));
    }

    protected void showMessage(String message) {
        System.out.println("\n" + message);
    }

    protected void showError(String error) {
        System.err.println("\nError: " + error);
    }

    protected void pause() {
        System.out.print("\nPress Enter to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
            // Ignore
        }
    }
}
