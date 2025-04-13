package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.dao.QuestionDao;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TestServiceImplTest {
    @Mock
    private IOService ioService;

    @Mock
    private QuestionDao questionDao;

    private TestService testService;

    @BeforeEach
    public void setUp() {
        given(questionDao.findAll()).willReturn(List.of());
        testService = new TestServiceImpl(ioService, questionDao);
    }

    @Test
    void shouldExecute() {
        testService.executeTest();
        verify(ioService).printLine("");
        verify(ioService).printFormattedLine("Please answer the questions below%n");
    }
}
