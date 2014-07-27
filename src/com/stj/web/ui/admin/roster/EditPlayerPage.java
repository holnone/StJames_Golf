package com.stj.web.ui.admin.roster;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.stj.model.Course;
import com.stj.model.Player;
import com.stj.model.PreRound;
import com.stj.model.Side;
import com.stj.services.LeagueService;
import com.stj.web.ui.admin.base.AdminBasePage;

public class EditPlayerPage extends AdminBasePage {
	private static final long serialVersionUID = 1L;

	private Player player = new Player();

	@SpringBean
	private LeagueService leagueService;

	private WebPage callingPage;

	private List<Side> sideChoices = new ArrayList<Side>();
	private List<Course> courseChoices = new ArrayList<Course>();

	private Integer preRound1Score;
	private Course preRound1Course;
	private Side preRound1Side;
	private Integer preRound2Score;
	private Course preRound2Course;
	private Side preRound2Side;
	private Integer preRound3Score;
	private Course preRound3Course;
	private Side preRound3Side;
	private Integer preRound4Score;
	private Course preRound4Course;
	private Side preRound4Side;
	private Integer preRound5Score;
	private Course preRound5Course;
	private Side preRound5Side;

	public EditPlayerPage(WebPage page, Player player) {
		this.callingPage = page;
		this.player = player;
		loadPreRounds();
		this.courseChoices.add(leagueService.getTheKnolls());
		this.courseChoices.add(leagueService.getIronwood());
		this.sideChoices.add(leagueService.getTheKnolls().getFrontNine());
		this.sideChoices.add(leagueService.getTheKnolls().getBackNine());
		renderContent();
	}

	private void renderContent() {
		Form<Object> form = new Form<Object>("form");
		form.add(new TextField<String>("firstNameText", new PropertyModel<String>(this.player, "name.firstName")));
		form.add(new TextField<String>("lastNameText", new PropertyModel<String>(this.player, "name.lastName")));
		for (int i = 1; i <= 5; i++) {
			form.add(new TextField<Integer>("preRound" + i + "Score", new PropertyModel<Integer>(this, "preRound" + i + "Score")));
			form.add(new DropDownChoice<Course>("preRound" + i + "Course", new PropertyModel<Course>(this, "preRound" + i + "Course"),
					new ListModel<Course>(courseChoices), getCourseRenderer()));
			form.add(new DropDownChoice<Side>("preRound" + i + "Side", new PropertyModel<Side>(this, "preRound" + i + "Side"), new ListModel<Side>(
					sideChoices), getSideRenderer()));
		}
		form.add(getSaveButton());
		form.add(getCancelButton());
		add(form);
	}

	private Component getSaveButton() {
		return new AjaxButton("saveButton") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				showFeedback(target);
				if (player.getName().getFirstName() == null || player.getName().getLastName() == null) {
					error("Please enter both a First and Last Name.");
				} else {
					populatePreRounds();
					player = leagueService.savePlayer(player);
					getSession().info("Player Saved Successfully.");
					if (callingPage instanceof EditTeamPage) {
						setResponsePage(new EditTeamPage(((EditTeamPage) callingPage).getTeam()));
					} else if (callingPage instanceof RosterPage) {
						setResponsePage(new RosterPage());
					} else {
						setResponsePage(callingPage);
					}
				}
			}
		}.setMarkupId("saveButton");
	}

	private Component getCancelButton() {
		AjaxButton cancelButton = new AjaxButton("cancelButton") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> arg1) {
				setResponsePage(callingPage);
			}
		};
		cancelButton.setDefaultFormProcessing(false);
		return cancelButton;
	}

	private IChoiceRenderer<Course> getCourseRenderer() {
		return new IChoiceRenderer<Course>() {
			private static final long serialVersionUID = 1L;

			public Object getDisplayValue(Course course) {
				return course.toString();
			}

			public String getIdValue(Course course, int index) {
				return course.getId().toString();
			}
		};
	}

	private IChoiceRenderer<Side> getSideRenderer() {
		return new IChoiceRenderer<Side>() {
			private static final long serialVersionUID = 1L;

			public Object getDisplayValue(Side side) {
				return side.toString();
			}

			public String getIdValue(Side side, int index) {
				return side.getId().toString();
			}
		};
	}

	private void loadPreRounds() {
		List<PreRound> list = new ArrayList<PreRound>(player.getPreRounds());
		Collections.sort(list);
		try {
			if (list.size() > 0) {
				this.preRound1Score = list.get(0).getScore();
				this.preRound1Course = list.get(0).getSide().getCourse();
				this.preRound1Side = list.get(0).getSide();
			}
			if (list.size() > 1) {
				this.preRound2Score = list.get(1).getScore();
				this.preRound2Course = list.get(1).getSide().getCourse();
				this.preRound2Side = list.get(1).getSide();
			}
			if (list.size() > 2) {
				this.preRound3Score = list.get(2).getScore();
				this.preRound3Course = list.get(2).getSide().getCourse();
				this.preRound3Side = list.get(2).getSide();
			}
			if (list.size() > 3) {
				this.preRound4Score = list.get(3).getScore();
				this.preRound4Course = list.get(3).getSide().getCourse();
				this.preRound4Side = list.get(3).getSide();
			}
			if (list.size() > 4) {
				this.preRound5Score = list.get(4).getScore();
				this.preRound5Course = list.get(4).getSide().getCourse();
				this.preRound5Side = list.get(4).getSide();
			}
		} catch (Exception e) {
		}
	}

	void populatePreRounds() {
		List<PreRound> list = new ArrayList<PreRound>();
		if (getPreRound1Score() != null && getPreRound1Date() != null && getPreRound1Course() != null && getPreRound1Side() != null) {
			PreRound preRound = new PreRound();
			preRound.setScore(getPreRound1Score());
			preRound.setScoreDate(getPreRound1Date());
			preRound
					.setSide("FT".equals(getPreRound1Side().getSideType()) ? getPreRound1Course().getFrontNine() : getPreRound1Course().getBackNine());
			list.add(preRound);
		}
		if (getPreRound2Score() != null && getPreRound2Date() != null && getPreRound2Course() != null && getPreRound2Side() != null) {
			PreRound preRound = new PreRound();
			preRound.setScore(getPreRound2Score());
			preRound.setScoreDate(getPreRound2Date());
			preRound
					.setSide("FT".equals(getPreRound2Side().getSideType()) ? getPreRound2Course().getFrontNine() : getPreRound2Course().getBackNine());
			list.add(preRound);
		}
		if (getPreRound3Score() != null && getPreRound3Date() != null && getPreRound3Course() != null && getPreRound3Side() != null) {
			PreRound preRound = new PreRound();
			preRound.setScore(getPreRound3Score());
			preRound.setScoreDate(getPreRound3Date());
			preRound
					.setSide("FT".equals(getPreRound3Side().getSideType()) ? getPreRound3Course().getFrontNine() : getPreRound3Course().getBackNine());
			list.add(preRound);
		}
		if (getPreRound4Score() != null && getPreRound4Date() != null && getPreRound4Course() != null && getPreRound4Side() != null) {
			PreRound preRound = new PreRound();
			preRound.setScore(getPreRound4Score());
			preRound.setScoreDate(getPreRound4Date());
			preRound
					.setSide("FT".equals(getPreRound4Side().getSideType()) ? getPreRound4Course().getFrontNine() : getPreRound4Course().getBackNine());
			list.add(preRound);
		}
		if (getPreRound5Score() != null && getPreRound5Date() != null && getPreRound5Course() != null && getPreRound5Side() != null) {
			PreRound preRound = new PreRound();
			preRound.setScore(getPreRound5Score());
			preRound.setScoreDate(getPreRound5Date());
			preRound
					.setSide("FT".equals(getPreRound4Side().getSideType()) ? getPreRound4Course().getFrontNine() : getPreRound4Course().getBackNine());
			list.add(preRound);
		}
		player.getPreRounds().clear();
		player.getPreRounds().addAll(list);
	}

	public void setLeagueService(LeagueService leagueService) {
		this.leagueService = leagueService;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public List<Side> getSideChoices() {
		return sideChoices;
	}

	public void setSideChoices(List<Side> sideChoices) {
		this.sideChoices = sideChoices;
	}

	public Integer getPreRound1Score() {
		return preRound1Score;
	}

	public void setPreRound1Score(Integer preRound1Score) {
		this.preRound1Score = preRound1Score;
	}

	public Date getPreRound1Date() {
		Calendar date = Calendar.getInstance();
		date.set(2010, 1, 1);
		return date.getTime();
	}

	public Side getPreRound1Side() {
		return preRound1Side;
	}

	public void setPreRound1Side(Side preRound1Side) {
		this.preRound1Side = preRound1Side;
	}

	public Integer getPreRound2Score() {
		return preRound2Score;
	}

	public void setPreRound2Score(Integer preRound2Score) {
		this.preRound2Score = preRound2Score;
	}

	public Date getPreRound2Date() {
		Calendar date = Calendar.getInstance();
		date.set(2010, 1, 2);
		return date.getTime();
	}

	public Side getPreRound2Side() {
		return preRound2Side;
	}

	public void setPreRound2Side(Side preRound2Side) {
		this.preRound2Side = preRound2Side;
	}

	public Integer getPreRound3Score() {
		return preRound3Score;
	}

	public void setPreRound3Score(Integer preRound3Score) {
		this.preRound3Score = preRound3Score;
	}

	public Date getPreRound3Date() {
		Calendar date = Calendar.getInstance();
		date.set(2010, 1, 3);
		return date.getTime();
	}

	public Side getPreRound3Side() {
		return preRound3Side;
	}

	public void setPreRound3Side(Side preRound3Side) {
		this.preRound3Side = preRound3Side;
	}

	public Integer getPreRound4Score() {
		return preRound4Score;
	}

	public void setPreRound4Score(Integer preRound4Score) {
		this.preRound4Score = preRound4Score;
	}

	public Date getPreRound4Date() {
		Calendar date = Calendar.getInstance();
		date.set(2010, 1, 4);
		return date.getTime();
	}

	public Side getPreRound4Side() {
		return preRound4Side;
	}

	public void setPreRound4Side(Side preRound4Side) {
		this.preRound4Side = preRound4Side;
	}

	public Integer getPreRound5Score() {
		return preRound5Score;
	}

	public void setPreRound5Score(Integer preRound5Score) {
		this.preRound5Score = preRound5Score;
	}

	public Date getPreRound5Date() {
		Calendar date = Calendar.getInstance();
		date.set(2010, 1, 5);
		return date.getTime();
	}

	public Side getPreRound5Side() {
		return preRound5Side;
	}

	public void setPreRound5Side(Side preRound5Side) {
		this.preRound5Side = preRound5Side;
	}

	public List<Course> getCourseChoices() {
		return courseChoices;
	}

	public void setCourseChoices(List<Course> courseChoices) {
		this.courseChoices = courseChoices;
	}

	public Course getPreRound1Course() {
		return preRound1Course;
	}

	public void setPreRound1Course(Course preRound1Course) {
		this.preRound1Course = preRound1Course;
	}

	public Course getPreRound2Course() {
		return preRound2Course;
	}

	public void setPreRound2Course(Course preRound2Course) {
		this.preRound2Course = preRound2Course;
	}

	public Course getPreRound3Course() {
		return preRound3Course;
	}

	public void setPreRound3Course(Course preRound3Course) {
		this.preRound3Course = preRound3Course;
	}

	public Course getPreRound4Course() {
		return preRound4Course;
	}

	public void setPreRound4Course(Course preRound4Course) {
		this.preRound4Course = preRound4Course;
	}

	public Course getPreRound5Course() {
		return preRound5Course;
	}

	public void setPreRound5Course(Course preRound5Course) {
		this.preRound5Course = preRound5Course;
	}
}
