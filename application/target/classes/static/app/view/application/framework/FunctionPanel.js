/**用户登录认证的时候,使用这个功能面板*/
Ext.define('AM.view.application.framework.FunctionPanel', {
	extend: 'Ext.panel.Panel',
	requires: [
		'Ext.layout.container.Accordion'
		,'AM.view.application.framework.FunctionTreePanel'
		,'AM.model.application.security.ResourceTreeNode'

	],
	alias: 'widget.mainFunctionPanel',
	controller: 'main-Function',
	width: 250,
	// collapsible: true,
	title: '业务中心',

	animate:true,
	layout: 'accordion',
	initComponent: function() {

		var me = this;

		Ext.apply(me, {

			viewConfig: {

			}
            ,defaults:{
                style:{
                    backgroundColor: '#28384a'//'#28384a'
                }
            }
			,items:[]
			,header:{
				height:64
				,style:{
					backgroundColor: '#2eb1f5'//'#28384a'
				}
			}
			,title:{
                style: 'color: white;font-size: 18px;'
                ,iconCls: 'fas fa-seedling'
			}
		});


		me.callParent(arguments);
	}
	,listeners:{
		afterrender:'loadFunctionTree'
	}

});

Ext.define('AM.view.application.framework.FunctionController', {
	extend: 'Ext.app.ViewController',
	requires: [

	]
	,alias: 'controller.main-Function',

	loadFunctionTree: function () {
		this.getView().mask();
		// var myMask = new Ext.LoadMask(this.getView(), {msg:"Please wait..."});
		// myMask.show();
		Ext.Ajax.request({
			url: 'security/login/getResource',
			method: 'POST',
			scope:this,
			success:this.onSuccess,
			failure: this.onFailure
			// ,mask:myMask
		});

	}
	,onFailure: function(response, opts) {

		console.log('server-side failure with status code ' + response.status);
		// opts.mask.hide()
        this.getView().unmask();
		if(401 == response.status){
            //window.location="login.html";
		}else {
            Ext.MessageBox.alert('获取资源失败', '获取用户功能清单失败, 请联系研发同事。status code:' + response.status);
        }
    }
	,onSuccess: function (response, opts) {

		// opts.mask.hide()
        this.getView().unmask();
		var resultSet = Ext.data.schema.Schema.lookupEntity('AM.model.application.security.ResourceTreeNode').getProxy().getReader().read(response);

		var resourceList = resultSet.getRecords();
		for(var i in resourceList){

			var store = Ext.create('Ext.data.TreeStore', {
				root : resourceList[i]
			})
			this.getView().add(Ext.create("AM.view.application.framework.FunctionTreePanel" ,{title:resourceList[i].get("name"), store:store }))
		}

	}
	,onReturn: function (options, success, response) {

		if (success) {
			// options.mask.hide()
            this.getView().unmask();
			var resultSet = Ext.data.schema.Schema.lookupEntity('AM.model.application.security.ResourceTreeNode').getProxy().getReader().read(response);

			var resourceList = resultSet.getRecords();
			for(var i in resourceList){

				var store = Ext.create('Ext.data.TreeStore', {
					root : resourceList[i]
				})
				this.getView().add(Ext.create("AM.view.application.framework.FunctionTreePanel", {title:resourceList[i].get("name"), store:store }))
			}

		}

	}


});

