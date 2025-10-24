CREATE TABLE Utente (
    id_utente INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(20),
    cognome VARCHAR(20),
    username VARCHAR(20),
    password CHAR(64)
);

CREATE TABLE Conto (
    id_conto INT AUTO_INCREMENT PRIMARY KEY,
    ref_utente INT NOT NULL,
    alias VARCHAR(30) UNIQUE NOT NULL,
    saldo DECIMAL NOT NULL,
    pan VARCHAR(16) UNIQUE NOT NULL,
    anno_scadenza INT NOT NULL,
    mese_scadenza INT NOT NULL,
    cvv VARCHAR(3),
    iban VARCHAR(27) UNIQUE NOT NULL,
    principale BIT NOT NULL,

    FOREIGN KEY (ref_utente) REFERENCES Utente(id_utente) ON DELETE CASCADE
);

CREATE TABLE Transazione (
    id_transazione INT AUTO_INCREMENT PRIMARY KEY,
    ref_mittente INT NOT NULL,
    ref_destinatario INT NOT NULL,
    descrizione VARCHAR(255),
    importo DECIMAL NOT NULL,
    data_t TIMESTAMP NOT NULL,

    FOREIGN KEY (ref_mittente) REFERENCES Conto(id_conto),
    FOREIGN KEY (ref_destinatario) REFERENCES Conto(id_conto)
);

CREATE TABLE Prestito (
    id_prestito INT AUTO_INCREMENT PRIMARY KEY,
    ref_conto INT NOT NULL,
    importo DECIMAL NOT NULL,
    numero_rate INT NOT NULL,
    tan DECIMAL,
    taeg DECIMAL,
    rata_mensile DECIMAL,
    importo_pagato DECIMAL NOT NULL,

    FOREIGN KEY (ref_conto) REFERENCES Conto(id_conto)
);