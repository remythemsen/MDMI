import java.util.ArrayList;
public class Cluster {
	public Point centroid;
	public ArrayList<Point> points;
	public int Id;

	// initialize
	public Cluster(int id) {
		this.Id = id;
		this.centroid = null;
		this.points = new ArrayList<Point>();
	}
}
