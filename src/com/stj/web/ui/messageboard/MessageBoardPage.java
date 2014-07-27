package com.stj.web.ui.messageboard;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.stj.model.MessageBoard;
import com.stj.model.Season;
import com.stj.services.LeagueService;
import com.stj.services.impl.DefaultMailService;
import com.stj.util.Constants;
import com.stj.web.ui.base.BasePage;

public class MessageBoardPage extends BasePage {
	private static final long serialVersionUID = 1L;

	private final static DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy hh:mm a");

	@SpringBean
	private LeagueService leagueService;

	@SpringBean
	private DefaultMailService defaultMailService;
	
	private Season season = new Season();

	WebMarkupContainer messageBoardContainer;

	MessageBoard messageBoard = new MessageBoard();

	List<MessageBoard> messages = new ArrayList<MessageBoard>();

	Form<Object> form = new Form<Object>("form");
	
	public MessageBoardPage() {
		init();

	}

	private void init() {
		season = leagueService.getSeason(Constants.CURRENT_YEAR);
		refreshMessages();
		renderContent();
	}

	private void refreshMessages() {
		messages = leagueService.getMessageBoard(season);
	}

	private void renderContent() {
		form.setOutputMarkupId(true);
		form.add(new TextField<String>("nameText", new PropertyModel<String>(this.messageBoard, "userName")).setOutputMarkupId(true));
		form.add(new TextArea<String>("messageText", new PropertyModel<String>(this.messageBoard, "message")).setOutputMarkupId(true));
		form.add(getSaveButton());
		add(form);

		add(getMessageBoardContainer());
		RepeatingView repeater = new RepeatingView("repeater");
		getMessageBoardContainer().add(repeater);

		int i = 0;
		for (MessageBoard message : messages) {
			WebMarkupContainer container = new WebMarkupContainer(repeater.newChildId());
			container.setOutputMarkupId(true);
			container.add(AttributeModifier.replace("class", (i % 2 == 0) ? "even" : "odd"));
			repeater.add(container);

			container.add(new Label("userName", message.getUserName()).add(AttributeModifier.replace("class", "bold")));
			container.add(new Label("messageDate", DATE_FORMAT.format(message.getCreatedDate())));
			container.add(new Label("message", message.getMessage()).add(AttributeModifier.replace("class", "bold")));
			i++;
		}
	}

	private Component getSaveButton() {
		return new AjaxButton("saveButton") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				showFeedback(target);
				if (messageBoard.getUserName() == null || messageBoard.getMessage() == null) {
					error("Please enter both your name and a message.");
				} else {
					messageBoard.setSeason(season);
					messageBoard.setCreatedDate(new Date());
					messageBoard = leagueService.saveMessageBoard(messageBoard);
					//defaultMailService.sendMessageBoard(messageBoard);
					getSession().info("Message Saved Successfully.");
					setResponsePage(MessageBoardPage.class);
				}
			}
		}.setMarkupId("saveButton");
	}

	private WebMarkupContainer getMessageBoardContainer() {
		if (messageBoardContainer == null) {
			messageBoardContainer = new WebMarkupContainer("messageBoardContainer");
			messageBoardContainer.setOutputMarkupId(true);
		}
		return messageBoardContainer;
	}

	public void setLeagueService(LeagueService leagueService) {
		this.leagueService = leagueService;
	}

	public MessageBoard getMessageBoard() {
		return messageBoard;
	}

	public void setMessageBoard(MessageBoard messageBoard) {
		this.messageBoard = messageBoard;
	}

	public void setDefaulMailService(DefaultMailService defaultMailService) {
		this.defaultMailService = defaultMailService;
	}
}
