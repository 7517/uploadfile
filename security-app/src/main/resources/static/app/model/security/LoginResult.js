Ext.define('AM.model.security.LoginResult', {
	extend: 'Ext.data.Model',
	proxy: {
		type: "rest",
		headers:{"Accept":"application/json"},
		url: 'aa/aa'
	},
	fields: [
		{ name: 'success', type:'boolean' },
		{ name: 'sessionId', type:'string' }
	]
});
