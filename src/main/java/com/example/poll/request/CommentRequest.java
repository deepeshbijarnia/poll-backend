package com.example.poll.request;

public class CommentRequest {
	private long commentId;
	private String comment;
	private String commentedBy;
	private PollRequest poll;
	private String serverMessage;

	public CommentRequest() {
		this.comment = null;
		this.commentedBy = null;
	}

	public CommentRequest(long quesId, String comment, String commentedBy) {
		this.comment = comment;
		this.commentedBy = commentedBy;
	}

	public CommentRequest(long commentId, long quesId, String comment, String commentedBy) {
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

	public PollRequest getPoll() {
		return poll;
	}

	public void setPoll(PollRequest poll) {
		this.poll = poll;
	}
	
	public String getServerMessage() {
		return serverMessage;
	}

	public void setServerMessage(String serverMessage) {
		this.serverMessage = serverMessage;
	}
}
