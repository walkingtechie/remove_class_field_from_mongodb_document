package com.walking.techie.bootstrap;

import com.walking.techie.model.Address;
import com.walking.techie.model.Person;
import com.walking.techie.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class PersonBootstrap implements ApplicationListener<ContextRefreshedEvent> {
  private final PersonRepository personRepository;

  public PersonBootstrap(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    Person person = loadPerson();
    Person savedPerson = personRepository.save(person);
    log.info("Person saved in DB :: {}", savedPerson);
    Optional<Person> personById = personRepository.findById(savedPerson.getId());
    if (personById.isPresent()) {
      log.info("Person fetched from DB :: {} with id :: {}", personById.get(), savedPerson.getId());
    }
  }

  private Person loadPerson() {
    Person person = new Person();
    person.setName("Walking Techie");
    person.setEmail("walkingtechie.blogspot@gmail.com");
    person.setAddress(loadAddress());
    return person;
  }

  private Address loadAddress() {
    Address address = new Address();
    address.setName("Walking Techie");
    address.setAddress("Test");
    address.setCity("Bangalore");
    address.setCountry("India");
    address.setPincode("560078");
    address.setState("KA");
    return address;
  }
}
