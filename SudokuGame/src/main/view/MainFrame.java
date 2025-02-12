package main.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JPanel gridPanel;
    private JButton newGameButton, checkSolutionButton, hintButton, solveButton;
    private JLabel timerLabel;

    public MainFrame() {
        setTitle("Sudoku Game");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set background color for the frame
        getContentPane().setBackground(Color.BLACK);

        // Grid Panel
        gridPanel = new JPanel(new GridLayout(9, 9));
        gridPanel.setBackground(Color.WHITE); // White background for the grid
        add(gridPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Add spacing between buttons
        buttonPanel.setBackground(Color.BLACK); // Black background for the button panel

        // Styling buttons
        newGameButton = createStyledButton("New Game");
        checkSolutionButton = createStyledButton("Check Solution");
        hintButton = createStyledButton("Hint");
        solveButton = createStyledButton("Solve");

        buttonPanel.add(newGameButton);
        buttonPanel.add(checkSolutionButton);
        buttonPanel.add(hintButton);
        buttonPanel.add(solveButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Timer Label
        timerLabel = new JLabel("Time: 0s", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Bold font for the timer
        timerLabel.setForeground(Color.WHITE); // White text color
        timerLabel.setBackground(Color.BLACK); // Black background
        timerLabel.setOpaque(true); // Make background visible
        add(timerLabel, BorderLayout.NORTH);

        setVisible(true);
    }

    // Helper method to create styled buttons
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.BLUE); // Blue background
        button.setForeground(Color.WHITE); // White text color
        button.setFont(new Font("Arial", Font.BOLD, 16)); // Bold font
        button.setFocusPainted(false); // Remove focus border
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Add padding
        return button;
    }

    public JPanel getGridPanel() {
        return gridPanel;
    }

    public JButton getNewGameButton() {
        return newGameButton;
    }

    public JButton getCheckSolutionButton() {
        return checkSolutionButton;
    }

    public JButton getHintButton() {
        return hintButton;
    }

    public JButton getSolveButton() {
        return solveButton;
    }

    public JLabel getTimerLabel() {
        return timerLabel;
    }
}