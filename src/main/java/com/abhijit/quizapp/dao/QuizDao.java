package com.abhijit.quizapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhijit.quizapp.model.Quiz;

public interface QuizDao extends JpaRepository<Quiz, Object>{
    
}
