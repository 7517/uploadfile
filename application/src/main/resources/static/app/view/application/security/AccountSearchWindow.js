Ext.define('AM.view.application.security.AccountSearchWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.applicationSecurityAccountSearchWindow',
    autoScroll: true,
    height: 250,
    width: 400,
    layout: {
        type: 'fit'
    },
    title: '账号高级查询',
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

                                        ,{
                                            xtype: 'textfield',

                                            anchor: '96%',
                                            itemId: 'nickNameField',
                                            padding: '5 0 0 5',
                                            name: 'nickName',
                                            fieldLabel: '昵称',
                                            labelAlign: 'top'
                                        }
                                        ,{
                                            xtype: 'textfield',

                                            anchor: '96%',
                                            itemId: 'accountNameField',
                                            padding: '5 0 0 5',
                                            name: 'accountName',
                                            fieldLabel: '账号',
                                            labelAlign: 'top'
                                        }
                                        ,{
                                            xtype: 'textfield',

                                            anchor: '96%',
                                            itemId: 'emailField',
                                            padding: '5 0 0 5',
                                            name: 'email',
                                            fieldLabel: '邮箱',
                                            labelAlign: 'top'
                                        }
                                        ,{
                                            xtype: 'combobox',
                                            store: [
                                                [true,'是'],
                                                [false,'否']
                                            ],

                                            anchor: '96%',
                                            itemId: 'enableField',
                                            padding: '5 0 0 5',
                                            name: 'enable',
                                            fieldLabel: '已启用',
                                            labelAlign: 'top'
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

                                        ,{
                                            xtype: 'textfield',
                                            anchor: '96%',
                                            itemId: 'nameField',
                                            padding: '5 0 0 5',
                                            name: 'name',
                                            fieldLabel: '姓名',
                                            labelAlign: 'top'
                                        }
                                        ,{
                                            xtype: 'textfield',
                                            anchor: '96%',
                                            itemId: 'mobileField',
                                            padding: '5 0 0 5',
                                            name: 'mobile',
                                            fieldLabel: '手机号',
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

        var nickNameField = me.down("#nickNameField");
        var nameField = me.down("#nameField");
        var accountNameField = me.down("#accountNameField");
        var mobileField = me.down("#mobileField");
        var emailField = me.down("#emailField");
        var enableField = me.down("#enableField");

        var condition = {
            nickName:nickNameField.getValue()==""?null:nickNameField.getValue(),
            name:nameField.getValue()==""?null:nameField.getValue(),
            accountName:accountNameField.getValue()==""?null:accountNameField.getValue(),
            mobile:mobileField.getValue()==""?null:mobileField.getValue(),
            email:emailField.getValue()==""?null:emailField.getValue(),
            enable:enableField.getValue()==""?null:enableField.getValue()
        };

        this.store.proxy.extraParams={searchCondition:condition};
        this.store.load({
            params:{
                start:0,
                page:0
            }
        });


        this.hide();
    }
    ,onRestButtonClick: function (button, e, options) {

        this.down('form').getForm().reset();

        this.store.proxy.extraParams={searchCondition:{}};
            this.store.load({
            params:{
                start:0,
                page:0
            }
        });


        this.hide();
    }

    ,setStore: function (store) {
        this.store = store;
    }

});