package ru.otus.service;

import ru.otus.model.Denomination;
import ru.otus.exception.BillAcceptorException;
import ru.otus.repository.AtmRepository;

import java.util.Map;

public class SimpleAtm implements Atm {

    private final AtmRepository atmRepository;

    public SimpleAtm(AtmRepository atmRepository) {
        this.atmRepository = atmRepository;
    }

    @Override
    public void acceptBills(Map<Denomination, Integer> bills) {
        bills.values().forEach(value -> {
            if (value < 0)
                throw new BillAcceptorException("Не принимается отрицательное количество банкнот.");
        });
        atmRepository.addBills(bills);
    }

    @Override
    public Map<Denomination, Integer> giveBills(int amount) {
        return new BillGiver(atmRepository, amount).giveBills();
    }

    @Override
    public Map<Denomination, Integer> getOverallNumberOfBillsOfEachDenomination() {
        return atmRepository.getNumberOfBillsOfEachDenomination();
    }
}
