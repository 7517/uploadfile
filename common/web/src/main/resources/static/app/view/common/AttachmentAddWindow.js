Ext.define('AM.view.common.AttachmentAddWindow', {
    extend: 'Ext.window.Window'
    ,xtype: 'common.AttachmentAddWindow'
    ,requires:[
    ]
    ,autoScroll: true
    ,height: 350
    ,width: 600
    ,layout: {
        type: 'fit'
    }
    ,title: '添加新Attachment'
    ,maximizable: true
    ,closeAction: 'hide'
    ,initComponent: function () {
        var me = this;

        Ext.apply(me, {
            items: [
                {
                    xtype: 'form'
                    ,autoScroll: true
                    ,bodyPadding: 10
                    ,width:'100%'
                    ,fieldDefaults: {
                        labelAlign: 'right'
                        ,msgTarget: 'side'
                        ,padding: '5 0 0 5'
                        ,blankText:'该字段为必填项'
                        ,anchor: '96%'
                    }
                    ,

                    items: [
                        ,{
                            xtype: 'textfield',
                            allowBlank:true,
                            itemId: 'nameField',
                            name: 'name',
                            fieldLabel: '文件名'

                        }
                        ,{
                            xtype: 'textfield',
                            allowBlank:true,
                            itemId: 'typeField',
                            name: 'type',
                            fieldLabel: '类型'

                        }
                        ,{
                            xtype: 'combobox',
                            store: [
                                [true,'是'],
                                [false,'否']
                            ],
                            value:true,
                            typeAhead:false,
                            editable:false,
                            allowBlank:true,
                            itemId: 'disabledField',
                            name: 'disabled',
                            fieldLabel: '是否有效'

                        }
                        ,{
                            xtype: 'textfield',
                            allowBlank:true,
                            itemId: 'newFileNameField',
                            name: 'newFileName',
                            fieldLabel: '存储服务器上文件名称'

                        }
                        ,{
                            xtype: 'textfield',
                            allowBlank:true,
                            itemId: 'contentTypeField',
                            name: 'contentType',
                            fieldLabel: '文件类型'

                        }
                        ,{
                            xtype: 'numberfield',
                            allowDecimals:false,
                            allowBlank:true,
                            itemId: 'sizeField',
                            name: 'size',
                            fieldLabel: '文件大小'

                        }
                        ,{
                            xtype: 'datefield',
                            format: 'Y-m-d',
                            allowBlank:true,
                            itemId: 'createTimeField',
                            name: 'createTime',
                            fieldLabel: '创建时间'

                        }


                    ]
                }
            ],
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'bottom',
                    ui: 'footer',
                    items: [
                        {
                            xtype: 'tbfill'
                        },
                        {
                            xtype: 'button',
                            iconCls: 'accept',
                            text: '确定',
                            listeners: {
                                click: {
                                    fn: me.onSaveButtonClick,
                                    scope: me
                                }
                            }
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    },

    onSaveButtonClick: function (button, e, options) {
        var me = this;
        if (!this.down('form').getForm().isValid()) {
            Ext.MessageBox.alert('提交失败', '请检查数据项');
            return;
        }

        //var id = this.down("#idField").getValue();

        var record = this.down('form').getForm().getRecord();


        //将form中得数据刷进record
        this.down('form').getForm().updateRecord(record);
        record.save({
            success: function (newRecord) {
                Ext.MsgUtil.show('操作成功', '保存Attachment成功!');
                me.down('form').getForm().loadRecord(newRecord);
                me.fireEvent('saved');
                me.hide(this.targetComp);

            }
        });



    },

    setModel: function (model) {
        if(!model){
            Ext.Msg.show({title: '操作失败', msg: "未设置模型", buttons: Ext.Msg.OK, icon: Ext.Msg.ERROR});
            return;
        }

        this.setTitle("修改Attachment信息");
        if(model.phantom){
            this.setTitle("新增Attachment信息");
        }
        this.down('form').getForm().loadRecord(model);
    }

})