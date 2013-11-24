// Vertex position (in model space)
attribute vec4 vPosition;

// Normal vector at vertext (in model space)
attribute vec3 vNormal;

// Light position is given in world space
uniform vec4 lightPosition;

// Light color
uniform vec4 lightColor;

// Diffuse reflection color
uniform vec4 diffuseColor;

// Color to pass to fragment shader for interpolation
varying vec4 color;


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
    
    vec3 L = normalize ((lightInEye - vertexInEye).xyz);
    vec3 N = normalize (normalInEye.xyz);

    
    // calculate components
    vec4 diffuse = lightColor * diffuseColor * (dot(N, L));
          
    // convert to clip space (like a vertex shader should)
    gl_Position =  projMatrix * viewMatrix * modelMatrix * vPosition;
    
    // Calculate color and pass to fragment shader
    color = diffuse;

}
