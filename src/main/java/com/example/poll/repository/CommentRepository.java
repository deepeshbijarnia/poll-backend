package com.example.poll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.poll.entity.Comment;
import com.example.poll.entity.Poll;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	public List<Comment> findByCommentedBy(String commentedBy);

//	@Query(value = "select * from comment where ques_id = :ques_id", nativeQuery = true)
//	public List<Comment> findByQuesId(@Param("ques_id") Long quesId);

	public List<Comment> findByPoll(Poll poll);
}
