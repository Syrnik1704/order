package com.example.order.mappers;

import com.example.order.entity.Deliver;
import com.example.order.entity.DeliverDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

@Mapper
public abstract class DeliverToDeliverDTOMapper {

    public DeliverDTO mapToDeliverDTO(Deliver deliver){
        return map(deliver);
    }


    @Mappings({})
    protected abstract DeliverDTO map(Deliver deliver);
}

