package com.example.order.service;

import com.example.order.entity.Items;
import com.example.order.entity.OrderItems;
import com.example.order.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public OrderItems save(OrderItems items){
        return itemRepository.saveAndFlush(items);
    }
}

