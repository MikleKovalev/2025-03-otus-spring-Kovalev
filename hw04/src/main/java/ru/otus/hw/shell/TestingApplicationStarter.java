package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.service.TestRunnerService;

@ShellComponent(value = "Students testing application")
@RequiredArgsConstructor
public class TestingApplicationStarter {

    private final TestRunnerService testRunnerService;

    @ShellMethod(value = "Start command", key = {"s", "start"})
    public void start() {
        testRunnerService.run();
    }
}
