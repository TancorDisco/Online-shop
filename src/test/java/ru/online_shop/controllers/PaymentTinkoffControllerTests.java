package ru.online_shop.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.online_shop.models.Person;
import ru.online_shop.services.OrderService;
import ru.online_shop.services.PaymentTinkoffService;
import ru.online_shop.services.PersonService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PaymentTinkoffControllerTests {

    private MockMvc mockMvc;
    @Mock
    private PaymentTinkoffService paymentService;
    @Mock
    private PersonService personService;
    @Mock
    private OrderService orderService;
    @InjectMocks
    private PaymentTinkoffController paymentTinkoffController;
    private Person personTest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentTinkoffController).build();
        personTest = Person.builder().build();
    }

    @Test
    public void createPayment_ReturnStatusPayment_WhenSuccess() throws Exception {
        Long personId = 1L;
        when(personService.findById(personId)).thenReturn(personTest);
        when(paymentService.processPayment(personTest)).thenReturn(true);

        mockMvc.perform(post("/payment/{id}", personId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Payment processed successfully"));

        verify(orderService).createOrder(personTest);
    }

    @Test
    public void createPayment_ReturnStatusPayment_WhenPersonNotFound() throws Exception {
        Long personId = 2L;
        when(personService.findById(personId)).thenReturn(null);

        mockMvc.perform(post("/payment/{id}", personId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(paymentService, never()).processPayment(any());
        verify(orderService, never()).createOrder(any());
    }

    @Test
    public void createPayment_ReturnStatusPayment_WhenFailed() throws Exception {
        Long personId = 1L;
        when(personService.findById(personId)).thenReturn(personTest);
        when(paymentService.processPayment(personTest)).thenReturn(false);

        mockMvc.perform(post("/payment/{id}", personId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(500))
                .andExpect(content().string("Payment failed"));

        verify(orderService, never()).createOrder(any());
    }
}
