Ext.define('AM.view.application.dashboard.UserInSiteMessageWindow', {
	extend: 'Ext.window.Window',
	alias: 'widget.dashboard-UserInSiteMessageWindow',
	autoScroll: true,
	height: 250,
	width: 400,
	layout: {
		type: 'fit'
	},
	title: '站内消息',
	maximizable: true,
	closeAction:'hide',
	initComponent: function () {
		var me = this;

		Ext.apply(me, {
			items: [
				{
					xtype: 'form',
					autoScroll: true,
					bodyPadding: 10,
					fieldDefaults: {
						labelAlign: 'right'
						,msgTarget: 'side'
						,padding: '5 0 0 5'
						,blankText:'该字段为必填项'
						,anchor: '96%'
					},
					items: [
						{
							xtype: 'displayfield',
							itemId: 'titleField',
							name: 'title',
							fieldLabel: '标题'
						}
						,{
							xtype: 'displayfield',
							itemId: 'senderField',
							name: 'senderName',
							fieldLabel: '发送人'
						}
						,{
							xtype: 'displayfield',
							itemId: 'createTimeField',
							name: 'createTime',
							fieldLabel: '创建时间'
						}
						,{

							xtype: 'displayfield',
							anchor: '98% 70%',
							itemId: 'contentField',
							padding: '5 0 0 5',
							name: 'content',
							fieldLabel: '内容',
							labelAlign: 'right'
						}
					]
				}
			],
			dockedItems: [
				{
					xtype: 'toolbar',
					dock: 'bottom',
					ui: 'footer',
					items: [
						{
							xtype: 'tbfill'
						},
						{
							xtype: 'button',
							iconCls: 'accept',
							text: '确定',
							listeners: {
								click: {
									fn: me.makeReaded,
									scope: me
								}
							}
						}
					]
				}
			]
			,listeners: {
				close: me.makeReaded
			}
		});

		me.callParent(arguments);
	}
	,setModel: function (model) {
		if(!model){
			Ext.Msg.show({title: '操作失败', msg: "未设置模型", buttons: Ext.Msg.OK, icon: Ext.Msg.ERROR});
			return;
		}

		this.setTitle("修改站内消息信息");
		if(model.phantom){
			this.setTitle("新增站内消息信息");
		}
		this.down('form').getForm().loadRecord(model);
		this.down('#createTimeField').setValue(Ext.Date.format(model.get('createTime'), 'Y-m-d'))
	}
	,makeReaded: function(){
		var me = this;
		var record = me.down('form').getRecord();

		var id = record.get('id');

		Ext.Ajax.request({
			//获取当前用户
			url: 'notification/inSiteMessage/'+id+"/read"
			,method: 'POST'
			,scope: this
			,success: function(){
				record.load();
			}
		});
	}

})