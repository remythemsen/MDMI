import java.util.ArrayList;
public class Cleaner {

	public static ArrayList<RawDataObject> clean(ArrayList<RawDataObject> objs) {
		return Normalize(FillInUnknown(objs));
	}
	static ArrayList<RawDataObject> Normalize(ArrayList<RawDataObject> objs) {
		// Normalizing Age using min-max
		double ageMin = Double.MAX_VALUE;
		double ageMax = Double.MIN_VALUE;
		// Normalizing Shoesize using min-max
		double shoeSizeMin = Double.POSITIVE_INFINITY;
		double shoeSizeMax = Double.NEGATIVE_INFINITY;
		// Normalizing Height using min-max
		double heightMin = Double.POSITIVE_INFINITY;
		double heightMax = Double.NEGATIVE_INFINITY;

		// Find Min and MAX
		for(RawDataObject o : objs) {
			// AGE
			if(o.Age < ageMin)
				ageMin = o.Age;
			if(o.Age > ageMax)
				ageMax = o.Age;

			// SHOESIZE
			if(o.ShoeSize < shoeSizeMin)
				shoeSizeMin = o.ShoeSize;
			if(o.ShoeSize > shoeSizeMax)
				shoeSizeMax = o.ShoeSize;

			// HEIGHT
			if(o.Height < heightMin)
				heightMin = o.Height;
			if(o.Height > heightMax)
				heightMax = o.Height;


		}

		// NORMALIZING
		for(RawDataObject o : objs) {
			//System.out.println(o.Height);
			o.Height = minMaxNormalize(o.Height, heightMin, heightMax);
			o.ShoeSize = minMaxNormalize(o.ShoeSize, shoeSizeMin, shoeSizeMax);
			o.Age = minMaxNormalize(o.Age, ageMin, ageMax);
			//System.out.println(o.Height);
		}

		return objs;
	}
	static double minMaxNormalize(double v, double min, double max) {
		return (v-min)/(max-min);//*(1-0)+0;
	}

	static ArrayList<RawDataObject> FillInUnknown(ArrayList<RawDataObject> objs) {
		// GETTING Means and Medians
		int ageSum = 0;
		double heightSum = 0;
		double shoeSizeSum = 0;
		int pickRandomNumberSum = 0;

		for(RawDataObject o : objs) {
			// Dealing with Missing values in Shoesize, Height and Age
			// Use Mean for symmetric data, median otherwise
			ageSum+=o.Age;
			heightSum+=o.Height;
			shoeSizeSum+=o.ShoeSize;
			pickRandomNumberSum+=o.RandomNumbers[0];
			pickRandomNumberSum+=o.RandomNumbers[1];
			pickRandomNumberSum+=o.RandomNumbers[2];
			pickRandomNumberSum+=o.RandomNumbers[3];

		}

		int ageMean = calculateMean(ageSum, 47);
		double heightMean = calculateMean(heightSum, 47);
		double shoeSizeMean = calculateMean(shoeSizeSum, 47);
		int pickRandomNumberMean = calculateMean(pickRandomNumberSum, 47*4);

		for(RawDataObject o : objs) {
			// Checking Age shoesize and height attr's for erronous values
			if(o.Age == 0)
				o.Age = ageMean;

			if(o.Height == 0 || o.Height < 3)
				o.Height = heightMean;

			if(o.ShoeSize == 0)
				o.ShoeSize = shoeSizeMean;

			// Checking Picked random numbers result
			for(int i = 0; i<4; i++)
				o.RandomNumbers[i] = checkRandomNumber(o.RandomNumbers[i]);

		}

		return objs;
	}
	static int checkRandomNumber(int number) {
		int result = number;
		if(result < 0 || result > 14) result = 14/2;


		return result;
	}
	static double calculateMean(double total, int amount) {
		return total/amount;
	}
	static int calculateMean(int total, int amount) {
		return total/amount;
	}
	static double calculateMedian() {
		return 1/47;
	}
}
