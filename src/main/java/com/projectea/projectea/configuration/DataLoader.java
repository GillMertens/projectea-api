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
import com.projectea.projectea.domain.impl.item.entities.ItemUnit;
import com.projectea.projectea.domain.impl.item.repositories.ItemUnitRepository;
import com.projectea.projectea.domain.impl.item.entities.ItemImage;
import com.projectea.projectea.domain.impl.item.repositories.ItemImageRepository;
import org.springframework.boot.CommandLineRunner;
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
    private final ItemUnitRepository itemUnitRepository;
    private final ItemImageRepository itemImageRepository;
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
        item1.setThumbnailUrl("assets/images/items/a-high-performance-led-light-panel/a-high-performance-led-light-panel-1.jpg");
        item1.setCategory(category1);
        itemRepository.save(item1);
        addImage(item1, "assets/images/items/a-high-performance-led-light-panel/a-high-performance-led-light-panel-1.jpg");
        addImage(item1, "assets/images/items/a-high-performance-led-light-panel/a-high-performance-led-light-panel-2.jpg");
        addImage(item1, "assets/images/items/a-high-performance-led-light-panel/a-high-performance-led-light-panel-3.jpg");
        addImage(item1, "assets/images/items/a-high-performance-led-light-panel/a-high-performance-led-light-panel-4.jpg");
        addImage(item1, "assets/images/items/a-high-performance-led-light-panel/a-high-performance-led-light-panel-5.jpg");
        ItemUnit item1u1 = new ItemUnit();
        item1u1.setItem(item1);
        item1u1.setCondition(Condition.NEW);
        itemUnitRepository.save(item1u1);
        ItemUnit item1u2 = new ItemUnit();
        item1u2.setItem(item1);
        item1u2.setCondition(Condition.USED);
        itemUnitRepository.save(item1u2);

        Item item2 = new Item();
        item2.setName("Stage Light MX-1000");
        item2.setDescription("A high-performance stage light");
        item2.setBrand("BrandY");
        item2.setThumbnailUrl("assets/images/items/a-high-performance-stage-light/a-high-performance-stage-light-1.avif");
        item2.setCategory(category1);
        itemRepository.save(item2);
        addImage(item2, "assets/images/items/a-high-performance-stage-light/a-high-performance-stage-light-1.avif");
        addImage(item2, "assets/images/items/a-high-performance-stage-light/a-high-performance-stage-light-2.avif");
        addImage(item2, "assets/images/items/a-high-performance-stage-light/a-high-performance-stage-light-3.avif");
        addImage(item2, "assets/images/items/a-high-performance-stage-light/a-high-performance-stage-light-4.avif");
        ItemUnit item2u1 = new ItemUnit();
        item2u1.setItem(item2);
        item2u1.setCondition(Condition.USED);
        itemUnitRepository.save(item2u1);

        Item item3 = new Item();
        item3.setName("LED Light Bulb");
        item3.setDescription("A high-performance LED light bulb");
        item3.setBrand("BrandZ");
        item3.setThumbnailUrl("assets/images/items/a-high-performance-led-light-bulb/a-high-performance-led-light-bulb-1.webp");
        item3.setCategory(category1);
        itemRepository.save(item3);
        addImage(item3, "assets/images/items/a-high-performance-led-light-bulb/a-high-performance-led-light-bulb-1.webp");
        ItemUnit item3u1 = new ItemUnit();
        item3u1.setItem(item3);
        item3u1.setCondition(Condition.NEW);
        itemUnitRepository.save(item3u1);

        Item item4 = new Item();
        item4.setName("LED Light Strip");
        item4.setDescription("A high-performance LED light strip");
        item4.setBrand("BrandZ");
        item4.setThumbnailUrl("assets/images/items/a-high-performance-led-light-strip/a-high-performance-led-light-strip-1.webp");
        item4.setCategory(category1);
        itemRepository.save(item4);
        addImage(item4, "assets/images/items/a-high-performance-led-light-strip/a-high-performance-led-light-strip-1.webp");
        addImage(item4, "assets/images/items/a-high-performance-led-light-strip/a-high-performance-led-light-strip-2.webp");
        addImage(item4, "assets/images/items/a-high-performance-led-light-strip/a-high-performance-led-light-strip-3.webp");
        addImage(item4, "assets/images/items/a-high-performance-led-light-strip/a-high-performance-led-light-strip-4.webp");
        ItemUnit item4u1 = new ItemUnit();
        item4u1.setItem(item4);
        item4u1.setCondition(Condition.NEW);
        itemUnitRepository.save(item4u1);

        //Audio category items
        Item item5 = new Item();
        item5.setName("Blue Yeti USB Microphone");
        item5.setDescription("A high quality USB microphone perfect for podcasting, streaming, and more");
        item5.setBrand("Blue");
        item5.setThumbnailUrl("assets/images/items/a-high-quality-usb-microphone-perfect-for-podcasting-streaming-and-more/a-high-quality-usb-microphone-perfect-for-podcasting-streaming-and-more-1.png");
        item5.setCategory(category2);
        itemRepository.save(item5);
        addImage(item5, "assets/images/items/a-high-quality-usb-microphone-perfect-for-podcasting-streaming-and-more/a-high-quality-usb-microphone-perfect-for-podcasting-streaming-and-more-1.png");
        addImage(item5, "assets/images/items/a-high-quality-usb-microphone-perfect-for-podcasting-streaming-and-more/a-high-quality-usb-microphone-perfect-for-podcasting-streaming-and-more-2.png");
        addImage(item5, "assets/images/items/a-high-quality-usb-microphone-perfect-for-podcasting-streaming-and-more/a-high-quality-usb-microphone-perfect-for-podcasting-streaming-and-more-3.png");
        addImage(item5, "assets/images/items/a-high-quality-usb-microphone-perfect-for-podcasting-streaming-and-more/a-high-quality-usb-microphone-perfect-for-podcasting-streaming-and-more-4.png");
        addImage(item5, "assets/images/items/a-high-quality-usb-microphone-perfect-for-podcasting-streaming-and-more/a-high-quality-usb-microphone-perfect-for-podcasting-streaming-and-more-5.png");
        ItemUnit item5u1 = new ItemUnit();
        item5u1.setItem(item5);
        item5u1.setCondition(Condition.USED);
        itemUnitRepository.save(item5u1);

        Item item6 = new Item();
        item6.setName("Audio-Technica ATH-M50x");
        item6.setDescription("Professional studio monitor headphones");
        item6.setBrand("Audio-Technica");
        item6.setThumbnailUrl("assets/images/items/professional-studio-monitor-headphones/professional-studio-monitor-headphones-1.webp");
        item6.setCategory(category2);
        itemRepository.save(item6);
        addImage(item6, "assets/images/items/professional-studio-monitor-headphones/professional-studio-monitor-headphones-1.webp");
        addImage(item6, "assets/images/items/professional-studio-monitor-headphones/professional-studio-monitor-headphones-2.webp");
        addImage(item6, "assets/images/items/professional-studio-monitor-headphones/professional-studio-monitor-headphones-3.webp");
        addImage(item6, "assets/images/items/professional-studio-monitor-headphones/professional-studio-monitor-headphones-4.webp");
        addImage(item6, "assets/images/items/professional-studio-monitor-headphones/professional-studio-monitor-headphones-5.webp");
        ItemUnit item6u1 = new ItemUnit();
        item6u1.setItem(item6);
        item6u1.setCondition(Condition.USED);
        itemUnitRepository.save(item6u1);

        Item item7 = new Item();
        item7.setName("Focusrite Scarlett 2i2");
        item7.setDescription("USB audio interface");
        item7.setBrand("Focusrite");
        item7.setThumbnailUrl("assets/images/items/usb-audio-interface/usb-audio-interface-1.jpg");
        item7.setCategory(category2);
        itemRepository.save(item7);
        addImage(item7, "assets/images/items/usb-audio-interface/usb-audio-interface-1.jpg");
        addImage(item7, "assets/images/items/usb-audio-interface/usb-audio-interface-2.jpg");
        ItemUnit item7u1 = new ItemUnit();
        item7u1.setItem(item7);
        item7u1.setCondition(Condition.USED);
        itemUnitRepository.save(item7u1);

        Item item8 = new Item();
        item8.setName("Shure SM58");
        item8.setDescription("Dynamic vocal microphone");
        item8.setBrand("Shure");
        item8.setThumbnailUrl("assets/images/items/dynamic-vocal-microphone/dynamic-vocal-microphone-1.webp");
        item8.setCategory(category2);
        itemRepository.save(item8);
        addImage(item8, "assets/images/items/dynamic-vocal-microphone/dynamic-vocal-microphone-1.webp");
        ItemUnit item8u1 = new ItemUnit();
        item8u1.setItem(item8);
        item8u1.setCondition(Condition.USED);
        itemUnitRepository.save(item8u1);

        Reservation reservation1 = new Reservation();
        reservation1.setUser(user1);
        reservation1.setStatus(ReservationStatus.PENDING);
        reservation1.setPickupDate(java.time.LocalDateTime.now().plusDays(1));
        reservation1.setReturnDate(java.time.LocalDateTime.now().plusDays(7));
        reservation1.setUnits(List.of(item1u1, item2u1, item3u1));
        reservationRepository.save(reservation1);

    }

    private void addImage(Item item, String url) {
        ItemImage img = new ItemImage();
        img.setItem(item);
        img.setUrl(url);
        itemImageRepository.save(img);
    }
}