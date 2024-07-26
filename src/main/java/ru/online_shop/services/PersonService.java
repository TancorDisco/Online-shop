package ru.online_shop.services;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.online_shop.models.Image;
import ru.online_shop.models.Person;
import ru.online_shop.models.Product;
import ru.online_shop.repositories.ImageRepository;
import ru.online_shop.repositories.PersonRepository;

import java.util.Optional;

@Slf4j
@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PersonService(PersonRepository personRepository, ImageRepository imageRepository, ModelMapper modelMapper) {
        this.personRepository = personRepository;
        this.imageRepository = imageRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<Person> findByUsername(String username) {
        return personRepository.findByUsername(username);
    }

    @Transactional
    public void update(Person personToBeUpdated, Person person) {
        person.setId(personToBeUpdated.getId());
        person.setRole(personToBeUpdated.getRole());
        person.setProfilePictureId(personToBeUpdated.getProfilePictureId());
        personRepository.save(person);
    }

    @Transactional
    public void addProfilePicture(Person authUser, MultipartFile image) {
        Image profilePicture = convertToImage(image);
        Long idCurrentProfilePicture = authUser.getProfilePictureId();

        imageRepository.save(profilePicture);
        profilePicture.setPerson(authUser);

        authUser.setProfilePictureId(profilePicture.getId());
        log.info("User: {}", authUser);
        personRepository.save(authUser);
        imageRepository.save(profilePicture);

        if (idCurrentProfilePicture != null) {
            imageRepository.deleteById(idCurrentProfilePicture);
        }
    }

    @SneakyThrows
    private Image convertToImage(MultipartFile file) {
        return modelMapper.map(file, Image.class);
    }

    public void addProductToCart(Person authUser, Product product) {
        authUser.getProducts().add(product);
        product.getPeople().add(authUser);
        personRepository.save(authUser);
    }
}
