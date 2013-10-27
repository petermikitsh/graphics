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

        drawCube(new Point(-0.5f, -0.5f,  0.5f), subdivisions);
    }

    /* Find each subsquare and draw on each axis. */
    private void drawCube(Point p, int n) {
        for (int i = 0; i < n; i++) {
            float x0 = (float) i/n - 0.5f;
            float x1 = (float) (i+1)/n - 0.5f;
            for (int j = 0; j < n; j++) {
                float y0 = (float) j/n - 0.5f;
                float y1 = (float) (j+1)/n - 0.5f;
                addTriangle(  x0,   y1,   p.z,
                              x1,   y0,   p.z,
                              x0,   y0,   p.z);
                addTriangle(  x1,   y1,   p.z,
                              x1,   y0,   p.z,
                              x0,   y1,   p.z);
                addTriangle(  x0,   y0,   -p.z,
                              x1,   y0,   -p.z,
                              x0,   y1,   -p.z);
                addTriangle(  x0,   y1,   -p.z,
                              x1,   y0,   -p.z,
                              x1,   y1,   -p.z);
                addTriangle( p.x,   x0,   y0,
                             p.x,   x1,   y0,
                             p.x,   x0,   y1);
                addTriangle( p.x,   x0,   y1,
                             p.x,   x1,   y0,
                             p.x,   x1,   y1);
                addTriangle(-p.x,   x0,   y1,
                            -p.x,   x1,   y0,
                            -p.x,   x0,   y0);
                addTriangle(-p.x,   x1,   y1,
                            -p.x,   x1,   y0,
                            -p.x,   x0,   y1);
                addTriangle(  x0,  p.y,   y1,
                              x1,  p.y,   y0,
                              x0,  p.y,   y0);
                addTriangle(  x1,  p.y,   y1,
                              x1,  p.y,   y0,
                              x0,  p.y,   y1);
                addTriangle(  x0, -p.y,   y0,
                              x1, -p.y,   y0,
                              x0, -p.y,   y1);
                addTriangle(  x0, -p.y,   y1,
                              x1, -p.y,   y0,
                              x1, -p.y,   y1);
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

        makeDisks(radius, radialDivisions, heightDivisions);

    }

    /* Approximates disks on the y-axis centered at (0,0).
       Draws triangles by connecting points at Theta_i, Theta_i+1, and the center.
    */
    private void makeDisks(float r, int n, int rect) {

        float x0, z0, x1, z1, theta0, theta1;

        Point d0 = new Point (0f, -0.5f, 0f);
        Point d1 = new Point (0f,  0.5f, 0f);

        theta0 = 0;
        x0 = r * (float) Math.cos(0);
        z0 = r * (float) Math.sin(0);

        for (int i = 0; i < n; i++) {

            theta1 = theta0 + (float) 360/n;

            x1 = r * (float) Math.cos(Math.toRadians(theta1));
            z1 = r * (float) Math.sin(Math.toRadians(theta1));

            addTriangle( d0.x, d0.y, d0.z,
                           x0, d0.y,   z0,
                           x1, d0.y,   z1);

            addTriangle(   x1, d1.y,   z1,
                           x0, d1.y,   z0,
                         d1.x, d1.y, d1.z);

            Point p0 = new Point(x0, d0.y, z0);
            Point p1 = new Point(x1, d0.y, z1);
            drawCylinderRect(p0, p1, rect);

            x0 = x1;
            z0 = z1;
            theta0 = theta1;
        }

    }

    private void drawCylinderRect(Point p0, Point p1, int n) {
        float y0, y1;
        
        y0 = -0.5f;
        
        for (int i = 0; i < n; i++) {
            
            y1 = y0 + (float) 1/n;

            addTriangle(p0.x, y1, p0.z,
                        p1.x, y1, p1.z,
                        p0.x, y0, p0.z);

            addTriangle(p1.x, y1, p1.z,
                        p1.x, y0, p1.z,
                        p0.x, y0, p0.z);

            y0 = y1;

        }
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

        makeConeDisk(radius, radialDivisions, heightDivisions);

    }


    /* Approximates disks on the y-axis centered at (0,0).
       Draws triangles by connecting points at Theta_i, Theta_i+1, and the center.
    */
    private void makeConeDisk(float r, int n, int h) {

        float x0, z0, x1, z1, theta0, theta1;

        Point d0 = new Point (0f, -0.5f, 0f);

        theta0 = 0;
        x0 = r * (float) Math.cos(0);
        z0 = r * (float) Math.sin(0);

        for (int i = 0; i < n; i++) {

            theta1 = theta0 + (float) 360/n;

            x1 = r * (float) Math.cos(Math.toRadians(theta1));
            z1 = r * (float) Math.sin(Math.toRadians(theta1));

            addTriangle( d0.x, d0.y, d0.z,
                           x0, d0.y,   z0,
                           x1, d0.y,   z1);

            Point p0 = new Point(x0, d0.y, z0);
            Point p1 = new Point(x1, d0.y, z1);

            makeConeHeight(p0, p1, h);

            x0 = x1;
            z0 = z1;
            theta0 = theta1;
        }

    }

    private void makeConeHeight(Point p0, Point p1, int h) {
        System.out.println("Height is now " + h);
        float ix0, iz0, ix1, iz1, fx0, fz0, fx1, fz1, y0, y1;

        ix0 = p0.x;
        iz0 = p0.z;
        ix1 = p1.x;
        iz1 = p1.z;
        y0 = -0.5f;

        for (int i = 0; i < h; i++) {
            if (i == h-1) {
                System.out.println("Base case: i = " + i); 
                Point base = new Point(0f, 0.5f, 0f);
                
                addTriangle(ix0,    y0,     iz0,
                            ix1,    y0,     iz1,
                            base.x, base.y, base.z);

            } else {
                System.out.println("I is " + i);

                y1  = y0 + (float) (1/h);
                fx0 = (1 - (float) (1/h)) * ix0;
                fz0 = (1 - (float) (1/h)) * iz0;
                fx1 = (1 - (float) (1/h)) * ix1;
                fz1 = (1 - (float) (1/h)) * iz1;

                addTriangle(ix0, y0, iz0,
                            ix1, y0, iz1,
                            fx0, y1, fz0);

                addTriangle(ix1, y0, iz1,
                            fx1, y1, fz1,
                            fx0, y1, fz0);


                y0  = y1;
                ix0 = fx0;
                iz0 = fz0;
                ix1 = fx1;
                iz1 = fz1;
            }
        }
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
