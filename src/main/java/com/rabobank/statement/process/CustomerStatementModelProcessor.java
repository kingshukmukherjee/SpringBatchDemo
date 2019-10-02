package com.rabobank.statement.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * <h1>Validation class!</h1>
 * This programme is controlling data validation
 *
 * @author Kingshuk Mukherjee - 233303
 * @version 1.0
 * @since 20/09/2019
 */

public class CustomerStatementModelProcessor implements ItemProcessor<CustomerStatementModel,
        CustomerStatementModel> {

    private static final Logger log = LoggerFactory.getLogger(CustomerStatementModelProcessor.class);
    private Map<CustomerStatementModel, String> previousStatement = new HashMap<>();

    @Override
    public CustomerStatementModel process(final CustomerStatementModel customerStatementModel) throws Exception {

        if (customerStatementModel != null) {

            /**
             * This implementation assumes that there is enough room in memory to store the duplicate
             * Users.  Otherwise, we have to store them somewhere and can do a look-up on.
             */
            if (customerStatementModel.getReference() != null) {
                if (previousStatement.containsValue(customerStatementModel.getReference())) {
                    log.warn(String.format("Duplicate Reference found: %s", customerStatementModel.getReference()));
                    return customerStatementModel;
                }
                previousStatement.put(customerStatementModel, customerStatementModel.getReference());
            }

            if (customerStatementModel.getEndBalance() != null) {

                float endBal = Float.parseFloat(customerStatementModel.getEndBalance());
                float startBal = Float.parseFloat(customerStatementModel.getStartBalance());
                float mutation = Float.parseFloat(customerStatementModel.getMutation());

                DecimalFormat decimalFormat = new DecimalFormat("#.##");

                float eval = Float.parseFloat(decimalFormat.format((startBal + mutation)));

                if (Float.compare(endBal, eval) != 0) {
                    log.warn(String.format("End balance is wrong: %s", customerStatementModel.getEndBalance()));
                    return customerStatementModel;
                }
            }
        }
        return null;
    }

}
