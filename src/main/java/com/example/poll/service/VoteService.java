package com.example.poll.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.poll.entity.Comment;
import com.example.poll.entity.Option;
import com.example.poll.entity.Poll;
import com.example.poll.entity.Vote;
import com.example.poll.exception.VoteException;
import com.example.poll.repository.OptionRepository;
import com.example.poll.repository.VoteRepository;

@Service
public class VoteService {
	@Autowired
	private VoteRepository voteRepository;
	@Autowired
	private OptionRepository optionRepository;
	@Value("${dateformat}")
	private String dateFormat;

	public Vote castVote(Vote vote) throws VoteException {
		Vote voteEntity = null;
		Vote deleteVote = checkIfAlreadyVoted(vote);
		if (deleteVote==null) {
			SimpleDateFormat pattern = new SimpleDateFormat(dateFormat);
			Date date = new Date();
			vote.setVotedDate(pattern.format(date));
			voteEntity = voteRepository.save(vote);
		} else {
			boolean voteDeleted = deleteVote(deleteVote);
			if(voteDeleted) {
				SimpleDateFormat pattern = new SimpleDateFormat(dateFormat);
				Date date = new Date();
				vote.setVotedDate(pattern.format(date));
				voteEntity = voteRepository.save(vote);
			} else {
				throw new VoteException("Error occured while deleting previous vote");
			}
		}
		return voteEntity;
	}

	public Vote viewVote(Long voteId) {
		Optional<Vote> optVoteRes = voteRepository.findById(voteId);
		Vote voteRes = optVoteRes.get();
		return voteRes;
	}

	public List<Poll> findVotedPollsByUser(String votedBy) {
		List<Vote> votesList = voteRepository.findByVotedBy(votedBy);
		List<Poll> pollsList = new ArrayList<>();
		for (Vote voteEntity : votesList) {
			Poll pollEntity = voteEntity.getOption().getPoll();
			formatPoll(pollEntity);
			pollsList.add(pollEntity);
		}
		return pollsList;
	}

	public void formatPoll(Poll pollEntity) {
		Poll clonedPollEntity = new Poll(pollEntity.getQuesId());
		for (Option optionEntity : pollEntity.getOptions()) {
			optionEntity.setPoll(clonedPollEntity);
			Option clonedOptionEntity = new Option(optionEntity.getOptionId());
			for (Vote voteEntity : optionEntity.getVotes()) {
				voteEntity.setOption(clonedOptionEntity);
			}
		}
		for (Comment commentEntity : pollEntity.getComments()) {
			commentEntity.setPoll(clonedPollEntity);
		}
	}

	public Vote checkIfAlreadyVoted(Vote vote) throws VoteException {
		// find poll id
		long optionId = vote.getOption().getOptionId();
		Optional<Option> optOption = optionRepository.findById(optionId);
		Option option = optOption.get();
		long pollId = option.getPoll().getQuesId();
		List<Vote> votesList = voteRepository.findByVotedBy(vote.getVotedBy());
		for (Vote voteEntity : votesList) {
			if (voteEntity.getOption().getPoll().getQuesId() == pollId) {
				//throw new VoteException("This poll is already voted");
				return voteEntity;
			}
		}
		return null;
	}

	public Integer getNumberOfVotedQuestions(String votedBy) {
		Integer count = findVotedPollsByUser(votedBy).size();
		return count;
	}

	public List<Poll> getPopularPollsOfUser(String votedBy) {
		List<Poll> allVotedPolls = findVotedPollsByUser(votedBy);
		List<Poll> filteredPolls = new ArrayList<>();
		Map<Poll, Integer> voteNPolls = new HashMap<>();
		int count = 0;
		for (Poll p : allVotedPolls) {
			count = 0;
			for (Option o : p.getOptions()) {
				count += o.getVotes().size();
			}
			voteNPolls.put(p, count);
		}
		Map<Poll, Integer> sortedVoteNPolls = voteNPolls.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
		count = 0;
		for (Poll p : sortedVoteNPolls.keySet()) {
			if(count>5) {
				break;
			}
			filteredPolls.add(p);
			count++;
			
		}
		return filteredPolls;
	}
	
	public boolean deleteVote(Vote vote) {
		vote = voteRepository.findById(vote.getVoteId()).get();
		if(vote!=null) {
			Option optionEntity = vote.getOption();
			List<Vote> voteEntities = optionEntity.getVotes();
			int index = -1;
			for(Vote voteEntity: voteEntities) {
				index++;
				if(voteEntity.getVoteId() == vote.getVoteId()) {
					break;
				}
			}
			voteEntities.remove(index);
			vote.setOption(null);
			voteRepository.delete(vote);
		}
		return true;
	}
}
