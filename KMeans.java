import java.util.Random;
import java.util.ArrayList;
public class KMeans {
	public static ArrayList<Cluster> run(ArrayList<RawDataObject> objs, int k) {
		int i =0; // Keeping track of iterations
		boolean done = false;
		double dist; // distance from old to new centroids
		ArrayList<Cluster> clusters = new ArrayList<Cluster>();
		ArrayList<Point> points = convertToPoints(objs);

		Random rnd = new Random();

		// Create Clusters
		for(int c = 0; c < k; c++) {
			Cluster cluster = new Cluster(c);
			//Put random centroid
			Point cent = new Point(rnd.nextDouble(), 0.0); // the random returns a value between 0-1, which is the same as the normalized range of Age or Shoesize

			cluster.centroid = cent;

			clusters.add(cluster);
		}

		while(!done) {
			// Clear the state
			for(Cluster c : clusters) {
				c.points.clear();
			}

			// Get Prev. Centroids
			ArrayList<Point> prevCents = getCents(clusters);

			// Put Points in closer clusters
			findCluster(clusters, points, k);

			// And find new centroids
			calcCents(clusters);

			// Get the current cents
			ArrayList<Point> currentCents = getCents(clusters);

			// Find total distance between old and new centroids
			dist = 0.0;
			for(int j = 0; j < prevCents.size(); j++) {
				dist+= prevCents.get(j).distanceTo(currentCents.get(j));
			}

			if(dist == 0) done = true;

		}

		return clusters;
	}
	static void calcCents(ArrayList<Cluster> clusters) {
		for(Cluster c : clusters) {
			double sumx =0.0;
			double sumy =0.0;
			ArrayList<Point> points = c.points;
			int n = points.size();
			for(Point p : points) {
				sumx+=p.X;
				sumy+=p.Y;
			}

			Point centroid = c.centroid;
			if(n > 0) {
				double newx = sumx / n;
				double newy = sumy / n;
				centroid.X=newx;
				centroid.Y=newy;
			}
		}
	}
	static void findCluster(ArrayList<Cluster> clusters, ArrayList<Point> points, int k) {
		double MAX = Double.POSITIVE_INFINITY;
		double MIN = MAX;

		int cluster = 0;
		double distance =0.0;

		for(Point p : points) {
			MIN = MAX;
			for(int i = 0; i < k; i++) {
				Cluster c = clusters.get(i);
				distance = p.distanceTo(c.centroid);

				if(distance < MIN) {
					MIN = distance;
					cluster = i;
				}
			}
			p.cluster = cluster;
			clusters.get(cluster).points.add(p);
		}
	}
	static ArrayList<Point> getCents(ArrayList<Cluster> clusters) {
		ArrayList<Point> centroids = new ArrayList<Point>();
		for(Cluster c : clusters) {
			Point temp = c.centroid;
			Point p = new Point(temp.X, temp.Y);
			centroids.add(p);
		}
		return centroids;
	}
	static ArrayList<Point> convertToPoints(ArrayList<RawDataObject> objs) {
		ArrayList<Point> points = new ArrayList<Point>();
		// The algorithm might be more generic if i make it work with points, and not specific 
		// attributs of my tuples
		for(RawDataObject o:objs) {
			// Only using the X axis for now
			points.add(new Point(o.Age, 0.0));
		}

		return points;
	}



}
