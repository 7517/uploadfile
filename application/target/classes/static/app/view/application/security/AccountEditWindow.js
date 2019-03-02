Ext.define('AM.view.application.security.AccountEditWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.applicationSecurityAccountEditWindow',

    autoScroll: true,
    height: 400,
    width: 500,
    layout: {
        type: 'fit'
    },
    title: '账号详细信息',
    maximizable: true,
    closeAction:'hide',
    initComponent: function () {
        var me = this;

        Ext.apply(me, {
            items: [
                {
                    xtype: 'form',
                    autoScroll: true,
                    bodyPadding: 10,
                    fieldDefaults: {
                        labelAlign: 'top'
                        ,msgTarget: 'side'
                        ,padding: '5 0 0 5'
                        ,blankText:'该字段为必填项'
                        ,anchor: '96%'
                    },
                    items: [
                        {
                            xtype: 'container',
                            layout: {
                                type: 'column'
                            },
                            items: [
                                {
                                    xtype: 'container',
                                    columnWidth: 0.5,
                                    layout: {
                                        type: 'anchor'
                                    },
                                    items: [
                                        {
                                            xtype: 'hiddenfield',
                                            anchor: '100%',
                                            itemId: 'idField',
                                            name: 'id',
                                            fieldLabel: 'Label'
                                        }
                                        ,{
	                                        xtype: 'textfield',
                                            allowBlank:false,
                                            afterLabelTextTpl: [
                                            '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>'
                                            ],
                                            itemId: 'nickNameField',
                                            name: 'nickName',
                                            fieldLabel: '昵称'
                                        }
                                        ,{
	                                        xtype: 'textfield',
		                                    editable :false,
                                            afterLabelTextTpl: [
                                            '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>'
                                            ],
                                            itemId: 'accountNameField',
                                            name: 'accountName',
                                            fieldLabel: '账号'
                                        }
                                        ,{
	                                        xtype: 'textfield',
                                            allowBlank:true,
                                            itemId: 'emailField',
                                            name: 'email',
                                            fieldLabel: '邮箱',
                                            vtype:'email'
                                        }
                                        ,{
                                            xtype: 'combobox',
                                            store: [
                                                [true,'是'],
                                                [false,'否']
                                            ],
                                            allowBlank:false,
                                            afterLabelTextTpl: [
                                            '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>'
                                            ],
                                            itemId: 'enableField',
                                            name: 'enable',
                                            fieldLabel: '已启用'
                                            ,typeAhead:false
		                                    ,editable:false
                                        }

                                    ]
                                },
                                {
                                    xtype: 'container',
                                    columnWidth: 0.5,
                                    layout: {
                                        type: 'anchor'
                                    },
                                    items: [
                                        {
                                            xtype: 'hiddenfield',
                                            anchor: '100%',
                                            itemId: 'versionField',
                                            name: 'version',
                                            fieldLabel: 'Label'
                                        }
                                        ,{
	                                        xtype: 'textfield',
	                                        allowBlank:false,
	                                        afterLabelTextTpl: [
	                                        '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>'
	                                        ],
                                            itemId: 'nameField',
                                            name: 'name',
                                            fieldLabel: '姓名'
                                        }
                                        ,{
	                                        xtype: 'textfield',
	                                        allowDecimals:false,
	                                        allowBlank:true,
                                            itemId: 'mobileField',
                                            name: 'mobile',
                                            fieldLabel: '手机号',
		                                    validator: function (val) {
	                                            if(!val){
	                                                return true;
                                                }
			                                    // remove non-numeric characters
			                                    var tn = val.replace(/[^0-9]/g,''),
				                                    errMsg = "必须为11位的手机号码";
			                                    // if the numeric value is not 10 digits return an error message
			                                    return (tn.length === 11) ? true : errMsg;
		                                    }
                                        }
                                        ,{
	                                        xtype: 'numberfield',
	                                        allowDecimals:false,
	                                        allowBlank:false,
	                                        afterLabelTextTpl: [
	                                        '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>'
	                                        ],
                                            itemId: 'maxClientField',
                                            name: 'maxClient',
                                            fieldLabel: '最大接待人数'
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

        var id = this.down("#idField").getValue();

        var record = this.down('form').getForm().getRecord();


        //将form中得数据刷进record
        this.down('form').getForm().updateRecord(record);
        record.save({
            success: function (newRecord) {
                Ext.MsgUtil.show('操作成功', '保存修息成功!');
                me.down('form').getForm().loadRecord(newRecord);
                me.store.load({
                    callback: function (records, operation, success) {
                        if (success) {
                            Ext.MsgUtil.show('操作成功', '同步资源树成功');
                        }

                    }
                });
            }
        });


        this.hide(this.targetComp);
    },

    setModel: function (model) {
        if(!model){
            Ext.Msg.show({title: '操作失败', msg: "未设置模型", buttons: Ext.Msg.OK, icon: Ext.Msg.ERROR});
            return;
        }

        this.setTitle("修改账号信息");
        if(model.phantom){
            this.setTitle("新增账号信息");
        }
        this.down('form').getForm().loadRecord(model);
    },
    setStore: function (store) {
        this.store = store;
    }

});