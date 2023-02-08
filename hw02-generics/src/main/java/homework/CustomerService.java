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
        return getCopyOrNull(smallest);
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> next = customers.entrySet().stream()
                .filter(it -> it.getKey().getScores() > customer.getScores())
                .findFirst()
                .orElse(null);
        return getCopyOrNull(next);
    }

    public void add(Customer customer, String data) {
        customers.put(customer, data);
    }

    private Map.Entry<Customer, String> getCopyOrNull(Map.Entry<Customer, String> entry) {
        if (entry != null)
            return Map.entry(entry.getKey().clone(), entry.getValue());
        else
            return null;
    }
}
