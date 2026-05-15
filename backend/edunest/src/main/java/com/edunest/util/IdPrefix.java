package com.edunest.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks an entity with the metadata needed by {@link CustomIdGenerator}.
 *
 * Place this annotation on the entity class itself (NOT on the field).
 *
 * Example:
 *
 *   @IdPrefix(prefix = "USER", tableName = "users", columnName = "id")
 *   @Entity
 *   public class User { ... }
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface IdPrefix {

    /** Short prefix prepended to the numeric counter.  e.g. "USER", "RES" */
    String prefix();

    /** Database table name to query for MAX(id). */
    String tableName();

    /** Column name that holds the ID.  Almost always "id". */
    String columnName() default "id";
}