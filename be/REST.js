var mysql = require("mysql");

function REST_ROUTER(router,connection,md5) {
    var self = this;
    self.handleRoutes(router,connection,md5);
}

REST_ROUTER.prototype.handleRoutes= function(router,connection,md5) {
    router.get("/",function(req,res){
        res.json({"Message" : "Hello World !"});
    });
    /*
	router.get("/home",function(req,res){
		res.json({"Message" : "Homepage!"})
	});*/

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
	});

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
    
    router.get('/users/:username/query/:timestamp',function(req,res){
		
		var query = 'select queries.query from queries where queries.username=? and timestamp>?';
		var params = [req.params.username,req.params.timestamp];
		query = mysql.format(query,params);
		connection.query(query,function(err,rows){
			if(err)
				res.json({'Error':true, 'Message':'Error executing MySQL query'});
			else
			{
				res.json(rows);
			}
		});
	});
    
    //close
    
    /**************** STANZE ******************///start
    
    
    
    //close
}

module.exports = REST_ROUTER;
