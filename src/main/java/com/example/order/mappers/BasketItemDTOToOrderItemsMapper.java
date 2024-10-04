package com.example.order.mappers;


import com.example.order.entity.BasketItemDTO;
import com.example.order.entity.OrderItems;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public abstract class BasketItemDTOToOrderItemsMapper {


    public OrderItems mapToOrderItems(BasketItemDTO basketItemDTO){
        return map(basketItemDTO);
    }


    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "product", source = "uid"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "quantity", source = "quantity"),
            @Mapping(target = "priceUnit", source = "price"),
            @Mapping(target = "priceSummary", source = "summaryPrice")
    })
    protected abstract OrderItems map(BasketItemDTO basketItemDTO);
}

