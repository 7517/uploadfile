Ext.define('AM.model.util.CommonTreeNode', {
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
		},
		{
			name: 'iconCls'
		},
		{
			name: 'type'
		},
		{
			name: 'orderIndex'
		},
		{
			name: 'parentId'
		},{
			name: 'objectId'
		}
	]
});

