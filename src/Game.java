/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/** 
 * Game
 * Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    public void run(){
        // NOTE : recall that the 'final' keyword notes inmutability
		  // even for local variables. 

        // Top-level frame in which game components live
		  // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("Connect Four");
        frame.setLocation(300,300);

		  // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);

        // Main playing area
        final GameCourt court = new GameCourt(status);
        frame.add(court, BorderLayout.CENTER);
        court.setBackground(Color.BLUE);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);
        
        // Instructions button and panel
        final JButton instructions = new JButton("Instructions");
        instructions.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JOptionPane.showMessageDialog(instructions, "Here are the" +
        				" instructions! " + '\n' +
        		"To move, press the arrow keys LEFT, RIGHT, and DOWN." +
        		" Once you have selected" + '\n' +
        		"a spot, press the ENTER key to finalize the spot and " +
        		"let the next player select his piece. " + '\n' +
        		"You can also change the color of your checker" +
        		" piece by using the color chooser on the left side" + '\n' +
        		"for player 1 and right side for player 2." +
        		" The first player who can place the checker piece in the" +
        		" board so that four of them are " + '\n' +
        		"connected either horizontally, vertically," +
        		" or diagonally wins!", null, 0);
        	}
        });
        control_panel.add(instructions);

        // Note here that when we add an action listener to the reset
        // button, we define it as an anonymous inner class that is 
        // an instance of ActionListener with its actionPerformed() 
        // method overridden. When the button is pressed,
        // actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    court.reset();
                }
            });
        control_panel.add(reset);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.reset();
    }

    /*
     * Main method run to start and run the game
     * Initializes the GUI elements specified in Game and runs it
     * NOTE: Do NOT delete! You MUST include this in the final submission of your game.
     */
    
	public static void main(String[] args){
        SwingUtilities.invokeLater(new Game());
        ColorChooserDemo.main(args);
        ColorChooserDemo2.main(args);
        StdAudio.play("newsintro.wav");
    }
}
