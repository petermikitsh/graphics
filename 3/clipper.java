//  Peter Mikitsh
//  Clipper.java
//  
//
//  Created by Joe Geigel on 1/21/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Object for performing clipping
 *
 */

public class clipper {

    public enum BOUNDARY_TYPE {
        LEFT, RIGHT, TOP, BOTTOM;
    }

    public class Boundary {
        Vertex v1, v2;
        BOUNDARY_TYPE boundary;

        public Boundary(Vertex lower, Vertex upper, BOUNDARY_TYPE boundary) {
            this.v1 = lower;
            this.v2 = upper;
            this.boundary = boundary;
        }

        public boolean inside(Vertex v) {
            switch (boundary) {
                case LEFT:      return v.x > v1.x; 
                case RIGHT:     return v.x < v1.x;
                case TOP:       return v.y < v1.y;
                case BOTTOM:    return v.y > v1.y;
                default: throw new IllegalArgumentException("Boundary not specified.");
            }
        }

        public Vertex intersect(Vertex p, Vertex s) {

            float d = (v2.x-v1.x) * (s.y-p.y) - (v2.y-v1.y) * (s.x-p.x);
            if (d == 0) return null;

            float vx = ((s.x-p.x)*(v2.x*v1.y-v2.y*v1.x)-(v2.x-v1.x)*(s.x*p.y-s.y*p.x))/d;
            float vy = ((s.y-p.y)*(v2.x*v1.y-v2.y*v1.x)-(v2.y-v1.y)*(s.x*p.y-s.y*p.x))/d;

            return new Vertex(vx, vy);

        }
    }

    public class Result {
        public float inx[], iny[], outx[], outy[];
        public int in, out;

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
        // Your implementation goes here

        Iterator<Boundary> it = getIterator(x0, y0, x1, y1);
        Result r = SHPC(inx, iny, outx, outy, in, 0, it.next());
        while (it.hasNext() && r.out > 0) {
            r = SHPC(r.inx, r.iny, r.outx, r.outy, r.in, r.out, it.next());
        }

        return r.out; // should return number of verricies in clipped poly.
    }

    public Result SHPC(float inx[], float iny[], float outx[], float outy[],
                    int in, int out, Boundary b) {

        out = 0;
        Vertex p = new Vertex(inx[in-1], iny[in-1]);

        for (int j = 0; j < in; j++) {

            Vertex s = new Vertex(inx[j], iny[j]);

            if (b.inside(s)) {
                if (b.inside(p)) {
                    output(s, outx, outy, out);
                    out++;
                } else {
                    Vertex i = b.intersect(p, s);
                    output(i, outx, outy, out);
                    out++;
                    output(s, outx, outy, out);
                    out++;
                }
            } else {
                if (b.inside(p)) {
                    Vertex i = b.intersect(p, s);
                    output(i, outx, outy, out);
                    out++;
                }
            }
            p = s;
        }

        return new Result(outx.clone(), outy.clone(), outx, outy, out, out);

    }

    public Iterator<Boundary> getIterator(float x0, float y0, float x1, float y1)
    {
        ArrayList<Boundary> bounds = new ArrayList<Boundary>();
        bounds.add(new Boundary(new Vertex(x0, y0), new Vertex(x0, y1), BOUNDARY_TYPE.LEFT));
        bounds.add(new Boundary(new Vertex(x1, y0), new Vertex(x1, y1), BOUNDARY_TYPE.RIGHT));
        bounds.add(new Boundary(new Vertex(x0, y1), new Vertex(x1, y1), BOUNDARY_TYPE.TOP));
        bounds.add(new Boundary(new Vertex(x0, y0), new Vertex(x1, y0), BOUNDARY_TYPE.BOTTOM));
        return bounds.iterator();
    }

    public void output(Vertex v, float outx[], float outy[], int out)
    {
        outx[out] = v.x;
        outy[out] = v.y;
    }
}
