import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StudentGradeCalculator extends JFrame implements ActionListener {
    private JTextField[] marksFields;
    private JButton calcButton;
    private JTextArea resultArea;
    private int numSubjects;

    public StudentGradeCalculator(int numSubjects) {
        this.numSubjects = numSubjects;
        setTitle("Marks Calculator");
        setSize(350, 250 + (numSubjects * 30));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(numSubjects + 2, 2, 5, 5));

        marksFields = new JTextField[numSubjects];

        for (int i = 0; i < numSubjects; i++) {
            panel.add(new JLabel("Marks for Subject " + (i + 1) + " (out of 100):"));
            marksFields[i] = new JTextField();
            panel.add(marksFields[i]);
        }

        calcButton = new JButton("Calculate");
        calcButton.addActionListener(this);

        panel.add(calcButton);

        resultArea = new JTextArea(5, 20);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int total = 0;
        boolean validInput = true;

        for (int i = 0; i < numSubjects; i++) {
            String input = marksFields[i].getText().trim();
            try {
                int marks = Integer.parseInt(input);
                if (marks < 0 || marks > 100) {
                    throw new NumberFormatException();
                }
                total += marks;
            } catch (NumberFormatException ex) {
                resultArea.setText("Please enter valid marks between 0 and 100 for Subject " + (i + 1));
                validInput = false;
                break;
            }
        }

        if (validInput) {
            double avg = (double) total / numSubjects;
            String grade = calculateGrade(avg);
            String result = "Total Marks: " + total + "\n"
                          + "Average Percentage: " + String.format("%.2f", avg) + "%\n"
                          + "Grade: " + grade;
            resultArea.setText(result);
        }
    }

    private String calculateGrade(double avg) {
        
        if (avg >= 90) return "A+";
        else if (avg >= 80) return "A";
        else if (avg >= 70) return "B";
        else if (avg >= 60) return "C";
        else if (avg >= 50) return "D";
        else return "F";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            String input = JOptionPane.showInputDialog(null, "Enter number of subjects:", "Subjects Input", JOptionPane.QUESTION_MESSAGE);

            int subjects = 0;
            try {
                subjects = Integer.parseInt(input.trim());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid number of subjects! Exiting...");
                System.exit(1);
            }

            if (subjects <= 0) {
                JOptionPane.showMessageDialog(null, "Number of subjects must be greater than zero! Exiting...");
                System.exit(1);
            }

            new StudentGradeCalculator(subjects).setVisible(true);
        });
    }
}
