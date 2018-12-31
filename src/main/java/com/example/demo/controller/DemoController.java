package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repo.CustomerRepository;
import com.example.demo.vo.Customer;
import com.example.demo.vo.Person;
import com.example.demo.vo.Provider;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class DemoController {

	@Autowired
	private Provider service;
	
	 @Autowired
	 private CustomerRepository customerRepository;
	
	@GetMapping("/features")
	public Flux<String> list() {
		return Flux.just("Features 1", "Features 2", "Features 3");
	}

	@GetMapping("/features/{id}")
	public ResponseEntity<Object> findById(@PathVariable Integer id) {
	//	long start = System.currentTimeMillis();
//		Flux<Person> dynamicFlux = Flux.create(sink -> {
//			service.register(sink);
//			service.generatePerson(id);
//		});
		//dynamicFlux.subscribe(System.out::println);
//		ParallelFlux<Person> dynamicFlux=service.generatePerson(id);
//		Mono<List<Person>> l = dynamicFlux.collectSortedList(new Comparator() {
//			public  int compare(Object o1, Object o2) {
//				String s1 =o1.toString();
//				String s2 =o2.toString();
//				return s1.compareTo(s2);
//			}
//		});
//		 Mono.when(l).block(); 
//		 long end = System.currentTimeMillis();
//		 System.out.println("total time: "+(end-start));
//		ParallelFlux<Person> f = this.service.generatePerson(id);
//		long end = System.currentTimeMillis();
//		System.out.println("total time: "+(end-start));
//		
		//System.out.println("p1nstarted");
	 //service.p(1);
	 //System.out.println("p1nstarted");
		// Mono<Person> fulx2 = service.p1(2);
		//Mono<Person> fulx3 = service.p1(3);
		//long end = System.currentTimeMillis();
		//System.out.println("total time: "+(end-start));
//		Mono<Object> mo = getMono();
//		obj.put("totalTime",(end-start));
//		obj.put("data", mo.toString());
		
		//Flux.just(service.p(2)).parallel(8);
		service.compatableFuture();
		return new ResponseEntity<Object>("completed",HttpStatus.OK);
		//return getMono();
		//.map(o->arr.put(new JSONObject(o.getT1().toJSON().toString().concat(o.getT2().toJSON().toString().concat(o.getT3().toJSON().toString())) )));
				//.map(o->arr.put(o.getT1().toJSON()));
		
//		.map(tuple3 -> tuple3.getT1().concat(tuple3.getT2()).concat(tuple3.getT3()))
//        .map(String::toUpperCase)
//        .subscribe(value -> System.out.println("zip result:" + value));
		
		//service.test(id);
		
		//Flux<Person> ppp = service.pp(id);
		//return ppp;
		
		//return null;
	}
	
	
	private Mono<Object> getMono(){
		Mono<Person> fulx1 = service.p1(1);
		Mono<Person> fulx2 = service.p1(2);
		JSONArray arr = new JSONArray();
		return Mono.zip(fulx1, fulx2)
		.map(t1->t1.getT1().toString().concat(t1.getT2().toString()));
		//.map(o->arr.put(new JSONObject(o.getT1().toJSON().toString().concat(o.getT2().toJSON().toString())).toString()));
	}
	
	@GetMapping("/featuress/{id}")
	public List<Person> getPerson(@PathVariable Integer id){
		long start = System.currentTimeMillis();
		List l = new ArrayList<>();
		for(int i=0;i<id;i++) {
			Person p  = new Person();
			p.setId(i);
			p.setName("name"+i);
			l.add(p.returnPerson());
		}
		
		long end = System.currentTimeMillis();
		 System.out.println("total time: "+(end-start));
		 return l;
		
	}
	
	@GetMapping("/test")
	public void getPerson2(){
		Flux<Integer> ints = Flux.range(1, 3); 
		ints.subscribe(s->{System.out.println(s);});
		
		Flux<Integer> ints2 = Flux.range(1, 4);
//				.map(i->{
//				if(i<=3)  {
//					return i;
//				}	throw new RuntimeException("got to 4");
//				});
		
		ints2.subscribe(i->System.out.println(i),error->System.out.println("error: "+error),()->System.out.println("done"));
	}
	
	
	@RequestMapping(value = "/save" , method = RequestMethod.POST)
	public ResponseEntity<Object> saveData(@RequestBody Customer input){
		this.customerRepository.save(input);
		return new ResponseEntity<Object>("Successfully inserted",HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/findByFirstName" , method = RequestMethod.GET)
	public ResponseEntity<Object> saveData(@RequestParam("input") String input){
		List<Customer> names = customerRepository.findByFirstname(input);
		return new ResponseEntity<Object>(names.toString(),HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/ageGreaterThan" , method = RequestMethod.GET)
	public ResponseEntity<Object> ageGreaterThan(@RequestParam("age") int input){
		List<Customer> names = customerRepository.findByAgeGreaterThan(input);
		return new ResponseEntity<Object>(names.toString(),HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/findById" , method = RequestMethod.GET)
	public ResponseEntity<Object> findById(@RequestParam("id") String input){
		Optional<Customer> names = customerRepository.findById(input);
		return new ResponseEntity<Object>(names.get(),HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/findAll" , method = RequestMethod.GET)
	public ResponseEntity<Object> findById(){
		List<Customer> names = customerRepository.findAll();
		return new ResponseEntity<Object>(names,HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/deleteById", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteById(@RequestParam("id") String id){
		this.customerRepository.deleteById(id);
		return new ResponseEntity<Object>("Successfully deleted",HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/deleteAll", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteAll(){
		this.customerRepository.deleteAll();
		return new ResponseEntity<Object>("Successfully deleted all ",HttpStatus.OK);
	}
	
}
