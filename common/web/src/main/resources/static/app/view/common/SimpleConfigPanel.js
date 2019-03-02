Ext.define('AM.view.common.SimpleConfigPanel', {
    extend: 'Ext.grid.Panel'
    ,xtype: 'common.SimpleConfigPanel'
    ,title: '配置项'
    ,requires: [
        'AM.view.common.SimpleConfigController'
        ,'AM.view.common.SimpleConfigAddWindow'
        ,'AM.view.common.SimpleConfigEditWindow'
        ,'AM.view.common.SimpleConfigSearchWindow'
        ,'AM.view.common.SimpleConfigDetailWindow'
    ]
    ,controller: 'common_SimpleConfigController'
    ,initComponent: function() {
        var me = this;

        Ext.apply(me, {
            columnLines: true
            ,columns: [
                {
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
                    
                }

                ,{
                    xtype: 'gridcolumn'
                    ,dataIndex: 'code'
                    ,text: '参数代码'
                    
                }

                ,{
                    xtype: 'gridcolumn'
                    ,dataIndex: 'value'
                    ,text: '参数值'
                    
                }

                ,{
                    xtype: 'gridcolumn'
                    ,dataIndex: 'description'
                    ,text: '参数说明'
                    
                }

                ,{
                    xtype: 'numbercolumn'
                    ,dataIndex: 'vIndex'
                    ,format:'0,000'
                    ,text: '展现顺序'
                    ,flex:1
                }
                ,{
                    xtype: 'actioncolumn'
                    ,menuDisabled: true
                    ,width:30
                    ,items: [{
                        iconCls: 'x-fa fa-eye'
                        ,tooltip: '详情'
                        ,handler: function(grid, rowIndex, colIndex) {
                            var record = grid.getStore().getAt(rowIndex);
                            me.getSelectionModel().deselectAll()
                            me.getSelectionModel().select(record)
                            me.showDetailWindow(record, this);
                        }
                    }]
                }
                ,{
                    xtype: 'actioncolumn'
                    ,menuDisabled: true
                    ,width:30
                    ,items: [{
                        iconCls: 'edit'
                        ,tooltip: '修改'
                        ,handler: function(grid, rowIndex, colIndex) {
                            var record = grid.getStore().getAt(rowIndex);
                            me.getSelectionModel().deselectAll()
                            me.getSelectionModel().select(record)
                            me.showEditWindow(record, this);
                        }
                    }]
                }
                ,{
                    xtype: 'actioncolumn'
                    ,menuDisabled: true
                    ,width:30
                    ,items: [{
                        iconCls: 'delete'
                        ,tooltip: '删除'
                        ,handler: function(grid, rowIndex, colIndex) {
                            var record = grid.getStore().getAt(rowIndex);
                            me.getSelectionModel().deselectAll()
                            me.getSelectionModel().select(record)
                            me.onDeleteButtonClick();
                        }
                    }]
                }
            ]
            ,viewConfig: {

            }
            ,dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'top',
                    items: [
                        {
                            xtype: 'button'
                            ,iconCls: 'add'
                            ,text: '新增'
                            ,listeners: {
                                click: {
                                    fn: me.onAddButtonClick,
                                    scope: me
                                }
                            }
                        }
                        ,{
                            xtype: 'button'
                            ,iconCls: 'edit'
                            ,text: '修改'
                            ,listeners: {
                                click: {
                                    fn: me.onEditButtonClick
                                    ,scope: me
                                }
                            }
                        }
                        ,{
                            xtype: 'button'
                            ,iconCls: 'delete'
                            ,text: '删除'
                            ,listeners: {
                                click: {
                                    fn: me.onDeleteButtonClick
                                    ,scope: me
                                }
                            }
                        }
                        ,'-'
                        ,{
                            xtype: 'button'
                            ,iconCls: 'search'
                            ,text: '查询'
                            ,listeners: {
                                click: {
                                    fn: me.onSimpleSearchButtonClick
                                    ,scope: me
                                }
                            }
                        }
                        ,{
                            xtype: 'button'
                            ,iconCls: 'search'
                            ,text: '高级查询'
                            ,listeners: {
                                click: {
                                    fn: me.showSearchWindow
                                    ,scope: me
                                }
                            }
                        }
                    ]
                }

            ]
            ,bbar:{
                xtype: 'pagingtoolbar'
                ,displayInfo: true
            }
            ,selModel: 'checkboxmodel'
            ,listeners: {
                beforeshow: {
                    fn: me.onBeforeShow
                    ,scope: me
                }
                ,beforehide: {
                    fn: me.onPanelBeforeHide
                    ,scope: me
                }
                ,itemdblclick: {
                    fn: me.onItemDblClick
                    ,scope: me
                }
            }
            ,items:[]
        });
        me.add({xtype:'common.SimpleConfigAddWindow',reference:'simpleConfigAddWindow',listeners:{saved:'reloadStore'}})
        me.add({xtype:'common.SimpleConfigEditWindow',reference:'simpleConfigEditWindow',listeners:{saved:'reloadStore'}})
        me.add({xtype:'common.SimpleConfigSearchWindow',reference:'simpleConfigSearchWindow',listeners:{saved:'doSearch'}})
        me.add({xtype:'common.SimpleConfigDetailWindow',reference:'simpleConfigDetailWindow'})

        me.callParent(arguments);
    }
    ,onSimpleSearchButtonClick: function(button, e, options) {
        var panel = options.scope;

        var toolbar = this.down('toolbar')

        var simpleSearchField = panel.down("#simpleSearchField");

        var searchCondition = {tid:simpleSearchField.getValue()}

        this.store.proxy.extraParams = {searchCondition:searchCondition};
        this.store.load({
            params:{
                page:0
            }
        });
    }
	,onAddButtonClick: function(button, e, options) {

        var modelConfig = {}

        var record = Ext.create('AM.model.common.SimpleConfig', modelConfig);

        options.src = button;
        var addWindow = this.showAddWindow(record, button);
        addWindow.setTitle('添加新通用配置');
    }
    ,onEditButtonClick: function(button, e, options) {

        var selections = this.getSelectionModel( ).getSelection( );

        if(selections.length <= 0){
            Ext.Msg.show({title: '操作失败', msg: '未选择数据', buttons: Ext.Msg.OK, icon: Ext.Msg.WARNING});
            return;
        }

        var record = selections[0];
        var detailWindow = this.showEditWindow(record, button);
        detailWindow.setTitle('修改通用配置信息');
    }
    ,onDeleteButtonClick: function(button, e, options) {
        var me = this;
        var selections = me.getSelectionModel( ).getSelection( );
        me.getStore().remove(selections);
        me.getStore().sync({
            success:function(batch,options){

                var store = options.scope;
		        var count = store.getCount();

		        var targetPage = count<=0 ? store.currentPage-1 : store.currentPage;
		        targetPage = targetPage <=0 ? 1 :targetPage;

                store.loadPage(targetPage,{
                    scope: this,
                    callback: function(records, operation, success) {
                        if(!success)
                        	Ext.Msg.show({title: '操作失败', msg: '重新加载数据失败', buttons: Ext.Msg.OK, icon: Ext.Msg.WARNING});
                        else
                        	Ext.MsgUtil.show('操作成功','删除通用配置成功!');
                    }
                });
            }
            ,scope:me.getStore()
        });
    }
    ,onItemDblClick: function(tablepanel, record, item, index, e, options) {
        var me = this;
        options.src=item;
        var editWindow = me.showEditWindow(record, item);
        editWindow.setTitle('修改通用配置信息');
    }
    ,showAddWindow: function(model, targetComponent) {
        var me = this;
        var addWindow = me.lookupReference('simpleConfigAddWindow');
        addWindow.setModel(model);
        addWindow.show(targetComponent);
        return addWindow;
    }
    ,showEditWindow: function(model, targetComponent) {
        var me = this;
        var editWindow = me.lookupReference('simpleConfigEditWindow');
        editWindow.setModel(model);
        editWindow.show(targetComponent);
        return editWindow;
    }
    ,showDetailWindow: function(model, targetComponent) {
        var me = this;
        var detailWindow = me.lookupReference('simpleConfigDetailWindow');
        detailWindow.setModel(model);
        detailWindow.show(targetComponent);
        return detailWindow;
    }
    ,showSearchWindow: function(button, e, options) {
        var me = options.scope;

        var searchWindow = me.lookupReference('simpleConfigSearchWindow');
        searchWindow.show(button);
    }
    ,setStore: function(store) {
        this.reconfigure(store);
        this.down('pagingtoolbar').bindStore(store);

        this.store = store;
    }
    ,onBeforeShow:function(abstractcomponent, options) {
	    this.store.reload({scope: this,callback: function(){}});
    }
    ,onPanelBeforeHide: function(abstractcomponent, options) {
    	var me = this;

    	if(me.searchWindow){
    		me.searchWindow.hide();
    	}
    	if(me.detailWindow){
    		me.detailWindow.hide();
    	}
    	if(me.editWindow){
    		me.editWindow.hide();
    	}
    	if(me.addWindow){
    		me.addWindow.hide();
    	}
    }

});