angular.module('helperapp.controllers')

.controller('AppCtrl', function($scope, $state, $http) {

  // With the new view caching in Ionic, Controllers are only called
  // when they are recreated or on app start, instead of every page change.
  // To listen for when this page is active (for example, to refresh data),
  // listen for the $ionicView.enter event:
  //$scope.$on('$ionicView.enter', function(e) {
  //});

  $scope.registrate = function() {
    $scope.username = window.localStorage['username'];
    $scope.password = window.localStorage['password'];
    if( $scope.username && $scope.password) {
      $state.go('app.needs.all')
    } else {
      $state.go('registration');
    }
  };

  $scope.logout = function() {
    $http.defaults.headers.common.Authorization = 'Basic';
  }

});
