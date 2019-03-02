Ext.define('AM.view.ViewportController', {
	extend: 'Ext.app.ViewController',
	requires: [
		// 'AM.model.main.Resource'
	]
	,alias: 'controller.ViewportController'
    ,loadAppInfo: function(){
        var me = this;
        this.getView().mask({msg:"Please wait..."});
        Ext.Ajax.request({
            //获取当前应用
            url: 'current/app'
            ,method: 'POST'
            ,scope: this
            ,success: function(response, opts) {
				this.getView().unmask();
				console.log(response.responseText);
				var app = Ext.decode(response.responseText)
                // me.getView().down('mainHeaderContainer').setTitle(app.name);
                me.getView().down('mainFunctionPanel').setTitle(app.name);
				document.title = app.name

			}
        });
    }
})