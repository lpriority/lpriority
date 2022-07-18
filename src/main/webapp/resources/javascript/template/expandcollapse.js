var menu ;
var item;
var expanded;
var isBack = false;
$(document).ready(function(){
	var height = $(document).height();
	// HTML markup implementation, overlap mode
	$( '#menu' ).multilevelpushmenu({
		containersToPush: [$( '#pushobj' )],

		// Just for fun also changing the look of the menu
		wrapperClass: 'mlpm_w',
		menuInactiveClass: 'mlpm_inactive',
		collapsed: true,
		fullCollapse: true,
		menu: arrMenu,
		menuHeight: height,
		menuWidth: 300,   
		onExpandMenuEnd:function() {
			expanded = true;
		},
		onCollapseMenuEnd: function() {
			expanded = false;
		},
		onBackItemClick: function() {
			expanded = true;
			isBack = true;
		},
	    onItemClick: function() {
	    	// First argument is original event object
	        var event = arguments[0],
	            // Second argument is menu level object containing clicked item (<div> element)
	            $menuLevelHolder = arguments[1],
	            // Third argument is clicked item (<li> element)
	            $item = arguments[2],
	            // Fourth argument is instance settings/options object
	            options = arguments[3];
	        
	         itemName = $item[0].textContent;
	    	 menuLevelTitle = $menuLevelHolder[0].childNodes[0].textContent;
	    	 /*console.log($menuLevelHolder[0].childNodes[0].textContent);
	    	 console.log($item[0].textContent);*/
	    	 storeValue("menuLevelTitle",menuLevelTitle);
	    	 storeValue("itemName",itemName);
	        // You can do some cool stuff here before
	        // redirecting to href location
	        // like logging the event or even
	        // adding some parameters to href, etc...

	        // Anchor href
	        var itemHref = $item.find( 'a:first' ).attr( 'href' );
	        // Redirecting the page
	        location.href = itemHref;
	    }
	});

	// Full collapse
	$( '#fullcollapse' ).click(function(){
		$( '#menu' ).multilevelpushmenu( 'collapse' );
	});

	// Base expand
	$( '#baseexpand' ).click(function(){
		$( '#menu' ).multilevelpushmenu( 'expand' );
	});

	// Expand to Men's Clothing
	$( '#expandmensclothing' ).click(function(){
		// Use menu title for expanding just in case you know that there
		// is only one option with such title. If there is more then one
		// menu has with the same title, expand/collapse invoked with
		// title name as parameter won't work.
		$( '#menu' ).multilevelpushmenu( 'expand' , 'Men\'s Clothing' );
		
		// More safe way is to use methods like
		// $( '#menu' ).multilevelpushmenu( 'findmenusbytitle' , 'Mobile Phones' );
		// and then invoke expand method with desired menu level object
		// (e.g. if we have several menu objects with title 'Mobile Phones' but
		// we want to expand the first one)
		// var $phonemenu = $( '#menu' ).multilevelpushmenu( 'findmenusbytitle' , 'Mobile Phones' ).first();
		// and then
		// $( '#menu' ).multilevelpushmenu( 'expand' , $phonemenu );
	});

	// Expand to Mobile Phones
	$( '#expandmobilephones' ).click(function(){
		// Use menu title for expanding just in case you know that there
		// is only one option with such title. If there is more then one
		// menu has with the same title, expand/collapse invoked with
		// title name as parameter won't work.
		$( '#menu' ).multilevelpushmenu( 'expand' , 'Mobile Phones' );
		
		// More safe way is to use methods like
		// $( '#menu' ).multilevelpushmenu( 'findmenusbytitle' , 'Mobile Phones' );
		// and then invoke expand method with desired menu level object
		// (e.g. if we have several menu objects with title 'Mobile Phones' but
		// we want to expand the first one)
		// var $phonemenu = $( '#menu' ).multilevelpushmenu( 'findmenusbytitle' , 'Mobile Phones' ).first();
		// and then
		// $( '#menu' ).multilevelpushmenu( 'expand' , $phonemenu );
	});

	// Collapse to the level of 'Devices' menu
	$( '#collapsedevices' ).click(function(){
		// Have in mind that this will collapse to the level of 'Devices'
		// menu (level 1 in our case). So even when open path is not containing
		// 'Devices' menu it will collapse expanded menus to level 1; it's
		// basically the same as
		// $( '#menu' ).multilevelpushmenu( 'collapse' , 1 );
		$( '#menu' ).multilevelpushmenu( 'collapse' , 'Devices' );
	});
});

function open(){
	// Use menu title for expanding just in case you know that there
	// is only one option with such title. If there is more then one
	// menu has with the same title, expand/collapse invoked with
	// title name as parameter won't work.
	$( '#menu' ).multilevelpushmenu( 'collapse' );
	
	// More safe way is to use methods like
	// $( '#menu' ).multilevelpushmenu( 'findmenusbytitle' , 'Mobile Phones' );
	// and then invoke expand method with desired menu level object
	// (e.g. if we have several menu objects with title 'Mobile Phones' but
	// we want to expand the first one)
	// var $phonemenu = $( '#menu' ).multilevelpushmenu( 'findmenusbytitle' , 'Mobile Phones' ).first();
	// and then
	// $( '#menu' ).multilevelpushmenu( 'expand' , $phonemenu );
}	
