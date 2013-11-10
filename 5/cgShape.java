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


    /**
     * makeLameCube - creates triangles for a pretty lame cube...
     * Only use for this assignment.
     *
     */
    private void makeLameCube()
    {
        addTriangle (-0.5f,-0.5f,-0.5f,-0.5f,-0.5f,0f,-0.5f,0f,0f);
        addTriangle (-0.5f,0f,0f,-0.5f,0f,-0.5f,-0.5f,-0.5f,-0.5f);
        addTriangle (-0.5f,0f,-0.5f,-0.5f,0f,0f,-0.5f,0.5f,0f);
        addTriangle (-0.5f,0.5f,0f,-0.5f,0.5f,-0.5f,-0.5f,0f,-0.5f);
        addTriangle (-0.5f,-0.5f,0f,-0.5f,-0.5f,0.5f,-0.5f,0f,0.5f);
        addTriangle (-0.5f,0f,0.5f,-0.5f,0f,0f,-0.5f,-0.5f,0f);
        addTriangle (-0.5f,0f,0f,-0.5f,0f,0.5f,-0.5f,0.5f,0.5f);
        addTriangle (-0.5f,0.5f,0.5f,-0.5f,0.5f,0f,-0.5f,0f,0f);
        addTriangle (0.5f,-0.5f,-0.5f,0.5f,0f,-0.5f,0.5f,0f,0f);
        addTriangle (0.5f,0f,0f,0.5f,-0.5f,0f,0.5f,-0.5f,-0.5f);
        addTriangle (0.5f,0f,-0.5f,0.5f,0.5f,-0.5f,0.5f,0.5f,0f);
        addTriangle (0.5f,0.5f,0f,0.5f,0f,0f,0.5f,0f,-0.5f);
        addTriangle (0.5f,-0.5f,0f,0.5f,0f,0f,0.5f,0f,0.5f);
        addTriangle (0.5f,0f,0.5f,0.5f,-0.5f,0.5f,0.5f,-0.5f,0f);
        addTriangle (0.5f,0f,0f,0.5f,0.5f,0f,0.5f,0.5f,0.5f);
        addTriangle (0.5f,0.5f,0.5f,0.5f,0f,0.5f,0.5f,0f,0f);
        addTriangle (-0.5f,-0.5f,-0.5f,0f,-0.5f,0f,-0.5f,-0.5f,0f);
        addTriangle (-0.5f,-0.5f,-0.5f,0f,-0.5f,-0.5f,0f,-0.5f,0f);
        addTriangle (0f,-0.5f,-0.5f,0.5f,-0.5f,0f,0f,-0.5f,0f);
        addTriangle (0f,-0.5f,-0.5f,0.5f,-0.5f,-0.5f,0.5f,-0.5f,0f);
        addTriangle (-0.5f,-0.5f,0f,0f,-0.5f,0.5f,-0.5f,-0.5f,0.5f);
        addTriangle (-0.5f,-0.5f,0f,0f,-0.5f,0f,0f,-0.5f,0.5f);
        addTriangle (0f,-0.5f,0f,0.5f,-0.5f,0.5f,0f,-0.5f,0.5f);
        addTriangle (0f,-0.5f,0f,0.5f,-0.5f,0f,0.5f,-0.5f,0.5f);
        addTriangle (-0.5f,0.5f,-0.5f,-0.5f,0.5f,0f,0f,0.5f,0f);
        addTriangle (-0.5f,0.5f,-0.5f,0f,0.5f,0f,0f,0.5f,-0.5f);
        addTriangle (0f,0.5f,-0.5f,0f,0.5f,0f,0.5f,0.5f,0f);
        addTriangle (0f,0.5f,-0.5f,0.5f,0.5f,0f,0.5f,0.5f,-0.5f);
        addTriangle (-0.5f,0.5f,0f,-0.5f,0.5f,0.5f,0f,0.5f,0.5f);
        addTriangle (-0.5f,0.5f,0f,0f,0.5f,0.5f,0f,0.5f,0f);
        addTriangle (0f,0.5f,0f,0f,0.5f,0.5f,0.5f,0.5f,0.5f);
        addTriangle (0f,0.5f,0f,0.5f,0.5f,0.5f,0.5f,0.5f,0f);
        addTriangle (-0.5f,-0.5f,0.5f,0f,-0.5f,0.5f,0f,0f,0.5f);
        addTriangle (-0.5f,-0.5f,0.5f,0f,0f,0.5f,-0.5f,0f,0.5f);
        addTriangle (0f,-0.5f,0.5f,0.5f,-0.5f,0.5f,0.5f,0f,0.5f);
        addTriangle (0f,-0.5f,0.5f,0.5f,0f,0.5f,0f,0f,0.5f);
        addTriangle (-0.5f,0f,0.5f,0f,0f,0.5f,0f,0.5f,0.5f);
        addTriangle (-0.5f,0f,0.5f,0f,0.5f,0.5f,-0.5f,0.5f,0.5f);
        addTriangle (0f,0f,0.5f,0.5f,0f,0.5f,0.5f,0.5f,0.5f);
        addTriangle (0f,0f,0.5f,0.5f,0.5f,0.5f,0f,0.5f,0.5f);
        addTriangle (-0.5f,-0.5f,-0.5f,-0.5f,0f,-0.5f,0f,-0.5f,-0.5f);
        addTriangle (0f,-0.5f,-0.5f,-0.5f,0f,-0.5f,0f,0f,-0.5f);
        addTriangle (0f,-0.5f,-0.5f,0f,0f,-0.5f,0.5f,-0.5f,-0.5f);
        addTriangle (0.5f,-0.5f,-0.5f,0f,0f,-0.5f,0.5f,0f,-0.5f);
        addTriangle (-0.5f,0f,-0.5f,-0.5f,0.5f,-0.5f,0f,0f,-0.5f);
        addTriangle (0f,0f,-0.5f,-0.5f,0.5f,-0.5f,0f,0.5f,-0.5f);
        addTriangle (0f,0f,-0.5f,0f,0.5f,-0.5f,0.5f,0f,-0.5f);
        addTriangle (0.5f,0f,-0.5f,0f,0.5f,-0.5f,0.5f,0.5f,-0.5f);
    }


    /**
     * makeDefaultShape - creates a "unit" shape of your choice using
     * your tesselation routines.  If you don't have working tessellation
     * code for any of the shapes, you can use the supplied
     * makeLameCube() routine.
     *
     */
    public void makeDefaultShape ()
    {
        makeLameCube();
    }

}
