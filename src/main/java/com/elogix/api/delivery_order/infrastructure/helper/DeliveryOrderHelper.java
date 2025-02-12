package com.elogix.api.delivery_order.infrastructure.helper;

public abstract class DeliveryOrderHelper {
    public static final String CREATE_DELIVERY_ORDER = "hasAnyAuthority('commercial:create', 'commercial:*') " +
            "or hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_GLOBAL_MANAGER')" +
            "or hasAnyRole('ROLE_MANAGER_COMMERCIAL')";
    public static final String UPDATE_DELIVERY_ORDER = "hasAnyAuthority('commercial:update', 'commercial:*')" +
            "or hasAnyAuthority('production:update', 'production:*')" +
            "or hasAnyAuthority('dispatch:update', 'dispatch:*') " +
            "or hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_GLOBAL_MANAGER')" +
            "or hasAnyRole('ROLE_MANAGER_COMMERCIAL', 'ROLE_MANAGER_PRODUCTION', 'ROLE_MANAGER_DISPATCH')";
    public static final String DELETE_DELIVERY_ORDER = "hasAnyAuthority('commercial:delete', 'commercial:*')" +
            "or hasAnyAuthority('production:delete', 'production:*')" +
            "or hasAnyAuthority('dispatch:delete', 'dispatch:*') " +
            "or hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_GLOBAL_MANAGER')" +
            "or hasAnyRole('ROLE_MANAGER_COMMERCIAL', 'ROLE_MANAGER_PRODUCTION', 'ROLE_MANAGER_DISPATCH')";
}
