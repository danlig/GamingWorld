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

