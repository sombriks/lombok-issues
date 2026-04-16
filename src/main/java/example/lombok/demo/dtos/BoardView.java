package example.lombok.demo.dtos;

import example.lombok.demo.models.Board;
import example.lombok.demo.models.Task;

import java.util.Set;
import java.util.stream.Stream;

public record BoardView(Long id, String name, TaskView[] tasks, AssigneeView[] assignees) {
    public static BoardView from(Board board) {
        return new BoardView(
                board.getId(),
                board.getName(),
                Stream.ofNullable(board.getTasks())
                        .flatMap(Set::stream)
                        .map(TaskView::from)
                        .toArray(TaskView[]::new),
                Stream.ofNullable(board.getAssignees())
                        .flatMap(Set::stream)
                        .map(AssigneeView::from)
                        .toArray(AssigneeView[]::new)
        );
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private TaskView[] tasks;
        private AssigneeView[] assignees;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder tasks(TaskView[] tasks) {
            this.tasks = tasks;
            return this;
        }

        public Builder assignees(AssigneeView[] assignees) {
            this.assignees = assignees;
            return this;
        }

        public BoardView build() {
            return new BoardView(id, name, tasks, assignees);
        }
    }
}
