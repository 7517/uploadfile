Ext.define('AM.controller.common.SimpleConfigController', {
    extend: 'Ext.app.Controller'
    ,init: function(application) {
        this.initPanel(application);
        //this.initMainPanel(application);
    }
    ,initPanel: function(application) {
        var center = application.getController('application.framework.MainController').getMainContentPanel();

        var simpleConfigPanel = center.child('application\\.common\\.SimpleConfigPanel');
        if(!simpleConfigPanel){
            simpleConfigPanel = Ext.create('AM.view.common.SimpleConfigPanel', {closable:true});

            var simpleConfigStore = Ext.create('AM.store.common.SimpleConfigStore');
            simpleConfigStore.proxy.extraParams={searchCondition:{}};
            simpleConfigPanel.setStore(simpleConfigStore);
            simpleConfigStore.load();

            center.add(simpleConfigPanel);
            center.setActiveTab(simpleConfigPanel);
        }
        center.setActiveTab(simpleConfigPanel);
    }
    ,initMainPanel: function(application) {
         var center = application.getController('application.framework.MainController').getMainContentPanel();

         var simpleConfigPanel = center.child('application\\.common\\.SimpleConfigMainPanel');
         if(!simpleConfigPanel){
             simpleConfigPanel = Ext.create('AM.view.common.SimpleConfigMainPanel',{closable:true});

             center.add(simpleConfigPanel);
             center.setActiveTab(simpleConfigPanel);
         }
         center.setActiveTab(simpleConfigPanel);
     }

})