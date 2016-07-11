/**
 * 
 */

var TODO = (function (window){

	 'use strict';

	function init() {
		 $(".signUp_btn").on("click", signup_page);
	}
	 
	function signup_page() {
		var url = "/signUp";
		$(location).attr('href',url);
		//window.location.href = (url);
	}

	return {
		"init" : init
	}
})(window);

$(function(){
    TODO.init();
});