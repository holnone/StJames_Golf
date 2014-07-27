package com.stj.repo;

import java.util.List;

import com.stj.model.MessageBoard;
import com.stj.model.Season;

public interface MessageBoardRepository {
	MessageBoard saveOrUpdate(MessageBoard message);

	List<MessageBoard> findAll(Season season);
}
