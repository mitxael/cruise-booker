USE db_GestioneCrociere
GO

CREATE TABLE [Anagrafica]
( 
	[nome]               varchar(50)  NOT NULL ,
	[cognome]            varchar(50)  NOT NULL ,
	[data_nascita]       date  NOT NULL ,
	[telefono]           varchar(20)  NULL ,
	[cellulare]          varchar(20)  NULL ,
	[email]              varchar(100)  NULL ,
	[indirizzo]          varchar(200)  NOT NULL ,
	[cap]                integer  NULL ,
	[citta]              varchar(50)  NOT NULL ,
	[cf]                 varchar(16)  NULL ,
	[paese_residenza]    varchar(20)  NOT NULL ,
	[nazionalita]        varchar(50)  NOT NULL ,
	[id_anagrafica]      integer  NOT NULL  IDENTITY ( 1,1 ) ,
	CONSTRAINT [XPKAnagrafica] PRIMARY KEY  CLUSTERED ([id_anagrafica] ASC)
)
go

CREATE TABLE [Cabina]
( 
	[id_cabina]          integer  NOT NULL  IDENTITY ( 1,1 ) ,
	[classe]             integer  NOT NULL ,
	[posti]              integer  NOT NULL ,
	[id_nave]            integer  NOT NULL ,
	[codice]             varchar(5)  NULL ,
	[ponte]              integer  NULL ,
	CONSTRAINT [XPKCabina] PRIMARY KEY  CLUSTERED ([id_cabina] ASC)
)
go

CREATE TABLE [Crociera]
( 
	[id_crociera]        integer  NOT NULL  IDENTITY ( 1,1 ) ,
	[cadenza_giorni]     integer  NOT NULL ,
	[costo_base]         float  NOT NULL ,
	[denominazione]      varchar(50)  NULL ,
	[id_tipologia_crociera] integer  NOT NULL ,
	CONSTRAINT [XPKCrociera] PRIMARY KEY  CLUSTERED ([id_crociera] ASC)
)
go

CREATE TABLE [Nave]
( 
	[id_nave]            integer  NOT NULL  IDENTITY ( 1,1 ) ,
	[stazza]             float  NOT NULL ,
	[cabine]             integer  NOT NULL ,
	[passeggeri]         integer  NOT NULL ,
	[anno]               integer  NOT NULL ,
	[nome]               varchar(50)  NULL ,
	[ponti]              integer  NULL ,
	CONSTRAINT [XPKNave] PRIMARY KEY  CLUSTERED ([id_nave] ASC)
)
go

CREATE TABLE [Porto]
( 
	[id_porto]           integer  NOT NULL  IDENTITY ( 1,1 ) ,
	[citta]              varchar(100)  NOT NULL ,
	[paese]              varchar(100)  NOT NULL ,
	CONSTRAINT [XPKPorto] PRIMARY KEY  CLUSTERED ([id_porto] ASC)
)
go

CREATE TABLE [Prenotazione]
( 
	[id_prenotazione]    integer  NOT NULL  IDENTITY ( 1,1 ) ,
	[costo_totale]       float  NULL ,
	[annotazioni]        varchar(200)  NULL ,
	[id_cabina]          integer  NULL ,
	[id_anagrafica]      integer  NOT NULL ,
	[id_registro_crociera] integer  NOT NULL ,
	CONSTRAINT [XPKPrenotazione] PRIMARY KEY  CLUSTERED ([id_prenotazione] ASC)
)
go

CREATE TABLE [Registro_Crociere]
( 
	[id_registro_crociera] integer  NOT NULL  IDENTITY ( 1,1 ) ,
	[data_inizio]        date  NOT NULL ,
	[data_fine]          date  NOT NULL ,
	[id_crociera]        integer  NULL ,
	[id_nave]            integer  NULL ,
	CONSTRAINT [XPKRegistro_Crociere] PRIMARY KEY  CLUSTERED ([id_registro_crociera] ASC)
)
go

CREATE TABLE [Registro_Porti]
( 
	[id_registro_porti]  integer  NOT NULL  IDENTITY ( 1,1 ) ,
	[id_porto]           integer  NOT NULL ,
	[tappa]              integer  NOT NULL ,
	[id_crociera]        integer  NOT NULL ,
	CONSTRAINT [XPKItinerario_Porti] PRIMARY KEY  CLUSTERED ([id_registro_porti] ASC)
)
go

CREATE TABLE [Registro_Servizi]
( 
	[id_registro_servizi] integer  NOT NULL  IDENTITY ( 1,1 ) ,
	[id_servizio]        integer  NOT NULL ,
	[id_prenotazione]    integer  NOT NULL ,
	CONSTRAINT [XPKRegistro_Servizi] PRIMARY KEY  CLUSTERED ([id_registro_servizi] ASC)
)
go

CREATE TABLE [Servizio]
( 
	[id_servizio]        integer  NOT NULL  IDENTITY ( 1,1 ) ,
	[descrizione]        varchar(150)  NOT NULL ,
	[costo_extra]        float  NOT NULL ,
	[id_nave]            integer  NULL ,
	CONSTRAINT [XPKServizio] PRIMARY KEY  CLUSTERED ([id_servizio] ASC)
)
go

CREATE TABLE [Tipologia_crociera]
( 
	[id_tipologia_crociera] integer  NOT NULL  IDENTITY ( 1,1 ) ,
	[descrizione]        varchar(50)  NULL ,
	CONSTRAINT [XPKTipologia_crociera] PRIMARY KEY  CLUSTERED ([id_tipologia_crociera] ASC)
)
go

CREATE TABLE [Utente]
( 
	[id_utente]          integer  NOT NULL  IDENTITY ( 1,1 ) ,
	[nome_utente]        varchar(20)  NOT NULL UNIQUE,
	[password_utente]    varchar(20)  NOT NULL ,
	[amministratore]     bit  NOT NULL ,
	[assistente]         integer  NOT NULL ,
	[id_anagrafica]      integer  NULL ,
	CONSTRAINT [XPKUtenti] PRIMARY KEY  CLUSTERED ([id_utente] ASC)
)
go


ALTER TABLE [Cabina]
	ADD CONSTRAINT [R_27] FOREIGN KEY ([id_nave]) REFERENCES [Nave]([id_nave])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Crociera]
	ADD CONSTRAINT [R_36] FOREIGN KEY ([id_tipologia_crociera]) REFERENCES [Tipologia_crociera]([id_tipologia_crociera])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Prenotazione]
	ADD CONSTRAINT [R_43] FOREIGN KEY ([id_cabina]) REFERENCES [Cabina]([id_cabina])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Prenotazione]
	ADD CONSTRAINT [R_44] FOREIGN KEY ([id_anagrafica]) REFERENCES [Anagrafica]([id_anagrafica])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Prenotazione]
	ADD CONSTRAINT [R_47] FOREIGN KEY ([id_registro_crociera]) REFERENCES [Registro_Crociere]([id_registro_crociera])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Registro_Crociere]
	ADD CONSTRAINT [R_41] FOREIGN KEY ([id_crociera]) REFERENCES [Crociera]([id_crociera])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Registro_Crociere]
	ADD CONSTRAINT [R_49] FOREIGN KEY ([id_nave]) REFERENCES [Nave]([id_nave])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Registro_Porti]
	ADD CONSTRAINT [R_16] FOREIGN KEY ([id_porto]) REFERENCES [Porto]([id_porto])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Registro_Porti]
	ADD CONSTRAINT [R_33] FOREIGN KEY ([id_crociera]) REFERENCES [Crociera]([id_crociera])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Registro_Servizi]
	ADD CONSTRAINT [R_12] FOREIGN KEY ([id_servizio]) REFERENCES [Servizio]([id_servizio])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Registro_Servizi]
	ADD CONSTRAINT [R_45] FOREIGN KEY ([id_prenotazione]) REFERENCES [Prenotazione]([id_prenotazione])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Servizio]
	ADD CONSTRAINT [R_50] FOREIGN KEY ([id_nave]) REFERENCES [Nave]([id_nave])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Utente]
	ADD CONSTRAINT [R_38] FOREIGN KEY ([id_anagrafica]) REFERENCES [Anagrafica]([id_anagrafica])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go
