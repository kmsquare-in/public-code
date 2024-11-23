package in.kmsquare.demo_app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import in.kmsquare.demo_app.api.ForCalculatingTaxes.TaxableType;
import in.kmsquare.demo_app.application.Application;
import in.kmsquare.demo_app.spi_implementation.GraalJsPersonalTaxRateAdapter;

public class TestApplication {

    @Test
    public void test_personal_tax() throws Exception {
        Application app = configureApplication();
        assertEquals(50, app.taxOn(TaxableType.Individual, 100));
    }

    @Test
    public void test_business_tax() throws Exception {
        Application app = configureApplication();
        assertEquals(30, app.taxOn(TaxableType.Business, 100));
    }

    private Application configureApplication() throws Exception {
        GraalJsPersonalTaxRateAdapter personalTaxRateProvider = new GraalJsPersonalTaxRateAdapter("/spi_js_implementations/PersonalTaxRateProvider.mjs");
        GraalJsPersonalTaxRateAdapter bizTaxRateProvider = new GraalJsPersonalTaxRateAdapter("/spi_js_implementations/BusinessTaxRateProvider.mjs");
        Application app = new Application(personalTaxRateProvider, bizTaxRateProvider);
        return app;
    }

}
