package com.in28minutes.springboot.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.in28minutes.springboot.Application;
import com.in28minutes.springboot.model.Question;
import com.in28minutes.springboot.service.SurveyService;


@RunWith(SpringRunner.class)
@WebMvcTest(SurveyController.class)
class SurveyControllerTest {
	
	@MockBean
	public SurveyService surveyService;
	
	@Autowired
	MockMvc mockMvc;
	
	@Test
	public void retrieveDetailsForQuestion() throws Exception {
		//use this specific data
		//expect this response
		Question mockQuestion = new Question("Question1", "Largest Country in the World", "Russia",
				Arrays.asList("India", "Russia", "United States", "China"));
		
		Mockito.when(surveyService.retriveQuestion(Mockito.anyString(), Mockito.anyString())).thenReturn(mockQuestion);
		
		RequestBuilder requestBuilder =MockMvcRequestBuilders.get("/surveys/Survey1/questions/Question1").accept(MediaType.APPLICATION_JSON);
		  
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			
		   String expected="{\"id\":\"Question1\",\"description\":\"Largest Country in the World\",\"correctAnswer\":\"Russia\"}";
		  
		   JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void retrieveQuestionsForSurvey() throws Exception {
		//use this specific data
		//expect this response
		List<Question> mockList = Arrays.asList(
				new Question("Question1", "First Alphabet", "A",Arrays.asList("A", "B", "C", "D")),
				new Question("Question2", "Last Alphabet", "Z",Arrays.asList("A", "X", "Y", "Z")));
		
		Mockito.when(surveyService.retrieveQuestions(Mockito.anyString())).thenReturn(mockList);
		  
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/surveys/Survey1/questions").accept(MediaType.APPLICATION_JSON)).andReturn();
			System.out.println(result);
		 String expected = "["
	                + "{\"id\":\"Question1\",\"description\":\"First Alphabet\",\"correctAnswer\":\"A\"},"
	                +"{\"id\":\"Question2\",\"description\":\"Last Alphabet\",\"correctAnswer\":\"Z\"}"
	                + "]";

		   JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
	
	@Test
    public void createSurveyQuestion() throws Exception {
    		Question mockQuestion = new Question("1", "Smallest Number", "1",
				Arrays.asList("1", "2", "3", "4"));

		String questionJson = "{\"description\":\"Smallest Number\",\"correctAnswer\":\"1\",\"options\":[\"1\",\"2\",\"3\",\"4\"]}";
		//surveyService.addQuestion to respond back with mockQuestion
		Mockito.when(
				surveyService.addQuestion(Mockito.anyString(), Mockito
						.any(Question.class))).thenReturn(mockQuestion);

		//Send question as body to /surveys/Survey1/questions
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				"/surveys/Survey1/questions")
				.accept(MediaType.APPLICATION_JSON).content(questionJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		assertEquals("http://localhost/surveys/Survey1/questions/1", response
				.getHeader(HttpHeaders.LOCATION));
    
    }

}
