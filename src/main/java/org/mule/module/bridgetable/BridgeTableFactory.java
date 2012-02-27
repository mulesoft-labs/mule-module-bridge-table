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

import java.util.Map;

import javax.sql.DataSource;


public class BridgeTableFactory
{

    /**
     * 
     */
    private BridgeTableFactory()
    {
    }

    public static BridgeTable create(Type type, Map<Type, Object> config) throws Exception
    {
        switch(type)
        {
            case DATA_SOURCE: return new DataSourceBridgeTable((DataSource) config.get(type));
            case DEFAULT_VOLATILE: return new InMemoryBridgeTable();
            default: throw new Exception("Unsupported type " + type);
        }
    }
}


