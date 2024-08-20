package ru.online_shop.services;

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
import ru.online_shop.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTests {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private ProductService productService;
    private Product productTest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productTest = Product.builder().id(1L).title("test").price(100.0).build();
    }

    @Test
    public void findAll_ShouldReturnAllProducts() {
        when(productRepository.findAll()).thenReturn(Collections.singletonList(productTest));

        List<Product> productList = productRepository.findAll();

        assertEquals(1, productList.size());
    }

    @Test
    public void findById_ShouldReturnProduct() {
        when(productRepository.findById(1L)).thenReturn(productTest);

        Product product = productRepository.findById(1L);

        assertThat(product).isNotNull();
        assertEquals("test", product.getTitle());
    }

    @Test
    public void findByTitleStartingWith_ShouldReturnProductsStartingWith() {
        when(productRepository.findByTitleStartingWith("te")).thenReturn(Collections.singletonList(productTest));

        List<Product> products = productRepository.findByTitleStartingWith("te");

        assertEquals(1, products.size());
        assertEquals("test", products.getFirst().getTitle());
    }

    @Test
    public void findProducts_ShouldReturnAllProducts_WhenRequestEquals0() {
        when(productRepository.findAll()).thenReturn(Collections.singletonList(productTest));

        List<Product> products = productRepository.findAll();

        assertEquals(1, products.size());
    }

    @Test
    public void findProducts_ShouldReturnOnlyRightProducts_WhenRequestNotEquals0() {
        when(productRepository.findByTitleStartingWith("pa")).thenReturn(new ArrayList<>());

        List<Product> products = productRepository.findByTitleStartingWith("pa");

        assertThat(products).isEmpty();
    }

    @Test
    public void createProduct_ShouldSaveProduct() {
        Image previewImage = Image.builder().id(1L).originalFileName("preview.jpg").build();
        MockMultipartFile imageFile = new MockMultipartFile("image", "preview.jpg",
                "image/jpeg", "test image data".getBytes());

        when(modelMapper.map(imageFile, Image.class)).thenReturn(previewImage);
        when(productRepository.save(any(Product.class))).thenReturn(productTest);

        productService.createProduct(productTest, Collections.singletonList(imageFile));

        assertEquals(1, productTest.getImages().size());
        assertEquals(1, productTest.getPreviewImageId());
        assertThat(previewImage.getProduct()).isNotNull();
    }

    @Test
    public void update_ShouldUpdatedProduct() {
        when(productRepository.findById(1L)).thenReturn(productTest);
        when(productRepository.save(any(Product.class))).thenReturn(productTest);

        productTest.setPrice(102.0);
        productService.update(productTest, 1L);

        assertEquals(102.0, productTest.getPrice());
        assertEquals(1, productTest.getId());
    }

    @Test
    public void delete_ShouldDeleteProduct() {
        productService.delete(1L);

        verify(productRepository, times(1)).deleteById(1L);
        assertThat(productRepository.findById(1L)).isNull();
    }

    @Test
    public void findByPerson_ShouldReturnProductsByPerson() {
        Person person = Person.builder().build();
        productTest.setPeople(new ArrayList<>(List.of(person)));
        when(productRepository.findAllByPeople(person)).thenReturn(Collections.singletonList(productTest));

        List<Product> products = productService.findByPerson(person);

        assertEquals(1, products.size());
        assertEquals("test", products.getFirst().getTitle());
    }

    @Test
    public void getTotalPrice_ReturnTotalPrice() {
        Person person = Person.builder().productsInTheCart(new ArrayList<>(List.of(productTest))).build();
        when(productRepository.findAllByPeople(person)).thenReturn(person.getProductsInTheCart());

        double sum = productService.getTotalPrice(person);

        assertEquals(100, sum);
    }
}
