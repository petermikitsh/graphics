import java.util.ArrayList;
import java.util.List;
import Jama.Matrix;

class Poly {

	private float[] x;
	private float[] y;
	private int n;

	/* Constructor */
	public Poly(float[] x, float[] y, int n) {
		this.x = x;
		this.y = y;
		this.n = n;
	}

	public float[] x() {
		return x;
	}

	public float[] y() {
		return y;
	}

	public int n() {
		return n;
	}

	public List<Matrix> matrices(Matrix transformation) {
		List<Matrix> list = new ArrayList<Matrix>();
		for (int i = 0; i < n; i++) {
			Matrix m = new Matrix(new double[][]{ { x[i] },
												  { y[i] },
												  {  1   }});
			list.add(transformation.times(m));
		}
		return list;
	}

	//public static matrixToPoly(List<Matrix> matrices) {}

}