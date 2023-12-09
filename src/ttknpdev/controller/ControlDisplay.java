package ttknpdev.controller;

import ttknpdev.ui.main.MainGUI;

public class ControlDisplay {
    private MainGUI mainGUI;

    public ControlDisplay() {
        mainGUI = new MainGUI();
        mainGUI.display();
    }
    public static void main(String[] args) {
        new ControlDisplay();
    }
}
