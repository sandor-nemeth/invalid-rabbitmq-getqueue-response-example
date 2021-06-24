package com.rabbitmq.http.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rabbitmq.http.client.domain.ConsumerDetails;

import java.util.List;

@JsonIgnoreProperties({"consumer_details"})
abstract class QueueInfoMixin {
    @JsonIgnore
    abstract public List<ConsumerDetails> getConsumerDetails();

    @JsonIgnore
    abstract public void setConsumerDetails(List<ConsumerDetails> consumerDetails);
}
