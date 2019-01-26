package com.baeldung.daopattern.config;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import javax.sql.DataSource;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import org.hibernate.jpa.HibernatePersistenceProvider;

public class PersistenceUnitInfoImpl implements PersistenceUnitInfo {
    
    public static final String JPA_VERSION = "2.1";
    private final String persistenceUnitName;
    private PersistenceUnitTransactionType transactionType = PersistenceUnitTransactionType.RESOURCE_LOCAL;
    private final List<String> managedClassNames;
    private final List<String> mappingFileNames = new ArrayList<>();
    private final Properties properties;
    private DataSource jtaDataSource;
    private DataSource nonJtaDataSource;
    
    public PersistenceUnitInfoImpl(String persistenceUnitName, List<String> managedClassNames, Properties properties) {
        this.persistenceUnitName = persistenceUnitName;
        this.managedClassNames = managedClassNames;
        this.properties = properties;
    }
    
    @Override
    public String getPersistenceUnitName() {
        return persistenceUnitName;
    }
 
    @Override
    public String getPersistenceProviderClassName() {
        return HibernatePersistenceProvider.class.getName();
    }
 
    @Override
    public PersistenceUnitTransactionType getTransactionType() {
        return transactionType;
    }
 
    @Override
    public DataSource getJtaDataSource() {
        return jtaDataSource;
    }
 
    public PersistenceUnitInfoImpl setJtaDataSource(DataSource jtaDataSource) {
        this.jtaDataSource = jtaDataSource;
        this.nonJtaDataSource = null;
        transactionType = PersistenceUnitTransactionType.JTA;
        return this;
    }
 
    @Override
    public DataSource getNonJtaDataSource() {
        return nonJtaDataSource;
    }
 
    public PersistenceUnitInfoImpl setNonJtaDataSource(DataSource nonJtaDataSource) {
        this.nonJtaDataSource = nonJtaDataSource;
        this.jtaDataSource = null;
        transactionType = PersistenceUnitTransactionType.RESOURCE_LOCAL;
        return this;
    }
 
    @Override
    public List<String> getMappingFileNames() {
        return mappingFileNames;
    }
 
    @Override
    public List<URL> getJarFileUrls() {
        return Collections.emptyList();
    }
 
    @Override
    public URL getPersistenceUnitRootUrl() {
        return null;
    }
 
    @Override
    public List<String> getManagedClassNames() {
        return managedClassNames;
    }
 
    @Override
    public boolean excludeUnlistedClasses() {
        return false;
    }
 
    @Override
    public SharedCacheMode getSharedCacheMode() {
        return SharedCacheMode.UNSPECIFIED;
    }
 
    @Override
    public ValidationMode getValidationMode() {
        return ValidationMode.AUTO;
    }
 
    public Properties getProperties() {
        return properties;
    }
 
    @Override
    public String getPersistenceXMLSchemaVersion() {
        return JPA_VERSION;
    }
 
    @Override
    public ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
 
    @Override
    public void addTransformer(ClassTransformer transformer) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public ClassLoader getNewTempClassLoader() {
        return null;
    }
}