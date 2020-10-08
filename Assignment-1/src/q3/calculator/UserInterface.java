package q3.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class UserInterface extends JFrame implements KeyListener {

    // Frame and text field for the calculator UI
    static  JFrame frame;
    static JTextField textField;

    // Variables to store the index of button currently highlighted
    static int currentFocusIndexNum;
    static int currentFocusIndexFunc;

    // Button array that stores the button displayed on screen
    static private ArrayList<JButton> numButtonArrayList;
    static private ArrayList<JButton> funcButtonArrayList;

    // Number of buttons of each type
    static public int NO_OF_BUTTONS_NUM;
    static public int NO_OF_BUTTONS_FUNC;

    // Variables to store which types of button are being highlighted
    static private boolean highlightNumPad;
    static private boolean highlightFunctions;

    // Variables to store the operands and operator at any time
    private String operand1, operator, operand2;

    // Boolean to store if stopped is pressed
    private boolean isStopped;

    // Constructor
    UserInterface () {
        operand1 = operator = operand2 = "";
        numButtonArrayList = new ArrayList<>();
        funcButtonArrayList = new ArrayList<>();
        NO_OF_BUTTONS_NUM = 10;
        NO_OF_BUTTONS_FUNC = 5;
        currentFocusIndexNum = 9;
        currentFocusIndexFunc = 4;
        isStopped = false;
        highlightNumPad = true;
        highlightFunctions = false;
    }

    // Function that sets up the UI
    public void setupUI () {

        // Create new frame for calculator
        frame = new JFrame("Calculator");
        // Setting the operation for close button of the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setting the look and feel of the window similar to OS
        try {
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // create the text field for display area which is not editable
        textField = new JTextField(26);
        textField.setEditable(false);

        // Attaching the key listener for Enter key
        textField.addKeyListener(this);

        // Creating buttons for numbers and functions
        JButton b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, bPlus, bMinus, bDivide, bMultiply, bEqual;

        b0 = new JButton("0"); b0.setPreferredSize(new Dimension(80,30));
        b1 = new JButton("1"); b1.setPreferredSize(new Dimension(80,30));
        b2 = new JButton("2"); b2.setPreferredSize(new Dimension(80,30));
        b3 = new JButton("3"); b3.setPreferredSize(new Dimension(80,30));
        b4 = new JButton("4"); b4.setPreferredSize(new Dimension(80,30));
        b5 = new JButton("5"); b5.setPreferredSize(new Dimension(80,30));
        b6 = new JButton("6"); b6.setPreferredSize(new Dimension(80,30));
        b7 = new JButton("7"); b7.setPreferredSize(new Dimension(80,30));
        b8 = new JButton("8"); b8.setPreferredSize(new Dimension(80,30));
        b9 = new JButton("9"); b9.setPreferredSize(new Dimension(80,30));

        bPlus = new JButton("+"); bPlus.setPreferredSize(new Dimension(80,30));
        bMinus = new JButton("-"); bMinus.setPreferredSize(new Dimension(80,30));
        bMultiply = new JButton("*"); bMultiply.setPreferredSize(new Dimension(80,30));
        bDivide = new JButton("/"); bDivide.setPreferredSize(new Dimension(80,30));
        bEqual = new JButton("STOP"); bEqual.setPreferredSize(new Dimension(80,30));

        // Adding the buttons to button array lists
        numButtonArrayList.add(b1);
        numButtonArrayList.add(b2);
        numButtonArrayList.add(b3);
        numButtonArrayList.add(b4);
        numButtonArrayList.add(b5);
        numButtonArrayList.add(b6);
        numButtonArrayList.add(b7);
        numButtonArrayList.add(b8);
        numButtonArrayList.add(b9);
        numButtonArrayList.add(b0);

        funcButtonArrayList.add(bPlus);
        funcButtonArrayList.add(bMinus);
        funcButtonArrayList.add(bMultiply);
        funcButtonArrayList.add(bDivide);
        funcButtonArrayList.add(bEqual);

        // Creating new panel for UI
        JPanel panel = new JPanel();

        // Adding textfield and buttons to the panel
        panel.add(UserInterface.textField);
        panel.add(b1);
        panel.add(b2);
        panel.add(b3);
        panel.add(b4);
        panel.add(b5);
        panel.add(b6);
        panel.add(b7);
        panel.add(b8);
        panel.add(b9);
        panel.add(Box.createRigidArea(new Dimension(100, 40)));
        panel.add(b0);
        panel.add(Box.createRigidArea(new Dimension(100, 40)));
        panel.add(bPlus);
        panel.add(bMinus);
        panel.add(bMultiply);
        panel.add(bDivide);
        panel.add(bEqual);

        // Adding panel to the frame and setting size
        frame.add(panel);
        frame.setSize(300,300);

        // Display the frame
        // Show function has been deprecated in the recent versions of java
        // If there is an error at this line replace setVisible(true) with show()
        frame.setVisible(true);

    }

    // Function to move the highlighter
    public static void changeFocus() {

        // synchronizing highlightNumPad as the key listener might also try to access this
        synchronized ((Boolean)highlightNumPad) {
            // if num pad is currently being highlighted
            if(highlightNumPad) {
                // synchronizing currentFocusIndexNum as the key listener might also try to access this
                synchronized ((Integer) currentFocusIndexNum) {
                    // get the index of button that should be highlighted next
                    int nextFocus = (currentFocusIndexNum + 1) % NO_OF_BUTTONS_NUM;
                    //highlight the next button
                    numButtonArrayList.get(nextFocus).setBackground(Color.GREEN);
                    // unhighlight the current highlighted button
                    numButtonArrayList.get(currentFocusIndexNum).setBackground(null);
                    // update the index of highlighted button
                    currentFocusIndexNum = nextFocus;
                }
                return;
            }
        }

        // synchronizing highlightFunctions as the key listener might also try to access this
        synchronized ((Boolean)highlightFunctions) {
            // if functions are currently being highlighted
            if(highlightFunctions) {
                // synchronizing currentFocusIndexFunc as the key listener might also try to access this
                synchronized ((Integer) currentFocusIndexFunc) {
                    // get the index of button that should be highlighted next
                    int nextFocus = (currentFocusIndexFunc + 1) % NO_OF_BUTTONS_FUNC;
                    //highlight the next button
                    funcButtonArrayList.get(nextFocus).setBackground(Color.GREEN);
                    // unhighlight the current highlighted button
                    funcButtonArrayList.get(currentFocusIndexFunc).setBackground(null);
                    // update the index of highlighted button
                    currentFocusIndexFunc = nextFocus;
                }
                return;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        //do nothing
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        // if the key pressed id not Enter key do nothing
        if(keyEvent.getKeyCode() != KeyEvent.VK_ENTER)
            return;

        // if stop was previously pressed then reset calculator
        if(isStopped) {
            textField.setText("");
            isStopped = false;
            operand1 = operator = operand2 = "";
            return;
        }

        String val = "";
        // if the highlighter was on numpad
        if(highlightNumPad) {
            // synchronizing currentFocusIndexNum as the timer function might also try to access this
            synchronized ((Integer) currentFocusIndexNum) {
                // get the value of key pressed
                val = numButtonArrayList.get(currentFocusIndexNum).getText();
                numButtonArrayList.get(currentFocusIndexNum).setBackground(null);
                currentFocusIndexNum = 9;
            }
            synchronized ((Boolean) highlightNumPad) {
                // numpad should no longer be highlighted
                highlightNumPad = false;
            }
            synchronized ((Boolean) highlightFunctions) {
                // functions should be highlighted
                highlightFunctions = true;
            }
        }
        // if the highlighter was on functions
        else if (highlightFunctions) {
            // synchronizing currentFocusIndexFunc as the timer function might also try to access this
            synchronized ((Integer) currentFocusIndexFunc) {
                // get the value of key pressed
                val = funcButtonArrayList.get(currentFocusIndexFunc).getText();
                funcButtonArrayList.get(currentFocusIndexFunc).setBackground(null);
                currentFocusIndexFunc = 4;
            }
            synchronized ((Boolean) highlightNumPad) {
                // numpad should be highlighted
                highlightNumPad = true;
            }
            synchronized ((Boolean) highlightFunctions) {
                // functions should no longer be highlighted
                highlightFunctions = false;
            }
        }

        if(val.charAt(0) >= '0' && val.charAt(0) <= '9') {
            // if the operator is already selected then update the second operand else first
            if (!operator.equals(""))
                operand2 = operand2 + val;
            else
                operand1 = operand1 + val;
            //update the display area
            textField.setText(operand1 + operator + operand2);
        }

        // if stop is pressed then evaluate the final value
        else if (val.equals("STOP")) {

            String ans = "";

            // get the answer depending on operator
            switch (operator) {
                case "+":
                    ans = Double.toString(Double.parseDouble(operand1) + Double.parseDouble(operand2));
                    break;
                case "-":
                    ans = Double.toString(Double.parseDouble(operand1) - Double.parseDouble(operand2));
                    break;
                case "*":
                    ans = Double.toString(Double.parseDouble(operand1) * Double.parseDouble(operand2));
                    break;
                case "/":
                    if(operand2.equals("0")) {
                        ans = "INVALID";
                        isStopped = true;
                    }
                    else
                        ans = Double.toString(Double.parseDouble(operand1) / Double.parseDouble(operand2));
                    break;
                default:
                    ans = operand1;
            }

            // set the value in display area
            textField.setText("= " + ans);
            operand1 = ans;
            operator = operand2 = "";
            isStopped = true;
        }
        else {
            // if operator is selected and second operand is not enter update the current operator
            if(operator.equals("") || operand2.equals("")) {
                operator = val;
                textField.setText(operand1 + operator + operand2);
            }
            // if the operands are not empty then calculate the intermediate value
            else {
                String ans = "";
                //calculate the value depending on operator
                switch (operator) {
                    case "+":
                        ans = Double.toString(Double.parseDouble(operand1) + Double.parseDouble(operand2));
                        break;
                    case "-":
                        ans = Double.toString(Double.parseDouble(operand1) - Double.parseDouble(operand2));
                        break;
                    case "*":
                        ans = Double.toString(Double.parseDouble(operand1) * Double.parseDouble(operand2));
                        break;
                    case "/":
                        if(operand2.equals("0")) {
                            ans = "INVALID";
                            isStopped = true;
                        }
                        else
                            ans = Double.toString(Double.parseDouble(operand1) / Double.parseDouble(operand2));
                        break;
                    default:
                        ans = operand1;
                }
                // update the display area
                operand1 = ans;
                operator = val;
                operand2 = "";
                textField.setText(operand1 + operator + operand2);
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        //do nothing
    }
}
