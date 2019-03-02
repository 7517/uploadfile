
Ext.define('AM.model.application.security.ResourceTreeNode', {
	extend: 'Ext.data.TreeModel',

	fields: [
		{
			name: 'id',
			type: 'string'
		},
		{
			name: 'name'
		},
		{
			name: 'url'
		},
		{
			name: 'leaf'
			,calculate: function (data) {
				return "function" ==  data.type;
			}
		}
		,{
			name: 'iconCls'
			,calculate: function (data) {
				return "function" == data.type?"x-fa fa-file":"x-fa fa-folder";
			}
		},
		{
			name: 'type'
		},
		{
			name: 'orderIndex'
		},
		{
			name: 'parentId'
		}
	]
});




