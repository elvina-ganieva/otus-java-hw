package ru.otus.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.model.Denomination;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleAtmRepositoryTest {

    private AtmRepository atmRepository;

    Map<Denomination, Integer> bills;

    @BeforeEach
    void setUp() {
        atmRepository = new SimpleAtmRepository();
        bills = new HashMap<>();
    }

    @Test
    public void testAddBills() {
        // given
        bills.put(Denomination.FIVE_THOUSAND, 3);
        bills.put(Denomination.ONE_THOUSAND, 5);

        // when
        atmRepository.addBills(bills);

        // then
        assertEquals(3, atmRepository.getNumberOfBillsOfDenomination(Denomination.FIVE_THOUSAND));
        assertEquals(5, atmRepository.getNumberOfBillsOfDenomination(Denomination.ONE_THOUSAND));
    }

    @Test
    public void testUpdateNumberOfBillsOfDenomination() {
        // when
        atmRepository.updateNumberOfBillsOfDenomination(Denomination.FIVE_THOUSAND, 10);

        // then
        assertEquals(10, atmRepository.getNumberOfBillsOfDenomination(Denomination.FIVE_THOUSAND));
    }

    @Test
    public void testGetNumberOfBillsOfEachDenomination() {
        // given
        bills.put(Denomination.FIVE_THOUSAND, 3);
        bills.put(Denomination.ONE_THOUSAND, 5);
        atmRepository.addBills(bills);

        // when
        var result = atmRepository.getNumberOfBillsOfEachDenomination();

        // then
        assertEquals(3, result.get(Denomination.FIVE_THOUSAND));
        assertEquals(5, result.get(Denomination.ONE_THOUSAND));
    }

    @Test
    public void testGetNumberOfBillsOfDenomination() {
        // given
        bills.put(Denomination.FIVE_THOUSAND, 3);
        bills.put(Denomination.ONE_THOUSAND, 5);
        atmRepository.addBills(bills);

        // when
        var result = atmRepository.getNumberOfBillsOfDenomination(Denomination.ONE_THOUSAND);
        assertEquals(5, result);
    }
}
