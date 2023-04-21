package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorSwapFields implements Processor {
    @Override
    public Message process(Message message) {
        var field1 = message.getField1();
        var field2 = message.getField2();
        return message.toBuilder()
                .field11(field2)
                .field12(field1)
                .build();
    }
}
//

//todo: 2. Сделать процессор, который поменяет местами значения field11 ии field12

