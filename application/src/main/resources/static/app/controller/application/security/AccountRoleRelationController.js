Ext.define('AM.controller.application.security.AccountRoleRelationController', {
    extend: 'Ext.app.Controller',

    views: [
        'application.security.AccountRoleRelationPanel'
    ],
	stores: [

    ],
    refs: [
        {
            ref: 'application.securityAccountRoleRelationPanel',
            selector: 'applicationSecurityAccountRoleRelationPanel'
        }
    ],

    init: function(application) {
        var center = application.getController('application.framework.MainController').getMainContentPanel();

        var accountRoleRelation = center.child('applicationSecurityAccountRoleRelationPanel');
        if(!accountRoleRelation){
            accountRoleRelation = Ext.widget('applicationSecurityAccountRoleRelationPanel',{closable:true});

            var store = Ext.create('AM.store.application.security.AccountRoleRelationStore');
            store.proxy.extraParams={searchCondition:{}};
            accountRoleRelation.setStore(store);
            store.load();

            center.add(accountRoleRelation);
            center.setActiveTab(accountRoleRelation);
        }
        center.setActiveTab(accountRoleRelation);
    }

});