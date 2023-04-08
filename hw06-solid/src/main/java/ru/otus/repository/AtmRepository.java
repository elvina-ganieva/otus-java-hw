package ru.otus.repository;

import ru.otus.model.Denomination;

import java.util.Map;

public interface AtmRepository {

    void addBills(Map<Denomination, Integer> bills);

    Map<Denomination, Integer> getNumberOfBillsOfEachDenomination();

    Integer getNumberOfBillsOfDenomination(Denomination denomination);

    void updateNumberOfBillsOfDenomination(Denomination denomination, Integer newNumber);
}

