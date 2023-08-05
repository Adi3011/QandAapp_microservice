package com.aditya.questionService.service;


import com.aditya.questionService.dao.QuestionDao;
import com.aditya.questionService.model.Question;
import com.aditya.questionService.model.QuestionWrapper;
import com.aditya.questionService.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;
    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK) ;
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);


    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK) ;
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionDao.save(question);
        return new ResponseEntity<>("Success",HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteQuestion(Integer id) {
        questionDao.deleteById(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQ) {
        List<Integer> questionList = questionDao.findRandomQuestionsByCategory(categoryName,numQ);

        return new ResponseEntity<>(questionList, HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        for(Integer id : questionIds){
            questions.add(questionDao.findById(id).get());
        }

        for(Question question :questions){
            QuestionWrapper wrap = new QuestionWrapper();
            wrap.setNew_id(question.getNew_id());
            wrap.setQuestionTitle(question.getQuestionTitle());
            wrap.setOption1(question.getOption1());
            wrap.setOption2(question.getOption2());
            wrap.setOption3(question.getOption3());
            wrap.setOption4(question.getOption4());
            wrap.setCategory(question.getCategory());

            wrappers.add(wrap);

        }

        return new ResponseEntity<>(wrappers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {

        int result = 0;
        for(Response resp : responses){
            Question question = questionDao.findById(resp.getId()).get();
            if(resp.getResponse().equals(question.getRightAnswer())){
                result++;
            }

        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}