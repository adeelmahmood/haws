var app = angular.module('haws-master', 
		['ngResource', 'ui.router', 'ngMaterial', 'ngMdIcons' , 'ngMessages']);

app.controller('HomeCtrl', [ '$scope', '$http', '$resource', '$mdSidenav', '$mdToast', 
                                  function($scope, $http, $resource, $mdSidenav, $mdToast) {
	
}])
.config(function($stateProvider, $urlRouterProvider, $mdThemingProvider) {
	var customBlueMap = $mdThemingProvider.extendPalette('blue-grey', {
	    'contrastDefaultColor': 'light',
	    'contrastDarkColors': ['50'],
	    '50': 'ffffff'
	  });
	
	  $mdThemingProvider.definePalette('customBlue', customBlueMap);
	
	  $mdThemingProvider.theme('default')
	    .primaryPalette('customBlue', {
	      'default': '500',
	      'hue-1': '50'
	    })
	    .accentPalette('pink');
	
	//default home page
	$urlRouterProvider.otherwise("/");	
	
	//states
	$stateProvider
		.state('home', {
			url: '/',
			templateUrl: 'partials/home.html'
		})
		;
})
;
