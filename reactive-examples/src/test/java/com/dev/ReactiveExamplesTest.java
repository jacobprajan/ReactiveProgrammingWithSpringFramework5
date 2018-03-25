package com.dev;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReactiveExamplesTest {

	Person max = new Person("Max", "Vaun");
	Person lijo = new Person("Lijo", "Paul");

	Person chris = new Person("Chris", "Noris");
	Person jack = new Person("Jack", "Lidell");

	@Test
	public void monoTests() throws Exception {

		// create new person mono
		Mono<Person> personMono = Mono.just(max);

		// block function is future and it wont execute until something blocks on it.

		// get person object from mono publisher
		Person person = personMono.block();

		log.info(person.sayMyName());

	}

	@Test
	public void monoTransform() throws Exception {
		// create new person mono
		Mono<Person> personMono = Mono.just(lijo);

		PersonCommand command1 = personMono.map(person -> new PersonCommand(person)).block();

		// OR

		PersonCommand command2 = personMono.map(person -> {
			return new PersonCommand(person);
		}).block();

		log.info(command1.sayMyName());
		log.info(command2.sayMyName());
	}

	@Test(expected = NullPointerException.class)
	public void monoFilter() throws Exception {

		Mono<Person> personMono = Mono.just(chris);

		Person chrisAxe = personMono.filter(person -> person.getFirstName().equalsIgnoreCase("foo")).block();

		// throws NullPointerException
		log.info(chrisAxe.sayMyName());
	}

	// FLUX -> multiple objects

	@Test
	public void fluxTest() throws Exception {

		Flux<Person> people = Flux.just(max, lijo, chris, jack);

		people.subscribe(person -> log.info(person.sayMyName()));

	}

	@Test
	public void fluxTestFilter() throws Exception {

		Flux<Person> people = Flux.just(max, lijo, chris, jack);

		people.filter(person -> person.getFirstName().equalsIgnoreCase("chris"))
				.subscribe(person -> log.info(person.sayMyName()));
	}

	@Test
	public void fluxTestDelayNoOutput() throws Exception {

		Flux<Person> people = Flux.just(max, lijo, chris, jack);

		people.delayElements(Duration.ofSeconds(1)).subscribe(person -> log.info(person.sayMyName()));

	}

	@Test
	public void fluxTestDelay() throws Exception {

		CountDownLatch countDownLatch = new CountDownLatch(1);

		Flux<Person> people = Flux.just(max, lijo, chris, jack);

		people.delayElements(Duration.ofSeconds(1))
				// on completion of stream, count down latch
				.doOnComplete(countDownLatch::countDown).subscribe(person -> log.info(person.sayMyName()));

		// tells test to wait until countdown is complete
		countDownLatch.await();
	}

	@Test
	public void fluxTestFilterDelay() throws Exception {

		CountDownLatch countDownLatch = new CountDownLatch(1);

		Flux<Person> people = Flux.just(max, lijo, chris, jack);

		people.delayElements(Duration.ofSeconds(1))
				.filter(person -> person.getFirstName().contains("i"))
				.doOnComplete(countDownLatch::countDown)
				.subscribe(person -> log.info(person.sayMyName()));
		countDownLatch.await();

	}

}
