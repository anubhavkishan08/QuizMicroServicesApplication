package com.project.quiz_service.Controller;

import com.project.quiz_service.DTO.QuestionDTO;
import com.project.quiz_service.DTO.QuizDTO;
import com.project.quiz_service.DTO.Response;
import com.project.quiz_service.Service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    private QuizService quizService;

    public QuizController(QuizService quizService){
        this.quizService=quizService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDTO quizDTO){

        return quizService.createQuiz(quizDTO.getCategoryName(),
                quizDTO.getNumOfQuestions(),quizDTO.getTitle());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionDTO>> getQuizQuestions(@PathVariable int id){
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> checkQuizAnswers(@PathVariable int id, @RequestBody List<Response> response){
        return quizService.checkQuizAnswers(id,response);
    }
}
