package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.exception.BillAcceptorException;
import ru.otus.model.Denomination;
import ru.otus.repository.SimpleAtmRepository;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


class SimpleAtmTest {

    private Atm atm;

    private Map<Denomination, Integer> bills;

    @BeforeEach
    void setUp() {
        atm = new SimpleAtm(new SimpleAtmRepository());
        bills = new HashMap<>();
    }

    @Test
    void acceptBillsShouldPutBillsToAtm() {
        // given
        bills.put(Denomination.ONE_THOUSAND, 1);
        bills.put(Denomination.FIVE_HUNDRED, 3);

        // when
        atm.acceptBills(bills);

        // then
        Map<Denomination, Integer> result = atm.getOverallNumberOfBillsOfEachDenomination();

        assertThat(result.get(Denomination.ONE_THOUSAND)).isEqualTo(1);
        assertThat(result.get(Denomination.FIVE_HUNDRED)).isEqualTo(3);
    }

    @Test
    void acceptBillsShouldThrowExceptionWhenBillsNumberIsNegative() {
        // given
        bills.put(Denomination.FIVE_HUNDRED, 3);
        bills.put(Denomination.ONE_THOUSAND, -1);

        // then
        assertThatThrownBy(() -> atm.acceptBills(bills)).isInstanceOf(BillAcceptorException.class);
    }

    @Test
    void giveBillsShouldTakeBillsFromAtm() {
        // given
        bills.put(Denomination.ONE_THOUSAND, 1);
        bills.put(Denomination.FIVE_HUNDRED, 3);
        atm.acceptBills(bills);

        // when
        Map<Denomination, Integer> result = atm.giveBills(1000);

        // then
        Map<Denomination, Integer> atmBanknotes = atm.getOverallNumberOfBillsOfEachDenomination();
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
        Map<Denomination, Integer> result = atm.getOverallNumberOfBillsOfEachDenomination();

        // then
        assertThat(result.get(Denomination.ONE_THOUSAND)).isEqualTo(1);
        assertThat(result.get(Denomination.FIVE_HUNDRED)).isEqualTo(3);
    }
}