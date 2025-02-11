package com.tarapaca.api.shared.infraestructure.helpers;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.tarapaca.api.delivery_order.infrastructure.repository.delivery_order.DeliveryOrderData;
import com.tarapaca.api.delivery_order.infrastructure.repository.delivery_order.DeliveryOrderDataJpaRepository;
import com.tarapaca.api.users.domain.model.UserBasic;
import com.tarapaca.api.users.domain.usecase.UserBasicUseCase;
import com.tarapaca.api.users.infrastructure.helpers.AuditorAwareImpl;

@Component
public class UpdateUtils {
    private final AuditorAwareImpl auditor;
    private final UserBasicUseCase userUseCase;
    private final DeliveryOrderDataJpaRepository deliveryOrderRepository;

    public UpdateUtils(
            AuditorAwareImpl auditor,
            UserBasicUseCase userUseCase,
            DeliveryOrderDataJpaRepository deliveryOrderRepository) {
        this.auditor = auditor;
        this.userUseCase = userUseCase;
        this.deliveryOrderRepository = deliveryOrderRepository;
    }

    public <T> void updateIfNotEmptyAndNotEqual(T newValue, Consumer<T> setter, T currentValue) {
        if (newValue == null) {
            return;
        }

        if (newValue instanceof String newStrValue && newStrValue.trim().isEmpty()) {
            return;
        }

        if (!newValue.equals(currentValue)) {
            setter.accept(newValue);
        }
    }

    public Long getCurrentUserId() {
        return auditor.getCurrentAuditor()
                .orElseThrow(() -> new IllegalStateException("No authenticated user found")).getId();
    }

    public UserBasic getCurrentUser() {
        Long currentUserId = getCurrentUserId();
        return userUseCase.findById(currentUserId, false);
    }

    public DeliveryOrderData getDeliveryOrder(Long id) {
        Optional<DeliveryOrderData> deliveryOrderData = ((CrudRepository<DeliveryOrderData, Long>) deliveryOrderRepository)
                .findById(id);
        return deliveryOrderData.orElseThrow(() -> new NoSuchElementException("Delivery order not found"));
    }
}
