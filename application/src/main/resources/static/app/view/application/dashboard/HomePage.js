Ext.define('AM.view.application.dashboard.HomePage', {
	extend: 'Ext.panel.Panel'
	,title:'欢迎使用'
	,bodyCls: 'app-dashboard'
	,reference: 'homepage'
	// ,controller: 'application.dashboard.DashboardController'
	// ,requires:['AM.view.application.dashboard.DashboardController', 'AM.view.application.dashboard.UserDetailPanel']
	,bodyPadding: 10
	,layout: {
		type: 'vbox',
		align: 'stretch'
	}
	,items: [
		{
			xtype: 'panel'
			,layout: 'hbox'
			,title:'常用操作'
            ,ui: 'light'
			,defaults:{
            	margin: '10 10 10 10'
                ,scale: 'large'
                ,iconAlign: 'top'
				,width:92
			}
			,items: [
				{
					xtype: 'button'
					,text: '新建任务'
					,iconCls: 'fas fa-file-medical'
				}
                ,{
                    xtype: 'button'
                    ,iconCls: 'fas fa-chart-bar'
                    ,text: '报表'

                }
                ,{
                    xtype: 'button'
                    ,iconCls: 'fas fa-plus-circle'
                    ,text: ''
					,height:'100%'
                }
            ]
		}
        ,{
            xtype: 'container'
            ,layout: 'hbox'
            ,margin: '10 0 10 0'
			,height:150
			,defaults:{
                margin: '0 10 0 0'
                ,frame:true
			}
            ,items: [
            	{
                    xtype: 'panel'
                    ,width: 250
                    ,height:'100%'
					,layout:'hbox'
					,items:[
						{
							xtype:'container'
							,width:100
                            ,height:'100%'
							,padding:'20 0 0 20'
							,html:'<span style="color: Dodgerblue;"><i class="fas fa-users fa-5x"></i></span>'
						}
						,{
                            xtype:'container'
                            // ,width:100
                            ,height:'100%'
                            ,padding:'20 0 0 20'
                            ,html:'活跃客户数:100'
                        }
					]
                }
                ,{
                    xtype: 'panel'
                    ,width: 250
                    ,height:'100%'
                    ,layout:'hbox'
                    ,items:[
                        {
                            xtype:'container'
                            ,width:100
                            ,height:'100%'
                            ,padding:'20 0 0 20'
                            ,html:'<span style="color: Dodgerblue;"><i class="fas fa-dollar-sign fa-5x"></i></span>'
                        }
                        ,{
                            xtype:'container'
                            // ,width:100
                            ,height:'100%'
                            ,padding:'20 0 0 20'
                            ,html:'合同额:100'
                        }
                    ]
                }
            ]
        }
		,{
            xtype:'tabpanel'
			,flex:1
			,items:[
				{
					xtype:'grid'
					,title:'重点事项'
				}
                ,{
                    xtype:'panel'
                    ,title:'待审批'
                }
                ,{
                    xtype:'panel'
                    ,title:'下发任务'
                }
                ,{
                    xtype:'panel'
                    ,title:'我的关注'
                }
			]
		}
	]

	,initComponent: function() {
		var me = this;

		Ext.apply(me, {


		});

		me.callParent(arguments);
	}
})