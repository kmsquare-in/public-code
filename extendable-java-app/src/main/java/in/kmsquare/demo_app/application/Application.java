package in.kmsquare.demo_app.application;

import in.kmsquare.demo_app.api.ForCalculatingTaxes;
import in.kmsquare.demo_app.spi.ForGettingTaxRates;
import in.kmsquare.demo_app.spi.TaxRateInput;
import in.kmsquare.demo_app.spi.TaxRateOutput;

public class Application implements ForCalculatingTaxes {

    private ForGettingTaxRates personalTaxRateProvider;
    private ForGettingTaxRates businessTaxRateProvider;

    public Application(ForGettingTaxRates personalTaxRateProvider, ForGettingTaxRates businessTaxRateProvider) {
        this.personalTaxRateProvider = personalTaxRateProvider;
        this.businessTaxRateProvider = businessTaxRateProvider;
    }

    @Override
    public double taxOn(TaxableType type, double amount) {
        TaxRateInput input = new TaxRateInput();
        input.amount = amount;
        TaxRateOutput output = getTaxRateProvider(type).taxRate(input);
        return amount * output.amount;
    }

    private ForGettingTaxRates getTaxRateProvider(TaxableType type) {
        switch (type) {
            case Individual:
                return personalTaxRateProvider;
            case Business:
                return businessTaxRateProvider;
        }
        return null;
    }

}
