/**
 * 
 */

var TODO = (function (window){

	 'use strict';

	function init() {
		 $(".signUp_btn").on("click", signup_page);
		 $(".login_with_github").on("click", login_githup);
	}
	 
	function signup_page() {
		var url = "/signUp";
		$(location).attr('href',url);
		//window.location.href = (url);
	}
	
	function login_githup() {
		console.log("GITHUB");
		var url = "/github/login";
		$(location).attr('href',url);
	}

	return {
		"init" : init
	}
})(window);

$(function(){
    TODO.init();
});