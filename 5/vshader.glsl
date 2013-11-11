attribute vec4 vPosition;
attribute float projType;

// scale 4.0x on the y axis
void scaleM(out mat4 scale)
{
  scale = mat4(
   1.0, 0.0, 0.0, 0.0,
   0.0, 4.0, 0.0, 0.0,
   0.0, 0.0, 1.0, 0.0,
   0.0, 0.0, 0.0, 1.0
  );
}

void rotateM(out mat4 rotate)
{
  // column major: rotate about z 90 degrees
  mat4 m0 = mat4(
   cos(90.0),  sin(90.0),  0.0, 0.0,
   -sin(90.0), cos(90.0),  0.0, 0.0,
   0.0,        0.0,        1.0, 0.0,
   0.0,        0.0,        0.0, 1.0
  );

  // column major: rotate about y 50 degrees
  mat4 m1 = mat4(
   cos(50.0), 0.0, -sin(50.0), 0.0,
   0.0,       1.0, 0.0,        0.0,
   sin(50.0), 0.0, cos(50.0),  0.0,
   0.0,       0.0, 0.0,        1.0
  );

  rotate = m0 * m1;
}

// translate by  1.0 in the x direction
// translate by -1.0 in the z direction
void translateM(out mat4 translate)
{
  translate = mat4(
   1.0, 0.0, 0.0,  0.0,
   0.0, 1.0, 0.0,  0.0,
   0.0, 0.0, 1.0,  0.0,
   1.0, 0.0, -1.0, 1.0
  );

}

// calculate the view transformation matrix
void viewT(out mat4 view)
{
  vec3 eye    = normalize(vec3(0.0, 3.0, 3.0));
  vec3 lookat = normalize(vec3(1.0, 0.0, 0.0));
  vec3 up     = normalize(vec3(0.0, 1.0, 0.0));

  vec3 n  = eye - lookat;
  vec3 u  = normalize(cross(up, n));
  vec3 v0 = normalize(cross(n, u));

  float du = -1.0 * dot(u,  eye);
  float dv = -1.0 * dot(v0, eye);
  float dn = -1.0 * dot(n,  eye);

  view = mat4 (u.x, v0.x, n.x, 0.0,
               u.y, v0.y, n.y, 0.0,
               u.z, v0.z, n.z, 0.0,
               du,  dv,   dn,  1.0);

}

void frustumT(out mat4 projection) {

  float left   = -1.5, 
        right  = 1.0,
        top    = 1.5,
        bottom = -1.5,
        near   = 1.0,
        far    = 8.5;

  float m11 = (2.0 * near) / (right - left);
  float m12 = 0.0;
  float m13 = (right + left) / (right - left);
  float m14 = 0.0;

  float m21 = 0.0;
  float m22 = (2.0 * near) / (top - bottom);
  float m23 = (top + bottom) / (top - bottom);
  float m24 = 0.0;

  float m31 = 0.0;
  float m32 = 0.0;
  float m33 = -1.0 * ((far + near) / (far - near));
  float m34 = (-2.0 * far * near) / (far - near);

  float m41 = 0.0;
  float m42 = 0.0;
  float m43 = -1.0;
  float m44 = 0.0;

  projection = mat4   (m11, m21, m31, m41,
                       m12, m22, m32, m42,
                       m13, m23, m33, m43,
                       m14, m24, m34, m44);

}

void orthographicT(out mat4 projection) {

  float left   = -1.5, 
        right  = 1.0,
        top    = 1.5,
        bottom = -1.5,
        near   = 1.0,
        far    = 8.5;

  float m11 = 2.0 / (right - left);
  float m12 = 0.0;
  float m13 = 0.0;
  float m14 = -1.0 * (right + left) / (right - left);

  float m21 = 0.0;
  float m22 = 2.0 / (top - bottom);
  float m23 = 0.0;
  float m24 = -1.0 * (top + bottom) / (top - bottom);

  float m31 = 0.0;
  float m32 = 0.0;
  float m33 = -2.0 / (far * near);
  float m34 = -1.0 * ((far + near) / (far - near));

  float m41 = 0.0;
  float m42 = 0.0;
  float m43 = 0.0;
  float m44 = 1.0;

  projection = mat4 ( m11, m21, m31, m41,
                       m12, m22, m32, m42,
                       m13, m23, m33, m43,
                       m14, m24, m34, m44);

}


void main()
{
    mat4 scale, rotate, translate, view, projection;

    scaleM(scale);
    rotateM(rotate);
    translateM(translate);
    viewT(view);
    if (projType == 0.0) {
      frustumT(projection);
    } else {
      orthographicT(projection);
    }
    gl_Position = normalize( projection * view * translate * rotate * scale * normalize(vPosition) );

}
