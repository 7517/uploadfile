Ext.define('AM.view.common.SimpleConfigTypeMainPanel', {
    extend: 'Ext.panel.Panel'
    ,xtype: 'common.SimpleConfigTypeMainPanel'
    ,requires: [
        'AM.view.common.SimpleConfigTypePanel'
		,'AM.view.common.SimpleConfigPanel'
		,'AM.view.common.SimpleConfigTypeController'
    ]
	, layout: {
		type: 'border'
	}
    , title: '业务参数配置'

    , bodyCls: 'app-dashboard'
	, bodyPadding: '10 10'
	,controller: 'common_SimpleConfigTypeController'
    ,constructor:function(cfg){
        var me = this;
        cfg = cfg || {}

        me.callParent([Ext.apply({
            viewModel : {
                stores:{
                    simpleConfigTypeStore:Ext.create('AM.store.common.SimpleConfigTypeStore').applyCondition().load()
					,simpleConfigStore:Ext.create('AM.store.common.SimpleConfigStore')
                }
            }
        }, cfg)])
    }
    ,initComponent: function() {
        var me = this;

	    Ext.apply(me, {
		    items: [
                {
                    xtype: 'common.SimpleConfigTypePanel'
                    ,region: 'west'
                    ,split:true
                    ,collapsed: false
                    ,collapsible: false
                    ,width:'25%'
					,frame: true
                    ,itemId: 'simpleConfigTypeGrid'
                    ,reference: 'simpleConfigTypeGrid'
                    ,title:'配置类型'
                    ,bind:{store:'{simpleConfigTypeStore}'}
                    ,listeners: {
                        itemclick: 'onMainPanelRowClick'
                        ,edit:function (editor, e) {
                            e.record.save({
                                success:function(record){

                                    // me.getViewModel().set('simpleConfigType', record)

                                    var simpleConfigStore = me.getViewModel().getStore('simpleConfigStore')
                                    simpleConfigStore.applyCondition({configType:record.get('typeCode')}).load();
                                }
                            });
                        }
                        ,canceledit: function(editor, e) {
                            if(e.record.phantom){
                                e.record.drop()
                            }
                        }
                    }
                }
			    ,{
		    		xtype: 'grid'
					,region: 'center'
                    ,reference: 'simpleConfigPanel'
                    ,title:'配置项'
					,frame:true
					, bind:{store:'{simpleConfigStore}'}
                    ,columns: [
                        {
                            xtype: 'numbercolumn'
                            ,dataIndex: 'vIndex'
                            ,format:'0000'
                            ,text: '展现顺序'
                            ,editor:'textfield'
                        }
                        ,{
                            xtype: 'gridcolumn'
                            ,dataIndex: 'configType'
                            ,renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                                return record.get("configTypeVO")?record.get("configTypeVO").typeName:'';
                            }
                            ,text: '配置类型'

                        }
                        ,{
                            xtype: 'gridcolumn'
                            ,dataIndex: 'displayName'
                            ,text: '参数名称'
                            ,editor:{xtype: 'textfield', blankText:'必填字段', emptyText:'必填字段',allowBlank:false}
                            ,flex:1

                        }
                        ,{
                            xtype: 'gridcolumn'
                            ,dataIndex: 'code'
                            ,text: '参数代码'
                            ,editor:{xtype: 'textfield', blankText:'必填字段', emptyText:'必填字段',allowBlank:false}
                            ,flex:1
                        }
                        ,{
                            xtype: 'gridcolumn'
                            ,dataIndex: 'value'
                            ,text: '参数值'
                            ,editor:{xtype: 'textfield', blankText:'必填字段', emptyText:'必填字段',allowBlank:false}
                            ,flex:1
                        }
                        ,{
                            xtype: 'gridcolumn'
                            ,dataIndex: 'description'
                            ,text: '参数说明'
                            ,editor:'textfield'
                            ,flex:1
                        }
                        ,{
                            xtype: 'actioncolumn'
                            ,menuDisabled: true
                            ,width:30
                            ,items: [{
                                iconCls: 'fas fa-minus-circle red'
                                ,tooltip: '删除'
                                ,handler: function(grid, rowIndex, colIndex, item, event, record) {
                                    record.erase();
                                }
                            }]
                        }
                    ]
					,tbar:{
                        xtype: 'toolbar'
                        ,dock: 'top'
                        ,items: [
                            {
                                xtype: 'button'
                                ,iconCls: 'add'
                                ,text: '新增'
                                ,listeners: {
                                    click: 'onAddSimpleConfigButtonClick'
                                }
                            }
                        ]
                    }
                    ,plugins: [
                        {
                            ptype: 'rowediting'
                            ,id: 'simpleConfigRowEditing'
                            ,clicksToEdit: 2
                            ,saveBtnText:'保存'
                            ,cancelBtnText: '取消'
                            ,dirtyText: "你要确认或取消更改"
                        }
                    ]
                    ,listeners: {
                        edit:function (editor, e) {
                            e.record.save();
                        }
                        ,canceledit: function(editor, e) {
                            if(e.record.phantom){
                                e.record.drop()
                            }
                        }
                    }
				}
			]
		    ,listeners: {
			    beforeshow: {
				    fn: me.onBeforeShow,
				    scope: me
			    },
			    beforehide: {
				    fn: me.onPanelBeforeHide,
				    scope: me
			    }
		    }
	    });
        me.callParent(arguments);
    }
	,onBeforeShow:function(abstractcomponent, options) {
		var panel = this.lookup('simpleConfigTypeGrid');
		panel.onBeforeShow();
	}
	,onPanelBeforeHide: function(abstractcomponent, options) {
		var panel = this.lookup('simpleConfigTypeGrid');
		panel.onPanelBeforeHide();
	}
	,onMainItemDblClick: function(tablepanel, record, item, index, e, options) {

		var me = this;

	}
	,initStore: function(){
		// var me = this;
		// var simpleConfigTypeStore = Ext.create('AM.store.common.SimpleConfigTypeStore');
		// simpleConfigTypeStore.proxy.extraParams={searchCondition:{}};
		// me.simpleConfigTypeStore = simpleConfigTypeStore;
		// simpleConfigTypeStore.load();
	}

});