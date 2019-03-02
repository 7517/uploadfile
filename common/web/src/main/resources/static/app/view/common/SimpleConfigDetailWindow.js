Ext.define('AM.view.common.SimpleConfigDetailWindow', {
    extend: 'Ext.window.Window'
    ,xtype: 'common.SimpleConfigDetailWindow'
    ,autoScroll: true
    ,height: 350
    ,width: 700
    ,layout: {
        type: 'fit'
    }
    ,title: '通用配置详细信息'
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
                            itemId: 'configTypeField'
                            ,padding: '5 0 0 5'
                            ,name: 'configType'
                            ,fieldLabel: '配置类型'
                            ,renderer: function (value, field) {

                                var record = me.down('form').getForm().getRecord();

                                return record.get('configTypeVO').typeName;
                            }
                        }
                        ,{
                            itemId: 'displayNameField'
                            ,padding: '5 0 0 5'
                            ,name: 'displayName'
                            ,fieldLabel: '参数名称'
                        }
                        ,{
                            itemId: 'codeField'
                            ,padding: '5 0 0 5'
                            ,name: 'code'
                            ,fieldLabel: '参数代码'
                        }
                        ,{
                            itemId: 'valueField'
                            ,padding: '5 0 0 5'
                            ,name: 'value'
                            ,fieldLabel: '参数值'
                        }
                        ,{
                            itemId: 'vIndexField'
                            ,padding: '5 0 0 5'
                            ,name: 'vIndex'
                            ,fieldLabel: '展现顺序'
                        }
                        ,{
                            anchor: '98% 70%'
                            ,itemId: 'descriptionField'
                            ,padding: '5 0 0 5'
                            ,name: 'description'
                            ,fieldLabel: '参数说明'
                            ,labelAlign: 'top'
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