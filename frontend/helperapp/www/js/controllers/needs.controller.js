/**
 * Created by MiiKE on 14.11.2015.
 */
angular.module('starter.controllers')
.controller('NeedsCtrl',function($scope, $state, Need, $ionicModal, Tag) {

  $scope.callbackMethod = function (query) {

    var tags = Tag.query({
      q: query
    }).$promise.then(function(result) {
      var tags = result;
      if (tags.length === 0) {
        tags.push(query);
      }

      return tags;
    });

    return tags;
  }

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
  $scope.need = {};
  // Perform the login action when the user submits the login form
  $scope.doCreate = function(need) {
    //console.log('Doing login', $scope.loginData);

    need.user = {username:window.localStorage['user']};
    need.location = {name:'Wien',location:[0,0]};
    Need.post(need);
    // Simulate a login delay. Remove this and replace with your login
    // code if using a login system

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

  $scope.newNeed = function() {
    $state.go('app.newNeed');
  }
  //$scope.needs = Need.query();
})
  .controller('AllCtrl',function($scope,Need) {
    $scope.needs = Need.query({'filter':'all'});
    /*$scope.needs = [
      {id: 1, owner: "Caritas", tags:[{name:'Sessel'},{name:'Stühle'}], amount: '200 Stk.'},
      {id: 2, owner: "Diakonie", tags:[{name:'Essen'},{name:'Babynahrung'}], amount: '50 Stk.'},
      {id: 3, owner: "Rotes Kreuz", tags:[{name:'Schuhe'}], amount: '20 Stk.'}
    ]*/
  }).controller('ByMeCtrl',function($scope,Need) {
    $scope.needs = Need.query({'filter':'user','user':window.localStorage['user']});

    /*$scope.needs = [
      {id: 1, owner: "By Me", tags:[{name:'Sessel'},{name:'Stühle'}], amount: '200 Stk.'},
      {id: 2, owner: "By Me", tags:[{name:'Essen'},{name:'Babynahrung'}], amount: '50 Stk.'}
    ]*/
  })
  .controller('ForMeCtrl',function($scope,Need) {
    $scope.needs = Need.query({'filter':'subscriptions','user':window.localStorage['user']});

    /*$scope.needs = [
      {id: 1, owner: "Caritas", tags:[{name:'Sessel'},{name:'Stühle'}], amount: '200 Stk.'},
      {id: 2, owner: "Diakonie", tags:[{name:'Essen'},{name:'Babynahrung'}], amount: '50 Stk.'}
    ]*/
  })
  .controller('ToDoCtrl',function($scope,Need) {
    $scope.needs = {};
    /*$scope.needs = [
      {id: 1, owner: "Caritas", tags:[{name:'Sessel'},{name:'Stühle'}], amount: '200 Stk.'}

    ]*/
  });
