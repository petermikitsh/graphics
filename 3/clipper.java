//  Clipper.java
//  
//
//  Created by Joe Geigel on 1/21/10.
//  Author: Peter Mikitsh pam3961
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Object for performing clipping.
 */

public class clipper {

    /* Enum declaration. */
    public enum BOUNDARY_TYPE {
        LEFT, RIGHT, TOP, BOTTOM;
    }

    public class Boundary {
        /* Vertices of the boundary (line segment).
           v1 is always to the left and bottom of v2. */
        Vertex v1, v2;

        /* Type of boundary. */
        BOUNDARY_TYPE boundary;

        /* Class constructor. */
        public Boundary(Vertex lower, Vertex upper, BOUNDARY_TYPE boundary) {
            this.v1 = lower;
            this.v2 = upper;
            this.boundary = boundary;
        }

        /* Determines if a vertex is inside or outside the clipping region by comparing
           the appropriate vertex axis and boundary axis.
        */
        public boolean inside(Vertex v) {
            switch (boundary) {
                case LEFT:      return v.x > v1.x; 
                case RIGHT:     return v.x < v1.x;
                case TOP:       return v.y < v1.y;
                case BOTTOM:    return v.y > v1.y;
                default: throw new IllegalArgumentException("Boundary not specified.");
            }
        }

        /* Finds the intersection point between the boundary line segment (v1 & v2)
           and the given vertices (p & s).
        */
        public Vertex intersect(Vertex p, Vertex s) {

            float d = (v2.x-v1.x) * (s.y-p.y) - (v2.y-v1.y) * (s.x-p.x);
            if (d == 0) return null;

            float vx = ((s.x-p.x)*(v2.x*v1.y-v2.y*v1.x)-(v2.x-v1.x)*(s.x*p.y-s.y*p.x))/d;
            float vy = ((s.y-p.y)*(v2.x*v1.y-v2.y*v1.x)-(v2.y-v1.y)*(s.x*p.y-s.y*p.x))/d;

            return new Vertex(vx, vy);

        }
    }

    /* Result object returned by each step of the Sutherland-Hodgman algorithm
       (to simply passing state between each clipping call). */
    public class Result {
        public float inx[], iny[], outx[], outy[];
        public int in, out;

        /* Constructor. */
        public Result( float inx[], float iny[], float outx[], float outy[],
                    int in, int out) {
            this.inx = inx;
            this.iny = iny;
            this.outx = outx;
            this.outy = outy;
            this.in = in;
            this.out = out;
        }
    }

    /* Vertex Data Type: Contains a set of Cartesian points. */
    public class Vertex {
        float x, y;

        public Vertex(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
    
    /**
     * clipPolygon
     * 
     * Clip the polygon with vertex count in and vertices inx/iny
     * against the rectangular clipping region specified by lower-left corner
     * (x0,y0) and upper-right corner (x1,y1). The resulting vertices are
     * placed in outx/outy.  
     * 
     * The routine should return the with the vertex count of polygon
     * resultinhg from the clipping.
     *
     * @param in the number of vertices in the polygon to be clipped
     * @param inx - x coords of vertices of polygon to be clipped.
     * @param int - y coords of vertices of polygon to be clipped.
     * @param outx - x coords of vertices of polygon resulting after clipping.
     * @param outy - y coords of vertices of polygon resulting after clipping.
     * @param x0 - x coord of lower left of clipping rectangle.
     * @param y0 - y coord of lower left of clipping rectangle.
     * @param x1 - x coord of upper right of clipping rectangle.
     * @param y1 - y coord of upper right of clipping rectangle.
     *
     * @return number of vertices in the polygon resulting after clipping
     * 
     */
    public int clipPolygon(int in, float inx[], float iny[], float outx[], 
                    float outy[], float x0, float y0, float x1, float y1)
    {

        /* Defines an initial result object and continues to run the Sutherland-
           Hodgman until no clipping remain. */
        Iterator<Boundary> it = getIterator(x0, y0, x1, y1);
        Result r = SHPC(inx, iny, outx, outy, in, 0, it.next());

        /* Check that there remaining vertices to clip. If r.out is zero, then
           the polgyon lies completely outside the clipping region.*/
        while (it.hasNext() && r.out > 0) {
            r = SHPC(r.inx, r.iny, r.outx, r.outy, r.in, r.out, it.next());
        }

        return r.out;
    }

    /* Sutherland-Hodgman Polygon Clipper
       inx[]: x coords of vertices of polygon to be clipped.
       iny[]: y coords of vertices of polygon to be clipped.
       outx[]: x coords of vertices of polygon resulting after clipping.
       outy[]: y coords of vertices of polygon resulting after clipping.
       in: the number of vertices in the polygon to be clipped.
       out: the number of vertices after the polygon is clipped.
    */
    public Result SHPC(float inx[], float iny[], float outx[], float outy[],
                    int in, int out, Boundary b) {

        // Initialize the out array size and the first vertex to the last vertex
        // in the input array.
        out = 0;
        Vertex p = new Vertex(inx[in-1], iny[in-1]);

        // Compare each pair of vertices with respect to the given boundary.
        for (int j = 0; j < in; j++) {

            Vertex s = new Vertex(inx[j], iny[j]);

            if (b.inside(s)) {
                if (b.inside(p)) {
                    // Both vertices inside region
                    output(s, outx, outy, out);
                    out++;
                } else {
                    // Previous outside region;
                    // Successor inside region.
                    Vertex i = b.intersect(p, s);
                    output(i, outx, outy, out);
                    out++;
                    output(s, outx, outy, out);
                    out++;
                }
            } else {
                if (b.inside(p)) {
                    // Previous inside region;
                    // Successor outside region.
                    Vertex i = b.intersect(p, s);
                    output(i, outx, outy, out);
                    out++;
                }
                // Else: Both outside region. No output.
            }
            p = s;
        }
        // Return copies of new incoming arrays with the outgoing lengths.
        return new Result(outx.clone(), outy.clone(), outx, outy, out, out);

    }

    /* Returns an iterator of a list of bounding line segments to clip to. */
    public Iterator<Boundary> getIterator(float x0, float y0, float x1, float y1)
    {
        ArrayList<Boundary> bounds = new ArrayList<Boundary>();
        bounds.add(new Boundary(new Vertex(x0, y0), new Vertex(x0, y1), BOUNDARY_TYPE.LEFT));
        bounds.add(new Boundary(new Vertex(x1, y0), new Vertex(x1, y1), BOUNDARY_TYPE.RIGHT));
        bounds.add(new Boundary(new Vertex(x0, y1), new Vertex(x1, y1), BOUNDARY_TYPE.TOP));
        bounds.add(new Boundary(new Vertex(x0, y0), new Vertex(x1, y0), BOUNDARY_TYPE.BOTTOM));
        return bounds.iterator();
    }

    /* Helper function to add Vertex v to the out axis arrays at index out. */
    public void output(Vertex v, float outx[], float outy[], int out)
    {
        outx[out] = v.x;
        outy[out] = v.y;
    }
}
