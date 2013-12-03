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
    }
    
     
    /**
     * makeDefaultShape - creates a "unit" shape of your choice using
     * your tesselation routines.
     * 
     *
     */
    public void makeDefaultShape ()
    {
        // tessellate your favorite unit shape here.
        constructIcosahedron();
        makeSphere();
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
     * Can only use calls to addTriangle
     */
    public void makeSphere()
    {

        /* Limit slices to five for recursive subdivision method. */
        int slices = 5;

        for (Triangle t : icosahedron.get(slices-1)) {
            addTriangle(t.get(0).x, t.get(0).y, t.get(0).z, 0f, 0f,
                        t.get(1).x, t.get(1).y, t.get(1).z, 0f, 0f,
                        t.get(2).x, t.get(2).y, t.get(2).z, 0f, 0f);
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
