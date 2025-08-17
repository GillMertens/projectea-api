package com.projectea.projectea.configuration;

import com.projectea.projectea.domain.impl.reservation.entities.Reservation;
import com.projectea.projectea.domain.impl.reservation.entities.ReservationStatus;
import com.projectea.projectea.domain.impl.reservation.repositories.ReservationRepository;
import com.projectea.projectea.domain.impl.user.entities.Address;
import com.projectea.projectea.domain.impl.user.entities.User;
import com.projectea.projectea.domain.impl.user.entities.Role;
import com.projectea.projectea.domain.impl.user.repositories.UserRepository;
import com.projectea.projectea.domain.impl.category.entities.Category;
import com.projectea.projectea.domain.impl.category.repositories.CategoryRepository;
import com.projectea.projectea.domain.impl.item.entities.Condition;
import com.projectea.projectea.domain.impl.item.entities.Item;
import com.projectea.projectea.domain.impl.item.repositories.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    private final ReservationRepository reservationRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create and save users with addresses
        Address address1 = Address.builder()
            .street("123 Main St")
            .city("Anytown")
            .state("Anystate")
            .zipCode("12345")
            .country("USA")
            .build();

        User user1 = User.builder()
            .firstName("John")
            .lastName("Doe")
            .email("john.doe@example.com")
            .password(passwordEncoder.encode("password"))
            .phoneNumber("123-456-7890")
            .address(address1)
            .role(Role.USER)
            .build();
        userRepository.save(user1);

        // Create and save categories
        Category category1 = new Category();
        category1.setName("Lighting");
        categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setName("Audio");
        categoryRepository.save(category2);

        // Create and save items
        //Lighting category items
        Item item1 = new Item();
        item1.setName("LED Light Panel");
        item1.setDescription("A high-performance LED light panel");
        item1.setBrand("BrandX");
        item1.setCondition(Condition.NEW);
        item1.setPrice(49.99);
        item1.setCategory(category1);
        itemRepository.save(item1);

        Item item2 = new Item();
        item2.setName("Stage Light MX-1000");
        item2.setDescription("A high-performance stage light");
        item2.setBrand("BrandY");
        item2.setCondition(Condition.USED);
        item2.setPrice(199.99);
        item2.setCategory(category1);
        itemRepository.save(item2);

        Item item3 = new Item();
        item3.setName("LED Light Bulb");
        item3.setDescription("A high-performance LED light bulb");
        item3.setBrand("BrandZ");
        item3.setCondition(Condition.NEW);
        item3.setPrice(19.99);
        item3.setCategory(category1);
        itemRepository.save(item3);

        Item item4 = new Item();
        item4.setName("LED Light Strip");
        item4.setDescription("A high-performance LED light strip");
        item4.setBrand("BrandZ");
        item4.setCondition(Condition.NEW);
        item4.setPrice(29.99);
        item4.setCategory(category1);
        itemRepository.save(item4);

        //Audio category items
        Item item5 = new Item();
        item5.setName("Blue Yeti USB Microphone");
        item5.setDescription("A high quality USB microphone perfect for podcasting, streaming, and more");
        item5.setBrand("Blue");
        item5.setCondition(Condition.USED);
        item5.setPrice(99.99);
        item5.setCategory(category2);
        itemRepository.save(item5);

        Item item6 = new Item();
        item6.setName("Audio-Technica ATH-M50x");
        item6.setDescription("Professional studio monitor headphones");
        item6.setBrand("Audio-Technica");
        item6.setCondition(Condition.USED);
        item6.setPrice(149.99);
        item6.setCategory(category2);
        itemRepository.save(item6);

        Item item7 = new Item();
        item7.setName("Focusrite Scarlett 2i2");
        item7.setDescription("USB audio interface");
        item7.setBrand("Focusrite");
        item7.setCondition(Condition.USED);
        item7.setPrice(159.99);
        item7.setCategory(category2);
        itemRepository.save(item7);

        Item item8 = new Item();
        item8.setName("Shure SM58");
        item8.setDescription("Dynamic vocal microphone");
        item8.setBrand("Shure");
        item8.setCondition(Condition.USED);
        item8.setPrice(99.99);
        item8.setCategory(category2);
        itemRepository.save(item8);

        Reservation reservation1 = new Reservation();
        reservation1.setUser(user1);
        reservation1.setStatus(ReservationStatus.PENDING);
        reservation1.setPickupDate(java.time.LocalDateTime.now().plusDays(1));
        reservation1.setReturnDate(java.time.LocalDateTime.now().plusDays(7));
        reservation1.setItems(List.of(item1, item2, item3));
        reservationRepository.save(reservation1);
    }
}