Ext.define('AM.model.application.security.UpdatePasswordRequest', {
    extend: 'Ext.data.Model',
    proxy: {
        type: "rest",
        headers:{"Accept":"application/json"},
        url: 'security/login/updatePassword'
    },
    fields: [
    	{
            name: 'id',
            type:'string',
            allowNull:true
        }
    	,{
            name: 'oldPassword'
			,type:'string'
            ,allowNull:false
            ,critical:true
        }
    	,{
            name: 'newPassword'
			,type:'string'
            ,allowNull:false
            ,critical:true
        }
    ]
});