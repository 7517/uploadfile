Ext.define('AM.view.common.AttachmentMainPanel', {
    extend: 'Ext.panel.Panel'
    ,xtype: 'common.AttachmentMainPanel'
    ,requires: [
        'AM.view.common.AttachmentPanel'
		,'AM.view.common.AttachmentController'
    ]
	,layout: {
		type: 'border'
	}
    ,title: 'Attachment'
	,store:null
	,controller: 'common_AttachmentController'
    ,initComponent: function() {
        var me = this;

		var attachmentStore = Ext.create('AM.store.common.AttachmentStore');
		attachmentStore.proxy.extraParams={searchCondition:{}};
		attachmentStore.load();

	    Ext.apply(me, {
		    items: [
			    {
				    xtype: 'common.AttachmentPanel'
				    ,region: 'center'
				    ,itemId: 'attachmentGrid'
					,reference: 'attachmentGrid'
				    ,store: attachmentStore
					,listeners: {

						itemdblclick: 'onMainPanelRowClick'
					}
			    } 		    ]
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
		var panel = this.lookup('attachmentGrid');
		panel.onBeforeShow();
	}
	,onPanelBeforeHide: function(abstractcomponent, options) {
		var panel = this.lookup('attachmentGrid');
		panel.onPanelBeforeHide();
	}
	,onMainItemDblClick: function(tablepanel, record, item, index, e, options) {

		var me = this;

	}
	,initStore: function(){
		var me = this;
		var attachmentStore = Ext.create('AM.store.common.AttachmentStore');
		attachmentStore.proxy.extraParams={searchCondition:{}};
		me.attachmentStore = attachmentStore;
		attachmentStore.load();
	}

});