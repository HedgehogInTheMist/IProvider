package com.epam.inet.provider.service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hedgehog on 17.06.2016.
 */
public final class ServiceFactory {

    private static volatile ServiceFactory serviceFactory = new ServiceFactory();
    private Map<ServiceType, Service> serviceMap;

    private ServiceFactory() {
        serviceMap = new HashMap<>(3);
        serviceMap.put(ServiceType.AUTHENTICATION, new AuthenticationService());
        serviceMap.put(ServiceType.CLIENT, new ClientService());
        serviceMap.put(ServiceType.ORDER, new OrderService());
        serviceMap.put(ServiceType.TARIFF, new TariffService());
    }

    public static ServiceFactory getServiceFactory() {
        return serviceFactory;
    }

    public Service getService(ServiceType type) {
        return serviceMap.get(type);
    }

    public Service getService(String type) {
        return serviceMap.get(ServiceType.valueOf(type.toUpperCase()));
    }

    public enum ServiceType {AUTHENTICATION, CLIENT, ORDER, TARIFF};




}
