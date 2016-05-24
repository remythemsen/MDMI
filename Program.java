import java.util.Arrays;
import java.util.ArrayList;
import java.io.IOException;
import data.*;

public class Program {
	public static void main(String[] args) {
		boolean debug = true;
		System.out.println("Application Started..");

		// Parse From File
		try {
			String[][] tuples = CSVFileReader.readDataFile("DataMining 2016 (Responses).csv",";", "-", false);
			System.out.println("Data Loaded..");


			// Convert to Data Objects
			ArrayList<RawDataObject> objectTuples = DataConverter.convert(tuples);

			System.out.println("Data Converted to Objects..");
			System.out.println("Size: " + objectTuples.size());

			// Send to Cleaner
			ArrayList<RawDataObject> cleanedTuples = Cleaner.clean(objectTuples);
			System.out.println("Data Cleaned..");

			//if(debug) {
				//for(RawDataObject o : cleanedTuples) {
					//System.out.println(Arrays.toString(o.RandomNumbers));
				//}
			//}

			System.out.println("K-Means Clusters");
			// Clustering K - MEANS on Age 
			ArrayList<Cluster> clusters = KMeans.run(cleanedTuples, 3);


			for(Cluster c : clusters) {
				System.out.println("Cluster: "+c.Id);
				for(Point p : c.points) {
					System.out.print("{"+p.X + ","+p.Y+"}, ");
				}
				System.out.println("\n");
			}

			System.out.println("K-Medoid Clusters");
			// Clustering K - Medoids on Age 
			KMedoids km = new KMedoids(3, 100);
			ArrayList<Cluster> mediodClusters = km.run(cleanedTuples);

			for(Cluster c : mediodClusters) {
				System.out.println("Cluster: "+c.Id);
				for(Point p : c.points) {
					System.out.print("{"+p.X + ","+p.Y+"}, ");
				}
				System.out.println("\n");
			}
			// Frequent Pattern Mining Apriori on Programming Langs
			Apriori.run();

			// Supervised Learning ID3 on predicting / classifying a tuple to have (nominal value, 

			// constructing tree eagerly
			ID3 ID3Alg = new ID3(objectTuples);

			RawDataObject testObject = new RawDataObject();
			testObject.Gender = gender.female;
			testObject.Degree = degree.games_t;
			System.out.println(ID3Alg.GetTree().Classify(testObject));
			System.out.println(ID3Alg.GetTree().PrettyPrint());

			// Generate test tuples
			//TODO Make generator

			//ID3Alg.TestAccuracy(objectTuples);


		} catch (IOException e) {
			System.err.println(e.getLocalizedMessage());
		}





	}
}
