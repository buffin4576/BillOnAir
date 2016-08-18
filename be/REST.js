var mysql = require("mysql");

function REST_ROUTER(router,connection,md5) {
    var self = this;
    self.handleRoutes(router,connection,md5);
}

REST_ROUTER.prototype.handleRoutes= function(router,connection,md5) {
    /*router.get("/",function(req,res){
        res.json({"Message" : "Hello World !"});
    });
    /*
	router.get("/home",function(req,res){
		res.json({"Message" : "Homepage!"})
	});*/
/*
	router.get('/users',function(req,res){
        console.log("safdsfdasd");
		var query = 'select utenti.username from Users';
		var params = [0];
		query = mysql.format(query,params);
		connection.query(query,function(err,rows){
			if(err)
				res.json({'Error':true, 'Message':'Error executing MySQL query'});
			else
			{
				res.json(rows[0]);
			}
		});
	});*/

    /**************** UTENTI *****************///start
    
	router.post('/users/register',function(req,res){
        //console.log('CHIAMATA');
		var query = "INSERT INTO `billonair`.`utenti` (`username`, `password`) VALUES (?, ?);";
		var params = [req.body.username,req.body.password];
		query = mysql.format(query,params);
		console.log(query);
		connection.query(query,function(err,rows){
			if(err)
				res.json({'Error':true, 'Message':'Error executing MySQL query', 'err':err});
			else
                res.json({'Message':'Utente inserito'});
			
		});
	});
    
    router.post('/users/login',function(req,res){
        var query = "SELECT * FROM billonair.utenti where username=? and password=?;";
        var params = [req.body.username,req.body.password];
        query = mysql.format(query,params);
        console.log(query);
        connection.query(query,function(err){
            if(err)
                res.json({'Error':true, 'Message':'Error executing MySQL query', 'err':err});
            else
                res.json({'Message':'Login'});
        });
    });
    
    //close
    
    /**************** QUERIES *****************///start
    
    router.post('/users/query',function(req,res){
        var query = "INSERT INTO billonair.queries (query, timestamp, username) VALUES (?,?,?)";
        var params = [req.body.query,req.body.timestamp,req.body.username];
        query = mysql.format(query,params);
        console.log(query);
		connection.query(query,function(err,rows){
			if(err)
				res.json({'Error':true, 'Message':'Error executing MySQL query', 'err':err});
			else
                res.json({'Message':'Query inserita'});
		});
    });
    
    router.get('/query/:username/:timestamp',function(req,res){
		var query = 'select queries.query from queries where queries.username=? and timestamp>?';
		var params = [req.params.username,req.params.timestamp];
		query = mysql.format(query,params);
        console.log(query);
		connection.query(query,function(err,rows){
			if(err)
				res.json({'Error':true, 'Message':'Error executing MySQL query'});
			else
			{
                /*res.setHeader('Content-Type', 'text/html');
                res.send(JSON.stringify(rows));*/
                res.json(rows);
                //res.writeHead(200, {'Content-Type': 'application/json'}).json(rows);
			}
		});
	});
    
    //close
    
    /**************** STANZE ******************///start
    
    //ottiene lista stanze
    router.get('/stanza/user/:username',function(rew, res){
        var query = "select * FROM billonair.stanze where username=?;";
        var params = [req.params.username];
        query = mysql.format(query,params);
        console.log(query);
		connection.query(query,function(err,rows){
			if(err)
				res.json({'Error':true, 'Message':'Error executing MySQL query', 'err':err});
			else
                res.json(rows);
		});
    });
    
    //crea la stanza
    router.post('/stanza',function(rew, res){
        var query = "INSERT INTO `billonair`.`stanze` (`idStanza`, `username`, `nome`) VALUES ('-1', ?, ?)";
        var params = [req.body.username, req.body.nome];
        query = mysql.format(query,params);
        console.log(query);
		connection.query(query,function(err,rows){
			if(err)
				res.json({'Error':true, 'Message':'Error executing MySQL query', 'err':err});
			else
                res.json({'Message':'Stanza inserita'});
		});
    });
    
    //aggiungi utente nella stanza
    router.post('/stanza/:idStanza',function(rew, res){
        var utenti = req.body.utenti;
        
        for(var i = 0; i < utenti.length;i++)
        {
            var query = "INSERT INTO `billonair`.`stanze` (`idStanza`, `username`, `nome`) VALUES (?, ?, ?)";
            var params = [req.params.idStanza, utenti[i], req.body.nome];
            query = mysql.format(query,params);
            console.log(query);
            connection.query(query,function(err,rows){
                if(err)
                    res.json({'Error':true, 'Message':'Error executing MySQL query', 'err':err});
                else
                    res.json({'Message':'Utente inserito nella stanza'});
            });
        }
        
    });
    
    //rimuovi utente dalla stanza
    router.delete('/stanza/:idStanza/:username',function(rew, res){
        var query = "DELETE FROM `billonair`.`stanze` WHERE `idStanza`=? and`username`=?";
        var params = [req.params.idStanza, req.params.username];
        query = mysql.format(query,params);
        console.log(query);
		connection.query(query,function(err,rows){
			if(err)
				res.json({'Error':true, 'Message':'Error executing MySQL query', 'err':err});
			else
                res.json({'Message':'Utente rimosso dalla stanza'});
		});
    });
    
    
    
    //rimuovi stanza
    router.delete('/stanza/:idStanza',function(rew, res){
        var query = "DELETE FROM `billonair`.`stanze` WHERE `idStanza`=?";
        var params = [req.params.idStanza];
        query = mysql.format(query,params);
        console.log(query);
		connection.query(query,function(err,rows){
			if(err)
				res.json({'Error':true, 'Message':'Error executing MySQL query', 'err':err});
			else
                res.json({'Message':'Stanza eliminata'});
		});
    });
    
    //close
    
    /**************** SPESESTANZE ******************///start
    
    //ottieni tutte le spese di una stanza
    router.get('/spesestanza/:idStanza',function(req,res){
		var query = 'select * from spesestanza where spesestanza.idStanza=?';
		var params = [req.params.idStanza];
		query = mysql.format(query,params);
        console.log(query);
		connection.query(query,function(err,rows){
			if(err)
				res.json({'Error':true, 'Message':'Error executing MySQL query'});
			else
			{
				res.json(rows);
			}
		});
	});
    
    //aggiorna una spesa
    router.put('/spesestanza/spesa/:idSpesa',function(rew, res){
        var query = "UPDATE `billonair`.`spesestanza` SET `dovuto`=? WHERE `idSpesa`=? and`debitore`=?";
        var params = [req.params.idStanza,req.body.dovuto, req.body.debitore];
        query = mysql.format(query,params);
        console.log(query);
        connection.query(query,function(err,rows){
            if(err)
                res.json({'Error':true, 'Message':'Error executing MySQL query', 'err':err});
            else
                res.json({'Message':'Dovuto modificato'});
        });
        
    });
    
    
    //aggiunge una spesa
    router.post('/spesestanza/spesa',function(rew, res){
        var query = "INSERT INTO `billonair`.`spesestanza` (`idSpesa`, `creditore`, `debitore`, `nome`, `dovuto`, `idStanza`, `importo`) VALUES ('-1', ?, ?, ?, ?, ?, ?)";
        var params = [req.body.creditore,req.body.debitore, req.body.nome, req.body.dovuto, req.body.idStanza,req.body.importo];
        query = mysql.format(query,params);
        console.log(query);
        connection.query(query,function(err,rows){
            if(err)
                res.json({'Error':true, 'Message':'Error executing MySQL query', 'err':err});
            else
                res.json({'Message':'Spesa inserita'});
        });
        
    });
    
    //elimina una spesa
    router.delete('/spesestanza/:idSpesa',function(rew, res){
        var query = "DELETE FROM `billonair`.`spesestanza` WHERE `idSpesa`='1'";
        var params = [req.params.idSpesa];
        query = mysql.format(query,params);
        console.log(query);
        connection.query(query,function(err,rows){
            if(err)
                res.json({'Error':true, 'Message':'Error executing MySQL query', 'err':err});
            else
                res.json({'Message':'Spesa eliminata'});
        });
        
    });
    
    //close
    
}

module.exports = REST_ROUTER;
