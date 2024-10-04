package com.example.order.mappers;

import com.example.order.entity.OrderItems;
import com.example.order.entity.PayUProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public abstract class OrderItemsToPayUProductMapper {
    public PayUProduct mapToPayUProduct(OrderItems orderItems){
        return map(orderItems);
    }


    @Mappings({
            @Mapping(source = "priceUnit",target = "unitPrice")
    })
    protected abstract PayUProduct map(OrderItems orderItems);
}
