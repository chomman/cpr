if (!Array.prototype.forEach){
    Array.prototype.forEach = function(fun /*, thisArg */){
        "use strict";
        if (this === void 0 || this === null || typeof fun !== "function") throw new TypeError();

        var
            t = Object(this),
            len = t.length >>> 0,
            thisArg = arguments.length >= 2 ? arguments[1] : void 0;

        for (var i = 0; i < len; i++)
            if (i in t)
                fun.call(thisArg, t[i], i, t);
    };
}

function isIE () {
	  var myNav = navigator.userAgent.toLowerCase();
	  return (myNav.indexOf('msie') != -1) ? parseInt(myNav.split('msie')[1]) : false;
}

//!function(){"use strict";function a(a,b,c){"addEventListener"in window?a.addEventListener(b,c,!1):"attachEvent"in window&&a.attachEvent("on"+b,c)}function b(){var a,b=["moz","webkit","o","ms"];for(a=0;a<b.length&&!v;a+=1)v=window[b[a]+"RequestAnimationFrame"];v||c(" RequestAnimationFrame not supported")}function c(a){x.log&&"object"==typeof console&&console.log(r+"[Host page"+t+"]"+a)}function d(a){function b(){function a(){h(v),f()}i(a,v,"resetPage")}function d(a){c(" Removing iFrame: "+a.id),a.parentNode.removeChild(a),c(" --")}function e(){var a=u.substr(s).split(":");return{iframe:document.getElementById(a[0]),id:a[0],height:a[1],width:a[2],type:a[3]}}function j(){var b=a.origin,d=v.iframe.src.split("/").slice(0,3).join("/");if(x.checkOrigin&&(c(" Checking conection is from: "+d),""+b!="null"&&b!==d))throw new Error("Unexpected message received from: "+b+" for "+v.iframe.id+". Message was: "+a.data+". This error can be disabled by adding the checkOrigin: false option.");return!0}function k(){return r===(""+u).substr(0,s)}function l(){var a=v.type in{"true":1,"false":1};return a&&c(" Ignoring init message from meta parent page"),a}function m(){var a=u.substr(u.indexOf(":")+q+6);c(" MessageCallback passed: {iframe: "+v.iframe.id+", message: "+a+"}"),x.messageCallback({iframe:v.iframe,message:a}),c(" --")}function n(){if(null===v.iframe)throw new Error("iFrame ("+v.id+") does not exist on "+t);return!0}function p(){switch(v.type){case"close":d(v.iframe),x.resizedCallback(v);break;case"message":m();break;case"reset":g(v);break;default:b(),x.resizedCallback(v)}}var u=a.data,v={};k()&&(c(" Received: "+u),v=e(),!l()&&n()&&j()&&(o=!1,p()))}function e(){null===u&&(u={x:void 0!==window.pageXOffset?window.pageXOffset:document.documentElement.scrollLeft,y:void 0!==window.pageYOffset?window.pageYOffset:document.documentElement.scrollTop},c(" Get position: "+u.x+","+u.y))}function f(){null!==u&&(window.scrollTo(u.x,u.y),c(" Set position: "+u.x+","+u.y),u=null)}function g(a){function b(){h(a),j("reset","reset",a.iframe)}c(" Size reset requested by "+("init"===a.type?"host page":"iFrame")),e(),i(b,a,"init")}function h(a){function b(b){a.iframe.style[b]=a[b]+"px",c(" IFrame ("+a.iframe.id+") "+b+" set to "+a[b]+"px")}x.sizeHeight&&b("height"),x.sizeWidth&&b("width")}function i(a,b,d){d!==b.type&&v?(c(" Requesting animation frame"),v(a)):a()}function j(a,b,d){c("["+a+"] Sending msg to iframe ("+b+")"),d.contentWindow.postMessage(r+b,"*")}function k(){function b(a){return""===a&&(i.id=a="iFrameResizer"+n++,c(" Added missing iframe ID: "+a)),a}function d(){c(" IFrame scrolling "+(x.scrolling?"enabled":"disabled")+" for "+k),i.style.overflow=!1===x.scrolling?"hidden":"auto",i.scrolling=!1===x.scrolling?"no":"yes"}function e(){("number"==typeof x.bodyMargin||"0"===x.bodyMargin)&&(x.bodyMarginV1=x.bodyMargin,x.bodyMargin=""+x.bodyMargin+"px")}function f(){return k+":"+x.bodyMarginV1+":"+x.sizeWidth+":"+x.log+":"+x.interval+":"+x.enablePublicMethods+":"+x.autoResize+":"+x.bodyMargin+":"+x.heightCalculationMethod+":"+x.bodyBackground+":"+x.bodyPadding}function h(b){a(i,"load",function(){j("iFrame.onload",b,i),!o&&x.heightCalculationMethod in w&&g({iframe:i,height:0,width:0,type:"init"})}),j("init",b,i)}var i=this,k=b(i.id);d(),e(),h(f())}function l(){function a(a){if("IFRAME"!==a.tagName)throw new TypeError("Expected <IFRAME> tag, found <"+a.tagName+">.");k.call(a)}function b(a){if(a=a||{},"object"!=typeof a)throw new TypeError("Options is not an object.");for(var b in y)y.hasOwnProperty(b)&&(x[b]=a.hasOwnProperty(b)?a[b]:y[b])}window.iFrameResize=function(c,d){b(c),Array.prototype.forEach.call(document.querySelectorAll(d||"iframe"),a)}}function m(a){a.fn.iFrameResize=function(b){return x=a.extend({},y,b),this.filter("iframe").each(k).end()}}var n=0,o=!0,p="message",q=p.length,r="[iFrameSizer]",s=r.length,t="",u=null,v=window.requestAnimationFrame,w={max:1,scroll:1,bodyScroll:1,documentElementScroll:1},x={},y={autoResize:!0,bodyBackground:null,bodyMargin:null,bodyMarginV1:8,bodyPadding:null,checkOrigin:!0,enablePublicMethods:!1,heightCalculationMethod:"offset",interval:32,log:!1,messageCallback:function(){},resizedCallback:function(){},scrolling:!1,sizeHeight:!0,sizeWidth:!1};b(),a(window,"message",d),l(),"jQuery"in window&&m(jQuery)}();

/*
 * File: iframeReizer.js
 * Desc: Force iframes to size to content.
 * Requires: iframeResizer.contentWindow.js to be loaded into the target frame.
 * Author: David J. Bradshaw - dave@bradshaw.net
 * Contributor: Jure Mav - jure.mav@gmail.com
 */
( function() {
    'use strict';

	var
		count                 = 0,
		firstRun              = true,
		msgHeader             = 'message',
		msgHeaderLen          = msgHeader.length,
		msgId                 = '[iFrameSizer]', //Must match iframe msg ID
		msgIdLen              = msgId.length,
		page                  =  '', //:'+location.href, //Uncoment to debug nested iFrames
		pagePosition          = null,
		requestAnimationFrame = window.requestAnimationFrame,
		resetRequiredMethods  = {max:1,scroll:1,bodyScroll:1,documentElementScroll:1},
		settings              = {},
		defaults              = {
			autoResize                : true,
			bodyBackground            : null,
			bodyMargin                : null,
			bodyMarginV1              : 8,
			bodyPadding               : null,
			checkOrigin               : true,
			enablePublicMethods       : false,
			heightCalculationMethod   : 'offset',
			interval                  : 32,
			log                       : false,
			messageCallback           : function(){},
			resizedCallback           : function(){},
			scrolling                 : false,
			sizeHeight                : true,
			sizeWidth                 : false
		};

	function addEventListener(obj,evt,func){
		if ('addEventListener' in window){
			obj.addEventListener(evt,func, false);
		} else if ('attachEvent' in window){//IE
			obj.attachEvent('on'+evt,func);
		}
	}

	function setupRequestAnimationFrame(){
		var
			vendors = ['moz', 'webkit', 'o', 'ms'],
			x;

		// Remove vendor prefixing if prefixed and break early if not
		for (x = 0; x < vendors.length && !requestAnimationFrame; x += 1) {
			requestAnimationFrame = window[vendors[x] + 'RequestAnimationFrame'];
		}

		if (!(requestAnimationFrame)){
			log(' RequestAnimationFrame not supported');
		}
	}

	function log(msg){
		if (settings.log && (typeof console === 'object')){
			console.log(msgId + '[Host page'+page+']' + msg);
		}
	}


	function iFrameListener(event){
		function resizeIFrame(){
			function resize(){
				setSize(messageData);
				setPagePosition();
			}

			syncResize(resize,messageData,'resetPage');
		}

		function closeIFrame(iframe){
			log(' Removing iFrame: '+iframe.id);
			iframe.parentNode.removeChild(iframe);
			log(' --');
		}

		function processMsg(){
			var data = msg.substr(msgIdLen).split(':');

			return {
				iframe: document.getElementById(data[0]),
				id:     data[0],
				height: data[1],
				width:  data[2],
				type:   data[3]
			};
		}

		function isMessageFromIFrame(){

			var
				origin     = event.origin,
				remoteHost = messageData.iframe.src.split('/').slice(0,3).join('/');

			if (settings.checkOrigin) {
				log(' Checking conection is from: '+remoteHost);

				if ((''+origin !== 'null') && (origin !== remoteHost)) {
					throw new Error(
						'Unexpected message received from: ' + origin +
						' for ' + messageData.iframe.id +
						'. Message was: ' + event.data +
						'. This error can be disabled by adding the checkOrigin: false option.'
					);
				}
			}

			return true;
		}

		function isMessageForUs(){
			return msgId === ('' + msg).substr(0,msgIdLen); //''+Protects against non-string msg
		}

		function isMessageFromMetaParent(){
			//test if this message is from a parent above us. This is an ugly test, however, updating
			//the message format would break backwards compatibity.
			var retCode = messageData.type in {'true':1,'false':1};

			if (retCode){
				log(' Ignoring init message from meta parent page');
			}

			return retCode;
		}

		function forwardMsgFromIFrame(){
			var msgBody = msg.substr(msg.indexOf(':')+msgHeaderLen+6); //6 === ':0:0:' + ':'

			log(' MessageCallback passed: {iframe: '+ messageData.iframe.id + ', message: ' + msgBody + '}');
			settings.messageCallback({
				iframe: messageData.iframe,
				message: msgBody
			});
			log(' --');
		}

		function checkIFrameExists(){
			if (null === messageData.iframe) {
				throw new Error('iFrame ('+messageData.id+') does not exist on ' + page);
			}
			return true;
		}

		function actionMsg(){
			switch(messageData.type){
				case 'close':
					closeIFrame(messageData.iframe);
					settings.resizedCallback(messageData);
					break;
				case 'message':
					forwardMsgFromIFrame();
					break;
				case 'reset':
					resetIFrame(messageData);
					break;
				default:
					resizeIFrame();
					settings.resizedCallback(messageData);
			}
		}

		var
			msg = event.data,
			messageData = {};

		if (isMessageForUs()){
			log(' Received: '+msg);
			messageData = processMsg();
			if ( !isMessageFromMetaParent() && checkIFrameExists() && isMessageFromIFrame() ){
				firstRun = false;
				actionMsg();
			}
		}
	}


	function getPagePosition (){
		if(null === pagePosition){
			pagePosition = {
				x: (window.pageXOffset !== undefined) ? window.pageXOffset : document.documentElement.scrollLeft,
				y: (window.pageYOffset !== undefined) ? window.pageYOffset : document.documentElement.scrollTop
			};
			log(' Get position: '+pagePosition.x+','+pagePosition.y);
		}
	}

	function setPagePosition(){
		if(null !== pagePosition){
			window.scrollTo(pagePosition.x,pagePosition.y);
			log(' Set position: '+pagePosition.x+','+pagePosition.y);
			pagePosition = null;
		}
	}

	function resetIFrame(messageData){
		function reset(){
			setSize(messageData);
			trigger('reset','reset',messageData.iframe);
		}

		log(' Size reset requested by '+('init'===messageData.type?'host page':'iFrame'));
		getPagePosition();
		syncResize(reset,messageData,'init');
	}

	function setSize(messageData){
		function setDimension(dimension){
			messageData.iframe.style[dimension] = messageData[dimension] + 'px';
			log(
				' IFrame (' + messageData.iframe.id +
				') ' + dimension +
				' set to ' + messageData[dimension] + 'px'
			);
		}

		if( settings.sizeHeight ) { setDimension('height'); }
		if( settings.sizeWidth  ) { setDimension('width');  }
	}

	function syncResize(func,messageData,doNotSync){
		if(doNotSync!==messageData.type && requestAnimationFrame){
			log(' Requesting animation frame');
			requestAnimationFrame(func);
		} else {
			func();
		}
	}

	function trigger(calleeMsg,msg,iframe){
		log('[' + calleeMsg + '] Sending msg to iframe ('+msg+')');
		iframe.contentWindow.postMessage( msgId + msg, '*' );
	}


	function setupIFrame(){
		function ensureHasId(iframeID){
			if (''===iframeID){
				iframe.id = iframeID = 'iFrameResizer' + count++;
				log(' Added missing iframe ID: '+ iframeID);
			}

			return iframeID;
		}

		function setScrolling(){
			log(' IFrame scrolling ' + (settings.scrolling ? 'enabled' : 'disabled') + ' for ' + iframeID);
			iframe.style.overflow = false === settings.scrolling ? 'hidden' : 'auto';
			iframe.scrolling      = false === settings.scrolling ? 'no' : 'yes';
		}

		//The V1 iFrame script expects an int, where as in V2 expects a CSS
		//string value such as '1px 3em', so if we have an int for V2, set V1=V2
		//and then convert V2 to a string PX value.
		function setupBodyMarginValues(){
			if (('number'===typeof(settings.bodyMargin)) || ('0'===settings.bodyMargin)){
				settings.bodyMarginV1 = settings.bodyMargin;
				settings.bodyMargin   = '' + settings.bodyMargin + 'px';
			}
		}

		function createOutgoingMsg(){
			return iframeID +
					':' + settings.bodyMarginV1 +
					':' + settings.sizeWidth +
					':' + settings.log +
					':' + settings.interval +
					':' + settings.enablePublicMethods +
					':' + settings.autoResize +
					':' + settings.bodyMargin +
					':' + settings.heightCalculationMethod +
					':' + settings.bodyBackground +
					':' + settings.bodyPadding;
		}

		function init(msg){
			//We have to call trigger twice, as we can not be sure if all 
			//iframes have completed loading when this code runs. The
			//event listener also catches the page changing in the iFrame.
			addEventListener(iframe,'load',function(){
				trigger('iFrame.onload',msg,iframe);
				if (!isIE() && !firstRun && settings.heightCalculationMethod in resetRequiredMethods){
					resetIFrame({
						iframe:iframe,
						height:0,
						width:0,
						type:'init'
					});
				}
			});
			trigger('init',msg,iframe);
		}

		var
            /*jshint validthis:true */
			iframe   = this,
			iframeID = ensureHasId(iframe.id);

		setScrolling();
		setupBodyMarginValues();
		init(createOutgoingMsg());
	}

	function createNativePublicFunction(){
		function init(element){
			if('IFRAME' !== element.tagName) {
				throw new TypeError('Expected <IFRAME> tag, found <'+element.tagName+'>.');
			} else {
				setupIFrame.call(element);
			}
		}

		function processOptions(options){
			options = options || {};

			if ('object' !== typeof options){
				throw new TypeError('Options is not an object.');
			}

			for (var option in defaults) {
				if (defaults.hasOwnProperty(option)){
					settings[option] = options.hasOwnProperty(option) ? options[option] : defaults[option];
				}
			}
		}

		window.iFrameResize = function iFrameResizeF(options,selecter){
			processOptions(options);
			Array.prototype.forEach.call( document.querySelectorAll( selecter || 'iframe' ), init );
		};
	}

	function createJQueryPublicMethod($){
		$.fn.iFrameResize = function $iFrameResizeF(options) {
			settings = $.extend( {}, defaults, options );
			return this.filter('iframe').each( setupIFrame ).end();
		};
	}

	setupRequestAnimationFrame();
	addEventListener(window,'message',iFrameListener);
	createNativePublicFunction();
	if ('jQuery' in window) { createJQueryPublicMethod(jQuery); }

})();




function initNLFWidget(){
	var opts = {};
	opts = {
			log : (document.location.hostname == "localhost"),
			checkOrigin : false,
			heightCalculationMethod : 'bodyScroll'
	};
	if(window.jQuery){
		$('iframe.nlf-widget').iFrameResize(opts);
	}else{
		iFrameResize( opts, ".nlf-widget");
	}
}
setTimeout(	initNLFWidget, 100);
