package twittervis;

// This class is not working.
// It only shows a simple GUI for inputs.
// Use TwitterVis class directly.

import javax.swing.*;    // Needed for Swing classes
import java.awt.event.*; // Needed for ActionListener Interface

/**
 *
 * @author Xavier
 */
/**
 * The Greetings class displays a JFrame that lets the user enter a distance in
 * kilometers. When the Submit button is clicked, a dialog box is displayed
 * and the input values are stored.
 */
public class Greetings extends JFrame {
    
    private JPanel panel;                    // To reference a panel
    private JLabel messageLabelA;            // To reference a label
    private JLabel messageLabelB;
    private JTextField topicA;               // To reference a text field
    private JTextField topicB;
    private JButton submitButton;
    private final int WINDOW_WIDTH = 650;    // Window width
    private final int WINDOW_HEIGHT = 220;   // Window height
    public static String inputA;
    public static String inputB;

    /**
     * Constructor
     * Make a new UI panel
     */
    public Greetings() {
        // Set the window title.
        setTitle("What's on your mind?");

        // Set the size of the window.
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        // Specify what happens when the close button is clicked.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Build the panel and add it to the frame.
        buildPanel();

        // Add the panel to the frame's content pane.
        add(panel);

        // Display the window.
        setVisible(true);
    }

    /**
     * The buildPanel method adds a label, text field, and and a submit
     * button to a panel.
     */
    private void buildPanel() {
        
        JLabel introduction = new JLabel("<html><body width='220'><h1>Plexus</h1>"+
                "<body width='220'><h3>Emotion Viz from Twitter</h3>"
                + "<p>What's on your mind? Type two related topics that you are most interested in. <br>");

        // Create a label to display instructions.
        messageLabelA = new JLabel("Enter Topic A");

        // Create a text field 10 characters wide.
        topicA = new JTextField(10);

        messageLabelB = new JLabel("Topic B");
        topicB = new JTextField(10);

        // Create a button with the caption "Calculate".
        submitButton = new JButton("Submit");

        // Add an action listener to the button.
        submitButton.addActionListener(new ButtonListener());

        // Create a JPanel object and let the panel
        // field reference it.
        panel = new JPanel();

        // Add the label, text field, and button
        // components to the panel.
        panel.add(introduction);
        panel.add(messageLabelA);
        panel.add(topicA);
        panel.add(messageLabelB);
        panel.add(topicB);
        panel.add(submitButton);

    }

    class ButtonListener implements ActionListener {

        //String inputA;
        // String inputB; // To hold the user's input

        /**
         * The actionPerformed method executes when the user clicks on the
         * Submit button.
         *
         * @param e The event object.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            // Get the text entered by the user into the
            // text field.
            inputA = topicA.getText();
            inputB = topicB.getText();
            JOptionPane.showMessageDialog(null, "Thanks.");
            
            
            // For debugging, display a message indicating
            // the application is ready for more input.
            
            System.out.println("Ready for the next input");
            
        }


        public String getInputA() {
            return inputA;
        }
        
        public String getInputB() {
            return inputB;
        }

    
    } // End of SubmitButtonListener class
    /**
     * The main method creates an instance of the KiloConverterWindow class,
     * which displays its window on the screen.
     *
     * @param args
     */
    public static void main(String[] args) {
        Greetings greetings = new Greetings();
    }
}
