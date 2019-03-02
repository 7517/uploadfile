Ext.define('AM.model.common.Attachment', {
    extend: 'Ext.data.Model',
    proxy: {
        type: "rest",
        writer:{writeRecordId:false, partialDataOptions:{changes:false}},
        headers:{"Accept":"application/json"},
        url: 'common/attachment'
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
            name: 'name'
            ,type:'string'
            ,allowNull:true
            ,critical:true
        }
    	,{
            name: 'type'
            ,type:'string'
            ,allowNull:true
            ,critical:true
        }
    	,{
            name: 'disabled'
            ,type:'bool'
            ,allowNull:true
            ,critical:true
        }
    	,{
            name: 'newFileName'
            ,type:'string'
            ,allowNull:true
            ,critical:true
        }
    	,{
            name: 'contentType'
            ,type:'string'
            ,allowNull:true
            ,critical:true
        }
    	,{
            name: 'size'
            ,type:'string'
            ,allowNull:true
            ,critical:true
        }
    	,{
            name: 'createTime'
            ,type:'date'
            ,dateFormat: 'time'
            ,allowNull:true
            ,critical:true
        }
    ]
});