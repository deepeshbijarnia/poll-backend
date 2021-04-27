package com.example.poll.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.poll.entity.Option;
import com.example.poll.exception.PollException;
import com.example.poll.repository.OptionRepository;

@Service
public class OptionService {
	@Autowired
	private OptionRepository optionRepository;

	Option updateOptions(Option option) {
		return optionRepository.save(option);
	}

	boolean deleteOptions(List<Option> options) throws PollException {
		for (Option o : options) {
			if (optionRepository.findById(o.getOptionId()) != null) {
				o.setPoll(null);
				optionRepository.delete(o);
			} else {
				throw new PollException("Option with option id: " + o.getOptionId());
			}
		}
		return true;
	}
}
