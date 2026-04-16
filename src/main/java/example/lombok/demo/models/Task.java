package example.lombok.demo.models;

import example.lombok.demo.dtos.TaskView;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private Assignee assignee;
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    public Task() {
    }

    public Task(Long id, String description, Assignee assignee, Board board) {
        this.id = id;
        this.description = description;
        this.assignee = assignee;
        this.board = board;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Assignee getAssignee() {
        return assignee;
    }

    public void setAssignee(Assignee assignee) {
        this.assignee = assignee;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Task withId(Long id) {
        return new Task(id, this.description, this.assignee, this.board);
    }

    public Task withDescription(String description) {
        return new Task(this.id, description, this.assignee, this.board);
    }

    public Task withAssignee(Assignee assignee) {
        return new Task(this.id, this.description, assignee, this.board);
    }

    public Task withBoard(Board board) {
        return new Task(this.id, this.description, this.assignee, board);
    }

    @Override
    public String toString() {
        return "Task(id=" + id + ", description=" + description + ", assignee=" + assignee + ", board=" + board + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(description, task.description) && Objects.equals(assignee, task.assignee) && Objects.equals(board, task.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, assignee, board);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String description;
        private Assignee assignee;
        private Board board;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder assignee(Assignee assignee) {
            this.assignee = assignee;
            return this;
        }

        public Builder board(Board board) {
            this.board = board;
            return this;
        }

        public Task build() {
            return new Task(id, description, assignee, board);
        }
    }

    public static Task from(TaskView taskView) {
        return Task.builder()
                .id(taskView.id())
                .description(taskView.description())
                .build();
    }
}
