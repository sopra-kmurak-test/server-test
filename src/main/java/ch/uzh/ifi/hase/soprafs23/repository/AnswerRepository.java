package com.example.accessingdatamysql;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.accessingdatamysql.User;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    @Query("SELECT COUNT(*) FROM Answer WHERE user.id =:userId AND question.id =:questionId")
    Integer findByUserAndQuestion(@Param("userId")Integer userId, @Param("questionId")Integer questionId);

//    @Query(value = "SELECT * FROM answer WHERE question_id = :questionId", nativeQuery = true)
//    List<Answer> findByQuestionId(@Param("questionId") Integer questionId);

    List<Answer> findByQuestionId(Integer questionId);

    List<Answer> findByUserId(Integer userId);

    Answer findAnswerById(Integer answerId);

    void deleteById(Integer id);

    @Query("SELECT a.question FROM Answer a WHERE a.id = :answerId")
    Question findQuestionByAnswerId(@Param("answerId") Integer answerId);
    
    @Query(value = "SELECT id, title, description, " +
                    "((LENGTH(title) - LENGTH(REPLACE(title, :keyword, ''))) / LENGTH(:keyword) * 2) + " +
                    "((LENGTH(description) - LENGTH(REPLACE(description, :keyword, ''))) / LENGTH(:keyword)) AS score " +
                    "FROM Question " +
                    "WHERE title LIKE %:keyword% OR description LIKE %:keyword% " +
                    "ORDER BY score DESC")
    List<Question> QuestionfindByKeyword(@Param("keyword") String keyword);
}
