Ext.define('AM.view.personal.AppPortal', {
    extend: 'Ext.window.Window'
    ,xtype: 'appPortal'
    ,requires: [
        'AM.view.personal.AppIcon'
    ]
    ,modal : true
    // ,layout: 'fit'
    ,width:580
    ,height:320
    ,bodyPadding: 10
    ,scrollable: true
    ,constructor:function(cfg){
        var me = this;
        cfg = cfg || {}
		me.app = cfg.app;
        me.callParent([Ext.apply({
            // viewModel : {
            //     data: cfg.app
            // }
        }, cfg)])
    }
    ,initComponent: function() {
        var me = this;
        me.imageUrl = me.imageUrl?me.imageUrl:'images/color/02.png';

	    Ext.apply(me, {

            layout: {
                type: 'table'
                ,columns: 5
                ,tableAttrs: {
                    style: {
                        width: '100%'
                    }
                }
            }
            ,items:[

            ]
            , listeners: {
                afterrender: me.loadUsableAppList
            }
	    });
        me.callParent(arguments);
    }
    ,loadUsableAppList: function(){
        var me = this;

        Ext.Ajax.request({
            //获取当前应用
            url: 'application/personal/platform/config'
            ,method: 'GET'
            ,scope: me
            ,success: function(response, opts) {
                console.log(response.responseText);
                var personalPlatformConfig = Ext.decode(response.responseText)
                console.log(personalPlatformConfig)

                var appBoard = me.lookupReference('appBoard');
                appBoard = me;
                console.log('me');
                console.log(appBoard);
                var usableAppList = personalPlatformConfig.usableAppList;
                console.log(usableAppList);
                for(var i in usableAppList){
                    console.log(i)
                    console.log(usableAppList[i])
                    var app = usableAppList[i];

                    console.log(app)
                    appBoard.add({
                        xtype:'appIcon'
                        ,width:80
                        // ,height:110
                        ,fontColor:'black'
                        ,app:app
                        ,name:app.appVO.name
                        ,imageUrl:app.appVO.imageUrl
                        ,url:app.appVO.url
                        ,appCode:app.appVO.code

                    })
                }

            }
        });
    }

});