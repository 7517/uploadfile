Ext.define('AM.controller.application.security.AccountController', {
    extend: 'Ext.app.Controller',

    views: [
        'application.security.AccountPanel'
    ],
	stores: [
         
    ],
    refs: [
        {
            ref: 'applicationSecurityAccountPanel',
            selector: 'applicationSecurityAccountPanel'
        }
    ],

    init: function(application) {
        var center = application.getController('application.framework.MainController').getMainContentPanel();

        var userPanel = center.child('applicationSecurityAccountPanel');
        if(!userPanel){
            userPanel = Ext.widget('applicationSecurityAccountPanel',{closable:true});

            var store = Ext.create('AM.store.application.security.AccountStore');
            store.proxy.extraParams={searchCondition:{}};
            userPanel.setStore(store);
            store.load();
            
            center.add(userPanel);
            center.setActiveTab(userPanel);
        }
        center.setActiveTab(userPanel);
    }

});