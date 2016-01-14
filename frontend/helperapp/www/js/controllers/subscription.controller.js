angular.module('helperapp.controllers')

.controller('SubscriptionCtrl', function($scope, $ionicModal, $filter, Subscription, Tag) {

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

  $scope.subscribe = function() {
    $scope.subscription = new Subscription();
    $scope.subscriptionModal.show();
  };

  $scope.doSubscribe = function() {
    $scope.subscription.start = $filter('date')($scope.subscription.startDate, 'yyyy-MM-dd');
    $scope.subscription.end = $filter('date')($scope.subscription.endDate, 'yyyy-MM-dd');
    Subscription.save($scope.subscription, function() {
      $scope.subscription = new Subscription();
      $scope.subscriptions = Subscription.query();
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
    Subscription.delete(subscription, function() {
      $scope.subscriptions = Subscription.query();
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
