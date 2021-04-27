package com.example.poll.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Option {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long optionId;
	private String option;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "quesId")
	private Poll poll;
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER, mappedBy = "option")
	// @Transient
	private List<Vote> votes;

	public Option() {
		this.option = null;
	}

	public Option(long optionId) {
		this.optionId = optionId;
	}

	public Option(String option) {
		this.option = option;
	}

	public long getOptionId() {
		return optionId;
	}

	public void setOptionId(long optionId) {
		this.optionId = optionId;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public Poll getPoll() {
		return poll;
	}

	public void setPoll(Poll poll) {
		this.poll = poll;
	}

	public List<Vote> getVotes() {
		return votes;
	}

	public void setVotes(List<Vote> votes) {
		this.votes = votes;
	}

	@Override
	public String toString() {
		return "Option [optionId=" + optionId + ", option=" + option + ", poll=" + poll + ", votes=" + votes + "]";
	}

}
