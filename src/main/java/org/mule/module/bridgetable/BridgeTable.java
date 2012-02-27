/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.module.bridgetable;

import java.util.List;

/**
 * A bridge table maps key1 to key2, allowing to do direct and reverse lookups. This mean that knowing key1 you can get the corresponding value
 * for key2 and viceversa. This mapping is 1-1, so just one value of key1 and one value of key2 are allowed.
 */
public interface BridgeTable
{
    /**
     * Initializes an instance of bridge table with all its configuration parameters
     * @param tableName Name of the table. This is both descriptive and in some cases (when backed up by a Database) it can be used to generate a database table.
     * @param key1Type Type of key1. In some cases (when backed up by a Database) it can be used to know the database table column type. 
     * @param key2Type Type of key2. In some cases (when backed up by a Database) it can be used to know the database table column type. 
     * @param key1Name Name of key1. In some cases (when backed up by a Database) it can be used to know the database table column name. 
     * @param key2Name Name of key2. In some cases (when backed up by a Database) it can be used to know the database table column name.
     * @param autoCreateTable If set to <b>true</b> the table will be created if it doesn't exist. Makes no sense for in memory bridge tables, but is useful when using a database bridge table.
     * @throws BridgeTableException In case there is any error.
     */
    void init(String tableName, KeyType key1Type, KeyType key2Type1, String key1Name, String key2Name, boolean autoCreateTable) throws BridgeTableException;
    
    /**
     * Cleans up and frees any resource used by the bridge table instance.
     * @throws BridgeTableException In case there is any error.
     */
    void destroy() throws BridgeTableException;
    
    /**
     * Inserts a key1 to key2 mapping. Both key1 and key2 should be objects accepted by their configured key type.
     * @see org.mule.module.bridgetable.BridgeTable#init(java.lang.String, org.mule.module.bridgetable.KeyType, org.mule.module.bridgetable.KeyType, java.lang.String, java.lang.String, boolean)
     * @param key1 Value for key1 to search an update.
     * @param key2 Value for key2.
     * @throws BridgeTableException If the mapping cannot be inserted. This can be related to the key types or persistence errors.
     */
    void insert(Object key1, Object key2) throws BridgeTableException;
    
    /**
     * Update the value of key2 given the value of key1.
     * @param key1 Value for key1 to search an update.
     * @param newKey2 New value for key2.
     * @throws BridgeTableException If the mapping cannot be updated. This can be related to the key types or persistence errors.
     * @throws KeyDoesNotExistException If key1 is not present in the bridge table
     */
    void updateByKey1(Object key1, Object newKey2) throws BridgeTableException;

    /**
     * Update the value of key1 given the value of key2.
     * @param key2 Value for key2 to search an update.
     * @param newKey1 New value for key1
     * @throws BridgeTableException If the mapping cannot be updated. This can be related to the key types or persistence errors.
     * @throws KeyDoesNotExistException If key2 is not present in the bridge table
     */
    void updateByKey2(Object key2, Object newKey1) throws BridgeTableException;
    
    /**
     * Delete the row for the given value of key1.
     * @param key1 The key1 of the table row to delete
     * @throws BridgeTableException If the mapping cannot be deleted. This can be related to the key types or persistence errors.
     * @throws KeyDoesNotExistException If key1 is not present in the bridge table
     */
    void removeByKey1(Object key1) throws BridgeTableException;
    
    /**
     * Delete the row for the given value of key2.
     * @param key2 The key2 of the table row to delete
     * @throws BridgeTableException If the mapping cannot be deleted. This can be related to the key types or persistence errors.
     * @throws KeyDoesNotExistException If key2 is not present in the bridge table
     */    
    void removeByKey2(Object key2) throws BridgeTableException;
    
    /**
     * Retrieves the value of key2 associated to the given key1.
     * @param key1 Value for key1 to search
     * @return The value for key2
     * @throws BridgeTableException If the value for key2 cannot be retrieved. This can be related to the key types or persistence errors.
     * @throws KeyDoesNotExistException If key1 is not present in the bridge table
     */
    Object retrieveByKey1(Object key1) throws BridgeTableException;
    
    /**
     * Retrieves the value of key1 associated to the given key2.
     * @param key2 Value for key2 to search
     * @return The value for key1
     * @throws BridgeTableException If the value for key1 cannot be retrieved. This can be related to the key types or persistence errors.
     * @throws KeyDoesNotExistException If key2 is not present in the bridge table
     */    
    Object retrieveByKey2(Object key2) throws BridgeTableException;
    
    /**
     * @return Whether this bridge table is persistent or not. 
     */
    boolean isPersistent();
    
    /**
     * Returns <b>true</b> if the bridge table contains a row for key1.
     * @param key1 Value for key1 to search
     * @return Whether the bridge table contains a row matching key1. 
     * @throws BridgeTableException If the mapping cannot be retrieved. This can be related to the key types or persistence errors.
     */
    boolean containsKey1(Object key1) throws BridgeTableException;

    /**
     * Returns <b>true</b> if the bridge table contains a row for key2.
     * @param key2 Value for key2 to search
     * @return Whether the bridge table contains a row matching key2. 
     * @throws BridgeTableException If the mapping cannot be retrieved. This can be related to the key types or persistence errors.
     */
    boolean containsKey2(Object key2) throws BridgeTableException;
    
    /**
     * @return A list of all the values for key1 in the bridge table
     * @throws BridgeTableException If the keys cannot be retrieved. This can be related to the key types or persistence errors.
     */
    List<Object> keys1() throws BridgeTableException;

    /**
     * @return A list of all the values for key2 in the bridge table
     * @throws BridgeTableException If the keys cannot be retrieved. This can be related to the key types or persistence errors.
     */
    List<Object> keys2() throws BridgeTableException;
}


