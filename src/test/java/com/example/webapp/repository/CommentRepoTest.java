package com.example.webapp.repository;

import com.example.webapp.model.Comment;
import com.example.webapp.model.CommentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepoTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void shouldReturn1Comment(){
        // given
        Comment comment = new Comment();
        comment.setComment("Test");
        comment.setType(CommentType.PLUS);
        comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        testEntityManager.persist(comment);
        testEntityManager.flush();

        // when
        LocalDate now = LocalDate.now();
        List<Comment> comments = commentRepository.findByCreatedYearAndMonthAndDay(
                now.getYear(),
                now.getMonth().getValue(),
                now.getDayOfMonth()
        );

        // then
        assertThat(comments).hasSize(1);
        assertThat(comments.get(0)).hasFieldOrPropertyWithValue("comment", "Test");
    }

    @Test
    public void shouldSave1Comment(){
        // given
        Comment comment = new Comment();
        comment.setComment("Test");
        comment.setType(CommentType.PLUS);
        comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        // when
        Comment saved = commentRepository.save(comment);

        // then
        assertThat(testEntityManager.find(Comment.class, saved.getId())).isEqualTo(saved);
    }
}
