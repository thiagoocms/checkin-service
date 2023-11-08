package com.nassau.checkinservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nassau.checkinservice.bean.CustomModelMapper;
import com.nassau.checkinservice.domain.ClassRoom;
import com.nassau.checkinservice.dto.classroom.ClassRoomSimpleDTO;
import com.nassau.checkinservice.service.MessageService;
import org.joda.time.DateTime;
import org.modelmapper.Conditions;
import org.modelmapper.PropertyMap;
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

        TypeMap<Long, DateTime> timestampToDateTimeTypeMap = modelMapper.createTypeMap(Long.class, DateTime.class);
        timestampToDateTimeTypeMap.setConverter(context -> {
            Long source = context.getSource();
            if (source == null) {
                return null;
            }
            return new DateTime(source);
        });

        TypeMap<DateTime, Long> dateTimeToTimestampTypeMap = modelMapper.createTypeMap(DateTime.class, Long.class);
        dateTimeToTimestampTypeMap.setConverter(context -> {
            DateTime source = context.getSource();
            if (source == null) {
                return null;
            }
            return source.getMillis();
        });

        return modelMapper;
    }
}
