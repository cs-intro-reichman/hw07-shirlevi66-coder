/** Draws the Koch curve and the the Koch snowflake fractal. */
public class Koch {

	public static void main(String[] args) {

		//// Uncomment the first code block to test the curve function.
		//// Uncomment the second code block to test the snowflake function.
		//// Uncomment only one block in each test, and remember to compile
		//// the class whenever you change the test.

        /*
		// Tests the curve function:
		// Gets n, x1, y1, x2, y2,
		// and draws a Koch curve of depth n from (x1,y1) to (x2,y2).
		curve(Integer.parseInt(args[0]),
			  Double.parseDouble(args[1]), Double.parseDouble(args[2]), 
		      Double.parseDouble(args[3]), Double.parseDouble(args[4]));
		*/

		/*
		// Tests the snowflake function:
		// Gets n, and draws a Koch snowflake of n edges in the standard canvass.
		snowFlake(Integer.parseInt(args[0]));
		*/
	}

	/** Gets n, x1, y1, x2, y2,
     *  and draws a Koch curve of depth n from (x1,y1) to (x2,y2). */
	public static void curve(int n, double x1, double y1, double x2, double y2) {
		
        if (n == 0) {
            StdDraw.line(x1, y1, x2, y2);
            return;
        }

        double dx = x2 - x1;
        double dy = y2 - y1;

        double xa = x1 + dx / 3.0;
        double ya = y1 + dy / 3.0;

        double xb = x1 + 2.0 * dx / 3.0;
        double yb = y1 + 2.0 * dy / 3.0;
		double x3 = (Math.sqrt(3) / 2.0) * (ya - yb) + 0.5 * (xa + xb);
        double y3 = (Math.sqrt(3) / 2.0) * (xb - xa) + 0.5 * (ya + yb);

        curve(n - 1, x1, y1, xa, ya);
        curve(n - 1, xa, ya, x3, y3);
        curve(n - 1, x3, y3, xb, yb);
        curve(n - 1, xb, yb, x2, y2);
	}

    /** Gets n, and draws a Koch snowflake of n edges in the standard canvass. */
	public static void snowFlake(int n) {
		// A little tweak that makes the drawing look better
		StdDraw.setYscale(0, 1.1);
		StdDraw.setXscale(0, 1.1);
		// Draws a Koch snowflake of depth n
		 StdDraw.setYscale(0, 1.1);
        StdDraw.setXscale(0, 1.1);

        StdDraw.clear(StdDraw.WHITE);
        StdDraw.setPenColor(StdDraw.BLACK);

        double x1 = 0.1, y1 = 0.1;
        double x2 = 1.0, y2 = 0.1;
        double x3 = 0.55, y3 = 0.1 + Math.sqrt(3) / 2.0 * 0.9;

        curve(n, x1, y1, x2, y2);
        curve(n, x2, y2, x3, y3);
        curve(n, x3, y3, x1, y1);
	}
}
