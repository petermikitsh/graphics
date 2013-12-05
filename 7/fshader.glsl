uniform sampler2D texture;
varying vec2 texCoord;

void main() 
{ 
  // replace with proper texture function
  gl_FragColor = texture2D(texture, texCoord);
} 
