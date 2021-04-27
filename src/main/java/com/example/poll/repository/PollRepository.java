package com.example.poll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.poll.entity.Poll;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {

	public List<Poll> findByCreatedBy(String createdBy);
}
