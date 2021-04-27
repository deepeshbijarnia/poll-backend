package com.example.poll.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.poll.entity.Comment;
import com.example.poll.entity.Poll;
import com.example.poll.repository.CommentRepository;

@Service
public class CommentService {
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private PollService pollService;

	public List<Comment> getComments(Long pollId) {
		List<Comment> comments = new ArrayList<>();
		Poll poll = pollService.viewPoll(pollId);
		comments = commentRepository.findByPoll(poll);
		// comments = commentRepository.findByQuesId(pollId);
		return comments;
	}

	public Comment postComment(Comment comment) {
		Comment commentEntity = commentRepository.save(comment);
		return commentEntity;
	}

	public Integer getNumberOfCommentedQuestions(String commentedBy) {
		Integer count = commentRepository.findByCommentedBy(commentedBy).size();
		return count;
	}
}
