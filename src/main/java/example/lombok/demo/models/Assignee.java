package example.lombok.demo.models;

import example.lombok.demo.dtos.AssigneeView;
import jakarta.persistence.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
public class Assignee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
    @OneToMany(mappedBy = "assignee", fetch = FetchType.EAGER)
    private Set<Task> tasks;

    public Assignee() {
    }

    public Assignee(Long id, String name, Board board, Set<Task> tasks) {
        this.id = id;
        this.name = name;
        this.board = board;
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

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Assignee withId(Long id) {
        return new Assignee(id, this.name, this.board, this.tasks);
    }

    public Assignee withName(String name) {
        return new Assignee(this.id, name, this.board, this.tasks);
    }

    public Assignee withBoard(Board board) {
        return new Assignee(this.id, this.name, board, this.tasks);
    }

    public Assignee withTasks(Set<Task> tasks) {
        return new Assignee(this.id, this.name, this.board, tasks);
    }

    @Override
    public String toString() {
        return "Assignee(id=" + id + ", name=" + name + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignee assignee = (Assignee) o;
        return Objects.equals(id, assignee.id) && Objects.equals(name, assignee.name);
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
        private Board board;
        private Set<Task> tasks;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder board(Board board) {
            this.board = board;
            return this;
        }

        public Builder tasks(Set<Task> tasks) {
            this.tasks = tasks;
            return this;
        }

        public Assignee build() {
            return new Assignee(id, name, board, tasks);
        }
    }

    public static Assignee from(AssigneeView assigneeView) {
        return Assignee.builder()
                .id(assigneeView.id())
                .name(assigneeView.name())
                .tasks(Stream.ofNullable(assigneeView.tasks())
                        .flatMap(Arrays::stream)
                        .map(Task::from)
                        .collect(Collectors.toSet()))
                .build();
    }
}
