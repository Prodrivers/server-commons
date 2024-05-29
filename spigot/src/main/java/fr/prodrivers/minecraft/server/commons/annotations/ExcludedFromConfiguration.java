package fr.prodrivers.minecraft.server.commons.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Exclude a field from field-based configurations
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcludedFromConfiguration {
}
