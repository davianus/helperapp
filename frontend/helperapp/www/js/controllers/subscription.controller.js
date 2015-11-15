angular.module('starter.controllers')

.controller('SubscriptionCtrl', function($scope, $ionicModal, $timeout) {
  $scope.subscriptions = [
    {
      tags: [
        'Essen',
        'Apfel'
      ]
    },
    {
      tags: [
        'Möbel',
        'Sessel'
      ]
    }
  ];

  $ionicModal.fromTemplateUrl('templates/new-subscription.html', {
    scope: $scope
  }).then(function(modal) {
    $scope.modal = modal;
  });

  // Triggered in the login modal to close it
  $scope.closeSubscribe = function() {
    $scope.modal.hide();
  };

  // Open the login modal
  $scope.subscribe = function() {
    $scope.modal.show();
  };

  // Perform the login action when the user submits the login form
  $scope.doSubscribe = function() {
    //console.log('Doing login', $scope.loginData);
    //TODO: check password

    // Simulate a login delay. Remove this and replace with your login
    // code if using a login system
    $timeout(function() {
      $scope.closeNewSubscription();
    }, 1000);
  };

});
