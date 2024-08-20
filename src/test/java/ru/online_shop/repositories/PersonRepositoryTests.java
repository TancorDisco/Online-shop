package ru.online_shop.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.online_shop.models.Person;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PersonRepositoryTests {

    @Mock
    PersonRepository personRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findByUsername_ShouldReturnPerson() {
        String username = "Gosha";
        Person person = Person.builder().username(username).build();

        when(personRepository.findByUsername(username)).thenReturn(Optional.of(person));

        Optional<Person> foundPerson = personRepository.findByUsername(username);

        assertThat(foundPerson).isPresent();
        assertThat(foundPerson.get().getUsername()).isEqualTo(username);
    }

    @Test
    public void findByUsername_ShouldReturnNothing() {
        String username = "Gosha";

        Optional<Person> foundPerson = personRepository.findByUsername(username);

        assertThat(foundPerson).isEmpty();
    }
}
