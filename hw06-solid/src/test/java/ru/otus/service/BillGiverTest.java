package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.exception.BillGiverException;
import ru.otus.model.Denomination;
import ru.otus.repository.AtmRepository;
import ru.otus.repository.SimpleAtmRepository;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class BillGiverTest {

    private BillGiver billGiver;

    private AtmRepository atmRepository;

    @BeforeEach
    void setUp() {
        var map = new HashMap<Denomination, Integer>();
        map.put(Denomination.FIVE_THOUSAND, 0);
        map.put(Denomination.ONE_THOUSAND, 1);
        map.put(Denomination.FIVE_HUNDRED, 3);
        map.put(Denomination.ONE_HUNDRED, 0);

        atmRepository = new SimpleAtmRepository();
        atmRepository.addBills(map);
    }

    @Test
    void giveBillsShouldReturnOneOneThousandBill() {
        // given
        billGiver = new BillGiver(atmRepository, 1000);

        // when
        Map<Denomination, Integer> result = billGiver.giveBills();

        // then
        assertThat(result.get(Denomination.ONE_THOUSAND)).isEqualTo(1);
    }

    @Test
    void giveBillsShouldReturnOneOneThousandAndOneFiveHundredBill() {
        // given
        billGiver = new BillGiver(atmRepository, 1500);

        // when
        Map<Denomination, Integer> result = billGiver.giveBills();

        // then
        assertThat(result.get(Denomination.FIVE_HUNDRED)).isEqualTo(1);
        assertThat(result.get(Denomination.ONE_THOUSAND)).isEqualTo(1);
    }

    @Test
    void giveBillsShouldThrowExceptionWhenThereIsNotEnoughBillsInAtm() {
        // given
        billGiver = new BillGiver(atmRepository, 5000);

        // then
        assertThatThrownBy(() -> billGiver.giveBills()).isInstanceOf(BillGiverException.class);
    }
}
