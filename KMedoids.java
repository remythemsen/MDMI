import java.util.Random;
import java.util.ArrayList;
public class KMedoids {
	private Random rnd;
	private int numberOfClusters, maxIterations;


	public KMedoids(int numberOfClusters, int maxIterations) {
		this.rnd = new Random(System.currentTimeMillis());
		this.numberOfClusters = numberOfClusters;
		this.maxIterations = maxIterations;
	}

	public ArrayList<Cluster> run(ArrayList<RawDataObject> objs) {
		ArrayList<Cluster> clusters = new ArrayList<Cluster>();
		ArrayList<Point> points = convertToPoints(objs);
		Point[] medoids = new Point[numberOfClusters];


		// 1: Init, Randomly select k of n data points as the medoids
		// 2: Associate each data point to the closest mediod
		// 3: for each medoid m
		// 			for each non-medoid data point o
		// 				swap m and o and compute the total cost of the configuration
		// 4: Select the configuration with the lowest cost.
		// 4: Repeat steps 2 to 4 until there is no change in the medoid

		// Step 1
		for (int i = 0; i < numberOfClusters; i++) {
			int random = this.rnd.nextInt(points.size());
			medoids[i] = points.get(random);
		}

		int c = 0;
		boolean different = true;

		while(different && c < maxIterations) {
			c++;
			different = false;
			// Step 2
			int[] assignedTo = assign(medoids, points);

			// Step 3			
			different = calculateMedoids(assignedTo, medoids, clusters, points);

		}

		return clusters;

	}

	private boolean calculateMedoids(int[] assignedTo, Point[] medoids, ArrayList<Cluster> clusters, ArrayList<Point> points) {
		boolean different = false;
		for(int i = 0 ; i < numberOfClusters; i++) {
			clusters.add(new Cluster(i));
			for(int j = 0; j < assignedTo.length; j++) {
				if(assignedTo[j] == i) {
					clusters.get(i).points.add(points.get(j));
				}
			}

			if(clusters.get(i).points.size() == 0) {
				medoids[i] = points.get(rnd.nextInt(points.size()));
				different = true;

			} else {
				Point centroid = averagePoint(points);
				Point oldMedoid = medoids[i];
				medoids[i] = getClosestPoint(centroid, points); // get 

				if(medoids[i] != oldMedoid) {
					different = true;
				}
			}

		}

		return different;
	}

	// Return the closest point to p found in points list
	private static Point getClosestPoint(Point centroid, ArrayList<Point> points) {
		double dist = Double.POSITIVE_INFINITY;
		Point closestPoint = points.get(0);

		for(Point p : points) {
			if(centroid.distanceTo(p) < dist) {
				dist = centroid.distanceTo(p);
				closestPoint = p;
			}
		}

		return closestPoint;
	}

	// Step 2
	private static int[] assign(Point[] medoids, ArrayList<Point> points) {
		int[] result = new int[points.size()];
		for(int i = 0; i < points.size(); i++) {
			double dist = points.get(i).distanceTo(medoids[0]);
			int index = 0;

			for(int j = 1;  j < medoids.length; j++) {
				double tmpDist = points.get(i).distanceTo(medoids[j]);
				if(tmpDist < dist) {
					dist = tmpDist;
					index = j;
				}
			}
			result[i] = index;
		}
		return result;
	}
	//returns the centroid of a dataset of points // simplified to only have 2 axis
	private static Point averagePoint(ArrayList<Point> points) {
		double sumX = 0;
		double sumY = 0;
		for(Point p : points) {
			sumX+=p.X;
			sumY+=p.Y;
		}

		return new Point(sumX/points.size(), sumY/points.size());
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
