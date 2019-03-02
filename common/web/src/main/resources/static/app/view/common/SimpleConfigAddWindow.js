Ext.define('AM.view.common.SimpleConfigAddWindow', {
    extend: 'Ext.window.Window'
    ,xtype: 'common.SimpleConfigAddWindow'
    ,requires:[
        'AM.store.common.SimpleConfigTypeStore'
    ]
    ,autoScroll: true
    ,height: 350
    ,width: 600
    ,layout: {
        type: 'fit'
    }
    ,title: '添加新通用配置'
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
                            xtype: 'combobox',
                            store: Ext.create("AM.store.common.SimpleConfigTypeStore"),
                            typeAhead:false,
                            editable:false,
                            displayField:'typeName',
                            valueField:'typeCode',
                            allowBlank:false,
                            afterLabelTextTpl: [
                            '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>'
                            ],
                            itemId: 'configTypeField',
                            name: 'configType',
                            fieldLabel: '配置类型'

                        }
                        ,{
                            xtype: 'textfield',
                            allowBlank:false,
                            afterLabelTextTpl: [
                            '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>'
                            ],
                            itemId: 'displayNameField',
                            name: 'displayName',
                            fieldLabel: '参数名称'

                        }
                        ,{
                            xtype: 'textfield',
                            allowBlank:false,
                            afterLabelTextTpl: [
                            '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>'
                            ],
                            itemId: 'codeField',
                            name: 'code',
                            fieldLabel: '参数代码'

                        }
                        ,{
                            xtype: 'textfield',
                            allowBlank:false,
                            afterLabelTextTpl: [
                            '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>'
                            ],
                            itemId: 'valueField',
                            name: 'value',
                            fieldLabel: '参数值'

                        }
                        ,{
                            xtype: 'numberfield',
                            allowDecimals:false,
                            allowBlank:true,
                            itemId: 'vIndexField',
                            name: 'vIndex',
                            fieldLabel: '展现顺序'

                        }
                        ,{

                            xtype: 'textarea',
                            anchor: '96% 70%',
                            itemId: 'descriptionField',
                            padding: '5 0 0 5',
                            name: 'description',
                            fieldLabel: '参数说明',
                            labelAlign: 'top'
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
                Ext.MsgUtil.show('操作成功', '保存通用配置成功!');
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

        this.setTitle("修改通用配置信息");
        if(model.phantom){
            this.setTitle("新增通用配置信息");
        }
        this.down('form').getForm().loadRecord(model);
    }

})