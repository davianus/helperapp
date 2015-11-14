/**
 * Created by MiiKE on 14.11.2015.
 */
angular.module('starter.controllers')
.controller('NeedsCtrl',function($scope, $ionicModal, $state) {

  $ionicModal.fromTemplateUrl('templates/new-need.html', {
    scope: $scope
  }).then(function(modal) {
    $scope.createModal = modal;
  });

  $ionicModal.fromTemplateUrl('templates/need-detail.html', {
    scope: $scope
  }).then(function(modal) {
    $scope.detailModal = modal;
  });

  // Triggered in the login modal to close it
  $scope.closeCreate = function() {
    $scope.createModal.hide();
  };

  // Open the login modal
  $scope.create = function() {
    $scope.createModal.show();
  };

  // Perform the login action when the user submits the login form
  $scope.doCreate = function() {
    //console.log('Doing login', $scope.loginData);
    //TODO: check password

    // Simulate a login delay. Remove this and replace with your login
    // code if using a login system
    $timeout(function() {
      $scope.closeCreate();
    }, 1000);
  };

  // Triggered in the login modal to close it
  $scope.closeDetail = function() {
    $scope.detailModal.hide();
  };

  // Open the login modal
  $scope.show = function(need) {
    $scope.detailNeed = angular.copy(need);
    $scope.detailModal.show();
  };
})
  .controller('AllCtrl',function($scope) {
    $scope.needs = [
      {id: 1, owner: "Caritas", tags:[{name:'Sessel'},{name:'Stühle'}], amount: '200 Stk.'},
      {id: 2, owner: "Diakonie", tags:[{name:'Essen'},{name:'Babynahrung'}], amount: '50 Stk.'},
      {id: 3, owner: "Rotes Kreuz", tags:[{name:'Schuhe'}], amount: '20 Stk.'}
    ]
  }).controller('ByMeCtrl',function($scope) {
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
  .controller('NewNeedCtrl', function($scope,$state) {

  });
