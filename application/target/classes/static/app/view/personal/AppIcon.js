Ext.define('AM.view.personal.AppIcon', {
    extend: 'Ext.container.Container'
    ,xtype: 'appIcon'
    ,requires: [

    ]
    ,width:200
    // ,height:220
    ,layout:'vbox'
    , margin:'0 0 5 0'
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
        me.imageUrl = me.imageUrl?me.imageUrl:'resources/images/color/02.png';
        console.log('me.name:'+me.name)
        me.fontColor = me.fontColor?me.fontColor:'white';
	    Ext.apply(me, {

            items:[
                {
                    xtype: 'container'
                    ,style : 'background-image:url('+me.imageUrl+');background-repeat: no-repeat;background-size:100% 100%;background-attachment: fix;'
                    ,width: me.width
                    ,height: me.width
                    ,margin:0
                    ,padding:0

                },{
                    xtype: 'container'
                    ,width: me.width
                    ,layout:'center'
                    // ,height:50
                    , margin:'5 0 0 0'
                    ,items:[
                        {
                            xtype: 'container'
                            // ,style: 'color: white;font-size: 18px;font-weight:bold;text-align: middle'
                            ,style:{
                                'color':me.fontColor
                                ,'font-size':'18px'
                                ,'font-weight':'bold'
                            }
                            // ,html:'asdfasdf'
							,html:me.name
                        }
                    ]
                }
            ]
		    ,listeners:{
                click:{
                    element: 'el'
                    ,fn: function(event,dom, options){
                        console.log('click el');
                        var app = options.scope.app
                        console.log(app)

                        if (!app){
                            console.log('ap not set');
                            return;
                        }
                        if (!options.scope.url) {
                            //没有设置URL, 则哪里都不跳转
                            return;
                        }

                        var targetUrl = options.scope.url;
                        console.log('jump to :'+options.scope.url);
                        //window.open(options.scope.url, "_blank");

                        Ext.Ajax.request({
                            //获取当前应用
                            url: 'security/jump/'+options.scope.appCode
                            ,method: 'POST'
                            ,scope: this
                            ,success: function(response, opts) {
                                // this.getView().unmask();
                                console.log(response.responseText);
                                var jumpToken = response.responseText
                                var targetUrlWithToken = targetUrl+"?token="+jumpToken
                                console.log('jump to target:'+targetUrlWithToken);
                                // window.open(targetUrlWithToken, "_blank");
                                window.location.href = targetUrlWithToken;
                                console.log('jump end');
                            }
                        });
                    }
                    ,scope:me
                }
                ,contextmenu:{
                    element: 'el'
                    ,scope:me
                    ,fn: function(event,dom, options){
                        event.stopEvent();
                        console.log(Ext.getClassName(event));
                        console.log('event');console.log(event);
                        console.log('dom');console.log(dom);
                        console.log('options');console.log(options);
                        console.log('contextmenu');
                    }
                }
            }
	    });
        me.callParent(arguments);
    }


});