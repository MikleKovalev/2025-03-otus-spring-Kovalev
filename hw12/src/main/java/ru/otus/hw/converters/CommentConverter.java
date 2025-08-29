package ru.otus.hw.converters;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.Comment;

@Component
@NoArgsConstructor
public class CommentConverter {
    public String commentToString(Comment comment) {
        return "Id: %d, Name: %s".formatted(comment.getId(), comment.getText());
    }
}
