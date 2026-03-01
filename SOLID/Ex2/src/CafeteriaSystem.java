import java.util.*;

public class CafeteriaSystem {
    private final Map<String, MenuItem> menu = new LinkedHashMap<>();
    private final InvoiceRepository repository;
    private final TaxPolicy taxPolicy;
    private final DiscountPolicy discountPolicy;
    private final InvoiceFormatter formatter;
    private int invoiceSeq = 1000;

    public CafeteriaSystem(InvoiceRepository repository, TaxPolicy taxPolicy, 
                          DiscountPolicy discountPolicy, InvoiceFormatter formatter) {
        this.repository = repository;
        this.taxPolicy = taxPolicy;
        this.discountPolicy = discountPolicy;
        this.formatter = formatter;
    }

    public void addToMenu(MenuItem i) { menu.put(i.id, i); }

    public void checkout(String customerType, List<OrderLine> lines) {
        String invId = "INV-" + (++invoiceSeq);

        double subtotal = 0.0;
        List<String> lineTexts = new ArrayList<>();

        for (OrderLine l : lines) {
            MenuItem item = menu.get(l.itemId);
            double lineTotal = item.price * l.qty;
            subtotal += lineTotal;

            lineTexts.add(
                String.format("- %s x%d = %.2f\n",
                item.name, l.qty, lineTotal));
        }

        double taxPct = taxPolicy.taxPercent(customerType);
        double tax = subtotal * (taxPct / 100.0);

        double discount = discountPolicy.discount(customerType, subtotal, lines.size());

        double total = subtotal + tax - discount;

        InvoiceData data = new InvoiceData(invId, lineTexts, subtotal, taxPct, tax, discount, total);

        String printable = formatter.format(data);
        System.out.print(printable);

        repository.save(invId, printable);
        System.out.println("Saved invoice: " + invId + " (lines=" + repository.countLines(invId) + ")");
    }
}
