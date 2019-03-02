Ext.define('AM.model.application.security.Role', {
    extend: 'Ext.data.Model',
    proxy: {
        type: "rest",
        headers:{"Accept":"application/json"},
        url: 'application/security/role'
    },
    fields: [
    	{
            name: 'id',
            type:'string',
            allowNull:true
        }
    	,{
            name: 'name'
			,type:'string'
            ,allowNull:false
            ,critical:true
        }
    	,{
            name: 'description'
			,type:'string'
            ,allowNull:true
            ,critical:true
        }
    ]
});