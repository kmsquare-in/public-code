package in.kmsquare.demo_app.spi;

public interface ForGettingTaxRates {
    TaxRateOutput taxRate(TaxRateInput amount);
}
