package com.stj.repo;

import com.stj.model.MessageBoard;
import com.stj.model.Season;

import java.util.List;

public interface MessageBoardRepository {
	MessageBoard saveOrUpdate(MessageBoard message);

	List<MessageBoard> findAll(Season season);
}
