Ext.define('AM.model.application.security.AccountRoleRelation', {
    extend: 'Ext.data.Model',
    proxy: {
        type: "rest",
        headers:{"Accept":"application/json"},
        writer:{writeRecordId:false, partialDataOptions:{changes:false}},
        url: 'application/security/accountRoleRelation'
    },
    fields: [
    	{
            name: 'id',
            type:'string',
            allowNull:true
        }
    	,{
            name: 'account'
			,type:'string'
            ,allowNull:false
            ,critical:true
        }
    	,{
            name: 'role'
			,type:'string'
            ,allowNull:false
            ,critical:true
        }
    ]
});