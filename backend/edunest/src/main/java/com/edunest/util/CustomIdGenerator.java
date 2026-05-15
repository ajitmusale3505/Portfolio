package com.edunest.util;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Generates custom human-readable IDs for each entity table.
 *
 * Format:  PREFIX + zero-padded 4-digit number
 * Examples:
 *   USER1000, USER1001, USER1002 ...
 *   RES1000,  RES1001  ...
 *   POST1000, POST1001 ...
 *   ANS1000,  ANS1001  ...
 *   UPD1000,  UPD1001  ...
 *   CUR1000,  CUR1001  ...
 *   NOTIF1000, NOTIF1001 ...
 *   VOTE1000, VOTE1001  ...
 *   RATING1000 ...
 *   SAVED1000  ...
 *
 * How it works:
 *   1. Queries the target table for MAX(id) using a LIKE filter on the prefix.
 *   2. Parses the numeric suffix of the last ID.
 *   3. Returns prefix + (lastNumber + 1).
 *   4. Starts at 1000 if the table is empty.
 *
 * Usage — annotate the @Id field in each entity:
 *
 *   @Id
 *   @GeneratedValue(generator = "custom-id")
 *   @GenericGenerator(
 *       name       = "custom-id",
 *       type       = CustomIdGenerator.class,
 *       parameters = {
 *           @Parameter(name = "prefix",     value = "USER"),
 *           @Parameter(name = "tableName",  value = "users"),
 *           @Parameter(name = "columnName", value = "id")
 *       }
 *   )
 *   private String id;
 */
public class CustomIdGenerator implements IdentifierGenerator {

    // ---------------------------------------------------------------
    // Constants
    // ---------------------------------------------------------------
    /** First numeric value used when the table is empty. */
    private static final int START_VALUE = 1000;

    // ---------------------------------------------------------------
    // IdentifierGenerator contract
    // ---------------------------------------------------------------

    @Override
    public Serializable generate(SharedSessionContractImplementor session,
                                  Object object) {

        // Resolve parameters supplied via @GenericGenerator
        String prefix     = resolvePrefix(object);
        String tableName  = resolveTableName(object);
        String columnName = resolveColumnName(object);

        try {
            Connection connection = session.getJdbcConnectionAccess()
                                           .obtainConnection();

            // Query for the highest existing ID with this prefix
            String sql = String.format(
                "SELECT MAX(%s) FROM %s WHERE %s LIKE '%s%%'",
                columnName, tableName, columnName, prefix
            );

            Statement stmt      = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);

            if (resultSet.next()) {
                String maxId = resultSet.getString(1);

                if (maxId == null) {
                    // Table is empty — start at 1000
                    return prefix + START_VALUE;
                }

                // Strip the prefix to get the numeric part, then increment
                String numericPart = maxId.replace(prefix, "");
                int    nextNumber  = Integer.parseInt(numericPart) + 1;
                return prefix + nextNumber;
            }

        } catch (Exception e) {
            throw new RuntimeException(
                "CustomIdGenerator: failed to generate ID for table ["
                + tableName + "] with prefix [" + prefix + "]", e
            );
        }

        // Fallback — should never reach here
        return prefix + START_VALUE;
    }

    // ---------------------------------------------------------------
    // Private helpers — extract metadata from the entity class
    // ---------------------------------------------------------------

    /**
     * Reads the @IdPrefix annotation from the entity class to get the prefix.
     * Falls back to "ID" if the annotation is missing.
     */
    private String resolvePrefix(Object object) {
        IdPrefix ann = object.getClass().getAnnotation(IdPrefix.class);
        return (ann != null) ? ann.prefix() : "ID";
    }

    private String resolveTableName(Object object) {
        IdPrefix ann = object.getClass().getAnnotation(IdPrefix.class);
        return (ann != null) ? ann.tableName() : object.getClass().getSimpleName().toLowerCase();
    }

    private String resolveColumnName(Object object) {
        IdPrefix ann = object.getClass().getAnnotation(IdPrefix.class);
        return (ann != null) ? ann.columnName() : "id";
    }
}