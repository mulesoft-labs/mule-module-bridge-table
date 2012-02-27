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
import java.sql.SQLIntegrityConstraintViolationException;

import org.apache.log4j.Logger;
import org.mule.module.bridgetable.BridgeTableException;
import org.mule.module.bridgetable.KeyAlreadyExistsException;
import org.mule.module.bridgetable.KeyType;

public class MySQLDialect implements DatabaseDialect
{
    private static final Logger LOGGER = Logger.getLogger(MySQLDialect.class);
    
    private KeyType key1Type;
    private KeyType key2Type;
    private String key1Name;
    private String key2Name;
    
    /**
     * 
     */
    public MySQLDialect()
    {
    }

    /*
     * Maps types to MySQL table column types
     */
    private String keySQLType2ColumnType(KeyType type)
    {
        String sqlType = null;
        switch(type)
        {
            case STRING: sqlType = "VARCHAR(255)"; break;
            case INT_NUMBER: sqlType = "INTEGER"; break;
            case LONG_NUMBER: sqlType = "BIGINT"; break;
            case DECIMAL_NUMBER: sqlType = "DOUBLE"; break;
            default: sqlType = "VARCHAR(255)"; break;
        }
        if(LOGGER.isDebugEnabled())
        {
            LOGGER.debug("SQL type for " + type + " is " + sqlType);
        }
        return sqlType;
    }
    
    private String getKey1SQLColumnName() 
    {
        return key1Name;
    }

    private String getKey2SQLColumnName() 
    {
        return key2Name;
    }
    
    /**
     * @see org.mule.module.bridgetable.dialect.DatabaseDialect#getCreateTableSQL(java.lang.String)
     */
    @Override
    public String getCreateTableSQL(String tableName)
    {
        if(tableName == null) throw new IllegalArgumentException("Table name cannot be null");
        
        StringBuilder sql = new StringBuilder();
        
        sql.append("CREATE TABLE IF NOT EXISTS ")
           .append(tableName)
           .append(" (")
           .append(getKey1SQLColumnName() + " " + keySQLType2ColumnType(key1Type) + " UNIQUE, ")
           .append(getKey2SQLColumnName() + " " + keySQLType2ColumnType(key2Type) + " UNIQUE, ")
           .append("creation_date TIMESTAMP default now(), ")
           .append("PRIMARY KEY (" + getKey1SQLColumnName() + ", " + getKey2SQLColumnName() + "), ")
           .append("INDEX key1_index (" + getKey1SQLColumnName() + "), ")
           .append("INDEX key2_index (" + getKey2SQLColumnName() + ")")
           .append(")");

        if(LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Create table statement: " + sql.toString());
        }
        
        return sql.toString();
    }

    /**
     * @see org.mule.module.bridgetable.dialect.DatabaseDialect#getInsertSQL(java.lang.String)
     */
    @Override
    public String getInsertSQL(String tableName)
    {
        if(tableName == null) throw new IllegalArgumentException("Table name cannot be null");
        String sql = "INSERT INTO " + tableName + " (" + getKey1SQLColumnName() + ", " + getKey2SQLColumnName() + ") VALUES (?, ?)";

        if(LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Insert statement: " + sql);
        }
        return sql;
    }

    /**
     * @see org.mule.module.bridgetable.dialect.DatabaseDialect#getUpdateByKey1SQL(java.lang.String)
     */
    @Override
    public String getUpdateByKey1SQL(String tableName)
    {
        if(tableName == null) throw new IllegalArgumentException("Table name cannot be null");
        String sql = "UPDATE " + tableName + " SET " + getKey2SQLColumnName() + " = ? WHERE " + getKey1SQLColumnName() + " = ?";
        
        if(LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Update by key1 statement: " + sql);
        }
        return sql;        
    }

    /**
     * @see org.mule.module.bridgetable.dialect.DatabaseDialect#getUpdateByKey2SQL(java.lang.String)
     */
    @Override
    public String getUpdateByKey2SQL(String tableName)
    {
        if(tableName == null) throw new IllegalArgumentException("Table name cannot be null");
        String sql = "UPDATE " + tableName + " SET " + getKey1SQLColumnName() + " = ? WHERE " + getKey2SQLColumnName() + " = ?";
        
        if(LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Update by key2 statement: " + sql);
        }
        return sql;             
    }

    /**
     * @see org.mule.module.bridgetable.dialect.DatabaseDialect#getLookupByKey1SQL(java.lang.String)
     */
    @Override
    public String getLookupByKey1SQL(String tableName)
    {
        if(tableName == null) throw new IllegalArgumentException("Table name cannot be null");
        String sql = "SELECT " + getKey2SQLColumnName() + " FROM " + tableName + " WHERE " + getKey1SQLColumnName() + " = ?";

        if(LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Lookup by key1 statement: " + sql);
        }
        return sql;             
    }

    /**
     * @see org.mule.module.bridgetable.dialect.DatabaseDialect#getLookupByKey2SQL(java.lang.String)
     */
    @Override
    public String getLookupByKey2SQL(String tableName)
    {
        if(tableName == null) throw new IllegalArgumentException("Table name cannot be null");
        String sql = "SELECT " + getKey1SQLColumnName() + " FROM " + tableName + " WHERE " + getKey2SQLColumnName() + " = ?";

        if(LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Lookup by key2 statement: " + sql);
        }
        return sql;           
    }

    /**
     * @see org.mule.module.bridgetable.dialect.DatabaseDialect#getDeleteByKey1SQL(java.lang.String)
     */
    @Override
    public String getDeleteByKey1SQL(String tableName)
    {
        if(tableName == null) throw new IllegalArgumentException("Table name cannot be null");
        String sql = "DELETE FROM " + tableName + " WHERE " + getKey1SQLColumnName() + " = ?";
        
        if(LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Delete by key1 statement: " + sql);
        }
        return sql;           
    }

    /**
     * @see org.mule.module.bridgetable.dialect.DatabaseDialect#getDeleteByKey2SQL(java.lang.String)
     */
    @Override
    public String getDeleteByKey2SQL(String tableName)
    {
        if(tableName == null) throw new IllegalArgumentException("Table name cannot be null");
        String sql = "DELETE FROM " + tableName + " WHERE " + getKey2SQLColumnName() + " = ?";

        if(LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Delete by key2 statement: " + sql);
        }
        return sql;           
    }

    /**
     * @see org.mule.module.bridgetable.dialect.DatabaseDialect#getAllKey1SQL(java.lang.String)
     */
    @Override
    public String getAllKey1SQL(String tableName)
    {
        if(tableName == null) throw new IllegalArgumentException("Table name cannot be null");
        String sql = "SELECT " + getKey1SQLColumnName() + " FROM " + tableName + " ORDER BY creation_date ASC";
        
        if(LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Get all key1 statement: " + sql);
        }
        return sql;           
    }

    /**
     * @see org.mule.module.bridgetable.dialect.DatabaseDialect#getAllKey2SQL(java.lang.String)
     */
    @Override
    public String getAllKey2SQL(String tableName)
    {
        if(tableName == null) throw new IllegalArgumentException("Table name cannot be null");
        String sql = "SELECT " + getKey2SQLColumnName() + " FROM " + tableName + " ORDER BY creation_date ASC";

        if(LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Get all key2 statement: " + sql);
        }
        return sql;           
    }

    /**
     * @see org.mule.module.bridgetable.dialect.DatabaseDialect#setKeyTypes(org.mule.module.bridgetable.KeyType, org.mule.module.bridgetable.KeyType)
     */
    @Override
    public void setKeys(KeyType key1Type, KeyType key2Type, String key1Name, String key2Name)
    {
        this.key1Type = key1Type;
        this.key2Type = key2Type;
        this.key1Name = key1Name;
        this.key2Name = key2Name;
    }

    /**
     * @see org.mule.module.bridgetable.dialect.DatabaseDialect#translateException(java.sql.SQLException, java.lang.String)
     */
    @Override
    public BridgeTableException translateException(SQLException sqlEx, String message)
    {
        if(LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Received exception: " + sqlEx.getClass().getName());
        }
        BridgeTableException ex = null;

        if(sqlEx instanceof SQLIntegrityConstraintViolationException)
        {
            ex = new KeyAlreadyExistsException(message, sqlEx);
        }
        else
        {
            ex = new BridgeTableException(message, sqlEx);
        }
        
        return ex;
    }

}


