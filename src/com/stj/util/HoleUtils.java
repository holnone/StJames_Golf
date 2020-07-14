package com.stj.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stj.model.Course;
import com.stj.model.Hole;
import com.stj.model.Ironwood;
import com.stj.model.Side;
import com.stj.model.TheKnolls;
import com.stj.model.TheKnolls2013;
import com.stj.model.TheKnolls2019;

public class HoleUtils {

	private static Map<Course, Map<Integer, Hole>> holes = new HashMap<Course, Map<Integer, Hole>>();

	public static List<String> FRONT_HOLE_NUMBERS = Arrays.asList(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" });
	public static List<String> BACK_HOLE_NUMBERS = Arrays.asList(new String[] { "10", "11", "12", "13", "14", "15", "16", "17", "18" });

	public static List<String> THE_KNOLLS_FRONT_HANDICAPS = Arrays.asList(new String[] { "7", "9", "13", "3", "17", "15", "1", "5", "11" });
	public static List<String> THE_KNOLLS_BACK_HANDICAPS = Arrays.asList(new String[] { "2", "10", "4", "12", "14", "18", "8", "6", "16" });

	public static List<String> THE_KNOLLS_2013_FRONT_HANDICAPS = Arrays.asList(new String[] { "7", "9", "13", "3", "17", "5", "11", "1", "15" });
	public static List<String> THE_KNOLLS_2013_BACK_HANDICAPS = Arrays.asList(new String[] { "2", "10", "4", "12", "14", "18", "8", "6", "16" });

	public static List<String> THE_KNOLLS_2019_FRONT_HANDICAPS = Arrays.asList(new String[] { "7", "9", "13", "3", "17", "15", "1", "5", "11" });
	public static List<String> THE_KNOLLS_2019_BACK_HANDICAPS = Arrays.asList(new String[] { "2", "10", "4", "12", "14", "18", "8", "6", "16" });

	public static List<String> IRONWOOD_FRONT_HANDICAPS = Arrays.asList(new String[] { "8", "18", "12", "2", "16", "6", "14", "4", "10" });
	public static List<String> IRONWOOD_BACK_HANDICAPS = Arrays.asList(new String[] { "9", "3", "15", "17", "13", "7", "1", "11", "5" });

	static {
		TheKnolls knolls = new TheKnolls();
		Map<Integer, Hole> knollsHoles = new HashMap<Integer, Hole>();
		holes.put(knolls, knollsHoles);
		knollsHoles.put(1, new Hole(1, 366, 4, 7));
		knollsHoles.put(2, new Hole(2, 171, 3, 9));
		knollsHoles.put(3, new Hole(3, 335, 4, 13));
		knollsHoles.put(4, new Hole(4, 368, 4, 3));
		knollsHoles.put(5, new Hole(5, 130, 3, 17));
		knollsHoles.put(6, new Hole(6, 498, 5, 15));
		knollsHoles.put(7, new Hole(7, 377, 4, 1));
		knollsHoles.put(8, new Hole(8, 157, 3, 5));
		knollsHoles.put(9, new Hole(9, 460, 5, 11));

		knollsHoles.put(10, new Hole(10, 170, 3, 2));
		knollsHoles.put(11, new Hole(11, 500, 5, 10));
		knollsHoles.put(12, new Hole(12, 354, 4, 4));
		knollsHoles.put(13, new Hole(13, 447, 5, 12));
		knollsHoles.put(14, new Hole(14, 363, 4, 14));
		knollsHoles.put(15, new Hole(15, 290, 4, 18));
		knollsHoles.put(16, new Hole(16, 158, 3, 8));
		knollsHoles.put(17, new Hole(17, 339, 4, 6));
		knollsHoles.put(18, new Hole(18, 330, 4, 16));

		TheKnolls2013 knolls2013 = new TheKnolls2013();
		Map<Integer, Hole> knolls2013Holes = new HashMap<Integer, Hole>();
		holes.put(knolls2013, knolls2013Holes);
		knolls2013Holes.put(1, new Hole(1, 366, 4, 7));
		knolls2013Holes.put(2, new Hole(2, 171, 3, 9));
		knolls2013Holes.put(3, new Hole(3, 335, 4, 13));
		knolls2013Holes.put(4, new Hole(4, 368, 4, 3));
		knolls2013Holes.put(5, new Hole(5, 130, 3, 17));
		knolls2013Holes.put(6, new Hole(6, 157, 3, 5));
		knolls2013Holes.put(7, new Hole(7, 460, 5, 11));
		knolls2013Holes.put(8, new Hole(8, 377, 4, 1));
		knolls2013Holes.put(9, new Hole(9, 498, 5, 15));

		knolls2013Holes.put(10, new Hole(10, 170, 3, 2));
		knolls2013Holes.put(11, new Hole(11, 500, 5, 10));
		knolls2013Holes.put(12, new Hole(12, 354, 4, 4));
		knolls2013Holes.put(13, new Hole(13, 447, 5, 12));
		knolls2013Holes.put(14, new Hole(14, 363, 4, 14));
		knolls2013Holes.put(15, new Hole(15, 290, 4, 18));
		knolls2013Holes.put(16, new Hole(16, 158, 3, 8));
		knolls2013Holes.put(17, new Hole(17, 339, 4, 6));
		knolls2013Holes.put(18, new Hole(18, 330, 4, 16));

		TheKnolls2019 knolls2019 = new TheKnolls2019();
		Map<Integer, Hole> knollsHoles2019 = new HashMap<Integer, Hole>();
		holes.put(knolls2019, knollsHoles2019);
		knollsHoles2019.put(1, new Hole(1, 366, 4, 7));
		knollsHoles2019.put(2, new Hole(2, 171, 3, 9));
		knollsHoles2019.put(3, new Hole(3, 335, 4, 13));
		knollsHoles2019.put(4, new Hole(4, 368, 4, 3));
		knollsHoles2019.put(5, new Hole(5, 130, 3, 17));
		knollsHoles2019.put(6, new Hole(6, 498, 5, 15));
		knollsHoles2019.put(7, new Hole(7, 377, 4, 1));
		knollsHoles2019.put(8, new Hole(8, 157, 3, 5));
		knollsHoles2019.put(9, new Hole(9, 460, 5, 11));

		knollsHoles2019.put(10, new Hole(10, 170, 3, 2));
		knollsHoles2019.put(11, new Hole(11, 500, 5, 10));
		knollsHoles2019.put(12, new Hole(12, 354, 4, 4));
		knollsHoles2019.put(13, new Hole(13, 447, 5, 12));
		knollsHoles2019.put(14, new Hole(14, 363, 4, 14));
		knollsHoles2019.put(15, new Hole(15, 290, 4, 18));
		knollsHoles2019.put(16, new Hole(16, 158, 3, 8));
		knollsHoles2019.put(17, new Hole(17, 339, 4, 6));
		knollsHoles2019.put(18, new Hole(18, 330, 4, 16));

		Ironwood ironwood = new Ironwood();
		Map<Integer, Hole> ironwoodHoles = new HashMap<Integer, Hole>();
		holes.put(ironwood, ironwoodHoles);
		ironwoodHoles.put(1, new Hole(1, 373, 4, 8));
		ironwoodHoles.put(2, new Hole(2, 137, 3, 18));
		ironwoodHoles.put(3, new Hole(3, 393, 4, 12));
		ironwoodHoles.put(4, new Hole(4, 476, 5, 2));
		ironwoodHoles.put(5, new Hole(5, 124, 3, 16));
		ironwoodHoles.put(6, new Hole(6, 535, 5, 6));
		ironwoodHoles.put(7, new Hole(7, 330, 4, 14));
		ironwoodHoles.put(8, new Hole(8, 400, 4, 4));
		ironwoodHoles.put(9, new Hole(9, 360, 4, 10));

		ironwoodHoles.put(10, new Hole(10, 370, 4, 9));
		ironwoodHoles.put(11, new Hole(11, 481, 5, 3));
		ironwoodHoles.put(12, new Hole(12, 172, 3, 15));
		ironwoodHoles.put(13, new Hole(13, 337, 4, 17));
		ironwoodHoles.put(14, new Hole(14, 371, 4, 13));
		ironwoodHoles.put(15, new Hole(15, 381, 4, 7));
		ironwoodHoles.put(16, new Hole(16, 512, 5, 1));
		ironwoodHoles.put(17, new Hole(17, 167, 3, 11));
		ironwoodHoles.put(18, new Hole(18, 406, 4, 5));
	}

	public static List<String> getHandicaps(Side side) {
		if (side != null) {
			if (side.getCourse() instanceof TheKnolls) {
				return "FT".equals(side.getSideType()) ? THE_KNOLLS_FRONT_HANDICAPS : THE_KNOLLS_BACK_HANDICAPS;
			} else if (side.getCourse() instanceof Ironwood) {
				return "FT".equals(side.getSideType()) ? IRONWOOD_FRONT_HANDICAPS : IRONWOOD_BACK_HANDICAPS;
			} else if (side.getCourse() instanceof TheKnolls2013) {
				return "FT".equals(side.getSideType()) ? THE_KNOLLS_2013_FRONT_HANDICAPS : THE_KNOLLS_2013_BACK_HANDICAPS;
			} else if (side.getCourse() instanceof TheKnolls2019) {
				return "FT".equals(side.getSideType()) ? THE_KNOLLS_2019_FRONT_HANDICAPS : THE_KNOLLS_2019_BACK_HANDICAPS;
			}
		}
		//Default to The Knolls Front handicaps 2019
		return THE_KNOLLS_2019_FRONT_HANDICAPS;
	}

	public static List<Hole> getFrontNineHoles(Course course) {
		List<Hole> list = new ArrayList<Hole>();
		list.add(holes.get(course).get(1));
		list.add(holes.get(course).get(2));
		list.add(holes.get(course).get(3));
		list.add(holes.get(course).get(4));
		list.add(holes.get(course).get(5));
		list.add(holes.get(course).get(6));
		list.add(holes.get(course).get(7));
		list.add(holes.get(course).get(8));
		list.add(holes.get(course).get(9));
		return list;
	}

	public static List<Hole> getBackNineHoles(Course course) {
		List<Hole> list = new ArrayList<Hole>();
		list.add(holes.get(course).get(10));
		list.add(holes.get(course).get(11));
		list.add(holes.get(course).get(12));
		list.add(holes.get(course).get(13));
		list.add(holes.get(course).get(14));
		list.add(holes.get(course).get(15));
		list.add(holes.get(course).get(16));
		list.add(holes.get(course).get(17));
		list.add(holes.get(course).get(18));
		return list;
	}

	public static Hole getHole(Course course, Integer holeNumber) {
		return holes.get(course).get(holeNumber);
	}

	// Returns actual hole number based on Side (Front/Side) and holeNumber
	// (1-9)
	public static Integer getHoleNumber(Side side, Integer holeNumber) {
		return side.getHoles().get(holeNumber).getHoleNumber();
	}
}
