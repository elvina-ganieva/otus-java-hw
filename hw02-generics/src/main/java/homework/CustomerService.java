package homework;


import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

public class CustomerService {

    private final Map<Customer, String> customers = new LinkedHashMap<>();

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> smallest = customers.entrySet().stream()
                .min(Comparator.comparingLong(x -> x.getKey().getScores()))
                .orElse(null);

        if (smallest != null)
            return Map.entry(smallest.getKey().clone(), smallest.getValue());
        else
            return null;
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> next = customers.entrySet().stream()
                .filter(it -> it.getKey().getScores() > customer.getScores())
                .findFirst()
                .orElse(null);

        if (next != null)
            return Map.entry(next.getKey().clone(), next.getValue());
        else
            return null;
    }

    public void add(Customer customer, String data) {
        customers.put(customer, data);
    }
}
