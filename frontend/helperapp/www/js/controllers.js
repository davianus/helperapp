angular.module('starter.controllers', [])

.controller('AppCtrl', function($scope, $ionicModal, $timeout, $state) {

  // With the new view caching in Ionic, Controllers are only called
  // when they are recreated or on app start, instead of every page change.
  // To listen for when this page is active (for example, to refresh data),
  // listen for the $ionicView.enter event:
  //$scope.$on('$ionicView.enter', function(e) {
  //});

  // Form data for the login modal
  $scope.loginData = {};

  // Create the login modal that we will use later
  $ionicModal.fromTemplateUrl('templates/login.html', {
    scope: $scope
  }).then(function(modal) {
    $scope.modal = modal;
  });

  // Triggered in the login modal to close it
  $scope.closeLogin = function() {
    $scope.modal.hide();
  };

  // Open the login modal
  $scope.login = function() {
    $scope.modal.show();
  };

  // Perform the login action when the user submits the login form
  $scope.doLogin = function() {
    //console.log('Doing login', $scope.loginData);
    //TODO: check password
    $state.go('app.needs.byme')
    // Simulate a login delay. Remove this and replace with your login
    // code if using a login system
    $timeout(function() {
      $scope.closeLogin();
    }, 1000);
  };
})

.controller('NeedsCtrl',function($scope) {

})
  .controller('ByMeCtrl',function($scope) {
    $scope.needs = [
      {id: 1, owner: "By Me", tags:[{name:'Sessel'},{name:'Stühle'}], amount: '200 Stk.'},
      {id: 2, owner: "By Me", tags:[{name:'Essen'},{name:'Babynahrung'}], amount: '50 Stk.'}
    ]
  })
  .controller('ForMeCtrl',function($scope) {
    $scope.needs = [
      {id: 1, owner: "Caritas", tags:[{name:'Sessel'},{name:'Stühle'}], amount: '200 Stk.'},
      {id: 2, owner: "Diakonie", tags:[{name:'Essen'},{name:'Babynahrung'}], amount: '50 Stk.'}
    ]
  })
  .controller('ToDoCtrl',function($scope) {
    $scope.needs = [
      {id: 1, owner: "Caritas", tags:[{name:'Sessel'},{name:'Stühle'}], amount: '200 Stk.'}

    ]
  })


.controller('NeedDetailCtrl', function($scope, $stateParams) {
});
