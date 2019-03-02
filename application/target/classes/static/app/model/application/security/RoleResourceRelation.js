Ext.define('AM.model.application.security.RoleResourceRelation', {
    extend: 'Ext.data.Model',
    proxy: {
        type: "rest",
        headers:{"Accept":"application/json"},
        writer:{writeRecordId:false, partialDataOptions:{changes:false}},
        url: 'application/security/roleResourceRelation'
    },
    fields: [
    	{
            name: 'id',
            type:'string',
            allowNull:true
        }
    	,{
            name: 'resourceId'
			,type:'string'
            ,allowNull:false
            ,critical:true
        }
    	,{
            name: 'roleId'
			,type:'string'
            ,allowNull:false
            ,critical:true
        }
    ]
});