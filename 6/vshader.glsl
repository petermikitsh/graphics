// vshader.glsl
// author: peter mikitsh pam3961

// Vertex position (in model space)
attribute vec4 vPosition;

// Normal vector at vertex (in model space)
attribute vec3 vNormal;


// Light position is given in world space
uniform vec4 lightPosition;

// Specular Exponent
uniform float Ns;

// Specular Color
uniform vec4 Is;


// Vectors to "attach" to vertex and get sent to fragment shader
// Vectors and points will be passed in "eye" space
varying vec3 lPos;
varying vec3 vPos;
varying vec3 vNorm;


void main()
{
    // View and projection matrices. 
    // These matrices are set corresponding to the model and viewing 
    // parameters required for the assignment.
    
    mat4 modelMatrix = mat4 (1.0,  0.0,  0.0,  0.0, 
                            0.0,  1.0,  0.0,  0.0,
                            0.0,  0.0,  1.0,  0.0,
                            0.0,  0.0,  -3.0,  1.0);
                            
    mat4 viewMatrix = mat4 (1.0,  0.0,  0.0,  0.0, 
                            0.0,  1.0,  0.0,  0.0,
                            0.0,  0.0,  1.0,  0.0,
                            0.0,  0.0,  0.0,  1.0);
                            
    mat4 projMatrix = mat4 (1.0,  0.0,  0.0,  0.0, 
                            0.0,  1.0,  0.0,  0.0,
                            0.0,  0.0,  0.11,  0.0,
                            0.0,  0.0,  0.0,  1.0);
                            
    mat4 modelViewMatrix = viewMatrix * modelMatrix;
    
    // commented out as my lame version of GLSL on my machine does
    // not support the inverse function.  See fix below
    //mat4 normalMatrix = transpose (inverse (modelViewMatrix));
    
    // All vectors need to be converted to "eye" space
    // All vectors should also be normalized
    vec4 vertexInEye = modelViewMatrix * vPosition;
    vec4 lightInEye = viewMatrix * lightPosition;
    
    // cheap and dirty way to transform normals
    // remember that translations should be ignored when transforming normals
    // Does not work with non uniform scaling in the modelView Matrix
    // See http://www.lighthouse3d.com/tutorials/glsl-tutorial/the-normal-matrix/ 
    vec4 normalInEye = normalize(modelViewMatrix * vec4(vNormal, 0.0));

    // pass our vertex data to the fragment shader
    lPos = lightInEye.xyz;
    vPos = vertexInEye.xyz;
    vNorm = normalInEye.xyz;
    
    // convert to clip space (like a vertex shader should)
    gl_Position =  projMatrix * viewMatrix * modelMatrix * vPosition;
}
