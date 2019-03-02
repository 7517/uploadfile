Ext.define('AM.view.LoginView', {
    extend: 'Ext.container.Viewport',
    requires: ['AM.model.security.LoginResult'],

    controller:'login',
    layout: 'fit',

    //style : 'background-image:url(resources/images/login.png);background-repeat: no-repeat;filter:"progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=\'scale\')";-moz-background-size:100% 100%;background-size:100% 100%;',
    items:{
        xtype:'panel'
        ,bodyCls:'login-view'
        ,layout: 'center'
        , items :[
            {
                xtype : 'container'
                ,items:[
                    {
                        xtype : 'container'
                    }
                    ,{
                        xtype: 'form'
                        ,reference: 'form'
                        ,title: '登录'
                        ,titleAlign: 'center'
                        ,frame: false
                        ,width: 320
                        ,margin: '10 0 0 0'
                        //border: 1,
                        //style: {borderColor:'#000000', borderStyle:'solid', borderWidth:'1px'},
                        ,bodyStyle : {
                            //'opacity': 0.5,
                            //background: 'url(resources/images/login.png) no-repeat #00FFFF'
                            //background: '#ffc'
                        }
                        ,defaultType: 'textfield'
                        ,defaults: {
                            anchor: '100%'
                        }
                        ,fieldDefaults: {
                            width:300
                            ,padding:10
                            // labelAlign: 'left'
                            ,labelStyle: 'font-weight: bold;'
                            ,fieldStyle: 'font-weight: bold;'
                        }
                        ,items: [{
                            allowBlank: false,
                            //fieldLabel: '用户名',
                            // padding:10,
                            name: 'username',
                            emptyText: '请输入用户名'

                        }, {
                            allowBlank: false
                            // padding:10,
                            //fieldLabel: '密&nbsp;&nbsp;&nbsp;码',
                            ,name: 'password'
                            ,emptyText: '请输入用户密码'
                            ,inputType: 'password'
                            ,keyMap:{
                                13:{handler:'onLoginClick'}
                            }
                        }
                        ]
                        ,fbar: [
                            '->'
                            ,{
                                xtype: 'button'
                                , text: '登录'
                                ,listeners: {
                                    click: 'onLoginClick'
                                }
                            }

                            ,{ xtype: 'button', text: '忘记密码?' }
                        ]

                    }

                ]
            }

        ]

    }
    , listeners: {
        afterrender: 'loadAppInfo'
    }
});
Ext.define('AM.controller.security.LoginController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.login',

    loginText: 'Logging in...'

    ,onSpecialKey: function(field, e) {
        if (e.getKey() === e.ENTER) {
            this.doLogin();
        }
    }
    ,loadAppInfo: function(){
        var me = this;
        this.getView().mask({msg:"Please wait..."});
        Ext.Ajax.request({
            //获取当前应用
            url: 'current/app'
            ,method: 'POST'
            ,scope: this
            ,success: function(response, opts) {
                this.getView().unmask();
                console.log(response.responseText);
                var app = Ext.decode(response.responseText)
                // me.getView().down('mainHeaderContainer').setTitle(app.name);
                me.getView().down('form').setTitle(app.name);
                document.title = app.name

            }
        });
    }
    ,onLoginClick: function() {

        var form = this.lookupReference('form');

        if (form.isValid()) {
            Ext.getBody().mask(this.loginText);


            this.login({
                data: form.getValues(),
                scope: this,
                success: 'onLoginSuccess',
                failure: 'onLoginFailure'
            });
        }
    }
    ,login: function(options) {
        Ext.Ajax.request({
            url: global.url+'/security/login/authenticate'
            ,method: 'POST'
            // ,cors: true
            ,withCredentials:true
            // ,jsonData: options.data
            ,params: options.data
            ,scope: this
            ,callback: this.onLoginReturn
            ,original: options
        });
    }
    ,onLoginReturn: function(options, success, response) {
        options = options.original;
        var session = this.getSession(),
            resultSet;

        if (success) {
            resultSet = Ext.data.schema.Schema.lookupEntity('AM.model.security.LoginResult').getProxy().getReader().read(response);

            if (resultSet.getSuccess()) {
                Ext.callback(options.success, options.scope);
                return;
            }
        }

        Ext.callback(options.failure, options.scope, [response]);
    }
    ,onLoginFailure: function(response) {

        //console.log('failure:'+response.responseText)

        var obj = Ext.decode(response.responseText);
        // Do something
        Ext.getBody().unmask();
        Ext.MessageBox.alert('登录失败', obj.message+',请重新输入.');
    },

    onLoginSuccess: function() {

        window.location="index.html";

        //Ext.getBody().unmask();
    }

});