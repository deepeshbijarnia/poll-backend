package com.example.poll.response;

import java.util.List;

public class PollResponse {
	private long quesId;
	private String question;
	private String username;
	private String createdBy;
	private String tag;
	private String createdDate;
	private List<OptionResponse> options;
	private List<CommentResponse> comments;
	private String serverMessage;

	public PollResponse() {
		this.question = null;
		this.createdBy = null;
	}

	public PollResponse(String question, String createdBy, String tag) {
		this.question = question;
		this.createdBy = createdBy;
		this.tag = tag;
	}

	public PollResponse(long quesId, String question, String createdBy, String tag) {
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

	public List<OptionResponse> getOptions() {
		return options;
	}

	public void setOptions(List<OptionResponse> options) {
		this.options = options;
	}

	public List<CommentResponse> getComments() {
		return comments;
	}

	public void setComments(List<CommentResponse> comments) {
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
