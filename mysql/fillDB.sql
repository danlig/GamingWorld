-- INSERIMENTO ACCOUNT
 INSERT INTO Account VALUES ('adonis.koss','pass','sberge@example.com','2022-04-23'),
 ('adrain90','pass','kristopher35@example.net','2022-01-25'),
 ('alana61','pass','pgutkowski@example.com','2022-10-12'),
 ('alayna.weimann','pass','estella34@example.com','2022-03-25'),
 ('alexandrea.torp','pass','orn.jaylen@example.com','2022-08-06'),
 ('alexis05','pass','tpowlowski@example.org','2022-05-07'),
 ('amelia35','pass','brennan97@example.org','2022-08-26'),
 ('bauch.arnulfo','pass','gemard@example.com','2022-06-20');
INSERT INTO Account values ("antonio", "pass", "email", '2002-01-01');
INSERT INTO Account values ("adolfo", "pass", "email", '2002-01-01');
INSERT INTO Account values ("ambrogio", "pass", "email", '2001-01-01');

-- INSERIMENTO AMICIZIA
INSERT INTO Amicizia values ("antonio", "adolfo", '2003-02-02');
-- INSERT INTO Amicizia values ("antonio", "ambrogio", '2003-02-02');
INSERT INTO Amicizia values ("ambrogio", "adolfo", '2003-02-02');
INSERT INTO Amicizia values ("ambrogio", "antonio", null);
INSERT INTO Amicizia values ("adonis.koss", "adolfo", '2023-02-01');
-- INSERT INTO Amicizia values ("ambrogio", "antonio2", null); #ERROR GENERATOR LINE
-- INSERT INTO Amicizia values ("antonio", "antonio", '2003-02-02'); #ERROR GENERATOR LINE

-- INSERIMENTO REPORT
INSERT INTO Report values ("antonio", "ambrogio", '2002-01-01', "spam");
INSERT INTO Report values ("adolfo", "ambrogio", '2002-01-01', "bullismo");
INSERT INTO Report values ("antonio", "ambrogio", '2003-01-01', "spam");

-- INSERIMENTO PUBLISHER
INSERT INTO Publisher (nome, sedeLegale) values ("Activision", "Santa monica");
INSERT INTO Publisher (nome, sedeLegale) values ("Blizzard", "3020 Hessel Extension Suite 007");
INSERT INTO Publisher (nome, sedeLegale) values ("EpicGames", "807 Meredith Corners Suite 048");
INSERT INTO Publisher (nome, sedeLegale) values ("Riot", "688 Lakin Points Apt. 975");

-- INSERIMENTO GIOCO
INSERT INTO Gioco (nome, memoriaOccupata, descrizione, pageURL, idPublisher) values ("God Of War", 200, "", "https://it.wikipedia.org/wiki/God_of_War", 1);
INSERT INTO Gioco (nome, memoriaOccupata, descrizione, pageURL, idPublisher) values ("God Of War Ragnarock", 2000, "", "https://it.wikipedia.org/wiki/God_of_War", 1);
INSERT INTO Gioco (nome, memoriaOccupata, descrizione, pageURL, idPublisher) values ("LeagueOfLegends", 5000, "", "https://it.wikipedia.org/wiki/God_of_War", 4);
INSERT INTO Gioco (nome, memoriaOccupata, descrizione, pageURL, idPublisher) values ("Fortnite", 2000, "", "https://www.epicgames.com/fortnite/it/home", 3);
INSERT INTO Gioco (nome, memoriaOccupata, descrizione, pageURL, idPublisher) values ("Cod", 2000, "First-person shooter arena game", "https://www.callofduty.com/it", 1);

-- INSERIMENTO TEAMGAME
INSERT INTO TeamGame values (3);

-- INSERIMENTO TEAM
INSERT INTO Team values ("Desperados");
-- INSERT INTO Team values ("Up-Elo");
INSERT INTO Team values ("Down-Elo");
INSERT INTO Team values ("Med-Elo");

/*
INSERT INTO Compone values ("Desperados", "adonis.koss");
INSERT INTO Compone values ("Desperados", "adrain90");
INSERT INTO Compone values ("Desperados", "alana61");
INSERT INTO Compone values ("Desperados", "antonio");
INSERT INTO Compone values ("Desperados", "adolfo");

INSERT INTO Compone values ("Up-Elo", "ambrogio");
INSERT INTO Compone values ("Up-Elo", "adrain90");
INSERT INTO Compone values ("Up-Elo", "alana61");
INSERT INTO Compone values ("Up-Elo", "antonio");
INSERT INTO Compone values ("Up-Elo", "adolfo");

INSERT INTO Compone values ("Down-Elo", "ambrogio");
INSERT INTO Compone values ("Down-Elo", "adrain90");
INSERT INTO Compone values ("Down-Elo", "alana61");
INSERT INTO Compone values ("Down-Elo", "antonio");
INSERT INTO Compone values ("Down-Elo", "adolfo");

INSERT INTO Compone values ("Med-Elo", "alayna.weimann");
INSERT INTO Compone values ("Med-Elo", "alexandrea.torp");
INSERT INTO Compone values ("Med-Elo", "alexis05");
INSERT INTO Compone values ("Med-Elo", "amelia35");
INSERT INTO Compone values ("Med-Elo", "bauch.arnulfo");
*/

INSERT INTO Compone values ("Med-Elo", "alayna.weimann");
INSERT INTO Compone values ("Med-Elo", "alexandrea.torp");
INSERT INTO Compone values ("Med-Elo", "alexis05");
INSERT INTO Compone values ("Med-Elo", "amelia35");
INSERT INTO Compone values ("Med-Elo", "alayna.weimann");

INSERT INTO Compone values ("Desperados", "adonis.koss");
INSERT INTO Compone values ("Desperados", "adrain90");
INSERT INTO Compone values ("Desperados", "alana61");
INSERT INTO Compone values ("Desperados", "antonio");
INSERT INTO Compone values ("Desperados", "adolfo");

INSERT INTO Compone values ("Down-Elo", "alayna.weimann");
INSERT INTO Compone values ("Down-Elo", "alexandrea.torp");
INSERT INTO Compone values ("Down-Elo", "alexis05");
INSERT INTO Compone values ("Down-Elo", "amelia35");
INSERT INTO Compone values ("Down-Elo", "alayna.weimann");
-- INSERT INTO Compone values ("Med-Elo", "ambrogio");

-- INSERIMENTO MATCH
/*
INSERT INTO `Match` (idGioco, team1, team2, teamVincitore, durata, data) values (3, "Desperados", "Up-Elo", "Up-Elo", 2000, '2015-01-02');
INSERT INTO `Match` (idGioco, team1, team2, teamVincitore, durata, data) values (3, "Up-Elo", "Desperados", "Up-Elo", 2000, '2015-01-02');
INSERT INTO `Match` (idGioco, team1, team2, teamVincitore, durata, data) values (3, "Up-Elo", "Desperados", "Up-Elo", 2000, '2015-01-02');
INSERT INTO `Match` (idGioco, team1, team2, teamVincitore, durata, data) values (3, "Desperados", "Med-Elo", "Med-Elo", 2000, '2015-01-02');
INSERT INTO `Match` (idGioco, team1, team2, teamVincitore, durata, data) values (3, "Desperados", "Med-Elo", "Med-Elo", 2000, '2015-01-02');
INSERT INTO `Match` (idGioco, team1, team2, teamVincitore, durata, data) values (3, "Desperados", "Med-Elo", "Med-Elo", 2000, '2015-01-02');
*/
INSERT INTO `Match` (idGioco, team1, team2, teamVincitore, durata, data) values (3, "Desperados", "Med-Elo", "Desperados", 200, '2015-01-02');
INSERT INTO `Match` (idGioco, team1, team2, teamVincitore, durata, data) values (3, "Desperados", "Med-Elo", "Desperados", 200, '2015-01-02');
INSERT INTO `Match` (idGioco, team1, team2, teamVincitore, durata, data) values (3, "Desperados", "Med-Elo", "Desperados", 200, '2015-01-02');
INSERT INTO `Match` (idGioco, team1, team2, teamVincitore, durata, data) values (3, "Desperados", "Med-Elo", "Desperados", 200, '2015-01-02');
INSERT INTO `Match` (idGioco, team1, team2, teamVincitore, durata, data) values (3, "Desperados", "Med-Elo", "Desperados", 200, '2015-01-02');
INSERT INTO `Match` (idGioco, team1, team2, teamVincitore, durata, data) values (3, "Desperados", "Down-Elo", "Down-Elo", 200, '2015-01-02');

-- INSERIMENTO SERVERGAME
INSERT INTO ServerGame values (4);
INSERT INTO ServerGame values (5);

-- INSERIMENTO SERVERS
INSERT INTO Server (idGioco, latenzaMedia) values (4, 24);
INSERT INTO Server (idGioco, latenzaMedia) values (4, 60);
INSERT INTO Server (idGioco, latenzaMedia) values (4, 45);
INSERT INTO Server (idGioco, latenzaMedia) values (5, 20);
INSERT INTO Server (idGioco, latenzaMedia) values (5, 25);

-- INSERIMENTO SALVA
INSERT INTO Salva (username, idServer, nome) values ("antonio", 1, "preferito");
INSERT INTO Salva (username, idServer, nome) values ("antonio", 4, "preferito Cod");

-- INSERIMENTO DEVELOPER
INSERT INTO Developer (sedeLegale, nome) values ("", "Santa Monica Studios");
INSERT INTO Developer (sedeLegale, nome) values ("", "CD Project RED");
INSERT INTO Developer (sedeLegale, nome) values ("", "Riot");

-- INSERIMENTO SVILUPPA
INSERT INTO Sviluppa values (1, 1, "Grafica");
INSERT INTO Sviluppa values (1, 2, "Grafica");
INSERT INTO Sviluppa values (3, 3, "Interattività");
INSERT INTO Sviluppa values (1, 5, "Sound");
 
 -- INSERIMENTO CATEGORIE
 INSERT INTO Categoria values("Action");
 INSERT INTO Categoria values("FPS");
 INSERT INTO Categoria values("FFA");
 
 -- INSERIMENTO APPARTIENE
 INSERT INTO Appartiene values (3, "Action");
 INSERT INTO Appartiene values (3, "FPS");
 INSERT INTO Appartiene values (4, "FPS");
 INSERT INTO Appartiene values (4, "FFA");
 INSERT INTO Appartiene values (5, "FPS");

-- INSERIMENTO CHANGELOG
INSERT INTO Changelog (idGioco, descrizione, URL, specifiche) values (3, "descrizione", "", "specifiche");
INSERT INTO Changelog (idGioco, descrizione, URL, specifiche) values (3, "descrizione", "", "specifiche");
INSERT INTO Changelog (idGioco, descrizione, URL, specifiche) values (3, "descrizione", "", "specifiche");
INSERT INTO Changelog (idGioco, descrizione, URL, specifiche) values (4, "descrizione", "", "specifiche");
INSERT INTO Changelog (idGioco, descrizione, URL, specifiche) values (1, "descrizione", "", "specifiche");

-- INSERIMENTO ACHIEVEMENT
INSERT INTO Achievement values ("Primo Sangue", 3, "Hai ucciso il tuo primo nemico");
INSERT INTO Achievement values ("Ultimo a sopravvivere", 4, "Sei rimasto l'ultimo in vita in una modalità di gioco");
INSERT INTO Achievement values ("L'inizio", 1, "Hai iniziato la tua avventura!");

INSERT INTO Possiede values ("antonio", 3, '2002-01-01', 20);
INSERT INTO Possiede values ("antonio", 4, '2003-01-01', 10);
INSERT INTO Possiede values ("adolfo", 3, '2020-02-04', 0);
INSERT INTO Possiede values ("adolfo", 4, '2004-01-01', 20);
INSERT INTO Possiede values ("ambrogio", 4, '2004-01-01', 0); 

-- INSERIMENTO SBLOCCA
INSERT INTO Sblocca values ("antonio", "Primo Sangue", 3);
INSERT INTO Sblocca values ("adolfo", "Primo Sangue", 3);
-- INSERT INTO Sblocca values ("antonio", "L'inizio", 1);

-- INSERIMENTO POSSIEDE
/*
INSERT INTO Possiede (username, idGioco, dataAcquisto) values ("antonio", 3, '2002-01-01');
INSERT INTO Possiede (username, idGioco, dataAcquisto) values ("antonio", 4, '2003-01-01');
INSERT INTO Possiede (username, idGioco, dataAcquisto) values ("antonio2", 4, '2004-01-01'); 
*/
