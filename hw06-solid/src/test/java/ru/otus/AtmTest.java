package ru.otus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class AtmTest {

    private Atm atm;

    private Map<Denomination, Integer> bills;


    @BeforeEach
    void setUp() {
        atm = new Atm();
        bills = new HashMap<>();
    }

    @Test
    void acceptBillsShouldPutBanknotesToAtm() {
        // given
        bills.put(Denomination.ONE_THOUSAND, 1);
        bills.put(Denomination.FIVE_HUNDRED, 3);

        // when
        atm.acceptBills(bills);

        // then
        Map<Denomination, Integer> result = atm.getOverallNumberOfBanknotesOfEachDenomination();

        assertThat(result.get(Denomination.ONE_THOUSAND)).isEqualTo(1);
        assertThat(result.get(Denomination.FIVE_HUNDRED)).isEqualTo(3);
    }

    @Test
    void giveBillsShouldTakeBanknotesFromAtm() {
        // given
        bills.put(Denomination.ONE_THOUSAND, 1);
        bills.put(Denomination.FIVE_HUNDRED, 3);
        atm.acceptBills(bills);

        // when
        Map<Denomination, Integer> result = atm.giveBills(1000);

        // then
        Map<Denomination, Integer> atmBanknotes = atm.getOverallNumberOfBanknotesOfEachDenomination();
        assertThat(result.get(Denomination.ONE_THOUSAND)).isEqualTo(1);
        assertThat(atmBanknotes.get(Denomination.ONE_THOUSAND)).isEqualTo(0);
        assertThat(atmBanknotes.get(Denomination.FIVE_HUNDRED)).isEqualTo(3);
    }

    @Test
    void getOverallNumberOfBanknotesOfEachDenominationShouldReturnAllBanknotesFromAtm() {
        // given
        bills.put(Denomination.ONE_THOUSAND, 1);
        bills.put(Denomination.FIVE_HUNDRED, 3);
        atm.acceptBills(bills);

        // when
        Map<Denomination, Integer> result = atm.getOverallNumberOfBanknotesOfEachDenomination();

        // then
        assertThat(result.get(Denomination.ONE_THOUSAND)).isEqualTo(1);
        assertThat(result.get(Denomination.FIVE_HUNDRED)).isEqualTo(3);
    }
}