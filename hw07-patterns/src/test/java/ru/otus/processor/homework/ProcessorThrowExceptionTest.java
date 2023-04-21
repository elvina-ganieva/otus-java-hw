package ru.otus.processor.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.*;

class ProcessorThrowExceptionTest {

    @Test
    void handleProcessorTest1() {
        // given
        var dateTimeProvider = mock(DateTimeProvider.class);
        when(dateTimeProvider.getTime()).thenReturn(LocalDateTime.of(2023, 4, 16, 0, 0, 0));
        var processor = new ProcessorThrowException(dateTimeProvider);
        var message = new Message.Builder(1L).build();

        // when
        assertThatExceptionOfType(EvenSecondException.class).isThrownBy(() -> processor.process(message));

        // then
        verify(dateTimeProvider, times(1)).getTime();
    }

    @Test
    void handleProcessorTest2() {
        // given
        var dateTimeProvider = mock(DateTimeProvider.class);
        when(dateTimeProvider.getTime()).thenReturn(LocalDateTime.of(2023, 4, 16, 0, 0, 1));
        var processor = new ProcessorThrowException(dateTimeProvider);
        var message = new Message.Builder(1L).build();

        // when
        assertThatNoException().isThrownBy(() -> processor.process(message));

        // then
        verify(dateTimeProvider, times(1)).getTime();
    }
}