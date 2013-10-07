//
//  Rasterizer.java
//  
//
//  Created by Joe Geigel on 1/21/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

/**
 * 
 * This is a class that performas rasterization algorithms
 *
 */

import java.util.*;

public class Rasterizer {
    
    /**
     * number of scanlines
     */
    int n_scanlines;

    Hashtable<Integer, ArrayList<Rasterizer.Bucket>> ET;
    ArrayList<Bucket> AEL;

    class Bucket implements Comparable<Bucket> {
        Integer ymax, x, dx, dy, sum;
        boolean positive_slope;

        // First compare x; then dx; then dy.
        public int compareTo(Bucket that) {
            int value1 = this.x.compareTo(that.x);
            if (value1 == 0) {
                int value2 = this.dx.compareTo(that.dx);
                if (value2 == 0) {
                    return this.dy.compareTo(that.dy);
                } else {
                    return value2;
                }
            } else {
                return value1;
            }
        }

        public void print() {
            System.out.printf("ymax: %d, x: %d, +/-: %b, dx: %d, dy: %d, sum: %d\n", ymax, x, positive_slope, dx, dy, sum);
        }
    }
    
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
     * Draw a filled polygon in the simpleCanvas C.
     *
     * The polygon has n distinct vertices. The 
     * coordinates of the vertices making up the polygon are stored in the 
     * x and y arrays.  The ith vertex will have coordinate  (x[i], y[i])
     *
     * You are to add the implementation here using only calls
	 * to C.setPixel()
     */
    public void drawPolygon(int n, int x[], int y[], simpleCanvas C)
    {
        ET = new Hashtable<Integer, ArrayList<Rasterizer.Bucket>>();
        populateET(n, x, y);
        printET();
        AEL = new ArrayList<Bucket>();
        drawPolygonImpl(C);
    }

    public void drawPolygonImpl(simpleCanvas C) {
        int y = firstNonEmptyBucketIndex();
        while (AEL.size() > 0 || ET.size() > 0) {
            discardAELEntries(y);
            moveFromETtoAEL(y);
            Collections.sort(AEL);
            printAEL(y);
            fillPixelsOnScanLine(y, C);
            y++;
            nonVerticalAELEdges();
        }

    }

    private void populateET(int n, int x[], int y[]) {
        int j;
        System.out.println("Populate ET:");
        //System.out.printf("n: %d, x[]: %d\n", n, x.length);
        // n - 2: -1 to offset index; another -1 to end loop on the i-1th element
        for (int i = 0; i <= n-1; i++) {
            // choose the next element unless we are the last element-- then pick the first (i==0).
            j = (i + 1 < n) ? i + 1 : 0;
            
            if (y[i] < y[j]) {
                addEdgeToET(x[i], y[i], x[j], y[j]);
            } else if (y[j] < y[i]) {
               addEdgeToET(x[j], y[j], x[i], y[i]);
            } else { // y[i] == y[j]
               addEdgeToET(Math.min(x[i], x[j]), y[i], Math.max(x[i], x[j]), y[j]);
            }
        }
    }

    private void addEdgeToET(int x1, int y1, int x2, int y2) {

        System.out.printf("Adding edge %d, %d, %d, %d\n", x1, y1, x2, y2);
        // Instantiate bucket and populate instance variables
        Bucket b = new Bucket();
        b.ymax = Math.max(y1, y2);
        if (y1 < y2) {
            b.x = x1;
        } else if (y2 < y1) {
            b.x = x2;
        } else { // y1 == y2
            b.x = Math.min(x1, x2);
        }
        b.dx = Math.abs(x2-x1);
        b.dy = Math.abs(y2-y1);
        if (y2-y1 < 0 ^ x2-x1 < 0) {
            b.positive_slope = false;
        } else {
            b.positive_slope = true;
        }
        b.sum = 0;

        // Add bucket to hashtable
        ArrayList<Bucket> array;
        if (ET.containsKey(Math.min(y1, y2))) {
            array = ET.get(Math.min(y1, y2));
            array.add(b);
        } else {
            array = new ArrayList<Bucket>();
            array.add(b);
            ET.put(Math.min(y1, y2), array);
        }
        b.print();
    }

    private void printETKeys() {
        ArrayList<Integer> keys = Collections.list(ET.keys());
        for (Integer i : keys) {
            System.out.printf("%d ", i);
        }
        System.out.println("\n");
    }

    private int firstNonEmptyBucketIndex() {
        int min = Integer.MAX_VALUE;
        ArrayList<Integer> keys = Collections.list(ET.keys());
        for (Integer i : keys) {
            if (i < min) {
                min = i;
            }
        }
        return min;
    }

    private void discardAELEntries(int y) {
        System.out.printf("AEL Size: %d\n", AEL.size());
        Iterator<Bucket> iter = AEL.iterator();
        while (iter.hasNext()) {
            if (y >= iter.next().ymax) {
                iter.remove();
            }
        }
        System.out.printf("AEL Size: %d\n\n", AEL.size());
    }

    private void moveFromETtoAEL(int y) {
        System.out.printf("ET Size: %d\n", ET.size());
        if (ET.containsKey(y)) {
            ArrayList<Bucket> array = ET.get(y);
            for (Bucket b : array) {
                AEL.add(b);
            }
            ET.remove(y);
        }
        System.out.printf("ET Size: %d\n\n", ET.size());
    }

    private void fillPixelsOnScanLine(int y, simpleCanvas C) {
        // Iterate all even indexes; except the last one
        for (int i = 0; i <= AEL.size() - 2; i++) {
            System.out.println("Print pixels from x=" + AEL.get(i).x + " to x=" + AEL.get(i+1).x);
            for (int x = AEL.get(i).x; x <= AEL.get(i+1).x; x++) {
                C.setPixel(x, y);
            }
        }
    }

    private void nonVerticalAELEdges() {
        // For each non-vertical edge
        for (Bucket b : AEL) {
            if (b.dx != 0 && b.dy != 0) {
                b.sum = b.sum + b.dx;
                if (b.sum >= b.dy) {
                    System.out.println("here! b.sum:" + b.sum + " b.dy:" + b.dy);
                    int change = b.sum / b.dy;
                    b.sum = b.sum % b.dy;
                    if (b.positive_slope) {
                        b.x = b.x + change;
                    }
                    else {
                        b.x = b.x - change;
                    }
                }
            }
        }
    }

    private void printAEL(int y) {
        System.out.println("---AEL--- (y=" + y +")");
        for (Bucket b : AEL) {
            b.print();
        }
        System.out.println();
    }

    private void printET() {
        Iterator it = ET.keySet().iterator();
        while (it.hasNext()) {
            Integer key = (Integer) it.next();
            System.out.printf("\nScanline %d:\n\n", key);
            for (Bucket b : ET.get(key)) {
                b.print();
            } 
        }
    }
    
}
