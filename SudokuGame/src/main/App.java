package main;

import main.controller.GameController;
import main.view.MainFrame;

public class App {
    public static void main(String[] args) {
        // Launch the GUI
        MainFrame view = new MainFrame();
        new GameController(view);
    }
}