package com.project.question_service.DAO;

import com.project.question_service.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDAO extends JpaRepository<Question,Integer> {

    List<Question> findQuestionsByCategory(String questionCategory);

    @Query(value = "Select q.id from question q where q.category=:category order by RAND() limit :numQ",nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(String category, int numQ);

}
