import java.util.Arrays;
import data.*;
import java.util.ArrayList;
public class DataConverter {
	// Convert the data into RawDataObjects
	public static ArrayList<RawDataObject> convert(String[][] tuples) {
		ArrayList<RawDataObject> datalist = new ArrayList<RawDataObject>();

		// Loop through tuples
		for(int i = 1; i < tuples.length; i++) {
			RawDataObject rdo = new RawDataObject();
			//#### TIME STAMP
			//rdo.Timestamp = tuples[i][0];

			//#### AGE
			try {
				rdo.Age = Double.parseDouble(tuples[i][1]);
			} catch(Exception e) {
				System.out.println("Failure on Age "+ i);
			}

			//#### GENDER
			rdo.Gender = (tuples[i][2].equals("Male")) ? gender.male : gender.female;

			//#### SHOESIZE
			try {
				rdo.ShoeSize = Double.parseDouble(tuples[i][3]);
			} catch(Exception e) {
				System.out.println("Failure on Shoesize "+ i +":  "+tuples[i][3]);
				rdo.ShoeSize = 0;
			}

			//#### HEIGHT
			try {
				rdo.Height = Double.parseDouble(tuples[i][4]);
			} catch(Exception e) {
				System.out.println("Failure on Height "+ i);
			}

			//#### DEGREE
			degree tempDegree = degree.other;
			for(degree d : degree.values()) {
				String td = tuples[i][5].replaceAll("-", "_");
				if(cleanString(td).equalsIgnoreCase(d.name())) {//d.name())) {
					tempDegree = d;
					break;
				}
			}
			rdo.Degree = tempDegree;

			//#### REASON FOR TAKING COURSE
			reason_for_taking_course tempReason = reason_for_taking_course.other;
			for(reason_for_taking_course r : reason_for_taking_course.values()) {
				String tr = tuples[i][6].replaceAll(" ", "_");
				if(cleanString(tr).equalsIgnoreCase(r.name())) {//d.name())) {
					tempReason = r;
					break;
				}
			}
			rdo.ReasonForTakingCourse = tempReason;

			//#### PROGRAMMING LANGUAGES
			String programmingLangsString = cleanString(tuples[i][7]);
			programmingLangsString = programmingLangsString.replaceAll("\\#","sharp");
			programmingLangsString = programmingLangsString.replaceAll("\\+","p");
			programmingLangsString = programmingLangsString.replaceAll("-", "_");
			programmingLangsString = programmingLangsString.replaceAll(",\\s", ",");
			String[] pLangs = programmingLangsString.split(",");

			//System.out.println(i + ": " +programmingLangsString);

			for(int j = 0; j < pLangs.length; j++) {
				programming_language tpla = programming_language.unknown;
				for(programming_language p : programming_language.values()) {
					if(pLangs[j].equalsIgnoreCase(p.name())) {
						tpla = p;
						break;
					}
				}
				rdo.ProgrammingLanguages.add(tpla);
			}

			//#### MOBILE OS PREFERRED
			mobile_os_preferred tempOS = mobile_os_preferred.other;
			for(mobile_os_preferred os : mobile_os_preferred.values()) {
				String mos = cleanString(tuples[i][8]).replaceAll("-", "_");
				if(mos.equalsIgnoreCase(os.name())) {//d.name())) {
					tempOS = os;
					break;
				}
			}
			rdo.MobileOsPreferred = tempOS;

			//#### PREFERRED TOPICS 
			rdo.PreferredTopics = new level_of_interest[11];

			for(int k = 0; k < 11; k++) {
				rdo.PreferredTopics[k] = matchInterest(cleanString(tuples[i][k+9]));
			}

			//#### GAMING FREQUENCY
			String freq = cleanString(tuples[i][20]);
			how_often_play_video_games tempFreq = how_often_play_video_games.never;
			switch(freq) {
				case "Never":
					tempFreq = how_often_play_video_games.never;
					break;

				case "< 5 hours a week":
					tempFreq = how_often_play_video_games.lt_5hweek;
					break;

				case "< 10 hours a week":
					tempFreq = how_often_play_video_games.lt_10hweek;
					break;

				case "< 15 hours a week":
					tempFreq = how_often_play_video_games.lt_15hweek;
					break;

				case "< 20 hours a week":
					tempFreq = how_often_play_video_games.lt_20hweek;
					break;

				case "> 20 hours a week":
					tempFreq = how_often_play_video_games.mt_20hweek;
					break;
			}
			rdo.HowOftenPlayVideoGames = tempFreq;


			//#### WHICH GAMES PLAYED
			String gamesString = cleanString(tuples[i][21]);
			gamesString = gamesString.replaceAll(",\\s", ",");
			gamesString = gamesString.replaceAll(" ", "_");
			gamesString = gamesString.replaceAll(":", "");
			String[] games = gamesString.split(",");

			//System.out.println(gamesString);

			for(int l = 0; l < games.length; l++) {
				for(game g : game.values()) {
					if(games[l].equalsIgnoreCase(g.name())) {
						rdo.GamesPlayed.add(g);
						break;
					}
				}
			}

			//#### COMMUTE
			String comsString = cleanString(tuples[i][22]);

			comsString = comsString.replaceAll(",\\s", ",");
			String[] coms = comsString.split(",");

			for(int m = 0; m < coms.length; m++) {
				for(commute_to_itu cti : commute_to_itu.values()) {
					if(coms[m].equalsIgnoreCase(cti.name())) {
						rdo.Commutes.add(cti);
						break;
					}
				}
			}

			//#### TRAVERSAL
			rdo.Traversal = new locations[15];

			for(int s = 0; s < 15; s++) {
				rdo.Traversal[s] = matchLocation(cleanString(tuples[i][s+23]));
			}

			//#### Random Numbers
			String numbersString = tuples[i][38];
			numbersString = cleanString(numbersString).replaceAll(",\\s", ",");
			String[] numbers = numbersString.split(",");

//			System.out.println(Arrays.toString(numbers));
			for(int n = 0; n < 4; n++) {
				int number;
				try {
					number = Integer.parseInt(numbers[n]);
				} catch(Exception e) {
					number = -99;
				}

				rdo.RandomNumbers[n] = number;

			}

			//#### therb fortt glag
			rdo.TherbForttGlag = tuples[i][39];

			//#### Pick a number
			//System.out.println(tuples[i][40]);

			String pickedNumber = cleanString(tuples[i][40]);
			pick_a_number tempNumber = pick_a_number.asparagus;

			switch(pickedNumber) {
				case "7":
					tempNumber = pick_a_number.seven;
					break;
				case "9":
					tempNumber = pick_a_number.nine;
					break;
				case "asparagus":
					tempNumber = pick_a_number.asparagus;
					break;
				case "13":
					tempNumber = pick_a_number.thirteen;
					break;
			}

			rdo.PickedNumber = tempNumber;

			//#### FAV FILMS
			String filmsString = tuples[i][41];
			filmsString = cleanString(filmsString).replaceAll(",\\s", ",");
			//System.out.println(filmsString);
			String[] films = filmsString.split(",");

			for(int o = 0; o < films.length; o++) {
				rdo.FavFilms.add(films[o].trim());
			}

			//#### FAV TV SHOWS
			String tvsString = tuples[i][42];
			tvsString = cleanString(tvsString).replaceAll(",\\s", ",");
			//System.out.println(filmsString);
			String[] tvshows = tvsString.split(",");

			for(int p = 0; p < tvshows.length; p++) {
				rdo.FavTVShows.add(tvshows[p].trim());
			}

			//#### FAV GAMES
			String favgamesString = tuples[i][43];
			favgamesString = cleanString(favgamesString).replaceAll(",\\s", ",");
			//System.out.println(filmsString);
			String[] favGames = favgamesString.split(",");

			for(int q = 0; q < favGames.length; q++) {
				rdo.FavGames.add(favGames[q].trim());
			}

			//#### WHAT ROW
			row tempRow = row.unknown;
			for(row r : row.values()) {
				String rowString = tuples[i][44];
				if(cleanString(rowString).equalsIgnoreCase(r.name())) {//d.name())) {
					tempRow = r;
					break;
				}
			}
			rdo.WhatRow = tempRow;

			//#### WHAT SEAT
			try {
				rdo.Seat = Integer.parseInt(tuples[i][45]);
			} catch(Exception e) {
				rdo.Seat = 0;
			}

			datalist.add(rdo);
		}

		return datalist;
	}
	static level_of_interest matchInterest(String s) {
		level_of_interest loi = level_of_interest.meh;
		for(level_of_interest l : level_of_interest.values())
			if(s.replaceAll(" ","_").equalsIgnoreCase(l.name()))
				loi = l;
		return loi;
	}

	static locations matchLocation(String s) {
		locations lo = locations.not_at_itu;
		for(locations location : locations.values())
			if(s.replaceAll(" ","_").replaceAll("/","_").equalsIgnoreCase(location.name()))
				lo = location;
		return lo;
	}

	static String cleanString(String s) {
		s.replaceAll("\\s+","");
		return s.replaceAll("\"", "");
	}

}
