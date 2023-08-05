package com.aditya.quizService.service;


import com.aditya.quizService.dao.QuizDao;
import com.aditya.quizService.feign.QuizInterface;
import com.aditya.quizService.model.QuestionWrapper;
import com.aditya.quizService.model.Quiz;
import com.aditya.quizService.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Integer> questions = quizInterface.getQuestionsForQuiz(category,numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }


    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Quiz quiz = quizDao.findById(id).get();
        List<Integer> questionIds = quiz.getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestions(questionIds);


       return questions;

    }


    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {


        ResponseEntity<Integer> result = quizInterface.getScore(responses);

        return result;
    }
}
