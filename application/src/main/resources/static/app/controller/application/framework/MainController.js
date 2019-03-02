Ext.define('AM.controller.application.framework.MainController', {

	extend: 'Ext.app.Controller',

	stores: [
		 // 'AM.store.security.AllResourceTreeStore'
	],

	models: [

	],

	views: [

	],

	refs: [
		{
			ref: 'mainContentPanel',
			selector: 'mainContentPanel'
		}
	],

	init: function(application) {
		this.control({
			'viewport > userlist': {
				itemdblclick: this.editUser
			}

			,'mainFunctionTreePanel': {
				itemclick: this.onTreepanelItemClick
			}
			// ,'mainContentPanel > #functionPanel > mainfunctionview': {
			// 	itemclick: this.onFunctionPanelItemClick
			// }
		});

		this.application = application;

		Ext.EventManager.on(document, 'keydown', function(e, t) {
			//e.stopEvent();

			if (e.getKey() == e.BACKSPACE && ((!/^input$/i.test(t.tagName) && !/^TEXTAREA$/i.test(t.tagName)) || t.disabled || t.readOnly)) {
				e.stopEvent();
			}
		});
	}
	,onTreepanelItemClick: function(tablepanel, record, item, index, e, options) {
		//Ext.LoadMaskUtil.show(this.getMainContentPanel());
		var me = this;
		var mainContentPanel = this.getMainContentPanel();
        mainContentPanel.mask("Please wait...");

        if("page" !== record.get('type') && "function" !== record.get('type')){
            mainContentPanel.unmask();
            return;
        }

        if(Ext.isEmpty(record.get('url'))){
            mainContentPanel.unmask();
            return;
        }

        var controllerName = record.get('url');



		function loadController(){

			console.log()
			try{

				var controllerName = record.get('url');

				//

				var controller = me.application.getController(controllerName);

				controller.init(me.application);

			}catch(e){
                console.log(e);
				Ext.MessageBox.show({
					title: '加载页面错误',
					msg: '不能加载页面,请重试或联系管理员!',
					buttons: Ext.MessageBox.OK,
					icon: Ext.MessageBox.ERROR
				});
				Ext.MsgUtil.show('ERROR',e);

				Ext.log({level:'ERROR', dump:e}, '不能加载页面,请重试或联系管理员!')

			}

			mainContentPanel.unmask();

		}
    	function addPanel(){
            var clazz = Ext.ClassManager.get(controllerName)
            var xtype = clazz.xtype.replace(new RegExp('\\.','g'), "\\.")
            var panel = mainContentPanel.child(xtype);
            if(!panel){
                panel = Ext.create(controllerName, {closable:true});
                mainContentPanel.add(panel);
			}
            mainContentPanel.setActiveTab(panel);
            mainContentPanel.unmask();


        }

        if(Ext.String.endsWith(controllerName, 'Controller')) {
            Ext.require(controllerName, loadController);
        }else{
            Ext.require(controllerName, addPanel);
		}

	}

	,onFunctionPanelItemClick: function(tablepanel,record) {
		var controllerName = record.get('url');

		var controller = this.application.getController(controllerName);

		controller.init(this.application);
	}



});
