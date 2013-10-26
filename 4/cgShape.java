/**
 * cgShape.java
 *
 * Class that includes routines for tessellating a number of basic shapes
 *
 * Students are to supply their implementations for the
 * functions in this file using the function "addTriangle()" to do the 
 * tessellation.
 *
 */

import java.awt.*;
import java.nio.*;
import java.awt.event.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import java.io.*;


public class cgShape extends simpleShape
{
    /**
	 * constructor
	 */
	public cgShape()
	{
	}

    // public enum Axis {
    //     X, Y, Z;
    // }

    static class Point {
        float x, y, z;
        public Point(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }   
    }

    /**
     * makeCube - Create a unit cube, centered at the origin, with a given number
     * of subdivisions in each direction on each face.
     *
     * @param subdivision - number of equal subdivisons to be made in each 
     *        direction along each face
     *
     * Can only use calls to addTriangle()
     */
    public void makeCube (int subdivisions)
    {
        if( subdivisions < 1 )
	    subdivisions = 1;
        Point top = new Point(-0.5f, -0.5f,  0.5f);
        drawSide(top, subdivisions);

    }

    private void drawSide(Point p, int n) {
        for (int i = 0; i < n; i++) {
            float x0 = (float) i/n - 0.5f;
            float x1 = (float) (i+1)/n - 0.5f;
            for (int j = 0; j < n; j++) {
                float y0 = (float) j/n - 0.5f;
                float y1 = (float) (j+1)/n - 0.5f;
                //System.out.printf("x0 = %f\n x1 = %f\n y0 = %f\n y1 = %f\n p.x = %f\n p.y = %f\n p.z = %f\n", x0, x1, y0, y1, p.x, p.y, p.z);
                addTriangle(x0, y1, p.z,
                            x1, y0, p.z,
                            x0, y0, p.z);
                addTriangle(x1, y1, p.z,
                            x1, y0, p.z,
                            x0, y1, p.z);
                addTriangle(x0, y0, -p.z,
                            x1, y0, -p.z,
                            x0, y1, -p.z);
                addTriangle(x0, y1, -p.z,
                            x1, y0, -p.z,
                            x1, y1, -p.z);
                addTriangle(p.x, x0, y0,
                            p.x, x1, y0,
                            p.x, x0, y1);
                addTriangle(p.x, x0, y1,
                            p.x, x1, y0,
                            p.x, x1, y1);
                addTriangle(-p.x, x0, y1,
                            -p.x, x1, y0,
                            -p.x, x0, y0);
                addTriangle(-p.x, x1, y1,
                            -p.x, x1, y0,
                            -p.x, x0, y1);
                addTriangle(x0, p.y, y1,
                            x1, p.y, y0,
                            x0, p.y, y0);
                addTriangle(x1, p.y, y1,
                            x1, p.y, y0,
                            x0, p.y, y1);
                addTriangle(x0, -p.y, y0,
                            x1, -p.y, y0,
                            x0, -p.y, y1);
                addTriangle(x0, -p.y, y1,
                            x1, -p.y, y0,
                            x1, -p.y, y1);
            }
        }

    }

    /**
     * makeCylinder - Create polygons for a cylinder with unit height, centered at
     * the origin, with separate number of radial subdivisions and height 
     * subdivisions.
     *
     * @param radius - Radius of the base of the cylinder
     * @param radialDivision - number of subdivisions on the radial base
     * @param heightDivisions - number of subdivisions along the height
     *
     * Can only use calls to addTriangle()
     */
    public void makeCylinder (float radius, int radialDivisions, int heightDivisions)
    {
        if( radialDivisions < 3 )
	    radialDivisions = 3;

        if( heightDivisions < 1 )
	    heightDivisions = 1;

    }

    /**
     * makeCone - Create polygons for a cone with unit height, centered at the
     * origin, with separate number of radial subdivisions and height 
     * subdivisions.
     *
     * @param radius - Radius of the base of the cone
     * @param radialDivision - number of subdivisions on the radial base
     * @param heightDivisions - number of subdivisions along the height
     *
     * Can only use calls to addTriangle()
     */
    public void makeCone (float radius, int radialDivisions, int heightDivisions)
    {
        if( radialDivisions < 3 )
	    radialDivisions = 3;

        if( heightDivisions < 1 )
	    heightDivisions = 1;

    }

    /**
     * makeSphere - Create sphere of a given radius, centered at the origin, 
     * using spherical coordinates with separate number of thetha and 
     * phi subdivisions.
     *
     * @param radius - Radius of the sphere
     * @param slides - number of subdivisions in the theta direction
     * @param stacks - Number of subdivisions in the phi direction.
     *
     * Can only use calls to addTriangle
     */
    public void makeSphere (float radius, int slices, int stacks)
    {
    }

}
