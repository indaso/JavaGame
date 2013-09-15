import java.awt.Color;
import java.awt.Graphics;

public class Grid extends GameObj {
	public static final int SIZE = 400;
	public static final int INIT_X = 20;
	public static final int INIT_Y = 100;
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;
	
    /** 
     * Note that because we don't do anything special
     * when constructing a Square, we simply use the
     * superclass constructor called with the correct parameters 
     */
    public Grid(int courtWidth, int courtHeight){
        super(INIT_VEL_X, INIT_VEL_Y, INIT_X, INIT_Y, 
        		2*SIZE, SIZE+50, courtWidth, courtHeight);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRoundRect(pos_x, pos_y, width, height, 100, 100);
        g.setColor(Color.BLACK);
        g.drawRoundRect(pos_x, pos_y, width, height, 100, 100);
    }

}