#!/bin/bash

# Database credentials
DB_USER="root"
DB_PASS="hot365fm"
DB_HOST="localhost"

# Create the user center database
echo "Creating user center database..."
mysql -h $DB_HOST -u $DB_USER -p$DB_PASS << EOF
CREATE DATABASE IF NOT EXISTS usercenter DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
EOF

# Create the trade center database
echo "Creating trade center database..."
mysql -h $DB_HOST -u $DB_USER -p$DB_PASS << EOF
CREATE DATABASE IF NOT EXISTS tradecenter DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
EOF

# Create the logistics center database
echo "Creating logistics center database..."
mysql -h $DB_HOST -u $DB_USER -p$DB_PASS << EOF
CREATE DATABASE IF NOT EXISTS lgcenter DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
EOF

# Create the item center database
echo "Creating item center database..."
mysql -h $DB_HOST -u $DB_USER -p$DB_PASS << EOF
CREATE DATABASE IF NOT EXISTS itemcenter DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
EOF

# Create the payment gateway database
echo "Creating payment gateway database..."
mysql -h $DB_HOST -u $DB_USER -p$DB_PASS << EOF
CREATE DATABASE IF NOT EXISTS paygw DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
EOF

echo "All databases created successfully."
echo "To initialize database schemas, run each application with spring.jpa.hibernate.ddl-auto=create" 