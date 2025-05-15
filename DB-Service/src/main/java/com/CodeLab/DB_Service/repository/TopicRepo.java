package com.CodeLab.DB_Service.repository;

import com.CodeLab.DB_Service.model.Topic;
import com.CodeLab.DB_Service.requestdto_converter.TopicConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TopicRepo extends JpaRepository<Topic, UUID> {

    public Optional<Topic> findByTopicName(String topic);
}
