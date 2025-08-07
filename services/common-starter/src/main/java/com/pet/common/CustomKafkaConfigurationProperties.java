package com.pet.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka.config")
public class CustomKafkaConfigurationProperties {

    private String deliveryTopic = "delivery";
    private String deliveryCancelTopic = "delivery-cancel";


    public String getDeliveryTopic() {
        return deliveryTopic;
    }

    public void setDeliveryTopic(String deliveryTopic) {
        this.deliveryTopic = deliveryTopic;
    }

    public String getDeliveryCancelTopic() {
        return deliveryCancelTopic;
    }

    public void setDeliveryCancelTopic(String deliveryCancelTopic) {
        this.deliveryCancelTopic = deliveryCancelTopic;
    }
}
