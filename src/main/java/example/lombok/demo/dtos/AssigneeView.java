package example.lombok.demo.dtos;

import example.lombok.demo.models.Assignee;
import lombok.Builder;

import java.util.Set;
import java.util.stream.Stream;

@Builder
public record AssigneeView(Long id, String name, TaskView[] tasks) {
    public static AssigneeView from(Assignee assignee) {
        return AssigneeView.builder()
                .id(assignee.getId())
                .name(assignee.getName())
                .tasks(Stream.ofNullable(assignee.getTasks())
                        .flatMap(Set::stream)
                        .map(TaskView::from)
                        .toArray(TaskView[]::new))
                .build();
    }
}
