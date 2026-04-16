package example.lombok.demo.dtos;

import example.lombok.demo.models.Board;
import example.lombok.demo.models.Task;
import lombok.Builder;

import java.util.Set;
import java.util.stream.Stream;

@Builder
public record BoardView(Long id, String name, TaskView[] tasks, AssigneeView[] assignees) {

    public static BoardView from(Board board) {
        return BoardView.builder()
                .id(board.getId())
                .name(board.getName())
                .tasks(Stream.ofNullable(board.getTasks())
                        .flatMap(Set::stream)
                        .map(TaskView::from)
                        .toArray(TaskView[]::new))
                .assignees(Stream.ofNullable(board.getAssignees())
                        .flatMap(Set::stream)
                        .map(AssigneeView::from)
                        .toArray(AssigneeView[]::new))
                .build();
    }
}
