// @Author Remy Themsen
// This is my take on the ID3 Algorithm implemented here in Java, using information gain
// The algorithm here is written of the version on p333 of Datamining third ed.
// This specialized version takes a set of dataTuples, and returns the how_Often_Plays_video_games enum value
import data.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ID3 {
	private DecisionTree dt;

	public ID3(ArrayList<RawDataObject> dataTuples) {
		// Make attribute list
		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add("Degree");
		attributes.add("Gender");
		//TODO Implement Continous Values
		//attributes.add("Age");

		this.dt = new DecisionTree(construct(dataTuples, attributes));
	}

	public DecisionTree GetTree() {
		return this.dt;
	}

	private Node construct(ArrayList<RawDataObject> dataPartition, ArrayList<String> attributes) {
		// recursive function to construct tree
		// Create node N
		Node n = new Node();
		if(allTheSameClass(dataPartition)) {
			// return n as a leaf node labeled with class C
			n.Label = dataPartition.get(0).HowOftenPlayVideoGames.toString();//TODO Check if tostring works
			return n;
		}
		if(attributes.size() == 0) {
			n.Label = getMajorityClass(dataPartition);
			return n;
		}
		String splittingCriterion = findBestSplittingAttribute(dataPartition, attributes);

		// Label node N with splitting criterion
		n.Label = splittingCriterion;
		// if splitting criterion is discrete valued:
		if(splittingCriterion.equals("Degree") || splittingCriterion.equals("Gender")) {
			// remove attribute from attributelist
			attributes.remove(splittingCriterion);
		}

		// For each outcome j of splitting criterion 
			// partion tuples, and grow subtrees for each partition

		// Create subsets from attribute's possible values
		ArrayList<ArrayList<RawDataObject>> subsets;

		switch(splittingCriterion) {
			case "Degree": {
				n.children = new Node[degree.values().length];
				subsets = new ArrayList<ArrayList<RawDataObject>>(degree.values().length);
				for(int i = 0; i < degree.values().length; i++) {
					subsets.add(new ArrayList<RawDataObject>());
					for(RawDataObject o : dataPartition) {
						if(o.Degree.ordinal() == i) {
							subsets.get(i).add(o);
						}
					}
					if(subsets.get(i).size() != 0) {
						// TODO Call recursively construct passing that dataset
						n.children[i] = construct(subsets.get(i), attributes);
					} else {
						Node node = new Node();
						node.Label = getMajorityClass(dataPartition);
						n.children[i] = node;
					}
				}
				break;
			}
			case "Gender": {
				n.children = new Node[gender.values().length];
				subsets = new ArrayList<ArrayList<RawDataObject>>(gender.values().length);
				for(int i = 0; i < gender.values().length; i++) {
					subsets.add(new ArrayList<RawDataObject>());
					for(RawDataObject o : dataPartition) {
						if(o.Gender.ordinal() == i) {
							subsets.get(i).add(o);
						}
					}
					if(subsets.get(i).size() != 0) {
						// TODO Call recursively construct passing that dataset
						n.children[i] = construct(subsets.get(i), attributes);
					} else {
						Node node = new Node();
						node.Label = getMajorityClass(dataPartition);
						n.children[i] = node;
					}
				}
				break;
			}
			default: {
				throw new IllegalArgumentException("Unknown splittingCriterion");
			}

		}
		// What are the different possibilities?
		// find the subsets
		// select enum value by string

		return n;
	}

	private static String getMajorityClass(ArrayList<RawDataObject> dataPartition) {

		int largestSeen = 0;
		int largestIndex = 0;

		// Six choices in enum how_often
		int[] counts = new int[how_often_play_video_games.values().length];
		for(int i = 0; i < counts.length; i++) {
			for(RawDataObject o : dataPartition) {
				if(i == o.HowOftenPlayVideoGames.ordinal()) {
					counts[i]++;
					if(counts[i] > largestSeen) {
						largestSeen = counts[i];
						largestIndex = i;
					}
				}
			}
		}

		// now return the largest.. 
		return how_often_play_video_games.values()[largestIndex].toString();

	}

	private static boolean allTheSameClass(ArrayList<RawDataObject> dataPartition) {
		int firstEnum = dataPartition.get(0).HowOftenPlayVideoGames.ordinal();
		for(RawDataObject o : dataPartition) {
			if(o.HowOftenPlayVideoGames.ordinal() != firstEnum)
				return false;
		}
		return true;
	}

	// Compute informationGain for an attribute
	private static String findBestSplittingAttribute(ArrayList<RawDataObject> dataPartition, ArrayList<String> attributes) {
		double highestGain = 0;
		String winnerAttribute = "";

		// Call calc gain on all attributes, return name of the highest gain one.
		for(int i = 0; i < attributes.size(); i++) {
			double gain = calcGain(dataPartition, attributes.get(i));
			if(gain >= highestGain) {
				highestGain = gain;
				winnerAttribute = attributes.get(i);
			}
		}

		return winnerAttribute;
	}

	private static double calcEntropy(ArrayList<RawDataObject> dataPartition) {
		// Terminate
		if(dataPartition.size() == 0)
			return 0;

		// target attribute counts
		int[] counts = new int[how_often_play_video_games.values().length];

		// loop over each distinct value, counting the number of occurences for each
		for(int i = 0 ; i < counts.length; i++) {
			for(RawDataObject o : dataPartition) {
				if(o.HowOftenPlayVideoGames.ordinal() == i) {
					counts[i]++;
				}
			}
		}

		double entropy = 0;

		// loop over counts, calculating sum (entropy)
		for(int j = 0; j < counts.length; j++) {
			// P(i) = tuples with class label (i) / number of tuples in D

			double probability = counts[j] / (double)dataPartition.size();
			if(counts[j] > 0) {
				entropy += -probability * (Math.log(probability) / Math.log(2));
			}

		}

		return entropy;
	}

	private static double calcGain(ArrayList<RawDataObject> dataPartition, String attribute) {
		// how much information needed if split on attribute

		// number of distinct possible values of attribute, (v) 
		ArrayList<ArrayList<RawDataObject>> subsets;		// which attribute is it?

		switch(attribute) {
			//case "Age":
				// TODO count this array occurences
				// TODO figure out to deal with continous values
				//break;
			case "Gender": {
				subsets = new ArrayList<ArrayList<RawDataObject>>(gender.values().length);
				for(int i = 0; i < subsets.size(); i++) {
					for(RawDataObject o : dataPartition) {
						if(o.Gender.ordinal() == i)
							subsets.get(i).add(o);
					}
				}
				break;
			}
			case "Degree": {
				subsets = new ArrayList<ArrayList<RawDataObject>>(degree.values().length);
				for(int i = 0; i < subsets.size(); i++) {
					for(RawDataObject o : dataPartition) {
						if(o.Degree.ordinal() == i)
							subsets.get(i).add(o);
					}
				}
				break;
			}
			default:
				throw new IllegalArgumentException("Notvalid attribute");
		}

		// info(age)(D) = sum((D(j) / D) * Info(D(j))) 
		double gain = 0;

		// sum from 0 to distinct values for attribute (v)
		for(int i = 0; i < subsets.size(); i++) {
			gain += (subsets.get(i).size() / (double)dataPartition.size()) * calcEntropy(subsets.get(i));
		}

		return gain;
	}

	public static void main(String[] args) {
		// testing counts array
		ArrayList<RawDataObject> test = new ArrayList<RawDataObject>();

		RawDataObject o = new RawDataObject();
		o.HowOftenPlayVideoGames = how_often_play_video_games.lt_15hweek;
		test.add(o);

		o = new RawDataObject();
		o.HowOftenPlayVideoGames = how_often_play_video_games.never;
		test.add(o);

		o = new RawDataObject();
		o.HowOftenPlayVideoGames = how_often_play_video_games.lt_15hweek;
		test.add(o);

		System.out.println(calcGain(test, "Degree"));
		System.out.println(allTheSameClass(test));
		System.out.println(getMajorityClass(test));

		ArrayList<String> atts = new ArrayList<String>();
		atts.add("Degree");
		atts.add("Gender");
		System.out.println(findBestSplittingAttribute(test, atts));


	}

}

class DecisionTree {
	private Node root;

	public DecisionTree(Node root) {
		this.root = root;
	}

	public String PrettyPrint() {
		Node n = this.root;
		return this.traverseTree(n);

	}
	private String traverseTree(Node n) {
		StringBuilder b = new StringBuilder();

		if(n.children != null) {
			for(int i = 0; i < n.children.length; i++) {
				b.append(this.traverseTree(n.children[i]));
			}
		}

		return n.Label + "(" + b.toString() + ")";
	}

	public String Classify(RawDataObject o) {
		Node node = this.root;

		// If it has children, then it must be a decision node
		while(node.children != null) {
			if(node.Label.equals("Degree")) {
					// If the correct child has no children, then were are done
					node = node.children[o.Degree.ordinal()];

			} else if(node.Label.equals("Gender")) {
					// If the correct child has no children, then were are done
					node = node.children[o.Gender.ordinal()];
			}

		}

		return node.Label;
	}
}

class Node {
	String Label;
	Node parent;
	Node children[];
}
