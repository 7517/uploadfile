Ext.define('AM.controller.application.security.ResourceController', {
	extend: 'Ext.app.Controller'

	,stores: [
		'application.security.AllResourceTreeStore'
	]
	,views: [
		'application.security.ResourcePanel'
	]

	,refs: [
		{
			ref: 'applicationSecurityResourcePanel',
			selector: 'applicationSecurityResourcePanel'
		}
	]

	,init: function(application) {

		var center = application.getController('application.framework.MainController').getMainContentPanel();

		var resourcePanel = center.child('applicationSecurityResourcePanel');

		if(!resourcePanel){
			resourcePanel = Ext.widget('applicationSecurityResourcePanel',{closable:true});
			center.add(resourcePanel);

			var resourceStore = Ext.create('AM.store.application.security.ResourceStore', {pageSize :1000});
			//resourceStore.proxy.extraParams={condition:condition};
			resourcePanel.setResourceStore(resourceStore);


			center.setActiveTab(resourcePanel);
		}

		center.setActiveTab(resourcePanel);

		Ext.LoadMaskUtil.hide();
	}

});
