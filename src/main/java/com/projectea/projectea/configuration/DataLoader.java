package com.projectea.projectea.configuration;

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

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
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
        category1.setName("Electronics");
        categoryRepository.save(category1);

        // Create and save items
        Item item1 = new Item();
        item1.setName("Laptop");
        item1.setDescription("A high-performance laptop");
        item1.setBrand("BrandX");
        item1.setCondition(Condition.NEW);
        item1.setPrice(999.99);
        item1.setCategory(category1);
        itemRepository.save(item1);
    }
}