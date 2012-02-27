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

import java.sql.SQLException;

import org.mule.module.bridgetable.BridgeTableException;
import org.mule.module.bridgetable.KeyType;

public interface DatabaseDialect
{
    /**
     * Sets the types and names for each key in the bridge table. The type will be
     * used to create the table with the correct SQL type for each column and the name
     * to set the name of the column.
     * 
     * @param key1Type The type of key1
     * @param key2Type The type of key2
     * @param key1Name The name of key1
     * @param key2Name The name of key2
     * 
     */
    void setKeys(KeyType key1Type, KeyType key2Type, String key1Name, String key2Name);
    
    /**
     * Returns the DLL statement to create the bridge table taking into
     * account the table name and the column types.
     * This script should create indexes if required and should ensure that
     * both key1 and key2 are unique. This means that all relations are 1 to 1
     * and there cannot be two different values for key2 associated with the same
     * key1 (and viceversa)
     * 
     * @param tableName The name of the table
     * @return The DLL statement
     */
    String getCreateTableSQL(String tableName);
    
    /**
     * Returns the SQL statement to insert a row in the bridge table.
     * A row is represented by the relationship <b>key1 <-> key2</b>.
     * 
     * It will return a statement with 2 replacement parameters (?): the first one for key1 and the
     * second one for key2
     * 
     * @param tableName The name of the table
     * @return The insert SQL statement
     */
    String getInsertSQL(String tableName);
    
    /**
     * Returns the SQL statement to update the value of the key2 that is linked to key1.
     * 
     * It will return a statement with 2 replacement parameters (?): the first one for the new
     * value of key2 and the second on for key1
     * 
     * @param tableName The name of the table
     * @return The update SQL statement
     */
    String getUpdateByKey1SQL(String tableName);

    /**
     * Returns the SQL statement to update the value of the key1 that is linked to key2.
     * 
     * It will return a statement with 2 replacement parameters (?): the first one for the new
     * value of key1 and the second on for key2
     * 
     * @param tableName The name of the table
     * @return The update SQL statement
     */    
    String getUpdateByKey2SQL(String tableName);

    /**
     * Returns the SQL statement to select the value of the key2 that is linked to key1.
     * 
     * It will return a statement with 1 replacement parameters (?) for key1.
     * 
     * The first attribute in the SELECT clause should be the value of key2.
     * 
     * @param tableName The name of the table
     * @return The select SQL statement
     */    
    String getLookupByKey1SQL(String tableName);

    /**
     * Returns the SQL statement to select the value of the key1 that is linked to key2.
     * 
     * It will return a statement with 1 replacement parameters (?) for key2.
     * 
     * The first attribute in the SELECT clause should be the value of key1.
     * 
     * @param tableName The name of the table
     * @return The select SQL statement
     */        
    String getLookupByKey2SQL(String tableName);
    
    /**
     * Returns the SQL statement to delete the row associated with key1
     * 
     * It will return a statement with 1 replacement parameters (?) for key1.
     * 
     * @param tableName The name of the table
     * @return The delete SQL statement
     */      
    String getDeleteByKey1SQL(String tableName);
    
    /**
     * Returns the SQL statement to delete the row associated with key2
     * 
     * It will return a statement with 1 replacement parameters (?) for key2.
     * 
     * @param tableName The name of the table
     * @return The delete SQL statement
     */    
    String getDeleteByKey2SQL(String tableName);
    
    /**
     * Returns all values for key1.
     * 
     * The first attribute in the SELECT clause should be the value of key1.
     * @param tableName The name of the table
     * @return The select SQL statement
     */
    String getAllKey1SQL(String tableName);

    /**
     * Returns all values for key2.
     * 
     * The first attribute in the SELECT clause should be the value of key2.
     * @param tableName The name of the table
     * @return The select SQL statement
     */
    String getAllKey2SQL(String tableName);

    /**
     * Translates a specific database related exception to a bridge table module
     * exception. Take special care to map KeyAlreadyExistsException.
     * @param ex The SQL exception
     * @param message What were we doing when the SQL exception was thrown
     * @return The bridge table module exception
     */
    BridgeTableException translateException(SQLException ex, String message);
}


