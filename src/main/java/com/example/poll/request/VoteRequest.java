package com.example.poll.request;

public class VoteRequest {

	private long voteId;
	private String votedBy;
	private String votedDate;
	private OptionRequest option;
	private String serverMessage;

	public long getVoteId() {
		return voteId;
	}

	public void setVoteId(long voteId) {
		this.voteId = voteId;
	}

	public VoteRequest() {
		this.votedBy = null;
	}

	public VoteRequest(String votedBy) {
		this.votedBy = votedBy;
	}

	public String getVotedBy() {
		return votedBy;
	}

	public void setVotedBy(String votedBy) {
		this.votedBy = votedBy;
	}

	public OptionRequest getOption() {
		return option;
	}

	public void setOption(OptionRequest option) {
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
