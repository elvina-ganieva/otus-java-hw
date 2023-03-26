package ru.otus;

import java.util.HashMap;
import java.util.Map;

public class BillGiver {

    private final Map<Denomination, Integer> billsToGive = new HashMap<>();

    private final Map<Denomination, Integer> overallAmount;

    private int remainder;

    public BillGiver(Map<Denomination, Integer> overallAmount, int remainder) {
        this.overallAmount = overallAmount;
        this.remainder = remainder;
    }

    public Map<Denomination, Integer> giveBills() {
        overallAmount.keySet().forEach(this::addBanknotesOfDenomination);

        if (remainder != 0) {
            throw new BillGiverException("Не удалось собрать требуемую сумму.");
        }
        return billsToGive;
    }

    private void addBanknotesOfDenomination(Denomination denomination) {
        var banknoteCount = remainder / denomination.getValue();
        if (banknoteCount != 0 && overallAmount.get(denomination) - banknoteCount >= 0) {
            overallAmount.put(denomination, overallAmount.get(denomination) - banknoteCount);
            billsToGive.put(denomination, banknoteCount);
        } else {
            banknoteCount = 0;
        }
        remainder = remainder - banknoteCount * denomination.getValue();
    }
}
