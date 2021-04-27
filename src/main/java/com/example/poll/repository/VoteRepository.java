package com.example.poll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.poll.entity.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

	List<Vote> findByVotedBy(String votedBy);
}
