package rsb.data.r2dbc;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.r2dbc.ConnectionFactoryAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.reactive.TransactionalOperator;

@Configuration
@AutoConfigureAfter(ConnectionFactoryAutoConfiguration.class)
@Import(CustomerDatabaseInitializer.class)
public class CommonAutoConfiguration {

	@Bean
	CustomerService defaultCustomerService(SimpleCustomerRepository cr,
			TransactionalOperator to, CustomerDatabaseInitializer dbi) {
		return new CustomerService(cr, to, dbi);
	}

}
