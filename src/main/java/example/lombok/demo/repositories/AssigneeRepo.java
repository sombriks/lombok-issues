package example.lombok.demo.repositories;

import example.lombok.demo.models.Assignee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssigneeRepo extends JpaRepository<Assignee, Long> {
}
