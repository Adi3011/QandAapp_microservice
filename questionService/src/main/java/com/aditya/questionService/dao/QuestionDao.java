package com.aditya.questionService.dao;

import com.aditya.questionService.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {
    List<Question> findByCategory(String category); //declare your own function to query db

    @Query(value = "SELECT q.new_id FROM question q where q.category=:category ORDER BY RANDOM() LIMIT :numQ",nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(String category, int numQ);

}

