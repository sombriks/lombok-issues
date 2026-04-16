package example.lombok.demo.models;

import example.lombok.demo.dtos.AssigneeView;
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
public class Assignee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
    @OneToMany(mappedBy = "assignee")
    private Set<Task> tasks;

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
