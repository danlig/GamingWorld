use dbTest;

/*
SELECT C.nome, SUM(P.OreGiocate) AS Ore
FROM Categoria as C, Appartiene as A, Gioco as G, Possiede as P
WHERE C.nome = A.nomeCategoria AND A.idGioco = G.idGioco AND P.idGioco = G.idGioco
GROUP BY C.nome;
*/



/*
SELECT username
FROM Account
WHERE 0.5 < (SELECT   
(SELECT Count(*)
	FROM Team T, `Match` M
	WHERE (T.nome = M.team1 OR T.nome = M.team2) 
		AND (T.username1 = username OR T.username2 = username OR T.username3 = username OR T.username4 = username OR T.username5 = username )
		AND M.teamVincitore = T.nome ) 
/ Count(*) as WinRate
		FROM Team T, `Match` M 
    WHERE (T.nome = M.team1 OR T.nome = M.team2) AND (T.username1 = username OR T.username2 = username OR T.username3 = username OR T.username4 = username OR T.username5 = username) );
*/




SELECT   
(SELECT Count(*)
	FROM Team T, `Match` M
	WHERE (T.nome = M.team1 OR T.nome = M.team2) 
    AND (T.username1 = "antonio" OR T.username2 = "antonio" OR T.username3 = "antonio" OR T.username4 = "antonio" OR T.username5 = "antonio" )
	AND M.teamVincitore = T.nome) / Count(*) as WinRate
FROM Team T, `Match` M 
WHERE (T.nome = M.team1 OR T.nome = M.team2) AND (T.username1 = "antonio" OR T.username2 = "antonio" OR T.username3 = "antonio" OR T.username4 = "antonio" OR T.username5 = "antonio" );



/*
SELECT T.nome, COUNT(*) as Vittorie
FROM Team as T, `Match` as M
WHERE (T.nome = M.team1 OR T.nome = M.team2) AND T.nome = M.teamVincitore
GROUP BY T.nome
ORDER BY Vittorie;
*/



/*
SELECT T.nome, (SELECT Count(*) as V FROM `Match` as M WHERE (T.nome = M.team1 OR T.nome = M.team2) AND T.nome = M.teamVincitore) as Vittorie
FROM Team as T
order by Vittorie;
*/



/*
SELECT DISTINCT P.username
FROM Amicizia as AM, Possiede as P, Gioco as G
WHERE G.nome = "LeagueOfLegends" AND P.idGioco = G.idGioco AND
(accountAccettante = "antonio2" AND P.username = accountRichiedente) 
	OR (accountRichiedente = "antonio2" AND P.username = accountAccettante);
*/











