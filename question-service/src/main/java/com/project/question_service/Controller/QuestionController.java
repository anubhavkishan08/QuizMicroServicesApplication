package com.project.question_service.Controller;


import com.project.question_service.DTO.QuestionDTO;
import com.project.question_service.DTO.Response;
import com.project.question_service.Model.Question;
import com.project.question_service.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    private QuestionService questionService;

    @Autowired
    Environment environment;

    public QuestionController(QuestionService questionService){
        this.questionService=questionService;
    }

    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping("/category/{questionCategory}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String questionCategory){
        return  questionService.getQuestionsByCategory(questionCategory);
    }

    @PostMapping("/addQuestion")
    public String addQuestion(@RequestBody Question question){
        questionService.addQuestion(question);
        return "Question added!....";

    }

    @DeleteMapping("/deletQuestion/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable int id){
        return questionService.deleteQuestion(id);
    }

    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category,
                                                             @RequestParam int noOfQuestion){
        return questionService.getQuestionsForQuiz(category,noOfQuestion);
    }

    @PostMapping("/getQuestions")
    public ResponseEntity<List<QuestionDTO>> getQuestionsFromId(@RequestBody List<Integer> questionIds){
        return questionService.getQuestionsFromId(questionIds);
    }

    @PostMapping("/calculateScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        System.out.println(environment.getProperty("local.server.port"));
        return questionService.getScore(responses);
    }

}
