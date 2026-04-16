package example.lombok.demo.dtos;

import example.lombok.demo.models.Assignee;

import java.util.Set;
import java.util.stream.Stream;

public record AssigneeView(Long id, String name, TaskView[] tasks) {
    public static AssigneeView from(Assignee assignee) {
        return new AssigneeView(
                assignee.getId(),
                assignee.getName(),
                Stream.ofNullable(assignee.getTasks())
                        .flatMap(Set::stream)
                        .map(TaskView::from)
                        .toArray(TaskView[]::new)
        );
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private TaskView[] tasks;

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

        public AssigneeView build() {
            return new AssigneeView(id, name, tasks);
        }
    }
}
