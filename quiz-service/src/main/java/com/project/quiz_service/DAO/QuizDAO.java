package com.project.quiz_service.DAO;

import com.project.quiz_service.Model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizDAO extends JpaRepository<Quiz,Integer> {

}
