Ext.define('AM.controller.application.security.RoleController', {
    extend: 'Ext.app.Controller',

    views: [
        'application.security.RolePanel'
    ],
	stores: [
         
    ],
    refs: [
        {
            ref: 'applicationSecurityRolePanel',
            selector: 'applicationSecurityRolePanel'
        }
    ],

    init: function(application) {
        var center = application.getController('application.framework.MainController').getMainContentPanel();

        var rolePanel = center.child('applicationSecurityRolePanel');
        if(!rolePanel){
            rolePanel = Ext.widget('applicationSecurityRolePanel', {closable:true});
            
            

            var store = Ext.create('AM.store.application.security.RoleStore');
            store.proxy.extraParams={searchCondition:{}};
            rolePanel.setStore(store);
            store.load();
            
            center.add(rolePanel);
            center.setActiveTab(rolePanel);
        }
        center.setActiveTab(rolePanel);
    }

});