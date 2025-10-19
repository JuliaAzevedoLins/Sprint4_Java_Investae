-- Flyway V5: Update admin password
-- Updates the admin user password to the correct BCrypt hash

UPDATE USUARIO
SET PASSWORD = '$2b$10$zJ6lhaY1a6f0E7b/7oXm9uvFnYz4jj0ZiRKrNnWrR4wtLUJnR8zqy'
WHERE USERNAME = 'admin';

COMMIT;