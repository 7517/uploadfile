/**
 * This class creates a multiline text field, which can be used as a direct replacement for traditional
 * textarea fields. In addition, it supports automatically {@link #grow growing} the height of the textarea to
 * fit its content.
 *
 * All of the configuration options from {@link Ext.form.field.Text} can be used on TextArea.
 *
 * Example usage:
 *
 *     @example
 *     Ext.create('Ext.form.FormPanel', {
 *         title      : 'Sample TextArea',
 *         width      : 400,
 *         bodyPadding: 10,
 *         renderTo   : Ext.getBody(),
 *         items: [{
 *             xtype     : 'textareafield',
 *             grow      : true,
 *             name      : 'message',
 *             fieldLabel: 'Message',
 *             anchor    : '100%'
 *         }]
 *     });
 *
 * Some other useful configuration options when using {@link #grow} are {@link #growMin} and {@link #growMax}.
 * These allow you to set the minimum and maximum grow heights for the textarea.
 *
 * **NOTE:** In some browsers, carriage returns ('\r', not to be confused with new lines)
 * will be automatically stripped out the value is set to the textarea. Since we cannot
 * use any reasonable method to attempt to re-insert these, they will automatically be
 * stripped out to ensure the behaviour is consistent across browser.
 */
Ext.define('AM.ux.form.field.CodeMirror', {
    extend:'Ext.form.field.Text',
    alias: ['widget.codemirrorfield', 'widget.codemirror'],
    alternateClassName: 'Ext.form.CodeMirror',
    requires: [
        'Ext.XTemplate',
        'Ext.util.DelayedTask'
    ],

    mode:'gfm',
    theme:'eclipse',
    readOnly:false,
    fieldSubTpl: [
        '<textarea id="{id}" data-ref="inputEl" {inputAttrTpl}',
        '<tpl if="name"> name="{name}"</tpl>',
        '<tpl if="placeholder"> placeholder="{placeholder}"</tpl>',
        '<tpl if="maxLength !== undefined"> maxlength="{maxLength}"</tpl>',
        '<tpl if="readOnly"> readonly="readonly"</tpl>',
        '<tpl if="disabled"> disabled="disabled"</tpl>',
        '<tpl if="tabIdx != null"> tabindex="{tabIdx}"</tpl>',
        ' class="{fieldCls} {typeCls} {typeCls}-{ui} {inputCls}" ',
        '<tpl if="fieldStyle"> style="{fieldStyle}"</tpl>',
        '<tpl foreach="ariaElAttributes"> {$}="{.}"</tpl>',
        '<tpl foreach="inputElAriaAttributes"> {$}="{.}"</tpl>',
        ' autocomplete="off">\n',
        '<tpl if="value">{[Ext.util.Format.htmlEncode(values.value)]}</tpl>',
        '</textarea>',
        {
            disableFormats: true
        }
    ],

    /**
     * @cfg {Boolean} enterIsSpecial
     * True if you want the ENTER key to be classed as a special key and the {@link #specialkey} event to be fired
     * when ENTER is pressed.
     */
    enterIsSpecial: false,

    /**
     * @cfg {Boolean} preventScrollbars
     * true to prevent scrollbars from appearing regardless of how much text is in the field. This option is only
     * relevant when {@link #grow} is true. Equivalent to setting overflow: hidden.
     */
    preventScrollbars: false,

    returnRe: /\r/g,

    inputCls: Ext.baseCSSPrefix + 'form-textarea',

    extraFieldBodyCls: Ext.baseCSSPrefix + 'form-textarea-body',

    ariaAttributes: {
        'aria-multiline': true
    },

    //<debug>
    constructor: function(config) {
        this.callParent([config]);
        if (this.cols) {
            Ext.log.warn('Ext.form.field.TextArea "cols" config was removed in Ext 5.0. Please specify a "width" or use a layout instead.');
        }

        if (this.rows) {
            Ext.log.warn('Ext.form.field.TextArea "rows" config was removed in Ext 5.0. Please specify a "height" or use a layout instead.');
        }
    },
    //</debug>

    getSubTplData: function(fieldData) {
        var me = this,
            fieldStyle = me.getFieldStyle(),
            ret = me.callParent(arguments);

        if (me.grow) {
            if (me.preventScrollbars) {
                ret.fieldStyle = (fieldStyle||'') + ';overflow:hidden;height:' + me.growMin + 'px';
            }
        }

        return ret;
    },

    afterRender: function () {
        var me = this;

        me.callParent(arguments);

        me.needsMaxCheck = me.enforceMaxLength && me.maxLength !== Number.MAX_VALUE && !Ext.supports.TextAreaMaxLength;
        if (me.needsMaxCheck) {
            me.inputEl.on('paste', me.onPaste, me);
        }
        me.codeMirrorEditor = new CodeMirror.fromTextArea(Ext.getDom(me.id+'-inputEl')
            ,{
                mode:  me.mode
                ,lineNumbers: true
                ,theme:me.theme
                ,matchBrackets: true
                ,readOnly:me.readOnly
                ,continueComments: "Enter"
            }
        );
        me.codeMirrorEditor.setSize('auto','500px');

    },
    setTheme:function(theme){
        if(this.codeMirrorEditor){
            return this.codeMirrorEditor.setOption('theme', theme);
        }
    },
    setMode:function(mode){
        if(this.codeMirrorEditor){
            return this.codeMirrorEditor.setOption('mode', mode);
        }
    },
    setModeByFileName:function(fileName){

        var fileName = Ext.valueFrom(fileName, "")

        var mode = 'gfm';

        if(fileName.lastIndexOf(".java")>=0){
            mode = 'text/x-java';
        }else if(fileName.lastIndexOf(".xml")>=0){
            mode = 'text/html';
        }else if(fileName.lastIndexOf(".yml")>=0){
            mode = 'text/yaml'
        }else if(fileName.lastIndexOf(".properties")>=0){
            mode = 'text/x-properties'
        }

        this.mode = mode;

        if(this.codeMirrorEditor){
            return this.codeMirrorEditor.setOption('mode', mode);
        }
    },

    transformRawValue: function(value){
        return value;
    },
    setValue: function(value) {
        var me = this;
        me.setRawValue(me.valueToRaw(value));
        if(this.codeMirrorEditor){
            this.codeMirrorEditor.setValue(Ext.valueFrom(value, ''));
        }
        return me.mixins.field.setValue.call(me, value);
    },
    setRawValue: function(value) {
        var me = this,
            rawValue = me.rawValue;

        if (!me.transformRawValue.$nullFn) {
            value = me.transformRawValue(value);
        }

        value = Ext.valueFrom(value, '');

        if (rawValue === undefined || rawValue !== value) {
            me.rawValue = value;

            // Some Field subclasses may not render an inputEl
            if (me.inputEl) {
                me.bindChangeEvents(false);
                me.inputEl.dom.value = value;
                me.bindChangeEvents(true);
            }
        }

        if (me.rendered && me.reference) {
            me.publishState('rawValue', value);
        }

        return value;
    },
    getValue: function() {
        var me = this,
            val = me.rawToValue(me.processRawValue(me.getRawValue()));
        me.value = val;
        return val;
    },

    getRawValue: function() {
        console.log('CodeMirror getRawValue')

        var me = this,
            v = Ext.valueFrom(me.rawValue, '');

        if(this.codeMirrorEditor){
            v = this.codeMirrorEditor.getValue();
        }
        console.log("v:"+v);
        me.rawValue = v;
        console.log("me.rawValue："+me.rawValue)
        return v;
    },
    getModelData: function(includeEmptyText, isSubmitting) {
        var me = this,
            data = null;

        // Note that we need to check if this operation is being called from a Submit action because displayfields aren't
        // to be submitted,  but they can call this to get their model data.
        if (!me.disabled && (me.submitValue || !isSubmitting)) {
            data = {};
            data[me.getFieldIdentifier()] = me.getValue();
            if(this.codeMirrorEditor){
                data[me.getFieldIdentifier()] = this.codeMirrorEditor.getValue();
            }
        }
        return data;
    },

    onPaste: function(){
        var me = this;
        if (!me.pasteTask) {
            me.pasteTask = new Ext.util.DelayedTask(me.pasteCheck, me);
        }
        // since we can't get the paste data, we'll give the area a chance to populate
        me.pasteTask.delay(1);
    },

    pasteCheck: function(){
        var me = this,
            value = me.getValue(),
            max = me.maxLength;

        if (value.length > max) {
            value = value.substr(0, max);
            me.setValue(value);
        }
    },
    startCheckChangeTask: function() {console.log('startCheckChangeTask')
        var me = this,
            task = me.checkChangeTask;

        if (!task) {
            me.checkChangeTask = task = new Ext.util.DelayedTask(me.doCheckChangeTask, me);
        }
        if (!me.bindNotifyListener) {
            // We continually create/destroy the listener as needed (see doCheckChangeTask) because we're listening
            // to a global event, so we don't want the event to be triggered unless absolutely necessary. In this case,
            // we only need to fix the value when we have a pending change to check.
            me.bindNotifyListener = Ext.on('beforebindnotify', me.onBeforeNotify, me, {destroyable: true});
        }
        task.delay(me.checkChangeBuffer);
    },

    doCheckChangeTask: function() {console.log('doCheckChangeTask')
        var bindNotifyListener = this.bindNotifyListener;

        if (bindNotifyListener) {
            bindNotifyListener.destroy();
            this.bindNotifyListener = null;
        }
        this.checkChange();
    },
    /**
     * @private
     */
    fireKey: function(e) {
        var me = this,
            key = e.getKey(),
            value;

        if (e.isSpecialKey() && (me.enterIsSpecial || (key !== e.ENTER || e.hasModifier()))) {
            me.fireEvent('specialkey', me, e);
        }

        // Enter key must not bubble up where it can trigger defaultButton action
        if (key === e.ENTER) {
            e.stopPropagation();
        }

        if (me.needsMaxCheck && key !== e.BACKSPACE && key !== e.DELETE && !e.isNavKeyPress() && !me.isCutCopyPasteSelectAll(e, key)) {
            value = me.getValue();
            if (value.length >= me.maxLength) {
                e.stopEvent();
            }
        }
    },

    isCutCopyPasteSelectAll: function(e, key) {
        if (e.ctrlKey) {
            return key === e.A || key === e.C || key === e.V || key === e.X;
        }
        return false;
    },

    /**
     * Automatically grows the field to accomodate the height of the text up to the maximum
     * field height allowed. This only takes effect if {@link #grow} = true, and fires the
     * {@link #autosize} event if the height changes.
     */

    onResize: function(width, height, oldWidth, oldHeight) {
        var me = this;

        if(me.rendered && this.codeMirrorEditor){
            console.log('CodeMirror valueToRaw:'+this.codeMirrorEditor.getValue())

            me.codeMirrorEditor.setSize(width-2, height-25);
        }
        me.callParent([width, height, oldWidth, oldHeight]);
    },
    doDestroy: function() {

        var task = this.pasteTask;

        if (task) {
            task.cancel();
        }

        this.callParent();
    }
});