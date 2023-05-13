package com.example.webapp.service;

import com.example.webapp.model.Comment;
import com.example.webapp.model.CommentType;
import com.example.webapp.repository.CommentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;


@RunWith(SpringRunner.class)
public class CommentServiceTest {
    @MockBean
    private CommentRepository commentRepository;

    private CommentService commentService;

    @Before
    public void init() {
        commentService = new CommentService(commentRepository);
    }

    @Test
    public void shouldReturn1Comment() {
        // given
        Comment comment = new Comment();
        comment.setComment("Test");
        comment.setType(CommentType.PLUS);
        comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        List<Comment> comments = Arrays.asList(comment);
        LocalDate now = LocalDate.now();
        when(commentRepository.findByCreatedYearAndMonthAndDay(
                now.getYear(),
                now.getMonth().getValue(),
                now.getDayOfMonth()
        )).thenReturn(comments);

        // when
        List<Comment> actualComments = commentService.getAllCommentsForToday();

        // then

        verify(
                commentRepository,
                times(1)
        ).findByCreatedYearAndMonthAndDay(
                now.getYear(),
                now.getMonth().getValue(),
                now.getDayOfMonth()
        );
        assertThat(comments).isEqualTo(actualComments);
    }

    @Test
    public void shouldSave2Comments(){
        // given
        Comment comment1 = new Comment();
        comment1.setComment("Test plus");
        comment1.setType(CommentType.PLUS);
        comment1.setCreatedBy("jimmy");

        Comment comment2 = new Comment();
        comment2.setComment("Test plus");
        comment2.setType(CommentType.STAR);
        comment2.setCreatedBy("jimmy");
        comment2.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        List<Comment> comments = Arrays.asList(comment1, comment2);
        when(commentRepository.saveAll(comments)).thenReturn(comments);

        // when
        List<Comment> saved = commentService.saveAll(comments);

        // then
        assertThat(saved).isNotEmpty();
        verify(commentRepository, times(1)).saveAll(comments);
    }
}
