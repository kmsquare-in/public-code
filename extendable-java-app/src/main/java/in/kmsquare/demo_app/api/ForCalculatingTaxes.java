package in.kmsquare.demo_app.api;

public interface ForCalculatingTaxes {
    enum TaxableType {
        Individual,
        Business
    }

    double taxOn(TaxableType type, double amount);
}
