Ext.define('AM.view.application.dashboard.UserInSiteMessagePanel', {
	extend: 'Ext.grid.Panel'
	,alias: 'widget.dashboard-UserInSiteMessage'
	,xtype:'dashboard-UserInSiteMessage'
	,requires:['AM.view.application.dashboard.UserInSiteMessagePanelController']
	,controller:'dashboard-UserInSiteMessage'
	,frame: true
	,title: '我的未读消息'
	,columnLines: true
	,columns: [

		{
			xtype: 'gridcolumn',
			dataIndex: 'title',

			text: '标题'
		}

		,{
			xtype: 'gridcolumn',
			dataIndex: 'content',
			flex:1,
			text: '内容'
		}

		,{
			xtype: 'datecolumn',
			format: 'Y-m-d H:i:s',
			dataIndex: 'createTime',
			width:150,
			text: '发送时间'
		}
		,{
			xtype: 'gridcolumn',
			dataIndex: 'status',
			flex:1,
			text: '状态'
			,renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
				if(!value){
					return '未读';
				}
				if(value=='READ'){
					return '已读';
				}
				return value;
			}
		}

		,{
			xtype: 'gridcolumn',
			dataIndex: 'senderId',
			renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
				return record.get("senderName")?record.get("senderName"):'';
			},

			text: '发送人'
		}
	]
	,dockedItems: [
		{
			xtype: 'toolbar',
			dock: 'top',
			items: [
				{
					xtype: 'button',
					iconCls: 'accept',
					text: '标记已读',
					listeners: {
						click: {
							fn: 'makeReaded'
						}
					}
				}
			]
		},
		{
			xtype: 'pagingtoolbar',
			dock: 'bottom',
			displayInfo: true
		}
	]

	,selModel: 'checkboxmodel'
	,initComponent: function() {

		var me = this;

		var store = Ext.create('AM.store.notification.InSiteMessageStore', {autoLoad :true});
		store.proxy.api = {read:"security/login/inSiteMessage/"};


		Ext.apply(me, {
			store:store

		});
		me.callParent(arguments);
	}
	,listeners: {
		itemdblclick: {
			fn: 'onItemDblClick'
		}
	}


});