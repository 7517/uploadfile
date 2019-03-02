Ext.define('AM.view.common.SimpleConfigSearchWindow', {
    extend: 'Ext.window.Window'
    ,xtype: 'common.SimpleConfigSearchWindow'
    ,alias: 'widget.commonSimpleConfigSearchWindow'
    ,autoScroll: true
    ,height: 250
    ,width: 400
    ,layout: {
        type: 'fit'
    }
    ,title: '通用配置高级查询'
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
                                            xtype: 'combobox',
                                            store: Ext.create("AM.store.common.SimpleConfigTypeStore"),
                                            typeAhead:false,
                                            editable:false,
                                            displayField:'typeName',
                                            valueField:'typeCode',
                                            itemId: 'configTypeField',
                                            name: 'configType',
                                            fieldLabel: '配置类型'
                                        }
                                        ,{
                                            xtype: 'textfield',
                                            itemId: 'displayNameField',
                                            name: 'displayName',
                                            fieldLabel: '参数名称'
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
                                            xtype: 'numberfield',
                                            allowDecimals:false,
                                            anchor: '96%',
                                            itemId: 'tidField',
                                            padding: '5 0 0 5',
                                            name: 'tid',
                                            fieldLabel: '租户ID',
                                            labelAlign: 'top'
                                        }
                                        ,{
                                            xtype: 'textfield',
                                            anchor: '96%',
                                            itemId: 'codeField',
                                            padding: '5 0 0 5',
                                            name: 'code',
                                            fieldLabel: '参数代码',
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
        var configTypeField = me.down("#configTypeField");
        var tidField = me.down("#tidField");
        var displayNameField = me.down("#displayNameField");
        var codeField = me.down("#codeField");

        var condition = {
            configType:Ext.isEmpty(configTypeField.getValue())?null:configTypeField.getValue()
            ,tid:Ext.isNumber(tidField.getValue())?tidField.getValue():null
            ,displayName:Ext.isEmpty(displayNameField.getValue())?null:displayNameField.getValue()
            ,code:Ext.isEmpty(codeField.getValue())?null:codeField.getValue()
        };

        return condition;
    }

});