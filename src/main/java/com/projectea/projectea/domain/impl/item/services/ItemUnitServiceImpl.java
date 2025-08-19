package com.projectea.projectea.domain.impl.item.services;

import com.projectea.projectea.domain.impl.item.entities.ItemUnit;
import com.projectea.projectea.domain.impl.item.repositories.ItemUnitRepository;
import com.projectea.projectea.domain.impl.reservation.repositories.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemUnitServiceImpl implements ItemUnitService {
    private final ItemUnitRepository itemUnitRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public List<ItemUnit> getUnitsByItemId(Long itemId) {
        return itemUnitRepository.findByItem_Id(itemId);
    }

    @Override
    public ItemUnit getUnitById(UUID id) {
        return itemUnitRepository.findById(id).orElse(null);
    }

    @Override
    public ItemUnit createUnit(ItemUnit unit) {
        return itemUnitRepository.save(unit);
    }

    @Override
    public ItemUnit updateUnit(UUID id, ItemUnit unit) {
        ItemUnit existing = itemUnitRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        existing.setCondition(unit.getCondition());
        existing.setItem(unit.getItem());
        return itemUnitRepository.save(existing);
    }

    @Override
    public void deleteUnit(UUID id) {
        itemUnitRepository.deleteById(id);
    }

    /**
     * Determines which units are available for reservation within a specified time window.
     * Filters out units that have overlapping reservations during the requested period.
     * 
     * @param unitIds List of unit UUIDs to check availability for
     * @param pickupDate Start date of the requested reservation period
     * @param returnDate End date of the requested reservation period
     * @return List of available ItemUnits that can be reserved during the specified period
     */
    @Override
    public List<ItemUnit> getUnitsAvailability(List<UUID> unitIds, LocalDateTime pickupDate, LocalDateTime returnDate) {
        List<ItemUnit> units = unitIds.stream()
                .map(id -> itemUnitRepository.findById(id).orElse(null))
                .filter(u -> u != null)
                .collect(Collectors.toList());
        
        if (units.isEmpty()) {
            return List.of();
        }

        List<UUID> ids = units.stream().map(ItemUnit::getId).collect(Collectors.toList());
        List<com.projectea.projectea.domain.impl.reservation.entities.Reservation> overlapping = reservationRepository
                .findByUnits_IdInAndReturnDateAfterAndPickupDateBefore(ids, pickupDate, returnDate);

        Map<UUID, LocalDateTime> unitToMaxReturn = overlapping.stream()
                .flatMap(r -> r.getUnits().stream().map(u -> Map.entry(u.getId(), r.getReturnDate())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a.isAfter(b) ? a : b));

        return units.stream()
                .filter(unit -> {
                    LocalDateTime until = unitToMaxReturn.get(unit.getId());
                    return until == null || !until.isAfter(pickupDate);
                })
                .collect(Collectors.toList());
    }
}


