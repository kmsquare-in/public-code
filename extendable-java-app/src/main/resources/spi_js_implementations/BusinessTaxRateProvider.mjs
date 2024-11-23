class PersonalTaxRateProvider {
    constructor() {
        this.TaxRateOutputClass = Java.type("in.kmsquare.demo_app.spi.TaxRateOutput");
    }

    taxRate(input) {
        let output = new this.TaxRateOutputClass();
        output.amount = 0.3;
        return output;
    }
}

let rateProvider = new PersonalTaxRateProvider();
rateProvider;