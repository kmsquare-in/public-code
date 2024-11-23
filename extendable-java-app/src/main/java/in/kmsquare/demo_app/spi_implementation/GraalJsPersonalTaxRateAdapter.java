package in.kmsquare.demo_app.spi_implementation;

import java.io.InputStream;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import in.kmsquare.demo_app.spi.ForGettingTaxRates;
import in.kmsquare.demo_app.spi.TaxRateInput;
import in.kmsquare.demo_app.spi.TaxRateOutput;

public class GraalJsPersonalTaxRateAdapter implements ForGettingTaxRates {

    private Engine engine;
    private Source source;

    public GraalJsPersonalTaxRateAdapter(String jsFileName) throws Exception {
        String jsSourceString = null;
        try (InputStream is = GraalJsPersonalTaxRateAdapter.class.getResourceAsStream(jsFileName)) {
            jsSourceString = new String(is.readAllBytes());
        }
        this.engine = Engine.newBuilder("js")
                .option("engine.Compilation", "true")
                .allowExperimentalOptions(true)
                .build();
        this.source = Source.newBuilder("js", jsSourceString, jsFileName).build();
    }

    @Override
    public TaxRateOutput taxRate(TaxRateInput amount) {
        try (Context context = Context
                .newBuilder()
                .engine(engine)
                .allowExperimentalOptions(true)
                .allowAllAccess(true)
                .allowHostAccess(HostAccess.ALL)
                .allowHostClassLookup(className -> {
                    return TaxRateOutput.class.getName().equals(className);
                })
                .build()) {
            Value scriptContext = context.eval(this.source);
            Value taxRateFunction = scriptContext.getMember("taxRate");
            if(taxRateFunction != null && taxRateFunction.canExecute()) {
                return taxRateFunction.execute(amount).as(TaxRateOutput.class);
            }
            return null;
        }
    }

}
