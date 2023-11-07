package com.nassau.checkinservice.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nassau.checkinservice.dto.res.MessageDTO;
import org.springframework.web.client.HttpStatusCodeException;

public class JsonParser {

    private static final Gson gson = new GsonBuilder().create();

    public static <T> T fromJsonToEntity(String json, Class<T> entity) {
        return gson.fromJson(json, entity);
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static MessageDTO doErrorParser(HttpStatusCodeException e) {
        MessageDTO message = gson.fromJson(e.getResponseBodyAsString(),
                MessageDTO.class);
        return message;
    }

    public static Gson getGson() {
        return gson;
    }

}