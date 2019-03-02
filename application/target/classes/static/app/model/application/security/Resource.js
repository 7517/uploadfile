Ext.define('AM.model.application.security.Resource', {
    extend: 'Ext.data.Model',
    proxy: {
        type: "rest",
        writer:{writeRecordId:false, partialDataOptions:{changes:false}},
        headers:{"Accept":"application/json"},
        url: 'application/security/resource'
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
            name: 'url'
			,type:'string'
            ,allowNull:false
            ,critical:true
        }
    	,{
            name: 'type'
			,type:'string'
            ,allowNull:false
            ,critical:true
        }
    	,{
            name: 'code'
			,type:'string'
            ,allowNull:true
            ,critical:true
        }
    	,{
            name: 'parentId'
			,type:'string'
            ,allowNull:false
            ,critical:true
        }
    	,{
            name: 'orderIndex'
			,type:'int'
            ,allowNull:false
            ,critical:true
        }
    ]
});