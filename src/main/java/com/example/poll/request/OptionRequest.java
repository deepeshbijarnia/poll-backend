package com.example.poll.request;

import java.util.List;

public class OptionRequest {
	private long optionId;
	private String option;
	private PollRequest poll;
	private List<VoteRequest> votes;

	public OptionRequest() {
		this.option = null;
	}

	public OptionRequest(String option) {
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

	public PollRequest getPoll() {
		return poll;
	}

	public void setPoll(PollRequest poll) {
		this.poll = poll;
	}

	public List<VoteRequest> getVotes() {
		return votes;
	}

	public void setVotes(List<VoteRequest> votes) {
		this.votes = votes;
	}

}
