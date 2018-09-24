package rsb.data.postgresql;

import io.r2dbc.postgresql.PostgresqlResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerServiceTest {

	@Autowired
	private CustomerService customerService;

	@Test
	public void all() {

		Flux<PostgresqlResult> insert = Flux
				.from(this.customerService.create(1L, "first@email.com"))
				.thenMany(this.customerService.create(2L, "second@email.com"))
				.thenMany(this.customerService.create(3L, "third@email.com"));

		Flux<PostgresqlResult> deleteEverything = this.customerService.all()
				.flatMap(customer -> this.customerService.delete(customer.getId()));

		StepVerifier.create(deleteEverything //
				.thenMany(insert) //
				.thenMany(this.customerService.all())//
		).expectNextCount(3).verifyComplete();
	}

}