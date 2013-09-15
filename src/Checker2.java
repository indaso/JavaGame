import java.awt.Color;
import java.awt.Graphics;

public class Checker2 extends GameObj {

	public static final int SIZE = 50;       
	public static final int INIT_POS_X = 100;  
	public static final int INIT_POS_Y = 40; 
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;
	public static Color c2 = Color.GREEN;

	public Checker2(int courtWidth, int courtHeight) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, 
				SIZE, SIZE, courtWidth, courtHeight);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(c2);
		g.fillOval(pos_x, pos_y, width, height);
		g.setColor(Color.BLACK);
		g.drawOval(pos_x, pos_y, width, height);
	}



}