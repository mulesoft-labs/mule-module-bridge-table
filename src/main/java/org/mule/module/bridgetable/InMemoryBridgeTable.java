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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Concrete implementation of a bridge table that uses two maps in memory to store key mappings.
 */
public class InMemoryBridgeTable implements BridgeTable
{
    private Object lock = new Object();
    
    // key1 => key2 mapping
    private Map<Object, Object> direct;
    // key2 => key1 mapping
    private Map<Object, Object> reverse;
    
    private String tableName;
    /**
     * 
     */
    public InMemoryBridgeTable()
    {
    }

    /**
     * @see org.mule.module.bridgetable.BridgeTable#init(java.lang.String, org.mule.module.bridgetable.KeyType, org.mule.module.bridgetable.KeyType, java.lang.String, java.lang.String, boolean)
     */
    @Override
    public void init(String tableName,
                     KeyType key1Type,
                     KeyType key2Type1,
                     String key1Name,
                     String key2Name,
                     boolean autoCreateTable) throws BridgeTableException
    {
        this.tableName = tableName;
        this.direct = new HashMap<Object, Object>();
        this.reverse = new HashMap<Object, Object>();
    }

    /**
     * @see org.mule.module.bridgetable.BridgeTable#destroy()
     */
    @Override
    public void destroy() throws BridgeTableException
    {
        this.direct.clear();
        this.reverse.clear();
        this.direct = null;
        this.reverse = null;
    }

    /**
     * @see org.mule.module.bridgetable.BridgeTable#insert(java.lang.Object, java.lang.Object)
     */
    @Override
    public void insert(Object key1, Object key2) throws BridgeTableException
    {
        synchronized (this.lock)
        {
            if(containsKey1(key1) && containsKey2(key2))
            {
                throw new KeyAlreadyExistsException("The row [key1, key2] = [" + key1 + ", " + key2 + "] already exists in " + this.tableName);
            }
            else if(containsKey1(key1))
            {
                throw new KeyAlreadyExistsException("The value [key1] = [" + key1 + "] already exists in " + this.tableName);
            }
            else if(containsKey2(key2))
            {
                throw new KeyAlreadyExistsException("The value [key2] = [" + key2 + "] already exists in " + this.tableName);
            }
            else
            {
                this.direct.put(key1, key2);
                this.reverse.put(key2, key1);
            }
        }
    }

    /**
     * @see org.mule.module.bridgetable.BridgeTable#updateByKey1(java.lang.Object, java.lang.Object)
     */
    @Override
    public void updateByKey1(Object key1, Object newKey2) throws BridgeTableException
    {
        synchronized (this.lock)
        {        
            if(!containsKey1(key1))
            {
                throw new KeyDoesNotExistException("The value [key1] = [" + key1 + "] does not exist in " + this.tableName);
            }
            else
            {
                Object oldKey2 = this.direct.get(key1);
                this.direct.put(key1, newKey2);
                this.reverse.remove(oldKey2);
                this.reverse.put(newKey2, key1);
            }
        }
    }

    /**
     * @see org.mule.module.bridgetable.BridgeTable#updateByKey2(java.lang.Object, java.lang.Object)
     */
    @Override
    public void updateByKey2(Object key2, Object newKey1) throws BridgeTableException
    {
        synchronized (this.lock)
        {        
            if(!containsKey2(key2))
            {
                throw new KeyDoesNotExistException("The value [key2] = [" + key2 + "] does not exist in " + this.tableName);
            }
            else
            {
                Object oldKey1 = this.reverse.get(key2);
                this.reverse.put(key2, newKey1);
                this.direct.remove(oldKey1);
                this.direct.put(newKey1, key2);
            }
        }        
    }

    /**
     * @see org.mule.module.bridgetable.BridgeTable#removeByKey1(java.lang.Object)
     */
    @Override
    public void removeByKey1(Object key1) throws BridgeTableException
    {
        synchronized (this.lock)
        {        
            if(!containsKey1(key1))
            {
                throw new KeyDoesNotExistException("The value [key1] = [" + key1 + "] does not exist in " + this.tableName);
            }
            else
            {
                Object key2 = this.direct.remove(key1);
                this.reverse.remove(key2);
            }
        }        
    }

    /**
     * @see org.mule.module.bridgetable.BridgeTable#removeByKey2(java.lang.Object)
     */
    @Override
    public void removeByKey2(Object key2) throws BridgeTableException
    {
        synchronized (this.lock)
        {        
            if(!containsKey2(key2))
            {
                throw new KeyDoesNotExistException("The value [key2] = [" + key2 + "] does not exist in " + this.tableName);
            }
            else
            {
                Object key1 = this.reverse.remove(key2);
                this.direct.remove(key1);
            }
        }         
    }

    /**
     * @see org.mule.module.bridgetable.BridgeTable#retrieveByKey1(java.lang.Object)
     */
    @Override
    public Object retrieveByKey1(Object key1) throws BridgeTableException
    {
        if(!containsKey1(key1))
        {
            throw new KeyDoesNotExistException("The value [key1] = [" + key1 + "] does not exist in " + this.tableName);
        }
        else
        {
            return this.direct.get(key1);
        }
    }

    /**
     * @see org.mule.module.bridgetable.BridgeTable#retrieveByKey2(java.lang.Object)
     */
    @Override
    public Object retrieveByKey2(Object key2) throws BridgeTableException
    {
        if(!containsKey2(key2))
        {
            throw new KeyDoesNotExistException("The value [key2] = [" + key2 + "] does not exist in " + this.tableName);
        }
        else
        {
            return this.reverse.get(key2);
        }
    }

    /**
     * @see org.mule.module.bridgetable.BridgeTable#isPersistent()
     */
    @Override
    public boolean isPersistent()
    {
        return false;
    }

    /**
     * @see org.mule.module.bridgetable.BridgeTable#containsKey1(java.lang.Object)
     */
    @Override
    public boolean containsKey1(Object key1) throws BridgeTableException
    {
        return this.direct.containsKey(key1);
    }

    /**
     * @see org.mule.module.bridgetable.BridgeTable#containsKey2(java.lang.Object)
     */
    @Override
    public boolean containsKey2(Object key2) throws BridgeTableException
    {
        return this.reverse.containsKey(key2);
    }

    /**
     * @see org.mule.module.bridgetable.BridgeTable#keys1()
     */
    @Override
    public List<Object> keys1() throws BridgeTableException
    {
        return new ArrayList<Object>(this.direct.keySet());
    }

    /**
     * @see org.mule.module.bridgetable.BridgeTable#keys2()
     */
    @Override
    public List<Object> keys2() throws BridgeTableException
    {
        return new ArrayList<Object>(this.reverse.keySet());
    }
}


