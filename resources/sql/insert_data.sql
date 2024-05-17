INSERT INTO Endereco (cep, estado, cidade, bairro, rua, numero, complemento)
VALUES (123456, 'SP', 'São Paulo', 'Vila Mariana', 'Rua Joaquim Távora', '123', 'Apt 45');

INSERT INTO Pessoa (nome, cpf, rg, dataNascimento, celular, email, genero, endereco_id)
VALUES ('Paula Marinho', '123.456.789-09', '123.456.789-Y', '2000-03-25', '1140028922', 'paula.marinho@email.com', 2, 1);

INSERT INTO Funcionario (pessoa_id, emailCorporativo, senha, tipoFuncionario)
VALUES (1, 'paula.marinho@examhub.com', 'password123', 1);




INSERT INTO Endereco (cep, estado, cidade, bairro, rua, numero, complemento)
VALUES (123456, 'SP', 'São Paulo', 'Pinheiros', 'Rua dos Pinheiros', '1234', 'Apto 101');

INSERT INTO Convenio (numeroCarteirinha, prestadora, plano)
VALUES (98765, 'Unimed', 'Plano Ouro');

INSERT INTO HistoricoMedico (alergias, medicamentos, condicaoMedica)
VALUES (NULL, 'Ibuprofeno e Losartana', 'Hipertenssão');

INSERT INTO Pessoa (nome, cpf, rg, dataNascimento, celular, email, genero, endereco_id)
VALUES ('Michele Nonato', '123.456.789-01', '123.456.782-X', '2003-04-15', 11999887766, 'michele.nonato@example.com', 2, 2);

INSERT INTO Paciente (altura, fumante, marcaPasso, convenio_id, historicoMedico_id, pessoa_id)
VALUES (1.65, FALSE, FALSE, 1, 1, 2);
