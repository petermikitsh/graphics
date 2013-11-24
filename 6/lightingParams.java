/**
 *
 * lightingParams.java
 *
 * Simple class for setting up the viewing and projection transforms
 * for the Shading Assignment.
 *
 * Students are to complete this class.
 *
 */


import javax.media.opengl.*;
import javax.media.opengl.fixedfunc.*; 

public class lightingParams
{
    // materials / lighting data
    private float lightpos[] = {0.0f, 5.0f, 2.0f, 1.0f};
    private float lightColor[] = {1.0f, 1.0f, 0.0f, 1.0f};
    private float diffuse[] = {0.89f, 0.0f, 0.0f, 1.0f};
    
    /**
     * constructor
     */
    public lightingParams()
    {
        
    }
    /**
     * This functions sets up the lighting, material, and shading parameters
     * for the Phong shader.
     *
     * You will need to write this function, and maintain all of the values
     * needed to be sent to the vertex shader.
     *
     * @param program - The ID of an OpenGL (GLSL) shader program to which
     * parameter values are to be sent
     *
     * @param gl2 - GL2 object on which all OpenGL calls are to be made
     *
     */
    public void setUpPhong (int program, GL2 gl2)
    {
        // Here's code for the diffuse component.
        int light = gl2.glGetUniformLocation( program , "lightPosition" );
        int lightc = gl2.glGetUniformLocation( program , "lightColor" );
        int diff = gl2.glGetUniformLocation( program , "diffuseColor" );
        
        gl2.glUniform4fv( light , 1 , lightpos, 0 );
        gl2.glUniform4fv( lightc , 1 , lightColor, 0 );
        gl2.glUniform4fv( diff , 1 , diffuse, 0 );
        
        // You need to add code for the specular component

    }
}
