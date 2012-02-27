/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.bridgetable.dialect;

import org.apache.log4j.Logger;
import org.mule.module.bridgetable.KeyType;

public class DatabaseDialectFactory
{
    private static final Logger LOGGER = Logger.getLogger(DatabaseDialectFactory.class);
    /**
     * 
     */
    private DatabaseDialectFactory()
    {
    }

    /**
     * Creates instances of the SQL dialect based on the target database. This method also sets the
     * types for key1 and key2.
     * @param databaseProductName Database product name as obtained from the JDBC connection metadata
     * @param databaseProductVersion Database product version as obtained from the JDBC connection metadata
     * @param key1Type Type for key1
     * @param key2Type Type for key2
     * @return An initialized instance of the database dialect
     */
    public static DatabaseDialect create(String databaseProductName, String databaseProductVersion, KeyType key1Type, KeyType key2Type, String key1Name, String key2Name)
    {
        DatabaseDialect dialect = null;

        LOGGER.debug("Creating dialect for database product name [" + databaseProductName + "] and database product version [" + databaseProductVersion + "]");

        if("MySQL".equalsIgnoreCase(databaseProductName))
        {
            dialect =  new MySQLDialect();
        }

        if(dialect != null)
        {
            LOGGER.debug("Setting key1 [" + key1Name + "] type to [" + key1Type + "] and key2 [" + key2Name + "] type to [" + key2Type + "]");
            dialect.setKeys(key1Type, key2Type, key1Name, key2Name);
            LOGGER.debug("Dialect set to [" + dialect.getClass().getName() + "]");
        }
        else
        {
            LOGGER.warn("No dialect for database product name [" + databaseProductName + "] and database product version [" + databaseProductVersion + "]");
        }
        
        return dialect;
    }

}


