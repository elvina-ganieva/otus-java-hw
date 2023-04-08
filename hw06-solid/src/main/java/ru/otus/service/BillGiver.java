package ru.otus.service;

import ru.otus.model.Denomination;
import ru.otus.exception.BillGiverException;
import ru.otus.repository.AtmRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BillGiver {

    private final Map<Denomination, Integer> billsToGive = new HashMap<>();

    private final AtmRepository atmRepository;

    private int remainder;

    public BillGiver(AtmRepository atmRepository, int remainder) {
        this.atmRepository = atmRepository;
        this.remainder = remainder;
    }

    public Map<Denomination, Integer> giveBills() {
        atmRepository.getNumberOfBillsOfEachDenomination().keySet().forEach(this::addBillsOfDenomination);

        if (remainder != 0) {
            throw new BillGiverException("Не удалось собрать требуемую сумму.");
        }
        return billsToGive;
    }

    private void addBillsOfDenomination(Denomination denomination) {
        var billsCount = remainder / denomination.getValue();
        var prevNumOfBills = atmRepository.getNumberOfBillsOfDenomination(denomination);
        var newNumOfBills = prevNumOfBills - billsCount;

        if (billsCount != 0 && newNumOfBills >= 0) {
            atmRepository.updateNumberOfBillsOfDenomination(denomination, newNumOfBills);
            billsToGive.put(denomination, billsCount);
        } else {
            billsCount = 0;
        }
        remainder = remainder - billsCount * denomination.getValue();
    }
}
