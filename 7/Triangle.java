import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import static java.util.Arrays.asList;

/* Triangle ADT: A store of three points.
 * Author: Peter Mikitsh pam3961
*/
class Triangle {

    /* Points: Intended usage of three points in the list. */
    List<Point> points;

    /* Constructor */
    public Triangle(List<Point> points) {
        this.points = points;
    }
    
    /* Convenience accessor for points. */
    public Point get (int i) {
        return points.get(i);
    }

    /* Splits a triangle into four triangles, using the midpoint
       of the three line segments formed from the three points. */
    public List<Triangle> subdivide() {

        List<Triangle> list = new ArrayList<Triangle>(4);
        Point m01 = new Point(midX(0), midY(0), midZ(0));
        Point m12 = new Point(midX(1), midY(1), midZ(1));
        Point m02 = new Point(midX(2), midY(2), midZ(2));

        m01.normalize();
        m12.normalize();
        m02.normalize();

        list.add(new Triangle(asList(get(0), m02,    m01)));
        list.add(new Triangle(asList(m02,    m12,    m01)));
        list.add(new Triangle(asList(m12,    get(1), m01)));
        list.add(new Triangle(asList(m02,    get(2), m12)));   

        return list;
    }

    /* Find the mid X coordinate between the ith and i+1th point. */
    public float midX(int i) {
        int j;
        j = i >= 2 ? 0 : i+1;
        return (points.get(i).x + points.get(j).x) / 2.0f;
    }

    /* Find the mid Y coordinate between the ith and i+1th point. */
    public float midY(int i) {
        int j;
        j = i >= 2 ? 0 : i+1;
        return (points.get(i).y + points.get(j).y) / 2.0f;
    }

    /* Find the mid Z coordinate between the ith and i+1th point. */
    public float midZ(int i) {
        int j;
        j = i >= 2 ? 0 : i+1;
        return (points.get(i).z + points.get(j).z) / 2.0f;
    }
}