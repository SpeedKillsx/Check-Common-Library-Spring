package com.tonorganisation.checkcommonlib.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for auditing functionality in the application.
 * This class is used to enable or disable auditing features globally
 * based on the property prefixed with "check-common".
 *
 * The auditing feature can be toggled using the "auditEnable" field, which
 * can be configured in the application's properties file.
 *
 * Example configuration key:
 * - check-common.audit-enable: true/false
 *
 * This property is primarily utilized in aspects or components that handle
 * auditing logic, such as tracking method execution or generating audit logs.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "check-common")
public class AuditProperties {
    private boolean auditEnable;
}
