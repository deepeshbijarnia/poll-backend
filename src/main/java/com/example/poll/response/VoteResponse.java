package com.example.poll.response;

public class VoteResponse {

	private long voteId;
	private String votedBy;
	private String votedDate;
	private OptionResponse option;
	private String serverMessage;

	public VoteResponse() {
		this.votedBy = null;
	}

	public VoteResponse(String votedBy) {
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

	public OptionResponse getOption() {
		return option;
	}

	public void setOption(OptionResponse option) {
		this.option = option;
	}

	public String getVotedDate() {
		return votedDate;
	}

	public void setVotedDate(String votedDate) {
		this.votedDate = votedDate;
	}

	public String getServerMessage() {
		return serverMessage;
	}

	public void setServerMessage(String serverMessage) {
		this.serverMessage = serverMessage;
	}
}
