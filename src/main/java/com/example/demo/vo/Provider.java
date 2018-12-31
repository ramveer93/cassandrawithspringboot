package com.example.demo.vo;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;

@Service
public class Provider {
int count = 0;
FluxSink<Person> sink;
Flux<Person> f=Flux.empty();;

public void generatePerson(int n) {
	System.out.println("started generating persons: ---->"+n);
	for(int i=0;i<n;i++) {
		Person p  = new Person();
		p.setId(i);
		p.setName("name"+i);
		//sink.next(p.returnPerson());
		 f.just(p.returnPerson()).parallel().runOn(Schedulers.parallel()).
		subscribe(m -> System.out.println(m));
		//System.out.println("started generating persons: ---->"+i);
	}
	 Flux.range(0, n)
		.map(v->doSom(v))
		.subscribeOn(Schedulers.elastic());
//		.parallel(8).runOn(Schedulers.parallel()).subscribe(s->System.out.println("no is L:"+s));
	//System.out.println("DONE generating persons: ---->"+n);
	//return f;
}

public Flux<Person> p(int n){
	//Flux.just(doSom(n)).subscribeOn(Schedulers.elastic());
	Scheduler scheduler2 = Schedulers.newElastic("thread");
	
	 return Flux.just(doSom(n))
            .doOnNext(value -> System.out.println("Value " + value + " on :" + Thread.currentThread().getName()))
            .subscribeOn(scheduler2);
	//Mono.just(doSom(n));
	//return pers;
}


public void compatableFuture() {
	// Run a task specified by a Runnable Object asynchronously.
	CompletableFuture.runAsync(new Runnable() {
	    @Override
	    public void run() {
	        // Simulate a long-running Job
	        try {
	        	doSom(12);
	        } catch (Exception e) {
	            throw new IllegalStateException(e);
	        }
	        //System.out.println("I'll run in a separate thread than the main thread.");
	    }
	});
	
}
public Mono<Person> p1(int n){
	return Mono.fromCallable(() -> {
		return doSom(n);
	}).subscribeOn(Schedulers.parallel());
}

public void pp(int n){
	 Flux.range(0, 1)
	.map(v->doSom(v))
	.subscribeOn(Schedulers.parallel());
}


public Person doSom(int n) {
	System.out.println("start building person: "+n);
		Person p  = new Person();
		p.setId(n);
		p.setName("name"+n);
		System.out.println("DONE building person: "+n);
		return p.returnPerson();
}

public void register(FluxSink<Person> sink) {
    this.sink = sink;
}

public void test(int n) {
	Mono<Person> mono1 = Mono.fromCallable(() -> {
		return doSom(n);
	}).subscribeOn(Schedulers.parallel());
	Mono<Person> mono2 = Mono.fromCallable(() -> {
		return doSom(n);
	}).subscribeOn(Schedulers.parallel());
	Tuple2<Person, Person> result = mono1.zipWith(mono2).block();
	System.out.println(result.getT1());
	System.out.println(result.getT2());
}

}
