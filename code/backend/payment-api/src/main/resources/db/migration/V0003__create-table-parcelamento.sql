create table if not exists parcelamento (
    id SERIAL PRIMARY KEY,
    descricao TEXT,
    valorTotal NUMERIC(10, 2) NOT NULL,
    quantidadeParcelas INT NOT NULL,
    dataCriacao DATE NOT NULL DEFAULT CURRENT_DATE,
    cliente_id INT REFERENCES cliente(cliente_id)
);