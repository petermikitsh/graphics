//
//  Rasterizer.java
//  
//
//  Created by Joe Geigel on 1/21/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

/**
 * 
 * A simple class for performing rasterization algorithms.
 *
 */

import java.util.*;

public class Rasterizer {
    
    /**
     * number of scanlines
     */
    int n_scanlines;
    
    /**
     * Constructor
     *
     * @param n - number of scanlines
     *
     */
    Rasterizer (int n)
    {
        n_scanlines = n;
    }
    
    /**
     * Draw a line from (x0,y0) to (x1, y1) on the simpleCanvas C.
     *
     * Implementation should be using the Midpoint Method
     *
	 * You are to add the implementation here using only calls
	 * to C.setPixel()
     *
     * @param x0 - x coord of first endpoint
     * @param y0 - y coord of first endpoint
     * @param x1 - x coord of second endpoint
     * @param y1 - y coord of second endpoint
     * @param C - The canvas on which to apply the draw command.
	 */
	public void drawLine (int x0, int y0, int x1, int y1, simpleCanvas C)
	{
        int x, y, d, dx, dy, ystep;

        boolean steep = Math.abs(y1 - y0) > Math.abs(x1 - x0);

        if (steep) {
            int x0temp = x0;
            int x1temp = x1;
            x0 = y0;
            y0 = x0temp;
            x1 = y1;
            y1 = x1temp;
        }

        if (x0 > x1) { // line goes from right to left
            int x0temp = x0;
            int y0temp = y0;
            x0 = x1;
            y0 = y1;
            x1 = x0temp;
            y1 = y0temp;
        }

        if (y0 < y1) {
            ystep = 1;
        } else {
            ystep = -1;
        }

        dy = Math.abs(y1 - y0);
        dx = x1 - x0;
        y = y0;
        int deltaE = 2 * dy;
        int deltaNE = 2 * (dy - dx);
        d = deltaE - dx;

        for (x = x0; x <= x1; x++) {

            d -= dy;

            if (d < 0) {
                y += ystep;
                d += dx;
            }

            if (steep)
                C.setPixel(y, x);
            else
                C.setPixel(x, y);
        }


    }
      
}
