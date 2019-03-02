
Ext.define('AM.model.util.CheckTreeNode', {
	extend: 'Ext.data.TreeModel',

	fields: [
		{
			name: 'id',
			type: 'string'
		}
		,{
			name: 'name'
		}
		,{
			name: 'url'
		}
		,{
			name: 'leaf'
		}
		,{
			name: 'iconCls'
		}
		,{
			name: 'type'
		}
		,{
			name: 'orderIndex'
		}
		,{
			name: 'parentId'
		}
		,{
			name: 'objectId'
		}
		,{
			name: 'checked',
			defaultValue: false,
			type: 'boolean'
		}
	]
});




