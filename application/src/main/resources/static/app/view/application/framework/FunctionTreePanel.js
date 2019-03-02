Ext.define('AM.view.application.framework.FunctionTreePanel', {
    extend: 'Ext.tree.Panel',
	requires: [
        //'AM.store.security.AllResourceTreeStore'

	],
    alias: 'widget.mainFunctionTreePanel',
    width: 150,
    collapsible: true,
    title: '功能菜单',
    //store: Ext.create('AM.store.security.AllResourceTreeStore'),

    displayField: 'name',
    // lines: false,
    rootVisible: false,
    singleExpand: false,
    useArrows: true,
	animate:true,

    initComponent: function() {
        var me = this;

        Ext.apply(me, {

            viewConfig: {

            }
            ,style:{
                backgroundColor: '#28384a'//'#28384a'
            }
        });

	    // Ext.data.StoreManager.each(function(obj){
		 //    console.log(obj.getId());
	    // })
        me.callParent(arguments);
    }

});