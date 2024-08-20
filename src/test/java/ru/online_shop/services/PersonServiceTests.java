package ru.online_shop.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import ru.online_shop.models.Image;
import ru.online_shop.models.Person;
import ru.online_shop.models.Product;
import ru.online_shop.repositories.ImageRepository;
import ru.online_shop.repositories.PersonRepository;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@Slf4j
public class PersonServiceTests {
    @Mock
    private PersonRepository personRepository;
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private PersonService personService;
    private Person personTest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        personTest = Person.builder().id(1L).username("Gosha").profilePictureId(1L)
                        .productsInTheCart(new ArrayList<>()).build();
    }

    @Test
    public void findByUsername_ReturnPerson() {

        when(personService.findByUsername(anyString())).thenReturn(Optional.ofNullable(personTest));

        Optional<Person> person = personService.findByUsername(personTest.getUsername());

        log.info("Person: {}", person);
        assertThat(person).isNotEmpty();
        verify(personRepository, times(1)).findByUsername(anyString());
    }

    @Test
    public void update_ShouldSaveUpdatedPerson() {
        Person person2 = Person.builder().id(2L).role("USER").profilePictureId(3L).build();

        personService.update(person2, personTest);

        assertEquals(2L, personTest.getId());
        assertEquals("USER", personTest.getRole());
        verify(personRepository, times(1)).save(personTest);
    }

    @Test
    public void addProfilePicture_ShouldAddProfilePictureToPerson() {
        Image profilePicture = new Image();
        profilePicture.setId(2L);
        MockMultipartFile imageFile = new MockMultipartFile("image", "profile.jpg", "image/jpeg",
                "test image data".getBytes());

        when(modelMapper.map(imageFile, Image.class)).thenReturn(profilePicture);
        when(imageRepository.save(any(Image.class))).thenReturn(profilePicture);

        personService.addProfilePicture(personTest, imageFile);

        assertEquals(2L, personTest.getProfilePictureId());
        verify(imageRepository, times(2)).save(any(Image.class));
        verify(personRepository, times(1)).save(any(Person.class));
        verify(imageRepository, times(1)).deleteById(1L);
    }

    @Test
    public void addProductToCart_ShouldAddProductToPerson() {
        Product product = Product.builder().id(1L).title("test").price(100.0).people(new ArrayList<>()).build();
        when(personRepository.save(any(Person.class))).thenReturn(personTest);

        personService.addProductToCart(personTest, product);

        assertEquals(1, product.getPeople().size());
        assertEquals(1, personTest.getProductsInTheCart().size());
    }

    @Test
    public void removeFromTheCart_ShouldRemoveProductInTheCart() {
        Product product = Product.builder().id(1L).title("test").price(100.0).people(new ArrayList<>()).build();
        when(personRepository.save(any(Person.class))).thenReturn(personTest);

        personService.addProductToCart(personTest, product);
        personService.removeFromTheCart(personTest, product);

        assertThat(personTest.getProductsInTheCart()).isEmpty();
        assertThat(product.getPeople()).isEmpty();
    }

    @Test
    public void removeAllFromTheCart_ShouldRemoveAllFromTheCart() {
        Product product = Product.builder().id(1L).title("test").price(100.0).people(new ArrayList<>()).build();
        when(personRepository.save(any(Person.class))).thenReturn(personTest);

        personService.addProductToCart(personTest, product);
        personService.removeAllFromTheCart(personTest);

        assertThat(personTest.getProductsInTheCart()).isEmpty();
    }

    @Test
    public void findById_ShouldReturnPerson() {
        when(personRepository.findById(1L)).thenReturn(Optional.ofNullable(personTest));

        Person person = personService.findById(1L);

        assertThat(person).isNotNull();
    }

    @Test
    public void addAccountNumber_ShouldAddAccountNumber() {
        String accountNumber = "123";
        when(personRepository.save(any(Person.class))).thenReturn(personTest);

        personService.addAccountNumber(personTest, accountNumber);

        assertEquals(accountNumber, personTest.getAccountNumber());
    }
}
