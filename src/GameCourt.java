/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * GameCourt
 * 
 * This class holds the primary game logic of how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 * 
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

	// the state of the game logic
	private Circle[][] gridSpaces; // empty gridspaces to overlap grid
	private Checkers[] checker; // array of checker for Player 1
	private Checker2[] checker2; // array of checker for Player 2
	private Grid grid; // grid
	private static turn x; // Determines which player's turn it is
	private boolean buttondown = false; // check for if down arrow has been
										// pressed
	private static String[][] space; // helps check if space is already taken up
										// by a Checker
	private int j = 0; // counter for space array for 1st Checker
	private int j2 = 0; // counter for space array for 2nd Checker
	private int m = 0; // index for checker (which checker it is)
	private int n = 0; // index for checker 2 (which checker it is)

	public boolean playing = false; // whether the game is running
	private JLabel status; // Current status text (i.e. Running...)

	// Game constants
	public static final int COURT_WIDTH = 850;
	public static final int COURT_HEIGHT = 600;
	// Update interval for timer in milliseconds
	public static final int INTERVAL = 35;

	public GameCourt(JLabel status) {
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// The timer is an object which triggers an action periodically
		// with the given INTERVAL. One registers an ActionListener with
		// this timer, whose actionPerformed() method will be called
		// each time the timer triggers. We define a helper method
		// called tick() that actually does everything that should
		// be done in a single timestep.
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		timer.start(); // MAKE SURE TO START THE TIMER!

		// Enable keyboard focus on the court area
		// When this component has the keyboard focus, key
		// events will be handled by its key listener.
		setFocusable(true);

		// this key listener allows the square to move as long
		// as an arrow key is pressed, by changing the square's
		// velocity accordingly. (The tick method below actually
		// moves the square.)
		space = new String[6][7];
		x = turn.FIRST;
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					// check if it's 1st Player turn, and if j is within bounds,
					// and if down arrow has been pressed
					if (x == turn.FIRST && j > 0 && buttondown == false) {
						// if so, move the checker and update index
						if (m == 21)
							tick();
						else {
							checker[m].pos_x = checker[m].pos_x - 100;
							StdAudio.play("woosh.wav");
							j--;
						}
					} else {
						// check if it's 2nd Player turn, and if j2 (checker2
						// index) is in bounds,
						// and if down arrow has been pressed
						if (x == turn.SECOND && j2 > 0 && buttondown == false) {
							// if so, update checker position and index
							if (n == 21)
								tick();
							else {
								checker2[n].pos_x = checker2[n].pos_x - 100;
								StdAudio.play("woosh.wav");
								j2--;
							}
						}
					}

				}
				// same as above, except with right key
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					if (x == turn.FIRST && j < 6 && buttondown == false) {
						// if out of pieces, make sure to tick to end game
						if (m == 21)
							tick();
						else {
							checker[m].pos_x = checker[m].pos_x + 100;
							StdAudio.play("woosh.wav");
							j++;
						}
					} else {
						if (x == turn.SECOND && j2 < 6 && buttondown == false) {
							// if out of piece, tick to end game
							if (n == 21)
								tick();
							else {
								checker2[n].pos_x = checker2[n].pos_x + 100;
								StdAudio.play("woosh.wav");
								j2++;
							}
						}
					}

				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					// check if 1st player's turn
					if (x == turn.FIRST) {

						// go through spaces, starting from bottom since you
						// drop checker,
						// and if it's empty, put the checker at the bottom
						for (int i = 5; i >= 0; i--) {

							if (space[i][j].equals("empty")) {
								checker[m].pos_y = gridSpaces[i][j].pos_y;
								StdAudio.play("blop.wav");
								// since down key was pressed, update buttondown
								// to true
								buttondown = true;
								break;
							}
						}
					} else { // if it's 2nd player's turn
						for (int i = 5; i >= 0; i--) {

							if (space[i][j2].equals("empty")) {
								checker2[n].pos_y = gridSpaces[i][j2].pos_y;
								StdAudio.play("blop.wav");
								// since down key was pressed,
								// update buttondown to true
								buttondown = true;
								break;
							}
						}
					}
				}
				// undo button
				else if (e.getKeyCode() == KeyEvent.VK_UP) {
					if (buttondown == true) {
						if (x == turn.FIRST) {
							checker[m].pos_y = Checkers.INIT_POS_Y;
							StdAudio.play("blop.wav");
						}
						if (x == turn.SECOND) {
							checker2[n].pos_y = Checker2.INIT_POS_Y;
							StdAudio.play("blop.wav");
						}
						buttondown = false;
					}
				}
				// press enter to finish move
				else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					// check if down key has been pressed

					if (buttondown == true) {
						if (x == turn.FIRST) {
							// if so, update space where checker is to true if
							// space is empty
							for (int i = 5; i >= 0; i--) {
								if (space[i][j].equals("empty")) {
									space[i][j] = "true";
									StdAudio.play("eyepoke.wav");
									break;
								}
							}

						} else { // second player's turn, same as above
							for (int i = 5; i >= 0; i--) {
								// j2 is for index of checker piece 2
								if (space[i][j2].equals("empty")) {
									space[i][j2] = "true2";
									StdAudio.play("eyepoke.wav");
									break;
								}
							}
						}
						// if first player, update his checker index and go to
						// next checker piece
						if (x == turn.FIRST) {
							m++;
							j = 0;
						}
						// same as above for player 2
						if (x == turn.SECOND) {
							n++;
							j2 = 0;
						}
						// set buttondown back to false
						buttondown = false;
						// go a timeskip to see if there's a winner
						tick();
						// switch player's turns
						change();
					}
				}
			}

			public void keyReleased(KeyEvent e) {

			}

			public void keyTyped(KeyEvent e) {

			}
		});
		this.status = status;
	}

	// change turn mode
	public turn change() {
		if (x == turn.FIRST) {
			x = turn.SECOND;
			return x;
		}
		if (x == turn.SECOND)
			x = turn.FIRST;
		return x;
	}

	// create 1st player's turn and 2nd player's turn
	public enum turn {
		FIRST, SECOND
	}

	/**
	 * (Re-)set the state of the game to its initial state.
	 */
	public void reset() {
		// create grid and gridSpaces array
		grid = new Grid(COURT_WIDTH, COURT_HEIGHT);
		gridSpaces = new Circle[6][7];
		// create space array to track if object space is occupied
		space = new String[6][7];
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 7; j++)
				space[i][j] = "empty";

		{
			int x = 0; // initial starting spot for grid spaces
			int y = 80; // initial starting y for grid spaces
			// create grid
			for (int i = 0; i < 6; i++) {
				y = y + 60;
				x = 0;
				for (int j = 0; j < 7; j++) {
					x = x + 100;
					Circle.INIT_POS_X = x;
					Circle.INIT_POS_Y = y;
					gridSpaces[i][j] = new Circle(COURT_WIDTH, COURT_HEIGHT);
				}
			}
			// create an array of checkers for each player
			checker = new Checkers[21];
			checker2 = new Checker2[21];
			// set checkers to color of player choosing and create checker
			// objects
			for (int i = 0; i < 21; i++) {
				Checker2.c2 = ColorChooserDemo2.newColor2;
				checker2[i] = new Checker2(COURT_WIDTH, COURT_HEIGHT);
				Checkers.c = ColorChooserDemo.newColor;
				checker[i] = new Checkers(COURT_WIDTH, COURT_HEIGHT);
			}
		}
		buttondown = false;
		// make first player the default option to go
		x = turn.FIRST;
		// set index variables to 0
		j = 0;
		j2 = 0;
		// start with beginning checker pieces
		m = 0;
		n = 0;
		playing = true;
		status.setText("Running...");

		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}

	/**
	 * This method is called every time the timer defined in the constructor
	 * triggers.
	 */
	void tick() {
		if (playing) {
			// check for a tie
			if (n == 21) {
				StdAudio.play("10secsapplause.wav");
				playing = false;
				status.setText("Tie game!");
			} else {
				if (x == turn.FIRST) {
					// if first turn, move checker
					if (m < 21) {
						
						checker[m].move();
						status.setText("Player 1's turn");
					}

				} else {
					// if second player turn, move his checker
					if (n < 21) {
						checker2[n].move();
						status.setText("Player 2's turn");
					}
				}

				// check for the game end conditions
				for (int i = 0; i < 6; i++)
					for (int j = 0; j < 7; j++) {
						// check horizontally right
						if (j + 3 < 7) {
							if (space[i][j].equals("true")
									&& space[i][j + 1].equals("true")
									&& space[i][j + 2].equals("true")
									&& space[i][j + 3].equals("true")) {
								StdAudio.play("10secsapplause.wav");
								playing = false;
								if (x == turn.FIRST)
									status.setText("Player 1 wins!");
							}
							if (space[i][j].equals("true2")
									&& space[i][j + 1].equals("true2")
									&& space[i][j + 2].equals("true2")
									&& space[i][j + 3].equals("true2")) {
								StdAudio.play("10secsapplause.wav");
								playing = false;
								if (x == turn.SECOND)
									status.setText("Player 2 wins!");
							}
						}
						// check horizontally left
						if (j - 3 >= 0) {
							if (space[i][j].equals("true")
									&& space[i][j - 1].equals("true")
									&& space[i][j - 2].equals("true")
									&& space[i][j - 3].equals("true")) {
								StdAudio.play("10secsapplause.wav");
								playing = false;
								if (x == turn.FIRST)
									status.setText("Player 1 wins!");
							}
							if (space[i][j].equals("true2")
									&& space[i][j - 1].equals("true2")
									&& space[i][j - 2].equals("true2")
									&& space[i][j - 3].equals("true2")) {
								StdAudio.play("10secsapplause.wav");
								playing = false;
								if (x == turn.SECOND)
									status.setText("Player 2 wins!");
							}
						}

						// check vertically upward
						if (i + 3 < 6) {
							if (space[i][j].equals("true")
									&& space[i + 1][j].equals("true")
									&& space[i + 2][j].equals("true")
									&& space[i + 3][j].equals("true")) {
								StdAudio.play("10secsapplause.wav");
								playing = false;
								if (x == turn.FIRST)
									status.setText("Player 1 wins!");
							}
							if (space[i][j].equals("true2")
									&& space[i + 1][j].equals("true2")
									&& space[i + 2][j].equals("true2")
									&& space[i + 3][j].equals("true2")) {
								StdAudio.play("10secsapplause.wav");
								playing = false;
								if (x == turn.SECOND)
									status.setText("Player 2 wins!");
							}
						}
						// check vertically downward
						if (i - 3 >= 0) {
							if (space[i][j].equals("true")
									&& space[i - 1][j].equals("true")
									&& space[i - 2][j].equals("true")
									&& space[i - 3][j].equals("true")) {
								StdAudio.play("10secsapplause.wav");
								playing = false;
								if (x == turn.FIRST)
									status.setText("Player 1 wins!");
							}
							if (space[i][j].equals("true2")
									&& space[i - 1][j].equals("true2")
									&& space[i - 2][j].equals("true2")
									&& space[i - 3][j].equals("true2")) {
								StdAudio.play("10secsapplause.wav");
								playing = false;
								if (x == turn.SECOND)
									status.setText("Player 2 wins!");
							}
						}
						// check diagonal upright
						if (i - 3 >= 0 && j + 3 < 7) {
							if (space[i][j].equals("true")
									&& space[i - 1][j + 1].equals("true")
									&& space[i - 2][j + 2].equals("true")
									&& space[i - 3][j + 3].equals("true")) {
								StdAudio.play("10secsapplause.wav");
								playing = false;
								if (x == turn.FIRST)
									status.setText("Player 1 wins!");
							}
							if (space[i][j].equals("true2")
									&& space[i - 1][j + 1].equals("true2")
									&& space[i - 2][j + 2].equals("true2")
									&& space[i - 3][j + 3].equals("true2")) {
								StdAudio.play("10secsapplause.wav");
								playing = false;
								if (x == turn.SECOND)
									status.setText("Player 2 wins!");
							}
						}
						// check diagonal upleft
						if (i - 3 >= 0 && j - 3 >= 0) {
							if (space[i][j].equals("true")
									&& space[i - 1][j - 1].equals("true")
									&& space[i - 2][j - 2].equals("true")
									&& space[i - 3][j - 3].equals("true")) {
								playing = false;
								StdAudio.play("10secsapplause.wav");
								if (x == turn.FIRST)
									status.setText("Player 1 wins!");
							}
							if (space[i][j].equals("true2")
									&& space[i - 1][j - 1].equals("true2")
									&& space[i - 2][j - 2].equals("true2")
									&& space[i - 3][j - 3].equals("true2")) {
								StdAudio.play("10secsapplause.wav");
								playing = false;
								if (x == turn.SECOND)
									status.setText("Player 2 wins!");
							}
						}
						// check diagonal downleft
						if (i + 3 < 6 && j - 3 >= 0) {
							if (space[i][j].equals("true")
									&& space[i + 1][j - 1].equals("true")
									&& space[i + 2][j - 2].equals("true")
									&& space[i + 3][j - 3].equals("true")) {
								StdAudio.play("10secsapplause.wav");
								playing = false;
								if (x == turn.FIRST)
									status.setText("Player 1 wins!");
							}
							if (space[i][j].equals("true2")
									&& space[i + 1][j - 1].equals("true2")
									&& space[i + 2][j - 2].equals("true2")
									&& space[i + 3][j - 3].equals("true2")) {
								StdAudio.play("10secsapplause.wav");
								playing = false;
								if (x == turn.SECOND)
									status.setText("Player 2 wins!");
							}
						}
						// check diagonal downright
						if (i + 3 < 6 && j + 3 < 7) {
							if (space[i][j].equals("true")
									&& space[i + 1][j + 1].equals("true")
									&& space[i + 2][j + 2].equals("true")
									&& space[i + 3][j + 3].equals("true")) {
								StdAudio.play("10secsapplause.wav");
								playing = false;
								if (x == turn.FIRST)
									status.setText("Player 1 wins!");
							}
							if (space[i][j].equals("true2")
									&& space[i + 1][j + 1].equals("true2")
									&& space[i + 2][j + 2].equals("true2")
									&& space[i + 3][j + 3].equals("true2")) {
								StdAudio.play("10secsapplause.wav");
								playing = false;
								if (x == turn.SECOND)
									status.setText("Player 2 wins!");

							}
						}
					}
			}
			// update the display
			repaint();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		grid.draw(g);
		// for loop to create "empty" grid spaces
		Checker2.c2 = ColorChooserDemo2.newColor2;
		Checkers.c = ColorChooserDemo.newColor;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				gridSpaces[i][j].draw(g);
			}
		}
		for (int i = 0; i < n; i++) {
			checker2[i].draw(g);
		}
		if(x == turn.SECOND && n < 21 && playing == true)
			checker2[n].draw(g);
		for (int i = 0; i < m; i++){
			checker[i].draw(g);
		}
		if(x == turn.FIRST && m < 21 && playing == true)
			checker[m].draw(g);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
}
