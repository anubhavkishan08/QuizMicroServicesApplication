package com.project.quiz_service.feign;

import com.project.quiz_service.DTO.QuestionDTO;
import com.project.quiz_service.DTO.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {

    @GetMapping("question/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category,
                                                             @RequestParam int noOfQuestion);

    @PostMapping("question/getQuestions")
    public ResponseEntity<List<QuestionDTO>> getQuestionsFromId(@RequestBody List<Integer> questionIds);

    @PostMapping("question/calculateScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);
}
