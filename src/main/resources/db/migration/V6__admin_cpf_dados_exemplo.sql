-- V6: Associar CPF ao admin e inserir dados de exemplo para demonstração
-- Migração para criar usuário investidor administrativo e dados de amostra

-- 1. Inserir usuário investidor para o admin (CPF fake para demonstração)
INSERT INTO TB_USUARIO_INVESTIMENTO (
    nome, 
    email, 
    cpf_identificacao, 
    cep, 
    endereco, 
    bairro, 
    cidade, 
    uf
) VALUES (
    'Administrador Sistema', 
    'admin@investae.com', 
    '11111111111',  -- CPF fake para admin
    '01310100', 
    'Av. Paulista, 1000', 
    'Bela Vista', 
    'São Paulo', 
    'SP'
);

-- 2. Inserir usuário investidor exemplo
INSERT INTO TB_USUARIO_INVESTIMENTO (
    nome, 
    email, 
    cpf_identificacao, 
    cep, 
    endereco, 
    bairro, 
    cidade, 
    uf
) VALUES (
    'João Silva Exemplo', 
    'joao.silva@exemplo.com', 
    '22222222222',  -- CPF fake para usuário exemplo
    '04567890', 
    'Rua das Flores, 456', 
    'Vila Madalena', 
    'São Paulo', 
    'SP'
);

-- 3. Inserir tipos de investimento (se não existirem)
INSERT INTO TB_TIPO_INVESTIMENTO (nome, descricao, risco, rentabilidade_esperada) 
SELECT 'Poupança', 'Investimento de baixo risco e liquidez diária', 'BAIXO', 6.50
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM TB_TIPO_INVESTIMENTO WHERE nome = 'Poupança');

INSERT INTO TB_TIPO_INVESTIMENTO (nome, descricao, risco, rentabilidade_esperada) 
SELECT 'CDB', 'Certificado de Depósito Bancário - Renda Fixa', 'MEDIO', 12.00
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM TB_TIPO_INVESTIMENTO WHERE nome = 'CDB');

INSERT INTO TB_TIPO_INVESTIMENTO (nome, descricao, risco, rentabilidade_esperada) 
SELECT 'Tesouro Direto', 'Títulos públicos do governo federal', 'BAIXO', 10.50
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM TB_TIPO_INVESTIMENTO WHERE nome = 'Tesouro Direto');

INSERT INTO TB_TIPO_INVESTIMENTO (nome, descricao, risco, rentabilidade_esperada) 
SELECT 'Ações', 'Investimento em ações da bolsa de valores', 'ALTO', 15.80
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM TB_TIPO_INVESTIMENTO WHERE nome = 'Ações');

-- 4. Inserir investimentos de exemplo
-- Primeiro vamos buscar os IDs dos usuários e tipos inseridos
DECLARE
    v_admin_user_id NUMBER;
    v_exemplo_user_id NUMBER;
    v_poupanca_tipo_id NUMBER;
    v_cdb_tipo_id NUMBER;
    v_tesouro_tipo_id NUMBER;
    v_acoes_tipo_id NUMBER;
BEGIN
    -- Buscar IDs dos usuários
    SELECT id_usuario INTO v_admin_user_id 
    FROM TB_USUARIO_INVESTIMENTO WHERE cpf_identificacao = '11111111111';
    
    SELECT id_usuario INTO v_exemplo_user_id 
    FROM TB_USUARIO_INVESTIMENTO WHERE cpf_identificacao = '22222222222';
    
    -- Buscar IDs dos tipos
    SELECT id_tipo INTO v_poupanca_tipo_id FROM TB_TIPO_INVESTIMENTO WHERE nome = 'Poupança';
    SELECT id_tipo INTO v_cdb_tipo_id FROM TB_TIPO_INVESTIMENTO WHERE nome = 'CDB';
    SELECT id_tipo INTO v_tesouro_tipo_id FROM TB_TIPO_INVESTIMENTO WHERE nome = 'Tesouro Direto';
    SELECT id_tipo INTO v_acoes_tipo_id FROM TB_TIPO_INVESTIMENTO WHERE nome = 'Ações';
    
    -- Inserir investimentos do admin
    INSERT INTO TB_INVESTIMENTO (nome, descricao, valor_minimo, rentabilidade, prazo_meses, id_tipo, id_usuario)
    VALUES ('Poupança Banco Central', 'Conta poupança para reserva de emergência', 100.00, 6.50, 0, v_poupanca_tipo_id, v_admin_user_id);
    
    INSERT INTO TB_INVESTIMENTO (nome, descricao, valor_minimo, rentabilidade, prazo_meses, id_tipo, id_usuario)
    VALUES ('CDB Banco do Brasil 120% CDI', 'CDB prefixado com rentabilidade de 120% CDI', 1000.00, 12.00, 24, v_cdb_tipo_id, v_admin_user_id);
    
    INSERT INTO TB_INVESTIMENTO (nome, descricao, valor_minimo, rentabilidade, prazo_meses, id_tipo, id_usuario)
    VALUES ('Tesouro SELIC 2026', 'Título público indexado à SELIC', 500.00, 10.50, 36, v_tesouro_tipo_id, v_admin_user_id);
    
    -- Inserir investimentos do usuário exemplo
    INSERT INTO TB_INVESTIMENTO (nome, descricao, valor_minimo, rentabilidade, prazo_meses, id_tipo, id_usuario)
    VALUES ('Ações VALE3', 'Investimento em ações da Vale', 100.00, 15.80, 60, v_acoes_tipo_id, v_exemplo_user_id);
    
    INSERT INTO TB_INVESTIMENTO (nome, descricao, valor_minimo, rentabilidade, prazo_meses, id_tipo, id_usuario)
    VALUES ('CDB Itaú 110% CDI', 'CDB do Itaú com rendimento de 110% CDI', 500.00, 11.00, 18, v_cdb_tipo_id, v_exemplo_user_id);
END;
/

-- 5. Inserir posições dos usuários nos investimentos (TB_USUARIO_INVESTIMENTO para relacionamento)
DECLARE
    v_admin_user_id NUMBER;
    v_exemplo_user_id NUMBER;
    v_inv_poupanca_id NUMBER;
    v_inv_cdb_bb_id NUMBER;
    v_inv_tesouro_id NUMBER;
    v_inv_vale_id NUMBER;
    v_inv_cdb_itau_id NUMBER;
BEGIN
    -- Buscar IDs dos usuários
    SELECT id_usuario INTO v_admin_user_id 
    FROM TB_USUARIO_INVESTIMENTO WHERE cpf_identificacao = '11111111111';
    
    SELECT id_usuario INTO v_exemplo_user_id 
    FROM TB_USUARIO_INVESTIMENTO WHERE cpf_identificacao = '22222222222';
    
    -- Buscar IDs dos investimentos
    SELECT id_investimento INTO v_inv_poupanca_id 
    FROM TB_INVESTIMENTO WHERE nome = 'Poupança Banco Central';
    
    SELECT id_investimento INTO v_inv_cdb_bb_id 
    FROM TB_INVESTIMENTO WHERE nome = 'CDB Banco do Brasil 120% CDI';
    
    SELECT id_investimento INTO v_inv_tesouro_id 
    FROM TB_INVESTIMENTO WHERE nome = 'Tesouro SELIC 2026';
    
    SELECT id_investimento INTO v_inv_vale_id 
    FROM TB_INVESTIMENTO WHERE nome = 'Ações VALE3';
    
    SELECT id_investimento INTO v_inv_cdb_itau_id 
    FROM TB_INVESTIMENTO WHERE nome = 'CDB Itaú 110% CDI';
    
    -- Inserir posições do admin
    INSERT INTO TB_USUARIO_INVESTIMENTO (id_usuario, id_investimento, valor_investido, data_investimento, data_vencimento, status)
    VALUES (v_admin_user_id, v_inv_poupanca_id, 5000.00, SYSDATE - 30, NULL, 'ATIVO');
    
    INSERT INTO TB_USUARIO_INVESTIMENTO (id_usuario, id_investimento, valor_investido, data_investimento, data_vencimento, status)
    VALUES (v_admin_user_id, v_inv_cdb_bb_id, 15000.00, SYSDATE - 15, ADD_MONTHS(SYSDATE - 15, 24), 'ATIVO');
    
    INSERT INTO TB_USUARIO_INVESTIMENTO (id_usuario, id_investimento, valor_investido, data_investimento, data_vencimento, status)
    VALUES (v_admin_user_id, v_inv_tesouro_id, 25000.00, SYSDATE - 7, ADD_MONTHS(SYSDATE - 7, 36), 'ATIVO');
    
    -- Inserir posições do usuário exemplo
    INSERT INTO TB_USUARIO_INVESTIMENTO (id_usuario, id_investimento, valor_investido, data_investimento, data_vencimento, status)
    VALUES (v_exemplo_user_id, v_inv_vale_id, 8000.00, SYSDATE - 45, ADD_MONTHS(SYSDATE - 45, 60), 'ATIVO');
    
    INSERT INTO TB_USUARIO_INVESTIMENTO (id_usuario, id_investimento, valor_investido, data_investimento, data_vencimento, status)
    VALUES (v_exemplo_user_id, v_inv_cdb_itau_id, 12000.00, SYSDATE - 20, ADD_MONTHS(SYSDATE - 20, 18), 'ATIVO');
END;
/

-- 6. Confirmar transações
COMMIT;