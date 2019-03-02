Ext.define('AM.view.application.framework.HeaderContainerController', {
    extend: 'Ext.app.ViewController',
    requires: [
        // 'AM.model.main.Resource'
    ]
    , alias: 'controller.HeaderContainerController'
    , refreshUserInfo: function (component) {
        var me = this;
        console.log("header refresh user info")
        me.loadUserInfo(component);
        var runner = new Ext.util.TaskRunner();

        var task = runner.newTask({
            run: function() {
                me.loadUserInfo(component)
            }
            ,interval: 60 * 1000
        });
        task.start();
    }
    , loadUserInfo: function (component) {
        // console.log("header load user info")
        Ext.Ajax.request({
            //获取当前用户
            url: global.url+'/current/account'
            , method: 'POST'
            // , scope: this
            , cors:true
            ,withCredentials:true
            , success: function(response){
                console.log(response.responseText);

                var accountConfig = Ext.decode(response.responseText)

                var account = Ext.create('AM.model.application.security.Account', accountConfig);
                component.setHtml(account.get('name'))
            }
        });



    }
    , userLogout: function(){
        Ext.Ajax.request({
            url: 'logout'
            ,method: 'POST'
            // ,params: options.data
            ,scope: this
            ,success:function(){
                var alertWindow = null;
                var second = 3;
                var runner = new Ext.util.TaskRunner();

                var task = runner.newTask({
                    run: function() {
                        if(second <= 0){
                            task.stop();
                            window.location = "login.html"
                        }
                        // if(!alertWindow){
                        alertWindow = Ext.MessageBox.alert("登出成功", "进入登录页. ("+(second--)+"s)");
                        // }
                    }
                    ,interval: 1000
                });
                task.start();

            }
        });
    }
    , hideFunctionPanel:function(){
        var mainFunctionPanel = Ext.getCmp('mainFunctionPanel');
        var collapsed = mainFunctionPanel.getCollapsed();
        console.log("collapsed:"+collapsed);

        mainFunctionPanel.setCollapsed(!collapsed);
    }
    ,showChangePasswordWindow:function(){
        if(!this.getView().changePasswordWindow){
            this.getView().changePasswordWindow = Ext.create('AM.view.application.framework.ChangePasswordWindow');
        }
        this.getView().changePasswordWindow.show();
    }

})