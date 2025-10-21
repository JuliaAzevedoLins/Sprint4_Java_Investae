-- Flyway V4: Insert default admin user (if not exists)

-- Insert default admin user only if it doesn't exist
-- Password is 'admin123' encoded with BCrypt (strength 10)
MERGE INTO USUARIO T
USING (
    SELECT 
        'admin' as USERNAME,
        '$2a$10$zJ6lhaY1a6f0E7b/7oXm9uvFnYz4jj0ZiRKrNnWrR4wtLUJnR8zqy' as PASSWORD,
        'Administrador' as NOME,
        'admin@investimentos.com' as EMAIL,
        'ADMIN' as ROLE,
        '11111111111' as CPF
    FROM DUAL
) S ON (T.USERNAME = S.USERNAME)
WHEN NOT MATCHED THEN
    INSERT (USERNAME, PASSWORD, NOME, EMAIL, ROLE, CPF)
    VALUES (S.USERNAME, S.PASSWORD, S.NOME, S.EMAIL, S.ROLE, S.CPF);