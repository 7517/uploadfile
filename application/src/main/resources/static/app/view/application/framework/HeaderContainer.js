Ext.define('AM.view.application.framework.HeaderContainer', {
    extend: 'Ext.panel.Panel'
    ,alias: 'widget.mainHeaderContainer'
    ,requires:[
        'AM.view.application.framework.UserSwitcher'
        ,'AM.view.application.framework.HeaderContainerController'
    ]
    ,title: ''
    ,height: 64
    ,layout: {
        type: 'hbox'
        ,align: 'middle'
    }
    // , margin:'0 0 0 5'
     // ,style: 'background: #28384a;border-bottom: 1px solid #0d1218;'
    // ,bodyStyle: 'background: #1669C0;border-bottom: 1px solid #0d1218;'
    // ,style:{
    //     backgroundColor: '#1669C0'
    // }
    ,controller:'HeaderContainerController'
    ,initComponent: function() {

        var me = this;

        me.items = [{
                xtype: 'component'
                ,id: 'app-header-logo'
                ,style: 'color: white;font-size: 20px;margin: 0 10px;'
                ,cls: [ 'fas', 'fa-search', 'ext-sencha' ]
            }
            ,{
                xtype: 'component'
                ,id: 'app-header-title'
                ,style: 'color: white;font-size: 18px;font-weight: bold;padding: 10px 0 10px 0;'
                ,html: this.title
            }
            ,{
                xtype: 'component'
                ,flex: 1
            }
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
                    xtype: 'userSwitcher'
                    ,style: 'color: #28384a;font-size: 18px; font-weight:bold; margin: 0 10px;'
                    ,html:'你好'
                    ,listeners:{
                        afterrender: 'loadUserInfo'
                    }
                }
            ];

        this.callParent();
    }


});