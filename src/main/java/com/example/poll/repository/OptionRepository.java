package com.example.poll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.poll.entity.Option;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {

}
