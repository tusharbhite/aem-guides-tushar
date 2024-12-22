package com.tushar.aem.guides.core.configurations.learning.factory;

import java.util.List;

public interface OSGiFactoryConfig {
    public int getConfigID();
    public String getServiceName();
    public String getServiceURL();
    public OSGiFactoryConfig get(int configID);
    public List<OSGiFactoryConfig> getAllConfigs();
}