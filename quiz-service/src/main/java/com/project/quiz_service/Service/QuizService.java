package com.project.quiz_service.Service;

import com.project.quiz_service.DAO.QuizDAO;
import com.project.quiz_service.DTO.QuestionDTO;
import com.project.quiz_service.DTO.Response;
import com.project.quiz_service.Model.Quiz;
import com.project.quiz_service.feign.QuizInterface;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private QuizDAO quizDAO;

    private QuizInterface quizInterface;


    public QuizService(QuizDAO quizDAO,QuizInterface quizInterface){

        this.quizDAO=quizDAO;
        this.quizInterface=quizInterface;
    }

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Integer> questions=quizInterface.getQuestionsForQuiz(category,numQ).getBody();

        System.out.println(numQ);

        Quiz quiz=new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);

        quizDAO.save(quiz);

        return new ResponseEntity<>("Quiz is created", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionDTO>> getQuizQuestions(int id) {
        Optional<Quiz> quiz=quizDAO.findById(id);

        List<Integer> questionIds=quiz.get().getQuestionIds();
        ResponseEntity<List<QuestionDTO>> questionDTOList= quizInterface.getQuestionsFromId(questionIds);

        return questionDTOList;
    }

    public ResponseEntity<Integer> checkQuizAnswers(int id, List<Response> response) {
        ResponseEntity<Integer> score=quizInterface.getScore(response);

        return score;
    }
}
