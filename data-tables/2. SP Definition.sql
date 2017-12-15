USE db_GestioneCrociere
GO

--*********REGISTRA CROCIERA********************************************************
IF EXISTS (SELECT * FROM sysobjects WHERE id = object_id('sp_registra_crociera'))
	DROP PROCEDURE dbo.sp_registra_crociera;
GO
CREATE PROCEDURE sp_registra_crociera
	(@id_crociera integer,
	@id_nave integer,
	@data_inizio date,
	@data_fine date)
AS
BEGIN TRANSACTION
	SET NOCOUNT ON
	DECLARE @status AS INTEGER
	SELECT @status = COUNT(T1.id_registro_crociera) from Registro_Crociere T1
	WHERE (@id_nave = T1.id_nave) AND	( 
	((@data_inizio >= T1.data_inizio AND @data_inizio <= T1.data_fine) OR (@data_fine >= T1.data_inizio AND @data_fine <= T1.data_fine)) OR
	((T1.data_inizio >= @data_inizio AND T1.data_inizio <= @data_fine) OR (T1.data_fine >= @data_inizio AND T1.data_fine <= @data_fine)) )

	IF @status < 1 -- nave libera nel periodo
	BEGIN
		INSERT INTO Registro_Crociere(id_crociera, id_nave, data_inizio, data_fine) VALUES
		(@id_crociera, @id_nave, @data_inizio, @data_fine)
		IF @@ERROR <> 0
			BEGIN
				ROLLBACK
				RAISERROR ('Errore durante l''inserimento della prenotazione.', 16, 1)
				RETURN
			END
	END
	ELSE
		RAISERROR ('Errore. Nave già assegnata ad una crociera nel periodo richiesto.', 16, 1)
COMMIT
GO
--EXEC sp_registra_crociera 1, 5, '09/01/2015', '09/14/2015'
--EXEC sp_registra_crociera 1, 5, '09/13/2015', '09/20/2015'
--EXEC sp_registra_crociera 1, 5, '08/30/2015', '10/01/2015'

--*********MODIFICA CROCIERA********************************************************
IF EXISTS (SELECT * FROM sysobjects WHERE id = object_id('sp_modifica_crociera'))
	DROP PROCEDURE dbo.sp_modifica_crociera;
GO
CREATE PROCEDURE sp_modifica_crociera
	(@id_registro_crociera integer,
	@id_crociera integer,
	@id_nave integer,
	@data_inizio date,
	@data_fine date)
AS
BEGIN TRANSACTION
	SET NOCOUNT ON
	--SET DATEFORMAT dmy
	--SELECT ISDATE(CONVERT(varchar(10), @data_inizio, 20))
	IF ((SELECT COUNT(id_crociera) FROM Crociera WHERE id_crociera = @id_crociera) > 0) 
	AND ((SELECT COUNT(id_nave) FROM Nave WHERE id_nave = @id_nave) > 0)
	BEGIN
		UPDATE Registro_Crociere SET id_crociera = IsNull(@id_crociera,id_crociera), id_nave = IsNull(@id_nave,id_nave), 
		data_inizio = IsNull(@data_inizio,data_inizio), data_fine = IsNull(@data_fine,data_fine)
		WHERE id_registro_crociera = @id_registro_crociera
		IF @@ERROR <> 0
		BEGIN
			ROLLBACK
			RAISERROR ('Errore durante l''aggiornamento del registro crociere.', 16, 1)
			RETURN
		END
	END
	ELSE
		RAISERROR ('Errore. Crociera o nave non esistente.', 16, 1)
COMMIT
GO
--EXEC sp_modifica_crociera 1, 1, 5, '',''
--EXEC sp_modifica_crociera 2, 3, 1, '06/06/2015','06/06/2015'
--EXEC sp_modifica_crociera 2, 3, 4, null, null


--*********CALCOLARE TOTALE********************************************************
IF EXISTS (SELECT * FROM sysobjects WHERE id = object_id('sp_calcola_totale'))
	DROP PROCEDURE dbo.sp_calcola_totale;
GO
CREATE PROCEDURE sp_calcola_totale
	(@id_prenotazione  integer)
AS
BEGIN TRANSACTION
	SET NOCOUNT ON
	DECLARE @id_crociera AS float
	DECLARE @costo_totale AS float
	DECLARE @costo_base AS float
	DECLARE @costo_extra AS float

	SELECT @id_crociera = T2.id_crociera from Prenotazione T1
	INNER JOIN Registro_Crociere T2
	ON T1.id_registro_crociera = T2.id_registro_crociera
	INNER JOIN CROCIERA T3
	ON T2.id_crociera = T3.id_crociera
	where T1.id_prenotazione = @id_prenotazione
	
	SELECT @costo_base = costo_base from Crociera where id_crociera = @id_crociera
  
	SELECT @costo_extra = ISNULL(SUM(T2.costo_extra), 0) FROM Registro_Servizi T1
	INNER JOIN Servizio T2 ON (T1.id_servizio = T2.id_servizio)
	WHERE T1.id_prenotazione=@id_prenotazione

	SET @costo_totale = @costo_base + @costo_extra

	UPDATE Prenotazione SET costo_totale = @costo_totale WHERE id_prenotazione = @id_prenotazione
	IF @@ERROR <> 0
	BEGIN
		ROLLBACK
		RAISERROR ('Errore durante il calcolo del costo totale della prenotazione.', 16, 1)
		RETURN
	END
COMMIT
GO
--EXEC sp_calcola_totale 1

--*********INSERIRE PRENOTAZIONE********************************************************
IF EXISTS (SELECT * FROM sysobjects WHERE id = object_id('sp_inserire_prenotazione'))
	DROP PROCEDURE dbo.sp_inserire_prenotazione;
GO
CREATE PROCEDURE sp_inserire_prenotazione
	(@id_anagrafica integer,
	@id_registro_crociera integer,
	@id_cabina integer,
	@annotazioni varchar(200),
	@id_prenotazione integer OUTPUT)
AS
BEGIN TRANSACTION
	SET NOCOUNT ON
	DECLARE @status AS INTEGER
	SELECT @status = COUNT(T1.id_cabina) from Prenotazione T1
	--SELECT IIF(id_cabina = null or id_cabina < 1, 'free', 'unavailable') AS result FROM Prenotazione T1
	INNER JOIN Registro_Crociere T2
	ON (T1.id_registro_crociera = T2.id_registro_crociera)
	WHERE (T1.id_cabina = @id_cabina and T1.id_registro_crociera = @id_registro_crociera)

	IF @status < 1
	BEGIN
		INSERT INTO Prenotazione (id_anagrafica, id_registro_crociera, id_cabina, costo_totale, annotazioni) VALUES
		(@id_anagrafica, @id_registro_crociera, @id_cabina, null, @annotazioni)
		IF @@ERROR <> 0
			BEGIN
				ROLLBACK
				RAISERROR ('Errore durante l''inserimento della prenotazione.', 16, 1)
				RETURN
			END
		--DECLARE @id_prenotazione INT
		SET @id_prenotazione = SCOPE_IDENTITY()
		EXEC sp_calcola_totale @id_prenotazione
	END
	ELSE
		RAISERROR ('Errore. Cabina gia prenotata.', 16, 1);
COMMIT
GO

/*DECLARE @my_var as int
EXEC sp_inserire_prenotazione 3, 1, 2, 'posto per un gatto', @id_prenotazione = @my_var OUTPUT
PRINT convert(varchar(10),@my_var)*/

/*DECLARE @my_var as int
EXEC sp_inserire_prenotazione 4, 1, 1, 'una persona affetta di diabete', @id_prenotazione = @my_var OUTPUT
PRINT convert(varchar(10),@my_var)*/


--*********REGISTRA SERVIZIO********************************************************
IF EXISTS (SELECT * FROM sysobjects WHERE id = object_id('sp_registra_servizio'))
	DROP PROCEDURE dbo.sp_registra_servizio;
GO
CREATE PROCEDURE sp_registra_servizio
	(@id_prenotazione integer,
	@id_servizio integer)
AS
BEGIN TRANSACTION
	SET NOCOUNT ON
	IF ((SELECT COUNT(id_prenotazione) FROM Prenotazione WHERE id_prenotazione = @id_prenotazione) > 0) 
	AND ((SELECT COUNT(id_servizio) FROM Servizio WHERE id_servizio = @id_servizio) > 0)
	BEGIN
		INSERT INTO Registro_Servizi(id_prenotazione, id_servizio) VALUES (@id_prenotazione, @id_servizio)
		IF @@ERROR <> 0
			BEGIN
				ROLLBACK
				RAISERROR ('Errore durante la registrazione del servizio. Controllare che la prenotazione ed il servizio siano validi.', 16, 1)
				RETURN
			END
	END
	ELSE
		RAISERROR ('Errore. Prenotazione o servizio non esistente.', 16, 1)

	EXEC sp_calcola_totale @id_prenotazione
COMMIT
GO
--EXEC sp_registra_servizio 2, 1
--EXEC sp_registra_servizio 2, 3
--EXEC sp_registra_servizio 2, 4


--*********CANCELLARE PRENOTAZIONE********************************************************
IF EXISTS (SELECT * FROM sysobjects WHERE id = object_id('sp_cancellare_prenotazione'))
	DROP PROCEDURE dbo.sp_cancellare_prenotazione;
GO
CREATE PROCEDURE sp_cancellare_prenotazione
	( @id_prenotazione int )
AS
BEGIN TRANSACTION
	SET NOCOUNT ON
	DELETE FROM Registro_Servizi WHERE id_prenotazione = @id_prenotazione
	IF @@ERROR <> 0
	 BEGIN
		ROLLBACK
		RAISERROR ('Errore durante la cancellazione dei servizi associati alla prenotazione.', 16, 1)
		RETURN
	 END

	DELETE FROM prenotazione WHERE id_prenotazione = @id_prenotazione
	IF @@ERROR <> 0
	 BEGIN
		ROLLBACK
		RAISERROR ('Errore durante la cancellazione della prenotazione.', 16, 1)
		RETURN
	 END
COMMIT
GO
--EXEC sp_cancellare_prenotazione 20


--*********CANCELLARE NAVE********************************************************
IF EXISTS (SELECT * FROM sysobjects WHERE id = object_id('sp_cancellare_nave'))
	DROP PROCEDURE dbo.sp_cancellare_nave;
GO
CREATE PROCEDURE sp_cancellare_nave
	( @id_nave int )
AS
BEGIN TRANSACTION
	SET NOCOUNT ON
	DECLARE @n_prenotazioni AS INT
	SELECT @n_prenotazioni = COUNT(T1.id_prenotazione) FROM prenotazione T1 INNER JOIN Registro_Crociere T2 
							ON T1.id_registro_crociera=T2.id_registro_crociera WHERE T2.id_nave=@id_nave

	IF @n_prenotazioni > 0 -- se esistono prenotazioni rimuovere i riferimenti
	BEGIN
		UPDATE T1 SET id_cabina=null FROM Prenotazione AS T1
		INNER JOIN Cabina AS T2 ON (T1.id_cabina = T2.id_cabina)
		WHERE T2.id_nave = @id_nave
		IF @@ERROR <> 0
		 BEGIN
			ROLLBACK
			RAISERROR ('Errore durante la rimozione della cabina dalla prenotazione.', 16, 1)
			RETURN
		END
		
		UPDATE T1 SET id_nave=null FROM Servizio AS T1 WHERE T1.id_nave = @id_nave
		IF @@ERROR <> 0
		 BEGIN
			ROLLBACK
			RAISERROR ('Errore durante la rimozione della nave dal servizio.', 16, 1)
			RETURN
		 END

		UPDATE T1 SET id_nave=null FROM Registro_Crociere AS T1 WHERE T1.id_nave = @id_nave
		IF @@ERROR <> 0
		 BEGIN
			ROLLBACK
			RAISERROR ('Errore durante la rimozione della crociera dal registro.', 16, 1)
			RETURN
		 END
	END
	ELSE -- altrimenti cancellare direttamente da servizi e registri
	BEGIN
		DELETE FROM	Servizio WHERE id_nave = @id_nave
		IF @@ERROR <> 0
		 BEGIN
			ROLLBACK
			RAISERROR ('Errore durante la cancellazione della nave dal registro navi.', 16, 1)
			RETURN
		 END

		DELETE FROM Registro_Crociere WHERE id_nave = @id_nave
		IF @@ERROR <> 0
		 BEGIN
			ROLLBACK
			RAISERROR ('Errore durante la rimozione della crociera dal registro.', 16, 1)
			RETURN
		END
	END

	DELETE FROM Cabina WHERE id_nave = @id_nave
	IF @@ERROR <> 0
	 BEGIN
		ROLLBACK
		RAISERROR ('Errore durante la cancellazione delle cabine associate alla nave.', 16, 1)
		RETURN
	END	

	DELETE FROM nave WHERE id_nave = @id_nave
	IF @@ERROR <> 0
	 BEGIN
		ROLLBACK
		RAISERROR ('Errore durante la cancellazione della nave.', 16, 1)
		RETURN
	 END
COMMIT
GO
--exec sp_cancellare_nave 3

--*********CANCELLARE CROCIERA********************************************************
IF EXISTS (SELECT * FROM sysobjects WHERE id = object_id('sp_cancellare_crociera'))
	DROP PROCEDURE dbo.sp_cancellare_crociera;
GO
CREATE PROCEDURE sp_cancellare_crociera
	( @id_crociera int )
AS
BEGIN TRANSACTION
	SET NOCOUNT ON
	UPDATE Registro_Crociere SET id_crociera=null FROM Registro_Crociere T1 
	INNER JOIN Prenotazione T2 
	ON T1.id_registro_crociera=T2.id_registro_crociera
	WHERE T1.id_crociera = @id_crociera
	IF @@ERROR <> 0
	 BEGIN
		ROLLBACK
		RAISERROR ('Errore durante la rimozione della crociera dal registro.', 16, 1)
		RETURN
	 END

	DELETE FROM Registro_Crociere WHERE id_crociera = @id_crociera
	IF @@ERROR <> 0
	 BEGIN
		ROLLBACK
		RAISERROR ('Errore durante la cancellazione della crociera dal registro crociere.', 16, 1)
		RETURN
	 END

	DELETE FROM Registro_Porti WHERE id_crociera = id_crociera
	IF @@ERROR <> 0
	 BEGIN
		ROLLBACK
		RAISERROR ('Errore durante la cancellazione dei porti associati alla crociera.', 16, 1)
		RETURN
	 END

	DELETE FROM Crociera WHERE id_crociera = @id_crociera
	IF @@ERROR <> 0
	 BEGIN
		ROLLBACK
		RAISERROR ('Errore durante la cancellazione della crociera.', 16, 1)
		RETURN
	 END
COMMIT
GO
--EXEC sp_cancellare_crociera 8


--*********CANCELLARE ANAGRAFICA********************************************************
IF EXISTS (SELECT * FROM sysobjects WHERE id = object_id('sp_cancellare_anagrafica'))
	DROP PROCEDURE dbo.sp_cancellare_anagrafica;
GO
CREATE PROCEDURE sp_cancellare_anagrafica
	( @id_anagrafica int )
AS
BEGIN TRANSACTION
	SET NOCOUNT ON
	DECLARE @n_prenotazioni AS INT
	SELECT @n_prenotazioni = COUNT(id_prenotazione) FROM prenotazione WHERE id_anagrafica=@id_anagrafica

	IF @n_prenotazioni > 0
	 BEGIN
		ROLLBACK
		RAISERROR ('Impossibile cancellare cliente, esistono delle prenotazioni associate.', 16, 1)
		RETURN
	END
	ELSE
	BEGIN
		UPDATE Utente SET id_anagrafica=null WHERE id_anagrafica = @id_anagrafica
		IF @@ERROR <> 0
		 BEGIN
			ROLLBACK
			RAISERROR ('Errore durante la cancellazione degli utenti associati al cliente.', 16, 1)
			RETURN
		 END

		DELETE FROM Anagrafica WHERE id_anagrafica = @id_anagrafica
		IF @@ERROR <> 0
		 BEGIN
			ROLLBACK
			RAISERROR ('Errore durante la cancellazione del cliente.', 16, 1)
			RETURN
		 END
	END
COMMIT
GO
--EXEC sp_cancellare_anagrafica 3

--*********CANCELLARE UTENTE********************************************************
IF EXISTS (SELECT * FROM sysobjects WHERE id = object_id('sp_cancellare_utente'))
	DROP PROCEDURE dbo.sp_cancellare_utente;
GO
CREATE PROCEDURE sp_cancellare_utente
	( @id_utente int )
AS
BEGIN TRANSACTION
	SET NOCOUNT ON
	DECLARE @is_admin AS INT
	SELECT @is_admin = amministratore FROM Utente WHERE id_utente=@id_utente
	IF @is_admin > 0
	 BEGIN
		ROLLBACK
		RAISERROR ('Impossibile cancellare utente, occorre togliere prima i privilegi di amministratore.', 16, 1)
		RETURN
	END
	ELSE
	BEGIN
		DELETE FROM Utente WHERE id_utente=@id_utente
		IF @@ERROR <> 0
		BEGIN
			ROLLBACK
			RAISERROR ('Errore durante la cancellazione dell''utente.', 16, 1)
			RETURN
		END
	END
COMMIT
GO
--EXEC sp_cancellare_utente 3

--*********CANCELLARE SERVIZIO********************************************************
IF EXISTS (SELECT * FROM sysobjects WHERE id = object_id('sp_cancellare_servizio'))
	DROP PROCEDURE dbo.sp_cancellare_servizio;
GO
CREATE PROCEDURE sp_cancellare_servizio
	( @id_servizio int )
AS
BEGIN TRANSACTION
	SET NOCOUNT ON
	DECLARE @n_prenotazioni AS INT
	SELECT @n_prenotazioni = COUNT(id_registro_servizi) FROM Registro_Servizi WHERE id_servizio=@id_servizio

	IF @n_prenotazioni > 0
	 BEGIN
		ROLLBACK
		RAISERROR ('Impossibile cancellare servizio, esistono delle prenotazioni associate.', 16, 1)
		RETURN
	END
	ELSE
	BEGIN
		/*UPDATE Registro_Servizi SET id_servizio=null WHERE id_servizio=@id_servizio
		IF @@ERROR <> 0
		 BEGIN
			ROLLBACK
			RAISERROR ('Errore durante la cancellazione dei registri associati al servizio.', 16, 1)
			RETURN
		 END*/

		DELETE FROM Servizio WHERE id_servizio=@id_servizio
		IF @@ERROR <> 0
		 BEGIN
			ROLLBACK
			RAISERROR ('Errore durante la cancellazione del servizio.', 16, 1)
			RETURN
		 END
	END
COMMIT
GO
--EXEC sp_cancellare_anagrafica 3

--*********CANCELLARE CABINA********************************************************
IF EXISTS (SELECT * FROM sysobjects WHERE id = object_id('sp_cancellare_cabina'))
	DROP PROCEDURE dbo.sp_cancellare_cabina;
GO
CREATE PROCEDURE sp_cancellare_cabina
	( @id_cabina int )
AS
BEGIN TRANSACTION
	SET NOCOUNT ON
	DECLARE @n_prenotazioni AS INT
	SELECT @n_prenotazioni = COUNT(id_prenotazione) FROM Prenotazione WHERE id_cabina=@id_cabina

	IF @n_prenotazioni > 0
	 BEGIN
		UPDATE Prenotazione SET id_cabina=null WHERE id_cabina=@id_cabina
		IF @@ERROR <> 0
		 BEGIN
			ROLLBACK
			RAISERROR ('Errore durante la rimozione della cabina dalle prenotazioni associate.', 16, 1)
			RETURN
		 END
	END
	ELSE
	BEGIN
		DELETE FROM Cabina WHERE id_cabina=@id_cabina
		IF @@ERROR <> 0
		 BEGIN
			ROLLBACK
			RAISERROR ('Errore durante la cancellazione della cabina.', 16, 1)
			RETURN
		 END
	END
COMMIT
GO
--EXEC sp_cancellare_cabina 7


--*********CANCELLARE REGISTRO CROCIERA********************************************************
IF EXISTS (SELECT * FROM sysobjects WHERE id = object_id('sp_cancellare_registro_crociera'))
	DROP PROCEDURE dbo.sp_cancellare_registro_crociera;
GO
CREATE PROCEDURE sp_cancellare_registro_crociera
	( @id_registro_crociera int )
AS
BEGIN TRANSACTION
	SET NOCOUNT ON
	DECLARE @n_prenotazioni AS INT
	SELECT @n_prenotazioni = COUNT(id_prenotazione) FROM Prenotazione WHERE id_registro_crociera=@id_registro_crociera

	IF @n_prenotazioni > 0
	 BEGIN
		ROLLBACK
		RAISERROR ('Impossibile cancellare la crociera dal registro, ci sono delle prenotazioni associate.', 16, 1)
		RETURN
	END
	ELSE
	BEGIN
		DELETE FROM Registro_Crociere WHERE id_registro_crociera=@id_registro_crociera
		IF @@ERROR <> 0
		 BEGIN
			ROLLBACK
			RAISERROR ('Errore durante la cancellazione del registro di crociera.', 16, 1)
			RETURN
		 END
	END
COMMIT
GO
--EXEC sp_cancellare_registro_crociera 12

--*********CANCELLARE REGISTRO PORTI********************************************************
IF EXISTS (SELECT * FROM sysobjects WHERE id = object_id('sp_cancellare_registro_porti'))
	DROP PROCEDURE dbo.sp_cancellare_registro_porti;
GO
CREATE PROCEDURE sp_cancellare_registro_porti
	( @id_registro_porti int )
AS
BEGIN TRANSACTION
	SET NOCOUNT ON

	DELETE FROM Registro_Porti WHERE id_registro_porti=@id_registro_porti
	IF @@ERROR <> 0
	 BEGIN
		ROLLBACK
		RAISERROR ('Errore durante la cancellazione del registro di porti.', 16, 1)
		RETURN
	 END
COMMIT
GO
--EXEC sp_cancellare_registro_porti 1

--*********CANCELLARE REGISTRO SERVIZI********************************************************
IF EXISTS (SELECT * FROM sysobjects WHERE id = object_id('sp_cancellare_registro_servizi'))
	DROP PROCEDURE dbo.sp_cancellare_registro_servizi;
GO
CREATE PROCEDURE sp_cancellare_registro_servizi
	( @id_prenotazione int )
AS
BEGIN TRANSACTION
	SET NOCOUNT ON

	DELETE FROM Registro_Servizi WHERE id_prenotazione = @id_prenotazione
	IF @@ERROR <> 0
	 BEGIN
		ROLLBACK
		RAISERROR ('Errore durante la cancellazione del servizio dal registro.', 16, 1)
		RETURN
	 END
COMMIT
GO
--EXEC sp_cancellare_registro_servizi 2

--*********CANCELLARE PORTO********************************************************
IF EXISTS (SELECT * FROM sysobjects WHERE id = object_id('sp_cancellare_porto'))
	DROP PROCEDURE dbo.sp_cancellare_porto;
GO
CREATE PROCEDURE sp_cancellare_porto
	( @id_porto int )
AS
BEGIN TRANSACTION
	SET NOCOUNT ON

	DELETE FROM Registro_Porti WHERE id_porto=@id_porto
	IF @@ERROR <> 0
	 BEGIN
		ROLLBACK
		RAISERROR ('Errore durante la cancellazione del porto dal registro.', 16, 1)
		RETURN
	 END

	DELETE FROM Porto WHERE id_porto=@id_porto
	IF @@ERROR <> 0
	 BEGIN
		ROLLBACK
		RAISERROR ('Errore durante la cancellazione del porto.', 16, 1)
		RETURN
	 END
COMMIT
GO
--EXEC sp_cancellare_porto 1

--*********CANCELLARE TIPOLOGIA CROCIERA********************************************************
IF EXISTS (SELECT * FROM sysobjects WHERE id = object_id('sp_cancellare_tipologia_crociera'))
	DROP PROCEDURE dbo.sp_cancellare_tipologia_crociera;
GO
CREATE PROCEDURE sp_cancellare_tipologia_crociera
	( @id_tipologia_crociera int )
AS
BEGIN TRANSACTION
	SET NOCOUNT ON
	DECLARE @n AS INT
	SELECT @n = COUNT(id_crociera) FROM Crociera WHERE id_tipologia_crociera=@id_tipologia_crociera

	IF @n > 0
	 BEGIN
		ROLLBACK
		RAISERROR ('Impossibile cancellare la tipologia, è abbinata ad una o più crociere.', 16, 1)
		RETURN
	 END
	ELSE
	 BEGIN
		DELETE FROM Tipologia_crociera WHERE id_tipologia_crociera = @id_tipologia_crociera
		IF @@ERROR <> 0
		 BEGIN
			ROLLBACK
			RAISERROR ('Errore durante la cancellazione della tipologia.', 16, 1)
			RETURN
		 END
	 END
COMMIT
GO
--EXEC sp_cancellare_tipologia_crociera 6

--********* VIEW DI PRENOTAZIONI********************************************************
IF EXISTS (SELECT * FROM sysobjects WHERE id = object_id('vw_prenotazione'))
	DROP VIEW dbo.vw_prenotazione;
GO
CREATE VIEW vw_prenotazione
AS
	SELECT T1.id_prenotazione, T1.id_anagrafica, T2.nome, T2.cognome, T1.id_registro_crociera, T4.denominazione,
	T1.id_cabina, T5.codice, T1.costo_totale, T3.data_inizio, T3.data_fine, T4.id_crociera, T1.annotazioni, T4.costo_base,
	T6.id_nave, T6.nome as nave, T7.descrizione, T5.posti, T5.classe
	FROM Prenotazione T1 
	INNER JOIN Anagrafica T2 
	ON T1.id_anagrafica = T2.id_anagrafica 
	INNER JOIN Registro_Crociere T3 
	ON T1.id_registro_crociera = T3.id_registro_crociera 
	INNER JOIN Crociera T4 
	ON T3.id_crociera = T4.id_crociera 
	INNER JOIN Cabina T5 
	ON T1.id_cabina = T5.id_cabina 
	INNER JOIN Nave T6 
	ON T3.id_nave = T6.id_nave 
	INNER JOIN Tipologia_crociera T7 
	ON T4.id_tipologia_crociera = T7. id_tipologia_crociera 
GO
--SELECT * FROM vw_prenotazione

--********* VIEW DI CROCIERE********************************************************
IF EXISTS (SELECT * FROM sysobjects WHERE id = object_id('vw_crociere'))
	DROP VIEW dbo.vw_crociere;
GO
CREATE VIEW vw_crociere
AS
	SELECT DISTINCT(T1.id_registro_crociera), T1.id_nave, T4.nome, T2.denominazione, T3.descrizione,
	T1.data_inizio, T1.data_fine, T2.costo_base
	FROM Registro_Crociere T1
	INNER JOIN Crociera T2
	ON T1.id_crociera=T2.id_crociera
	INNER JOIN Tipologia_crociera T3
	ON T2.id_tipologia_crociera=T3.id_tipologia_crociera
	INNER JOIN Nave T4
	ON T1.id_nave=T4.id_nave
	INNER JOIN Registro_Porti T5
	ON T1.id_crociera=T5.id_crociera
	INNER JOIN Porto T6
	ON T5.id_porto=T6.id_porto
GO
--SELECT * FROM vw_crociere

--********* VIEW DI RICERCA_CROCIERE********************************************************
IF EXISTS (SELECT * FROM sysobjects WHERE id = object_id('vw_ricerca_crociere'))
	DROP VIEW dbo.vw_ricerca_crociere;
GO
CREATE VIEW vw_ricerca_crociere
AS
	SELECT id_registro_crociera, nome, denominazione, costo_base, id_nave, descrizione, data_inizio, data_fine, SUM(posti) AS posti_liberi FROM (
		SELECT T1.id_registro_crociera, T3.nome, T2.denominazione, T2. costo_base, T3.id_nave, T4.descrizione, T1.data_inizio, T1.data_fine, T7.posti, T7.id_cabina
		FROM Registro_crociere T1
		INNER JOIN Crociera T2
		ON T1.id_crociera=T2.id_crociera
		INNER JOIN Nave T3
		ON T1.id_nave=T3.id_nave
		INNER JOIN Tipologia_crociera T4
		ON T2.id_tipologia_crociera=T4.id_tipologia_crociera
		INNER JOIN Registro_Porti T5
		ON T1.id_crociera=T5.id_crociera
		INNER JOIN Porto T6
		ON T5.id_porto=T6.id_porto
		INNER JOIN Cabina T7
		ON T3.id_nave=T7.id_nave
		--WHERE (T6.citta like '%%')
		--AND (T1.data_inizio >= '2015-11-01' AND T1.data_inizio <= '2015-12-21')
		AND (T7.id_cabina NOT IN (SELECT id_cabina FROM Prenotazione WHERE T1.id_registro_crociera IN (SELECT id_registro_crociera FROM Prenotazione)))
		GROUP BY T1.id_registro_crociera, T3.nome, T2.denominazione, T2. costo_base, T1.id_nave, T3.id_nave, T4.descrizione, T1.data_inizio, T1.data_fine, T7.id_cabina, T7.posti, T7.id_cabina
	) AS T0
	GROUP BY id_registro_crociera, nome, denominazione, costo_base, id_nave, descrizione, data_inizio, data_fine
	--HAVING (SUM(posti) >= 50)
GO
--SELECT * FROM vw_ricerca_crociere