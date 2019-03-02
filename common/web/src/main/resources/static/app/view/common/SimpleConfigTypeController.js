Ext.define('AM.view.common.SimpleConfigTypeController', {
	extend: 'Ext.app.ViewController',
	requires: [

	]
	,alias: 'controller.common_SimpleConfigTypeController'

	,onMainPanelRowClick:function(tablepanel, record, item, index, e, options) {
		//点击主数据的某行
		var me = this;
        var searchCondition = {configType:record.get('typeCode')}
        var simpleConfigPanel = me.lookup('simpleConfigPanel');
        simpleConfigPanel.expand();


        simpleConfigPanel.getStore().applyCondition(searchCondition)
        simpleConfigPanel.getStore().load();

        me.getViewModel().set('simpleConfigType', record);

        //配置项页展现对应

	}
	,reloadStore:function () {
        var me = this;
        var simpleConfigTypeAddWindow = me.lookupReference ('simpleConfigTypeAddWindow');
        var simpleConfigTypeGrid = simpleConfigTypeAddWindow.up('gridpanel');

        simpleConfigTypeGrid.getStore().load({
            callback: function (records, operation, success) {
                if (success) {
                    Ext.MsgUtil.show('操作成功', '同步列表成功');
                }
            }
        });
    }
    ,doSearch:function () {
        var me = this;
        var simpleConfigTypeSearchWindow = me.lookupReference ('simpleConfigTypeSearchWindow');
        var simpleConfigTypeGrid = simpleConfigTypeSearchWindow.up('gridpanel');
        simpleConfigTypeGrid.getStore().proxy.extraParams={searchCondition:simpleConfigTypeSearchWindow.getCondition()};
        simpleConfigTypeGrid.getStore().load({
            params:{
                start:0,
                page:0
            }
        });
    }

    ,onAddSimpleConfigButtonClick: function() {

        var me = this;

        var simpleConfigType = me.getViewModel().get('simpleConfigType');
        if(!simpleConfigType){
            Ext.MessageBox.alert('未选择分类', '请先在左侧选择分类');
            return;
        }

        var rowEditing =  this.lookup('simpleConfigPanel').getPlugin('simpleConfigRowEditing');
        rowEditing.cancelEdit();


        var modelConfig = {configType:simpleConfigType.get('typeCode')}
        var simpleConfig = Ext.create('AM.model.common.SimpleConfig', modelConfig);

        var simpleConfigStore = me.getViewModel().getStore('simpleConfigStore');
        simpleConfigStore.add(simpleConfig);
        rowEditing.startEdit(simpleConfig, 2);
    }
})