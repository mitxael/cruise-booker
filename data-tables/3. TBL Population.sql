USE db_GestioneCrociere
GO
--SET DATEFORMAT dmy;
--GO
--*********ANAGRAFICA********************************************************
insert into anagrafica (nome, cognome, data_nascita, telefono, cellulare, email, indirizzo, cap, citta, cf, paese_residenza, nazionalita) values 
('Michael', 'Vasquez', '30/11/1983', '0733638879', '3276970872', 'mitxael@gmail.com', 'via camus 69', '62027', 'Macerata', 'VSQMHL83S30Z611X', 'Italia', 'Peruviana')
insert into anagrafica (nome, cognome, data_nascita, telefono, cellulare, email, indirizzo, cap, citta, cf, paese_residenza, nazionalita) values 
('Laura', 'Natalini', '08/08/1991', '0733638879', '3276970872', 'lau.nata@alice.it', 'contrada sibilla 2', '31041', 'Milano', 'NTLLRR91T08Y108I', 'Italia', 'Italiana')
insert into anagrafica (nome, cognome, data_nascita, telefono, cellulare, email, indirizzo, cap, citta, cf, paese_residenza, nazionalita) values 
('Marianna', 'Poggi', '08/12/1961', '0733638879', '3687259671', 'm.poggi@alice.it', 'piazza coralo 9', '71231', 'Pisa', 'PGGMRR61A125Z8I', 'Italia', 'Italiana')
insert into anagrafica (nome, cognome, data_nascita, telefono, cellulare, email, indirizzo, cap, citta, cf, paese_residenza, nazionalita) values 
('Serena', 'Gualdrini', '07/11/1973', '0733328964','3456984819', 'serenag@alice.it', 'via sartre 3', '14134', 'Roma', 'GLDSRN61A125Z8I', 'Italia', 'Italiana')
insert into anagrafica (nome, cognome, data_nascita, telefono, cellulare, email, indirizzo, cap, citta, cf, paese_residenza, nazionalita) values 
('Melanie', 'Laurent', '25/02/1981', '05874241','96548712', 'mlau@orange.fr', 'rue du soleil 7', '35412', 'Paris', 'MLRNT3257962451', 'Francia', 'Francese')
insert into anagrafica (nome, cognome, data_nascita, telefono, cellulare, email, indirizzo, cap, citta, cf, paese_residenza, nazionalita) values 
('Kaissa', 'Schmitt', '14/08/1969', '033586412','915231875', 'kaissas@bild.de', 'can der brant 7', '98231', 'Munich', 'KSCHM3297426428', 'Germania', 'Tedesca')
insert into anagrafica (nome, cognome, data_nascita, telefono, cellulare, email, indirizzo, cap, citta, cf, paese_residenza, nazionalita) values 
('Lorenzo', 'Porcelli', '25/12/1983', '07342523','3205634532', 'porcellinog@tiscali.it', 'località granali 64', '56231', 'Siena', 'GLDSRN61A125Z8I', 'Italia', 'Italiana')
insert into anagrafica (nome, cognome, data_nascita, telefono, cellulare, email, indirizzo, cap, citta, cf, paese_residenza, nazionalita) values 
('Jordan', 'Roberts', '13/03/1985', '05834713412', '962412323', 'jordan.doc@mails.au', 'st. summerland 7', '96491', 'Sidney', 'AUHTPR61A125Z7A', 'Australia', 'Australiana')
select * from anagrafica


--*********UTENTE********************************************************
insert into utente (nome_utente, password_utente, amministratore, assistente, id_anagrafica) values ('admin', '1234', 1, 0, null)
insert into utente (nome_utente, password_utente, amministratore, assistente, id_anagrafica) values ('operatore', '1234', 0, 1, null)
insert into utente (nome_utente, password_utente, amministratore, assistente, id_anagrafica) values ('mitxael', '1234', 1, 1, 1)
insert into utente (nome_utente, password_utente, amministratore, assistente, id_anagrafica) values ('amadeus01', '1234', 0, 1, 2)
select * from utente

--*********TIPOLOGIA CROCIERA********************************************************
insert into tipologia_crociera (descrizione) values ('Economy')
insert into tipologia_crociera (descrizione) values ('Deluxe')
insert into tipologia_crociera (descrizione) values ('Royal')
select * from tipologia_crociera 

--*********NAVE********************************************************
insert into nave (stazza, ponti, cabine, passeggeri, anno, nome) values 
(59058, 13, 795, 1560, '2003', 'Armonia')
insert into nave (stazza, ponti, cabine, passeggeri, anno, nome) values
(65591, 13, 1071, 2150, '2002', 'Sinfonia')
insert into nave (stazza, ponti, cabine, passeggeri, anno, nome) values
(95128, 16, 1259, 2518, '2010', 'Magnifica')
insert into nave (stazza, ponti, cabine, passeggeri, anno, nome) values
(137936, 18, 1637, 3247, '2009', 'Splendida')
insert into nave (stazza, ponti, cabine, passeggeri, anno, nome) values
(139072, 18, 1751, 3502, '2013', 'Preziosa') 
select * from nave

--*********CABINA********************************************************
insert into cabina (classe, posti, ponte, id_nave, codice) values (1, 2, 1, 1, 'A01')
insert into cabina (classe, posti, ponte, id_nave, codice) values (1, 2, 1, 1, 'A02')
insert into cabina (classe, posti, ponte, id_nave, codice) values (1, 2, 1, 1, 'A03')
insert into cabina (classe, posti, ponte, id_nave, codice) values (1, 2, 1, 1, 'A04')
insert into cabina (classe, posti, ponte, id_nave, codice) values (1, 2, 1, 1, 'A05')
insert into cabina (classe, posti, ponte, id_nave, codice) values (2, 4, 2, 1, 'B01')
insert into cabina (classe, posti, ponte, id_nave, codice) values (2, 4, 2, 1, 'B02')
insert into cabina (classe, posti, ponte, id_nave, codice) values (2, 4, 2, 1, 'B03')
insert into cabina (classe, posti, ponte, id_nave, codice) values (2, 4, 2, 1, 'B04')
insert into cabina (classe, posti, ponte, id_nave, codice) values (2, 4, 2, 1, 'B05')
insert into cabina (classe, posti, ponte, id_nave, codice) values (3, 6, 3, 1, 'C01')
insert into cabina (classe, posti, ponte, id_nave, codice) values (3, 6, 3, 1, 'C02')
insert into cabina (classe, posti, ponte, id_nave, codice) values (3, 6, 3, 1, 'C03')
insert into cabina (classe, posti, ponte, id_nave, codice) values (3, 6, 3, 1, 'C04')
insert into cabina (classe, posti, ponte, id_nave, codice) values (3, 6, 3, 1, 'C05')

insert into cabina (classe, posti, ponte, id_nave, codice) values (1, 2, 1, 2, 'A01')
insert into cabina (classe, posti, ponte, id_nave, codice) values (1, 2, 1, 2, 'A02')
insert into cabina (classe, posti, ponte, id_nave, codice) values (1, 2, 1, 2, 'A03')
insert into cabina (classe, posti, ponte, id_nave, codice) values (1, 2, 1, 2, 'A04')
insert into cabina (classe, posti, ponte, id_nave, codice) values (1, 2, 1, 2, 'A05')
insert into cabina (classe, posti, ponte, id_nave, codice) values (2, 4, 2, 2, 'B01')
insert into cabina (classe, posti, ponte, id_nave, codice) values (2, 4, 2, 2, 'B02')
insert into cabina (classe, posti, ponte, id_nave, codice) values (2, 4, 2, 2, 'B03')
insert into cabina (classe, posti, ponte, id_nave, codice) values (2, 4, 2, 2, 'B04')
insert into cabina (classe, posti, ponte, id_nave, codice) values (2, 4, 2, 2, 'B05')
insert into cabina (classe, posti, ponte, id_nave, codice) values (3, 6, 3, 2, 'C01')
insert into cabina (classe, posti, ponte, id_nave, codice) values (3, 6, 3, 2, 'C02')
insert into cabina (classe, posti, ponte, id_nave, codice) values (3, 6, 3, 2, 'C03')
insert into cabina (classe, posti, ponte, id_nave, codice) values (3, 6, 3, 2, 'C04')
insert into cabina (classe, posti, ponte, id_nave, codice) values (3, 6, 3, 2, 'C05')

insert into cabina (classe, posti, ponte, id_nave, codice) values (1, 2, 1, 3, 'A01')
insert into cabina (classe, posti, ponte, id_nave, codice) values (1, 2, 1, 3, 'A02')
insert into cabina (classe, posti, ponte, id_nave, codice) values (1, 2, 1, 3, 'A03')
insert into cabina (classe, posti, ponte, id_nave, codice) values (1, 2, 1, 3, 'A04')
insert into cabina (classe, posti, ponte, id_nave, codice) values (1, 2, 1, 3, 'A05')
insert into cabina (classe, posti, ponte, id_nave, codice) values (2, 4, 2, 3, 'B01')
insert into cabina (classe, posti, ponte, id_nave, codice) values (2, 4, 2, 3, 'B02')
insert into cabina (classe, posti, ponte, id_nave, codice) values (2, 4, 2, 3, 'B03')
insert into cabina (classe, posti, ponte, id_nave, codice) values (2, 4, 2, 3, 'B04')
insert into cabina (classe, posti, ponte, id_nave, codice) values (2, 4, 2, 3, 'B05')
insert into cabina (classe, posti, ponte, id_nave, codice) values (3, 6, 3, 3, 'C01')
insert into cabina (classe, posti, ponte, id_nave, codice) values (3, 6, 3, 3, 'C02')
insert into cabina (classe, posti, ponte, id_nave, codice) values (3, 6, 3, 3, 'C03')
insert into cabina (classe, posti, ponte, id_nave, codice) values (3, 6, 3, 3, 'C04')
insert into cabina (classe, posti, ponte, id_nave, codice) values (3, 6, 3, 3, 'C05')

insert into cabina (classe, posti, ponte, id_nave, codice) values (1, 2, 1, 4, 'A01')
insert into cabina (classe, posti, ponte, id_nave, codice) values (1, 2, 1, 4, 'A02')
insert into cabina (classe, posti, ponte, id_nave, codice) values (1, 2, 1, 4, 'A03')
insert into cabina (classe, posti, ponte, id_nave, codice) values (1, 2, 1, 4, 'A04')
insert into cabina (classe, posti, ponte, id_nave, codice) values (1, 2, 1, 4, 'A05')
insert into cabina (classe, posti, ponte, id_nave, codice) values (2, 4, 2, 4, 'B01')
insert into cabina (classe, posti, ponte, id_nave, codice) values (2, 4, 2, 4, 'B02')
insert into cabina (classe, posti, ponte, id_nave, codice) values (2, 4, 2, 4, 'B03')
insert into cabina (classe, posti, ponte, id_nave, codice) values (2, 4, 2, 4, 'B04')
insert into cabina (classe, posti, ponte, id_nave, codice) values (2, 4, 2, 4, 'B05')
insert into cabina (classe, posti, ponte, id_nave, codice) values (3, 6, 3, 4, 'C01')
insert into cabina (classe, posti, ponte, id_nave, codice) values (3, 6, 3, 4, 'C02')
insert into cabina (classe, posti, ponte, id_nave, codice) values (3, 6, 3, 4, 'C03')
insert into cabina (classe, posti, ponte, id_nave, codice) values (3, 6, 3, 4, 'C04')
insert into cabina (classe, posti, ponte, id_nave, codice) values (3, 6, 3, 4, 'C05')

insert into cabina (classe, posti, ponte, id_nave, codice) values (1, 2, 1, 5, 'A01')
insert into cabina (classe, posti, ponte, id_nave, codice) values (1, 2, 1, 5, 'A02')
insert into cabina (classe, posti, ponte, id_nave, codice) values (1, 2, 1, 5, 'A03')
insert into cabina (classe, posti, ponte, id_nave, codice) values (1, 2, 1, 5, 'A04')
insert into cabina (classe, posti, ponte, id_nave, codice) values (1, 2, 1, 5, 'A05')
insert into cabina (classe, posti, ponte, id_nave, codice) values (2, 4, 2, 5, 'B01')
insert into cabina (classe, posti, ponte, id_nave, codice) values (2, 4, 2, 5, 'B02')
insert into cabina (classe, posti, ponte, id_nave, codice) values (2, 4, 2, 5, 'B03')
insert into cabina (classe, posti, ponte, id_nave, codice) values (2, 4, 2, 5, 'B04')
insert into cabina (classe, posti, ponte, id_nave, codice) values (2, 4, 2, 5, 'B05')
insert into cabina (classe, posti, ponte, id_nave, codice) values (3, 6, 3, 5, 'C01')
insert into cabina (classe, posti, ponte, id_nave, codice) values (3, 6, 3, 5, 'C02')
insert into cabina (classe, posti, ponte, id_nave, codice) values (3, 6, 3, 5, 'C03')
insert into cabina (classe, posti, ponte, id_nave, codice) values (3, 6, 3, 5, 'C04')
insert into cabina (classe, posti, ponte, id_nave, codice) values (3, 6, 3, 5, 'C05')

select * from cabina

--*********PORTI********************************************************
insert into porto (citta, paese) values ('Ancona','Italia')
insert into porto (citta, paese) values ('Genova','Italia')
insert into porto (citta, paese) values ('Civitavecchia','Italia')
insert into porto (citta, paese) values ('Trapani','Italia')
insert into porto (citta, paese) values ('Ibiza','Spagna')
insert into porto (citta, paese) values ('Barcellona','Spagna')
insert into porto (citta, paese) values ('Marsiglia','Francia')
insert into porto (citta, paese) values ('Cannes','Francia')
insert into porto (citta, paese) values ('Naxos','Grecia')
insert into porto (citta, paese) values ('Atene','Grecia')
insert into porto (citta, paese) values ('La Valletta','Malta')
insert into porto (citta, paese) values ('Lisbona','Portogallo')
insert into porto (citta, paese) values ('Montecarlo','Principato di Monaco')
select * from porto 

--*********CROCIERA********************************************************
insert into crociera (id_tipologia_crociera, cadenza_giorni, costo_base, denominazione) values (1, 7, 800, 'Mediterraneo Occidentale')
insert into crociera (id_tipologia_crociera, cadenza_giorni, costo_base, denominazione) values (1, 14, 1150, 'Nord Europa')
insert into crociera (id_tipologia_crociera, cadenza_giorni, costo_base, denominazione) values (2, 7, 600, 'Mediterraneo Orientale')
insert into crociera (id_tipologia_crociera, cadenza_giorni, costo_base, denominazione) values (2, 14, 1200, 'Stati Uniti')
insert into crociera (id_tipologia_crociera, cadenza_giorni, costo_base, denominazione) values (3, 30, 1650, 'Caraibi')
select * from crociera 

--*********REGISTRO_PORTI********************************************************
insert into Registro_Porti (id_porto, tappa, id_crociera) values (1, 0, 1)
insert into Registro_Porti (id_porto, tappa, id_crociera) values (4, 1, 1)
insert into Registro_Porti (id_porto, tappa, id_crociera) values (7, 2, 1)
insert into Registro_Porti (id_porto, tappa, id_crociera) values (2, 3, 1)

insert into Registro_Porti (id_porto, tappa, id_crociera) values (3, 0, 2)
insert into Registro_Porti (id_porto, tappa, id_crociera) values (5, 1, 2)
insert into Registro_Porti (id_porto, tappa, id_crociera) values (6, 2, 2)
insert into Registro_Porti (id_porto, tappa, id_crociera) values (8, 3, 2)

insert into Registro_Porti (id_porto, tappa, id_crociera) values (9, 0, 3)
insert into Registro_Porti (id_porto, tappa, id_crociera) values (1, 1, 3)
insert into Registro_Porti (id_porto, tappa, id_crociera) values (10, 2, 3)
insert into Registro_Porti (id_porto, tappa, id_crociera) values (12, 3, 3)

insert into Registro_Porti (id_porto, tappa, id_crociera) values (11, 0, 4)
insert into Registro_Porti (id_porto, tappa, id_crociera) values (2, 1, 4)
insert into Registro_Porti (id_porto, tappa, id_crociera) values (13, 2, 4)
insert into Registro_Porti (id_porto, tappa, id_crociera) values (7, 3, 4)

insert into Registro_Porti (id_porto, tappa, id_crociera) values (4, 0, 5)
insert into Registro_Porti (id_porto, tappa, id_crociera) values (3, 1, 5)
insert into Registro_Porti (id_porto, tappa, id_crociera) values (6, 2, 5)
insert into Registro_Porti (id_porto, tappa, id_crociera) values (8, 3, 5)

select * from Registro_Porti

--*********REGISTRO_CROCIERE********************************************************
insert into Registro_Crociere (data_inizio, data_fine, id_nave, id_crociera) values ('01/10/2015', '07/10/2015', 1, 1)
insert into Registro_Crociere (data_inizio, data_fine, id_nave, id_crociera) values ('08/10/2015', '15/10/2015', 1, 1)
insert into Registro_Crociere (data_inizio, data_fine, id_nave, id_crociera) values ('16/10/2015', '23/10/2015', 1, 1)

insert into Registro_Crociere (data_inizio, data_fine, id_nave, id_crociera) values ('01/10/2015', '14/10/2015', 2, 2)
insert into Registro_Crociere (data_inizio, data_fine, id_nave, id_crociera) values ('15/10/2015', '30/10/2015', 2, 2)
insert into Registro_Crociere (data_inizio, data_fine, id_nave, id_crociera) values ('01/11/2015', '14/11/2015', 2, 2)

insert into Registro_Crociere (data_inizio, data_fine, id_nave, id_crociera) values ('01/10/2015', '07/10/2015', 3, 3)
insert into Registro_Crociere (data_inizio, data_fine, id_nave, id_crociera) values ('08/10/2015', '15/10/2015', 3, 3)
insert into Registro_Crociere (data_inizio, data_fine, id_nave, id_crociera) values ('16/11/2015', '23/11/2015', 3, 3)

insert into Registro_Crociere (data_inizio, data_fine, id_nave, id_crociera) values ('01/10/2015', '14/10/2015', 4, 4)
insert into Registro_Crociere (data_inizio, data_fine, id_nave, id_crociera) values ('01/10/2015', '30/10/2015', 5, 4)
insert into Registro_Crociere (data_inizio, data_fine, id_nave, id_crociera) values ('01/11/2015', '14/11/2015', 4, 4)

select * from Registro_Crociere

--*********SERVIZIO********************************************************
insert into servizio (descrizione, costo_extra, id_nave) values ('palestra', 25, 1)
insert into servizio (descrizione, costo_extra, id_nave) values ('drinks', 45, 1)
insert into servizio (descrizione, costo_extra, id_nave) values ('massaggi', 50, 1)
insert into servizio (descrizione, costo_extra, id_nave) values ('internet', 5, 1)

insert into servizio (descrizione, costo_extra, id_nave) values ('palestra', 25, 2)
insert into servizio (descrizione, costo_extra, id_nave) values ('drinks', 45, 2)
insert into servizio (descrizione, costo_extra, id_nave) values ('massaggi', 50, 2)
insert into servizio (descrizione, costo_extra, id_nave) values ('internet', 5, 2)
insert into servizio (descrizione, costo_extra, id_nave) values ('piscina', 15, 2)

insert into servizio (descrizione, costo_extra, id_nave) values ('palestra', 25, 3)
insert into servizio (descrizione, costo_extra, id_nave) values ('drinks', 45, 3)
insert into servizio (descrizione, costo_extra, id_nave) values ('disco club', 10, 3)
insert into servizio (descrizione, costo_extra, id_nave) values ('massaggi', 50, 3)
insert into servizio (descrizione, costo_extra, id_nave) values ('internet', 5, 3)
insert into servizio (descrizione, costo_extra, id_nave) values ('piscina', 15, 3)

insert into servizio (descrizione, costo_extra, id_nave) values ('palestra', 25, 4)
insert into servizio (descrizione, costo_extra, id_nave) values ('drinks', 45, 4)
insert into servizio (descrizione, costo_extra, id_nave) values ('disco club', 10, 4)
insert into servizio (descrizione, costo_extra, id_nave) values ('massaggi', 50, 4)
insert into servizio (descrizione, costo_extra, id_nave) values ('internet', 5, 4)
insert into servizio (descrizione, costo_extra, id_nave) values ('piscina', 15, 4)

insert into servizio (descrizione, costo_extra, id_nave) values ('palestra', 25, 5)
insert into servizio (descrizione, costo_extra, id_nave) values ('casino', 90, 5)
insert into servizio (descrizione, costo_extra, id_nave) values ('campo di golf', 60, 5)
insert into servizio (descrizione, costo_extra, id_nave) values ('drinks', 45, 5)
insert into servizio (descrizione, costo_extra, id_nave) values ('disco club', 10, 5)
insert into servizio (descrizione, costo_extra, id_nave) values ('massaggi', 50, 5)
insert into servizio (descrizione, costo_extra, id_nave) values ('internet', 5, 5)
insert into servizio (descrizione, costo_extra, id_nave) values ('piscina', 15, 5)

select * from servizio

--*********PRENOTAZIONE E SERVIZI********************************************************
DECLARE @my_var as int
EXEC sp_inserire_prenotazione 1, 2, 1, 'fumatori', @id_prenotazione = @my_var OUTPUT
PRINT 'Codice prenotazione: ' + convert(varchar(10),@my_var)
EXEC sp_registra_servizio @my_var, 2
EXEC sp_registra_servizio @my_var, 4
GO

DECLARE @my_var as int
EXEC sp_inserire_prenotazione 2, 2, 2, '', @id_prenotazione = @my_var OUTPUT
PRINT 'Codice prenotazione: ' + convert(varchar(10),@my_var)
EXEC sp_registra_servizio @my_var, 2
EXEC sp_registra_servizio @my_var, 4
GO

DECLARE @my_var as int
EXEC sp_inserire_prenotazione 3, 2, 3, '', @id_prenotazione = @my_var OUTPUT
PRINT 'Codice prenotazione: ' + convert(varchar(10),@my_var)
EXEC sp_registra_servizio @my_var, 1
EXEC sp_registra_servizio @my_var, 2
EXEC sp_registra_servizio @my_var, 3
EXEC sp_registra_servizio @my_var, 4
GO

DECLARE @my_var as int
EXEC sp_inserire_prenotazione 4, 2, 6, 'fumatori', @id_prenotazione = @my_var OUTPUT
PRINT 'Codice prenotazione: ' + convert(varchar(10),@my_var)
EXEC sp_registra_servizio @my_var, 1
EXEC sp_registra_servizio @my_var, 4
GO

DECLARE @my_var as int
EXEC sp_inserire_prenotazione 5, 2, 11, '', @id_prenotazione = @my_var OUTPUT
PRINT 'Codice prenotazione: ' + convert(varchar(10),@my_var)
EXEC sp_registra_servizio @my_var, 2
EXEC sp_registra_servizio @my_var, 3
EXEC sp_registra_servizio @my_var, 4
GO

DECLARE @my_var as int
EXEC sp_inserire_prenotazione 6, 7, 31, 'due gatti', @id_prenotazione = @my_var OUTPUT
PRINT 'Codice prenotazione: ' + convert(varchar(10),@my_var)
EXEC sp_registra_servizio @my_var, 10
EXEC sp_registra_servizio @my_var, 11
EXEC sp_registra_servizio @my_var, 12
EXEC sp_registra_servizio @my_var, 13
EXEC sp_registra_servizio @my_var, 14
EXEC sp_registra_servizio @my_var, 15
GO

DECLARE @my_var as int
EXEC sp_inserire_prenotazione 7, 7, 36, 'disabile', @id_prenotazione = @my_var OUTPUT
PRINT 'Codice prenotazione: ' + convert(varchar(10),@my_var)
EXEC sp_registra_servizio @my_var, 12
EXEC sp_registra_servizio @my_var, 13
EXEC sp_registra_servizio @my_var, 14
EXEC sp_registra_servizio @my_var, 15
GO

DECLARE @my_var as int
EXEC sp_inserire_prenotazione 8, 7, 41, '', @id_prenotazione = @my_var OUTPUT
PRINT 'Codice prenotazione: ' + convert(varchar(10),@my_var)
EXEC sp_registra_servizio @my_var, 10
EXEC sp_registra_servizio @my_var, 11
EXEC sp_registra_servizio @my_var, 12
EXEC sp_registra_servizio @my_var, 14
GO

select * from Prenotazione
select * from Registro_Servizi