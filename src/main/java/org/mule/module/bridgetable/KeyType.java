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

public enum KeyType
{
    STRING, INT_NUMBER, LONG_NUMBER, DECIMAL_NUMBER;
    
    /**
     * Transform the String representation of the value
     * based on the type to the corresponding object.
     * @param value
     * @return
     */
    public Object toObject(String value)
    {
        if(value == null)
        {
            return null;
        }
        else if(equals(INT_NUMBER))
        {
            return new Integer(value);
        }
        else if(equals(LONG_NUMBER))
        {
            return new Long(value);
        }
        else if(equals(DECIMAL_NUMBER))
        {
            return new Double(value);
        }
        else
        {
            return new String(value);
        }
    }
    
    /**
     * Transform the object as retrieved from the database to
     * the corresponding value.
     * @param value
     * @return
     */
    public Object fromObject(Object value)
    {
        if(value == null)
        {
            return null;
        }
        else if(equals(INT_NUMBER))
        {
            return new Integer(String.valueOf(value));
        }
        else if(equals(LONG_NUMBER))
        {
            return new Long(String.valueOf(value));
        }
        else if(equals(DECIMAL_NUMBER))
        {
            return new Double(String.valueOf(value));
        }
        else
        {
            return String.valueOf(value);
        }
        
    }
}


