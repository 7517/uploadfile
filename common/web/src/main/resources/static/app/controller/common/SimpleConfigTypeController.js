Ext.define('AM.controller.common.SimpleConfigTypeController', {
    extend: 'Ext.app.Controller'
    ,init: function(application) {
        // this.initPanel(application);
        this.initMainPanel(application);
    }
    ,initPanel: function(application) {
        var center = application.getController('application.framework.MainController').getMainContentPanel();

        var simpleConfigTypePanel = center.child('common\\.SimpleConfigTypePanel');
        if(!simpleConfigTypePanel){
            simpleConfigTypePanel = Ext.create('AM.view.common.SimpleConfigTypePanel', {closable:true});

            center.add(simpleConfigTypePanel);
            center.setActiveTab(simpleConfigTypePanel);
        }
        center.setActiveTab(simpleConfigTypePanel);
    }
    ,initMainPanel: function(application) {
         var center = application.getController('application.framework.MainController').getMainContentPanel();

         var simpleConfigTypePanel = center.child('common\\.SimpleConfigTypeMainPanel');
         if(!simpleConfigTypePanel){
             simpleConfigTypePanel = Ext.create('AM.view.common.SimpleConfigTypeMainPanel',{closable:true});

             center.add(simpleConfigTypePanel);
             center.setActiveTab(simpleConfigTypePanel);
         }
         center.setActiveTab(simpleConfigTypePanel);
     }

})