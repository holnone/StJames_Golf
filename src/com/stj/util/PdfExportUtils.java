package com.stj.util;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.FontSelector;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import com.stj.model.ApplicationProperty;
import com.stj.model.Player;
import com.stj.model.Round;
import com.stj.model.Season;
import com.stj.model.Team;
import com.stj.model.Week;

public class PdfExportUtils {
	protected static SimpleDateFormat dateFormat_MMMddYYYY = new SimpleDateFormat("MMMM dd, yyyy");
	protected static SimpleDateFormat dateFormat_MMMdd = new SimpleDateFormat("MMMM dd");
	protected static NumberFormat pointsFormat = new DecimalFormat("##0.0");
	protected static Paragraph newLine = new Paragraph("\n");
	protected static LineSeparator separator = new LineSeparator();

	public static byte[] buildWeeklyReport(Season season, Week selectedWeek, ApplicationProperty publicMessage) {
		List<Round> rounds = new ArrayList<Round>(season.getRounds());
		Collections.sort(rounds);
		String roundNumber = "";
		Round round = null;
		Week nextWeek = null;
		Week previousWeek = null;
		String weekNumber = "";
		Round standingsRound = null;
		if (rounds.get(0).containsWeek(selectedWeek)) {
			roundNumber = "1st";
			round = rounds.get(0);
			standingsRound = round;
			nextWeek = round.getNextWeek(selectedWeek);
			if (nextWeek == null) {
				nextWeek = rounds.get(1).getFirstWeek();
			}
			List<Week> weeks = new ArrayList<Week>(round.getWeeks());
			Collections.sort(weeks);
			previousWeek = round.getPreviousWeek(selectedWeek);
			if (previousWeek == null) {
				previousWeek = selectedWeek;
				weekNumber = "1";
			} else {
				weekNumber = String.valueOf(weeks.indexOf(previousWeek) + 1);
			}
		} else {
			roundNumber = "2nd";
			round = rounds.get(1);
			standingsRound = round;
			nextWeek = round.getNextWeek(selectedWeek);
			List<Week> weeks = new ArrayList<Week>(round.getWeeks());
			Collections.sort(weeks);
			previousWeek = round.getPreviousWeek(selectedWeek);
			if (previousWeek == null) {
				roundNumber = "1st";
				previousWeek = rounds.get(0).getLastWeek();
				weekNumber = String.valueOf(rounds.get(0).getWeeks().size());
				standingsRound = rounds.get(0);
			} else {
				weekNumber = String.valueOf(weeks.indexOf(previousWeek) + 1);
			}
		}

		List<Team> teams = new ArrayList<Team>(season.getTeams());
		Collections.sort(teams);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			Font font_times_large = new Font(Font.TIMES_ROMAN, 14);
			Font font_times_large_underline = new Font(Font.TIMES_ROMAN, 14, Font.UNDERLINE);
			Font font_times_normal = new Font(Font.TIMES_ROMAN, 12);
			Font font_times_normal_underline = new Font(Font.TIMES_ROMAN, 12, Font.UNDERLINE);
			Font font_times_normal_bold = new Font(Font.TIMES_ROMAN, 12, Font.BOLD);
			Font font_times_normal_bold_underline = new Font(Font.TIMES_ROMAN, 12, Font.BOLD | Font.UNDERLINE);
			Font font_times_small = new Font(Font.TIMES_ROMAN, 10);
			Font font_times_small_underline = new Font(Font.TIMES_ROMAN, 10, Font.UNDERLINE);
			Font font_times_small_bold = new Font(Font.TIMES_ROMAN, 10, Font.BOLD);
			Font font_times_small_bold_underline = new Font(Font.TIMES_ROMAN, 10, Font.BOLD | Font.UNDERLINE);

			FontSelector underlineSelectorLarge = new FontSelector();
			underlineSelectorLarge.addFont(font_times_large_underline);

			FontSelector selectorLarge = new FontSelector();
			selectorLarge.addFont(font_times_large);

			FontSelector underlineSelectorNormal = new FontSelector();
			underlineSelectorNormal.addFont(font_times_normal_underline);

			FontSelector boldSelectorNormal = new FontSelector();
			boldSelectorNormal.addFont(font_times_normal_bold);

			FontSelector boldUnderlineSelectorNormal = new FontSelector();
			boldUnderlineSelectorNormal.addFont(font_times_normal_bold_underline);

			FontSelector selectorNormal = new FontSelector();
			selectorNormal.addFont(font_times_normal);

			FontSelector underlineSelectorSmall = new FontSelector();
			underlineSelectorSmall.addFont(font_times_small_underline);

			FontSelector boldSelectorSmall = new FontSelector();
			boldSelectorSmall.addFont(font_times_small_bold);

			FontSelector boldUnderlineSelectorSmall = new FontSelector();
			boldUnderlineSelectorSmall.addFont(font_times_small_bold_underline);

			FontSelector selectorSmall = new FontSelector();
			selectorSmall.addFont(font_times_small);

			PdfPCell blankCellNoBorder = new PdfPCell(new Phrase(""));
			blankCellNoBorder.setBorder(Rectangle.NO_BORDER);

			Document document = new Document();
			PdfWriter.getInstance(document, baos);
			document.open();

			document.add(new Paragraph(underlineSelectorLarge.process("St. James/Elizabeth Ann Friday Night Men's Golf League")));
			document.add(new Paragraph(selectorLarge.process(dateFormat_MMMddYYYY.format(selectedWeek.getDate()))));

			document.add(newLine);

			document.add(new Paragraph(selectorLarge.process(publicMessage != null ? publicMessage.getValue() : "")));

			document.add(newLine);

			document.add(new Paragraph(selectorNormal.process("www.stjamesgolfleague.com")));

			document.add(newLine);

			//Normal Fridays
			float[] colWidths = { 3f, 1f, 1f };
			PdfPTable table = new PdfPTable(colWidths);
			table.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
			table.addCell(new BorderlessPdfPCell(underlineSelectorLarge.process("TONIGHT'S SCHEDULE")));
			table.addCell(new BorderlessPdfPCell(underlineSelectorLarge.process("FRONT")));
			table.addCell(new BorderlessPdfPCell(underlineSelectorLarge.process("BACK")));

			table.addCell(new BorderlessPdfPCell(selectorLarge.process(Constants.TEE_TIME_1_GROUP_1 + " & " + Constants.TEE_TIME_1_GROUP_2)));
			table.addCell(new BorderlessPdfPCell(selectorLarge.process(selectedWeek.getFrontNineTeeTime1().toString())));
			table.addCell(new BorderlessPdfPCell(selectorLarge.process(selectedWeek.getBackNineTeeTime1().toString())));

			table.addCell(new BorderlessPdfPCell(selectorLarge.process(Constants.TEE_TIME_2_GROUP_1 + " & " + Constants.TEE_TIME_2_GROUP_2)));
			table.addCell(new BorderlessPdfPCell(selectorLarge.process(selectedWeek.getFrontNineTeeTime2().toString())));
			table.addCell(new BorderlessPdfPCell(selectorLarge.process(selectedWeek.getBackNineTeeTime2().toString())));

			table.addCell(new BorderlessPdfPCell(selectorLarge.process(Constants.TEE_TIME_3_GROUP_1 + " & " + Constants.TEE_TIME_3_GROUP_2)));
			table.addCell(new BorderlessPdfPCell(selectorLarge.process(selectedWeek.getFrontNineTeeTime3().toString())));
			table.addCell(new BorderlessPdfPCell(selectorLarge.process(selectedWeek.getBackNineTeeTime3().toString())));

			//Sundays
            /*float[] colWidths = { 3f, 1f };
            PdfPTable table = new PdfPTable(colWidths);
            table.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
            table.addCell(new BorderlessPdfPCell(underlineSelectorLarge.process("TONIGHT'S SCHEDULE")));
            table.addCell(new BorderlessPdfPCell(underlineSelectorLarge.process("FRONT")));

            table.addCell(new BorderlessPdfPCell(selectorLarge.process(Constants.TEE_TIME_1_GROUP_1 + " & " + Constants.TEE_TIME_1_GROUP_2)));
            table.addCell(new BorderlessPdfPCell(selectorLarge.process(selectedWeek.getFrontNineTeeTime1().toString())));

            table.addCell(new BorderlessPdfPCell(selectorLarge.process(Constants.TEE_TIME_4_GROUP_1 + " & " + Constants.TEE_TIME_4_GROUP_2)));
            table.addCell(new BorderlessPdfPCell(selectorLarge.process(selectedWeek.getBackNineTeeTime1().toString())));

            table.addCell(new BorderlessPdfPCell(selectorLarge.process(Constants.TEE_TIME_2_GROUP_1 + " & " + Constants.TEE_TIME_2_GROUP_2)));
            table.addCell(new BorderlessPdfPCell(selectorLarge.process(selectedWeek.getFrontNineTeeTime2().toString())));

            table.addCell(new BorderlessPdfPCell(selectorLarge.process(Constants.TEE_TIME_5_GROUP_1 + " & " + Constants.TEE_TIME_5_GROUP_2)));
            table.addCell(new BorderlessPdfPCell(selectorLarge.process(selectedWeek.getBackNineTeeTime2().toString())));

            table.addCell(new BorderlessPdfPCell(selectorLarge.process(Constants.TEE_TIME_3_GROUP_1 + " & " + Constants.TEE_TIME_3_GROUP_2)));
            table.addCell(new BorderlessPdfPCell(selectorLarge.process(selectedWeek.getFrontNineTeeTime3().toString())));

            table.addCell(new BorderlessPdfPCell(selectorLarge.process(Constants.TEE_TIME_6_GROUP_1 + " & " + Constants.TEE_TIME_6_GROUP_2)));
            table.addCell(new BorderlessPdfPCell(selectorLarge.process(selectedWeek.getBackNineTeeTime3().toString())));*/

			document.add(table);

			document.add(newLine);

			/*if (nextWeek != null) {
				colWidths = new float[] { 3f, 1f, 1f };
				table = new PdfPTable(colWidths);
				table.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
				table.addCell(new BorderlessPdfPCell(underlineSelectorLarge.process(dateFormat_MMMdd.format(nextWeek.getDate()) + " SCHEDULE")));
				table.addCell(new BorderlessPdfPCell(underlineSelectorLarge.process("FRONT")));
				table.addCell(new BorderlessPdfPCell(underlineSelectorLarge.process("BACK")));

				table.addCell(new BorderlessPdfPCell(selectorLarge.process(Constants.TEE_TIME_1_GROUP_1 + " & " + Constants.TEE_TIME_1_GROUP_2)));
				table.addCell(new BorderlessPdfPCell(selectorLarge.process(nextWeek.getFrontNineTeeTime1().toString())));
				table.addCell(new BorderlessPdfPCell(selectorLarge.process(nextWeek.getBackNineTeeTime1().toString())));

				table.addCell(new BorderlessPdfPCell(selectorLarge.process(Constants.TEE_TIME_2_GROUP_1 + " & " + Constants.TEE_TIME_2_GROUP_2)));
				table.addCell(new BorderlessPdfPCell(selectorLarge.process(nextWeek.getFrontNineTeeTime2().toString())));
				table.addCell(new BorderlessPdfPCell(selectorLarge.process(nextWeek.getBackNineTeeTime2().toString())));

				table.addCell(new BorderlessPdfPCell(selectorLarge.process(Constants.TEE_TIME_3_GROUP_1 + " & " + Constants.TEE_TIME_3_GROUP_2)));
				table.addCell(new BorderlessPdfPCell(selectorLarge.process(nextWeek.getFrontNineTeeTime3().toString())));
				table.addCell(new BorderlessPdfPCell(selectorLarge.process(nextWeek.getBackNineTeeTime3().toString())));

				document.add(table);

				document.add(newLine);
			}*/

			Calendar standingsDate = Calendar.getInstance();
			standingsDate.setTime(selectedWeek.getDate());
			standingsDate.add(Calendar.DAY_OF_YEAR, -1);
			List<Team> teamStandings = standingsRound.getStandings(season, standingsDate.getTime());
			colWidths = new float[] { 3f, 3f };
			table = new PdfPTable(colWidths);
			table.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);

			colWidths = new float[] { 1f, 1f };
			PdfPTable standingsTable1 = new PdfPTable(colWidths);
			standingsTable1.addCell(new BorderlessPdfPCell(underlineSelectorNormal.process(roundNumber + " Round, Week #" + weekNumber), 2));
			for (int i = 0; i < 6; i++) {
				Team team = teamStandings.get(i);
				standingsTable1.addCell(new BorderlessPdfPCell(selectorNormal.process(String.valueOf(team.getPlace()) + ". " + team.getName() + " ("
						+ team.getTeamNumber() + ")")));
				standingsTable1.addCell(new BorderlessPdfPCell(selectorNormal.process(pointsFormat.format(team.getPoints()))));
			}

			colWidths = new float[] { 1f, 1f };
			PdfPTable standingsTable2 = new PdfPTable(colWidths);
			standingsTable2.addCell(new BorderlessPdfPCell(underlineSelectorNormal.process(" "), 2));
			for (int i = 6; i < 12; i++) {
				Team team = teamStandings.get(i);
				standingsTable2.addCell(new BorderlessPdfPCell(selectorNormal.process(String.valueOf(team.getPlace()) + ". " + team.getName() + " ("
						+ team.getTeamNumber() + ")")));
				standingsTable2.addCell(new BorderlessPdfPCell(selectorNormal.process(pointsFormat.format(team.getPoints()))));
			}
			PdfPCell table1Cell = new PdfPCell(standingsTable1);
			table1Cell.setBorder(Rectangle.NO_BORDER);
			PdfPCell table2Cell = new PdfPCell(standingsTable2);
			table2Cell.setBorder(Rectangle.NO_BORDER);
			table.addCell(table1Cell);
			table.addCell(table2Cell);
			document.add(table);

			document.add(newLine);

			document.add(new Paragraph(underlineSelectorNormal.process("WEEKLY RULES:")));
			document.add(new Paragraph(selectorNormal.process("-PLAY FROM THE WHITE TEES")));
			document.add(new Paragraph(selectorNormal.process("-BALLS ARE TO BE PLAYED UP EVERYWHERE, WITHIN A SCORECARD (EXCEPT HAZARDS)")));
			document.add(new Paragraph(selectorNormal.process("-HANDICAP CALCULATIONS ARE STROKES OVER PAR TIMES 90%")));
			document.add(newLine);
			document.add(new Paragraph(
					boldSelectorSmall
							.process("*****ALL NEW PLAYERS PLAY #4. IF THERE'S MORE THAN ONE, YOU COUNT DOWN FROM THERE. DON'T REDO SCORECARDS AFTER FIGURING HANDICAPS TO DETERMINE YOUR MATCH.*****")));
			document.add(newLine);
			document.add(new Paragraph(selectorNormal.process("-UNLIMITED HANDICAPS ON ALL HOLES.")));
			document.add(new Paragraph(selectorNormal.process("-HANDICAPS ARE BASED ON YOUR LAST 5 ROUNDS.")));
			document.add(new Paragraph(selectorNormal.process("-TEAMS MUST HAVE 4 PLAYERS TO WIN TEAM LOW NET.")));
			document.add(new Paragraph(selectorNormal.process("-PLEASE PUT FULL NAMES ON SCORE SHEETS.")));
			document.newPage();
			document.add(new Paragraph(underlineSelectorNormal.process("****" + season.getYear() + " RULES****")));
			document.add(new Paragraph(selectorSmall.process("-Play it up anywhere, scorecard length, except in a hazard(traps, water, tall grass).")));
			document.add(new Paragraph(
					selectorSmall
							.process("-Water hazards. Move your ball to the other side of the hazard. 1 stroke penalty.(examples:ponds, lakes, creek crossing fairway on #3)")));
			document.add(new Paragraph(
					selectorSmall
							.process("-Lateral hazard. Place ball line of flight or across the hazard no closer to the hole. 1 stroke penalty.(Example:Ball in crud left side of #3 fairway. Place ball north or south side of hazard no closer to hole. Example #2: Hole #6-Ball hit into lake on right side of faiway. Place ball line of flight anywhere between where it entered the lake and it was teed from. Don't take distance and set it in the middle of the fairway.")));
			document.add(new Paragraph(
					selectorSmall
							.process("-In the tall grass we're not going to allow you to set it up this year. You must hit it as it lays or take a 1 stroke penalty and a drop.")));
			document.add(new Paragraph(
					selectorSmall
							.process("-Against an OUT-OF-Bounds fence it is a drop with a 1 stroke penalty. The fence on #16 over the bike path is a free drop. Any other fences constructed inside the course boundaries would be a free drop.")));
			document.add(newLine);

			Paragraph para = new Paragraph(boldSelectorNormal.process("LEAGUE ROSTERS & HANDICAP-"));
			para.add(selectorNormal.process("HANDICAPS BASED ON LAST 5 SCORES AT 90% OF STROKES OVER PAR."));
			document.add(para);

			colWidths = new float[] { 2.5f, 2.5f, 2.5f };
			PdfPTable rosterTable = new PdfPTable(colWidths);
			rosterTable.setHorizontalAlignment(Element.ALIGN_LEFT);

			boolean first = true;
			for (int i = 0; i < teams.size(); i += 3) {
				colWidths = (new float[] { 1.5F, 1.0F });
				PdfPTable rosterTable1 = new PdfPTable(colWidths);
				rosterTable1.addCell(new BorderlessShortPdfPCell(boldUnderlineSelectorSmall.process((new StringBuilder("#")).append(
						((Team) teams.get(i)).toString()).toString())));
				if (first) {
					rosterTable1.addCell(new BorderlessShortPdfPCell(boldUnderlineSelectorSmall.process("HDP")));
				} else {
					rosterTable1.addCell(blankCellNoBorder);
				}
				List<Player> players = new ArrayList<Player>(((Team) teams.get(i)).getPlayers());
				Collections.sort(players);
				for (Player player : players) {
					rosterTable1.addCell(new BorderlessShortPdfPCell(selectorSmall.process(player.toString())));
					rosterTable1.addCell(new BorderlessShortPdfPCell(
							selectorSmall.process(player.getCurrentHandicap(selectedWeek.getDate()) == null ? "" : String.valueOf(player
									.getCurrentHandicap(selectedWeek.getDate())))));
				}

				colWidths = (new float[] { 1.5F, 1.0F });
				PdfPTable rosterTable2 = new PdfPTable(colWidths);
				rosterTable2.addCell(new BorderlessShortPdfPCell(boldUnderlineSelectorSmall.process((new StringBuilder("#")).append(
						((Team) teams.get(i + 1)).toString()).toString())));
				if (first) {
					rosterTable2.addCell(new BorderlessShortPdfPCell(boldUnderlineSelectorSmall.process("HDP")));
				} else {
					rosterTable2.addCell(blankCellNoBorder);
				}
				players = new ArrayList<Player>(((Team) teams.get(i + 1)).getPlayers());
				Collections.sort(players);
				for (Player player : players) {
					rosterTable2.addCell(new BorderlessShortPdfPCell(selectorSmall.process(player.toString())));
					rosterTable2.addCell(new BorderlessShortPdfPCell(
							selectorSmall.process(player.getCurrentHandicap(selectedWeek.getDate()) == null ? "" : String.valueOf(player
									.getCurrentHandicap(selectedWeek.getDate())))));
				}

				colWidths = (new float[] { 1.5F, 1.0F });
				PdfPTable rosterTable3 = new PdfPTable(colWidths);
				rosterTable3.addCell(new BorderlessShortPdfPCell(boldUnderlineSelectorSmall.process((new StringBuilder("#")).append(
						((Team) teams.get(i + 2)).toString()).toString())));
				if (first) {
					rosterTable3.addCell(new BorderlessShortPdfPCell(boldUnderlineSelectorSmall.process("HDP")));
				} else {
					rosterTable3.addCell(blankCellNoBorder);
				}
				players = new ArrayList<Player>(((Team) teams.get(i + 2)).getPlayers());
				Collections.sort(players);
				for (Player player : players) {
					rosterTable3.addCell(new BorderlessShortPdfPCell(selectorSmall.process(player.toString())));
					rosterTable3.addCell(new BorderlessShortPdfPCell(
							selectorSmall.process(player.getCurrentHandicap(selectedWeek.getDate()) == null ? "" : String.valueOf(player
									.getCurrentHandicap(selectedWeek.getDate())))));
				}

				PdfPCell rosterTableCell1 = new PdfPCell(rosterTable1);
				rosterTableCell1.setBorder(0);
				PdfPCell rosterTableCell2 = new PdfPCell(rosterTable2);
				rosterTableCell2.setBorder(0);
				PdfPCell rosterTableCell3 = new PdfPCell(rosterTable3);
				rosterTableCell3.setBorder(0);
				rosterTable.addCell(rosterTableCell1);
				rosterTable.addCell(rosterTableCell2);
				rosterTable.addCell(rosterTableCell3);
			}

			document.add(rosterTable);

			// addGolfOutingPage(document);

			document.close();
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] buildScheduleReport(Season season) {
		List<Round> rounds = new ArrayList<Round>(season.getRounds());
		Collections.sort(rounds);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			Font font_times_large_underline = new Font(Font.TIMES_ROMAN, 14, Font.UNDERLINE);

			Font font_times_normal = new Font(Font.TIMES_ROMAN, 12);
			Font font_times_normal_bold = new Font(Font.TIMES_ROMAN, 12, Font.BOLD);

			FontSelector underlineSelectorLarge = new FontSelector();
			underlineSelectorLarge.addFont(font_times_large_underline);

			FontSelector boldSelectorNormal = new FontSelector();
			boldSelectorNormal.addFont(font_times_normal_bold);

			FontSelector selectorNormal = new FontSelector();
			selectorNormal.addFont(font_times_normal);

			PdfPCell blankCellNoBorder = new PdfPCell(new Phrase(""));
			blankCellNoBorder.setBorder(Rectangle.NO_BORDER);

			Document document = new Document();
			PdfWriter.getInstance(document, baos);
			document.open();

			document.add(new Paragraph(underlineSelectorLarge.process("St. James/Elizabeth Ann Friday Night Men's Golf League")));

			document.add(newLine);

			float[] colWidths = { 1f, 1f, 1f, 1f, .5f, 1f, 1f, 1f };
			PdfPTable table = new PdfPTable(colWidths);
			table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
			table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, boldSelectorNormal.process("SCHEDULE - " + season.getYear()), 8));

			table.addCell(new BorderlessPdfPCell(boldSelectorNormal.process("")));
			table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, boldSelectorNormal.process("FRONT NINE"), 3));
			table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, boldSelectorNormal.process("|")));
			table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, boldSelectorNormal.process("BACK NINE"), 3));

			table.addCell(new BorderlessPdfPCell(boldSelectorNormal.process("")));
			table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, boldSelectorNormal.process(Constants.TEE_TIME_1_GROUP_1)));
			table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, boldSelectorNormal.process(Constants.TEE_TIME_2_GROUP_1)));
			table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, boldSelectorNormal.process(Constants.TEE_TIME_3_GROUP_1)));
			table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, boldSelectorNormal.process("|")));
			table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, boldSelectorNormal.process(Constants.TEE_TIME_4_GROUP_1)));
			table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, boldSelectorNormal.process(Constants.TEE_TIME_5_GROUP_1)));
			table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, boldSelectorNormal.process(Constants.TEE_TIME_6_GROUP_1)));

			table.addCell(new BorderlessPdfPCell(boldSelectorNormal.process("")));
			table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, boldSelectorNormal.process("&")));
			table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, boldSelectorNormal.process("&")));
			table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, boldSelectorNormal.process("&")));
			table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, boldSelectorNormal.process("|")));
			table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, boldSelectorNormal.process("&")));
			table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, boldSelectorNormal.process("&")));
			table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, boldSelectorNormal.process("&")));

			table.addCell(new BorderlessPdfPCell(boldSelectorNormal.process("DATE")));
			table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, boldSelectorNormal.process(Constants.TEE_TIME_1_GROUP_2)));
			table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, boldSelectorNormal.process(Constants.TEE_TIME_2_GROUP_2)));
			table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, boldSelectorNormal.process(Constants.TEE_TIME_3_GROUP_2)));
			table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, boldSelectorNormal.process("|")));
			table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, boldSelectorNormal.process(Constants.TEE_TIME_4_GROUP_2)));
			table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, boldSelectorNormal.process(Constants.TEE_TIME_5_GROUP_2)));
			table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, boldSelectorNormal.process(Constants.TEE_TIME_6_GROUP_2)));

			table.addCell(new BorderBottomPdfPCell(PdfPCell.ALIGN_CENTER, boldSelectorNormal.process(""), 8));

			Week lastWeekOfSeason = null;
			List<Week> weekList;
			boolean printEndOfRoundText = true;
			for (Round round : rounds) {
				weekList = new ArrayList<Week>(round.getWeeks());
				Collections.sort(weekList);
				for (Week week : weekList) {
					lastWeekOfSeason = week;
					table.addCell(new BorderlessPdfPCell(selectorNormal.process(Constants.DATE_FORMAT.format(week.getDate()))));
					table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, selectorNormal.process(week.getFrontNineTeeTime1() != null ? week
							.getFrontNineTeeTime1().toString() : "")));
					table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, selectorNormal.process(week.getFrontNineTeeTime2() != null ? week
							.getFrontNineTeeTime2().toString() : "")));
					table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, selectorNormal.process(week.getFrontNineTeeTime3() != null ? week
							.getFrontNineTeeTime3().toString() : "")));
					table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, selectorNormal.process("|")));
					table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, selectorNormal.process(week.getBackNineTeeTime1() != null ? week
							.getBackNineTeeTime1().toString() : "")));
					table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, selectorNormal.process(week.getBackNineTeeTime2() != null ? week
							.getBackNineTeeTime2().toString() : "")));
					table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, selectorNormal.process(week.getBackNineTeeTime3() != null ? week
							.getBackNineTeeTime3().toString() : "")));
				}
				if (printEndOfRoundText) {
					table.addCell(new BorderlessPdfPCell(boldSelectorNormal.process("")));
					table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, boldSelectorNormal.process("*************END OF " + round.getName()
							+ "*************"), 7));
				}
				printEndOfRoundText = false;
			}

			table.addCell(new BorderlessPdfPCell(selectorNormal.process(Constants.DATE_FORMAT.format(new DateTime(lastWeekOfSeason.getDate())
					.plusWeeks(1).toDate()))));
			table.addCell(new BorderlessPdfPCell(PdfPCell.ALIGN_CENTER, boldSelectorNormal.process("*************PLAYOFF WEEK*************"), 7));

			document.add(table);

			document.close();
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static void addGolfOutingPage(Document document) {
		Font font_times_large = new Font(Font.TIMES_ROMAN, 14);
		Font font_times_large_bold = new Font(Font.TIMES_ROMAN, 14, Font.BOLD);
		Font font_times_small = new Font(Font.TIMES_ROMAN, 10);
		Font font_times_small_bold = new Font(Font.TIMES_ROMAN, 10, Font.BOLD);

		FontSelector selectorLarge = new FontSelector();
		selectorLarge.addFont(font_times_large);

		FontSelector boldSelectorLarge = new FontSelector();
		boldSelectorLarge.addFont(font_times_large_bold);

		FontSelector boldSelectorSmall = new FontSelector();
		boldSelectorSmall.addFont(font_times_small_bold);

		FontSelector selectorSmall = new FontSelector();
		selectorSmall.addFont(font_times_small);

		document.newPage();

		try {
			Paragraph header = new Paragraph(boldSelectorLarge.process("ST. JAMES GOLF FIELD DAY"));
			header.setAlignment(Element.ALIGN_CENTER);
			document.add(header);

			document.add(newLine);

			float[] colWidths = { 1f, 3f };
			PdfPTable table = new PdfPTable(colWidths);
			table.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
			table.addCell(new BorderlessPdfPCell(Element.ALIGN_RIGHT, boldSelectorSmall.process("WHEN:")));
			table.addCell(new BorderlessPdfPCell(selectorSmall.process("JULY 31, 2010")));
			table.addCell(new BorderlessPdfPCell(selectorSmall.process(" ")));
			table.addCell(new BorderlessPdfPCell(selectorSmall.process(" ")));

			table.addCell(new BorderlessPdfPCell(Element.ALIGN_RIGHT, boldSelectorSmall.process("TIME:")));
			table.addCell(new BorderlessPdfPCell(selectorSmall.process("8:00 A.M.  START")));
			table.addCell(new BorderlessPdfPCell(selectorSmall.process(" ")));
			table.addCell(new BorderlessPdfPCell(selectorSmall.process(" ")));

			table.addCell(new BorderlessPdfPCell(Element.ALIGN_RIGHT, boldSelectorSmall.process("WHERE:")));
			table.addCell(new BorderlessPdfPCell(selectorSmall.process("INDIAN TRAILS, BEEMER, NE ")));
			table.addCell(new BorderlessPdfPCell(selectorSmall.process(" ")));
			table.addCell(new BorderlessPdfPCell(selectorSmall.process(" ")));

			table.addCell(new BorderlessPdfPCell(Element.ALIGN_RIGHT, boldSelectorSmall.process("COST:")));
			Paragraph cost = new Paragraph();
			cost.add(selectorSmall.process("$45 MEMBERS/GUESTS $55"));
			cost.add(newLine);
			cost.add(selectorSmall.process("    INCLUDES 18 HOLES OF GOLF W/CART"));
			cost.add(newLine);
			cost.add(selectorSmall.process("    DINNER AFTERWARDS"));
			cost.add(newLine);
			cost.add(selectorSmall.process("    OPTIONAL MONEY GAMES"));
			cost.add(newLine);
			cost.add(selectorSmall.process("    FELLOWSHIP AND RELAXATION"));
			table.addCell(new BorderlessPdfPCell(cost));
			table.addCell(new BorderlessPdfPCell(selectorSmall.process(" ")));
			table.addCell(new BorderlessPdfPCell(selectorSmall.process(" ")));

			table.addCell(new BorderlessPdfPCell(Element.ALIGN_RIGHT, boldSelectorSmall.process("FORMAT:")));
			table.addCell(new BorderlessPdfPCell(selectorSmall.process("18 HOLES OF MEDAL PLAY")));

			document.add(table);

			document.add(newLine);
			document.add(newLine);

			document.add(new Paragraph(boldSelectorLarge.process("ADVANCED REGISTRATION AND PAYMENT REQUIRED!!!!!!!")));
			document.add(new Paragraph(boldSelectorLarge.process("NO LATER THAN JULY 17, 2010")));
			document.add(new Paragraph(boldSelectorLarge.process("*****************************************************")));

			document.add(newLine);

			document.add(new Paragraph(
					selectorLarge
							.process("PLEASE SEND YOUR CHECK(MADE OUT TO ST. JAMES GOLF LEAGUE)  AND BELOW INFORMATION TO KEN MURCEK @ 4736 N. 112TH CR, OMAHA, 68164")));
			document.add(new Paragraph(selectorSmall
					.process("ANY QUESTIONS CALL KEN @ 598-8812, H-493-9022, FAX-493-0319, E-MAIL KMURCEK@STJAMESGOLFLEAGUE.COM")));

			document.add(newLine);
			document.add(separator);
			document.add(newLine);

			document.add(new Paragraph(selectorLarge.process("FOURSOME:")));

			document.add(new Paragraph(selectorLarge.process("_____________________________MEMBER___ GUEST___")));
			document.add(newLine);
			document.add(new Paragraph(selectorLarge.process("_____________________________MEMBER___ GUEST___")));
			document.add(newLine);
			document.add(new Paragraph(selectorLarge.process("_____________________________MEMBER___ GUEST___")));
			document.add(newLine);
			document.add(new Paragraph(selectorLarge.process("_____________________________MEMBER___ GUEST___")));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static class BorderlessPdfPCell extends PdfPCell {

		public BorderlessPdfPCell(Phrase phrase) {
			super(phrase);
			this.setHorizontalAlignment(Element.ALIGN_LEFT);
			this.setPadding(3.0f);
			this.setNoWrap(true);
			this.border = Rectangle.NO_BORDER;
		}

		public BorderlessPdfPCell(int alignment, Phrase phrase) {
			super(phrase);
			this.setHorizontalAlignment(alignment);
			this.setPadding(3.0f);
			this.setNoWrap(true);
			this.border = Rectangle.NO_BORDER;
		}

		public BorderlessPdfPCell(Phrase phrase, int colspan) {
			super(phrase);
			this.setHorizontalAlignment(Element.ALIGN_LEFT);
			this.setPadding(3.0f);
			this.setNoWrap(true);
			this.border = Rectangle.NO_BORDER;
			this.setColspan(colspan);
		}

		public BorderlessPdfPCell(int alignment, Phrase phrase, int colspan) {
			super(phrase);
			this.setHorizontalAlignment(alignment);
			this.setPadding(3.0f);
			this.setNoWrap(true);
			this.border = Rectangle.NO_BORDER;
			this.setColspan(colspan);
		}
	}

	private static class BorderlessShortPdfPCell extends PdfPCell {

		public BorderlessShortPdfPCell(Phrase phrase) {
			super(phrase);
			this.setHorizontalAlignment(Element.ALIGN_LEFT);
			this.setPadding(2f);
			this.setNoWrap(true);
			this.border = Rectangle.NO_BORDER;
		}
	}

	private static class BorderBottomPdfPCell extends PdfPCell {

		public BorderBottomPdfPCell(int alignment, Phrase phrase, int colspan) {
			super(phrase);
			this.setHorizontalAlignment(alignment);
			this.setPadding(3.0f);
			this.setNoWrap(true);
			this.border = Rectangle.BOTTOM;
			this.setColspan(colspan);
		}

	}
}