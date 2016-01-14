angular.module('helperapp.controllers')

.controller('SubscriptionCtrl', function($scope, $ionicModal, $filter, Subscription, Tag) {

  $scope.subscription = new Subscription();

  $scope.subscriptions = Subscription.query({
    user: window.localStorage.username
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
    $scope.subscription.user = window.localStorage.username;
    $scope.subscription.start = $filter('date')($scope.subscription.startDate, 'yyyy-MM-dd');
    $scope.subscription.end = $filter('date')($scope.subscription.endDate, 'yyyy-MM-dd');
    Subscription.save($scope.subscription, function() {
      $scope.subscription = new Subscription();
      $scope.subscriptions = Subscription.query({
        user: window.localStorage.username
      });
      $scope.closeSubscribe();
    });
  };

  $scope.editSubscription = function(subscription) {
    $scope.subscription = angular.copy(subscription);
    $scope.subscription.startDate = new Date(Date.parse($scope.subscription.start));
    $scope.subscription.endDate = new Date(Date.parse($scope.subscription.end));
    $scope.subscribe();
  }

  $scope.deleteSubscription = function(subscription) {
    var subscription = angular.copy(subscription);
    subscription.user = window.localStorage.username;
    Subscription.delete(subscription, function() {
      $scope.subscriptions = Subscription.query({
        user: window.localStorage.username
      });
    });
  }

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
