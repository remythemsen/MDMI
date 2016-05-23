import java.util.Date;
import java.util.ArrayList;
import data.*;

public class RawDataObject {
	Date Timestamp;
	double Age;
	gender Gender;
	double ShoeSize;
	double Height;
	degree Degree;
	reason_for_taking_course ReasonForTakingCourse;
	// Known Programming Languages, sort from best to worst
	ArrayList<programming_language> ProgrammingLanguages;
	mobile_os_preferred MobileOsPreferred;

	//what topics do you want to learn
	// Do vector here??
	level_of_interest[] PreferredTopics; // Size 11
	// 1 Design efficient databases for large amounts of data
	// 2 Create predictive models (e.g. weather or stock market prediction)
	// 3 Define groups of similar objects (e.g. users from a dating site)
	// 4 Visualise data
	// 5 Study patterns on sets (e.g. Amazon shopping carts)
	// 6 Study patterns on sequences (e.g. exercises on a workout session)
	// 7 Study patterns on graphs (e.g. Facebook)
	// 8 Study patterns on text (e.g. spam mail)
	// 9 Study patterns on images (e.g. face detection)
	// 10 Code data mining algorithms
	// 11 Use off-the-shelf data mining tools

	how_often_play_video_games HowOftenPlayVideoGames;
	ArrayList<game> GamesPlayed;
	ArrayList<commute_to_itu> Commutes;
	locations[] Traversal; // Size 15
	int[] RandomNumbers;// between 0-15
	String TherbForttGlag;
	pick_a_number PickedNumber;
	ArrayList<String> FavFilms;
	ArrayList<String> FavTVShows;
	ArrayList<String> FavGames;
	row WhatRow;
	int Seat; // 

	public RawDataObject() {
		this.ProgrammingLanguages = new ArrayList<programming_language>();
		this.GamesPlayed = new ArrayList<game>();
		this.Commutes = new ArrayList<commute_to_itu>();
		this.RandomNumbers = new int[4];
		this.FavFilms = new ArrayList<String>();
		this.FavTVShows = new ArrayList<String>();
		this.FavGames = new ArrayList<String>();
	}

}
