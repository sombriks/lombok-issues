package example.lombok.demo.models;

import example.lombok.demo.dtos.BoardView;
import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Entity
@With
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "board")
    private Set<Assignee> assignees;
    @OneToMany(mappedBy = "board")
    private Set<Task> tasks;

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

