Ext.define('AM.view.application.security.ResourceSearchWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.applicationSecurityResourceSearchWindow',

    autoScroll: true,
    height: 250,
    width: 400,
    layout: {
        type: 'fit'
    },
    title: '资源高级查询',
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
                                            itemId: 'nameField',
                                            padding: '5 0 0 5',
                                            name: 'name',
                                            fieldLabel: '资源名',
                                            labelAlign: 'top'
                                        }
                                        ,{
                                            xtype: 'textfield',

                                            anchor: '96%',
                                            itemId: 'typeField',
                                            padding: '5 0 0 5',
                                            name: 'type',
                                            fieldLabel: '资源类型',
                                            labelAlign: 'top'
                                        }
                                        ,{
                                            xtype: 'textfield',

                                            anchor: '96%',
                                            itemId: 'parentIdField',
                                            padding: '5 0 0 5',
                                            name: 'parentId',
                                            fieldLabel: '父节点',
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
                                            itemId: 'urlField',
                                            padding: '5 0 0 5',
                                            name: 'url',
                                            fieldLabel: '资源链接',
                                            labelAlign: 'top'
                                        }
                                        ,{
                                            xtype: 'textfield',
                                            anchor: '96%',
                                            itemId: 'codeField',
                                            padding: '5 0 0 5',
                                            name: 'code',
                                            fieldLabel: '资源代码',
                                            labelAlign: 'top'
                                        }
                                        ,{
                                            xtype: 'textfield',
                                            anchor: '96%',
                                            itemId: 'orderIndexField',
                                            padding: '5 0 0 5',
                                            name: 'orderIndex',
                                            fieldLabel: '排序',
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

        var nameField = me.down("#nameField");
        var urlField = me.down("#urlField");
        var typeField = me.down("#typeField");
        var codeField = me.down("#codeField");
        var parentIdField = me.down("#parentIdField");
        var orderIndexField = me.down("#orderIndexField");

        var condition = {
            name:nameField.getValue()==""?null:nameField.getValue(),
            url:urlField.getValue()==""?null:urlField.getValue(),
            type:typeField.getValue()==""?null:typeField.getValue(),
            code:codeField.getValue()==""?null:codeField.getValue(),
            parentId:parentIdField.getValue()==""?null:parentIdField.getValue(),
            orderIndex:orderIndexField.getValue()==""?null:orderIndexField.getValue()
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