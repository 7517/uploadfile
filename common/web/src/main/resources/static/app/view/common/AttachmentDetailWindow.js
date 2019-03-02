Ext.define('AM.view.common.AttachmentDetailWindow', {
    extend: 'Ext.window.Window'
    ,xtype: 'common.AttachmentDetailWindow'
    ,autoScroll: true
    ,height: 350
    ,width: 700
    ,layout: {
        type: 'fit'
    }
    ,title: 'Attachment详细信息'
    ,maximizable: true
    ,closeAction:'hide'
    ,initComponent: function () {
        var me = this;

        Ext.apply(me, {
            items: [
                {
                    xtype: 'form'
                    ,autoScroll: true
                    ,bodyPadding: 10
                    ,layout: {
                        type: 'anchor'
                    }
                    ,defaults: {
                        labelAlign: 'right'
                        ,xtype: 'displayfield'
                        ,padding: '5 0 0 5'
                        ,anchor: '100%'
                        ,labelWidth:150
                    }
                    ,items: [
                        ,{
                            itemId: 'nameField'
                            ,padding: '5 0 0 5'
                            ,name: 'name'
                            ,fieldLabel: '文件名'
                        }
                        ,{
                            itemId: 'typeField'
                            ,padding: '5 0 0 5'
                            ,name: 'type'
                            ,fieldLabel: '类型'
                        }
                        ,{
                            itemId: 'disabledField'
                            ,padding: '5 0 0 5'
                            ,name: 'disabled'
                            ,fieldLabel: '是否有效'
                        }
                        ,{
                            itemId: 'newFileNameField'
                            ,padding: '5 0 0 5'
                            ,name: 'newFileName'
                            ,fieldLabel: '存储服务器上文件名称'
                        }
                        ,{
                            itemId: 'contentTypeField'
                            ,padding: '5 0 0 5'
                            ,name: 'contentType'
                            ,fieldLabel: '文件类型'
                        }
                        ,{
                            itemId: 'sizeField'
                            ,padding: '5 0 0 5'
                            ,name: 'size'
                            ,fieldLabel: '文件大小'
                        }
                        ,{
                            itemId: 'createTimeField'
                            ,padding: '5 0 0 5'
                            ,name: 'createTime'
                            ,fieldLabel: '创建时间'
                            ,renderer: function (value, field) {
                                return Ext.Date.format(value, 'Y-m-d H:i:s')
                            }
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    }

    ,setModel: function (model) {
        if (model && model.get('id')) {
            this.down('form').getForm().loadRecord(model);

        } else {
            this.down('form').getForm().reset();

        }
    }

});