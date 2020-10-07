package q3.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class UserInterface extends JFrame implements KeyListener {

    static  JFrame frame;
    static JTextField textField;
    static int currentFocusIndexNum;
    static int currentFocusIndexFunc;
    static private ArrayList<JButton> numButtonArrayList;
    static private ArrayList<JButton> funcButtonArrayList;
    static public int NO_OF_BUTTONS_NUM;
    static public int NO_OF_BUTTONS_FUNC;
    static private boolean highlightNumPad;
    static private boolean highlightFunctions;

    private String operand1, operator, operand2;
    private boolean isStopped;

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

    public void setupUI () {

        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        textField = new JTextField(11);
        textField.setEditable(false);
        textField.addKeyListener(this);

        JButton b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, bPlus, bMinus, bDivide, bMultiply, bEqual;

        b0 = new JButton("0");
        b1 = new JButton("1");
        b2 = new JButton("2");
        b3 = new JButton("3");
        b4 = new JButton("4");
        b5 = new JButton("5");
        b6 = new JButton("6");
        b7 = new JButton("7");
        b8 = new JButton("8");
        b9 = new JButton("9");

        bPlus = new JButton("+");
        bMinus = new JButton("-");
        bMultiply = new JButton("*");
        bDivide = new JButton("/");
        bEqual = new JButton("STOP");

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

        JPanel panel = new JPanel();

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
        panel.add(Box.createRigidArea(new Dimension(25, 20)));
        panel.add(b0);
        panel.add(Box.createRigidArea(new Dimension(25, 20)));
        panel.add(bPlus);
        panel.add(bMinus);
        panel.add(bMultiply);
        panel.add(bDivide);
        panel.add(bEqual);

        frame.add(panel);
        frame.setSize(150,250);
        frame.setVisible(true);

    }

    public static void changeFocus() {

        if(highlightNumPad) {
            int nextFocus = (currentFocusIndexNum + 1) % NO_OF_BUTTONS_NUM;
            numButtonArrayList.get(nextFocus).setBackground(Color.GREEN);
            numButtonArrayList.get(currentFocusIndexNum).setBackground(null);
            currentFocusIndexNum = nextFocus;
        }
        else if(highlightFunctions) {
            int nextFocus = (currentFocusIndexFunc + 1) % NO_OF_BUTTONS_FUNC;
            funcButtonArrayList.get(nextFocus).setBackground(Color.GREEN);
            funcButtonArrayList.get(currentFocusIndexFunc).setBackground(null);
            currentFocusIndexFunc = nextFocus;
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        //do nothing
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() != KeyEvent.VK_ENTER)
            return;

        if(isStopped) {
            textField.setText("");
            isStopped = false;
            operand1 = operator = operand2 = "";
            return;
        }

        String val = "";
        if(highlightNumPad) {
            val = numButtonArrayList.get(currentFocusIndexNum).getText();
            numButtonArrayList.get(currentFocusIndexNum).setBackground(null);
            highlightNumPad = false;
            highlightFunctions = true;
            currentFocusIndexNum = 9;
        }
        else if (highlightFunctions) {
            val = funcButtonArrayList.get(currentFocusIndexFunc).getText();
            funcButtonArrayList.get(currentFocusIndexFunc).setBackground(null);
            highlightNumPad = true;
            highlightFunctions = false;
            currentFocusIndexFunc = 4;
        }

        if(val.charAt(0) >= '0' && val.charAt(0) <= '9') {
            if (!operator.equals(""))
                operand2 = operand2 + val;
            else
                operand1 = operand1 + val;
            textField.setText(operand1 + operator + operand2);
        }
        else if (val.equals("STOP")) {

            String ans = "";

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

            textField.setText("" + ans);
            operand1 = ans;
            operator = operand2 = "";
            isStopped = true;
        }
        else {
            if(operator.equals("") || operand2.equals("")) {
                operator = val;
                textField.setText(operand1 + operator + operand2);
            }
            else {
                String ans = "";
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
