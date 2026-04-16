package example.lombok.demo.repositories;

import example.lombok.demo.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<Task, Long> {
}
