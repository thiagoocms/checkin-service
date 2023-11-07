package com.nassau.checkinservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nassau.checkinservice.bean.CustomModelMapper;
import com.nassau.checkinservice.service.MessageService;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

@Configuration
public class ModelMapperConfig {

    @Autowired
    private MessageService message;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public CustomModelMapper modelMapper() {
        CustomModelMapper modelMapper = new CustomModelMapper(message);

        TypeMap<LocalDate, Long> localDateToTimestampTypeMap = modelMapper.createTypeMap(LocalDate.class, Long.class);
        localDateToTimestampTypeMap.setConverter(context -> {
            LocalDate source = context.getSource();
            if (source == null) {
                return null;
            }
            return source.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
        });

        TypeMap<Long, LocalDate> timestampToLocalDateTypeMap = modelMapper.createTypeMap(Long.class, LocalDate.class);
        timestampToLocalDateTypeMap.setConverter(context -> {
            Long source = context.getSource();
            if (source == null) {
                return null;
            }
            return Instant.ofEpochMilli(source).atZone(ZoneOffset.UTC).toLocalDate();
        });

        return modelMapper;
    }
}
