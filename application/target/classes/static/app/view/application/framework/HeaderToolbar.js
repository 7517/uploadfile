Ext.define('AM.view.application.framework.HeaderToolbar', {
    extend: 'Ext.toolbar.Toolbar'
    ,alias: 'widget.mainHeaderToolbar'
    ,requires:[
        'AM.view.application.framework.UserSwitcher'
        ,'AM.view.application.framework.HeaderContainerController'
        ,'AM.view.application.framework.ChangePasswordWindow'
    ]
    ,title: ''
    ,height: 64
    ,controller:'HeaderContainerController'
    ,initComponent: function() {

        var me = this;

        me.items= [
            {
                xtype:'button'
                // text:'Menu Button',
                ,iconCls: 'fas fa-bars'
                ,ui:'plain-toolbar-small'
                ,style: 'color: #28384a;'
                ,handler:'hideFunctionPanel'
            }
            ,'->'
            ,{
                xtype:'button'
                // text:'Menu Button',
                ,iconCls: 'fas fa-bell'
                ,ui:'plain-toolbar-small'
                ,style: 'color: #28384a;'
                ,tooltip: '提醒'
                ,menu:[{
                    text:'Menu Button 1'
                }]
            }
            ,{ xtype: 'tbseparator' }
            ,{
                xtype:'button'
                // text:'Menu Button',
                ,iconCls: 'fas fa-envelope'
                ,ui:'plain-toolbar-small'
                ,style: 'color: #28384a;'
                ,tooltip: '消息'
                ,menu:[{
                    text:'Menu Button 1'
                }]
            }
            ,{ xtype: 'tbseparator' }
            ,{
                xtype:'button'
                ,text:'平台切换'
                ,iconCls: 'fas fa-boxes'
                ,ui:'plain-toolbar-small'
                ,style: 'color: #28384a;'
                ,tooltip: '点击弹出可用系统列表'
                ,handler:function () {
                    console.log('平台切换')
                    var appPortal = Ext.create('AM.view.personal.AppPortal')
                    appPortal.show()
                }
            }
            ,{ xtype: 'tbseparator' }
            ,{
                xtype:'button'
                ,ui:'plain-toolbar-small'
                ,style: 'color: #28384a;'
                ,tooltip: '当前用户'
                // ,text:'超级管理员'
                ,style: 'color: 28384a;font-size: 18px; font-weight:bold; margin: 0 10px;'
                ,menu:[
                    {text:'修改密码',handler:'showChangePasswordWindow' }
                    ,'-'
                    ,{text:'登出' ,handler:'userLogout'}
                ]
                ,listeners:{
                    // afterrender: 'loadUserInfo'
                    afterrender: 'refreshUserInfo'
                }
            }
            // ,{
            //     xtype:'application.framework.ChangePasswordWindow'
            //     ,reference:'changePasswordWindow'
            // }
        ];

        this.callParent();
    }

});