package com.CodeLab.DB_Service.requestdto_converter;

import com.CodeLab.DB_Service.model.Topic;
import com.CodeLab.DB_Service.requestDTO.TopicRequestDTO;

import java.util.ArrayList;

public class TopicConverter {
    public static Topic topicConverter(TopicRequestDTO requestDTO){
        Topic topic = new Topic();
        topic.setTopicName(requestDTO.getTopicName());

        return topic;
    }
}
