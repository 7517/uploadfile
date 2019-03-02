Ext.application({
	name: 'AM'
	,controllers: [
		//'security.LoginController'
	]
	,launch: function() {
		Ext.create('AM.view.LoginView');
	}
});
