Ext.define('AM.view.application.framework.MainContentPanel', {
    extend: 'Ext.tab.Panel',
    alias: 'widget.mainContentPanel',
	requires:[
        'AM.view.application.dashboard.Dashboard'

	    //,'AM.view.maintenance.asset.info.AssetCmdbPanel'

	]
	,defaults: {
		//bodyPadding: 10,
		scrollable: true,
		closable: true,
		//border: false
	}

    ,referenceHolder:true
    ,initComponent: function() {
        var me = this;

        Ext.apply(me, {
            listeners: {
                beforerender: {
                    fn: me.onTabpanelBeforeRender,
                    scope: me
                }
                ,createMainTabPanel: {fn:me.createMainTabPanel, scope:me }
            }
        });

        me.callParent(arguments);
    },

    onTabpanelBeforeRender: function(abstractcomponent, options) {
        var me = this;
        function getTools(){
            return [{
                xtype: 'tool',
                type: 'gear',
                handler: function(e, target, panelHeader, tool){
                    var portlet = panelHeader.ownerCt;
                    portlet.setLoading('Loading...');
                    Ext.defer(function() {
                        portlet.setLoading(false);
                    }, 2000);
                }
            }];
        }


        if(!Ext.dashboard ||Ext.dashboard.length <=0 ){
            var dashboard = Ext.create("AM.view.application.dashboard.Dashboard",{
                closable:false
            })
            this.insert(0,dashboard);
            this.setActiveTab(0);
        }
        var homepage = Ext.create('AM.view.application.dashboard.HomePage');
        this.insert(0,homepage);
        this.setActiveTab(0);

        Ext.require(Ext.dashboard, function () {
            // var panel = Ext.create(aa,{
            //     closable:true
            // })
            //
            // //this.insert(1,panel);
            // me.add(panel)

            for(var i in Ext.dashboard){
                var panel = Ext.create(Ext.dashboard[i],{
                    closable:false
                })

                me.add(panel)
                if(Ext.dashboard.length > 0){
                    me.setActiveTab(0);
                }
            }
        })





    }
    ,createMainTabPanel: function(srcView, tabConfig){
        if(!tabConfig.reference){
            Ext.MessageBox.alert('创建tab页失败', '页面配置必须包含reference属性');
            return;
        }

        var panel = this.lookup(tabConfig.reference);

        if(!panel){
            panel = this.add(tabConfig)
        }

        this.setActiveTab(panel);
    }
});