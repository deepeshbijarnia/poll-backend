package com.example.poll.request;

import java.util.List;

public class PollRequest {

	private long quesId;
	private String question;
	private String username;
	private String createdBy;
	private String tag;
	private String createdDate;
	private List<OptionRequest> options;
	private List<CommentRequest> comments;
	private String serverMessage;

	public PollRequest() {
		this.question = null;
		this.createdBy = null;
	}

	public PollRequest(String question, String createdBy, String tag) {
		this.question = question;
		this.createdBy = createdBy;
		this.tag = tag;
	}

	public PollRequest(long quesId, String question, String createdBy, String tag) {
		this.quesId = quesId;
		this.question = question;
		this.createdBy = createdBy;
		this.tag = tag;
	}

	public long getQuesId() {
		return quesId;
	}

	public void setQuesId(long quesId) {
		this.quesId = quesId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public List<OptionRequest> getOptions() {
		return options;
	}

	public void setOptions(List<OptionRequest> options) {
		this.options = options;
	}

	public List<CommentRequest> getComments() {
		return comments;
	}

	public void setComments(List<CommentRequest> comments) {
		this.comments = comments;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getServerMessage() {
		return serverMessage;
	}

	public void setServerMessage(String serverMessage) {
		this.serverMessage = serverMessage;
	}
}
