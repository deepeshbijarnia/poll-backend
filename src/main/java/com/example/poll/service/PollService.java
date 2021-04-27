package com.example.poll.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.poll.entity.Comment;
import com.example.poll.entity.Option;
import com.example.poll.entity.Poll;
import com.example.poll.entity.Vote;
import com.example.poll.exception.PollException;
import com.example.poll.repository.PollRepository;

@Service
public class PollService {

	@Autowired
	private PollRepository pollRepository;
	@Autowired
	private OptionService optionService;
	@Value("${dateformat}")
	private String dateFormat;

	public Poll createPoll(Poll poll) {
		SimpleDateFormat pattern = new SimpleDateFormat(dateFormat);
		Date date = new Date();
		poll.setCreatedDate(pattern.format(date));
		List<Option> pollOptions = poll.getOptions();
		poll = pollRepository.save(poll);
		for (Option o : pollOptions) {
			o.setPoll(poll);
			optionService.updateOptions(o);
		}
		return poll;
	}

	public Poll viewPoll(Long pollId) {
		Optional<Poll> optPollRes = pollRepository.findById(pollId);
		Poll pollRes = optPollRes.get();
		formatPoll(pollRes);
//		return pollRes;
		Poll clonedPollResponse = new Poll(pollRes.getQuesId(), pollRes.getQuestion(), pollRes.getUsername(),
				pollRes.getCreatedBy(), pollRes.getTag(), pollRes.getCreatedDate());
		List<Option> pollOptions = pollRes.getOptions();
		Set<Option> uniquePollOptions = new LinkedHashSet<Option>(pollOptions);
		for (Option o : uniquePollOptions) {
			clonedPollResponse.getOptions().add(o);
		}
		for (Comment c : pollRes.getComments()) {
			clonedPollResponse.getComments().add(c);
		}
		return clonedPollResponse;
	}

	public Poll updatePoll(Poll poll) throws PollException {
		Long pollId = poll.getQuesId();
		Poll pollEntity = null;
		Optional<Poll> optPollRes = pollRepository.findById(pollId);

		if (optPollRes.isPresent()) {
			pollEntity = optPollRes.get();
			List<Option> pollEntityOptions = pollEntity.getOptions();
			List<Option> pollOptions = poll.getOptions();
//			Poll clonedPollRes = new Poll(pollId);
//			for (Option o : pollOptions) {
//				o.setPoll(clonedPollRes);
//			}
			formatPoll(poll);
			pollEntity.setQuestion(poll.getQuestion());
			pollEntity.setOptions(poll.getOptions());
			pollEntity = pollRepository.save(pollEntity);

			// [start]handle deleted options
			List<Option> deleteOptions = pollEntityOptions.stream()
					.filter(entityOption -> pollOptions.stream()
							.allMatch(option -> entityOption.getOptionId() != option.getOptionId()))
					.collect(Collectors.toList());
			if (!deleteOptions.isEmpty()) {
				boolean result = optionService.deleteOptions(deleteOptions);
				System.out.println("Deleted Options: " + result);
			}
			// [end]handle deleted options
		} else {
			throw new PollException("Poll " + pollId + " not present in db");
		}
		return pollEntity;
	}

	public List<Poll> findPollsByUser(String createdBy) {
		List<Poll> pollsList = pollRepository.findByCreatedBy(createdBy);
		for (Poll pollEntity : pollsList) {
//			Poll clonedPollEntity = new Poll(pollEntity.getQuesId());
//			for (Option optionEntity : pollEntity.getOptions()) {
//				optionEntity.setPoll(clonedPollEntity);
//			}
			formatPoll(pollEntity);
		}
		return pollsList;
	}

	public List<Poll> findPollsByAllUser() {
		List<Poll> pollsList = pollRepository.findAll();
		for (Poll pollEntity : pollsList) {
//			Poll clonedPollEntity = new Poll(pollEntity.getQuesId());
//			for (Option optionEntity : pollEntity.getOptions()) {
//				optionEntity.setPoll(clonedPollEntity);
//			}
			formatPoll(pollEntity);
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

	public Integer getNumberOfPostedQuestions(String createdBy) {
		Integer count = findPollsByUser(createdBy).size();
		return count;
	}
}
