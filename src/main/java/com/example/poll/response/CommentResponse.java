package com.example.poll.response;

public class CommentResponse {
	private long commentId;
	private String comment;
	private String commentedBy;
	private PollResponse poll;
	private String serverMessage;

	public CommentResponse() {
		this.comment = null;
		this.commentedBy = null;
	}

	public CommentResponse(long quesId, String comment, String commentedBy) {
		this.comment = comment;
		this.commentedBy = commentedBy;
	}

	public CommentResponse(long commentId, long quesId, String comment, String commentedBy) {
		this.commentId = commentId;
		this.comment = comment;
		this.commentedBy = commentedBy;
	}

	public long getCommentId() {
		return commentId;
	}

	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCommentedBy() {
		return commentedBy;
	}

	public void setCommentedBy(String commentedBy) {
		this.commentedBy = commentedBy;
	}

	public PollResponse getPoll() {
		return poll;
	}

	public void setPoll(PollResponse poll) {
		this.poll = poll;
	}
	
	public String getServerMessage() {
		return serverMessage;
	}

	public void setServerMessage(String serverMessage) {
		this.serverMessage = serverMessage;
	}
}
