sql.delete.from.partition=DELETE FROM :tableName
sql.check.partition.exists=SELECT 1 FROM pg_catalog.pg_inherits i JOIN pg_catalog.pg_class c ON i.inhrelid = c.oid JOIN pg_catalog.pg_class p ON i.inhparent = p.oid WHERE p.relname = :tableName AND c.relname = :partitionTableName
