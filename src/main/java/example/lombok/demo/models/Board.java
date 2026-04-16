package example.lombok.demo.models;

import example.lombok.demo.dtos.BoardView;
import jakarta.persistence.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "board")
    private Set<Assignee> assignees;
    @OneToMany(mappedBy = "board")
    private Set<Task> tasks;

    public Board() {
    }

    public Board(Long id, String name, Set<Assignee> assignees, Set<Task> tasks) {
        this.id = id;
        this.name = name;
        this.assignees = assignees;
        this.tasks = tasks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Assignee> getAssignees() {
        return assignees;
    }

    public void setAssignees(Set<Assignee> assignees) {
        this.assignees = assignees;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Board withId(Long id) {
        return new Board(id, this.name, this.assignees, this.tasks);
    }

    public Board withName(String name) {
        return new Board(this.id, name, this.assignees, this.tasks);
    }

    public Board withAssignees(Set<Assignee> assignees) {
        return new Board(this.id, this.name, assignees, this.tasks);
    }

    public Board withTasks(Set<Task> tasks) {
        return new Board(this.id, this.name, this.assignees, tasks);
    }

    @Override
    public String toString() {
        return "Board(id=" + id + ", name=" + name + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return Objects.equals(id, board.id) && Objects.equals(name, board.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private Set<Assignee> assignees;
        private Set<Task> tasks;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder assignees(Set<Assignee> assignees) {
            this.assignees = assignees;
            return this;
        }

        public Builder tasks(Set<Task> tasks) {
            this.tasks = tasks;
            return this;
        }

        public Board build() {
            return new Board(id, name, assignees, tasks);
        }
    }

    public static Board from(BoardView bSave) {
        Board b = new Board();
        return b.withId(bSave.id())
                .withName(bSave.name())
                .withAssignees(Stream.ofNullable(bSave.assignees())
                        .flatMap(Arrays::stream)
                        .map(Assignee::from)
                        .map(a -> a.withBoard(b))
                        .collect(Collectors.toSet()))
                .withTasks(Stream.ofNullable(bSave.tasks())
                        .flatMap(Arrays::stream)
                        .map(Task::from)
                        .map(t -> t.withBoard(b))
                        .collect(Collectors.toSet()));
    }
}

