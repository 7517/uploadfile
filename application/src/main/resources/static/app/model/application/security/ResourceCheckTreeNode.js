Ext.define('AM.model.application.security.ResourceCheckTreeNode', {
    extend: 'Ext.data.TreeModel',

    fields: [
        {
            name: 'id',
            type: 'int'
        },
        {
            name: 'name',
            type: 'string'
        },
        {
            name: 'checked',
            defaultValue: false,
            type: 'boolean'
        }
    ]
});