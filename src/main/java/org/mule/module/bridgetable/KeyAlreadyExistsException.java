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

public class KeyAlreadyExistsException extends BridgeTableException
{

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public KeyAlreadyExistsException()
    {
    }

    /**
     * @param message
     */
    public KeyAlreadyExistsException(String message)
    {
        super(message);
    }

    /**
     * @param cause
     */
    public KeyAlreadyExistsException(Throwable cause)
    {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public KeyAlreadyExistsException(String message, Throwable cause)
    {
        super(message, cause);
    }

}


