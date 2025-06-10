#!/bin/bash

# Create databases if they don't exist
mysql -h 127.0.0.1 -u root -proot -e "
CREATE DATABASE IF NOT EXISTS usercenter;
CREATE DATABASE IF NOT EXISTS tradecenter;
CREATE DATABASE IF NOT EXISTS lgcenter;
CREATE DATABASE IF NOT EXISTS itemcenter;
CREATE DATABASE IF NOT EXISTS paygw;
"

# Execute SQL scripts
mysql -h 127.0.0.1 -u root -proot < db_usercenter.sql
mysql -h 127.0.0.1 -u root -proot < db_itemcenter.sql
mysql -h 127.0.0.1 -u root -proot < db_tradecenter.sql
mysql -h 127.0.0.1 -u root -proot < db_lgcenter.sql
mysql -h 127.0.0.1 -u root -proot < db_paygw.sql

echo "Database setup completed successfully!" 