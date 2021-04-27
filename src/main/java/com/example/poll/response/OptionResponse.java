package com.example.poll.response;

import java.util.List;

public class OptionResponse {
	private long optionId;
	private String option;
	private PollResponse poll;
	private List<VoteResponse> votes;

	public OptionResponse() {
		this.option = null;
	}

	public OptionResponse(String option) {
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

	public PollResponse getPoll() {
		return poll;
	}

	public void setPoll(PollResponse poll) {
		this.poll = poll;
	}

	public List<VoteResponse> getVotes() {
		return votes;
	}

	public void setVotes(List<VoteResponse> votes) {
		this.votes = votes;
	}

}
