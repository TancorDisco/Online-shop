package ru.online_shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.online_shop.models.Person;
import ru.online_shop.repositories.PersonRepository;

import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Optional<Person> findByUsername(String username) {
        return personRepository.findByUsername(username);
    }

    @Transactional
    public void update(Person personToBeUpdated, Person person) {
        person.setId(personToBeUpdated.getId());
        person.setRole(personToBeUpdated.getRole());
        personRepository.save(person);
    }
}
