package ru.otus;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Atm {

    private final Map<Denomination, Integer> denominationsCount = new TreeMap<>((o1, o2) -> o2.getValue() - o1.getValue());

    public Atm() {
        denominationsCount.put(Denomination.FIVE_THOUSAND, 0);
        denominationsCount.put(Denomination.ONE_THOUSAND, 0);
        denominationsCount.put(Denomination.FIVE_HUNDRED, 0);
        denominationsCount.put(Denomination.ONE_HUNDRED, 0);
    }

    public void acceptBills(Map<Denomination, Integer> bills) {
        bills.forEach((key, value) -> denominationsCount.put(key, denominationsCount.get(key) + value));
    }

    public Map<Denomination, Integer> giveBills(int amount) {
        return new BillGiver(denominationsCount, amount).giveBills();
    }

    public Map<Denomination, Integer> getOverallNumberOfBanknotesOfEachDenomination() {
        return new HashMap<>(denominationsCount);
    }
}
