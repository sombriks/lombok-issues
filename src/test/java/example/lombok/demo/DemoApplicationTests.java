package example.lombok.demo;

import example.lombok.demo.dtos.BoardView;
import example.lombok.demo.models.Assignee;
import example.lombok.demo.models.Board;
import example.lombok.demo.models.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

@AutoConfigureTestRestTemplate
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void requestWorks() {
        Board b = Board.builder().name("works").build();
        restTemplate.postForObject("/boards", b, Board.class);
        var works = restTemplate.getForObject("/boards", Object.class);
        assertThat(works, notNullValue());
    }

    @Test
    void boardSaves() {
        Board b = Board.builder().name("test").build();
        b = restTemplate.postForObject("/boards", b, Board.class);
        assertThat(b, notNullValue());
        assertThat(b.getId(), notNullValue());
        var boards = restTemplate.getForObject("/boards", BoardView[].class);
        assertThat(boards, notNullValue());
        assertThat(boards.length, greaterThan(0));
    }

    @Test
    void boardWithAsigneeesSaves() {
        var b = Board.builder()
                .name("test 2")
                .assignees(Set.of(Assignee.builder()
                        .name("test 2").build()))
                .build();
        restTemplate.postForObject("/boards", b, Object.class);
        var boards = restTemplate
                .getForObject("/boards", BoardView[].class);
        assertThat(boards, notNullValue());
        assertThat(boards.length, greaterThan(0));
        Arrays.stream(boards)
                .forEach(brd -> {
                    assertThat(brd.assignees(), notNullValue());
                    assertThat(brd.assignees().length, greaterThan(0));
                });
    }

    @Test
    void boardWithAsigneeesAndTasksSaves() {
        var b = Board.builder()
                .name("test 3")
                .assignees(Set.of(Assignee.builder()
                        .name("test 3").build()))
                .tasks(Set.of(Task.builder()
                        .description("test 3").build()))
                .build();
        var bSave = restTemplate.postForObject("/boards", b, BoardView.class);
        assertThat(bSave, notNullValue());
        assertThat(bSave.id(), notNullValue());
        assertThat(bSave.tasks(), notNullValue());
        assertThat(bSave.tasks().length, greaterThan(0));
        assertThat(bSave.assignees(), notNullValue());
        assertThat(bSave.assignees().length, greaterThan(0));
        var boards = restTemplate
                .getForEntity("/boards", BoardView[].class)
                .getBody();
        assertThat(boards, notNullValue());
        assertThat(boards.length, greaterThan(0));
    }

    @Test
    void boardWithasksUpdatesAssignees() {
        var b = Board.builder()
                .name("test 4")
                .assignees(Set.of(Assignee.builder()
                        .name("test 4").build()))
                .tasks(Set.of(Task.builder()
                        .description("test 4").build()))
                .build();
        var bSave = restTemplate.postForObject("/boards", b, BoardView.class);
        assertThat(bSave, notNullValue());
        var update = Board.from(bSave);
        update.getAssignees().stream().findFirst().ifPresent(a ->
                a.getTasks().add(update.getTasks().stream().findFirst().orElse(null)));
        restTemplate.put("/boards/{id}", update, update.getId());
        var bUpdate = restTemplate.getForObject("/boards/{id}", BoardView.class, update.getId());
        assertThat(bUpdate, notNullValue());
        assertThat(bUpdate.assignees(), notNullValue());
        assertThat(bUpdate.assignees().length, greaterThan(0));
        assertThat(bUpdate.assignees()[0].tasks(), notNullValue());
        assertThat(bUpdate.assignees()[0].tasks().length, greaterThan(0));
    }

}
