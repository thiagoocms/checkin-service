package com.nassau.checkinservice.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.nassau.checkinservice.exception.BadRequestException;
import com.nassau.checkinservice.exception.ConflictException;
import com.nassau.checkinservice.exception.ResourceNotFoundException;
import com.nassau.checkinservice.util.JsonParser;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import java.io.IOException;

@Service
public class AbstractMessage {

	//**********************************************************************************
	// PUBLIC ATTRIBUTES
	//**********************************************************************************
	
	/** Default logger */
    public final Logger log = LoggerFactory.getLogger(AbstractMessage.class);
    
    @Autowired
 	public MessageService messageService;

	@Autowired
	public ModelMapper modelMapper;

	@Autowired
	public ObjectMapper objectMapper;

    //**********************************************************************************
 	// PUBLIC FUNCTIONS
 	//**********************************************************************************

 	public Throwable throwsException(String key, String... args) throws IOException {
		RestClientException ex = new RestClientException(buildMessage(key,args));
		ex.printStackTrace();
		throw ex;
 	}

	public Throwable conflictException(String key, String... args) throws IOException {
		ConflictException ex = new ConflictException(buildMessage(key,args));
		ex.printStackTrace();
		throw ex;
	}

	public Throwable badRequestException(String key, String... args) throws IOException {
		BadRequestException ex = new BadRequestException(buildMessage(key,args));
		ex.printStackTrace();
		throw ex;
	}

	public Throwable resourceNotFoundException(String key, String... args) throws IOException {
		ResourceNotFoundException ex = new ResourceNotFoundException(buildMessage(key, args));
		ex.printStackTrace();
		throw ex;
	}

 	public void throwsException(HttpStatusCodeException e) {
		RestClientException ex = new RestClientException(JsonParser.doErrorParser(e).getMessage());
		ex.printStackTrace();
		throw ex;
 	}

 	public void throwsException(String message) throws IOException {
 		RestClientException ex = new RestClientException(buildMessage(message,""));
		ex.printStackTrace();
		throw ex;
 	}
 	
 	public void throwsExceptionWithoutBuildMessage(String message) throws IOException {
 		RestClientException ex = new RestClientException(message);
		ex.printStackTrace();
		throw ex;
 	}

 	public static void throwsStaticException(String message) throws IOException {
 		AbstractMessage msg = new AbstractMessage();
 		throw new RestClientException(msg.buildMessage(message,""));
 	}
 	
 	public void throwsRuntimeException(String key, String... args) throws RuntimeException {
 		RuntimeException ex = new RuntimeException(buildMessage(key,args));
		ex.printStackTrace();
		throw ex;
 	}
 	
 	//**********************************************************************************
	// PRIVATE FUNCTIONS
	//**********************************************************************************

	public String buildMessage(String key, String... args) {
		String errorMessage = messageService.getMessage(key, args);
		return errorMessage;
	}

	public HttpHeaders jsonHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		return headers;
	}

}
