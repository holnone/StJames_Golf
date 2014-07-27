package com.stj.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import com.stj.model.Hole;
import com.stj.model.Side;

public class HandicapUtils {

	public static Integer calculateHandicap(Integer totalScore, Integer totalPar, int scoreCount) {
		Integer hdcp = null;
		Integer difference = totalScore - totalPar;
		BigDecimal avgDiff = new BigDecimal(difference).divide(new BigDecimal(scoreCount), 2, RoundingMode.HALF_UP);
		BigDecimal temp = avgDiff.multiply(new BigDecimal(".9"));

		BigDecimal rounded = temp.setScale(0, RoundingMode.HALF_UP);
		hdcp = rounded.intValue();
		return hdcp;
	}

	public static boolean calculateHoleStrokes(Map<Integer, Integer> holeStrokeMap, Side side, Integer player1Handicap, Integer player2Handicap) {
		boolean addToPlayer1 = true;
		if (player1Handicap != null && player2Handicap != null && (player1Handicap > player2Handicap || player2Handicap > player1Handicap)) {
			List<Hole> holes = side.getHolesByHandicap();
			Integer handicapDifference = 0;
			for (int i = 0; i < 9; i++) { // Initialize holeStrokeMap
				holeStrokeMap.put(holes.get(i).getHoleNumber(), 0);
			}
			if (player1Handicap > player2Handicap) { // e.g. 26 > 3
				addToPlayer1 = false;
				handicapDifference = player1Handicap - player2Handicap; // 26 -
				// 3 =
				// 23
			} else {
				handicapDifference = player2Handicap - player1Handicap;
			}
			for (int i = 0; i < handicapDifference; i++) {
				int index = i;
				if (i >= 36) {
					index = i - 36;
				} else if (i >= 27) {
					index = i - 27;
				} else if (i >= 18) {
					index = i - 18;
				} else if (i >= 9) {
					index = i - 9;
				}
				holeStrokeMap.put(holes.get(index).getHoleNumber(), holeStrokeMap.get(holes.get(index).getHoleNumber()) + 1);
			}
		}

		return addToPlayer1;
	}
}
