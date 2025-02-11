package com.tarapaca.api.shared.infraestructure.helpers;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

import com.tarapaca.api.delivery_orders.domain.model.EStatus;
import com.tarapaca.api.delivery_orders.domain.model.Status;
import com.tarapaca.api.delivery_orders.domain.usecase.StatusUseCase;

@Component
public class RequestUtils {
    private final StatusUseCase statusUseCase;

    public RequestUtils(StatusUseCase statusUseCase) {
        this.statusUseCase = statusUseCase;
    }

    public static List<Order> getListSortOrders(List<String> sortFields, List<String> sortDirections) {
        return sortFields.stream()
                .map(field -> {
                    Direction sortDirection = Sort.Direction.fromString(sortDirections.get(sortFields.indexOf(field)));
                    return new Order(sortDirection, field);
                })
                .collect(java.util.stream.Collectors.toList());
    }

    public List<Long> getBillableDeliveryOrderStatusIds() {
        List<EStatus> nameList = List.of(EStatus.PENDING, EStatus.PRODUCTION, EStatus.FINISHED, EStatus.DELIVERED);
        List<Status> statusList = statusUseCase.findByNameIn(nameList, false);

        return statusList.stream().map(Status::getId).toList();
    }
}
