package com.rabbitmq.http.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.http.client.domain.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class CustomRabbitClient extends Client {
    public CustomRabbitClient(String url, String username, String password) throws MalformedURLException, URISyntaxException {
        super(url, username, password);
        this.rt.setMessageConverters(messageConverters());
    }

    private List<HttpMessageConverter<?>> messageConverters() {
        List<HttpMessageConverter<?>> xs = new ArrayList<HttpMessageConverter<?>>();
        xs.add(new MappingJackson2HttpMessageConverter(customObjectMapper()));
        return xs;
    }

    private ObjectMapper customObjectMapper() {
        ObjectMapper om = Jackson2ObjectMapperBuilder
                .json()
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .featuresToEnable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)
                .deserializerByType(VhostLimits.class, JsonUtils.VHOST_LIMITS_DESERIALIZER_INSTANCE)
                .deserializerByType(UserInfo.class, JsonUtils.USER_INFO_DESERIALIZER_INSTANCE)
                .deserializerByType(CurrentUserDetails.class, JsonUtils.CURRENT_USER_DETAILS_DESERIALIZER_INSTANCE)
                .mixIn(QueueInfo.class, QueueInfoMixin.class)
                .build();
        return om;
    }

}
