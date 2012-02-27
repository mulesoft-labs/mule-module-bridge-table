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

public class BridgeTableException extends Exception
{

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public BridgeTableException()
    {
    }

    /**
     * @param message
     */
    public BridgeTableException(String message)
    {
        super(message);
    }

    /**
     * @param cause
     */
    public BridgeTableException(Throwable cause)
    {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public BridgeTableException(String message, Throwable cause)
    {
        super(message, cause);
    }

}


