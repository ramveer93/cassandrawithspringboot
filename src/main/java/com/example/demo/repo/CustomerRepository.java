package com.example.demo.repo;
import java.util.List;

import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import com.example.demo.vo.Customer;
 
public interface CustomerRepository extends CassandraRepository<Customer, String> {
 
	@AllowFiltering
	public List<Customer> findByFirstname(String firstname);
 
	@AllowFiltering
	public List<Customer> findByAgeGreaterThan(int age);
	
	@AllowFiltering
	public List<Customer> findById(int age);
}