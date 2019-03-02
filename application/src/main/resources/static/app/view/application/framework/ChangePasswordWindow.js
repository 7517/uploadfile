Ext.define('AM.view.application.framework.ChangePasswordWindow', {
    extend: 'Ext.window.Window'
    ,xtype: 'application.framework.ChangePasswordWindow'
    ,requires:[
        'AM.model.application.security.UpdatePasswordRequest'
    ]

    ,height: '60%'
    ,width: '60%'
    ,layout: {
        type: 'vbox'
    }
    ,title: '修改密码'
    ,maximizable: true
    ,closeAction:'hide'
    ,initComponent: function () {
        var me = this;

        Ext.apply(me, {
            items: [
                {
                    xtype: 'form',
                    autoScroll: true,
                    bodyPadding: 10

                    ,width:'100%'
                    ,fieldDefaults: {
                        labelAlign: 'top'
                        ,msgTarget: 'side'
                        ,padding: '5 0 0 5'
                        ,blankText:'该字段为必填项'
                        ,anchor: '96%'
                    },
                    items: [
                        {
                            xtype:'container'
                            ,anchor: '96% 70%'
                            ,layout: {
                                type: 'table',
                                columns: 1,
                                tableAttrs: {
                                    style: {
                                        width: '100%'
                                    }
                                }
                            }
                            ,defaults:{width:'100%'}
                            ,items:[
                                ,{
                                    xtype: 'textfield'
                                    ,hidden: false
                                    ,readOnly:false
                                    ,allowBlank:false
                                    ,afterLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="Required">*</span>']
                                    ,itemId: 'oldPwdField'
                                    ,name: 'oldPwd'
                                    ,fieldLabel: '旧密码'
                                }
                                ,{
                                    xtype: 'textfield'
                                    ,hidden: false
                                    ,readOnly:false
                                    ,allowBlank:false
                                    ,afterLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="Required">*</span>']
                                    ,itemId: 'newPwdField'
                                    ,name: 'newPwd'
                                    ,fieldLabel: '新密码'
                                }
                                ,{
                                    xtype: 'textfield'
                                    ,hidden: false
                                    ,readOnly: false
                                    ,allowBlank: false
                                    ,afterLabelTextTpl: ['<span style="color:red;font-weight:bold" data-qtip="Required">*</span>']
                                    ,itemId: 'confirmField'
                                    ,name: 'confirm'
                                    ,fieldLabel: '再次输入新密码'
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
        var form = this.down('form').getForm();
        if (!form.isValid()) {
            Ext.MessageBox.alert('提交失败', '请检查数据项');
            return;
        }


        var oldPwd = this.down("#oldPwdField").getValue();
        var newPwd = this.down("#newPwdField").getValue();
        var confirm = this.down("#confirmField").getValue();

        if(newPwd != confirm){
            Ext.MessageBox.alert('提交失败', '两次输入的新密码不同, 请重新输入');
            return;
        }
        var record = Ext.create('AM.model.application.security.UpdatePasswordRequest', {oldPassword:oldPwd, newPassword:newPwd});

        //将form中的数据刷进record
        form.updateRecord(record);
        record.save({
            success: function (newRecord) {
                Ext.MsgUtil.notification('操作成功', '修改密码成功!');
                form.reset();
                me.hide();

            }
            ,failure: function(record, operation) {
                console.log(operation.responseText)
                var response = operation.getResponse();
                var responseBody = Ext.decode(response.responseText)

                Ext.MessageBox.alert('操作失败', "ERROR:"+response.status+"<br/>"+responseBody.message);
            }
        });



    }
});