package com.lp.appadmin.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.lp.model.Comments;
import com.lp.utils.CustomHibernateDaoSupport;

@Repository("commentsDao")
public class CommentsDAOImpl extends CustomHibernateDaoSupport implements
		CommentsDAO {
	static final Logger logger = Logger.getLogger(CommentsDAOImpl.class);

	@Override
	public Comments getComments(long commentsId) {
		Comments comm = (Comments) super.find(Comments.class, commentsId);
		return comm;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comments> getCommentsList() {
		List<Comments> commentsList = null;
		commentsList = (List<Comments>) super.findAll(Comments.class);
		return commentsList;
	}

	@Override
	public void deleteComments(long commentsId) {
		super.delete(getComments(commentsId));
	}

	@Override
	public void saveComments(Comments comments) {
		super.saveOrUpdate(comments);
	}
}
