/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;

/** A basic game object displayed as a yellow circle, starting in the 
 * upper left corner of the game court.
 *
 */
public class Circle extends GameObj {

	public static final int SIZE = 50;       
	public static int INIT_POS_X;  
	public static int INIT_POS_Y; 
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;

	public Circle(int courtWidth, int courtHeight) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, 
				SIZE, SIZE, courtWidth, courtHeight);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval(pos_x, pos_y, width, height);
		g.setColor(Color.BLACK);
		g.drawOval(pos_x, pos_y, width, height);
	}



}