// Light color
uniform vec4 lightColor;

// Diffuse reflection color
uniform vec4 diffuseColor;

// Specular Exponent
uniform float Ns;

// Specular Color
uniform vec4 Is;

// Vectors "attached" to vertex and get sent to fragment shader
varying vec3 lPos;
varying vec3 vPos;
varying vec3 vNorm;


void main()
{        
    // calculate your vectors
    vec3 L = normalize (lPos - vPos);
    vec3 N = normalize (vNorm);

    // specular vectors
    vec3 R = normalize(-reflect(L, N));
    vec3 V = normalize (-vPos.xyz);
    
    // calculate components
    vec4 diffuse = lightColor * diffuseColor * (dot(N, L));
    vec4 specular = Is * pow(max(0.0, dot(R, V)), Ns);

    // set the final color
    gl_FragColor = diffuse + specular;

}
