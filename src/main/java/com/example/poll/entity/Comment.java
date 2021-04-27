package com.example.poll.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long commentId;
	private String comment;
	private String commentedBy;

	@ManyToOne
	@JoinColumn(name = "quesId")
	private Poll poll;

	public Comment() {
		this.comment = null;
		this.commentedBy = null;
	}

	public Comment(long quesId, String comment, String commentedBy) {
		this.comment = comment;
		this.commentedBy = commentedBy;
	}

	public Comment(long commentId, long quesId, String comment, String commentedBy) {
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

	public Poll getPoll() {
		return poll;
	}

	public void setPoll(Poll poll) {
		this.poll = poll;
	}
}
