package com.lp.appadmin.dao;

import java.util.List;

import com.lp.model.Comments;

public interface CommentsDAO {
	
	public Comments getComments(long commentsId);
	
	public List<Comments> getCommentsList();
	
	public void deleteComments(long commentsId);
	
	public void saveComments(Comments comments);

}
