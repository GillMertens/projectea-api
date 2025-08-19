package com.projectea.projectea.domain.impl.item.adapter;

import com.projectea.projectea.domain.impl.item.DTO.ItemUnitDto;
import com.projectea.projectea.domain.impl.item.DTO.ItemUnitAvailabilityDto;
import com.projectea.projectea.domain.impl.item.entities.ItemUnit;
import com.projectea.projectea.domain.impl.item.services.ItemUnitService;
import com.projectea.projectea.domain.impl.reservation.repositories.ReservationRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ItemUnitAdapter {
    private final ItemUnitService itemUnitService;
    private final ModelMapper modelMapper;
    private final ReservationRepository reservationRepository;

    @PostConstruct
    public void configureMappings() {
        Converter<com.projectea.projectea.domain.impl.item.entities.Item, Long> itemToId = ctx -> ctx.getSource() == null ? null : ctx.getSource().getId();
        modelMapper.typeMap(ItemUnit.class, ItemUnitDto.class).addMappings(mapper ->
                mapper.using(itemToId).map(ItemUnit::getItem, ItemUnitDto::setItemId)
        );
    }

    public List<ItemUnitDto> getUnitsByItemIdDto(Long itemId) {
        return itemUnitService.getUnitsByItemId(itemId).stream()
                .map(unit -> modelMapper.map(unit, ItemUnitDto.class))
                .collect(Collectors.toList());
    }

    public ItemUnitDto getUnitByIdDto(UUID id) {
        ItemUnit unit = itemUnitService.getUnitById(id);
        return unit != null ? modelMapper.map(unit, ItemUnitDto.class) : null;
    }

    public ItemUnitDto createUnitDto(ItemUnit unit) {
        ItemUnit created = itemUnitService.createUnit(unit);
        return modelMapper.map(created, ItemUnitDto.class);
    }

    public ItemUnitDto updateUnitDto(UUID id, ItemUnit unit) {
        ItemUnit updated = itemUnitService.updateUnit(id, unit);
        return updated != null ? modelMapper.map(updated, ItemUnitDto.class) : null;
    }

    public void deleteUnit(UUID id) {
        itemUnitService.deleteUnit(id);
    }

    /**
     * Determines availability status for a list of item units within a specified time window.
     * Checks for overlapping reservations and calculates the latest return date for each unit.
     * 
     * @param unitIds List of unit UUIDs to check availability for
     * @param pickupDate Start date of the requested reservation period
     * @param returnDate End date of the requested reservation period
     * @return List of ItemUnitAvailabilityDto containing availability status and reservation end dates
     */
    public List<ItemUnitAvailabilityDto> getUnitsAvailabilityDto(List<UUID> unitIds, LocalDateTime pickupDate, LocalDateTime returnDate) {
        List<ItemUnit> availableUnits = itemUnitService.getUnitsAvailability(unitIds, pickupDate, returnDate);
        
        List<UUID> ids = unitIds.stream().collect(Collectors.toList());
        List<com.projectea.projectea.domain.impl.reservation.entities.Reservation> overlapping = reservationRepository
                .findByUnits_IdInAndReturnDateAfterAndPickupDateBefore(ids, pickupDate, returnDate);

        Map<UUID, LocalDateTime> unitToMaxReturn = overlapping.stream()
                .flatMap(r -> r.getUnits().stream().map(u -> Map.entry(u.getId(), r.getReturnDate())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a.isAfter(b) ? a : b));

        return unitIds.stream()
                .map(id -> {
                    LocalDateTime until = unitToMaxReturn.get(id);
                    boolean available = until == null || !until.isAfter(pickupDate);
                    return new ItemUnitAvailabilityDto(id, available, until);
                })
                .collect(Collectors.toList());
    }
}


