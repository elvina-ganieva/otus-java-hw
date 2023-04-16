package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;
import java.util.*;

public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long, Message> history = new LinkedHashMap<>();

    @Override
    public void onUpdated(Message msg) {
        var msgCopy = msg.copy();
        history.put(msgCopy.getId(), msgCopy);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(history.get(id));
    }
}
