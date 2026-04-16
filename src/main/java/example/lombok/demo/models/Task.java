package example.lombok.demo.models;

import example.lombok.demo.dtos.TaskView;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@With
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
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

    public static Task from(TaskView taskView) {
        return Task.builder()
                .id(taskView.id())
                .description(taskView.description())
                .build();
    }
}
