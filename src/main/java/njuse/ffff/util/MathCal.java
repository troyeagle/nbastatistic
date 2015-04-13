package njuse.ffff.util;

import java.math.BigDecimal;

public class MathCal {

	/**
	 * 给出形如 <code>a*x^2+b*x+c=0</code> 方程的解（不精确）
	 * 
	 * @param a
	 *            二次项系数
	 * @param b
	 *            一次项系数
	 * @param c
	 *            常数项系数
	 * @return double型数组<br />
	 *         若方程有2实根则长度为2，0处为<code>(-b+√Δ)/2a</code>，
	 *         1处为<code>(-b-√Δ)/2a</code><br />
	 *         若方程有重根则长度为1<br />
	 *         若方程无根则长度为0
	 */
	public static double[] solveQuadraticEquation(double a, double b, double c) {
		if (a == 0) {
			return new double[] { -c / b };
		}

		double delta = b * b - 4 * a * c;
		if (Math.abs(delta) < 0.00000001) {	// 视为0？
			return new double[] { -b / (2 * a) };
		} else if (delta < 0) {
			return new double[0];
		} else {
			double sqrtDelta = Math.sqrt(delta);	// √Δ
			return new double[] { (-b + sqrtDelta) / (2 * a), (-b - sqrtDelta) / (2 * a) };
		}
	}

	/**
	 * 给出形如 <code>a*x^2+b*x+c=0</code> 方程的解（精确到1000位）
	 * 
	 * @param a
	 *            二次项系数
	 * @param b
	 *            一次项系数
	 * @param c
	 *            常数项系数
	 * @return BigDecimal型数组<br />
	 *         若<code>a=0</code>则长度为1<br />
	 *         若<code>a!=0</code><br />
	 *         &nbsp;若方程有2实根则长度为2，0处为<code>(-b+√Δ)/2a</code>，
	 *         &nbsp;1处为<code>(-b-√Δ)/2a</code><br />
	 *         &nbsp;若方程有重根则长度为1<br />
	 *         &nbsp;若方程无根则长度为0
	 */
	public static BigDecimal[] solveQuadraticEquation(BigDecimal a, BigDecimal b, BigDecimal c) {
		return solveQuadraticEquation(a, b, c, 1000);
	}

	/**
	 * 给出形如 <code>a*x^2+b*x+c=0</code> 的二次方程的解
	 * 
	 * @param a
	 *            二次项系数
	 * @param b
	 *            一次项系数
	 * @param c
	 *            常数项系数
	 * @param accuracy
	 *            精确的位数
	 * @return BigDecimal型数组<br />
	 *         若<code>a=0</code>则长度为1<br />
	 *         若<code>a!=0</code><br />
	 *         &nbsp;若方程有2实根则长度为2，0处为<code>(-b+√Δ)/2a</code>，
	 *         &nbsp;1处为<code>(-b-√Δ)/2a</code><br />
	 *         &nbsp;若方程有重根则长度为1<br />
	 *         &nbsp;若方程无根则长度为0
	 */
	public static BigDecimal[] solveQuadraticEquation(BigDecimal a, BigDecimal b,
			BigDecimal c, int accuracy) {
		BigDecimal nb = b.negate();	// -b
		if (a.equals(BigDecimal.ZERO)) {	// a=0
			return new BigDecimal[] { c.divide(nb) };		// result=-c/b=c/(-b)
		}
		// delta = b^2-4ac
		BigDecimal delta = b.pow(2).subtract(a.multiply(c).multiply(new BigDecimal(4)));
		BigDecimal a2 = a.add(a);	// 2 * a
		if (delta.compareTo(BigDecimal.ZERO) < 0) {	// 无解
			return new BigDecimal[0];
		} else if (delta.equals(BigDecimal.ZERO)) {	// 重根
			return new BigDecimal[] { nb.divide(a2) };
		} else {
			// TODO sqrt BigDecimal
			return new BigDecimal[] {};
		}

	}

}
