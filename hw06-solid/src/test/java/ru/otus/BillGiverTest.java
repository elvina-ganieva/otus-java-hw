package ru.otus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class BillGiverTest {

    private BillGiver billGiver;

    private Map<Denomination, Integer> denominationsCount;

    @BeforeEach
    void setUp() {
        denominationsCount = new TreeMap<>((o1, o2) -> o2.getValue() - o1.getValue());
        denominationsCount.put(Denomination.FIVE_THOUSAND, 0);
        denominationsCount.put(Denomination.ONE_THOUSAND, 1);
        denominationsCount.put(Denomination.FIVE_HUNDRED, 3);
        denominationsCount.put(Denomination.ONE_HUNDRED, 0);
    }

    @Test
    void giveBillsShouldReturnOneThousandBanknote() {
        // given
        billGiver = new BillGiver(denominationsCount, 1000);

        // when
        Map<Denomination, Integer> result = billGiver.giveBills();

        // then
        assertThat(result.get(Denomination.ONE_THOUSAND)).isEqualTo(1);
    }

    @Test
    void giveBillsShouldReturnOneThousandBanknoteAndOneFiveHundredBanknote() {
        // given
        billGiver = new BillGiver(denominationsCount, 1500);

        // when
        Map<Denomination, Integer> result = billGiver.giveBills();

        // then
        assertThat(result.get(Denomination.FIVE_HUNDRED)).isEqualTo(1);
        assertThat(result.get(Denomination.ONE_THOUSAND)).isEqualTo(1);
    }

    @Test
    void giveBillsShouldThrowExceptionWhenThereIsNotEnoughBanknotes() {
        // given
        billGiver = new BillGiver(denominationsCount, 5000);

        // then
        assertThatThrownBy(() -> billGiver.giveBills()).isInstanceOf(BillGiverException.class);
    }
}
