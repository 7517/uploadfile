Ext.define('AM.view.common.AttachmentController', {
	extend: 'Ext.app.ViewController',
	requires: [

	]
	,alias: 'controller.common_AttachmentController'

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
        var attachmentAddWindow = me.lookupReference ('attachmentAddWindow');
        var attachmentGrid = attachmentAddWindow.up('gridpanel');

        attachmentGrid.getStore().load({
            callback: function (records, operation, success) {
                if (success) {
                    Ext.MsgUtil.show('操作成功', '同步列表成功');
                }
            }
        });
    }
    ,doSearch:function () {
        var me = this;
        var attachmentSearchWindow = me.lookupReference ('attachmentSearchWindow');
        var attachmentGrid = attachmentSearchWindow.up('gridpanel');
        attachmentGrid.getStore().proxy.extraParams={searchCondition:attachmentSearchWindow.getCondition()};
        attachmentGrid.getStore().load({
            params:{
                start:0,
                page:0
            }
        });
    }
})