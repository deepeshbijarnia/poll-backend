package com.example.poll.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Poll {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long quesId;
	private String question;
	private String username;
	private String createdBy;
	private String tag;
	private String createdDate;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, mappedBy = "poll")
	private List<Option> options;
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "poll")
	// @Transient
	private List<Comment> comments;

	public Poll() {
		this.question = null;
		this.createdBy = null;
	}

	public Poll(long quesId) {
		this.quesId = quesId;
		this.options = new ArrayList<Option>();
	}

	public Poll(String question, String createdBy, String tag) {
		this.question = question;
		this.createdBy = createdBy;
		this.tag = tag;
	}

	public Poll(long quesId, String question, String username, String createdBy, String tag, String createdDate) {
		this.quesId = quesId;
		this.question = question;
		this.username = username;
		this.createdBy = createdBy;
		this.tag = tag;
		this.createdDate = createdDate;
		this.options = new ArrayList<Option>();
		this.comments = new ArrayList<Comment>();
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

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
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

	@Override
	public String toString() {
		return "Poll [quesId=" + quesId + ", question=" + question + ", username=" + username + ", createdBy="
				+ createdBy + ", tag=" + tag + ", createdDate=" + createdDate + ", options=" + options + ", comments="
				+ comments + "]";
	}

}
