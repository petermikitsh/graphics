/**
 * cgShape.java
 *
 * Class that includes routines for tessellating a number of basic shapes
 *
 * Students are to supply their implementations for the
 * functions in this file using the function "addTriangle()" to do the 
 * tessellation.
 *
 * author: peter mikitsh pam3961
 */

import java.awt.*;
import java.nio.*;
import java.awt.event.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import static java.util.Arrays.asList;


public class cgShape extends simpleShape
{

    HashMap<Integer, List<Triangle>> icosahedron;

    /**
	 * constructor
	 */
	public cgShape()
	{
        constructIcosahedron();
	}

    /* A point in 3d space (world coordinates). */
    static class Point {
        float x, y, z;
        public Point(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        /* Normalize the point. */
        public void normalize() {
            float d = (float) Math.sqrt((x * x) + (y * y) + (z * z));
            if (d != 0.0) {
                x /= d;
                y /= d;
                z /= d;
            }
            scale();
        }

        /* Scale: used to reduce icosahedron by half. */
        public void scale() {
            x /= 2;
            y /= 2;
            z /= 2; 
        }
    }

    /* Triangle ADT: A store of three points. */
    static class Triangle {

        /* Points: Intended usage of three points in the list. */
        List<Point> points;

        /* Constructor */
        public Triangle(List<Point> points) {
            this.points = points;
        }
        
        /* Convenience accessor for points. */
        public Point get (int i) {
            return points.get(i);
        }

        /* Splits a triangle into four triangles, using the midpoint
           of the three line segments formed from the three points. */
        public List<Triangle> subdivide() {

            List<Triangle> list = new ArrayList<Triangle>(4);
            Point m01 = new Point(midX(0), midY(0), midZ(0));
            Point m12 = new Point(midX(1), midY(1), midZ(1));
            Point m02 = new Point(midX(2), midY(2), midZ(2));

            m01.normalize();
            m12.normalize();
            m02.normalize();

            list.add(new Triangle(asList(get(0), m02,    m01)));
            list.add(new Triangle(asList(m02,    m12,    m01)));
            list.add(new Triangle(asList(m12,    get(1), m01)));
            list.add(new Triangle(asList(m02,    get(2), m12)));   

            return list;
        }

        /* Find the mid X coordinate between the ith and i+1th point. */
        public float midX(int i) {
            int j;
            j = i >= 2 ? 0 : i+1;
            return (points.get(i).x + points.get(j).x) / 2.0f;
        }

        /* Find the mid Y coordinate between the ith and i+1th point. */
        public float midY(int i) {
            int j;
            j = i >= 2 ? 0 : i+1;
            return (points.get(i).y + points.get(j).y) / 2.0f;
        }

        /* Find the mid Z coordinate between the ith and i+1th point. */
        public float midZ(int i) {
            int j;
            j = i >= 2 ? 0 : i+1;
            return (points.get(i).z + points.get(j).z) / 2.0f;
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
       Draws triangles by connecting points at Theta_i, Theta_i+1, and the center. */
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

    /* Draws the cylinder's height triangles. */
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
       Draws triangles by connecting points at Theta_i, Theta_i+1, and the center. */
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

            addTriangle( x1,   d0.y,   z1,
                         x0,   d0.y,   z0,
                         d0.x, d0.y, d0.z);

            Point p0 = new Point(x0, d0.y, z0);
            Point p1 = new Point(x1, d0.y, z1);

            makeConeHeight(p0, p1, h);

            x0 = x1;
            z0 = z1;
            theta0 = theta1;
        }

    }

    /* Draws the cone height. Calculates an initial (x,z) pair for the ith level,
      and another (x,z) pair for the i+1th level. For the last iteration, draw the
      single triangle to cone top. */
    private void makeConeHeight(Point p0, Point p1, int h) {
        float ix0, iz0, ix1, iz1, fx0, fz0, fx1, fz1, y0, y1;

        ix0 = p0.x;
        iz0 = p0.z;
        ix1 = p1.x;
        iz1 = p1.z;
        y0 = -0.5f;

        for (int i = 0; i < h; i++) {
            if (i == h-1) {
                Point base = new Point(0f, 0.5f, 0f);
                
                addTriangle(ix0,    y0,     iz0,
                            ix1,    y0,     iz1,
                            base.x, base.y, base.z);

            } else {

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

    /* Icosahedron, as defined in the lecture notes. */
    private void constructIcosahedron() {
        float a = 2f / (float) (1f + Math.sqrt(5));

        Point[] p = new Point[12];
        p[0]  = new Point( 0,  a, -1);
        p[1]  = new Point(-a,  1,  0);
        p[2]  = new Point( a,  1,  0);
        p[3]  = new Point( 0,  a,  1);
        p[4]  = new Point(-1,  0,  a);
        p[5]  = new Point( 0, -a,  1);
        p[6]  = new Point( 1,  0,  a);
        p[7]  = new Point( 1,  0, -a);
        p[8]  = new Point( 0, -a, -1);
        p[9]  = new Point(-1,  0, -a);
        p[10] = new Point(-a, -1,  0);
        p[11] = new Point( a, -1,  0);

        for (Point pt : p) {
            pt.normalize();
        }
        
        List<Triangle> d0 = new ArrayList<Triangle>();
        d0.add(new Triangle(asList(p[0],  p[1],  p[2])));
        d0.add(new Triangle(asList(p[3],  p[2],  p[1])));
        d0.add(new Triangle(asList(p[3],  p[4],  p[5])));
        d0.add(new Triangle(asList(p[3],  p[5],  p[6])));
        d0.add(new Triangle(asList(p[0],  p[7],  p[8])));
        d0.add(new Triangle(asList(p[0],  p[8],  p[9])));
        d0.add(new Triangle(asList(p[5],  p[10], p[11])));
        d0.add(new Triangle(asList(p[8],  p[11], p[10])));
        d0.add(new Triangle(asList(p[1],  p[9],  p[4])));
        d0.add(new Triangle(asList(p[10], p[4],  p[9])));
        d0.add(new Triangle(asList(p[2],  p[6],  p[7])));
        d0.add(new Triangle(asList(p[11], p[7],  p[6])));
        d0.add(new Triangle(asList(p[3],  p[1],  p[4])));
        d0.add(new Triangle(asList(p[3],  p[6],  p[2])));
        d0.add(new Triangle(asList(p[0],  p[9],  p[1])));
        d0.add(new Triangle(asList(p[0],  p[2],  p[7])));
        d0.add(new Triangle(asList(p[8],  p[10], p[9])));
        d0.add(new Triangle(asList(p[8],  p[7],  p[11])));
        d0.add(new Triangle(asList(p[5],  p[4],  p[10])));
        d0.add(new Triangle(asList(p[5],  p[11], p[6])));

        icosahedron = new HashMap<Integer, List<Triangle>>();
        icosahedron.put(0, d0);

        for (int i = 1; i <= 5; i++)
          makeSphereDivision(i);

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

        /* Limit slices to five for recursive subdivision method. */
        if (slices > 5)
          slices = 5;

        for (Triangle t : icosahedron.get(slices-1)) {
            addTriangle(t.get(0).x, t.get(0).y, t.get(0).z,
                        t.get(1).x, t.get(1).y, t.get(1).z,
                        t.get(2).x, t.get(2).y, t.get(2).z);
        }

    }

    /* makeSphereDivision: Initialize the list for the icosahedron hash map at level n.
       For each triangle in the parent subdivision, form its subdivisions and store  in the list.
       Store the list in the hash map with key n. */
    private void makeSphereDivision(int n) {
        List<Triangle> dN = new ArrayList<Triangle>(4*icosahedron.get(n-1).size());
        for (Triangle t : icosahedron.get(n-1)) {
            for (Triangle tN : t.subdivide()) {
                dN.add(tN);
            }
        }
        icosahedron.put(n, dN);
    }

}
