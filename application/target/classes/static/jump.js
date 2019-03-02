
Ext.application({
    name: 'AM'
    ,launch: function () {

        var url = window.location.href;


        console.log("href:"+window.location.href)
        var query =  url.split("?")[1];
        console.log("query:"+query)
		console.log(Ext.Object.fromQueryString(query))
        var token = Ext.Object.fromQueryString(query).token
        console.log("token:"+token)
        Ext.Ajax.request({
            //获取当前应用
            url: 'security/login/jump'
            ,method: 'POST'
			,params:{token:token}
            ,scope: this
            ,success: function(response, opts) {
                console.log("success");
                var result = Ext.decode(response.responseText)
                console.log(result)
                if (result.success){
                    window.location = "index.html";
                }
            }
        });
        console.log(123123123)
    }
});