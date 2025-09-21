import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage; // ✅ Needed for icon
import java.util.ArrayList;
import java.text.DecimalFormat;

public class GradeCalculator extends JFrame {
    // Fayetteville Tech Colors
    private static final Color TECH_BLACK = new Color(33, 37, 41);
    private static final Color TECH_YELLOW = new Color(255, 193, 7);
    private static final Color LIGHT_GRAY = new Color(248, 249, 250);
    private static final Color DARK_GRAY = new Color(52, 58, 64);
    private static final Color SUCCESS_GREEN = new Color(40, 167, 69);
    private static final Color DANGER_RED = new Color(220, 53, 69);

    private ArrayList<Double> grades = new ArrayList<>();
    private DecimalFormat df = new DecimalFormat("#.##");

    // GUI Components
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel contentPanel;
    private JPanel statsPanel;
    private JPanel buttonPanel;
    private JLabel titleLabel;
    private JLabel statsLabel;
    private JLabel averageLabel;
    private JLabel letterGradeLabel;
    private JTextArea gradesDisplay;
    private JScrollPane scrollPane;
    private JButton addSingleBtn;
    private JButton addMultipleBtn;
    private JButton clearAllBtn;
    private JButton exitBtn;

    public GradeCalculator() {
        setTitle("Fayetteville Tech - Grade Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(true);

        // Set application icon
        setIconImage(createColoredIcon());

        initializeComponents();
        layoutComponents();
        addEventListeners();
        updateDisplay();

        // ✅ Correct Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Use default look and feel
        }
    }

    private Image createColoredIcon() {
        BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(TECH_YELLOW);
        g2d.fillRect(0, 0, 32, 32);
        g2d.setColor(TECH_BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("FT", 6, 22);
        g2d.dispose();
        return image;
    }

    private void initializeComponents() {
        // Main panels
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(LIGHT_GRAY);

        // Header panel
        headerPanel = new JPanel();
        headerPanel.setBackground(TECH_BLACK);
        headerPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        titleLabel = new JLabel("FAYETTEVILLE TECH GRADE CALCULATOR", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(TECH_YELLOW);

        // Content panel
        contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBackground(LIGHT_GRAY);
        contentPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        // Stats panel
        statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(TECH_BLACK, 2),
            new EmptyBorder(20, 20, 20, 20)
        ));

        statsLabel = new JLabel("Grade Statistics", JLabel.CENTER);
        statsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        statsLabel.setForeground(TECH_BLACK);
        statsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        averageLabel = new JLabel("No grades entered yet", JLabel.CENTER);
        averageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        averageLabel.setForeground(DARK_GRAY);
        averageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        letterGradeLabel = new JLabel("", JLabel.CENTER);
        letterGradeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        letterGradeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Grades display
        gradesDisplay = new JTextArea(10, 30);
        gradesDisplay.setFont(new Font("Monospaced", Font.PLAIN, 12));
        gradesDisplay.setBackground(Color.WHITE);
        gradesDisplay.setForeground(TECH_BLACK);
        gradesDisplay.setEditable(false);
        gradesDisplay.setBorder(new EmptyBorder(10, 10, 10, 10));

        scrollPane = new JScrollPane(gradesDisplay);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(TECH_BLACK, 1),
                "Your Grades",
                0, 0,
                new Font("Arial", Font.BOLD, 14),
                TECH_BLACK
            ),
            new EmptyBorder(5, 5, 5, 5)
        ));

        // Button panel
        buttonPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        buttonPanel.setBackground(LIGHT_GRAY);
        buttonPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        // Buttons
        addSingleBtn = createStyledButton("Add Single Grade", TECH_YELLOW, TECH_BLACK);
        addMultipleBtn = createStyledButton("Add Multiple Grades", TECH_YELLOW, TECH_BLACK);
        clearAllBtn = createStyledButton("Clear All Grades", DANGER_RED, Color.WHITE);
        exitBtn = createStyledButton("Exit Application", DARK_GRAY, Color.WHITE);
    }

    private JButton createStyledButton(String text, Color background, Color foreground) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            new EmptyBorder(12, 20, 12, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            Color originalBg = button.getBackground();

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(originalBg.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalBg);
            }
        });

        return button;
    }

    private void layoutComponents() {
        // Header
        headerPanel.setLayout(new BorderLayout());
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Stats panel layout
        statsPanel.add(statsLabel);
        statsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        statsPanel.add(averageLabel);
        statsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        statsPanel.add(letterGradeLabel);

        // Button panel
        buttonPanel.add(addSingleBtn);
        buttonPanel.add(addMultipleBtn);
        buttonPanel.add(clearAllBtn);
        buttonPanel.add(exitBtn);

        // Content panel layout
        contentPanel.add(statsPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Main panel layout
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void addEventListeners() {
        addSingleBtn.addActionListener(e -> addSingleGrade());
        addMultipleBtn.addActionListener(e -> addMultipleGrades());
        clearAllBtn.addActionListener(e -> clearAllGrades());
        exitBtn.addActionListener(e -> exitApplication());
    }

    private void addSingleGrade() {
        String input = JOptionPane.showInputDialog(this,
            "Enter a grade (0-100):",
            "Add Single Grade",
            JOptionPane.QUESTION_MESSAGE);

        if (input == null) return;

        try {
            double grade = Double.parseDouble(input);
            if (grade >= 0 && grade <= 100) {
                grades.add(grade);
                updateDisplay();
                showSuccessMessage("Grade " + df.format(grade) + "% added successfully!");
            } else {
                showErrorMessage("Please enter a grade between 0 and 100.");
            }
        } catch (NumberFormatException e) {
            showErrorMessage("Please enter a valid number.");
        }
    }

    private void addMultipleGrades() {
        String input = JOptionPane.showInputDialog(this,
            "How many grades would you like to add?",
            "Number of Grades",
            JOptionPane.QUESTION_MESSAGE);

        if (input == null) return;

        try {
            int numGrades = Integer.parseInt(input);
            if (numGrades < 1 || numGrades > 20) {
                showErrorMessage("Please enter a number between 1 and 20.");
                return;
            }

            ArrayList<Double> newGrades = new ArrayList<>();
            int skipped = 0;

            for (int i = 1; i <= numGrades; i++) {
                String gradeInput = JOptionPane.showInputDialog(this,
                    "Enter grade " + i + " of " + numGrades + ":",
                    "Grade " + i + "/" + numGrades,
                    JOptionPane.QUESTION_MESSAGE);

                if (gradeInput == null) {
                    skipped++;
                    continue;
                }

                try {
                    double grade = Double.parseDouble(gradeInput);
                    if (grade >= 0 && grade <= 100) {
                        newGrades.add(grade);
                    } else {
                        showErrorMessage("Grade " + i + " invalid (0-100 range). Skipping...");
                        skipped++;
                    }
                } catch (NumberFormatException e) {
                    showErrorMessage("Grade " + i + " invalid format. Skipping...");
                    skipped++;
                }
            }

            grades.addAll(newGrades);
            updateDisplay();

            String message = "Added " + newGrades.size() + " grade(s) successfully!";
            if (skipped > 0) {
                message += "\n" + skipped + " grade(s) were skipped.";
            }
            showSuccessMessage(message);

        } catch (NumberFormatException e) {
            showErrorMessage("Please enter a valid number.");
        }
    }

    private void clearAllGrades() {
        if (grades.isEmpty()) {
            showWarningMessage("No grades to clear.");
            return;
        }

        int result = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to clear all " + grades.size() + " grades?",
            "Confirm Clear All",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            grades.clear();
            updateDisplay();
            showSuccessMessage("All grades cleared successfully!");
        }
    }

    private void exitApplication() {
        int result = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to exit?",
            "Confirm Exit",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void updateDisplay() {
        if (grades.isEmpty()) {
            averageLabel.setText("No grades entered yet");
            averageLabel.setForeground(DARK_GRAY);
            letterGradeLabel.setText("");
            gradesDisplay.setText("No grades to display.\n\nClick 'Add Single Grade' or 'Add Multiple Grades' to get started!");
        } else {
            double average = calculateAverage();
            String letterGrade = getLetterGrade(average);

            averageLabel.setText("Average: " + df.format(average) + "% (" + grades.size() + " grades)");
            averageLabel.setForeground(TECH_BLACK);

            letterGradeLabel.setText("Letter Grade: " + letterGrade);

            // Set letter grade color
            switch (letterGrade) {
                case "A":
                    letterGradeLabel.setForeground(SUCCESS_GREEN);
                    break;
                case "B":
                    letterGradeLabel.setForeground(new Color(0, 123, 255));
                    break;
                case "C":
                    letterGradeLabel.setForeground(TECH_YELLOW.darker());
                    break;
                case "D":
                    letterGradeLabel.setForeground(new Color(255, 133, 27));
                    break;
                default:
                    letterGradeLabel.setForeground(DANGER_RED);
            }

            // Update grades display
            StringBuilder sb = new StringBuilder();
            sb.append("GRADE BREAKDOWN:\n");
            sb.append("================\n\n");

            for (int i = 0; i < grades.size(); i++) {
                sb.append(String.format("Grade %d: %s%%\n", i + 1, df.format(grades.get(i))));
            }

            sb.append("\n");
            sb.append("STATISTICS:\n");
            sb.append("===========\n");
            sb.append("Total Grades: ").append(grades.size()).append("\n");
            sb.append("Average: ").append(df.format(average)).append("%\n");
            sb.append("Letter Grade: ").append(letterGrade).append("\n");

            gradesDisplay.setText(sb.toString());
        }
    }

    private double calculateAverage() {
        if (grades.isEmpty()) return 0.0;
        return grades.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    private String getLetterGrade(double average) {
        if (average >= 90) return "A";
        else if (average >= 80) return "B";
        else if (average >= 70) return "C";
        else if (average >= 60) return "D";
        else return "F";
    }

    // --- Helper Messages ---
    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GradeCalculator().setVisible(true);
        });
    }
}
