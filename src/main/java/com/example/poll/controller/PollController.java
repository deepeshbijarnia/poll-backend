package com.example.poll.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.poll.entity.Comment;
import com.example.poll.entity.Poll;
import com.example.poll.entity.User;
import com.example.poll.entity.Vote;
import com.example.poll.request.CommentRequest;
import com.example.poll.request.PollRequest;
import com.example.poll.request.VoteRequest;
import com.example.poll.response.CommentResponse;
import com.example.poll.response.PollResponse;
import com.example.poll.response.VoteResponse;
import com.example.poll.service.CommentService;
import com.example.poll.service.PollService;
import com.example.poll.service.UserService;
import com.example.poll.service.VoteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/poll")
public class PollController {

	@Autowired
	private PollService pollService;
	@Autowired
	private VoteService voteService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private UserService userService;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private ObjectMapper objectMapper;

	@PostMapping("/create-poll")
	public PollResponse createPoll(@RequestBody PollRequest pollRequest) {
		PollResponse pollResponse = new PollResponse();
		try {
			Poll poll = modelMapper.map(pollRequest, Poll.class);
			Poll pollRes = pollService.createPoll(poll);
			pollRes = pollService.viewPoll(pollRes.getQuesId());
			if (pollRes != null) {
				pollResponse = modelMapper.map(pollRes, PollResponse.class);
				pollResponse.setServerMessage("Poll created successfully");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return pollResponse;
	}

	@PostMapping("/update-poll")
	public PollResponse updatePoll(@RequestBody PollRequest pollRequest) {
		PollResponse pollResponse = new PollResponse();
		try {
			Poll poll = objectMapper.readValue(objectMapper.writeValueAsBytes(pollRequest), Poll.class);
			Poll pollRes = pollService.updatePoll(poll);
			pollRes = pollService.viewPoll(pollRes.getQuesId());
			if (pollRes != null) {
				pollResponse = modelMapper.map(pollRes, PollResponse.class);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return pollResponse;
	}

	@GetMapping("/view-poll")
	public PollResponse viewPoll(@RequestParam String quesId) {
		PollResponse pollResponse = new PollResponse();
		try {
			Poll poll = pollService.viewPoll(Long.parseLong(quesId));
			if (poll != null) {
				pollResponse = modelMapper.map(poll, PollResponse.class);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return pollResponse;
	}

	@GetMapping("/view-my-polls")
	public List<PollResponse> viewMyPolls(@RequestParam Long userId) {
		List<PollResponse> pollResponses = new ArrayList<>();
		try {
			User user = userService.findUser(userId);
			List<Poll> polls = pollService.findPollsByUser(user.getEmail());
			for (Poll poll : polls) {
				pollResponses.add(objectMapper.readValue(objectMapper.writeValueAsBytes(poll), PollResponse.class));
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return pollResponses;
	}

	@GetMapping("/public-polls")
	public List<PollResponse> getAllPublicPolls() {
		List<PollResponse> pollResponses = new ArrayList<>();
		try {
			List<Poll> polls = pollService.findPollsByAllUser();
			for (Poll poll : polls) {
				pollResponses.add(objectMapper.readValue(objectMapper.writeValueAsBytes(poll), PollResponse.class));
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return pollResponses;
	}

	@PostMapping("/vote")
	public VoteResponse castVote(@RequestBody VoteRequest voteRequest) {
		VoteResponse voteResponse = new VoteResponse();
		String serverMessage = "";
		try {
			Vote vote = objectMapper.readValue(objectMapper.writeValueAsBytes(voteRequest), Vote.class);
			if (voteService.checkIfAlreadyVoted(vote) != null) {
				serverMessage = "Vote Updated Successfully";
			} else {
				serverMessage = "Vote Added Successfully";
			}
			Vote voteRes = voteService.castVote(vote);
			// voteRes = voteService.viewVote(voteRes.getVoteId());
			voteResponse = objectMapper.readValue(objectMapper.writeValueAsBytes(voteRes), VoteResponse.class);
			voteResponse.setServerMessage(serverMessage);

		} catch (Exception e) {
			System.err.println(e.getMessage());
			voteResponse.setServerMessage(e.getMessage());
		}
		return voteResponse;
	}

	@GetMapping("/view-my-votes")
	public List<PollResponse> viewMyVotes(@RequestParam Long userId) {
		List<PollResponse> pollResponses = new ArrayList<>();
		try {
			User user = userService.findUser(userId);
			List<Poll> polls = voteService.findVotedPollsByUser(user.getEmail());
			for (Poll poll : polls) {
				pollResponses.add(objectMapper.readValue(objectMapper.writeValueAsBytes(poll), PollResponse.class));
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return pollResponses;
	}

	@GetMapping("/comment")
	public List<CommentResponse> getComments(@RequestParam Long pollId) {
		List<CommentResponse> commentResponse = new ArrayList<>();
		try {
			List<Comment> comments = commentService.getComments(pollId);
			for (Comment comment : comments) {
				System.out.println(comment);
				commentResponse
						.add(objectMapper.readValue(objectMapper.writeValueAsBytes(comment), CommentResponse.class));
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return commentResponse;
	}

	@PostMapping("/comment")
	public CommentResponse postComment(@RequestBody CommentRequest commentRequest) {
		CommentResponse commentResponse = new CommentResponse();
		try {
			Comment comment = objectMapper.readValue(objectMapper.writeValueAsBytes(commentRequest), Comment.class);
			comment = commentService.postComment(comment);
			commentResponse = objectMapper.readValue(objectMapper.writeValueAsBytes(comment), CommentResponse.class);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return commentResponse;
	}

	@GetMapping("/number-of-posted-ques")
	public Integer getNumberOfPostedQuestions(@RequestParam String email) {
		Integer count = 0;
		try {
			count = pollService.getNumberOfPostedQuestions(email);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return count;
	}

	@GetMapping("/number-of-voted-ques")
	public Integer getNumberOfVotedQuestions(@RequestParam String email) {
		Integer count = 0;
		try {
			count = voteService.getNumberOfVotedQuestions(email);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return count;
	}

	@GetMapping("/number-of-comments")
	public Integer getNumberOfComments(@RequestParam String email) {
		Integer count = 0;
		try {
			count = commentService.getNumberOfCommentedQuestions(email);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return count;
	}

	@GetMapping("/popular-polls")
	public List<PollResponse> getPopularPollsOfUser(@RequestParam Long userId) {
		List<PollResponse> pollResponses = new ArrayList<>();
		try {
			User user = userService.findUser(userId);
			List<Poll> polls = voteService.getPopularPollsOfUser(user.getEmail());
			for (Poll poll : polls) {
				pollResponses.add(objectMapper.readValue(objectMapper.writeValueAsBytes(poll), PollResponse.class));
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return pollResponses;
	}

}
