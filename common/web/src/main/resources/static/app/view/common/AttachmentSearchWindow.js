Ext.define('AM.view.common.AttachmentSearchWindow', {
    extend: 'Ext.window.Window'
    ,xtype: 'common.AttachmentSearchWindow'
    ,alias: 'widget.commonAttachmentSearchWindow'
    ,autoScroll: true
    ,height: 250
    ,width: 400
    ,layout: {
        type: 'fit'
    }
    ,title: 'Attachment高级查询'
    ,maximizable: true
    ,closeAction:'hide'
    ,initComponent: function () {
        var me = this;

        Ext.apply(me, {
            items: [
                {
                    xtype: 'form',
                    autoScroll: true,
                    bodyPadding: 10,
                    items: [
                        {
                            xtype: 'container',
                            layout: {
                                type: 'column'
                            }
                            ,items: [
                                {
                                    xtype: 'container',
                                    columnWidth: 0.5,
                                    layout: {
                                        type: 'anchor'
                                    }
                                    ,defaults:{
                                        xtype: 'textfield'
                                        ,labelAlign: 'top'
                                        ,padding: '5 0 0 5'
                                        ,anchor: '96%',
                                    }
                                    ,items: [

                                        ,{
                                            xtype: 'textfield',
                                            itemId: 'nameField',
                                            name: 'name',
                                            fieldLabel: '文件名'
                                        }
                                        ,{
                                            xtype: 'combobox',
                                            store: [
                                                [true,'是'],
                                                [false,'否']
                                            ],
                                            itemId: 'disabledField',
                                            name: 'disabled',
                                            fieldLabel: '是否有效'
                                        }
                                        ,{
                                            xtype: 'textfield',
                                            itemId: 'contentTypeField',
                                            name: 'contentType',
                                            fieldLabel: '文件类型'
                                        }
                                        ,{
                                            xtype: 'datefield',
                                            format: 'Y-m-d',
                                            itemId: 'createTimeField',
                                            name: 'createTime',
                                            fieldLabel: '创建时间'
                                        }

                                    ]
                                },
                                {
                                    xtype: 'container',
                                    columnWidth: 0.5,
                                    layout: {
                                        type: 'anchor'
                                    }
                                    ,defaults:{
                                        xtype: 'textfield'
                                        ,labelAlign: 'top'
                                        ,padding: '5 0 0 5'
                                        ,anchor: '96%',
                                    }
                                    ,items: [
                                        ,{
                                            xtype: 'textfield',
                                            anchor: '96%',
                                            itemId: 'typeField',
                                            padding: '5 0 0 5',
                                            name: 'type',
                                            fieldLabel: '类型',
                                            labelAlign: 'top'
                                        }
                                        ,{
                                            xtype: 'textfield',
                                            anchor: '96%',
                                            itemId: 'newFileNameField',
                                            padding: '5 0 0 5',
                                            name: 'newFileName',
                                            fieldLabel: '存储服务器上文件名称',
                                            labelAlign: 'top'
                                        }
                                        ,{
                                            xtype: 'numberfield',
                                            allowDecimals:false,
                                            anchor: '96%',
                                            itemId: 'sizeField',
                                            padding: '5 0 0 5',
                                            name: 'size',
                                            fieldLabel: '文件大小',
                                            labelAlign: 'top'
                                        }
                                    ]
                                }
                            ]
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
                        }

                        ,{
                            xtype: 'button',
                            iconCls: 'page_white',
                            text: '重置',
                            listeners: {
                                click: {
                                    fn: me.onRestButtonClick,
                                    scope: me
                                }
                            }
                        }
                        ,{
                            xtype: 'button',
                            iconCls: 'search',
                            text: '查询',
                            listeners: {
                                click: {
                                    fn: me.onSearchButtonClick,
                                    scope: me
                                }
                            }
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    }

    ,onSearchButtonClick: function (button, e, options) {

        var me = this;
        if (!this.down('form').getForm().isValid()) {
            Ext.MessageBox.alert('提交失败', '请检查数据项');
            return;
        }

        me.fireEvent('saved');
        me.hide();
    }
    ,onRestButtonClick: function (button, e, options) {
        var me = this;
        me.down('form').getForm().reset();

        me.fireEvent('saved');


    }
    ,getCondition: function(){

        var me = this;
        var nameField = me.down("#nameField");
        var typeField = me.down("#typeField");
        var disabledField = me.down("#disabledField");
        var newFileNameField = me.down("#newFileNameField");
        var contentTypeField = me.down("#contentTypeField");
        var sizeField = me.down("#sizeField");
        var createTimeField = me.down("#createTimeField");

        var condition = {
            name:Ext.isEmpty(nameField.getValue())?null:nameField.getValue()
            ,type:Ext.isEmpty(typeField.getValue())?null:typeField.getValue()
            ,disabled:Ext.isEmpty(disabledField.getValue())?null:disabledField.getValue()
            ,newFileName:Ext.isEmpty(newFileNameField.getValue())?null:newFileNameField.getValue()
            ,contentType:Ext.isEmpty(contentTypeField.getValue())?null:contentTypeField.getValue()
            ,size:Ext.isNumber(sizeField.getValue())?sizeField.getValue():null
            ,createTime:Ext.isEmpty(createTimeField.getValue())?null:Ext.Date.format(createTimeField.getValue(),'Y-m-d H:i:s')
        };

        return condition;
    }

});