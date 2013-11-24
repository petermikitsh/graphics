// Light color
uniform vec4 lightColor;

// Diffuse reflection color
uniform vec4 diffuseColor;

// Vectors "attached" to vertex and get sent to fragment shader
varying vec3 lPos;
varying vec3 vPos;
varying vec3 vNorm;


void main()
{        
    // calculate your vectors
    vec3 L = normalize (lPos - vPos);
    vec3 N = normalize (vNorm);
    
     // calculate components
    vec4 diffuse = lightColor * diffuseColor * (dot(N, L));

    // set the final color
    gl_FragColor = diffuse;

}
