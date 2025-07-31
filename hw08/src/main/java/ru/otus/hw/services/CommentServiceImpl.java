package ru.otus.hw.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookService bookService;

    @Transactional(readOnly = true)
    @Override
    public Optional<Comment> findById(String id) {
        return commentRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findByBookId(String bookId) {
        var book = bookService.findById(bookId).orElseThrow(() ->
                new EntityNotFoundException("Book with id " + bookId + " not found"));
        return commentRepository.findByBookId(book.getId());
    }

    @Transactional
    @Override
    public Comment create(String text, String bookId) {
        var book = bookService.findById(bookId).orElseThrow(() ->
                new EntityNotFoundException("Book with id " + bookId + " not found"));
        return commentRepository.save(new Comment(null, text, book));
    }

    @Transactional
    @Override
    public Comment update(String id, String text) {
        var comment = commentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Comment with id " + id + " not found"));
        comment.setText(text);
        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }
}
