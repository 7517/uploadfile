

Ext.MsgUtil = function(){
    var msgCt;

    function createBox(t, s){
       // return ['<div class="msg">',
       //         '<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>',
       //         '<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc"><h3>', t, '</h3>', s, '</div></div></div>',
       //         '<div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>',
       //         '</div>'].join('');
       return '<div class="msg"><h3>' + t + '</h3><p>' + s + '</p></div>';
    }
    return {
    	notification:function(title, message){
    		this.show(title, message)
		}
        ,show : function(title, message){
            // if(!msgCt){
            //     msgCt = Ext.DomHelper.insertFirst(document.body, {id:'msg-div'}, true);
            // }
            //var s = Ext.String.format.apply(String, Array.prototype.slice.call(arguments, 1));
            //var m = Ext.DomHelper.append(msgCt, createBox(title, s), true);
            // var m = Ext.DomHelper.append(msgCt, createBox(title, format), true);
            // m.hide();
            // m.slideIn('t').ghost("t", { delay: 1000, remove: true});

	        // Ext.toast({
		     //    title:title,
		     //    html: message,
		     //    closable: false,
		     //    //align: 't',
		     //    //slideInDuration: 200,
		     //    minWidth: 200,
		     //    timeout: 1000
	        // });
	        Ext.toast({
		        html: message,
		        title: title,
		        width: 200,
		        align: 'tr'
	        })
        },

        init : function(){
            if(!msgCt){
                // It's better to create the msg-div here in order to avoid re-layouts 
                // later that could interfere with the HtmlEditor and reset its iFrame.
                msgCt = Ext.DomHelper.insertFirst(document.body, {id:'msg-div'}, true);
            }
//            var t = Ext.get('exttheme');
//            if(!t){ // run locally?
//                return;
//            }
//            var theme = Cookies.get('exttheme') || 'aero';
//            if(theme){
//                t.dom.value = theme;
//                Ext.getBody().addClass('x-'+theme);
//            }
//            t.on('change', function(){
//                Cookies.set('exttheme', t.getValue());
//                setTimeout(function(){
//                    window.location.reload();
//                }, 250);
//            });
//
//            var lb = Ext.get('lib-bar');
//            if(lb){
//                lb.show();
//            }
        }
    };
}();
Ext.MsgUtil.toast = function (title, msg) {
	Ext.toast({
		title:title,
		html: msg,
		closable: false,
		align: 't',
		slideInDuration: 400,
		minWidth: 400
	});
}
Ext.LoadMaskUtil = {};
Ext.LoadMaskUtil.show = function(target){
	
	if(!Ext.LoadMaskUtil.instance){
		//Ext.LoadMaskUtil.instance = new Ext.LoadMask(target, {msg:"Please wait..."});
		Ext.LoadMaskUtil.instance = new Ext.LoadMask(target, {msg:"Please wait..."});
	}
	
	Ext.LoadMaskUtil.instance.show(target);
	
}
Ext.LoadMaskUtil.hide = function(){
	if(Ext.LoadMaskUtil.instance)
		Ext.LoadMaskUtil.instance.hide();
}


Ext.onReady(Ext.MsgUtil.init);

