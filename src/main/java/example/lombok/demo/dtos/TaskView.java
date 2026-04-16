package example.lombok.demo.dtos;

import example.lombok.demo.models.Task;

public record TaskView(Long id, String description) {
    public static TaskView from(Task task) {
        return new TaskView(task.getId(), task.getDescription());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String description;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public TaskView build() {
            return new TaskView(id, description);
        }
    }
}
