package com.example.poll.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Vote {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long voteId;
	private String votedBy;
	private String votedDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "optionId")
	private Option option;

	public Vote() {
		this.votedBy = null;
	}

	public Vote(String votedBy) {
		this.votedBy = votedBy;
	}

	public long getVoteId() {
		return voteId;
	}

	public void setVoteId(long voteId) {
		this.voteId = voteId;
	}

	public String getVotedBy() {
		return votedBy;
	}

	public void setVotedBy(String votedBy) {
		this.votedBy = votedBy;
	}

	public Option getOption() {
		return option;
	}

	public void setOption(Option option) {
		this.option = option;
	}

	public String getVotedDate() {
		return votedDate;
	}

	public void setVotedDate(String votedDate) {
		this.votedDate = votedDate;
	}

	@Override
	public String toString() {
		return "Vote [voteId=" + voteId + ", votedBy=" + votedBy + ", votedDate=" + votedDate + ", option=" + option
				+ "]";
	}

}
