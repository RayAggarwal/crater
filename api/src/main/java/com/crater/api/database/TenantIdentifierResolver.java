package com.crater.api.database;

import com.crater.api.database.TenantContext;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    private String defaultTenant;

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenantId = TenantContext.getCurrentTenant();
        if (StringUtils.isEmpty(tenantId)) {
            return defaultTenant;
        }
        return tenantId;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
