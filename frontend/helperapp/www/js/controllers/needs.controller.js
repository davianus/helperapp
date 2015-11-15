/**
 * Created by MiiKE on 14.11.2015.
 */
angular.module('starter.controllers')
.controller('NeedsCtrl',function($scope, $state, Need, $ionicModal, Tag, Fulfillment) {

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

  $scope.fulfillment = new Fulfillment();
  $scope.doFulfillment = function() {
    $scope.fulfillment.user = window.localStorage.user;
    $scope.fulfillment.requestId = $scope.detailNeed.id;
    $scope.fulfillment.until = $filter('date')($scope.fulfillment.untilDate, 'yyyy-MM-dd');
    $scope.fulfillment = new Fulfillment();
    Fulfillment.save($scope.fulfillment, function() {
      $state.go('app.needs.todo');
    });
  };
})
  .controller('AllCtrl',function($scope,Need) {
    $scope.needs = Need.query({'filter':'all'});
  }).controller('ByMeCtrl',function($scope,Need) {
    $scope.needs = Need.query({'filter':'user','user':window.localStorage['user']});
  })
  .controller('ForMeCtrl',function($scope,Need) {
    $scope.needs = Need.query({'filter':'subscriptions','user':window.localStorage['user']});
  })
  .controller('ToDoCtrl',function($scope, Fulfillment) {
    $scope.needs = Fulfillment.query({user: window.localStorage.user});
  });
