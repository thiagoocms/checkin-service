package com.nassau.checkinservice.bean;

import com.nassau.checkinservice.service.MessageService;
import lombok.EqualsAndHashCode;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EqualsAndHashCode(callSuper=false)
public class CustomModelMapper extends ModelMapper {

	 private MessageService messageService;

	 @Autowired
	 public CustomModelMapper(MessageService messageService) {
		 this.messageService = messageService;
		 this.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	 }

	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

}
