var mysql = require("mysql");

function REST_ROUTER(router,connection,md5) {
    var self = this;
    self.handleRoutes(router,connection,md5);
}

REST_ROUTER.prototype.handleRoutes= function(router,connection,md5) {
    router.get("/",function(req,res){
        res.json({"Message" : "Hello World !"});
    });
	router.get("/home",function(req,res){
		res.json({"Message" : "Homepage!"})
	});

	router.get('/users',function(req,res){
		var query = 'select Users.id_user,Users.username from Users';
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

	router.post('/users/register',function(req,res){
        //console.log('CHIAMATA');
		var query = "INSERT INTO `billonair`.`users` (`username`, `password`) VALUES (?, ?);";
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
        var query = "SELECT * FROM billonair.users where username=? and password=?;";
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
}

module.exports = REST_ROUTER;
