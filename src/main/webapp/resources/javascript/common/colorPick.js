/*!
*
* ColorPick jQuery plugin
* https://github.com/philzet/ColorPick.js
*
* Copyright (c) 2017 Phil Zet (a.k.a. Philipp Zakharchenko)
* Licensed under the MIT License
*
*/

(function( $ ) {
 
    $.fn.colorPick = function(config) {
 
        return this.each(function() {
            new $.colorPick(this, config || {});
        });
 
    };
    
    $.colorPick = function (element, options) {
        options = options || {};
        this.options = $.extend({}, $.fn.colorPick.defaults, options);
        if(options.str) {
            this.options.str = $.extend({}, $.fn.colorpickr.defaults.str, options.str);
        }
        this.color   = this.options.initialColor;
        this.element = $(element);
        return this.element.hasClass(this.options.pickrclass) ? this : this.init();
    };
    
    $.fn.colorPick.defaults = {
        'initialColor': 'none',
        'allowRecent': false,
        'recentMax': 5,
        'palette': ["#ffffff", "#ff99cc", "#ffcc99", "#ffff99", "#ccffff", "#99ccff", "#dfef46", "#00ff00", "#00ffff", "#00ccff", "#993366", "#ffc107", "#3366ff", "#800080", "#ffff00","#ff00ff","#ffcc00", "#ff9900", "#99cc00", "#339966", "#33cccc", "#ff6600", "#808000", "#008080", "#0000ff", "#ff0000", "#3f51b5", "#666699", "#808080", "#363b3e"],
        'onColorSelected': function() {
            this.element.css({'backgroundColor': this.color, 'color': this.color});
        }
    };
    
    $.colorPick.prototype = {
        
        init : function(){
            var self = this;
            var o = this.options;
            
            $.proxy($.fn.colorPick.defaults.onColorSelected, this)();
            
            this.element.click(function(event) {
                event.preventDefault();
                self.show(event.pageX, event.pageY);
                
                $('.colorPickButton').click(function(event) {
					self.color = $(event.target).attr('hexValue');
					self.appendToStorage($(event.target).attr('hexValue'));
					self.hide();
					$.proxy(self.options.onColorSelected, self)();
					return false;
            	});
                
                return false;
            }).blur(function() {
                self.element.val(self.color);
                $.proxy(self.options.onColorSelected, self)();
                self.hide();
                return false;
            });
            
            $(document).click(function(event) {
                self.hide();
                return true;
            });
            
            return this;
        },
        
        appendToStorage: function(color) {
	        if ($.fn.colorPick.defaults.allowRecent === true) {
	        	var storedColors = JSON.parse(localStorage.getItem("colorPickRecentItems"));
				if (storedColors == null) {
		     	    storedColors = [];
	        	}
				if ($.inArray(color, storedColors) == -1) {
		    	    storedColors.unshift(color);
					storedColors = storedColors.slice(0, $.fn.colorPick.defaults.recentMax)
					localStorage.setItem("colorPickRecentItems", JSON.stringify(storedColors));
	        	}
	        }
        },
        
        show: function(left, top) {
	        $("body").append('<div id="colorPick" class="colorPicker" style="display:none;top:' + top + 'px;left:' + left + 'px"><span>SELECT A COLOR:</span></div>');
	        jQuery.each($.fn.colorPick.defaults.palette, (index, item) => {
		        $("#colorPick").append('<div class="colorPickButton" hexValue="' + item + '" style="background:' + item + '"></div>');
			});
			if ($.fn.colorPick.defaults.allowRecent === true) {
				$("#colorPick").append('<span style="margin-top:5px">Recent:</span>');
				if (JSON.parse(localStorage.getItem("colorPickRecentItems")) == null || JSON.parse(localStorage.getItem("colorPickRecentItems")) == []) {
					$("#colorPick").append('<div class="colorPickButton colorPickDummy"></div>');
				} else {
					jQuery.each(JSON.parse(localStorage.getItem("colorPickRecentItems")), (index, item) => {
		        		$("#colorPick").append('<div class="colorPickButton" hexValue="' + item + '" style="background:' + item + '"></div>');
					});
				}
			}
	        $("#colorPick").fadeIn(200);
	    },
	    
	    hide: function() {
		    $( "#colorPick" ).fadeOut(200, function() {
			    $("#colorPick").remove();
			    return this;
			});
        },
        
    };
 
}( jQuery ));
 