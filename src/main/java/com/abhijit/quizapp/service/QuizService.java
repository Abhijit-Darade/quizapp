package com.abhijit.quizapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.abhijit.quizapp.dao.QuestionDao;
import com.abhijit.quizapp.dao.QuizDao;
import com.abhijit.quizapp.model.Question;
import com.abhijit.quizapp.model.QuestionWrapper;
import com.abhijit.quizapp.model.Quiz;
import com.abhijit.quizapp.model.Response;

import org.springframework.http.HttpStatus;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionsFromDB = quiz.get().getQuestions();

        List<QuestionWrapper> questionsForUser = new ArrayList<>();

        for(Question q : questionsFromDB){
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());

            questionsForUser.add(qw);
        }

        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        int score = 0, i=0;

        for(Response response : responses){
            if(response.getResponse().equals(questions.get(i).getRightAnswer())){
                score++;
            }
            i++;
        }
        return new ResponseEntity<>(score, HttpStatus.OK);
    }
    
}
