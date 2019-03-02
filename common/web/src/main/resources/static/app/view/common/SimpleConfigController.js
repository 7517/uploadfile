Ext.define('AM.view.common.SimpleConfigController', {
	extend: 'Ext.app.ViewController',
	requires: [

	]
	,alias: 'controller.common_SimpleConfigController'

	,onMainPanelRowClick:function(tablepanel, record, item, index, e, options) {
		//点击主数据的某行
		var me = this;


		var detailTabPanel = me.lookup('detailTabPanel');
		if(detailTabPanel) {
            detailTabPanel.expand();
        }

		var id = record.get('id');



	}
	,reloadStore:function () {
        var me = this;
        var simpleConfigAddWindow = me.lookupReference ('simpleConfigAddWindow');
        var simpleConfigGrid = simpleConfigAddWindow.up('gridpanel');

        simpleConfigGrid.getStore().load({
            callback: function (records, operation, success) {
                if (success) {
                    Ext.MsgUtil.show('操作成功', '同步列表成功');
                }
            }
        });
    }
    ,doSearch:function () {
        var me = this;
        var simpleConfigSearchWindow = me.lookupReference ('simpleConfigSearchWindow');
        var simpleConfigGrid = simpleConfigSearchWindow.up('gridpanel');
        simpleConfigGrid.getStore().proxy.extraParams={searchCondition:simpleConfigSearchWindow.getCondition()};
        simpleConfigGrid.getStore().load({
            params:{
                start:0,
                page:0
            }
        });
    }
})