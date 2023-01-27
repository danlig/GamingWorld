use dbtest;

/*
					SELECT   
						(SELECT Count(*)
						FROM Team T, `Match` M, Compone C
						WHERE (T.nome = M.team1 OR T.nome = M.team2) 
						AND T.nome = C.nome
						AND C.componente = "ambrogio"
						AND M.teamVincitore = T.nome) / Count(*) as WinRate
						FROM Team T, `Match` M, Compone C
						WHERE (T.nome = M.team1 OR T.nome = M.team2) 
                        AND T.nome = C.nome
                        AND (C.componente = "ambrogio");
*/


/*
				SELECT username
				FROM Account
				WHERE 0.5 < (SELECT   
					(SELECT Count(*)
						FROM Team T, `Match` M, Compone C
						WHERE (T.nome = M.team1 OR T.nome = M.team2) 
						AND C.componente = username 
						AND M.teamVincitore = T.nome  
                        AND C.nome = T.nome )
				/ Count(*) as WinRate
				FROM Team T, `Match` M, Compone C 
				WHERE (T.nome = M.team1 OR T.nome = M.team2) AND C.componente = username AND C.nome = T.nome);
*/


/*
				SELECT COUNT(*) AS NumeroAmici
				FROM Account, Amicizia
				WHERE username = "antonio" 
				AND (
					username = accountRichiedente
					OR username = accountAccettante
				)
				AND data IS NOT NULL;	
*/


-- UPDATE Amicizia SET data = '2002-10-20' WHERE (accountAccettante = "antonio" AND accountRichiedente = "ambrogio") OR (accountAccettante = "ambrogio" AND accountRichiedente = "antonio");

SELECT accountRichiedente
FROM Amicizia
WHERE accountAccettante = "ambrogio" AND data IS NULL;

