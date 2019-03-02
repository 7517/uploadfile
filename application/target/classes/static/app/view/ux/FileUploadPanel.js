Ext.define('AM.view.ux.FileUploadPanel', {
    extend: 'Ext.grid.Panel'
    ,xtype: 'fileuploadpanel'
    // ,frame:true
    ,requires:['AM.store.devp.ops.DevpOpsAttachmentStore']
    ,initComponent: function() {
        var me = this;

        Ext.apply(me, {
            width:'96%'
            ,padding: '0 10 0 15'
            ,selModel: 'checkboxmodel'

            // ,frame:true
            ,title:'附件'
            ,store:Ext.create('AM.store.devp.ops.DevpOpsAttachmentStore')
            ,collapsible:true
            ,border:true
            ,columnLines:true
            ,tbar: [
            {   xtype:'form'
                ,itemId:'fileForm'
                ,items:[{
                    xtype: 'filefield'
                    ,buttonOnly:true
                    , buttonText:'新增附件'
                    ,listeners:{
                        change:function (field, value) {
                            var form = me.down('#fileForm').getForm();

                            if (form.isValid()) {
                                form.submit({
                                    url: 'common/attachment/upload'
                                    ,waitMsg: '附件上传中...'
                                    ,success: function(formPanel, action) {
                                        console.log(action)
                                        var attachment = action.result.initialPreviewConfig[0];

                                        me.getStore().add({
                                            name:attachment.caption
                                            ,address:'common/attachment/download/'+attachment.extra.id
                                            ,code:attachment.extra.id
                                        });
                                    }
                                    ,failure: function(form, action) {
                                        console.log(action);
                                        Ext.Msg.alert('failure', action.failureType);
                                    }
                                });
                            }
                        }
                    }
                }]}
        ]
            ,columns: [
            {
                xtype: 'gridcolumn'
                ,dataIndex: 'name'
                ,menuDisabled: true
                ,text: '名称'
                ,flex: 1
                ,renderer: function (value, field, record) {
                    return '<a href="'+record.get('address')+'">'+record.get('name')+'</a>';
                }
            }
            ,{
                xtype: 'actioncolumn'
                ,menuDisabled: true
                ,width:30
                ,items: [{
                    iconCls: 'delete'
                    ,tooltip: '删除'
                    ,handler: function(grid, rowIndex, colIndex) {
                        var record = grid.getStore().getAt(rowIndex);
                        grid.getStore().remove(record);
                    }
                }]
            }

        ]
        })

        me.callParent(arguments);
    }
    ,save:function (record, type) {
        var me = this;
        console.log(record)
        console.log("type:"+type)
        me.getStore().each(function (data) {
            data.set('nexusRid', record.get('id'))
            data.set('nexusType', type)
            console.log(data);
        })
        me.getStore().sync({
            success: function (batch, options) {
                Ext.MsgUtil.show('操作成功','同步附件成功!');
            }
        })
    }
    ,reset:function (record, type) {

        var me = this;

        var id = -999;

        if( record && !record.phantom){
            id = record.get('id')
        }

        //处理附件的列表
        var attachmentCondition = {nexusRid:id, nexusType:type}

        var attachmentStore = me.getStore();
        attachmentStore.proxy.extraParams = {searchCondition:attachmentCondition};
        attachmentStore.load({
            params:{
                page:0,limit:1000
            }
        });
    }
    ,clean:function () {
        var me = this;
        var attachmentStore = me.getStore();
        attachmentStore.proxy.extraParams = {searchCondition:{nexusRid:-999}};
        attachmentStore.load({
            params:{
                page:0,limit:1000
            }
        });

    }

});