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

public class KeyDoesNotExistException extends BridgeTableException
{

    private static final long serialVersionUID = 4009098240942456285L;

    /**
     * 
     */
    public KeyDoesNotExistException()
    {
    }

    /**
     * @param message
     */
    public KeyDoesNotExistException(String message)
    {
        super(message);
    }

    /**
     * @param cause
     */
    public KeyDoesNotExistException(Throwable cause)
    {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public KeyDoesNotExistException(String message, Throwable cause)
    {
        super(message, cause);
    }

}


