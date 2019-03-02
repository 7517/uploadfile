Ext.define('AM.view.Viewport', {
    extend: 'Ext.container.Viewport',
    requires: [
    	'AM.view.ViewportController'
    	// ,'AM.view.application.framework.HeaderContainer'
        , 'AM.view.application.framework.HeaderToolbar'
        , 'AM.view.application.framework.FunctionPanel'
        , 'AM.view.application.framework.MainContentPanel'
        , 'AM.view.application.dashboard.Dashboard'

        //,'AM.view.ux.FileUploadPanel'
    ]
    , layout: 'border'
    , bodyCls: 'app-dashboard'
	, controller: 'ViewportController'
    , items: [
        {
            xtype:'panel'
            ,region: 'center'
            ,layout: 'border'
            , bodyCls: 'app-dashboard'
            // , bodyStyle: 'background:#2eb1f5'
            , items:[
                {
                    xtype:'mainHeaderToolbar'
                    // xtype: 'mainHeaderContainer'
                    ,region: 'north'
                    ,height: 64
                }//Header Container 必须放在第一个。如果放在最后一个，则可能出现，头部样式设置的不合适（头部的实际高度比展示的高度要大，则会在样式上有个透明的块遮挡tab页），导致tab的关闭按钮无法点击的情况
                , {
                    xtype: 'mainContentPanel'
                    // , padding:'5'
                    ,region: 'center'
                    // ,frame:true
                    // , bodyCls: 'app-dashboard'
                    ,bodyStyle: 'background:#2eb1f5'
                }
            ]
        }
        , {
            xtype: 'mainFunctionPanel'
            ,region: 'west'
            ,width: 250
            , id:'mainFunctionPanel'
            ,split: true
        }
    ]
    , listeners: {
    	afterrender: 'loadAppInfo'
	}
});

