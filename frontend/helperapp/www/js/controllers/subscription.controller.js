angular.module('starter.controllers')

.controller('SubscriptionCtrl', function($scope, $ionicModal, $timeout, Subscription, Tag) {
  $scope.subscriptions = Subscription.query({
    user: 'test'
  });

  $ionicModal.fromTemplateUrl('templates/new-subscription.html', {
    scope: $scope
  }).then(function(modal) {
    $scope.subscriptionModal = modal;
  });

  // Triggered in the login modal to close it
  $scope.closeSubscribe = function() {
    $scope.subscriptionModal.hide();
  };

  // Open the login modal
  $scope.subscribe = function() {
    $scope.subscriptionModal.show();
  };

  // Perform the login action when the user submits the login form
  $scope.doSubscribe = function() {
    //console.log('Doing login', $scope.loginData);
    //TODO: check password

    // Simulate a login delay. Remove this and replace with your login
    // code if using a login system
    $timeout(function() {
      $scope.closeSubscribe();
    }, 1000);
  };

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

});
