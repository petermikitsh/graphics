/* Point
 * author: peter mikitsh pam3961
*/

class Point {
    float x, y, z;
    public Point(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /* Normalize the point. */
    public void normalize() {
        float d = (float) Math.sqrt((x * x) + (y * y) + (z * z));
        if (d != 0.0) {
            x /= d;
            y /= d;
            z /= d;
        }
        scale();
    }

    /* Scale: used to reduce icosahedron by half. */
    public void scale() {
        x /= 2;
        y /= 2;
        z /= 2; 
    }

    /* Calculate spherical mapping coordinates.
     * Functions from http://www.mvps.org/directx/articles/spheremap.htm
    */ 

    public float u() {
        return (float) (Math.asin(x) / Math.PI + 0.5f); 
    }

    public float v() {
        return (float) (Math.asin(y) / Math.PI + 0.5f);
    }
}