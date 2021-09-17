package com.in28minutes.springboot.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.springboot.model.Question;
import com.in28minutes.springboot.service.SurveyService;

@RestController
public class SurveyController {

	@Autowired
	SurveyService surveyService;

	@GetMapping("/surveys/{surveyid}/questions")
	public List<Question> retrieveQuestionsForSurvey(@PathVariable String surveyid) {

		return surveyService.retrieveQuestions(surveyid);

	}

	@PostMapping("/surveys/{surveyid}/questions")
	public ResponseEntity<Void> add(@PathVariable String surveyid, @RequestBody Question newQuestion) {

		Question question = surveyService.addQuestion(surveyid, newQuestion);

		if (question == null)
			return ResponseEntity.noContent().build();

		// Success - URI of the new resource in Response Header
		// Status - created

		// URI -> /surveys/{surveyId}/questions/{questionId}
		// question.getQuestionId()
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(question.getId())
				.toUri();
		// Status
		return ResponseEntity.created(location).build();

	}

	@GetMapping("/surveys/{surveyid}/questions/{questionid}")
	public Question retrieveDetailsForQuestion(@PathVariable String surveyid, @PathVariable String questionid) {

		return surveyService.retriveQuestion(surveyid, questionid);

	}

}
