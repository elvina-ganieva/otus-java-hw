package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;


public class ProcessorThrowException implements Processor {

    private final DateTimeProvider dateTimeProvider;

    public ProcessorThrowException(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        if (dateTimeProvider.getTime().getSecond() % 2 == 0) {
            throw new EvenSecondException();
        }
        return message;
    }

}

//todo: 3. ������� ���������, ������� ����� ����������� ���������� � ������ ������� (�������� ���� � ��������������� �����������)
//         ������� ������ ������������ �� ����� ����������.
//         ���� - ������ ����� �������
// ����������� ���������� ������ � �������� �������!
//