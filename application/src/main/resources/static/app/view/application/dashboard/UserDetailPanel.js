Ext.define('AM.view.application.dashboard.UserDetailPanel', {
	extend: 'Ext.form.Panel'
	,alias: 'widget.form-register'
	,requires:['AM.view.application.dashboard.UserDetailPanelController','AM.model.application.security.Account']
	,xtype:'portlet-userDetail'
	,controller:'application.dashboard.UserDetailPanelController'

	,frame: true
	,title: '当前用户'
	,bodyPadding: 10
	,scrollable:true
	,fieldDefaults: {
		labelAlign: 'right'
		,labelWidth: 115
		,msgTarget: 'side'
	}

	,items: [{
		xtype: 'fieldset'
		,title: '基本信息'
		,defaultType: 'displayfield'
		,defaults: {anchor: '100%'}
		,items: [
			{ allowBlank:false, fieldLabel: '昵称', name: 'nickName', value:'' }
			,{ allowBlank:false, fieldLabel: '姓名', name: 'name', value:''}
			,{ allowBlank:false, fieldLabel: '账号', name: 'accountName', value:'' }
		]
	}
	,{
		xtype: 'fieldset'
		,title: '联系方式'
		,defaultType: 'displayfield'
		,defaults: {anchor: '100%'}
		,items: [
			{
				fieldLabel: '电话'
				,name: 'mobile'
			}
			,{
				fieldLabel: '邮箱'
				,name: 'email'
				,vtype: 'email'
			}
		]
	}
	]
	,listeners:{
		afterrender:'loadUserInfo'
	}
});