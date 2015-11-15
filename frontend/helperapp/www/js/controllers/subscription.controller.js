angular.module('starter.controllers')

.controller('SubscriptionCtrl', function($scope, $ionicModal, $filter, Subscription, Tag) {

  $scope.subscription = new Subscription();

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
    $scope.subscription.user = 'test';
    $scope.subscription.start = $filter('date')($scope.subscription.startDate, 'yyyy-MM-dd');
    delete $scope.subscription.startDate;
    $scope.subscription.end = $filter('date')($scope.subscription.endDate, 'yyyy-MM-dd');
    delete $scope.subscription.endDate;
    Subscription.save($scope.subscription, function() {
      $scope.subscriptions = Subscription.query({
        user: 'test'
      });
    });
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
