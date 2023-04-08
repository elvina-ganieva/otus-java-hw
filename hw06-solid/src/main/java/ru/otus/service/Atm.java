package ru.otus.service;

import ru.otus.model.Denomination;

import java.util.Map;

public interface Atm {

    void acceptBills(Map<Denomination, Integer> bills);

    Map<Denomination, Integer> giveBills(int amount);

    Map<Denomination, Integer> getOverallNumberOfBillsOfEachDenomination();
}
