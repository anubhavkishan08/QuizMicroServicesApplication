package com.project.question_service.Service;


import com.project.question_service.DAO.QuestionDAO;
import com.project.question_service.DTO.QuestionDTO;
import com.project.question_service.DTO.Response;
import com.project.question_service.Model.Question;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class QuestionService {

    private QuestionDAO questionDAO;

    public QuestionService(QuestionDAO questionDAO){
        this.questionDAO=questionDAO;
    }


    public ResponseEntity<List<Question>> getAllQuestions()
    {
        try {
            return new ResponseEntity<>(questionDAO.findAll(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String questionCategory) {
        try {
            return new ResponseEntity<>(questionDAO.findQuestionsByCategory(questionCategory), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> addQuestion(Question question) {
            questionDAO.save(question);
            return new ResponseEntity<>("Success",HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteQuestion(int id) {
        Optional<Question> question=questionDAO.findById(id);
        if(question.isPresent()){
            questionDAO.delete(question.get());
            return new ResponseEntity<>("Question is deleted",HttpStatus.OK);
        }else
            return new ResponseEntity<>("Question Not found",HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, int noOfQuestions) {
        List<Integer> questions=questionDAO.findRandomQuestionsByCategory(category,noOfQuestions);

        return new ResponseEntity<>(questions,HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionDTO>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        List<Question> questionsList=new ArrayList<>();
        for(int id:questionIds){
            questionsList.add(questionDAO.findById(id).get());
        }
        for(Question question:questionsList){
            QuestionDTO questionDTO=new QuestionDTO();
            questionDTO.setId(question.getId());
            questionDTO.setQuestionTitle(question.getQuestionTitle());
            questionDTO.setOption1(question.getOption1());
            questionDTO.setOption2(question.getOption2());
            questionDTO.setOption3(question.getOption3());
            questionDTO.setOption4(question.getOption4());
            questionDTOList.add(questionDTO);
        }

        return new ResponseEntity<>(questionDTOList,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {

        int right=0;
        for(Response response:responses){
            Question question=questionDAO.findById(response.getId()).get();
            if(response.getResponse().equals(question.getRightOption()))
                right++;
        }

        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
