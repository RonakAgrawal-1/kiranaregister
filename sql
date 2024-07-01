sql.delete.from.partition=DELETE FROM :tableName
sql.check.partition.exists=SELECT 1 FROM pg_catalog.pg_inherits i JOIN pg_catalog.pg_class c ON i.inhrelid = c.oid JOIN pg_catalog.pg_class p ON i.inhparent = p.oid WHERE p.relname = :tableName AND c.relname = :partitionTableName

SELECT table_name, table_type
FROM information_schema.tables
WHERE table_schema = 'public'  -- Replace with your schema name
  AND table_name = 'my_table'
  AND table_type = 'BASE TABLE';

SELECT 
    COUNT(*) AS partition_exists
FROM 
    information_schema.tables
WHERE 
    table_schema = SCHEMA()  -- use the current schema
    AND table_name = 'your_partition_table_name';  -- replace with your partition table name
