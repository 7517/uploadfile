Ext.define('AM.view.application.security.AccountRoleRelationWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.applicationSecurityAccountRoleRelationWindow',

    height: 400,
    width: 208,
    layout: {
        type: 'fit'
    },
    title: 'My Window',
    closeAction:'hide',
	accountRoleStore: Ext.create('AM.store.application.security.AccountRoleRelationStore'),
    initComponent: function() {
        var me = this;

        Ext.apply(me, {
            items: [
                {
                    xtype: 'gridpanel',
                    itemId: 'roleGrid',
	                store: Ext.create('AM.store.application.security.RoleStore', {pageSize:10000}),
                    columnLines: true,
                    columns: [
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'name',
                            flex: 1,
                            text: '角色名'
                        }
                    ],
                    viewConfig: {

                    },
                    selModel: Ext.create('Ext.selection.CheckboxModel', {

                    })
                }
            ],
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'top',
                    items: [
                        {
                            xtype: 'button',
                            iconCls: 'accept',
                            text: '确定',
                            listeners: {
                                click: {
                                    fn: me.onButtonClick,
                                    scope: me
                                }
                            }
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    }
	,onButtonClick: function(button, e, options) {
        var me = this;
        var user = this.user;
        var accountRoleStore = this.accountRoleStore;
        var roleStore = this.down('grid').getStore();

        var records = this.down('grid').getSelectionModel().getSelection();


        var removed = [];
        var containRoles = [];

        //遍历旧的关系
        accountRoleStore.each(function(rec){
            console.log('accountRole :'+rec);
            console.log(rec);
            var role = roleStore.getById(rec.get('role'));
            console.log('old role:'+role);
            //已存在的关联，但是未被选中
            if(role && !me.down('grid').getSelectionModel().isSelected(role)){
                console.log('add removed role:'+role);
                removed.push(rec);
            }else{
                console.log('add containRoles role:'+role);
	            containRoles[rec.get('role')+""]=1;
            }
        });

        accountRoleStore.remove(removed);
        console.log(records)

        var addCount = 0;
        Ext.Array.each(records, function(rec){
            var roleId = rec.get('id');
            console.log("select roleId:"+roleId);
            console.log("select roleId:"+containRoles[roleId+""]);
            if(containRoles[roleId+""]!=1)
            {
                addCount++;
                accountRoleStore.add(Ext.create('AM.model.application.security.AccountRoleRelation', {account:user.get('id'),role:rec.get('id')}));

            }else{
                console.log("select roleId:"+roleId);
            }
        });



        accountRoleStore.sync({
            success:function(){
                Ext.MsgUtil.show('为'+user.get('name')+'分配角色成功','新增角色'+addCount+"个,删除角色"+removed.length+'个');
            }
        });

    }
    ,setAccount: function(record){

        var window = this;

        window.setTitle('可用角色类表');
		window.down('grid').getSelectionModel( ).deselectAll();

		//call the show immediately,so it will render the ui
		window.show();
		window.down('grid').getStore().reload();

		var accountRoleStore = this.accountRoleStore;

		var condition = {account:record.get('id')};
		accountRoleStore.proxy.extraParams = {searchCondition:condition};
		accountRoleStore.pageSize = 1000;
		accountRoleStore.load({
			callback:function(){
				accountRoleStore.each(function(rec){

					var role = window.down('grid').getStore().getById(rec.get('role'));

					window.down('grid').getSelectionModel().select(role,true);
				});
				window.user = record;
				window.show();
			}
		});
    }

});