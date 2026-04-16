package example.lombok.demo.dtos;

import example.lombok.demo.models.Task;
import lombok.Builder;

@Builder
public record TaskView(Long id, String description) {
    public static TaskView from(Task task) {
        return TaskView.builder()
                .id(task.getId())
                .description(task.getDescription())
                .build();
    }
}
