Ext.define('AM.model.common.SimpleConfigType', {
    extend: 'Ext.data.Model',
    proxy: {
        type: "rest",
        writer:{writeRecordId:false, partialDataOptions:{changes:false}},
        headers:{"Accept":"application/json"},
        url: 'common/simpleConfigType'
        ,listeners: {
            exception: {
                fn:  function(server, response, operation, options) {
                    var errorBody = Ext.decode(response.responseText);
                    Ext.MessageBox.alert('操作失败('+errorBody.code+')', errorBody.message);
                }
            }
        }
    },
    fields: [
    	{
            name: 'id'
            ,type:'string'
            ,allowNull:true
        }
    	,{
            name: 'typeName'
            ,type:'string'
            ,allowNull:true
            ,critical:true
        }
    	,{
            name: 'typeCode'
            ,type:'string'
            ,allowNull:true
            ,critical:true
        }
    ]
});