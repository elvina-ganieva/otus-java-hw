package ru.otus.repository;

import ru.otus.model.Denomination;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class SimpleAtmRepository implements AtmRepository {

    private final Map<Denomination, Integer> denominationsCount = new TreeMap<>((o1, o2) -> o2.getValue() - o1.getValue());

    public SimpleAtmRepository() {
        for (var denomination : Denomination.values()) {
            denominationsCount.put(denomination, 0);
        }
    }

    @Override
    public void addBills(Map<Denomination, Integer> bills) {
        bills.forEach((key, value) -> denominationsCount.put(key, denominationsCount.get(key) + value));
    }

    @Override
    public Map<Denomination, Integer> getNumberOfBillsOfEachDenomination() {
        return new HashMap<>(denominationsCount);
    }

    @Override
    public Integer getNumberOfBillsOfDenomination(Denomination denomination) {
        return denominationsCount.get(denomination);
    }

    @Override
    public void updateNumberOfBillsOfDenomination(Denomination denomination, Integer newNumber) {
        denominationsCount.put(denomination, newNumber);
    }
}
