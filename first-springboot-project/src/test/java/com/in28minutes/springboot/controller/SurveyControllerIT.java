package com.in28minutes.springboot.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.aspectj.lang.annotation.Before;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.in28minutes.springboot.Application;
import com.in28minutes.springboot.model.Question;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SurveyControllerIT {
	
	@LocalServerPort
	private String port;
	
	TestRestTemplate restTemplate=new TestRestTemplate();
	
	HttpHeaders headers=new HttpHeaders();
	
	
  @BeforeAll
  public void beforeAll() {
	  headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
  }
	
	
	@Test
	void testRetriveSurveyQuestion() throws JSONException {
		
		
        HttpEntity<String> entity=new HttpEntity<String>(null,headers);		
		
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/surveys/Survey1/questions/Question1"), HttpMethod.GET,entity,String.class);
		
	String expected="{\"id\":\"Question1\",\"description\":\"Largest Country in the World\",\"correctAnswer\":\"Russia\"}";
	
	JSONAssert.assertEquals(expected,response.getBody(), false);
	
	}
	
	@Test
	void addQuestion() throws JSONException {
		
		Question question=new Question("Doesntmatter", "Question1", "Russia",
				Arrays.asList("India", "Russia", "United States", "China"));
       
		HttpEntity entity=new HttpEntity<Question>(question,headers);	
		
		
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/surveys/Survey1/questions"), HttpMethod.POST,entity,String.class);
        
        String actual=response.getHeaders().get(HttpHeaders.LOCATION).get(0);
        
        System.out.println(actual);
        
        assertTrue(actual.contains("/surveys/Survey1/questions/"));
		
	   
	    
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:"+port+uri;
	}



}
