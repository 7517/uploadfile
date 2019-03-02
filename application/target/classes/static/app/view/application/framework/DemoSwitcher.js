Ext.define('AM.view.application.framework.UserSwitcher', {
    extend: 'Ext.Component'
    ,xtype: 'userSwitcher'
    ,cls: [ 'x-fa', 'fa-bars' ]
    ,initComponent: function() {

        var me = this
            ,menuItems = []
            ,menu;

        var logoutItem = {
            text:'登出'
            , handler: function () {
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
        }

        menuItems.push(logoutItem);

        menuItems.push('-');


        menuItems.push('-', {
            text: 'Modern Toolkit',
            iconCls: 'x-fa fa-external-link',
            handler: function () {
                window.location = location.pathname + '?modern';
            }
        });

        menu = new Ext.menu.Menu({
            items: menuItems
        });

        this.on({
            scope: this,
            click: function (e) {
                menu.showBy(this);
            },
            element: 'el'
        });

        this.callParent();
    }
});
