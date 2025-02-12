package main.controller;

import main.model.SudokuGenerator;
import main.view.MainFrame;

import javax.swing.*;
import java.awt.*;

public class GameController {
    private MainFrame view;
    private int[][] board; // Stores the current puzzle (with empty cells)
    private int[][] solution; // Stores the complete solution

    public GameController(MainFrame view) {
        this.view = view;
        setupListeners();
    }

    // Set up action listeners for buttons
    private void setupListeners() {
        // "New Game" button
        view.getNewGameButton().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                startNewGame();
            }
        });

        // "Check Solution" button
        view.getCheckSolutionButton().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (checkSolution()) {
                    JOptionPane.showMessageDialog(view, "Congratulations! You solved the puzzle!");
                } else {
                    JOptionPane.showMessageDialog(view, "Incorrect solution. Keep trying!");
                }
            }
        });

        // "Hint" button
        view.getHintButton().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                provideHint();
            }
        });

        // "Solve" button (optional)
        view.getSolveButton().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                solvePuzzle();
            }
        });
    }

    // Start a new game with a generated puzzle
    private void startNewGame() {
        // Prompt user to select difficulty
        int difficulty = JOptionPane.showOptionDialog(
                view,
                "Select Difficulty",
                "New Game",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new String[]{"Easy", "Medium", "Hard"},
                "Easy"
        );

        // Generate the puzzle and solution
        board = SudokuGenerator.generatePuzzle(difficulty + 1);
        solution = deepCopy(board); // Save the solution before removing numbers
        SudokuGenerator.removeNumbers(board, difficulty + 1); // Remove numbers for the puzzle

        // Update the grid in the UI
        updateGrid();
    }

    // Update the grid in the UI based on the current puzzle
    private void updateGrid() {
        JPanel gridPanel = view.getGridPanel();
        gridPanel.removeAll(); // Clear the existing grid

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JTextField cell = new JTextField();
                if (board[i][j] != 0) { // Pre-filled cell
                    cell.setText(String.valueOf(board[i][j]));
                    cell.setEditable(false); // Make it non-editable
                    cell.setBackground(Color.LIGHT_GRAY); // Highlight pre-filled cells
                } else {
                    cell.setBackground(Color.WHITE); // Empty cells are editable
                }
                cell.setHorizontalAlignment(JTextField.CENTER);
                gridPanel.add(cell);
            }
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    // Check if the current grid matches the solution
    private boolean checkSolution() {
        JPanel gridPanel = view.getGridPanel();
        Component[] components = gridPanel.getComponents();

        if (components.length != 81) {
            JOptionPane.showMessageDialog(view, "Grid is not properly initialized!");
            return false;
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JTextField cell = (JTextField) components[i * 9 + j];
                String text = cell.getText().trim();

                if (text.isEmpty()) {
                    return false; // Empty cells mean the puzzle isn't complete
                }

                try {
                    int value = Integer.parseInt(text);
                    if (value != solution[i][j]) {
                        return false; // Incorrect number
                    }
                } catch (NumberFormatException ex) {
                    return false; // Invalid input (non-numeric)
                }
            }
        }
        return true; // All cells are correct
    }

    // Provide a hint by filling one empty cell with the correct number
    private void provideHint() {
        JPanel gridPanel = view.getGridPanel();
        Component[] components = gridPanel.getComponents();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JTextField cell = (JTextField) components[i * 9 + j];
                if (cell.getText().trim().isEmpty()) { // Find an empty cell
                    cell.setText(String.valueOf(solution[i][j])); // Fill it with the correct number
                    cell.setEditable(false); // Make it non-editable
                    cell.setBackground(Color.YELLOW); // Highlight the hinted cell
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(view, "No more hints available!");
    }

    // Solve the puzzle automatically
    private void solvePuzzle() {
        JPanel gridPanel = view.getGridPanel();
        Component[] components = gridPanel.getComponents();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JTextField cell = (JTextField) components[i * 9 + j];
                cell.setText(String.valueOf(solution[i][j])); // Fill the cell with the solution
                cell.setEditable(false); // Make it non-editable
                cell.setBackground(Color.LIGHT_GRAY); // Highlight solved cells
            }
        }
    }

    // Helper method to create a deep copy of the board
    private int[][] deepCopy(int[][] original) {
        int[][] copy = new int[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, original[i].length);
        }
        return copy;
    }
}