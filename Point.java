public class Point {
	double X;
	double Y;
	int cluster;

	Point(double x, double y) {
		this.X = x;
		this.Y = y;
	}
	public double distanceTo(Point that) {
		// eucledian distance 
		return Math.sqrt(Math.pow((that.Y - this.Y), 2) + Math.pow((that.X - this.X), 2));
	}
}
